// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			FailureResult

public abstract class b extends Binder implements FailureResult {

    public b() {
        attachInterface(this, "com.amazon.venezia.command.FailureResult");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
            throws RemoteException {
        switch (i) {
        default:
            return super.onTransact(i, parcel, parcel1, j);

        case 1598968902:
            parcel1.writeString("com.amazon.venezia.command.FailureResult");
            return true;

        case 1: // '\001'
            parcel.enforceInterface("com.amazon.venezia.command.FailureResult");
            String s3 = getAuthToken();
            parcel1.writeNoException();
            parcel1.writeString(s3);
            return true;

        case 2: // '\002'
            parcel.enforceInterface("com.amazon.venezia.command.FailureResult");
            String s2 = getDisplayableName();
            parcel1.writeNoException();
            parcel1.writeString(s2);
            return true;

        case 3: // '\003'
            parcel.enforceInterface("com.amazon.venezia.command.FailureResult");
            String s1 = getDisplayableMessage();
            parcel1.writeNoException();
            parcel1.writeString(s1);
            return true;

        case 4: // '\004'
            parcel.enforceInterface("com.amazon.venezia.command.FailureResult");
            String s = getButtonLabel();
            parcel1.writeNoException();
            parcel1.writeString(s);
            return true;

        case 5: // '\005'
            parcel.enforceInterface("com.amazon.venezia.command.FailureResult");
            boolean flag = show();
            parcel1.writeNoException();
            int k;
            if (flag)
                k = 1;
            else
                k = 0;
            parcel1.writeInt(k);
            return true;

        case 6: // '\006'
            parcel.enforceInterface("com.amazon.venezia.command.FailureResult");
            java.util.Map map = getExtensionData();
            parcel1.writeNoException();
            parcel1.writeMap(map);
            return true;
        }
    }
}
