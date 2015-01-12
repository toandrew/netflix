// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

public interface Command
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements Command
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.command.Command";
		static final int TRANSACTION_getData = 4;
		static final int TRANSACTION_getName = 1;
		static final int TRANSACTION_getPackageName = 3;
		static final int TRANSACTION_getVersion = 2;

		public static Command asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.Command");
			if (iinterface != null && (iinterface instanceof Command))
				return (Command)iinterface;
			else
				return new com.amazon.venezia.command.Command.Stub.Proxy(ibinder);
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
				parcel1.writeString("com.amazon.venezia.command.Command");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.amazon.venezia.command.Command");
				String s2 = getName();
				parcel1.writeNoException();
				parcel1.writeString(s2);
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.amazon.venezia.command.Command");
				String s1 = getVersion();
				parcel1.writeNoException();
				parcel1.writeString(s1);
				return true;

			case 3: // '\003'
				parcel.enforceInterface("com.amazon.venezia.command.Command");
				String s = getPackageName();
				parcel1.writeNoException();
				parcel1.writeString(s);
				return true;

			case 4: // '\004'
				parcel.enforceInterface("com.amazon.venezia.command.Command");
				Map map = getData();
				parcel1.writeNoException();
				parcel1.writeMap(map);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.command.Command");
		}
	}

	static class Proxy
		implements Command
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
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
			parcel.writeInterfaceToken("com.amazon.venezia.command.Command");
			mRemote.transact(4, parcel, parcel1, 0);
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
			return "com.amazon.venezia.command.Command";
		}

		public String getName()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.Command");
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			s = parcel1.readString();
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return s;
		}

		public String getPackageName()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.Command");
			mRemote.transact(3, parcel, parcel1, 0);
			parcel1.readException();
			s = parcel1.readString();
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return s;
		}

		public String getVersion()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.Command");
			mRemote.transact(2, parcel, parcel1, 0);
			parcel1.readException();
			s = parcel1.readString();
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return s;
		}

		Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract Map getData()
		throws RemoteException;

	public abstract String getName()
		throws RemoteException;

	public abstract String getPackageName()
		throws RemoteException;

	public abstract String getVersion()
		throws RemoteException;
}
