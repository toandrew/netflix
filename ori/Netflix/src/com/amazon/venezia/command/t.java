// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			f, FailureResult, SuccessResult, j, 
//			r

final class t implements f {

    private IBinder a;

    t(IBinder ibinder) {
        a = ibinder;
    }

    public final void a(FailureResult failureresult) throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
            IBinder ibinder;
            if (failureresult == null) {
                ibinder = null;
            } else {
                ibinder = failureresult.asBinder();
            }

            parcel.writeStrongBinder(ibinder);
            a.transact(2, parcel, parcel1, 0);
            parcel1.readException();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return;
    }

    public final void a(SuccessResult successresult) throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
            IBinder ibinder;
            if (successresult == null) {
                ibinder = null;
            } else {
                ibinder = successresult.asBinder();
            }

            parcel.writeStrongBinder(ibinder);
            a.transact(1, parcel, parcel1, 0);
            parcel1.readException();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return;
    }

    public final void a(j j1) throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
            IBinder ibinder;
            if (j1 == null) {
                ibinder = null;
            } else {
                ibinder = j1.asBinder();
            }

            parcel.writeStrongBinder(ibinder);
            a.transact(4, parcel, parcel1, 0);
            parcel1.readException();
        } finally {
            parcel1.recycle();
            parcel.recycle();
        }
        return;

    }

    public final void a(r r1) throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
            IBinder ibinder;
            if (r1 == null) {
                ibinder = null;
            } else {
                ibinder = r1.asBinder();
            }

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
}
