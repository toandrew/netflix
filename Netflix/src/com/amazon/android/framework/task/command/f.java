// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;

import android.os.RemoteException;
import com.amazon.venezia.command.n;
import com.amazon.venezia.command.r;

// Referenced classes of package com.amazon.android.framework.task.command:
//			l

final class f
{

	final String a;
	final String b;
	final long c;
	final l d;
	final l e;
	final l f;

	public f(r r1)
		throws RemoteException
	{
		a = r1.b();
		b = r1.c();
		c = r1.d();
		d = a(r1.e());
		e = a(r1.f());
		f = a(r1.g());
	}

	private static l a(n n)
		throws RemoteException
	{
		if (n == null)
			return null;
		else
			return new l(n);
	}
}
