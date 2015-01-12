// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.common;

import android.os.*;
import android.view.Surface;

public interface IChildProcessCallback
	extends IInterface
{
	public static abstract class Stub extends Binder
		implements IChildProcessCallback
	{

		private static final String DESCRIPTOR = "org.chromium.content.common.IChildProcessCallback";
		static final int TRANSACTION_establishSurfacePeer = 1;
		static final int TRANSACTION_getViewSurface = 2;

		public static IChildProcessCallback asInterface(IBinder ibinder)
		{
			if (ibinder == null)
				return null;
			IInterface iinterface = ibinder.queryLocalInterface("org.chromium.content.common.IChildProcessCallback");
			if (iinterface != null && (iinterface instanceof IChildProcessCallback))
				return (IChildProcessCallback)iinterface;
			else
				return new org.chromium.content.common.IChildProcessCallback.Stub.Proxy(ibinder);
		}

		public IBinder asBinder()
		{
			return this;
		}

		public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
			throws RemoteException
		{
			Surface surface;
			switch (i)
			{
			default:
				return super.onTransact(i, parcel, parcel1, j);

			case 1598968902: 
				parcel1.writeString("org.chromium.content.common.IChildProcessCallback");
				return true;

			case 1: // '\001'
				parcel.enforceInterface("org.chromium.content.common.IChildProcessCallback");
				int k = parcel.readInt();
				Surface surface1;
				if (parcel.readInt() != 0)
					surface1 = (Surface)Surface.CREATOR.createFromParcel(parcel);
				else
					surface1 = null;
				establishSurfacePeer(k, surface1, parcel.readInt(), parcel.readInt());
				parcel1.writeNoException();
				return true;

			case 2: // '\002'
				parcel.enforceInterface("org.chromium.content.common.IChildProcessCallback");
				surface = getViewSurface(parcel.readInt());
				parcel1.writeNoException();
				break;
			}
			if (surface != null)
			{
				parcel1.writeInt(1);
				surface.writeToParcel(parcel1, 1);
				return true;
			} else
			{
				parcel1.writeInt(0);
				return true;
			}
		}

		public Stub()
		{
			attachInterface(this, "org.chromium.content.common.IChildProcessCallback");
		}
	}

	static class Proxy
		implements IChildProcessCallback
	{

		private IBinder mRemote;

		public IBinder asBinder()
		{
			return mRemote;
		}

		public void establishSurfacePeer(int i, Surface surface, int j, int k)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("org.chromium.content.common.IChildProcessCallback");
			parcel.writeInt(i);
			if (surface == null) {
			    parcel.writeInt(0);
			} else {
			parcel.writeInt(1);
			surface.writeToParcel(parcel, 0);
			}

			parcel.writeInt(j);
			parcel.writeInt(k);
			mRemote.transact(1, parcel, parcel1, 0);
			parcel1.readException();
			parcel1.recycle();
			parcel.recycle();
			return;
		}

		public String getInterfaceDescriptor()
		{
			return "org.chromium.content.common.IChildProcessCallback";
		}

		public Surface getViewSurface(int i)
			throws RemoteException
		{
			Parcel parcel;
			Parcel parcel1;
			parcel = Parcel.obtain();
			parcel1 = Parcel.obtain();
			parcel.writeInterfaceToken("org.chromium.content.common.IChildProcessCallback");
			parcel.writeInt(i);
			mRemote.transact(2, parcel, parcel1, 0);
			parcel1.readException();
			Surface surface;
			if (parcel1.readInt() == 0) {
			    surface = null;
			} else {

			surface = (Surface)Surface.CREATOR.createFromParcel(parcel1);
			}

			parcel1.recycle();
			parcel.recycle();
			return surface;
		}

		Proxy(IBinder ibinder)
		{
			mRemote = ibinder;
		}
	}


	public abstract void establishSurfacePeer(int i, Surface surface, int j, int k)
		throws RemoteException;

	public abstract Surface getViewSurface(int i)
		throws RemoteException;
}
