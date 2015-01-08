// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.content.*;
import android.os.Handler;
import android.os.Looper;

// Referenced classes of package org.chromium.base:
//			ActivityStatus

public class PowerMonitor
	implements ActivityStatus.StateListener
{
	private static class LazyHolder
	{

		private static final PowerMonitor INSTANCE = new PowerMonitor();



		private LazyHolder()
		{
		}
	}


	private static final long SUSPEND_DELAY_MS = 60000L;
	private static PowerMonitor sInstance;
	private static final Runnable sSuspendTask = new Runnable() {

		public void run()
		{
			PowerMonitor.nativeOnMainActivitySuspended();
		}

	};
	private final Handler mHandler;
	private boolean mIsBatteryPower;

	private PowerMonitor()
	{
		mHandler = new Handler(Looper.getMainLooper());
	}


	public static void create(Context context)
	{
		if (sInstance == null)
		{
			sInstance = LazyHolder.INSTANCE;
			ActivityStatus.registerStateListener(sInstance);
			onBatteryChargingChanged(context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")));
		}
	}

	public static void createForTests(Context context)
	{
		sInstance = LazyHolder.INSTANCE;
	}

	private static boolean isBatteryPower()
	{
		return sInstance.mIsBatteryPower;
	}

	private static native void nativeOnBatteryChargingChanged();

	private static native void nativeOnMainActivityResumed();

	private static native void nativeOnMainActivitySuspended();

	public static void onBatteryChargingChanged(Intent intent)
	{
		boolean flag = true;
		if (sInstance == null)
			return;
		int i = intent.getIntExtra("plugged", -1);
		PowerMonitor powermonitor = sInstance;
		if (i == 2 || i == flag)
			flag = false;
		powermonitor.mIsBatteryPower = flag;
		nativeOnBatteryChargingChanged();
	}

	public void onActivityStateChange(int i)
	{
		if (i == 3)
		{
			mHandler.removeCallbacks(sSuspendTask);
			nativeOnMainActivityResumed();
		} else
		if (i == 4)
		{
			mHandler.postDelayed(sSuspendTask, 60000L);
			return;
		}
	}


}
