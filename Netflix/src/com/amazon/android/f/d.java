// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.f;

import com.amazon.android.framework.context.ContextManager;
import com.amazon.android.framework.task.Task;
import com.amazon.android.framework.util.KiwiLogger;

// Referenced classes of package com.amazon.android.f:
//			c, a

final class d
	implements Task
{

	private a a;
	private ActivityResultManagerImpl_c b;

	d(ActivityResultManagerImpl_c c1, a a1)
	{
		b = c1;
		a = a1;
	}

	public final void execute()
	{
		android.app.Activity activity = ActivityResultManagerImpl_c.b(b).getVisible();
		if (activity == null)
		{
		    ActivityResultManagerImpl_c.a().trace("No activity to call startActivityForResult on. startActivityForResult when an activity becomes visible");
			return;
		} else
		{
			a.a(activity);
			return;
		}
	}
}
