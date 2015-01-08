// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.pipeline;

import android.os.SystemClock;
import com.amazon.android.framework.task.Task;
import com.amazon.android.framework.util.KiwiLogger;
import java.util.Date;
import java.util.Set;

// Referenced classes of package com.amazon.android.framework.task.pipeline:
//			a

final class d
	implements Runnable
{

	private Task a;
	private a b;

	d(a a1, Task task)
	{
		b = a1;
		a = task;
	}

	public final void run()
	{
		com.amazon.android.framework.task.pipeline.a.a(b).remove(this);
		if (KiwiLogger.TRACE_ON)
			com.amazon.android.framework.task.pipeline.a.b().trace((new StringBuilder()).append(com.amazon.android.framework.task.pipeline.a.b(b)).append(": Executing Task: ").append(a).append(", current time: ").append(new Date()).append(", uptime: ").append(SystemClock.uptimeMillis()).toString());
		a.execute();
		if (KiwiLogger.TRACE_ON)
			com.amazon.android.framework.task.pipeline.a.b().trace((new StringBuilder()).append(com.amazon.android.framework.task.pipeline.a.b(b)).append(": Task finished executing: ").append(a).toString());
_L2:
		return;
		Throwable throwable;
		throwable;
		if (KiwiLogger.ERROR_ON)
			com.amazon.android.framework.task.pipeline.a.b().error((new StringBuilder()).append("Task Failed with unhandled exception: ").append(throwable).toString(), throwable);
		if (!KiwiLogger.TRACE_ON) goto _L2; else goto _L1
_L1:
		com.amazon.android.framework.task.pipeline.a.b().trace((new StringBuilder()).append(com.amazon.android.framework.task.pipeline.a.b(b)).append(": Task finished executing: ").append(a).toString());
		return;
		Exception exception;
		exception;
		if (KiwiLogger.TRACE_ON)
			com.amazon.android.framework.task.pipeline.a.b().trace((new StringBuilder()).append(com.amazon.android.framework.task.pipeline.a.b(b)).append(": Task finished executing: ").append(a).toString());
		throw exception;
	}

	public final String toString()
	{
		return a.toString();
	}
}
