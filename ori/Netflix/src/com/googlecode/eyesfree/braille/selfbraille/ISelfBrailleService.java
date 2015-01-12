// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.googlecode.eyesfree.braille.selfbraille;

import android.os.*;

// Referenced classes of package com.googlecode.eyesfree.braille.selfbraille:
//			WriteData

public interface ISelfBrailleService
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements ISelfBrailleService
	{

		private static final String DESCRIPTOR = "com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService";
		static final int TRANSACTION_disconnect = 2;
		static final int TRANSACTION_write = 1;

		public static ISelfBrailleService asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService");
			if (iinterface != null && (iinterface instanceof ISelfBrailleService))
				return (ISelfBrailleService)iinterface;
			else
				return new com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService.Stub.Proxy(ibinder);
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
				parcel1.writeString("com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService");
				IBinder ibinder = parcel.readStrongBinder();
				WriteData writedata;
				if (parcel.readInt() != 0)
					writedata = (WriteData)WriteData.CREATOR.createFromParcel(parcel);
				else
					writedata = null;
				write(ibinder, writedata);
				parcel1.writeNoException();
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService");
				disconnect(parcel.readStrongBinder());
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService");
		}
	}

	static class Proxy
		implements ISelfBrailleService
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public void disconnect(IBinder ibinder)
			throws RemoteException
		{
			Parcel parcel = Parcel.obtain();
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService");
			parcel.writeStrongBinder(ibinder);
			try{
			mRemote.transact(2, parcel, null, 1);
			}finally {
			parcel.recycle();
			}
			return;
		}

		public String getInterfaceDescriptor()
		{
			return "com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService";
		}

		public void write(IBinder ibinder, WriteData writedata)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.selfbraille.ISelfBrailleService");
			parcel.writeStrongBinder(ibinder);
			try {
			if (writedata == null) {
			    parcel.writeInt(0);
			} else {
			    parcel.writeInt(1);
			}
			writedata.writeToParcel(parcel, 0);

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


	public abstract void disconnect(IBinder ibinder)
		throws RemoteException;

	public abstract void write(IBinder ibinder, WriteData writedata)
		throws RemoteException;
}
