// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android;

import com.amazon.android.l.b;
import com.amazon.android.r.SubmitCrashReportsTask_a;

// Referenced classes of package com.amazon.android:
//			i

public final class DrmFreeApplicationLaunchTaskWorkflow_h extends b
{

	public DrmFreeApplicationLaunchTaskWorkflow_h()
	{
		a(new CheckIfAppisBlockedTask_i());
		a(new SubmitCrashReportsTask_a());
		a(new com.amazon.android.CommandResultVerifier_e.DataStore_a());
	}

	protected final String b()
	{
		return "DrmFreeApplicationLaunchTaskWorkflow";
	}
}
