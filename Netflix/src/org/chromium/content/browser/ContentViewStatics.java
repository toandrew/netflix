// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import org.chromium.net.ProxyChangeListener;

public class ContentViewStatics
{

	public ContentViewStatics()
	{
	}

	public static void disablePlatformNotifications()
	{
		ProxyChangeListener.setEnabled(false);
	}

	public static void enablePlatformNotifications()
	{
		ProxyChangeListener.setEnabled(true);
	}

	public static String findAddress(String s)
	{
		if (s == null)
			throw new NullPointerException("addr is null");
		String s1 = nativeFindAddress(s);
		if (s1 == null || s1.isEmpty())
			s1 = null;
		return s1;
	}

	private static native String nativeFindAddress(String s);

	private static native void nativeSetWebKitSharedTimersSuspended(boolean flag);

	public static void setWebKitSharedTimersSuspended(boolean flag)
	{
		nativeSetWebKitSharedTimersSuspended(flag);
	}
}
