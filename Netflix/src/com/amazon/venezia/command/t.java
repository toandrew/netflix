// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			f, FailureResult, SuccessResult, j, 
//			r

final class t
	implements f
{

	private IBinder a;

	t(IBinder ibinder)
	{
		a = ibinder;
	}

	public final void a(FailureResult failureresult)
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
		if (failureresult == null)
			break MISSING_BLOCK_LABEL_59;
		IBinder ibinder = failureresult.asBinder();
_L1:
		parcel.writeStrongBinder(ibinder);
		a.transact(2, parcel, parcel1, 0);
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

	public final void a(SuccessResult successresult)
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
		if (successresult == null)
			break MISSING_BLOCK_LABEL_59;
		IBinder ibinder = successresult.asBinder();
_L1:
		parcel.writeStrongBinder(ibinder);
		a.transact(1, parcel, parcel1, 0);
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

	public final void a(j j1)
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
		if (j1 == null)
			break MISSING_BLOCK_LABEL_59;
		IBinder ibinder = j1.asBinder();
_L1:
		parcel.writeStrongBinder(ibinder);
		a.transact(4, parcel, parcel1, 0);
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

	public final void a(r r1)
		throws RemoteException
	{
		Parcel parcel;
		Parcel parcel1;
		parcel = Parcel.obtain();
		parcel1 = Parcel.obtain();
		parcel.writeInterfaceToken("com.amazon.venezia.command.ResultCallback");
		if (r1 == null)
			break MISSING_BLOCK_LABEL_59;
		IBinder ibinder = r1.asBinder();
_L1:
		parcel.writeStrongBinder(ibinder);
		a.transact(3, parcel, parcel1, 0);
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

	public final IBinder asBinder()
	{
		return a;
	}
}
