// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.prompt;

import android.app.Activity;
import android.app.Dialog;
import com.amazon.android.d.a;

// Referenced classes of package com.amazon.android.framework.prompt:
//			Prompt, PromptContent, g, d

public abstract class SimplePrompt extends Prompt
{

	private final PromptContent content;

	public SimplePrompt(PromptContent promptcontent)
	{
		a.a(promptcontent, "content");
		content = promptcontent;
	}

	protected abstract void doAction();

	protected boolean doCompatibilityCheck(Activity activity)
	{
		return content.isVisible();
	}

	public final Dialog doCreate(Activity activity)
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
		builder.setTitle(content.getTitle()).setMessage(content.getMessage()).setCancelable(false).setNeutralButton(content.getButtonLabel(), new g(this));
		return builder.create();
	}

	protected void doExpiration(d d)
	{
		doAction();
	}
}
