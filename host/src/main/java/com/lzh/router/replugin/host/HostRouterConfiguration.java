package com.lzh.router.replugin.host;

import android.content.Context;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.router.replugin.core.IPluginCallback;
import com.lzh.router.replugin.core.IUriConverter;
import com.lzh.router.replugin.core.RePluginRouteCallback;

/**
 * 宿主配置入口
 * Created by haoge on 2017/8/24.
 */
public final class HostRouterConfiguration {

    private RePluginRouteCallback callback;
    /**
     * 初始化加载。此方法每个插件只需要被加载一次。请尽早进行初始化。
     * @param hostPackage 宿主包名：用于指定启动、连接远程路由服务。为各插件提供路由索引功能
     * @param context 用于启动远程任务的。
     */
    public static void init(String hostPackage, Context context) {
        // 启动并连接远程路由服务。
        RouterConfiguration.get().startHostService(hostPackage, context);
        // 初始化callback.
        RouterConfiguration.get().setCallback(get().callback = new RePluginRouteCallback(context));
        // 设置路由启动器
        RouterConfiguration.get().setActionLauncher(HostActionLauncher.class);
        RouterConfiguration.get().setActivityLauncher(HostActivityLauncher.class);
    }

    public HostRouterConfiguration setCallback(IPluginCallback callback) {
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
    public HostRouterConfiguration setConverter(IUriConverter converter) {
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

    private static HostRouterConfiguration configuration = new HostRouterConfiguration();
    private HostRouterConfiguration() {}
    public static HostRouterConfiguration get() {
        return configuration;
    }

}
