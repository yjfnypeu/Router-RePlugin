package com.lzh.router.replugin.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.route.RouteCallback;
import com.qihoo360.replugin.RePlugin;

/**
 * 针对RePlugin框架所配置的路由回调。将在此进行连接Router-RePlugin配置：
 *
 * Created by haoge on 2017/8/24.
 */
public final class RePluginRouteCallback implements RouteCallback{
    private Context context;// application context.
    private IUriConverter converter = IUriConverter.internal;
    private IPluginCallback callback;
    private RouteCallback routeCallback;

    public RePluginRouteCallback(Context context) {
        this.context = context.getApplicationContext();
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

        // 请求加载插件并启动中间桥接页面.便于加载插件成功后恢复路由。
        Intent intent = RePlugin.createIntent(alias, RouterBridgeActivity.class.getCanonicalName());
        intent.putExtra("uri", uri);
        intent.putExtra("extras", RouterConfiguration.get().restoreExtras(uri));
        RePlugin.startActivity(context, intent);
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
}
