package com.lzh.baselib;

import java.io.Serializable;

/**
 * Created by haoge on 2017/9/13.
 */

public class SerialData implements Serializable{
    private String name;

    public String getName() {
        return name;
    }

    public SerialData setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
