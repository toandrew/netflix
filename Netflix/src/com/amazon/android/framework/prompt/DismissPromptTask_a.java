// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.prompt;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.framework.prompt:
//			Prompt

final class DismissPromptTask_a
	implements Task
{

	private Prompt a;

	DismissPromptTask_a(Prompt prompt)
	{
		a = prompt;
	}

	public final void execute()
	{
		a.dismiss();
	}

	public final String toString()
	{
		return (new StringBuilder()).append("DismissPromptTask: ").append(a.toString()).toString();
	}
}
