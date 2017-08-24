package com.lzh.router.replugin.core;

import android.net.Uri;

/**
 * 用于当指定路由为匹配到对应的路由规则时。对路由的uri进行转换。转换为对应的RePlugin所需要的包名。
 *
 * @see IUriConverter#internal
 * Created by haoge on 2017/8/24.
 */
public interface IUriConverter {

    /**
     * @param uri 未找到的路由uri
     * @return 根据uri转换后的插件别名。
     */
    String transform(Uri uri);

    /**
     * 默认的插件路由规则转换器。此转换器的规则为：路由url的scheme为各自路由的别名。
     */
    IUriConverter internal = new IUriConverter() {
        @Override
        public String transform(Uri uri) {
            return uri.getScheme();
        }
    };
}
