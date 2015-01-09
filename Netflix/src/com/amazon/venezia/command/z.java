// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			w

public abstract class z extends Binder implements w {

    public z() {
        attachInterface(this, "com.amazon.venezia.command.Command");
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
            throws RemoteException {
        switch (i) {
        default:
            return super.onTransact(i, parcel, parcel1, j);

        case 1598968902:
            parcel1.writeString("com.amazon.venezia.command.Command");
            return true;

        case 1: // '\001'
            parcel.enforceInterface("com.amazon.venezia.command.Command");
            String s2 = a();
            parcel1.writeNoException();
            parcel1.writeString(s2);
            return true;

        case 2: // '\002'
            parcel.enforceInterface("com.amazon.venezia.command.Command");
            String s1 = b();
            parcel1.writeNoException();
            parcel1.writeString(s1);
            return true;

        case 3: // '\003'
            parcel.enforceInterface("com.amazon.venezia.command.Command");
            String s = c();
            parcel1.writeNoException();
            parcel1.writeString(s);
            return true;

        case 4: // '\004'
            parcel.enforceInterface("com.amazon.venezia.command.Command");
            java.util.Map map = d();
            parcel1.writeNoException();
            parcel1.writeMap(map);
            return true;
        }
    }
}
