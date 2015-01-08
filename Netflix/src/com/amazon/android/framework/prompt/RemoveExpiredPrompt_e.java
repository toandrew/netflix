// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.prompt;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.framework.prompt:
//			PromptManagerImpl, Prompt

final class RemoveExpiredPrompt_e
	implements Task
{

	private Prompt a;
	private PromptManagerImpl b;

	RemoveExpiredPrompt_e(PromptManagerImpl promptmanagerimpl, Prompt prompt)
	{
		b = promptmanagerimpl;
		a = prompt;
	}

	public final void execute()
	{
		PromptManagerImpl.b(b, a);
	}

	public final String toString()
	{
		return (new StringBuilder()).append("PromptManager:removeExpiredPrompt: ").append(a).toString();
	}
}
