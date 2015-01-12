// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

public class MemoryPressureListener
{

	public MemoryPressureListener()
	{
	}

	private static void maybeNotifyMemoryPresure(int i)
	{
		if (i == 80)
			nativeOnMemoryPressure(2);
		else
		if (i >= 40 || i == 15)
		{
			nativeOnMemoryPressure(0);
			return;
		}
	}

	private static native void nativeOnMemoryPressure(int i);

	private static void registerSystemCallback(Context context)
	{
		context.registerComponentCallbacks(new ComponentCallbacks2() {

			public void onConfigurationChanged(Configuration configuration)
			{
			}

			public void onLowMemory()
			{
				MemoryPressureListener.nativeOnMemoryPressure(2);
			}

			public void onTrimMemory(int i)
			{
				MemoryPressureListener.maybeNotifyMemoryPresure(i);
			}

		});
	}

	public static void simulateMemoryPressureSignal(int i)
	{
		maybeNotifyMemoryPresure(i);
	}


}
