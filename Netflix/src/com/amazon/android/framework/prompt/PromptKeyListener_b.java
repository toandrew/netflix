// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.prompt;

import android.content.DialogInterface;
import android.view.KeyEvent;

// Referenced classes of package com.amazon.android.framework.prompt:
//			Prompt

final class PromptKeyListener_b
	implements android.content.DialogInterface.OnKeyListener
{

	private Prompt a;

	PromptKeyListener_b(Prompt prompt)
	{
		a = prompt;
	}

	public final boolean onKey(DialogInterface dialoginterface, int i, KeyEvent keyevent)
	{
		return i == 84;
	}
}
