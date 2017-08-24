package com.lzh.router.replugin.host;

import android.net.Uri;

import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.route.RouteCallback;

/**
 * Created by haoge on 2017/8/24.
 */

public class HostRouterCallback implements RouteCallback {
    @Override
    public void notFound(Uri uri, NotFoundException e) {

    }

    @Override
    public void onOpenSuccess(Uri uri, RouteRule rule) {

    }

    @Override
    public void onOpenFailed(Uri uri, Throwable e) {

    }
}
