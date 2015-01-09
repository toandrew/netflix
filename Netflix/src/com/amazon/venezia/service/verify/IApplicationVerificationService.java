// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.service.verify;

import android.os.*;

public interface IApplicationVerificationService
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements IApplicationVerificationService
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.service.verify.IApplicationVerificationService";
		static final int TRANSACTION_getAmazonId = 3;
		static final int TRANSACTION_getDeviceId = 4;
		static final int TRANSACTION_getToken = 2;
		static final int TRANSACTION_reportVerificationResults = 1;

		public static IApplicationVerificationService asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.service.verify.IApplicationVerificationService");
			if (iinterface != null && (iinterface instanceof IApplicationVerificationService))
				return (IApplicationVerificationService)iinterface;
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
				parcel1.writeString("com.amazon.venezia.service.verify.IApplicationVerificationService");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.amazon.venezia.service.verify.IApplicationVerificationService");
				String s3 = parcel.readString();
				boolean flag;
				if (parcel.readInt() != 0)
					flag = true;
				else
					flag = false;
				reportVerificationResults(s3, flag, parcel.readString());
				parcel1.writeNoException();
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.amazon.venezia.service.verify.IApplicationVerificationService");
				String s2 = getToken(parcel.readString());
				parcel1.writeNoException();
				parcel1.writeString(s2);
				return true;

			case 3: // '\003'
				parcel.enforceInterface("com.amazon.venezia.service.verify.IApplicationVerificationService");
				String s1 = getAmazonId();
				parcel1.writeNoException();
				parcel1.writeString(s1);
				return true;

			case 4: // '\004'
				parcel.enforceInterface("com.amazon.venezia.service.verify.IApplicationVerificationService");
				String s = getDeviceId();
				parcel1.writeNoException();
				parcel1.writeString(s);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.service.verify.IApplicationVerificationService");
		}
	}

	private static class Stub.Proxy
		implements IApplicationVerificationService
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public String getAmazonId()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.service.verify.IApplicationVerificationService");
			mRemote.transact(3, parcel, parcel1, 0);
			parcel1.readException();
			s = parcel1.readString();
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return s;
		}

		public String getDeviceId()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.service.verify.IApplicationVerificationService");
			mRemote.transact(4, parcel, parcel1, 0);
			parcel1.readException();
			s = parcel1.readString();
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return s;
		}

		public String getInterfaceDescriptor()
		{
			return "com.amazon.venezia.service.verify.IApplicationVerificationService";
		}

		public String getToken(String s)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s1;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.service.verify.IApplicationVerificationService");
			parcel.writeString(s);
			mRemote.transact(2, parcel, parcel1, 0);
			parcel1.readException();
			s1 = parcel1.readString();
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return s1;
		}

		public void reportVerificationResults(String s, boolean flag, String s1)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.service.verify.IApplicationVerificationService");
			parcel.writeString(s);
			int i;
			if (flag)
				i = 1;
			else
				i = 0;
			parcel.writeInt(i);
			parcel.writeString(s1);
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return;
		}

		Stub.Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract String getAmazonId()
		throws RemoteException;

	public abstract String getDeviceId()
		throws RemoteException;

	public abstract String getToken(String s)
		throws RemoteException;

	public abstract void reportVerificationResults(String s, boolean flag, String s1)
		throws RemoteException;
}
