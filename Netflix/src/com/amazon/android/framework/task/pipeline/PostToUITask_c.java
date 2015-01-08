// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.pipeline;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.framework.task.pipeline:
//			e

final class PostToUITask_c
	implements Task
{

	private Task a;
	private e b;

	PostToUITask_c(e e1, Task task)
	{
		b = e1;
		a = task;
		super();
	}

	public final void execute()
	{
		e.a(b, a);
	}

	public final String toString()
	{
		return (new StringBuilder()).append("Future:PostToUITask: ").append(a.toString()).toString();
	}
}
