package com.lzh.router.replugin.plugin;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.RouteBundleExtras;

/**
 * 由于RePlugin无法直接指定加载某插件。所以使用Router跳转时可能由于插件未被加载。导致启动失败。
 *
 * 此类需结合{@link PluginRouterCallback}使用。callback使用统一规则匹配。发现对应的插件并启动。然后插件被启动后在此中转页面做路由恢复
 */
public class RouterBridgeActivity extends Activity {

    Uri uri;
    RouteBundleExtras extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 恢复路由并销毁当前页面。
        uri = getIntent().getParcelableExtra("uri");
        extras = getIntent().getParcelableExtra("extras");
        Router.resume(uri, extras).open(RouterBridgeActivity.this);
        finish();
    }

}