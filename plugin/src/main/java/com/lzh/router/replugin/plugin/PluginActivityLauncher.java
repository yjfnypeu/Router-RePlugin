package com.lzh.router.replugin.plugin;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lzh.nonview.router.launcher.DefaultActivityLauncher;
import com.lzh.router.replugin.core.RouterBridgeActivity;
import com.qihoo360.replugin.RePlugin;

/**
 * 针对RePlugin框架定制的页面路由启动器
 */
public class PluginActivityLauncher extends DefaultActivityLauncher {


    @Override
    public Intent createIntent(Context context) {
        String alias = alias();
        if (TextUtils.isEmpty(alias)) {
            return super.createIntent(context);
        } else {
            Intent intent = RePlugin.createIntent(alias, rule.getRuleClz());
            intent.putExtras(bundle);
            intent.putExtras(extras.getExtras());
            intent.addFlags(extras.getFlags());
            return intent;
        }
    }

    @Override
    public void open(android.support.v4.app.Fragment fragment) throws Exception {
        if (TextUtils.isEmpty(alias())) {
            super.open(fragment);
        } else {
            open(fragment.getActivity());
        }
    }

    @Override
    public void open(Fragment fragment) throws Exception {
        if (TextUtils.isEmpty(alias())) {
            super.open(fragment);
        } else {
            open(fragment.getActivity());
        }
    }

    @Override
    public void open(Context context) throws Exception {
        // 根据是否含有alias判断是否需要使用RePlugin进行跳转
        if (TextUtils.isEmpty(alias())) {
            super.open(context);
        } else {
            RouterBridgeActivity.start(context, alias(), uri, extras);
        }
    }

    private String alias() {
        String alias = remote.getString("alias");
        return alias;
    }
}
