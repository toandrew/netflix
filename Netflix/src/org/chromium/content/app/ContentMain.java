// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.app;

import android.content.Context;

public class ContentMain
{

	public ContentMain()
	{
	}

	public static void initApplicationContext(Context context)
	{
		nativeInitApplicationContext(context);
	}

	private static native void nativeInitApplicationContext(Context context);

	private static native int nativeStart();

	public static int start()
	{
		return nativeStart();
	}
}
