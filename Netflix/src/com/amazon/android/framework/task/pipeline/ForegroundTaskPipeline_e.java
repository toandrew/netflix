// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.pipeline;

import com.amazon.android.framework.context.ContextManager;
import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.task.Task;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.o.EventManager_g;
import java.util.*;

// Referenced classes of package com.amazon.android.framework.task.pipeline:
//			f, a, c, b

public final class ForegroundTaskPipeline_e
	implements b, f
{

	private static final KiwiLogger a = new KiwiLogger("ForegroundTaskPipeline");
	private ContextManager b;
	private EventManager_g c;
	private f d;
	private f e;
	private List f;

	public ForegroundTaskPipeline_e(f f1)
	{
		f = new ArrayList();
		d = com.amazon.android.framework.task.pipeline.a.b("KIWI_UI");
		e = f1;
	}

	private void a(Task task, boolean flag)
	{
		if (b.isVisible())
			if (flag)
			{
				d.b(task);
				return;
			} else
			{
				d.a(task);
				return;
			}
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("No UI visible to execute task: ").append(task).append(", placing into pending queue until task ").append("is visible").toString());
		f.add(task);
	}

	static void a(e e1)
	{
		if (KiwiLogger.TRACE_ON)
			a.trace("Activity resumed, scheduling tasks on UI thread");
		for (Iterator iterator = e1.f.iterator(); iterator.hasNext(); e1.a((Task)iterator.next(), true));
		e1.f.clear();
	}

	static void a(e e1, Task task)
	{
		e1.a(task, true);
	}

	private Task c(Task task)
	{
		return new c(this, task);
	}

	public final void a()
	{
		d.a();
		e.a();
		f.clear();
	}

	public final void a(Task task)
	{
		a(task, false);
	}

	public final void a(Task task, long l)
	{
		e.a(c(task), l);
	}

	public final void a(Task task, Date date)
	{
		e.a(c(task), date);
	}

	public final void b(Task task)
	{
		a(task, true);
	}

	public final void onResourcesPopulated()
	{
		c.a(new com.amazon.android.framework.task.pipeline.b(this));
	}

}
