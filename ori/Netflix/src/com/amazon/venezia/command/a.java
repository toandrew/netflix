// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.content.Intent;
import android.os.*;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			n, y

final class a implements n {

    private IBinder a;

    a(IBinder ibinder) {
        a = ibinder;
    }

    public final String a() throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();

        String s;
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
            a.transact(1, parcel, parcel1, 0);
            parcel1.readException();
            s = parcel1.readString();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return s;
    }

    public final void a(y y1) throws RemoteException {
        Parcel parcel;
        Parcel parcel1;

        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
            if (y1 == null)
                return;
            IBinder ibinder = y1.asBinder();

            parcel.writeStrongBinder(ibinder);
            a.transact(3, parcel, parcel1, 0);
            parcel1.readException();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return;
    }

    public final IBinder asBinder() {
        return a;
    }

    public final Intent b() throws RemoteException {
        Intent intent;

        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
            a.transact(2, parcel, parcel1, 0);
            parcel1.readException();

            if (parcel1.readInt() == 0) {
                intent = null;
            } else {

                intent = (Intent) Intent.CREATOR.createFromParcel(parcel1);
            }
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }

        parcel1.recycle();
        parcel.recycle();
        return intent;

    }

    public final Map c() throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        java.util.HashMap hashmap;
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
            a.transact(4, parcel, parcel1, 0);
            parcel1.readException();
            hashmap = parcel1.readHashMap(getClass().getClassLoader());
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return hashmap;
    }
}
