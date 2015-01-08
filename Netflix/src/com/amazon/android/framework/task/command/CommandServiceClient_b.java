// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import com.amazon.android.b.CommandServiceBindFailureKiwiException_f;
import com.amazon.android.b.CommandServiceNotInstalledKiwiException_g;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.venezia.command.h;
import com.amazon.venezia.command.n;
import com.amazon.venezia.command.r;
import com.amazon.venezia.command.w;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Referenced classes of package com.amazon.android.framework.task.command:
//			i, j, k, h, 
//			a

public final class CommandServiceClient_b
{

	private static final KiwiLogger a = new KiwiLogger("CommandServiceClient");
	private h b;
	private final BlockingQueue c = new LinkedBlockingQueue();
	private final BlockingQueue d = new LinkedBlockingQueue();
	private Application e;
	private final ServiceConnection f = new i(this);
	private final com.amazon.venezia.command.f g = new j(this);

	public CommandServiceClient_b()
	{
	}

	static BlockingQueue a(CommandServiceClient_b b1)
	{
		return b1.d;
	}

	static KiwiLogger b()
	{
		return a;
	}

	static BlockingQueue b(CommandServiceClient_b b1)
	{
		return b1.c;
	}

	private k c()
		throws RemoteException
	{
		k k1;
		try
		{
			a.trace("Blocking for result from service");
			k1 = (k)c.take();
			a.trace("Received result from service");
		}
		catch (InterruptedException interruptedexception)
		{
			a.trace("TaskThread interrupted, returning null result");
			return null;
		}
		return k1;
	}

	public final k a(n n1)
		throws RemoteException
	{
		n1.a(null);
		return c();
	}

	public final k a(r r1, FailedReason_a a1)
		throws RemoteException
	{
		r1.a(new com.amazon.android.framework.task.command.h(this, a1));
		return c();
	}

	public final k a(w w)
		throws CommandServiceNotInstalledKiwiException_g, CommandServiceBindFailureKiwiException_f, RemoteException
	{
		boolean flag;
		if (b != null)
			flag = true;
		else
			flag = false;
		if (!flag)
		{
			long l = System.currentTimeMillis();
			a.trace("Binding Service!!!");
			Intent intent = new Intent();
			intent.setClassName("com.amazon.venezia", "com.amazon.venezia.service.command.CommandServiceImpl");
			intent.setAction("com.amazon.venezia.CommandService");
			boolean flag1;
			if (e.getPackageManager().resolveService(intent, 64) != null)
				flag1 = true;
			else
				flag1 = false;
			if (!flag1)
				throw new CommandServiceNotInstalledKiwiException_g();
			if (!e.bindService(intent, f, 1))
				throw new CommandServiceBindFailureKiwiException_f();
			try
			{
				a.trace("Blocking while service is being bound!!");
				b = (h)d.take();
				a.trace("service bound, returning!!");
				if (KiwiLogger.TRACE_ON)
				{
					long l1 = System.currentTimeMillis();
					a.trace((new StringBuilder()).append("Kiwi.BindService Time: ").append(l1 - l).toString());
				}
			}
			catch (InterruptedException interruptedexception)
			{
				Thread.currentThread().interrupt();
				throw new CommandServiceBindFailureKiwiException_f();
			}
		}
		b.a(w, g);
		return c();
	}

	public final void a()
	{
		KiwiLogger kiwilogger = a;
		StringBuilder stringbuilder = (new StringBuilder()).append("Finishing CommandServiceClient, unbinding service: ");
		boolean flag;
		if (b != null)
			flag = true;
		else
			flag = false;
		kiwilogger.trace(stringbuilder.append(flag).toString());
		if (b != null)
		{
			e.unbindService(f);
			b = null;
		}
	}

}
