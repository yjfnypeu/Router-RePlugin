package com.lzh.router.replugin.core;

import android.net.Uri;

/**
 * 插件的回调。用于在对插件路由的接入过程中。出现的问题进行通知。
 * @author haoge
 */
public interface IPluginCallback {

    /**
     * 当此uri所代表的路由为无效时。回调通知到此.
     *
     * <p>若通知到此，则代表未能通过此uri使用{@link IUriConverter}转换器获取到对应的插件别名进行启动
     *
     * @param uri uri
     */
    void onInvalidUri(Uri uri);

    /**
     * 当此uri所代表的路由未找到时且此uri所对应的插件也启动了的情况下。回调通知到此。
     *
     * <p>若通知到此。则代表此uri尚未被定义。可选择在此提示用于升级新版。
     *
     * @param uri uri
     * @param alias 插件别名。
     */
    void notFound(Uri uri, String alias);

    /**
     * 当此uri对应的插件被加载成功。且成功恢复时。回调通知到此。此回调运行于主线程中。
     * @param uri 恢复的路由uri
     */
    void onResume(Uri uri);

    /**
     * 当未匹配到此uri对应的路由规则且此uri所对应的插件别名尚未运行(即插件未运行)，则启动RePlugin的加载插件功能，此为开始加载指定插件时的回调。
     *
     * <p>
     *     此回调运行于主线程，可在此针对插件首次加载时，提供进度条通知。避免UI无反应导致用户多次点击
     * </p>
     *
     * @param uri uri
     * @param alias 插件别名。
     */
    void onStartLoading(Uri uri, String alias);

    /**
     * 当加载指定插件完成后的回调，此处不在意是否插件加载成功。只是加载完成状态。
     *
     * <p>
     *     此回调运行于主线程，可在此对{@link #onStartLoading(Uri, String)}中所做操作做个闭合，调用了{@link #onStartLoading(Uri, String)}的必定会调用此方法。
     * </p>
     *
     * @param uri uri
     * @param alias 插件别名。
     */
    void onLoadedCompleted(Uri uri, String alias);
}
