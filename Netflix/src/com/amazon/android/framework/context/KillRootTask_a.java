package com.amazon.android.framework.context;

import android.app.Activity;
import com.amazon.android.framework.task.Task;
import com.amazon.android.framework.util.KiwiLogger;

// Referenced classes of package com.amazon.android.framework.context:
//			d

final class KillRootTask_a
	implements Task
{

	private ContextManagerImpl_d a;

	KillRootTask_a(ContextManagerImpl_d d1)
	{
		a = d1;
	}

	public final void execute()
	{
		Activity activity = a.getRoot();
		ContextManagerImpl_d.a.trace((new StringBuilder()).append("Finishing Root Task: ").append(activity).toString());
		if (activity != null)
		{
		    ContextManagerImpl_d.a.trace("Finishing Root");
			activity.finish();
		}
	}

	public final String toString()
	{
		return "ContextManager: kill root task";
	}
}
