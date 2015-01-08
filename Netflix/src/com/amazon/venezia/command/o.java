// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			f, SuccessResult, x, FailureResult, 
//			ab, r, aa, j, 
//			v

public abstract class o extends Binder
	implements f
{

	public o()
	{
		attachInterface(this, "com.amazon.venezia.command.ResultCallback");
	}

	public IBinder asBinder()
	{
		return this;
	}

	public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int k)
		throws RemoteException
	{
		IBinder ibinder;
		switch (i)
		{
		default:
			return super.onTransact(i, parcel, parcel1, k);

		case 1598968902: 
			parcel1.writeString("com.amazon.venezia.command.ResultCallback");
			return true;

		case 1: // '\001'
			parcel.enforceInterface("com.amazon.venezia.command.ResultCallback");
			IBinder ibinder3 = parcel.readStrongBinder();
			Object obj3;
			if (ibinder3 == null)
			{
				obj3 = null;
			} else
			{
				android.os.IInterface iinterface3 = ibinder3.queryLocalInterface("com.amazon.venezia.command.SuccessResult");
				if (iinterface3 != null && (iinterface3 instanceof SuccessResult))
					obj3 = (SuccessResult)iinterface3;
				else
					obj3 = new x(ibinder3);
			}
			a(((SuccessResult) (obj3)));
			parcel1.writeNoException();
			return true;

		case 2: // '\002'
			parcel.enforceInterface("com.amazon.venezia.command.ResultCallback");
			IBinder ibinder2 = parcel.readStrongBinder();
			Object obj2;
			if (ibinder2 == null)
			{
				obj2 = null;
			} else
			{
				android.os.IInterface iinterface2 = ibinder2.queryLocalInterface("com.amazon.venezia.command.FailureResult");
				if (iinterface2 != null && (iinterface2 instanceof FailureResult))
					obj2 = (FailureResult)iinterface2;
				else
					obj2 = new ab(ibinder2);
			}
			a(((FailureResult) (obj2)));
			parcel1.writeNoException();
			return true;

		case 3: // '\003'
			parcel.enforceInterface("com.amazon.venezia.command.ResultCallback");
			IBinder ibinder1 = parcel.readStrongBinder();
			Object obj1;
			if (ibinder1 == null)
			{
				obj1 = null;
			} else
			{
				android.os.IInterface iinterface1 = ibinder1.queryLocalInterface("com.amazon.venezia.command.DecisionResult");
				if (iinterface1 != null && (iinterface1 instanceof r))
					obj1 = (r)iinterface1;
				else
					obj1 = new aa(ibinder1);
			}
			a(((r) (obj1)));
			parcel1.writeNoException();
			return true;

		case 4: // '\004'
			parcel.enforceInterface("com.amazon.venezia.command.ResultCallback");
			ibinder = parcel.readStrongBinder();
			break;
		}
		Object obj;
		if (ibinder == null)
		{
			obj = null;
		} else
		{
			android.os.IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.ExceptionResult");
			if (iinterface != null && (iinterface instanceof j))
				obj = (j)iinterface;
			else
				obj = new v(ibinder);
		}
		a(((j) (obj)));
		parcel1.writeNoException();
		return true;
	}
}
