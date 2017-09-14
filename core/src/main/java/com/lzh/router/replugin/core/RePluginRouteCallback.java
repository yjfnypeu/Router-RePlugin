package com.lzh.router.replugin.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.route.RouteCallback;
import com.qihoo360.replugin.RePlugin;

import java.lang.ref.WeakReference;
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
    private RePluginRouteCallback() {}
    public static RePluginRouteCallback get() {
        return instance;
    }

    private WeakReference<Context> context;// application context.
    private IUriConverter converter = IUriConverter.internal;
    private IPluginCallback callback;

    private RouteCallback routeCallback;

    public RePluginRouteCallback setContext(Context context) {
        if (context != null) {
            this.context = new WeakReference<>(context.getApplicationContext());
        }
        return this;
    }

    public RePluginRouteCallback setConverter(IUriConverter converter) {
        if (converter != null) {
            this.converter = converter;
        }
        return this;
    }

    public RePluginRouteCallback setCallback(IPluginCallback callback) {
        this.callback = callback;
        return this;
    }

    public RePluginRouteCallback setRouteCallback(RouteCallback routeCallback) {
        this.routeCallback = routeCallback;
        return this;
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
        pool.execute(new StartBridgeTask(getValidContext(), alias, uri,RouterConfiguration.get().restoreExtras(uri)));

    }

    /**
     * 获取有效的context进行跳转。一般不会被触发。
     * @return 保存的有效context或者Host context。
     */
    private Context getValidContext() {
        Context valid = context.get();
        if (valid != null) {
            return valid;
        }
        return RePlugin.getHostContext();
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
            Intent intent = RePlugin.createIntent(alias, RouterBridgeActivity.class.getCanonicalName());
            intent.putExtra("uri", uri);
            intent.putExtra("extras", extras);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            RePlugin.startActivity(context, intent);

            main.post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        callback.onLoadedCompleted(uri, alias);
                    }
                }
            });
        }
    }
}
