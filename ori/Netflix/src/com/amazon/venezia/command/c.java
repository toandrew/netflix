// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			r, s, u, n

public abstract class c extends Binder implements r {

    public c() {
        attachInterface(this, "com.amazon.venezia.command.DecisionResult");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
            throws RemoteException {
        switch (i) {
        default:
            return super.onTransact(i, parcel, parcel1, j);

        case 1598968902:
            parcel1.writeString("com.amazon.venezia.command.DecisionResult");
            return true;

        case 1: // '\001'
            parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
            String s3 = a();
            parcel1.writeNoException();
            parcel1.writeString(s3);
            return true;

        case 2: // '\002'
            parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
            String s2 = b();
            parcel1.writeNoException();
            parcel1.writeString(s2);
            return true;

        case 3: // '\003'
            parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
            String s1 = c();
            parcel1.writeNoException();
            parcel1.writeString(s1);
            return true;

        case 4: // '\004'
            parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
            long l = d();
            parcel1.writeNoException();
            parcel1.writeLong(l);
            return true;

        case 5: // '\005'
            parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
            IBinder ibinder3 = parcel.readStrongBinder();
            Object obj;
            if (ibinder3 == null) {
                obj = null;
            } else {
                android.os.IInterface iinterface = ibinder3
                        .queryLocalInterface("com.amazon.venezia.command.DecisionExpirationContext");
                if (iinterface != null && (iinterface instanceof s))
                    obj = (s) iinterface;
                else
                    obj = new u(ibinder3);
            }
            a(((s) (obj)));
            parcel1.writeNoException();
            return true;

        case 6: // '\006'
            parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
            n n3 = e();
            parcel1.writeNoException();
            IBinder ibinder2;
            if (n3 != null)
                ibinder2 = n3.asBinder();
            else
                ibinder2 = null;
            parcel1.writeStrongBinder(ibinder2);
            return true;

        case 7: // '\007'
            parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
            n n2 = f();
            parcel1.writeNoException();
            IBinder ibinder1;
            if (n2 != null)
                ibinder1 = n2.asBinder();
            else
                ibinder1 = null;
            parcel1.writeStrongBinder(ibinder1);
            return true;

        case 8: // '\b'
            parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
            n n1 = g();
            parcel1.writeNoException();
            IBinder ibinder;
            if (n1 != null)
                ibinder = n1.asBinder();
            else
                ibinder = null;
            parcel1.writeStrongBinder(ibinder);
            return true;

        case 9: // '\t'
            parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
            java.util.Map map = h();
            parcel1.writeNoException();
            parcel1.writeMap(map);
            return true;
        }
    }
}
