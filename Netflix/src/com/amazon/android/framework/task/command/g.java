// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;

import android.app.Application;
import android.os.RemoteException;
import com.amazon.venezia.command.z;
import java.util.Map;

// Referenced classes of package com.amazon.android.framework.task.command:
//			AbstractCommandTask

final class g extends z
{

	private AbstractCommandTask a;

	g(AbstractCommandTask abstractcommandtask)
	{
		a = abstractcommandtask;
	}

	public final String a()
		throws RemoteException
	{
		return a.getCommandName();
	}

	public final String b()
		throws RemoteException
	{
		return a.getCommandVersion();
	}

	public final String c()
		throws RemoteException
	{
		return AbstractCommandTask.a(a).getPackageName();
	}

	public final Map d()
		throws RemoteException
	{
		return a.getCommandData();
	}
}
