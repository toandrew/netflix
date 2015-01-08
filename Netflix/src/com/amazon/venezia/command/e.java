// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			SuccessResult

public abstract class e extends Binder
	implements SuccessResult
{

	public e()
	{
		attachInterface(this, "com.amazon.venezia.command.SuccessResult");
	}

	public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
		throws RemoteException
	{
		switch (i)
		{
		default:
			return super.onTransact(i, parcel, parcel1, j);

		case 1598968902: 
			parcel1.writeString("com.amazon.venezia.command.SuccessResult");
			return true;

		case 1: // '\001'
			parcel.enforceInterface("com.amazon.venezia.command.SuccessResult");
			String s = getAuthToken();
			parcel1.writeNoException();
			parcel1.writeString(s);
			return true;

		case 2: // '\002'
			parcel.enforceInterface("com.amazon.venezia.command.SuccessResult");
			java.util.Map map = getData();
			parcel1.writeNoException();
			parcel1.writeMap(map);
			return true;
		}
	}
}
