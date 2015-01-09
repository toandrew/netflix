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
