// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.prompt;

import com.amazon.android.j.b;
import com.amazon.android.o.a;
import com.amazon.android.o.c;
import com.amazon.android.o.d;

// Referenced classes of package com.amazon.android.framework.prompt:
//			PromptManagerImpl

final class f
	implements c
{

	private PromptManagerImpl a;

	f(PromptManagerImpl promptmanagerimpl)
	{
		a = promptmanagerimpl;
	}

	public final com.amazon.android.o.f a()
	{
		return com.amazon.android.j.c.c;
	}

	public final volatile void a(d d)
	{
		b b1 = (b)d;
		com.amazon.android.framework.prompt.PromptManagerImpl.a(a, b1.a);
	}

	public final a b()
	{
		return a.b;
	}
}
