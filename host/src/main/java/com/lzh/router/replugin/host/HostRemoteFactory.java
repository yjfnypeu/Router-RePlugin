package com.lzh.router.replugin.host;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.protocol.IRemoteFactory;

class HostRemoteFactory implements IRemoteFactory {
    @Override
    public Bundle createRemote(Context application, RouteRule rule) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHost", true);
        return bundle;
    }
}
