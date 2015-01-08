// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			r, s, m, n

final class aa
	implements r
{

	private IBinder a;

	aa(IBinder ibinder)
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
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
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

	public final void a(s s1)
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
		if (s1 == null)
			break MISSING_BLOCK_LABEL_59;
		IBinder ibinder = s1.asBinder();
_L1:
		parcel.writeStrongBinder(ibinder);
		a.transact(5, parcel, parcel1, 0);
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

	public final String b()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		String s1;
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
		a.transact(2, parcel, parcel1, 0);
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

	public final String c()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		String s1;
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
		a.transact(3, parcel, parcel1, 0);
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

	public final long d()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		long l;
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
		a.transact(4, parcel, parcel1, 0);
		parcel1.readException();
		l = parcel1.readLong();
		parcel1.recycle();
		parcel.recycle();
		return l;
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
	}

	public final n e()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		n n;
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
		a.transact(6, parcel, parcel1, 0);
		parcel1.readException();
		n = m.a(parcel1.readStrongBinder());
		parcel1.recycle();
		parcel.recycle();
		return n;
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
	}

	public final n f()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		n n;
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
		a.transact(7, parcel, parcel1, 0);
		parcel1.readException();
		n = m.a(parcel1.readStrongBinder());
		parcel1.recycle();
		parcel.recycle();
		return n;
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
	}

	public final n g()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		n n;
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
		a.transact(8, parcel, parcel1, 0);
		parcel1.readException();
		n = m.a(parcel1.readStrongBinder());
		parcel1.recycle();
		parcel.recycle();
		return n;
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
	}

	public final Map h()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		java.util.HashMap hashmap;
		parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
		a.transact(9, parcel, parcel1, 0);
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
