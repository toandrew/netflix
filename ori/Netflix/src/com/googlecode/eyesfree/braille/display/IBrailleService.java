// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.googlecode.eyesfree.braille.display;

import android.os.*;

// Referenced classes of package com.googlecode.eyesfree.braille.display:
//			IBrailleServiceCallback

public interface IBrailleService
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements IBrailleService
	{

		private static final String DESCRIPTOR = "com.googlecode.eyesfree.braille.display.IBrailleService";
		static final int TRANSACTION_displayDots = 3;
		static final int TRANSACTION_registerCallback = 1;
		static final int TRANSACTION_unregisterCallback = 2;

		public static IBrailleService asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.googlecode.eyesfree.braille.display.IBrailleService");
			if (iinterface != null && (iinterface instanceof IBrailleService))
				return (IBrailleService)iinterface;
			else
				return new com.googlecode.eyesfree.braille.display.IBrailleService.Stub.Proxy(ibinder);
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
				parcel1.writeString("com.googlecode.eyesfree.braille.display.IBrailleService");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.display.IBrailleService");
				boolean flag = registerCallback(IBrailleServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
				parcel1.writeNoException();
				int k;
				if (flag)
					k = 1;
				else
					k = 0;
				parcel1.writeInt(k);
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.display.IBrailleService");
				unregisterCallback(IBrailleServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
				return true;

			case 3: // '\003'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.display.IBrailleService");
				displayDots(parcel.createByteArray());
				parcel1.writeNoException();
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.googlecode.eyesfree.braille.display.IBrailleService");
		}
	}

	static class Proxy
		implements IBrailleService
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public void displayDots(byte abyte0[])
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			try {
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.display.IBrailleService");
			parcel.writeByteArray(abyte0);
			mRemote.transact(3, parcel, parcel1, 0);
			parcel1.readException();
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return;
		}

		public String getInterfaceDescriptor()
		{
			return "com.googlecode.eyesfree.braille.display.IBrailleService";
		}

		public boolean registerCallback(IBrailleServiceCallback ibrailleservicecallback)
			throws RemoteException
		{
			boolean flag;
			Parcel parcel;
			Parcel parcel1;
			flag = true;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			try {
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.display.IBrailleService");
			IBinder ibinder;
			if (ibrailleservicecallback == null) {
			    ibinder = null;
			} else {
			ibinder = ibrailleservicecallback.asBinder();
		}

			int i;
			parcel.writeStrongBinder(ibinder);
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			i = parcel1.readInt();
			if (i == 0)
				flag = false;
		}finally {
			parcel1.recycle();
			parcel.recycle();
		}
		    return flag;
		    
		}

		public void unregisterCallback(IBrailleServiceCallback ibrailleservicecallback)
			throws RemoteException
		{
			Parcel parcel = Parcel.obtain();
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.display.IBrailleService");
			IBinder ibinder;
			ibinder = null;
			try{
			if (ibrailleservicecallback != null) {

			ibinder = ibrailleservicecallback.asBinder();
			}
			parcel.writeStrongBinder(ibinder);
			mRemote.transact(2, parcel, null, 1);
			}finally {
			parcel.recycle();
			}
			return;
		}

		Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract void displayDots(byte abyte0[])
		throws RemoteException;

	public abstract boolean registerCallback(IBrailleServiceCallback ibrailleservicecallback)
		throws RemoteException;

	public abstract void unregisterCallback(IBrailleServiceCallback ibrailleservicecallback)
		throws RemoteException;
}
