// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

public interface DecisionExpirationContext
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements DecisionExpirationContext
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.command.DecisionExpirationContext";
		static final int TRANSACTION_getExtensionData = 2;
		static final int TRANSACTION_getReason = 1;

		public static DecisionExpirationContext asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.DecisionExpirationContext");
			if (iinterface != null && (iinterface instanceof DecisionExpirationContext))
				return (DecisionExpirationContext)iinterface;
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
				parcel1.writeString("com.amazon.venezia.command.DecisionExpirationContext");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionExpirationContext");
				String s = getReason();
				parcel1.writeNoException();
				parcel1.writeString(s);
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionExpirationContext");
				Map map = getExtensionData();
				parcel1.writeNoException();
				parcel1.writeMap(map);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.command.DecisionExpirationContext");
		}
	}

	private static class Stub.Proxy
		implements DecisionExpirationContext
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public Map getExtensionData()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			java.util.HashMap hashmap;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionExpirationContext");
			mRemote.transact(2, parcel, parcel1, 0);
			parcel1.readException();
			hashmap = parcel1.readHashMap(getClass().getClassLoader());
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return hashmap;
		}

		public String getInterfaceDescriptor()
		{
			return "com.amazon.venezia.command.DecisionExpirationContext";
		}

		public String getReason()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionExpirationContext");
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			s = parcel1.readString();
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return s;
		}

		Stub.Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract Map getExtensionData()
		throws RemoteException;

	public abstract String getReason()
		throws RemoteException;
}
