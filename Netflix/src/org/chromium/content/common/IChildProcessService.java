// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.common;

import android.os.*;

// Referenced classes of package org.chromium.content.common:
//			IChildProcessCallback

public interface IChildProcessService
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements IChildProcessService
	{

		private static final String DESCRIPTOR = "org.chromium.content.common.IChildProcessService";
		static final int TRANSACTION_setupConnection = 1;

		public static IChildProcessService asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("org.chromium.content.common.IChildProcessService");
			if (iinterface != null && (iinterface instanceof IChildProcessService))
				return (IChildProcessService)iinterface;
			else
				return new org.chromium.content.common.IChildProcessService.Stub.Proxy(ibinder);
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
				parcel1.writeString("org.chromium.content.common.IChildProcessService");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("org.chromium.content.common.IChildProcessService");
				break;
			}
			Bundle bundle;
			int k;
			if (parcel.readInt() != 0)
				bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
			else
				bundle = null;
			k = setupConnection(bundle, IChildProcessCallback.Stub.asInterface(parcel.readStrongBinder()));
			parcel1.writeNoException();
			parcel1.writeInt(k);
			return true;
		}

		public Stub()
		{
			attachInterface(this, "org.chromium.content.common.IChildProcessService");
		}
	}

	static class Proxy
		implements IChildProcessService
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public String getInterfaceDescriptor()
		{
			return "org.chromium.content.common.IChildProcessService";
		}

		public int setupConnection(Bundle bundle, IChildProcessCallback ichildprocesscallback)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("org.chromium.content.common.IChildProcessService");
			if (bundle == null) {
			    parcel.writeInt(0);
			} else {
			parcel.writeInt(1);
			bundle.writeToParcel(parcel, 0);
			}
	         int i;
			try {
			IBinder ibinder = null;
			if (ichildprocesscallback != null) 
			    ibinder = ichildprocesscallback.asBinder();


			parcel.writeStrongBinder(ibinder);
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
	         i = parcel1.readInt();
			}finally {

			parcel1.recycle();
			parcel.recycle();
			}
			return i;
		}

		Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract int setupConnection(Bundle bundle, IChildProcessCallback ichildprocesscallback)
		throws RemoteException;
}
