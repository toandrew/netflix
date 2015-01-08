// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.venezia.command.q;
import java.util.concurrent.BlockingQueue;

// Referenced classes of package com.amazon.android.framework.task.command:
//			b

final class i
	implements ServiceConnection
{

	private CommandServiceClient_b a;

	i(CommandServiceClient_b b1)
	{
		a = b1;
	}

	public final void onServiceConnected(ComponentName componentname, IBinder ibinder)
	{
		CommandServiceClient_b.b().trace("onServiceConnected");
		CommandServiceClient_b.a(a).add(q.a(ibinder));
	}

	public final void onServiceDisconnected(ComponentName componentname)
	{
		CommandServiceClient_b.b().trace("onServiceDisconnected!!!");
	}
}
