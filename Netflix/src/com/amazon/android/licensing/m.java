// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.licensing;

import com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b.e;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptContent;
import java.util.Map;

// Referenced classes of package com.amazon.android.licensing:
//			h, a, i

final class m
	implements h
{

	private Map a;

	m()
	{
		this((byte)0);
	}

	private m(byte byte0)
	{
		a = new a(this);
	}

	public final volatile PromptContent a(KiwiException kiwiexception)
	{
		e e1 = (e)kiwiexception;
		PromptContent promptcontent = (PromptContent)a.get(e1.getReason());
		if (promptcontent != null)
			return promptcontent;
		else
			return com.amazon.android.licensing.i.e;
	}
}
