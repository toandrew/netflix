// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android;

import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.j.b;
import com.amazon.android.o.c;
import com.amazon.android.o.d;
import com.amazon.android.t.a;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.amazon.android:
//			Kiwi

final class f
	implements c
{

	private Kiwi a;

	f(Kiwi kiwi)
	{
		a = kiwi;
	}

	public final com.amazon.android.o.f a()
	{
		return com.amazon.android.j.c.c;
	}

	public final volatile void a(d d)
	{
		DrmFullApplicationLaunchTaskWorkflow_b b1 = (DrmFullApplicationLaunchTaskWorkflow_b)d;
		HashMap hashmap = new HashMap();
		hashmap.put("EventName", com.amazon.android.j.c.c.name());
		hashmap.put("ActivityName", b1.a.getClass().getName());
		hashmap.put("Timestamp", Long.valueOf(System.currentTimeMillis()));
		a a1 = new a(hashmap);
		if (KiwiLogger.TRACE_ON)
			com.amazon.android.Kiwi.a().trace("Adding lifecycle RESUME command to pipeline");
		Kiwi.addCommandToCommandTaskPipeline(a1);
	}

	public final com.amazon.android.o.a b()
	{
		return com.amazon.android.o.a.b;
	}
}
