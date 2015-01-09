// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			h, w, f

final class l implements h {

    private IBinder a;

    l(IBinder ibinder) {
        a = ibinder;
    }

    public final void a(w w1, f f1) throws RemoteException {
        Parcel parcel;
        Parcel parcel1;
        parcel = Parcel.obtain();
        parcel1 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.amazon.venezia.command.CommandService");
            IBinder ibinder;
            if (w1 == null) {
                ibinder = null;
            } else {
                ibinder = w1.asBinder();
            }
            parcel.writeStrongBinder(ibinder);

            IBinder ibinder1;
            if (f1 == null) {
                ibinder1 = null;
            } else {
                ibinder1 = f1.asBinder();
            }
            parcel.writeStrongBinder(ibinder1);
            a.transact(1, parcel, parcel1, 0);
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
