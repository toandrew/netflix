// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.licensing;

import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptContent;
import com.amazon.android.s.a;
import java.util.Map;

// Referenced classes of package com.amazon.android.licensing:
//			h, k, i

final class j
	implements h
{

	j()
	{
		this((byte)0);
	}

	private j(byte byte0)
	{
	}

	public final volatile PromptContent a(KiwiException kiwiexception)
	{
		com.amazon.android.k.a a1 = ((a)kiwiexception).a;
		k k1 = com.amazon.android.licensing.k.a;
		if (a1.a.containsKey(k1))
			return i.d;
		else
			return i.e;
	}
}
