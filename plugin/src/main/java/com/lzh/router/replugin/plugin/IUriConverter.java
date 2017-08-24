package com.lzh.router.replugin.plugin;

import android.net.Uri;

/**
 * 用于当指定路由为匹配到对应的路由规则时。对路由的uri进行转换。转换为对应的RePlugin所需要的包名。
 *
 * @see PluginRouterCallback.InternalConverter
 * Created by haoge on 2017/8/24.
 */
public interface IUriConverter {

    /**
     * @param uri 未找到的路由uri
     * @return 根据uri转换后的插件别名。
     */
    String transform(Uri uri);
}
