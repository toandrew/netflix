// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.*;

// Referenced classes of package com.amazon.venezia.command:
//			y

public abstract class g extends Binder
	implements y
{

	public g()
	{
		attachInterface(this, "com.amazon.venezia.command.ChoiceContext");
	}

	public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
		throws RemoteException
	{
		switch (i)
		{
		default:
			return super.onTransact(i, parcel, parcel1, j);

		case 1598968902: 
			parcel1.writeString("com.amazon.venezia.command.ChoiceContext");
			return true;

		case 1: // '\001'
			parcel.enforceInterface("com.amazon.venezia.command.ChoiceContext");
			java.util.Map map = a();
			parcel1.writeNoException();
			parcel1.writeMap(map);
			return true;
		}
	}
}
