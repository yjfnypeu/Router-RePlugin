package com.lzh.router.replugin.plugin;

import android.content.Context;

import com.lzh.nonview.router.RouterConfiguration;

/**
 * RePlugin插件配置入口。
 * Created by haoge on 2017/8/24.
 */
public final class PluginRouterConfiguration {

    IUriConverter converter = new PluginRouterCallback.InternalConverter();
    IPluginCallback callback;

    /**
     * 初始化加载。此方法每个插件只需要被加载一次。请尽早进行初始化。
     * @param hostPackage 宿主包名：用于指定启动、连接远程路由服务。为各插件提供路由索引功能
     * @param alias 插件别名：路由启动器将会使用此别名作为唯一标识。
     * @param context 用于启动远程任务的。
     */
    public static void init(String hostPackage, String alias, Context context) {
        RouterConfiguration.get().startHostService(hostPackage, context, alias);
        RouterConfiguration.get().setRemoteFactory(new PluginRemoteFactory(alias));
        RouterConfiguration.get().setCallback(new PluginRouterCallback(context));
        // 设置路由启动器
        RouterConfiguration.get().setActionLauncher(PluginActionLauncher.class);
        RouterConfiguration.get().setActivityLauncher(PluginActivityLauncher.class);
    }

    public PluginRouterConfiguration setCallback(IPluginCallback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 设置路由uri转换器。
     *
     * @see IUriConverter
     * @param converter non-null.
     * @return configuration
     */
    public PluginRouterConfiguration setConverter(IUriConverter converter) {
        if (converter != null) {
            this.converter = converter;
        }
        return this;
    }

    private static PluginRouterConfiguration configuration = new PluginRouterConfiguration();
    private PluginRouterConfiguration() {}
    public static PluginRouterConfiguration get() {
        return configuration;
    }
}
