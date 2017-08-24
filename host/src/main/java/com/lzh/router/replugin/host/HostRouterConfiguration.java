package com.lzh.router.replugin.host;

import android.content.Context;

import com.lzh.nonview.router.RouterConfiguration;

/**
 * 宿主配置入口
 * Created by haoge on 2017/8/24.
 */
public final class HostRouterConfiguration {

    public static void init(String hostPackage, Context context) {
        RouterConfiguration.get().startHostService(hostPackage, context);

    }

}
