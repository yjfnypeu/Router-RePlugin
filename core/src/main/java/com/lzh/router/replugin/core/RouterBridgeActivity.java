package com.lzh.router.replugin.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.route.IRoute;
import com.qihoo360.replugin.RePlugin;

/**
 * 插件间跳转的中间桥接页面。当Router启动一个未被加载的插件的路由时。在加载插件成功后。将会跳转到此页面并恢复之前启动的路由。
 *
 * <p>外部不应该直接跳转至此页面。此页面跳转处应只存在于{@link RePluginRouteCallback}中</p>
 */
public class RouterBridgeActivity extends Activity {

    Uri uri;
    RouteBundleExtras extras;
    IPluginCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = getIntent().getParcelableExtra("uri");
        extras = getIntent().getParcelableExtra("extras");
        callback = RePluginRouteCallback.get().getCallback();
        if (uri != null) {
            // 通过传递过来的uri与其对应的extras。恢复之前插件未加载时的路由。
            IRoute resume = Router.resume(uri, extras);
            if (resume instanceof IRoute.EmptyRoute) {
                // 当恢复的路由为EmptyRoute时。代表此为无效路由。不进行启动操作。
                return;
            }
            resume.open(RouterBridgeActivity.this);
        }
        IPluginCallback callback = RePluginRouteCallback.get().getCallback();
        if (callback != null) {
            RePluginRouteCallback.get().getCallback().onResume(uri);
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public static void start(Context context, String alias, Uri uri, RouteBundleExtras extras) {
        // 请求加载插件并启动中间桥接页面.便于加载插件成功后恢复路由。
        Intent intent = RePlugin.createIntent(alias, RouterBridgeActivity.class.getCanonicalName());
        intent.putExtra("uri", uri);
        intent.putExtra("extras", extras);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        RePlugin.startActivity(context, intent);
    }
}