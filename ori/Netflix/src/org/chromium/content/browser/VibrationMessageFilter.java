// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.os.Vibrator;

class VibrationMessageFilter
{

	private final Vibrator mVibrator;

	private VibrationMessageFilter(Context context)
	{
		mVibrator = (Vibrator)context.getSystemService("vibrator");
	}

	private void cancelVibration()
	{
		mVibrator.cancel();
	}

	private static VibrationMessageFilter create(Context context)
	{
		return new VibrationMessageFilter(context);
	}

	private void vibrate(long l)
	{
		mVibrator.vibrate(l);
	}
}
