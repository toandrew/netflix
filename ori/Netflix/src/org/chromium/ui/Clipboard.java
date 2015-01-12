// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui;

import android.content.*;
import android.text.TextUtils;

public class Clipboard
{

	private final ClipboardManager mClipboardManager;
	private final Context mContext;

	private Clipboard(Context context)
	{
		mContext = context;
		mClipboardManager = (ClipboardManager)context.getSystemService("clipboard");
	}

	private static Clipboard create(Context context)
	{
		return new Clipboard(context);
	}

	private String getCoercedText()
	{
		ClipData clipdata = mClipboardManager.getPrimaryClip();
		if (clipdata != null && clipdata.getItemCount() > 0)
		{
			CharSequence charsequence = clipdata.getItemAt(0).coerceToText(mContext);
			if (charsequence != null)
				return charsequence.toString();
		}
		return null;
	}

	private boolean hasPlainText()
	{
		ClipData clipdata = mClipboardManager.getPrimaryClip();
		boolean flag = false;
		if (clipdata != null)
		{
			int i = clipdata.getItemCount();
			flag = false;
			if (i > 0)
			{
				boolean flag1 = TextUtils.isEmpty(clipdata.getItemAt(0).getText());
				flag = false;
				if (!flag1)
					flag = true;
			}
		}
		return flag;
	}

	private void setText(String s)
	{
		mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, s));
	}
}
