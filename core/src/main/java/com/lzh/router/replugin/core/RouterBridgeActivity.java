package com.lzh.router.replugin.core;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.RouteBundleExtras;

/**
 * 插件间跳转的中间桥接页面。当Router启动一个未被加载的插件的路由时。在加载插件成功后。将会跳转到此页面并恢复之前启动的路由。
 *
 * <p>外部不应该直接跳转至此页面。此页面跳转处应只存在于{@link RePluginRouteCallback}中</p>
 */
public class RouterBridgeActivity extends Activity {

    Uri uri;
    RouteBundleExtras extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = getIntent().getParcelableExtra("uri");
        extras = getIntent().getParcelableExtra("extras");
        if (uri != null) {
            // 通过传递过来的uri与其对应的extras。恢复之前插件未加载时的路由。
            Router.resume(uri, extras).open(RouterBridgeActivity.this);
        }
        finish();
    }

}