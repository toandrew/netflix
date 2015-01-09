// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.IInterface;
import android.os.RemoteException;

// Referenced classes of package com.amazon.venezia.command:
//			FailureResult, SuccessResult, j, r

public interface f extends IInterface {

    public abstract void a(FailureResult failureresult) throws RemoteException;

    public abstract void a(SuccessResult successresult) throws RemoteException;

    public abstract void a(j j) throws RemoteException;

    public abstract void a(r r) throws RemoteException;
}
