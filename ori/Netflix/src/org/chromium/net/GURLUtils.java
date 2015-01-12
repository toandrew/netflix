// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.net;


public final class GURLUtils
{

	public GURLUtils()
	{
	}

	public static String getOrigin(String s)
	{
		return nativeGetOrigin(s);
	}

	public static String getScheme(String s)
	{
		return nativeGetScheme(s);
	}

	private static native String nativeGetOrigin(String s);

	private static native String nativeGetScheme(String s);
}
