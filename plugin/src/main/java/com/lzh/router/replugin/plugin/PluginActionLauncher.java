package com.lzh.router.replugin.plugin;

import android.content.Context;
import android.text.TextUtils;

import com.lzh.nonview.router.launcher.DefaultActionLauncher;
import com.lzh.router.replugin.core.RouterBridgeReceiver;

/**
 * 针对RePlugin框架定制的动作路由启动器。
 */
public class PluginActionLauncher extends DefaultActionLauncher {

    @Override
    public void open(Context context) throws Exception {
        String alias = alias();
        boolean isHost = isHost();
        if (isHost || !TextUtils.isEmpty(alias)) {
            RouterBridgeReceiver.start(context, alias, isHost, uri, extras);
        } else {
            super.open(context);
        }
    }

    private String alias() {
        return remote.getString("alias");
    }

    private boolean isHost() {
        return remote.getBoolean("isHost");
    }
}
