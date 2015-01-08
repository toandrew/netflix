// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			s

final class u
	implements s
{

	private IBinder a;

	u(IBinder ibinder)
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
		String s1;
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionExpirationContext");
		a.transact(1, parcel, parcel1, 0);
		parcel1.readException();
		s1 = parcel1.readString();
		parcel1.recycle();
		parcel.recycle();
		return s1;
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

	public final Map b()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		java.util.HashMap hashmap;
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionExpirationContext");
		a.transact(2, parcel, parcel1, 0);
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
