// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.prompt;

import com.amazon.android.a;
import com.amazon.android.o.c;
import com.amazon.android.o.d;
import com.amazon.android.o.f;

// Referenced classes of package com.amazon.android.framework.prompt:
//			PromptManagerImpl, Prompt

final class j
	implements c
{

	private PromptManagerImpl a;

	j(PromptManagerImpl promptmanagerimpl)
	{
		a = promptmanagerimpl;
	}

	public final f a()
	{
		return a.a;
	}

	public final volatile void a(d d)
	{
		if (PromptManagerImpl.b(a) != null)
			PromptManagerImpl.b(a).expire();
	}

	public final com.amazon.android.o.a b()
	{
		return com.amazon.android.o.a.b;
	}
}
