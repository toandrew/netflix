// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			SuccessResult

final class x implements SuccessResult {

    private IBinder a;

    x(IBinder ibinder) {
        a = ibinder;
    }

    public final IBinder asBinder() {
        return a;
    }

    public final String getAuthToken() throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();

        String s;
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.SuccessResult");
            a.transact(1, parcel, parcel1, 0);
            parcel1.readException();
            s = parcel1.readString();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return s;
    }

    public final Map getData() throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        java.util.HashMap hashmap;
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.SuccessResult");
            a.transact(2, parcel, parcel1, 0);
            parcel1.readException();
            hashmap = parcel1.readHashMap(getClass().getClassLoader());
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return hashmap;
    }
}
