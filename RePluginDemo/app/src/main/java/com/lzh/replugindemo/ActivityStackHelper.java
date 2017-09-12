package com.lzh.replugindemo;

import android.app.Activity;

import java.util.LinkedList;

/**
 * Created by haoge on 2017/9/12.
 */

public class ActivityStackHelper {

    private static LinkedList<Activity> stacks = new LinkedList<>();

    public static void add(Activity activity) {
        if (!stacks.contains(activity)) {
            stacks.add(activity);
        }
    }

    public static void remove(Activity activity) {
        if (stacks.contains(activity)) {
            stacks.remove(activity);
        }
    }

    public static Activity top() {
        return stacks.getLast();
    }
}
