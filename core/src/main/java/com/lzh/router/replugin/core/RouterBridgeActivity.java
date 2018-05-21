package com.lzh.router.replugin.core;

import android.app.Activity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (intent.hasExtra(Keys.KEY_URI)) {
            // 当含有URI值时。直接恢复路由
            Uri uri = intent.getParcelableExtra(Keys.KEY_URI);
            RouteBundleExtras extras = intent.getParcelableExtra(Keys.KEY_EXTRAS);
            Router.resume(uri, extras).open(this);
        } else {
            // 启动插件成功后，将其转回启动的插件(或宿主)中去进行路由恢复
            Intent resume = new Intent();
            String action = intent.getStringExtra(Keys.KEY_ACTION);
            resume.setAction(action);
            resume.putExtras(intent);
            sendBroadcast(resume);
        }

        finish();
    }
}