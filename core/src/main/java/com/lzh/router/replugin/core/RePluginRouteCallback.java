package com.lzh.router.replugin.core;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.route.RouteCallback;
import com.lzh.nonview.router.tools.CacheStore;
import com.qihoo360.replugin.RePlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 针对RePlugin框架所配置的路由回调。将在此进行连接Router-RePlugin配置：
 *
 * Created by haoge on 2017/8/24.
 */
public final class RePluginRouteCallback implements RouteCallback{

    private ExecutorService pool = Executors.newSingleThreadExecutor();
    private static RePluginRouteCallback instance = new RePluginRouteCallback();
    private String selfAlias;
    private RePluginRouteCallback() {}
    public static RePluginRouteCallback get() {
        return instance;
    }

    private IUriConverter converter = IUriConverter.internal;
    private IPluginCallback callback;

    private RouteCallback routeCallback;

    public void setAlias(String alias) {
        if (!TextUtils.isEmpty(alias)) {
            this.selfAlias = alias;
        }
    }

    public void setConverter(IUriConverter converter) {
        if (converter != null) {
            this.converter = converter;
        }
    }

    public void setCallback(IPluginCallback callback) {
        this.callback = callback;
    }

    public void setRouteCallback(RouteCallback routeCallback) {
        this.routeCallback = routeCallback;
    }

    @Override
    public void notFound(Uri uri, NotFoundException e) {
        if (routeCallback != null) {
            routeCallback.notFound(uri, e);
        }

        String alias = converter.transform(uri);
        if (TextUtils.isEmpty(alias)) {
            // 表示此uri非法。不处理
            if (callback != null) {
                callback.onInvalidUri(uri);
            }
            return;
        }

        // 用于判断此别名所代表的插件路由
        if (RouterConfiguration.get().isRegister(alias)) {
            // 当插件已被注册过。
            if (callback != null) {
                callback.notFound(uri, alias);
            }
            return;
        }

        // 将中转任务放入子线程中进行启动。避免阻塞UI线程。
        pool.execute(new StartBridgeTask(RouterConfiguration.get().restorContext(uri), alias, uri,RouterConfiguration.get().restoreExtras(uri)));

    }

    @Override
    public void onOpenSuccess(Uri uri, RouteRule rule) {
        if (routeCallback != null) {
            routeCallback.onOpenSuccess(uri, rule);
        }
    }

    @Override
    public void onOpenFailed(Uri uri, Throwable e) {
        if (routeCallback != null) {
            routeCallback.onOpenFailed(uri, e);
        } else {
            e.printStackTrace();
        }
    }

    public IPluginCallback getCallback() {
        return callback;
    }

    private static class StartBridgeTask implements Runnable {
        private static Handler main = new Handler(Looper.getMainLooper());
        Context context;
        String alias;
        Uri uri;
        RouteBundleExtras extras;

        StartBridgeTask(Context context, String alias, Uri uri, RouteBundleExtras extras) {
            this.context = context;
            this.alias = alias;
            this.uri = uri;
            this.extras = extras;
        }

        @Override
        public void run() {
            final IPluginCallback callback = RePluginRouteCallback.get().getCallback();
            main.post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        callback.onStartLoading(uri, alias);
                    }
                }
            });

            // 请求加载插件并启动中间桥接页面.便于加载插件成功后恢复路由。
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(alias, RouterBridgeActivity.class.getCanonicalName()));
            if (RePlugin.isPluginInstalled(alias)) {
                // 对于已安装、尚未启动的插件。将待启动的路由相关信息存入临时缓存中。待插件启动成功后通知回当前插件。进行恢复
                // 此种方式能最大程度的保留路由启动信息。但是因为有额外的缓存，所以只适用于此种已安装的插件。避免内存泄漏
                String selfAlias = RePluginRouteCallback.instance.selfAlias;
                intent.putExtra(Keys.KEY_ACTION, RouterResumeReceiver.obtainAction(selfAlias, TextUtils.isEmpty(selfAlias)));
                intent.putExtra(Keys.KEY_RESUME_CONTEXT_INDEX, CacheStore.get().put(context));
                intent.putExtra(Keys.KEY_URI_INDEX, CacheStore.get().put(uri));
                intent.putExtra(Keys.KEY_EXTRAS_INDEX, CacheStore.get().put(extras));
            } else {
                // 对于未安装的远程插件。传递路由信息。待下载完成、启动成功后直接进行恢复
                // 此种方式将会丢失路由回调、路由拦截器等启动信息。但是没有内存泄漏。适用于远程插件。
                intent.putExtra(Keys.KEY_URI, uri);
                intent.putExtra(Keys.KEY_EXTRAS, RouterResumeReceiver.copy(extras));
            }
            if (!(context instanceof Activity)) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            RePlugin.startActivity(context, intent);

            main.post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        callback.onLoadedCompleted(uri, alias);
                    }
                }
            });

            main.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 10秒后进行
                }
            }, 10 * 1000 * 1000);
        }
    }

}
