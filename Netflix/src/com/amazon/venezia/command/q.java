// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			h, l, w, d, 
//			f, t

public abstract class q extends Binder implements h {

    public q() {
        attachInterface(this, "com.amazon.venezia.command.CommandService");
    }

    public static h a(IBinder ibinder) {
        if (ibinder == null)
            return null;
        android.os.IInterface iinterface = ibinder
                .queryLocalInterface("com.amazon.venezia.command.CommandService");
        if (iinterface != null && (iinterface instanceof h))
            return (h) iinterface;
        else
            return new l(ibinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
            throws RemoteException {
        IBinder ibinder;
        switch (i) {
        default:
            return super.onTransact(i, parcel, parcel1, j);

        case 1598968902:
            parcel1.writeString("com.amazon.venezia.command.CommandService");
            return true;

        case 1: // '\001'
            parcel.enforceInterface("com.amazon.venezia.command.CommandService");
            ibinder = parcel.readStrongBinder();
            break;
        }
        Object obj;
        IBinder ibinder1;
        Object obj1;
        if (ibinder == null) {
            obj = null;
        } else {
            android.os.IInterface iinterface = ibinder
                    .queryLocalInterface("com.amazon.venezia.command.Command");
            if (iinterface != null && (iinterface instanceof w))
                obj = (w) iinterface;
            else
                obj = new d(ibinder);
        }
        ibinder1 = parcel.readStrongBinder();
        if (ibinder1 == null) {
            obj1 = null;
        } else {
            android.os.IInterface iinterface1 = ibinder1
                    .queryLocalInterface("com.amazon.venezia.command.ResultCallback");
            if (iinterface1 != null && (iinterface1 instanceof f))
                obj1 = (f) iinterface1;
            else
                obj1 = new t(ibinder1);
        }
        a(((w) (obj)), ((f) (obj1)));
        parcel1.writeNoException();
        return true;
    }
}
