// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.*;
import android.os.Debug;
import android.util.Log;
import org.chromium.content.common.CommandLine;

public class HeapStatsLogger
{
	private static class HeapStatsLoggerIntentFilter extends IntentFilter
	{

		HeapStatsLoggerIntentFilter()
		{
			addAction("com.google.android.apps.chrome.LOG_HEAP_STATS");
		}
	}

	private class HeapStatsLoggerReceiver extends BroadcastReceiver
	{

		final HeapStatsLogger this$0;

		public void onReceive(Context context, Intent intent)
		{
			if ("com.google.android.apps.chrome.LOG_HEAP_STATS".equals(intent.getAction()))
			{
				log();
				return;
			} else
			{
				Log.e("HeapStatsLogger", (new StringBuilder()).append("Unexpected intent: ").append(intent).toString());
				return;
			}
		}

		private HeapStatsLoggerReceiver()
		{
			this$0 = HeapStatsLogger.this;
			super();
		}

	}


	private static final String ACTION_LOG = "com.google.android.apps.chrome.LOG_HEAP_STATS";
	private static final String TAG = "HeapStatsLogger";
	private static HeapStatsLogger sHeapStats;
	private HeapStatsLoggerReceiver mBroadcastReceiver;
	private HeapStatsLoggerIntentFilter mIntentFilter;

	private HeapStatsLogger(Context context)
	{
		Debug.startAllocCounting();
		mBroadcastReceiver = new HeapStatsLoggerReceiver();
		mIntentFilter = new HeapStatsLoggerIntentFilter();
		context.registerReceiver(mBroadcastReceiver, mIntentFilter);
	}

	public static void init(Context context)
	{
		if (CommandLine.getInstance().hasSwitch("enable-test-intents"))
			sHeapStats = new HeapStatsLogger(context);
	}

	private void log()
	{
		Log.i("HeapStatsLogger", (new StringBuilder()).append("heap_stats gc_count=").append(Debug.getGlobalGcInvocationCount()).append(" times ").append("alloc_count=").append(Debug.getGlobalAllocCount()).append(" times ").append("alloc_size=").append(Debug.getGlobalAllocSize()).append(" bytes ").append("freed_count=").append(Debug.getGlobalFreedCount()).append(" times ").append("freed_size=").append(Debug.getGlobalFreedSize()).append(" bytes").toString());
	}

}
