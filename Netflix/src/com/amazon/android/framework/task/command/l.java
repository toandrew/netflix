// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;

import android.content.Intent;
import android.os.RemoteException;
import com.amazon.venezia.command.n;

final class l
{

	final n a;
	final String b;
	final Intent c;

	public l(n n1)
		throws RemoteException
	{
		a = n1;
		b = n1.a();
		c = n1.b();
	}
}
