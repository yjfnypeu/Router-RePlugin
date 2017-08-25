package com.lzh.router.replugin.core;

import android.net.Uri;

/**
 * 当uri所对应的路由未找到时。对uri进行转换。转换为对应的需要加载的插件别名。提供给{@link RePluginRouteCallback} 做插件接入。
 *
 * @see IUriConverter#internal
 * Created by haoge on 2017/8/24.
 */
public interface IUriConverter {

    /**
     * 将从uri中获取出正确的插件别名。或者返回null，代表此uri无效。
     * @param uri 未找到的路由uri
     * @return 根据uri转换后的插件别名。或者当别名为不支持时。返回null。
     */
    String transform(Uri uri);

    /**
     * 默认的插件路由规则转换器。此转换器的规则为：使用路由uri的scheme作为各自插件的别名。
     */
    IUriConverter internal = new IUriConverter() {
        @Override
        public String transform(Uri uri) {
            return uri.getScheme();
        }
    };
}
