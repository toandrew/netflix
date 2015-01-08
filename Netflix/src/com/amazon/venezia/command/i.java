// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			j

public abstract class i extends Binder
	implements j
{

	public i()
	{
		attachInterface(this, "com.amazon.venezia.command.ExceptionResult");
	}

	public boolean onTransact(int k, Parcel parcel, Parcel parcel1, int l)
		throws RemoteException
	{
		switch (k)
		{
		default:
			return super.onTransact(k, parcel, parcel1, l);

		case 1598968902: 
			parcel1.writeString("com.amazon.venezia.command.ExceptionResult");
			return true;

		case 1: // '\001'
			parcel.enforceInterface("com.amazon.venezia.command.ExceptionResult");
			String s = a();
			parcel1.writeNoException();
			parcel1.writeString(s);
			return true;

		case 2: // '\002'
			parcel.enforceInterface("com.amazon.venezia.command.ExceptionResult");
			java.util.Map map = b();
			parcel1.writeNoException();
			parcel1.writeMap(map);
			return true;
		}
	}
}
