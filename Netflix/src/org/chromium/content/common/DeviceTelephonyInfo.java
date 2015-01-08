// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.common;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceTelephonyInfo
{

	private TelephonyManager mTelManager;

	private DeviceTelephonyInfo(Context context)
	{
		mTelManager = (TelephonyManager)context.getApplicationContext().getSystemService("phone");
	}

	public static DeviceTelephonyInfo create(Context context)
	{
		return new DeviceTelephonyInfo(context);
	}

	public String getNetworkCountryIso()
	{
		return mTelManager.getNetworkCountryIso();
	}
}
