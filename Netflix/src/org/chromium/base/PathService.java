// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;


public abstract class PathService
{

	public static final int DIR_MODULE = 3;

	private PathService()
	{
	}

	private static native void nativeOverride(int i, String s);

	public static void override(int i, String s)
	{
		nativeOverride(i, s);
	}
}
