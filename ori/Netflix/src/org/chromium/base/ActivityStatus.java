// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.app.Activity;
import java.util.Iterator;

// Referenced classes of package org.chromium.base:
//			ObserverList, ThreadUtils

public class ActivityStatus
{
	public static interface StateListener
	{

		public abstract void onActivityStateChange(int i);
	}


	public static final int CREATED = 1;
	public static final int DESTROYED = 6;
	public static final int PAUSED = 4;
	public static final int RESUMED = 3;
	public static final int STARTED = 2;
	public static final int STOPPED = 5;
	private static Activity sActivity;
	private static int sActivityState;
	private static final ObserverList sStateListeners = new ObserverList();

	private ActivityStatus()
	{
	}

	public static Activity getActivity()
	{
		return sActivity;
	}

	public static int getState()
	{
		return sActivityState;
	}

	public static boolean isPaused()
	{
		return sActivityState == 4;
	}

	private static native void nativeOnActivityStateChange(int i);

	public static void onStateChange(Activity activity, int i)
	{
		if (sActivity != activity)
			sActivity = activity;
		sActivityState = i;
		for (Iterator iterator = sStateListeners.iterator(); iterator.hasNext(); ((StateListener)iterator.next()).onActivityStateChange(i));
		if (i == 6)
			sActivity = null;
	}

	public static void registerStateListener(StateListener statelistener)
	{
		sStateListeners.addObserver(statelistener);
	}

	private static void registerThreadSafeNativeStateListener()
	{
		ThreadUtils.runOnUiThread(new Runnable() {

			public void run()
			{
				ActivityStatus.sStateListeners.addObserver(new StateListener() {
					public void onActivityStateChange(int i)
					{
						ActivityStatus.nativeOnActivityStateChange(i);
					}
				});
			}

		});
	}

	public static void unregisterStateListener(StateListener statelistener)
	{
		sStateListeners.removeObserver(statelistener);
	}



}
