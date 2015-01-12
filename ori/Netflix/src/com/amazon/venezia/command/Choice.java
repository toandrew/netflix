// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.content.Intent;
import android.os.*;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			ChoiceContext

public interface Choice
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements Choice
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.command.Choice";
		static final int TRANSACTION_choose = 3;
		static final int TRANSACTION_getDisplayableName = 1;
		static final int TRANSACTION_getExtensionData = 4;
		static final int TRANSACTION_getIntent = 2;

		public static Choice asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.Choice");
			if (iinterface != null && (iinterface instanceof Choice))
				return (Choice)iinterface;
			else
				return new com.amazon.venezia.command.Choice.Stub.Proxy(ibinder);
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
				parcel1.writeString("com.amazon.venezia.command.Choice");
				return true;

			case TRANSACTION_getDisplayableName: // '\001'
				parcel.enforceInterface("com.amazon.venezia.command.Choice");
				String s = getDisplayableName();
				parcel1.writeNoException();
				parcel1.writeString(s);
				return true;

			case TRANSACTION_getIntent: // '\002'
				parcel.enforceInterface("com.amazon.venezia.command.Choice");
				Intent intent = getIntent();
				parcel1.writeNoException();
				if (intent != null)
				{
					parcel1.writeInt(1);
					intent.writeToParcel(parcel1, 1);
				} else
				{
					parcel1.writeInt(0);
				}
				return true;

			case TRANSACTION_choose: // '\003'
				parcel.enforceInterface("com.amazon.venezia.command.Choice");
				choose(ChoiceContext.Stub.asInterface(parcel.readStrongBinder()));
				parcel1.writeNoException();
				return true;

			case TRANSACTION_getExtensionData: // '\004'
				parcel.enforceInterface("com.amazon.venezia.command.Choice");
				Map map = getExtensionData();
				parcel1.writeNoException();
				parcel1.writeMap(map);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.command.Choice");
		}
	}

	static class Proxy
		implements Choice
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public void choose(ChoiceContext choicecontext)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
			IBinder ibinder;
			if (choicecontext == null) {
			    ibinder = null;
			}
			ibinder = choicecontext.asBinder();
			parcel.writeStrongBinder(ibinder);
			mRemote.transact(3, parcel, parcel1, 0);
			parcel1.readException();
		}finally{
			parcel1.recycle();
			parcel.recycle();
		}
		}

		public String getDisplayableName()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			s = parcel1.readString();
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return s;
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
			parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
			mRemote.transact(4, parcel, parcel1, 0);
			parcel1.readException();
			hashmap = parcel1.readHashMap(getClass().getClassLoader());
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return hashmap;
		}

		public Intent getIntent()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			Intent intent;
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.Choice");
			mRemote.transact(2, parcel, parcel1, 0);
			parcel1.readException();
			
			if (parcel1.readInt() == 0) {
			    intent = null;
			} else 
			intent = (Intent)Intent.CREATOR.createFromParcel(parcel1);
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return intent;
		}

		public String getInterfaceDescriptor()
		{
			return "com.amazon.venezia.command.Choice";
		}

		Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract void choose(ChoiceContext choicecontext)
		throws RemoteException;

	public abstract String getDisplayableName()
		throws RemoteException;

	public abstract Map getExtensionData()
		throws RemoteException;

	public abstract Intent getIntent()
		throws RemoteException;
}
