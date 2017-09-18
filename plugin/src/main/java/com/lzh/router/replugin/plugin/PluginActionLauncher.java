package com.lzh.router.replugin.plugin;

import android.content.Context;
import android.text.TextUtils;

import com.lzh.nonview.router.launcher.DefaultActionLauncher;
import com.lzh.router.replugin.core.RouterBridgeActivity;

/**
 * 针对RePlugin框架定制的动作路由启动器。
 */
public class PluginActionLauncher extends DefaultActionLauncher {

    @Override
    public void open(Context context) throws Exception {
        String alias = alias();
        if (TextUtils.isEmpty(alias)) {
            super.open(context);
        } else {
            RouterBridgeActivity.start(context, alias, uri, extras);
        }
    }

    private String alias() {
        return remote.getString("alias");
    }
}
