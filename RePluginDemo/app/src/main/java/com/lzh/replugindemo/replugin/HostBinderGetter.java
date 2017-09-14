package com.lzh.replugindemo.replugin;

import android.os.IBinder;
import android.os.RemoteException;

import com.qihoo360.replugin.IBinderGetter;

public class HostBinderGetter implements IBinderGetter {

    @Override
    public IBinder get() throws RemoteException {
        return null;
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
