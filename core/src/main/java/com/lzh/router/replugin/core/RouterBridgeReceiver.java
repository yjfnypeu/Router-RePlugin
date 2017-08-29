package com.lzh.router.replugin.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.RouteBundleExtras;

public class RouterBridgeReceiver extends BroadcastReceiver {

    static final String ACTION_PREFIX = "com.lzh.router.replugin.actionlauncher.action.";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        Uri uri = intent.getParcelableExtra("uri");
        RouteBundleExtras extras = intent.getParcelableExtra("extras");
        Router.resume(uri, extras);
    }

    public static void registerSelf (Context context, String alias) {
        IntentFilter filter = new IntentFilter(ACTION_PREFIX + alias);
        context.getApplicationContext().registerReceiver(new RouterBridgeReceiver(), filter);
    }
}
