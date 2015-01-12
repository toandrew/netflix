// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

public class BuildInfo
{

	private static final int MAX_FINGERPRINT_LENGTH = 128;
	private static final String TAG = "BuildInfo";

	private BuildInfo()
	{
	}

	public static String getAndroidBuildFingerprint()
	{
		return Build.FINGERPRINT.substring(0, Math.min(Build.FINGERPRINT.length(), 128));
	}

	public static String getAndroidBuildId()
	{
		return Build.ID;
	}

	public static String getBrand()
	{
		return Build.BRAND;
	}

	public static String getDevice()
	{
		return Build.DEVICE;
	}

	public static String getDeviceModel()
	{
		return Build.MODEL;
	}

	public static String getPackageLabel(Context context)
	{
		CharSequence charsequence;
		try
		{
			PackageManager packagemanager = context.getPackageManager();
			charsequence = packagemanager.getApplicationLabel(packagemanager.getApplicationInfo(context.getPackageName(), 128));
		}
		catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
		{
			return "";
		}
		if (charsequence == null) {
		      return "";
		}
		return charsequence.toString();

	}

	public static String getPackageName(Context context)
	{
		String s;
		if (context != null)
			s = context.getPackageName();
		else
			s = null;
		if (s != null)
			return s;
		else
			return "";
	}

	public static String getPackageVersionCode(Context context)
	{
		String s = "versionCode not available.";
		PackageInfo packageinfo;
		String s1;
		try
		{
			packageinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		}
		catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
		{
			Log.d("BuildInfo", s);
			return s;
		}
		s = "";
		if (packageinfo.versionCode <= 0) {
		    return s;
		}
		s1 = Integer.toString(packageinfo.versionCode);
		s = s1;
		return s;
	}

	public static String getPackageVersionName(Context context)
	{
		String s;
		try
		{
			s = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		}
		catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
		{
			Log.d("BuildInfo", "versionName not available");
			return "versionName not available";
		}
		return s;
	}

	public static int getSdkInt()
	{
		return android.os.Build.VERSION.SDK_INT;
	}
}
