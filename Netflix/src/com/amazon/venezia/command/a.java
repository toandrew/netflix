// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.content.Intent;
import android.os.*;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			n, y

final class a
	implements n
{

	private IBinder a;

	a(IBinder ibinder)
	{
		a = ibinder;
	}

	public final String a()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		String s;
		parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
		a.transact(1, parcel, parcel1, 0);
		parcel1.readException();
		s = parcel1.readString();
		parcel1.recycle();
		parcel.recycle();
		return s;
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
	}

	public final void a(y y1)
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
		if (y1 == null)
			break MISSING_BLOCK_LABEL_59;
		IBinder ibinder = y1.asBinder();
_L1:
		parcel.writeStrongBinder(ibinder);
		a.transact(3, parcel, parcel1, 0);
		parcel1.readException();
		parcel1.recycle();
		parcel.recycle();
		return;
		ibinder = null;
		  goto _L1
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
	}

	public final IBinder asBinder()
	{
		return a;
	}

	public final Intent b()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
		a.transact(2, parcel, parcel1, 0);
		parcel1.readException();
		if (parcel1.readInt() == 0) goto _L2; else goto _L1
_L1:
		Intent intent1 = (Intent)Intent.CREATOR.createFromParcel(parcel1);
		Intent intent = intent1;
_L4:
		parcel1.recycle();
		parcel.recycle();
		return intent;
_L2:
		intent = null;
		if (true) goto _L4; else goto _L3
_L3:
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
	}

	public final Map c()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		java.util.HashMap hashmap;
		parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
		a.transact(4, parcel, parcel1, 0);
		parcel1.readException();
		hashmap = parcel1.readHashMap(getClass().getClassLoader());
		parcel1.recycle();
		parcel.recycle();
		return hashmap;
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
	}
}
