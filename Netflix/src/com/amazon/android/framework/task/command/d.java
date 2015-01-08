// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;

import android.content.DialogInterface;
import com.amazon.android.framework.util.KiwiLogger;
import java.util.concurrent.BlockingQueue;

// Referenced classes of package com.amazon.android.framework.task.command:
//			c, l

final class d
	implements android.content.DialogInterface.OnClickListener
{

	private l a;
	private c b;

	d(c c1, l l)
	{
		b = c1;
		a = l;
	}

	public final void onClick(DialogInterface dialoginterface, int i)
	{
		if (KiwiLogger.TRACE_ON)
			c.b().trace("Choice selected!");
		if (c.a(b))
			c.b(b).add(a);
	}
}
