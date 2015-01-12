// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import org.chromium.content.common.CommandLine;

public class DeviceUtils
{

	private static final int MINIMUM_TABLET_WIDTH_DP = 600;
	private static Boolean sIsTablet = null;
	private static Boolean sIsTv = null;

	public DeviceUtils()
	{
	}

	public static void addDeviceSpecificUserAgentSwitch(Context context)
	{
		if (isTablet(context))
		{
			CommandLine.getInstance().appendSwitch("tablet-ui");
			return;
		} else
		{
			CommandLine.getInstance().appendSwitch("use-mobile-user-agent");
			return;
		}
	}

	public static boolean isTablet(Context context)
	{
		boolean flag = true;
		if (sIsTablet == null)
		{
			if (isTv(context))
			{
				sIsTablet = Boolean.valueOf(flag);
				return sIsTablet.booleanValue();
			}
			if (context.getResources().getConfiguration().smallestScreenWidthDp < 600)
				flag = false;
			sIsTablet = Boolean.valueOf(flag);
		}
		return sIsTablet.booleanValue();
	}

	public static boolean isTv(Context context)
	{
		if (sIsTv == null)
		{
			PackageManager packagemanager = context.getPackageManager();
			if (packagemanager != null)
			{
				sIsTv = Boolean.valueOf(packagemanager.hasSystemFeature("android.hardware.type.television"));
				return sIsTv.booleanValue();
			}
			sIsTv = Boolean.valueOf(false);
		}
		return sIsTv.booleanValue();
	}

}
