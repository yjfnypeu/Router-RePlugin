package com.lzh.router.replugin.core;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lzh.nonview.router.launcher.DefaultActionLauncher;

public class RePluginActionLauncher extends DefaultActionLauncher {

    @Override
    public void open(Context context) throws Exception {
        String alias = alias();
        if (TextUtils.isEmpty(alias)) {
            super.open(context);
        } else {
            context.sendBroadcast(new Intent(RouterBridgeReceiver.ACTION_PREFIX + alias));
        }
    }

    private String alias() {
        String alias = "";
        if (remote == null) {
            return null;
        }
        if (remote.containsKey("alias")) {
            return remote.getString("alias");
        }

        if (remote.containsKey("isHost")) {
            alias = "ROUTER.HOST";
        }
        return alias;
    }
}
