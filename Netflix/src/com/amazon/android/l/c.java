// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.l;

import com.amazon.android.d.a;

// Referenced classes of package com.amazon.android.l:
//			a, b

public abstract class c
	implements com.amazon.android.l.a
{

	private b workflow;

	public c()
	{
	}

	protected final boolean isWorkflowChild()
	{
		return workflow != null;
	}

	protected final void quitParentWorkflow()
	{
		a.a(isWorkflowChild(), "task is no a workflow child");
		workflow.c();
	}

	public final void setWorkflow(b b1)
	{
		a.a(b1, "workflow");
		boolean flag;
		if (workflow == null)
			flag = true;
		else
			flag = false;
		a.a(flag, "workflow instance can only be set once");
		workflow = b1;
	}
}
