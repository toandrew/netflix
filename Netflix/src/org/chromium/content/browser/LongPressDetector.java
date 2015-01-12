// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import java.util.Iterator;

class LongPressDetector
{
	static interface LongPressDelegate
	{

		public abstract void onLongPress(MotionEvent motionevent);
	}

	private class LongPressHandler extends Handler
	{
		public void handleMessage(Message message)
		{
			switch (message.what)
			{
			default:
				throw new RuntimeException((new StringBuilder()).append("Unknown message ").append(message).toString());

			case 2: // '\002'
				dispatchLongPress();
				break;
			}
		}

		LongPressHandler()
		{
			super();
		}
	}


	private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
	private static final int LONG_PRESS = 2;
	private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
	private MotionEvent mCurrentDownEvent;
	private boolean mInLongPress;
	private final LongPressDelegate mLongPressDelegate;
	private final Handler mLongPressHandler = new LongPressHandler();
	private boolean mMoveConfirmed;
	private int mTouchInitialX;
	private int mTouchInitialY;
	private final int mTouchSlopSquare;

	LongPressDetector(Context context, LongPressDelegate longpressdelegate)
	{
		mLongPressDelegate = longpressdelegate;
		int i = ViewConfiguration.get(context).getScaledTouchSlop();
		mTouchSlopSquare = i * i;
	}

	private void dispatchLongPress()
	{
		mInLongPress = true;
		mLongPressDelegate.onLongPress(mCurrentDownEvent);
		mCurrentDownEvent.recycle();
		mCurrentDownEvent = null;
	}

	void cancelLongPress()
	{
		mInLongPress = false;
		if (hasPendingMessage())
		{
			mLongPressHandler.removeMessages(2);
			mCurrentDownEvent.recycle();
			mCurrentDownEvent = null;
		}
	}

	void cancelLongPressIfNeeded(MotionEvent motionevent)
	{
//		if (hasPendingMessage() && mCurrentDownEvent != null && motionevent.getDownTime() == mCurrentDownEvent.getDownTime()) goto _L2; else goto _L1
//_L1:
//		return;
//_L2:
//		int j;
//		int k;
//		int i = motionevent.getAction();
//		float f = motionevent.getY();
//		float f1 = motionevent.getX();
//		switch (i & 0xff)
//		{
//		default:
//			return;
//
//		case 1: // '\001'
//		case 3: // '\003'
//			if (mCurrentDownEvent.getDownTime() + (long)TAP_TIMEOUT + (long)LONGPRESS_TIMEOUT > motionevent.getEventTime())
//			{
//				cancelLongPress();
//				return;
//			}
//			break;
//
//		case 2: // '\002'
//			j = (int)(f1 - mCurrentDownEvent.getX());
//			k = (int)(f - mCurrentDownEvent.getY());
//			continue; /* Loop/switch isn't completed */
//		}
//		if (true) goto _L1; else goto _L3
//_L3:
//		if (j * j + k * k <= mTouchSlopSquare) goto _L1; else goto _L4
//_L4:
//		cancelLongPress();
//		return;
	}

	void cancelLongPressIfNeeded(Iterator iterator)
	{
//		if (mCurrentDownEvent != null) goto _L2; else goto _L1
//_L1:
//		return;
//_L2:
//		long l = mCurrentDownEvent.getDownTime();
//_L5:
//		if (!iterator.hasNext()) goto _L1; else goto _L3
//_L3:
//		MotionEvent motionevent = (MotionEvent)iterator.next();
//		if (motionevent.getDownTime() != l) goto _L1; else goto _L4
//_L4:
//		cancelLongPressIfNeeded(motionevent);
//		  goto _L5
	}

	boolean confirmOfferMoveEventToJavaScript(MotionEvent motionevent)
	{
		if (!mMoveConfirmed)
		{
			int i = Math.round(motionevent.getX()) - mTouchInitialX;
			int j = Math.round(motionevent.getY()) - mTouchInitialY;
			if (i * i + j * j >= mTouchSlopSquare)
				mMoveConfirmed = true;
		}
		return mMoveConfirmed;
	}

	boolean hasPendingMessage()
	{
		return mCurrentDownEvent != null;
	}

	boolean isInLongPress()
	{
		return mInLongPress;
	}

	void onOfferTouchEventToJavaScript(MotionEvent motionevent)
	{
		if (motionevent.getActionMasked() == 0)
		{
			mMoveConfirmed = false;
			mTouchInitialX = Math.round(motionevent.getX());
			mTouchInitialY = Math.round(motionevent.getY());
		}
	}

	void sendLongPressGestureForTest()
	{
		if (mCurrentDownEvent == null)
		{
			return;
		} else
		{
			dispatchLongPress();
			return;
		}
	}

	void startLongPressTimerIfNeeded(MotionEvent motionevent)
	{
		while (motionevent.getAction() != 0 || mCurrentDownEvent != null) 
			return;
		mCurrentDownEvent = MotionEvent.obtain(motionevent);
		mLongPressHandler.sendEmptyMessageAtTime(2, mCurrentDownEvent.getDownTime() + (long)TAP_TIMEOUT + (long)LONGPRESS_TIMEOUT);
		mInLongPress = false;
	}


}
