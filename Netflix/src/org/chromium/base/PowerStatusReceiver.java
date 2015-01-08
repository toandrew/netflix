// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.content.*;

// Referenced classes of package org.chromium.base:
//			PowerMonitor

public class PowerStatusReceiver extends BroadcastReceiver
{

	public PowerStatusReceiver()
	{
	}

	public void onReceive(Context context, Intent intent)
	{
		PowerMonitor.onBatteryChargingChanged(intent);
	}
}
