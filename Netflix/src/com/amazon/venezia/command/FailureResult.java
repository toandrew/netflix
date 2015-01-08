// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

public interface FailureResult
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements FailureResult
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.command.FailureResult";
		static final int TRANSACTION_getAuthToken = 1;
		static final int TRANSACTION_getButtonLabel = 4;
		static final int TRANSACTION_getDisplayableMessage = 3;
		static final int TRANSACTION_getDisplayableName = 2;
		static final int TRANSACTION_getExtensionData = 6;
		static final int TRANSACTION_show = 5;

		public static FailureResult asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.FailureResult");
			if (iinterface != null && (iinterface instanceof FailureResult))
				return (FailureResult)iinterface;
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
				Map map = getExtensionData();
				parcel1.writeNoException();
				parcel1.writeMap(map);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.command.FailureResult");
		}
	}

	private static class Stub.Proxy
		implements FailureResult
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
			parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
			mRemote.transact(1, parcel, parcel1, 0);
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

		public String getButtonLabel()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
			mRemote.transact(4, parcel, parcel1, 0);
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

		public String getDisplayableMessage()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
			mRemote.transact(3, parcel, parcel1, 0);
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

		public String getDisplayableName()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
			mRemote.transact(2, parcel, parcel1, 0);
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

		public Map getExtensionData()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			java.util.HashMap hashmap;
			parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
			mRemote.transact(6, parcel, parcel1, 0);
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

		public String getInterfaceDescriptor()
		{
			return "com.amazon.venezia.command.FailureResult";
		}

		public boolean show()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			int i;
			parcel.writeInterfaceToken("com.amazon.venezia.command.FailureResult");
			mRemote.transact(5, parcel, parcel1, 0);
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

		Stub.Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract String getAuthToken()
		throws RemoteException;

	public abstract String getButtonLabel()
		throws RemoteException;

	public abstract String getDisplayableMessage()
		throws RemoteException;

	public abstract String getDisplayableName()
		throws RemoteException;

	public abstract Map getExtensionData()
		throws RemoteException;

	public abstract boolean show()
		throws RemoteException;
}
