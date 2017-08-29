package com.lzh.router.replugin.host;

import android.content.Context;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.route.RouteCallback;
import com.lzh.router.replugin.core.IPluginCallback;
import com.lzh.router.replugin.core.IUriConverter;
import com.lzh.router.replugin.core.RePluginRouteCallback;

/**
 * 宿主配置入口
 * Created by haoge on 2017/8/24.
 */
@SuppressWarnings("unused")
public final class HostRouterConfiguration {

    /**
     * 初始化加载。此方法每个插件只需要被加载一次。请尽早进行初始化。
     * @param hostPackage 宿主包名：用于指定启动、连接远程路由服务。为各插件提供路由索引功能
     * @param context 用于启动远程任务的。
     */
    public static void init(String hostPackage, Context context) {
        // 启动并连接远程路由服务。
        RouterConfiguration.get().startHostService(hostPackage, context);
        // 初始化callback.
        RouterConfiguration.get().setRemoteFactory(new HostRemoteFactory());
        RouterConfiguration.get().setCallback(RePluginRouteCallback.get().setContext(context));
        // 设置路由启动器
        RouterConfiguration.get().setActionLauncher(HostActionLauncher.class);
        RouterConfiguration.get().setActivityLauncher(HostActivityLauncher.class);
    }

    public HostRouterConfiguration setCallback(IPluginCallback callback) {
        RePluginRouteCallback.get().setCallback(callback);
        return this;
    }

    public HostRouterConfiguration setRouteCallback(RouteCallback callback) {
        RePluginRouteCallback.get().setRouteCallback(callback);
        return this;
    }

    /**
     * 设置路由uri转换器。
     *
     * @see IUriConverter
     * @param converter non-null.
     * @return configuration
     */
    public HostRouterConfiguration setConverter(IUriConverter converter) {
        if (converter != null) {
            RePluginRouteCallback.get().setConverter(converter);
        }
        return this;
    }

    private static HostRouterConfiguration configuration = new HostRouterConfiguration();
    private HostRouterConfiguration() {}
    public static HostRouterConfiguration get() {
        return configuration;
    }

}
