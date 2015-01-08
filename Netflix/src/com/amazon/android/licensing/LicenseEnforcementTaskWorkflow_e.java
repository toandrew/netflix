// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.licensing;

import com.amazon.android.e.SubmitMetricsTask_a;
import com.amazon.android.l.TaskWorkflow_b;

// Referenced classes of package com.amazon.android.licensing:
//			l, b

public final class LicenseEnforcementTaskWorkflow_e extends TaskWorkflow_b
{

	private com.amazon.android.framework.task.command.CommandServiceClient_b a;

	public LicenseEnforcementTaskWorkflow_e()
	{
		a(new VerifyApplicationEntitlmentTask_l());
		a(new SubmitMetricsTask_a());
		a(new com.amazon.android.licensing.LicenseKillTask_b());
	}

	protected final void a()
	{
		if (!isWorkflowChild())
			a.a();
	}

	protected final String b()
	{
		return "LicenseEnforcementTaskWorkflow";
	}
}
