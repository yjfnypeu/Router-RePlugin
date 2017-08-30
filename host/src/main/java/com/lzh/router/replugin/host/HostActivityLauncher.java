package com.lzh.router.replugin.host;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lzh.nonview.router.launcher.DefaultActivityLauncher;
import com.qihoo360.replugin.RePlugin;

/**
 * 针对RePlugin框架定制的宿主使用的页面路由启动器
 */
public class HostActivityLauncher extends DefaultActivityLauncher {

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
            RePlugin.startActivityForResult((Activity) context, createIntent(context), extras.getRequestCode());
            overridePendingTransition((Activity) context, extras);
        }
    }

    private String alias() {
        // remote: 由其他组件通过IRemoteFactory接口创建的bundle并通过远程服务传递过来的共享数据。
        // 在此取出进行适配：(存取数据参考PluginRemoteFactory)
        // 若不含有别名。表示此路由匹配的页面。在当前插件中。或者在host中。
        if (remote == null || !remote.containsKey("alias")) {
            return null;
        }
        return remote.getString("alias");
    }
}
