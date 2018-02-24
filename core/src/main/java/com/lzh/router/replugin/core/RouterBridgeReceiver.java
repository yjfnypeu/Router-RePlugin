package com.lzh.router.replugin.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.RouteBundleExtras;

/**
 * @author haoge on 2018/2/24.
 */
public class RouterBridgeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uri = intent.getParcelableExtra("uri");
        RouteBundleExtras extras = intent.getParcelableExtra("extras");
        Router.resume(uri, extras).open(context);
    }

    public static void registerSelf(Context context, String alias, boolean isHost) {
        String action = obtainAction(alias, isHost);

        IntentFilter filter = new IntentFilter(action);
        context.registerReceiver(new RouterBridgeReceiver(), filter);
    }

    public static void start(Context context, String alias, boolean isHost, Uri uri, RouteBundleExtras extras) {
        String action = obtainAction(alias, isHost);

        Intent intent = new Intent(action);
        intent.putExtra("uri", uri);
        intent.putExtra("extras", extras);
        context.sendBroadcast(intent);
    }

    private static String obtainAction(String alias, boolean isHost) {
        String action;
        if (isHost) {
            action = "com.RePlugin.Router.Host";
        } else {
            action = "com.RePlugin.Router.Plugin." + alias;
        }
        return action;
    }
}
