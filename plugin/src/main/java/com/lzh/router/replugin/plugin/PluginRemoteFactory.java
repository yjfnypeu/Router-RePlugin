package com.lzh.router.replugin.plugin;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.protocol.IRemoteFactory;

/**
 * 插件远程数据创建器。用于对其他插件提供额外参数。供其他插件或宿主做路由启动。
 * Created by haoge on 2017/8/24.
 */
class PluginRemoteFactory implements IRemoteFactory {

    String alias;

    public PluginRemoteFactory(String alias) {
        this.alias = alias;
    }

    @Override
    public Bundle createRemote(Context application, RouteRule rule) {
        Bundle bundle = new Bundle();
        bundle.putString("alias", alias);
        return bundle;
    }
}
