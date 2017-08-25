package com.lzh.router.replugin.core;

import android.net.Uri;

/**
 * 插件的回调。用于在对插件路由的接入过程中。出现的问题进行通知。
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
     *
     * @param uri uri
     * @param alias 插件别名。
     */
    void notFound(Uri uri, String alias);

    /**
     * 当此uri对应的插件被加载成功。且成功恢复时。回调通知到此
     * @param uri 恢复的路由uri
     */
    void onResume(Uri uri);
}
