// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;

import android.os.RemoteException;
import com.amazon.venezia.command.p;
import java.util.Map;

// Referenced classes of package com.amazon.android.framework.task.command:
//			a, b

final class h extends p
{

	private a a;
	private CommandServiceClient_b b;

	h(CommandServiceClient_b b1, a a1)
	{
		b = b1;
		a = a1;
	}

	public final String a()
		throws RemoteException
	{
		return a.name();
	}

	public final Map b()
		throws RemoteException
	{
		return null;
	}
}
