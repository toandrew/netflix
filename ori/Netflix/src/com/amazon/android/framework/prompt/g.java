// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.prompt;

import android.content.DialogInterface;

// Referenced classes of package com.amazon.android.framework.prompt:
//			SimplePrompt

final class g
	implements android.content.DialogInterface.OnClickListener
{

	private SimplePrompt a;

	g(SimplePrompt simpleprompt)
	{
		a = simpleprompt;
	}

	public final void onClick(DialogInterface dialoginterface, int i)
	{
		if (a.dismiss())
			a.doAction();
	}
}
