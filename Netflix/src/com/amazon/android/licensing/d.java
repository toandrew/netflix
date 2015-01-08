// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.licensing;

import com.amazon.android.framework.task.TaskManager;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.android.n.b;
import java.util.Date;

// Referenced classes of package com.amazon.android.licensing:
//			l

final class d extends b
{

	private VerifyApplicationEntitlmentTask_l b;

	d(VerifyApplicationEntitlmentTask_l l1, Object obj, Date date)
	{
		b = l1;
	}

	public final void doExpiration()
	{
	    VerifyApplicationEntitlmentTask_l l1 = new VerifyApplicationEntitlmentTask_l();
		taskManager.enqueue(TaskPipelineId.COMMAND, l1);
	}
}
