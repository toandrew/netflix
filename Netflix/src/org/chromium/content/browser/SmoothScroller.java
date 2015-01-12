// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.animation.TimeAnimator;
import android.os.*;
import android.view.MotionEvent;

// Referenced classes of package org.chromium.content.browser:
//			ContentViewCore, RenderCoordinates

public class SmoothScroller
{

	static final boolean $assertionsDisabled;
	private final byte STATE_FINAL = 3;
	private final byte STATE_INITIAL = 0;
	private final byte STATE_MOVING = 1;
	private final byte STATE_PENDING_UP = 2;
	private final ContentViewCore mContentViewCore;
	private float mCurrentY;
	private long mDownTime;
	private final Handler mHandler = new Handler(Looper.getMainLooper());
	private final float mMouseEventX;
	private final float mMouseEventY;
	private int mNativePtr;
	private final boolean mScrollDown;
	private TimeAnimator mTimeAnimator;
	private byte state;

	SmoothScroller(ContentViewCore contentviewcore, boolean flag, int i, int j)
	{
		state = 0;
		mContentViewCore = contentviewcore;
		mScrollDown = flag;
		float f = mContentViewCore.getRenderCoordinates().getDeviceScaleFactor();
		mMouseEventX = f * (float)i;
		mMouseEventY = f * (float)j;
		mCurrentY = mMouseEventY;
	}

	private Runnable createJBRunnable()
	{
		return new Runnable() {

			public void run()
			{
				mTimeAnimator = new TimeAnimator();
				mTimeAnimator.setTimeListener(new android.animation.TimeAnimator.TimeListener() {
					public void onTimeUpdate(TimeAnimator timeanimator, long l, long l1)
					{
						if (!sendEvent(l + mDownTime))
							mTimeAnimator.end();
					}

				});
				mTimeAnimator.start();
			}

		};
	}

	private Runnable createPreJBRunnable()
	{
		return new Runnable() {

			public void run()
			{
				if (sendEvent(SystemClock.uptimeMillis()))
					mHandler.post(this);
			}

		};
	}

	private native double nativeGetScrollDelta(int i, double d);

	private native boolean nativeHasFinished(int i);

	private native void nativeSetHasSentMotionUp(int i);

	boolean sendEvent(long l)
	{
	    return true;
//		state;
//		JVM INSTR tableswitch 0 2: default 32
//	//	               0 42
//	//	               1 95
//	//	               2 206;
//		   goto _L1 _L2 _L3 _L4
//_L1:
//		break; /* Loop/switch isn't completed */
//_L4:
//		break MISSING_BLOCK_LABEL_206;
//_L5:
//		MotionEvent motionevent2;
//		return state != 3;
//_L2:
//		mDownTime = SystemClock.uptimeMillis();
//		motionevent2 = MotionEvent.obtain(mDownTime, mDownTime, 0, mMouseEventX, mCurrentY, 0);
//		mContentViewCore.onTouchEvent(motionevent2);
//		motionevent2.recycle();
//		state = 1;
//		  goto _L5
//_L3:
//		double d = nativeGetScrollDelta(mNativePtr, mContentViewCore.getRenderCoordinates().getDeviceScaleFactor());
//		if (d != 0.0D)
//		{
//			double d1 = mCurrentY;
//			if (mScrollDown)
//				d = -d;
//			mCurrentY = (float)(d1 + d);
//			MotionEvent motionevent1 = MotionEvent.obtain(mDownTime, l, 2, mMouseEventX, mCurrentY, 0);
//			mContentViewCore.onTouchEvent(motionevent1);
//			motionevent1.recycle();
//		}
//		if (nativeHasFinished(mNativePtr))
//			state = 2;
//		  goto _L5
//		MotionEvent motionevent = MotionEvent.obtain(mDownTime, l, 1, mMouseEventX, mCurrentY, 0);
//		mContentViewCore.onTouchEvent(motionevent);
//		motionevent.recycle();
//		nativeSetHasSentMotionUp(mNativePtr);
//		state = 3;
//		  goto _L5
	}

	void start(int i)
	{
		if (!$assertionsDisabled && mNativePtr != 0)
			throw new AssertionError();
		mNativePtr = i;
		Runnable runnable;
		if (android.os.Build.VERSION.SDK_INT >= 16)
			runnable = createJBRunnable();
		else
			runnable = createPreJBRunnable();
		mHandler.post(runnable);
	}

	static 
	{
//		boolean flag;
//		if (!org/chromium/content/browser/SmoothScroller.desiredAssertionStatus())
//			flag = true;
//		else
//			flag = false;
		$assertionsDisabled = false;
	}



/*
	static TimeAnimator access$002(SmoothScroller smoothscroller, TimeAnimator timeanimator)
	{
		smoothscroller.mTimeAnimator = timeanimator;
		return timeanimator;
	}

*/


}
