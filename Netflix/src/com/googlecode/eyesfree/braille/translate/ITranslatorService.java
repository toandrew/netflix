// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.googlecode.eyesfree.braille.translate;

import android.os.*;

// Referenced classes of package com.googlecode.eyesfree.braille.translate:
//			ITranslatorServiceCallback

public interface ITranslatorService
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements ITranslatorService
	{

		private static final String DESCRIPTOR = "com.googlecode.eyesfree.braille.translate.ITranslatorService";
		static final int TRANSACTION_backTranslate = 4;
		static final int TRANSACTION_checkTable = 2;
		static final int TRANSACTION_setCallback = 1;
		static final int TRANSACTION_translate = 3;

		public static ITranslatorService asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.googlecode.eyesfree.braille.translate.ITranslatorService");
			if (iinterface != null && (iinterface instanceof ITranslatorService))
				return (ITranslatorService)iinterface;
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
				parcel1.writeString("com.googlecode.eyesfree.braille.translate.ITranslatorService");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.translate.ITranslatorService");
				setCallback(ITranslatorServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
				parcel1.writeNoException();
				return true;

			case 2: // '\002'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.translate.ITranslatorService");
				boolean flag = checkTable(parcel.readString());
				parcel1.writeNoException();
				int k;
				if (flag)
					k = 1;
				else
					k = 0;
				parcel1.writeInt(k);
				return true;

			case 3: // '\003'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.translate.ITranslatorService");
				byte abyte0[] = translate(parcel.readString(), parcel.readString());
				parcel1.writeNoException();
				parcel1.writeByteArray(abyte0);
				return true;

			case 4: // '\004'
				parcel.enforceInterface("com.googlecode.eyesfree.braille.translate.ITranslatorService");
				String s = backTranslate(parcel.createByteArray(), parcel.readString());
				parcel1.writeNoException();
				parcel1.writeString(s);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.googlecode.eyesfree.braille.translate.ITranslatorService");
		}
	}

	private static class Stub.Proxy
		implements ITranslatorService
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public String backTranslate(byte abyte0[], String s)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			String s1;
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.translate.ITranslatorService");
			parcel.writeByteArray(abyte0);
			parcel.writeString(s);
			mRemote.transact(4, parcel, parcel1, 0);
			parcel1.readException();
			s1 = parcel1.readString();
			parcel1.recycle();
			parcel.recycle();
			return s1;
			Exception exception;
			exception;
			parcel1.recycle();
			parcel.recycle();
			throw exception;
		}

		public boolean checkTable(String s)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			int i;
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.translate.ITranslatorService");
			parcel.writeString(s);
			mRemote.transact(2, parcel, parcel1, 0);
			parcel1.readException();
			i = parcel1.readInt();
			boolean flag = false;
			if (i != 0)
				flag = true;
			parcel1.recycle();
			parcel.recycle();
			return flag;
			Exception exception;
			exception;
			parcel1.recycle();
			parcel.recycle();
			throw exception;
		}

		public String getInterfaceDescriptor()
		{
			return "com.googlecode.eyesfree.braille.translate.ITranslatorService";
		}

		public void setCallback(ITranslatorServiceCallback itranslatorservicecallback)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.translate.ITranslatorService");
			if (itranslatorservicecallback == null)
				break MISSING_BLOCK_LABEL_59;
			IBinder ibinder = itranslatorservicecallback.asBinder();
_L1:
			parcel.writeStrongBinder(ibinder);
			mRemote.transact(1, parcel, parcel1, 0);
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

		public byte[] translate(String s, String s1)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			byte abyte0[];
			parcel.writeInterfaceToken("com.googlecode.eyesfree.braille.translate.ITranslatorService");
			parcel.writeString(s);
			parcel.writeString(s1);
			mRemote.transact(3, parcel, parcel1, 0);
			parcel1.readException();
			abyte0 = parcel1.createByteArray();
			parcel1.recycle();
			parcel.recycle();
			return abyte0;
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


	public abstract String backTranslate(byte abyte0[], String s)
		throws RemoteException;

	public abstract boolean checkTable(String s)
		throws RemoteException;

	public abstract void setCallback(ITranslatorServiceCallback itranslatorservicecallback)
		throws RemoteException;

	public abstract byte[] translate(String s, String s1)
		throws RemoteException;
}
