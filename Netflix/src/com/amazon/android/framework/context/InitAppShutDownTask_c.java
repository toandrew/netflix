// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.context;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.framework.context:
//			d

final class InitAppShutDownTask_c
	implements Task
{

	private ContextManagerImpl_d a;

	InitAppShutDownTask_c(ContextManagerImpl_d d1)
	{
		a = d1;
	}

	public final void execute()
	{
	    ContextManagerImpl_d.a(a);
	}

	public final String toString()
	{
		return "ContextManager: init app shutdown on main thread";
	}
}
