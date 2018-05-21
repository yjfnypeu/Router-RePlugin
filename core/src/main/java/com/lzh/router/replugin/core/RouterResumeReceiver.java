package com.lzh.router.replugin.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.tools.CacheStore;

/**
 * 用于进行跨插件启动路由桥接、恢复的接收器。
 *
 * @author haoge on 2018/2/24.
 */
public class RouterResumeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uri;
        RouteBundleExtras extras;
        Context resume;
        if (intent.hasExtra(Keys.KEY_URI_INDEX)) {
            resume = CacheStore.get().get(intent.getIntExtra(Keys.KEY_RESUME_CONTEXT_INDEX, -1));
            extras = CacheStore.get().get(intent.getIntExtra(Keys.KEY_EXTRAS_INDEX, -1));
            uri = CacheStore.get().get(intent.getIntExtra(Keys.KEY_URI_INDEX, -1));
        } else {
            resume = context;
            extras = intent.getParcelableExtra(Keys.KEY_EXTRAS);
            uri = intent.getParcelableExtra(Keys.KEY_URI);
        }

        IPluginCallback callback = RePluginRouteCallback.get().getCallback();
        if (resume != null && uri != null) {
            Router.resume(uri, extras).open(resume);
            if (callback != null) {
                callback.onResume(uri);
            }
        }
    }

    public static void registerSelf(Context context, String alias, boolean isHost) {
        String action = obtainAction(alias, isHost);

        IntentFilter filter = new IntentFilter(action);
        context.registerReceiver(new RouterResumeReceiver(), filter);
    }

    public static void start(Context context, String alias, boolean isHost, Uri uri, RouteBundleExtras extras) {
        String action = obtainAction(alias, isHost);

        Intent intent = new Intent(action);
        intent.putExtra(Keys.KEY_URI, uri);
        intent.putExtra(Keys.KEY_EXTRAS, copy(extras));
        context.sendBroadcast(intent);
    }

    public static String obtainAction(String alias, boolean isHost) {
        String action;
        if (isHost) {
            action = "com.RePlugin.Router.Host";
        } else {
            action = "com.RePlugin.Router.Plugin." + alias;
        }
        return action;
    }

    public static RouteBundleExtras copy(RouteBundleExtras extras) {
        // 新版本的Router跨组件传输时，采用了临时缓存策略(使用CacheStore将一些不可序列化的数据进行临时存储)
        // 所以这里为了防止跨插件传递数据时导致内存泄漏。将这部分的数据移除
        // 简而言之，就是为此次路由配置的拦截器、回调等数据，将会失效
        RouteBundleExtras copy = new RouteBundleExtras();
        copy.addExtras(extras.getExtras());
        copy.addFlags(extras.getFlags());
        copy.setRequestCode(extras.getRequestCode());
        copy.setOutAnimation(extras.getOutAnimation());
        copy.setInAnimation(extras.getInAnimation());
        return copy;
    }
}
