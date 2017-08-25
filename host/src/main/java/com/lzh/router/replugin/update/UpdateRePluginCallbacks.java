package com.lzh.router.replugin.update;

import android.content.Context;
import android.content.Intent;

import com.qihoo360.replugin.RePluginCallbacks;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;

/**
 * 接入<a href="https://github.com/yjfnypeu/UpdatePlugin">UpdatePlugin</a>框架，用于方便的进行远程插件下载、安装、启动配置。
 */
public class UpdateRePluginCallbacks extends RePluginCallbacks{

    private IUpdateCombine combine;
    private UpdateConfig updateConfig;

    public UpdateRePluginCallbacks(Context context, UpdateConfig updateConfig, IUpdateCombine combine) {
        super(context);
        this.updateConfig = updateConfig;
        this.combine = combine;
    }

    @Override
    public boolean onPluginNotExistsForActivity(Context context, String plugin, Intent intent, int process) {
        UpdateBuilder.create(updateConfig)
                .checkEntity(combine.combine(plugin))
                .installStrategy(new RePluginInstall(plugin, context, intent))
                .check();
        return true;
    }
}
