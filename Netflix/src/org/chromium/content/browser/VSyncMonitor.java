// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.os.Handler;
import android.view.*;
import org.chromium.content.common.TraceEvent;

public class VSyncMonitor
{
	public static interface Listener
	{

		public abstract void onVSync(VSyncMonitor vsyncmonitor, long l);
	}


	static final boolean $assertionsDisabled = false;
	private static final int MAX_VSYNC_COUNT = 5;
	private static final long NANOSECONDS_PER_MICROSECOND = 1000L;
	private static final long NANOSECONDS_PER_MILLISECOND = 0xf4240L;
	private static final long NANOSECONDS_PER_SECOND = 0x3b9aca00L;
	private static final String TAG = "VSyncMonitor";
	private final Choreographer mChoreographer;
	private long mGoodStartingPointNano;
	private final Handler mHandler;
	private boolean mHaveRequestInFlight;
	private long mLastPostedNano;
	private long mLastUpdateRequestNano;
	private Listener mListener;
	private final long mRefreshPeriodNano;
	private int mTriggerNextVSyncCount;
	private final android.view.Choreographer.FrameCallback mVSyncFrameCallback;
	private final Runnable mVSyncRunnableCallback;

	public VSyncMonitor(Context context, Listener listener)
	{
		this(context, listener, true);
	}

	VSyncMonitor(Context context, Listener listener, boolean flag)
	{
		mListener = listener;
		float f = ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getRefreshRate();
		if (f <= 0.0F)
			f = 60F;
		mRefreshPeriodNano = (long)(1E+009F / f);
		mTriggerNextVSyncCount = 0;
		if (flag && android.os.Build.VERSION.SDK_INT >= 16)
		{
			mChoreographer = Choreographer.getInstance();
			mVSyncFrameCallback = new android.view.Choreographer.FrameCallback() {

				final VSyncMonitor this$0;

				public void doFrame(long l)
				{
					TraceEvent.instant("VSync");
					onVSyncCallback(l);
				}

			
			{
				this$0 = VSyncMonitor.this;
				super();
			}
			};
			mHandler = null;
			mVSyncRunnableCallback = null;
			return;
		} else
		{
			mChoreographer = null;
			mVSyncFrameCallback = null;
			mHandler = new Handler();
			mVSyncRunnableCallback = new Runnable() {

				final VSyncMonitor this$0;

				public void run()
				{
					TraceEvent.instant("VSyncTimer");
					onVSyncCallback(System.nanoTime());
				}

			
			{
				this$0 = VSyncMonitor.this;
				super();
			}
			};
			mGoodStartingPointNano = getCurrentNanoTime();
			mLastPostedNano = 0L;
			return;
		}
	}

	private long getCurrentNanoTime()
	{
		return System.nanoTime();
	}

	private void onVSyncCallback(long l)
	{
		if (!$assertionsDisabled && !mHaveRequestInFlight)
			throw new AssertionError();
		mHaveRequestInFlight = false;
		if (mTriggerNextVSyncCount > 0)
		{
			mTriggerNextVSyncCount = -1 + mTriggerNextVSyncCount;
			postCallback();
		}
		if (mListener != null)
			mListener.onVSync(this, l / 1000L);
	}

	private void postCallback()
	{
		if (mHaveRequestInFlight)
			return;
		mHaveRequestInFlight = true;
		if (isVSyncSignalAvailable())
		{
			mChoreographer.postFrameCallback(mVSyncFrameCallback);
			return;
		} else
		{
			postRunnableCallback();
			return;
		}
	}

	private void postRunnableCallback()
	{
		if (!$assertionsDisabled && isVSyncSignalAvailable())
			throw new AssertionError();
		long l = mLastUpdateRequestNano;
		long l1 = (mGoodStartingPointNano + ((l - mGoodStartingPointNano) / mRefreshPeriodNano) * mRefreshPeriodNano + mRefreshPeriodNano) - l;
		if (!$assertionsDisabled && (l1 < 0L || l1 >= mRefreshPeriodNano))
			throw new AssertionError();
		if (l + l1 <= mLastPostedNano + mRefreshPeriodNano / 2L)
			l1 += mRefreshPeriodNano;
		mLastPostedNano = l + l1;
		if (l1 == 0L)
		{
			mHandler.post(mVSyncRunnableCallback);
			return;
		} else
		{
			mHandler.postDelayed(mVSyncRunnableCallback, l1 / 0xf4240L);
			return;
		}
	}

	public long getVSyncPeriodInMicroseconds()
	{
		return mRefreshPeriodNano / 1000L;
	}

	public boolean isVSyncSignalAvailable()
	{
		return mChoreographer != null;
	}

	public void requestUpdate()
	{
		mTriggerNextVSyncCount = 5;
		mLastUpdateRequestNano = getCurrentNanoTime();
		postCallback();
	}

	public void setVSyncPointForICS(long l)
	{
		mGoodStartingPointNano = l;
	}

	public void stop()
	{
		mTriggerNextVSyncCount = 0;
	}

	public void unregisterListener()
	{
		stop();
		mListener = null;
	}

	static 
	{
		boolean flag;
		if (!org/chromium/content/browser/VSyncMonitor.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}

}
