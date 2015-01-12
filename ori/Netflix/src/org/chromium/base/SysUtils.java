// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;


public class SysUtils
{

	private static Boolean sLowEndDevice;

	private SysUtils()
	{
	}

	public static boolean isLowEndDevice()
	{
		if (sLowEndDevice == null)
			sLowEndDevice = Boolean.valueOf(nativeIsLowEndDevice());
		return sLowEndDevice.booleanValue();
	}

	private static native boolean nativeIsLowEndDevice();
}
