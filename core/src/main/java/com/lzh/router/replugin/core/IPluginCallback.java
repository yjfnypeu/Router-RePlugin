package com.lzh.router.replugin.core;

import android.net.Uri;

/**
 * 插件的回调。
 * Created by haoge on 2017/8/24.
 */
public interface IPluginCallback {

    /**
     * 当此uri所代表的路由为无效时。回调通知到此
     * @param uri uri
     */
    void onInvalidUri(Uri uri);

    /**
     * 当此uri所代表的路由未找到时且此uri所对应的插件也启动了的情况下。回调通知到此。
     * @param uri uri
     * @param alias 插件别名。
     */
    void notFound(Uri uri, String alias);
}
