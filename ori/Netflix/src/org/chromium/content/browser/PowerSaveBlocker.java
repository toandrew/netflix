// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import org.chromium.ui.ViewAndroid;

class PowerSaveBlocker
{

	PowerSaveBlocker()
	{
	}

	private static void applyBlock(ViewAndroid viewandroid)
	{
		viewandroid.incrementKeepScreenOnCount();
	}

	private static void removeBlock(ViewAndroid viewandroid)
	{
		viewandroid.decrementKeepScreenOnCount();
	}
}
