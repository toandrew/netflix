// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			DecisionExpirationContext, Choice

public interface DecisionResult
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements DecisionResult
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.command.DecisionResult";
		static final int TRANSACTION_expire = 5;
		static final int TRANSACTION_getAuthToken = 1;
		static final int TRANSACTION_getDecisionDurationInSeconds = 4;
		static final int TRANSACTION_getDescription = 3;
		static final int TRANSACTION_getDisplayableName = 2;
		static final int TRANSACTION_getExtensionData = 9;
		static final int TRANSACTION_getNegativeChoice = 8;
		static final int TRANSACTION_getNeutralChoice = 7;
		static final int TRANSACTION_getPositiveChoice = 6;

		public static DecisionResult asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.DecisionResult");
			if (iinterface != null && (iinterface instanceof DecisionResult))
				return (DecisionResult)iinterface;
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
				parcel1.writeString("com.amazon.venezia.command.DecisionResult");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
				String s2 = getAuthToken();
				parcel1.writeNoException();
				parcel1.writeString(s2);
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
				String s1 = getDisplayableName();
				parcel1.writeNoException();
				parcel1.writeString(s1);
				return true;

			case 3: // '\003'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
				String s = getDescription();
				parcel1.writeNoException();
				parcel1.writeString(s);
				return true;

			case 4: // '\004'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
				long l = getDecisionDurationInSeconds();
				parcel1.writeNoException();
				parcel1.writeLong(l);
				return true;

			case 5: // '\005'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
				expire(DecisionExpirationContext.Stub.asInterface(parcel.readStrongBinder()));
				parcel1.writeNoException();
				return true;

			case 6: // '\006'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
				Choice choice2 = getPositiveChoice();
				parcel1.writeNoException();
				IBinder ibinder2;
				if (choice2 != null)
					ibinder2 = choice2.asBinder();
				else
					ibinder2 = null;
				parcel1.writeStrongBinder(ibinder2);
				return true;

			case 7: // '\007'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
				Choice choice1 = getNeutralChoice();
				parcel1.writeNoException();
				IBinder ibinder1;
				if (choice1 != null)
					ibinder1 = choice1.asBinder();
				else
					ibinder1 = null;
				parcel1.writeStrongBinder(ibinder1);
				return true;

			case 8: // '\b'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
				Choice choice = getNegativeChoice();
				parcel1.writeNoException();
				IBinder ibinder;
				if (choice != null)
					ibinder = choice.asBinder();
				else
					ibinder = null;
				parcel1.writeStrongBinder(ibinder);
				return true;

			case 9: // '\t'
				parcel.enforceInterface("com.amazon.venezia.command.DecisionResult");
				Map map = getExtensionData();
				parcel1.writeNoException();
				parcel1.writeMap(map);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.command.DecisionResult");
		}
	}

	private static class Stub.Proxy
		implements DecisionResult
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public void expire(DecisionExpirationContext decisionexpirationcontext)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
			if (decisionexpirationcontext == null)
				break MISSING_BLOCK_LABEL_59;
			IBinder ibinder = decisionexpirationcontext.asBinder();
_L1:
			parcel.writeStrongBinder(ibinder);
			mRemote.transact(5, parcel, parcel1, 0);
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

		public String getAuthToken()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
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

		public long getDecisionDurationInSeconds()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			long l;
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
			mRemote.transact(4, parcel, parcel1, 0);
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

		public String getDescription()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s;
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
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
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
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
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
			mRemote.transact(9, parcel, parcel1, 0);
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
			return "com.amazon.venezia.command.DecisionResult";
		}

		public Choice getNegativeChoice()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			Choice choice;
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
			mRemote.transact(8, parcel, parcel1, 0);
			parcel1.readException();
			choice = Choice.Stub.asInterface(parcel1.readStrongBinder());
			parcel1.recycle();
			parcel.recycle();
			return choice;
			Exception exception;
			exception;
			parcel1.recycle();
			parcel.recycle();
			throw exception;
		}

		public Choice getNeutralChoice()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			Choice choice;
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
			mRemote.transact(7, parcel, parcel1, 0);
			parcel1.readException();
			choice = Choice.Stub.asInterface(parcel1.readStrongBinder());
			parcel1.recycle();
			parcel.recycle();
			return choice;
			Exception exception;
			exception;
			parcel1.recycle();
			parcel.recycle();
			throw exception;
		}

		public Choice getPositiveChoice()
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			Choice choice;
			parcel.writeInterfaceToken("com.amazon.venezia.command.DecisionResult");
			mRemote.transact(6, parcel, parcel1, 0);
			parcel1.readException();
			choice = Choice.Stub.asInterface(parcel1.readStrongBinder());
			parcel1.recycle();
			parcel.recycle();
			return choice;
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


	public abstract void expire(DecisionExpirationContext decisionexpirationcontext)
		throws RemoteException;

	public abstract String getAuthToken()
		throws RemoteException;

	public abstract long getDecisionDurationInSeconds()
		throws RemoteException;

	public abstract String getDescription()
		throws RemoteException;

	public abstract String getDisplayableName()
		throws RemoteException;

	public abstract Map getExtensionData()
		throws RemoteException;

	public abstract Choice getNegativeChoice()
		throws RemoteException;

	public abstract Choice getNeutralChoice()
		throws RemoteException;

	public abstract Choice getPositiveChoice()
		throws RemoteException;
}
