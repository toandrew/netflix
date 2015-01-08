// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.i;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.i:
//			b

final class ExpireTask_a
	implements Task
{

	private b a;

	ExpireTask_a(b b1)
	{
		a = b1;
	}

	public final void execute()
	{
		a.expire();
	}

	public final String toString()
	{
		return (new StringBuilder()).append("Expire: ").append(a.toString()).toString();
	}
}
