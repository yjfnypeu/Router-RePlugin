package com.lzh.router.replugin.plugin;

import android.content.Context;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.router.replugin.core.IPluginCallback;
import com.lzh.router.replugin.core.IUriConverter;
import com.lzh.router.replugin.core.RePluginRouteCallback;

/**
 * RePlugin插件配置入口。
 * Created by haoge on 2017/8/24.
 */
public final class PluginRouterConfiguration {

    private RePluginRouteCallback callback;
    /**
     * 初始化加载。此方法每个插件只需要被加载一次。请尽早进行初始化。
     * @param hostPackage 宿主包名：用于指定启动、连接远程路由服务。为各插件提供路由索引功能
     * @param alias 插件别名：路由启动器将会使用此别名作为唯一标识。
     * @param context 用于启动远程任务的。
     */
    public static void init(String hostPackage, String alias, Context context) {
        // 启动并连接远程路由服务。
        RouterConfiguration.get().startHostService(hostPackage, context, alias);
        // 提供远程数据创建工厂
        RouterConfiguration.get().setRemoteFactory(new PluginRemoteFactory(alias));
        // 初始化callback.
        RouterConfiguration.get().setCallback(get().callback = new RePluginRouteCallback(context));
        // 设置路由启动器
        RouterConfiguration.get().setActionLauncher(PluginActionLauncher.class);
        RouterConfiguration.get().setActivityLauncher(PluginActivityLauncher.class);
    }

    public PluginRouterConfiguration setCallback(IPluginCallback callback) {
        check();
        this.callback.setCallback(callback);
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
        check();
        if (converter != null) {
            this.callback.setConverter(converter);
        }
        return this;
    }

    private void check() {
        if (callback == null) {
            throw new RuntimeException("Should call PluginRouterConfiguration.init() first!");
        }
    }

    private static PluginRouterConfiguration configuration = new PluginRouterConfiguration();
    private PluginRouterConfiguration() {}
    public static PluginRouterConfiguration get() {
        return configuration;
    }
}
