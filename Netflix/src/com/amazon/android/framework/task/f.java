// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task;


// Referenced classes of package com.amazon.android.framework.task:
//			b, a, Task

final class f
	implements b
{

	private long a;
	private TaskManagerImpl_a b;

	f(TaskManagerImpl_a a1, long l)
	{
		b = a1;
		a = l;
	}

	public final void a(Task task, com.amazon.android.framework.task.pipeline.f f1)
	{
		f1.a(task, a);
	}
}
