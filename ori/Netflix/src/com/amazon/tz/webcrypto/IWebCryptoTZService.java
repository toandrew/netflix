package com.amazon.tz.webcrypto;

import android.os.*;

public interface IWebCryptoTZService
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements IWebCryptoTZService
	{

		private static final String DESCRIPTOR = "com.amazon.tz.webcrypto.IWebCryptoTZService";
		static final int TRANSACTION_sendReq2TZ = 1;

		public static IWebCryptoTZService asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("com.amazon.tz.webcrypto.IWebCryptoTZService");
			if (iinterface != null && (iinterface instanceof IWebCryptoTZService))
				return (IWebCryptoTZService)iinterface;
			else
				return new com.amazon.tz.webcrypto.IWebCryptoTZService.Stub.Proxy(ibinder);
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
				parcel1.writeString("com.amazon.tz.webcrypto.IWebCryptoTZService");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("com.amazon.tz.webcrypto.IWebCryptoTZService");
				byte abyte0[] = sendReq2TZ(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createByteArray());
				parcel1.writeNoException();
				parcel1.writeByteArray(abyte0);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "com.amazon.tz.webcrypto.IWebCryptoTZService");
		}
	}

	static class Proxy
		implements com.amazon.tz.webcrypto.IWebCryptoTZService
	{

		private IBinder mRemote;
		Proxy(android.os.IBinder remote)
		{
		mRemote = remote;
		}

		public IBinder asBinder()
		{
			return mRemote;
		}

		public String getInterfaceDescriptor()
		{
			return "com.amazon.tz.webcrypto.IWebCryptoTZService";
		}

		public byte[] sendReq2TZ(int i, int j, int k, int l, int i1, int j1, int k1, 
				int l1, byte abyte0[])
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			byte abyte1[];
			try {
			parcel.writeInterfaceToken("com.amazon.tz.webcrypto.IWebCryptoTZService");
			parcel.writeInt(i);
			parcel.writeInt(j);
			parcel.writeInt(k);
			parcel.writeInt(l);
			parcel.writeInt(i1);
			parcel.writeInt(j1);
			parcel.writeInt(k1);
			parcel.writeInt(l1);
			parcel.writeByteArray(abyte0);
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			abyte1 = parcel1.createByteArray();
			}finally {
			parcel1.recycle();
			parcel.recycle();
			}
			return abyte1;
		}

	}


	public abstract byte[] sendReq2TZ(int i, int j, int k, int l, int i1, int j1, int k1, 
			int l1, byte abyte0[])
		throws RemoteException;
}
