// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.pipeline;

import com.amazon.android.o.Order_a;
import com.amazon.android.o.c;
import com.amazon.android.o.d;
import com.amazon.android.o.f;

// Referenced classes of package com.amazon.android.framework.task.pipeline:
//			e

final class b
	implements c
{

	private e a;

	b(e e1)
	{
		a = e1;
	}

	public final f a()
	{
		return com.amazon.android.j.c.c;
	}

	public final void a(d d)
	{
		com.amazon.android.framework.task.pipeline.e.a(a);
	}

	public final a b()
	{
		return a.b;
	}

	public final String toString()
	{
		return "ForegroundTaskPipeline:onResume listener";
	}
}
