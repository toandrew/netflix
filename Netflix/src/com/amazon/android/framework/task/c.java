// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task;

import com.amazon.android.framework.task.pipeline.f;

// Referenced classes of package com.amazon.android.framework.task:
//			b, a, Task

final class c
	implements b
{

	private TaskManagerImpl_a a;

	c(TaskManagerImpl_a a1)
	{
		a = a1;
	}

	public final void a(Task task, f f1)
	{
		f1.a(task);
	}
}
