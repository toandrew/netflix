// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

public interface SuccessResult
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements SuccessResult
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.command.SuccessResult";
		static final int TRANSACTION_getAuthToken = 1;
		static final int TRANSACTION_getData = 2;

		public static SuccessResult asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.SuccessResult");
			if (iinterface != null && (iinterface instanceof SuccessResult))
				return (SuccessResult)iinterface;
			else
				return new Proxy(ibinder);
		}

		public IBinder asBinder()
		{
			return this;
		}

		public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
			throws RemoteException
		{
			switch (i)
			{
			default:
				return super.onTransact(i, parcel, parcel1, j);

			case 1598968902: 
				parcel1.writeString("com.amazon.venezia.command.SuccessResult");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.amazon.venezia.command.SuccessResult");
				String s = getAuthToken();
				parcel1.writeNoException();
				parcel1.writeString(s);
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.amazon.venezia.command.SuccessResult");
				Map map = getData();
				parcel1.writeNoException();
				parcel1.writeMap(map);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.command.SuccessResult");
		}
	}

	private static class Stub.Proxy
		implements SuccessResult
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public String getAuthToken()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.SuccessResult");
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			s = parcel1.readString();
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return s;
		}

		public Map getData()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			java.util.HashMap hashmap;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.SuccessResult");
			mRemote.transact(2, parcel, parcel1, 0);
			parcel1.readException();
			hashmap = parcel1.readHashMap(getClass().getClassLoader());
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return hashmap;
		}

		public String getInterfaceDescriptor()
		{
			return "com.amazon.venezia.command.SuccessResult";
		}

		Stub.Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract String getAuthToken()
		throws RemoteException;

	public abstract Map getData()
		throws RemoteException;
}
