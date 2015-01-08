// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			FailureResult

final class ab
	implements FailureResult
{

	private IBinder a;

	ab(IBinder ibinder)
	{
		a = ibinder;
	}

	public final IBinder asBinder()
	{
		return a;
	}

	public final String getAuthToken()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		String s;
		parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
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

	public final String getButtonLabel()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		String s;
		parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
		a.transact(4, parcel, parcel1, 0);
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

	public final String getDisplayableMessage()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		String s;
		parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
		a.transact(3, parcel, parcel1, 0);
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

	public final String getDisplayableName()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		String s;
		parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
		a.transact(2, parcel, parcel1, 0);
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

	public final Map getExtensionData()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		java.util.HashMap hashmap;
		parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
		a.transact(6, parcel, parcel1, 0);
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

	public final boolean show()
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		int i;
		parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
		a.transact(5, parcel, parcel1, 0);
		parcel1.readException();
		i = parcel1.readInt();
		boolean flag;
		if (i != 0)
			flag = true;
		else
			flag = false;
		parcel1.recycle();
		parcel.recycle();
		return flag;
		Exception exception;
		exception;
		parcel1.recycle();
		parcel.recycle();
		throw exception;
	}
}
