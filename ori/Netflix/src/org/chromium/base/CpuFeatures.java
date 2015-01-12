// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;


public abstract class CpuFeatures
{

	public CpuFeatures()
	{
	}

	public static int getCount()
	{
		return nativeGetCoreCount();
	}

	public static long getMask()
	{
		return nativeGetCpuFeatures();
	}

	private static native int nativeGetCoreCount();

	private static native long nativeGetCpuFeatures();
}
