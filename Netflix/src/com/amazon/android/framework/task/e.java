// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task;

import com.amazon.android.framework.task.pipeline.f;
import java.util.Date;

// Referenced classes of package com.amazon.android.framework.task:
//			b, a, Task

final class e
	implements b
{

	private Date a;
	private TaskManagerImpl_a b;

	e(TaskManagerImpl_a a1, Date date)
	{
		b = a1;
		a = date;
	}

	public final void a(Task task, f f1)
	{
		f1.a(task, a);
	}
}
