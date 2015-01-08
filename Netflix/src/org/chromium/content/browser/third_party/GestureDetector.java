// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.third_party;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.*;

public class GestureDetector
{
	private class GestureHandler extends Handler
	{

		final GestureDetector this$0;

		public void handleMessage(Message message)
		{
			message.what;
			JVM INSTR tableswitch 1 3: default 32
		//		               1 59
		//		               2 79
		//		               3 87;
			   goto _L1 _L2 _L3 _L4
_L1:
			throw new RuntimeException((new StringBuilder()).append("Unknown message ").append(message).toString());
_L2:
			mListener.onShowPress(mCurrentDownEvent);
_L6:
			return;
_L3:
			dispatchLongPress();
			return;
_L4:
			if (mDoubleTapListener != null && !mStillDown)
			{
				mDoubleTapListener.onSingleTapConfirmed(mCurrentDownEvent);
				return;
			}
			if (true) goto _L6; else goto _L5
_L5:
		}

		GestureHandler()
		{
			this$0 = GestureDetector.this;
			super();
		}

		GestureHandler(Handler handler)
		{
			this$0 = GestureDetector.this;
			super(handler.getLooper());
		}
	}

	public static interface OnDoubleTapListener
	{

		public abstract boolean onDoubleTap(MotionEvent motionevent);

		public abstract boolean onDoubleTapEvent(MotionEvent motionevent);

		public abstract boolean onSingleTapConfirmed(MotionEvent motionevent);
	}

	public static interface OnGestureListener
	{

		public abstract boolean onDown(MotionEvent motionevent);

		public abstract boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1);

		public abstract void onLongPress(MotionEvent motionevent);

		public abstract boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1);

		public abstract void onShowPress(MotionEvent motionevent);

		public abstract boolean onSingleTapUp(MotionEvent motionevent);
	}

	public static class SimpleOnGestureListener
		implements OnGestureListener, OnDoubleTapListener
	{

		public boolean onDoubleTap(MotionEvent motionevent)
		{
			return false;
		}

		public boolean onDoubleTapEvent(MotionEvent motionevent)
		{
			return false;
		}

		public boolean onDown(MotionEvent motionevent)
		{
			return false;
		}

		public boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
		{
			return false;
		}

		public void onLongPress(MotionEvent motionevent)
		{
		}

		public boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
		{
			return false;
		}

		public void onShowPress(MotionEvent motionevent)
		{
		}

		public boolean onSingleTapConfirmed(MotionEvent motionevent)
		{
			return false;
		}

		public boolean onSingleTapUp(MotionEvent motionevent)
		{
			return false;
		}

		public SimpleOnGestureListener()
		{
		}
	}


	private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
	private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
	private static final int LONG_PRESS = 2;
	private static final int SHOW_PRESS = 1;
	private static final int TAP = 3;
	private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
	private boolean mAlwaysInBiggerTapRegion;
	private boolean mAlwaysInTapRegion;
	private MotionEvent mCurrentDownEvent;
	private OnDoubleTapListener mDoubleTapListener;
	private int mDoubleTapSlopSquare;
	private int mDoubleTapTouchSlopSquare;
	private float mDownFocusX;
	private float mDownFocusY;
	private final Handler mHandler;
	private boolean mInLongPress;
	private boolean mIsDoubleTapping;
	private boolean mIsLongpressEnabled;
	private float mLastFocusX;
	private float mLastFocusY;
	private final OnGestureListener mListener;
	private int mMaximumFlingVelocity;
	private int mMinimumFlingVelocity;
	private MotionEvent mPreviousUpEvent;
	private boolean mStillDown;
	private int mTouchSlopSquare;
	private VelocityTracker mVelocityTracker;

	public GestureDetector(Context context, OnGestureListener ongesturelistener)
	{
		this(context, ongesturelistener, null);
	}

	public GestureDetector(Context context, OnGestureListener ongesturelistener, Handler handler)
	{
		if (handler != null)
			mHandler = new GestureHandler(handler);
		else
			mHandler = new GestureHandler();
		mListener = ongesturelistener;
		if (ongesturelistener instanceof OnDoubleTapListener)
			setOnDoubleTapListener((OnDoubleTapListener)ongesturelistener);
		init(context);
	}

	public GestureDetector(Context context, OnGestureListener ongesturelistener, Handler handler, boolean flag)
	{
		this(context, ongesturelistener, handler);
	}

	public GestureDetector(OnGestureListener ongesturelistener)
	{
		this(null, ongesturelistener, null);
	}

	public GestureDetector(OnGestureListener ongesturelistener, Handler handler)
	{
		this(null, ongesturelistener, handler);
	}

	private void cancel()
	{
		mHandler.removeMessages(1);
		mHandler.removeMessages(2);
		mHandler.removeMessages(3);
		mVelocityTracker.recycle();
		mVelocityTracker = null;
		mIsDoubleTapping = false;
		mStillDown = false;
		mAlwaysInTapRegion = false;
		mAlwaysInBiggerTapRegion = false;
		if (mInLongPress)
			mInLongPress = false;
	}

	private void cancelTaps()
	{
		mHandler.removeMessages(1);
		mHandler.removeMessages(2);
		mHandler.removeMessages(3);
		mIsDoubleTapping = false;
		mAlwaysInTapRegion = false;
		mAlwaysInBiggerTapRegion = false;
		if (mInLongPress)
			mInLongPress = false;
	}

	private void dispatchLongPress()
	{
		mHandler.removeMessages(3);
		mInLongPress = true;
		mListener.onLongPress(mCurrentDownEvent);
	}

	private void init(Context context)
	{
		if (mListener == null)
		{
			throw new NullPointerException("OnGestureListener must not be null");
		} else
		{
			mIsLongpressEnabled = true;
			ViewConfiguration viewconfiguration = ViewConfiguration.get(context);
			int i = viewconfiguration.getScaledTouchSlop();
			int j = viewconfiguration.getScaledDoubleTapSlop();
			mMinimumFlingVelocity = viewconfiguration.getScaledMinimumFlingVelocity();
			mMaximumFlingVelocity = viewconfiguration.getScaledMaximumFlingVelocity();
			mTouchSlopSquare = i * i;
			mDoubleTapTouchSlopSquare = i * i;
			mDoubleTapSlopSquare = j * j;
			return;
		}
	}

	private boolean isConsideredDoubleTap(MotionEvent motionevent, MotionEvent motionevent1, MotionEvent motionevent2)
	{
		if (mAlwaysInBiggerTapRegion && motionevent2.getEventTime() - motionevent1.getEventTime() <= (long)DOUBLE_TAP_TIMEOUT)
		{
			int i = (int)motionevent.getX() - (int)motionevent2.getX();
			int j = (int)motionevent.getY() - (int)motionevent2.getY();
			if (i * i + j * j < mDoubleTapSlopSquare)
				return true;
		}
		return false;
	}

	public boolean isLongpressEnabled()
	{
		return mIsLongpressEnabled;
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		int k;
		float f2;
		float f3;
		boolean flag1;
		int i = motionevent.getAction();
		if (mVelocityTracker == null)
			mVelocityTracker = VelocityTracker.obtain();
		mVelocityTracker.addMovement(motionevent);
		boolean flag;
		int j;
		float f;
		float f1;
		int l;
		if ((i & 0xff) == 6)
			flag = true;
		else
			flag = false;
		if (flag)
			j = motionevent.getActionIndex();
		else
			j = -1;
		f = 0.0F;
		f1 = 0.0F;
		k = motionevent.getPointerCount();
		l = 0;
		while (l < k) 
		{
			if (j != l)
			{
				f += motionevent.getX(l);
				f1 += motionevent.getY(l);
			}
			l++;
		}
		int i1;
		int j1;
		if (flag)
			i1 = k - 1;
		else
			i1 = k;
		f2 = f / (float)i1;
		f3 = f1 / (float)i1;
		j1 = i & 0xff;
		flag1 = false;
		j1;
		JVM INSTR tableswitch 0 6: default 200
	//	               0 392
	//	               1 906
	//	               2 658
	//	               3 1151
	//	               4 200
	//	               5 210
	//	               6 240;
		   goto _L1 _L2 _L3 _L4 _L5 _L1 _L6 _L7
_L1:
		return flag1;
_L6:
		mLastFocusX = f2;
		mDownFocusX = f2;
		mLastFocusY = f3;
		mDownFocusY = f3;
		cancelTaps();
		return false;
_L7:
		int j3;
		float f8;
		float f9;
		int l3;
		mLastFocusX = f2;
		mDownFocusX = f2;
		mLastFocusY = f3;
		mDownFocusY = f3;
		mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
		j3 = motionevent.getActionIndex();
		int k3 = motionevent.getPointerId(j3);
		f8 = mVelocityTracker.getXVelocity(k3);
		f9 = mVelocityTracker.getYVelocity(k3);
		l3 = 0;
_L9:
		int i4 = l3;
		flag1 = false;
		if (i4 >= k)
			continue; /* Loop/switch isn't completed */
		if (l3 != j3)
			break; /* Loop/switch isn't completed */
_L11:
		l3++;
		int j4;
		if (true) goto _L9; else goto _L8
_L8:
		if (f8 * mVelocityTracker.getXVelocity(j4 = motionevent.getPointerId(l3)) + f9 * mVelocityTracker.getYVelocity(j4) >= 0.0F) goto _L11; else goto _L10
_L10:
		mVelocityTracker.clear();
		return false;
_L2:
		OnDoubleTapListener ondoubletaplistener = mDoubleTapListener;
		boolean flag5 = false;
		if (ondoubletaplistener != null)
		{
			boolean flag6 = mHandler.hasMessages(3);
			if (flag6)
				mHandler.removeMessages(3);
			if (mCurrentDownEvent != null && mPreviousUpEvent != null && flag6 && isConsideredDoubleTap(mCurrentDownEvent, mPreviousUpEvent, motionevent))
			{
				mIsDoubleTapping = true;
				flag5 = false | mDoubleTapListener.onDoubleTap(mCurrentDownEvent) | mDoubleTapListener.onDoubleTapEvent(motionevent);
			} else
			{
				mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
				flag5 = false;
			}
		}
		mLastFocusX = f2;
		mDownFocusX = f2;
		mLastFocusY = f3;
		mDownFocusY = f3;
		if (mCurrentDownEvent != null)
			mCurrentDownEvent.recycle();
		mCurrentDownEvent = MotionEvent.obtain(motionevent);
		mAlwaysInTapRegion = true;
		mAlwaysInBiggerTapRegion = true;
		mStillDown = true;
		mInLongPress = false;
		if (mIsLongpressEnabled)
		{
			mHandler.removeMessages(2);
			mHandler.sendEmptyMessageAtTime(2, mCurrentDownEvent.getDownTime() + (long)TAP_TIMEOUT + (long)LONGPRESS_TIMEOUT);
		}
		mHandler.sendEmptyMessageAtTime(1, mCurrentDownEvent.getDownTime() + (long)TAP_TIMEOUT);
		return flag5 | mListener.onDown(motionevent);
_L4:
		float f6;
		float f7;
		boolean flag3 = mInLongPress;
		flag1 = false;
		if (flag3)
			continue; /* Loop/switch isn't completed */
		f6 = mLastFocusX - f2;
		f7 = mLastFocusY - f3;
		if (mIsDoubleTapping)
			return false | mDoubleTapListener.onDoubleTapEvent(motionevent);
		if (!mAlwaysInTapRegion)
			break; /* Loop/switch isn't completed */
		int j2 = (int)(f2 - mDownFocusX);
		int k2 = (int)(f3 - mDownFocusY);
		int l2 = j2 * j2 + k2 * k2;
		int i3 = mTouchSlopSquare;
		flag1 = false;
		if (l2 > i3)
		{
			flag1 = mListener.onScroll(mCurrentDownEvent, motionevent, f6, f7);
			mLastFocusX = f2;
			mLastFocusY = f3;
			mAlwaysInTapRegion = false;
			mHandler.removeMessages(3);
			mHandler.removeMessages(1);
			mHandler.removeMessages(2);
		}
		if (l2 > mDoubleTapTouchSlopSquare)
		{
			mAlwaysInBiggerTapRegion = false;
			return flag1;
		}
		if (true) goto _L1; else goto _L12
_L12:
		int i2;
		if (Math.abs(f6) >= 1.0F)
			break; /* Loop/switch isn't completed */
		i2 = Math.abs(f7) != 1.0F;
		flag1 = false;
		if (i2 < 0) goto _L1; else goto _L13
_L13:
		boolean flag4 = mListener.onScroll(mCurrentDownEvent, motionevent, f6, f7);
		mLastFocusX = f2;
		mLastFocusY = f3;
		return flag4;
_L3:
		MotionEvent motionevent1;
		mStillDown = false;
		motionevent1 = MotionEvent.obtain(motionevent);
		if (!mIsDoubleTapping) goto _L15; else goto _L14
_L14:
		boolean flag2 = false | mDoubleTapListener.onDoubleTapEvent(motionevent);
_L18:
		if (mPreviousUpEvent != null)
			mPreviousUpEvent.recycle();
		mPreviousUpEvent = motionevent1;
		if (mVelocityTracker != null)
		{
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
		mIsDoubleTapping = false;
		mHandler.removeMessages(1);
		mHandler.removeMessages(2);
		return flag2;
_L15:
		if (!mInLongPress) goto _L17; else goto _L16
_L16:
		mHandler.removeMessages(3);
		mInLongPress = false;
		flag2 = false;
		  goto _L18
_L17:
		if (!mAlwaysInTapRegion) goto _L20; else goto _L19
_L19:
		flag2 = mListener.onSingleTapUp(motionevent);
		  goto _L18
_L20:
		float f4;
		float f5;
		VelocityTracker velocitytracker = mVelocityTracker;
		int k1 = motionevent.getPointerId(0);
		velocitytracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
		f4 = velocitytracker.getYVelocity(k1);
		f5 = velocitytracker.getXVelocity(k1);
		if (Math.abs(f4) > (float)mMinimumFlingVelocity) goto _L22; else goto _L21
_L21:
		int l1;
		l1 = Math.abs(f5) != (float)mMinimumFlingVelocity;
		flag2 = false;
		if (l1 <= 0) goto _L18; else goto _L22
_L22:
		flag2 = mListener.onFling(mCurrentDownEvent, motionevent, f5, f4);
		  goto _L18
_L5:
		cancel();
		return false;
	}

	public void setIsLongpressEnabled(boolean flag)
	{
		mIsLongpressEnabled = flag;
	}

	public void setOnDoubleTapListener(OnDoubleTapListener ondoubletaplistener)
	{
		mDoubleTapListener = ondoubletaplistener;
	}






}
