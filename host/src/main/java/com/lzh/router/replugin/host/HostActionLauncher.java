package com.lzh.router.replugin.host;

import android.content.Context;
import android.text.TextUtils;

import com.lzh.nonview.router.launcher.DefaultActionLauncher;
import com.lzh.router.replugin.core.RouterResumeReceiver;

/**
 * 针对RePlugin框架定制的宿主使用的动作路由启动器。
 */
public class HostActionLauncher extends DefaultActionLauncher {

    @Override
    public void open(Context context) {
        String alias = remote.getString("alias");
        if (TextUtils.isEmpty(alias)) {
            // 代表在宿主中
            super.open(context);
        } else {
            // 桥接到指定插件并进行处理
            RouterResumeReceiver.start(context, alias, false, uri, extras);
        }
    }
}
