// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			FailureResult

final class ab implements FailureResult {

    private IBinder a;

    ab(IBinder ibinder) {
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
            parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
            a.transact(1, parcel, parcel1, 0);
            parcel1.readException();
            s = parcel1.readString();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return s;
    }

    public final String getButtonLabel() throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        String s;
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
            a.transact(4, parcel, parcel1, 0);
            parcel1.readException();
            s = parcel1.readString();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return s;
    }

    public final String getDisplayableMessage() throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        String s;
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
            a.transact(3, parcel, parcel1, 0);
            parcel1.readException();
            s = parcel1.readString();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return s;
    }

    public final String getDisplayableName() throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        String s;
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
            a.transact(2, parcel, parcel1, 0);
            parcel1.readException();
            s = parcel1.readString();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return s;
    }

    public final Map getExtensionData() throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        java.util.HashMap hashmap;
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
            a.transact(6, parcel, parcel1, 0);
            parcel1.readException();
            hashmap = parcel1.readHashMap(getClass().getClassLoader());
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return hashmap;
    }

    public final boolean show() throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        boolean flag;
        int i;
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
            a.transact(5, parcel, parcel1, 0);
            parcel1.readException();
            i = parcel1.readInt();

            if (i != 0)
                flag = true;
            else
                flag = false;
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return flag;
    }
}
