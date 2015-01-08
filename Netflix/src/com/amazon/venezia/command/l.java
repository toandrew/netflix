// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			h, w, f

final class l
	implements h
{

	private IBinder a;

	l(IBinder ibinder)
	{
		a = ibinder;
	}

	public final void a(w w1, f f1)
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		parcel.writeInterfaceToken("com.amazon.venezia.command.CommandService");
		if (w1 == null) goto _L2; else goto _L1
_L1:
		IBinder ibinder = w1.asBinder();
_L5:
		parcel.writeStrongBinder(ibinder);
		if (f1 == null) goto _L4; else goto _L3
_L3:
		IBinder ibinder1 = f1.asBinder();
_L6:
		parcel.writeStrongBinder(ibinder1);
		a.transact(1, parcel, parcel1, 0);
		parcel1.readException();
		parcel1.recycle();
		parcel.recycle();
		return;
_L2:
		ibinder = null;
		  goto _L5
_L4:
		ibinder1 = null;
		  goto _L6
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
		  goto _L5
	}

	public final IBinder asBinder()
	{
		return a;
	}
}
