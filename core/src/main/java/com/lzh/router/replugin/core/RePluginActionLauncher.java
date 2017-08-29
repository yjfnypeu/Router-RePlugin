package com.lzh.router.replugin.core;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lzh.nonview.router.launcher.DefaultActionLauncher;

/**
 * 定制的动作路由启动器：
 *
 * <ul>
 *     <li>当无插件别名数据时。代表未在当前插件内部使用。直接使用默认逻辑触发动作路由。</li>
 *     <li>当含有插件别名数据时，代表动作路由定制在其他插件中。将uri与extras通过广播发送到对方的插件中去进行恢复启动。</li>
 * </ul>
 */
public class RePluginActionLauncher extends DefaultActionLauncher {

    @Override
    public void open(Context context) throws Exception {
        String alias = alias();
        if (TextUtils.isEmpty(alias)) {
            super.open(context);
        } else {
            Intent intent = new Intent(RouterBridgeReceiver.ACTION_PREFIX + alias);
            intent.putExtra("uri", uri);
            intent.putExtra("extras", extras);
            context.sendBroadcast(intent);
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

        // 宿主使用一个独特的别名作为标识符。
        if (remote.containsKey("isHost")) {
            alias = "ROUTER.HOST";
        }
        return alias;
    }
}
