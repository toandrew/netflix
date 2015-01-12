// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;


public class BrowserStartupConfig
{
	public static interface StartupCallback
	{

		public abstract void run(int i);
	}


	private static boolean sBrowserMayStartAsynchronously = false;
	private static StartupCallback sBrowserStartupCompleteCallback = null;

	public BrowserStartupConfig()
	{
	}

	private static boolean browserMayStartAsynchonously()
	{
		return sBrowserMayStartAsynchronously;
	}

	private static void browserStartupComplete(int i)
	{
		if (sBrowserStartupCompleteCallback != null)
			sBrowserStartupCompleteCallback.run(i);
	}

	public static void setAsync(StartupCallback startupcallback)
	{
		sBrowserMayStartAsynchronously = true;
		sBrowserStartupCompleteCallback = startupcallback;
	}

}
