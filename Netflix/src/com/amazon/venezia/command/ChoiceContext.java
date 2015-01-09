// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

public interface ChoiceContext
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements ChoiceContext
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.command.ChoiceContext";
		static final int TRANSACTION_getExtensionData = 1;

		public static ChoiceContext asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.ChoiceContext");
			if (iinterface != null && (iinterface instanceof ChoiceContext))
				return (ChoiceContext)iinterface;
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
				parcel1.writeString("com.amazon.venezia.command.ChoiceContext");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.amazon.venezia.command.ChoiceContext");
				Map map = getExtensionData();
				parcel1.writeNoException();
				parcel1.writeMap(map);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.command.ChoiceContext");
		}
	}

	private static class Stub.Proxy
		implements ChoiceContext
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
			parcel.writeInterfaceToken("com.amazon.venezia.command.ChoiceContext");
			mRemote.transact(1, parcel, parcel1, 0);
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
			return "com.amazon.venezia.command.ChoiceContext";
		}

		Stub.Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract Map getExtensionData()
		throws RemoteException;
}
