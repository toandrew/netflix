// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.content.Intent;
import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			n, a, y, k

public abstract class m extends Binder
	implements n
{

	public m()
	{
		attachInterface(this, "com.amazon.venezia.command.Choice");
	}

	public static n a(IBinder ibinder)
	{
		if (ibinder == null)
			return null;
		android.os.IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.Choice");
		if (iinterface != null && (iinterface instanceof n))
			return (n)iinterface;
		else
			return new a(ibinder);
	}

	public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
		throws RemoteException
	{
		switch (i)
		{
		default:
			return super.onTransact(i, parcel, parcel1, j);

		case 1598968902: 
			parcel1.writeString("com.amazon.venezia.command.Choice");
			return true;

		case 1: // '\001'
			parcel.enforceInterface("com.amazon.venezia.command.Choice");
			String s = a();
			parcel1.writeNoException();
			parcel1.writeString(s);
			return true;

		case 2: // '\002'
			parcel.enforceInterface("com.amazon.venezia.command.Choice");
			Intent intent = b();
			parcel1.writeNoException();
			if (intent != null)
			{
				parcel1.writeInt(1);
				intent.writeToParcel(parcel1, 1);
			} else
			{
				parcel1.writeInt(0);
			}
			return true;

		case 3: // '\003'
			parcel.enforceInterface("com.amazon.venezia.command.Choice");
			IBinder ibinder = parcel.readStrongBinder();
			Object obj;
			if (ibinder == null)
			{
				obj = null;
			} else
			{
				android.os.IInterface iinterface = ibinder.queryLocalInterface("com.amazon.venezia.command.ChoiceContext");
				if (iinterface != null && (iinterface instanceof y))
					obj = (y)iinterface;
				else
					obj = new k(ibinder);
			}
			a(((y) (obj)));
			parcel1.writeNoException();
			return true;

		case 4: // '\004'
			parcel.enforceInterface("com.amazon.venezia.command.Choice");
			java.util.Map map = c();
			parcel1.writeNoException();
			parcel1.writeMap(map);
			return true;
		}
	}
}
