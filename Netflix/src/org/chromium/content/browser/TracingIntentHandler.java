// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;


public class TracingIntentHandler
{

	public TracingIntentHandler()
	{
	}

	public static void beginTracing(String s)
	{
		nativeBeginTracing(s);
	}

	public static void endTracing()
	{
		nativeEndTracing();
	}

	private static native void nativeBeginTracing(String s);

	private static native void nativeEndTracing();
}
