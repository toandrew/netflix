// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			DecisionResult, ExceptionResult, FailureResult, SuccessResult

public interface ResultCallback
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements ResultCallback
	{

		private static final String DESCRIPTOR = "com.amazon.venezia.command.ResultCallback";
		static final int TRANSACTION_onDecide = 3;
		static final int TRANSACTION_onException = 4;
		static final int TRANSACTION_onFailure = 2;
		static final int TRANSACTION_onSuccess = 1;

		public static ResultCallback asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.ResultCallback");
			if (iinterface != null && (iinterface instanceof ResultCallback))
				return (ResultCallback)iinterface;
			else
				return new com.amazon.venezia.command.ResultCallback.Stub.Proxy(ibinder);
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
				parcel1.writeString("com.amazon.venezia.command.ResultCallback");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.amazon.venezia.command.ResultCallback");
				onSuccess(SuccessResult.Stub.asInterface(parcel.readStrongBinder()));
				parcel1.writeNoException();
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.amazon.venezia.command.ResultCallback");
				onFailure(FailureResult.Stub.asInterface(parcel.readStrongBinder()));
				parcel1.writeNoException();
				return true;

			case 3: // '\003'
				parcel.enforceInterface("com.amazon.venezia.command.ResultCallback");
				onDecide(DecisionResult.Stub.asInterface(parcel.readStrongBinder()));
				parcel1.writeNoException();
				return true;

			case 4: // '\004'
				parcel.enforceInterface("com.amazon.venezia.command.ResultCallback");
				onException(ExceptionResult.Stub.asInterface(parcel.readStrongBinder()));
				parcel1.writeNoException();
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.venezia.command.ResultCallback");
		}
	}

	static class Proxy
		implements ResultCallback
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public String getInterfaceDescriptor()
		{
			return "com.amazon.venezia.command.ResultCallback";
		}

		public void onDecide(DecisionResult decisionresult)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
			IBinder ibinder;
			if (decisionresult == null) {
			    ibinder = null;
			} else {
			    ibinder = decisionresult.asBinder();
			}
			
			parcel.writeStrongBinder(ibinder);
			mRemote.transact(3, parcel, parcel1, 0);
			parcel1.readException();
			}finally{
			parcel1.recycle();
			parcel.recycle();
			}
			return;
		}

		public void onException(ExceptionResult exceptionresult)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			try {
			    IBinder ibinder;
			parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
			if (exceptionresult == null) {
			    ibinder = null;
			} else {
			    ibinder = exceptionresult.asBinder();
			}
			parcel.writeStrongBinder(ibinder);
			mRemote.transact(4, parcel, parcel1, 0);
			parcel1.readException();
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return;
		}

		public void onFailure(FailureResult failureresult)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
			IBinder ibinder;
			if (failureresult == null) {
			    ibinder = null;
			} else {
			    ibinder = failureresult.asBinder();
			}
			parcel.writeStrongBinder(ibinder);
			mRemote.transact(2, parcel, parcel1, 0);
			parcel1.readException();
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return;
		}

		public void onSuccess(SuccessResult successresult)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			try {
			parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
			IBinder ibinder;
			if (successresult == null) {
			    ibinder = null;
			} else{
			ibinder = successresult.asBinder();
			}
			
			parcel.writeStrongBinder(ibinder);
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return;
		}

		Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract void onDecide(DecisionResult decisionresult)
		throws RemoteException;

	public abstract void onException(ExceptionResult exceptionresult)
		throws RemoteException;

	public abstract void onFailure(FailureResult failureresult)
		throws RemoteException;

	public abstract void onSuccess(SuccessResult successresult)
		throws RemoteException;
}
