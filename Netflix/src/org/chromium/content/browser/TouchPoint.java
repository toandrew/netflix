// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.util.Log;
import android.view.MotionEvent;

class TouchPoint
{

	public static final int CONVERSION_ERROR = -1;
	static int TOUCH_EVENT_TYPE_CANCEL;
	static int TOUCH_EVENT_TYPE_END;
	static int TOUCH_EVENT_TYPE_MOVE;
	static int TOUCH_EVENT_TYPE_START;
	private static int TOUCH_POINT_STATE_CANCELLED;
	private static int TOUCH_POINT_STATE_MOVED;
	private static int TOUCH_POINT_STATE_PRESSED;
	private static int TOUCH_POINT_STATE_RELEASED;
	private static int TOUCH_POINT_STATE_STATIONARY;
	private static int TOUCH_POINT_STATE_UNDEFINED;
	private final int mId;
	private final float mPressure;
	private final float mSize;
	private final int mState;
	private final float mX;
	private final float mY;

	TouchPoint(int i, int j, float f, float f1, float f2, float f3)
	{
		mState = i;
		mId = j;
		mX = f;
		mY = f1;
		mSize = f2;
		mPressure = f3;
	}

	public static int createTouchPoints(MotionEvent motionevent, TouchPoint atouchpoint[])
	{
		motionevent.getActionMasked();
		JVM INSTR tableswitch 0 6: default 48
	//	               0 80
	//	               1 190
	//	               2 179
	//	               3 201
	//	               4 48
	//	               5 212
	//	               6 212;
		   goto _L1 _L2 _L3 _L4 _L5 _L1 _L6 _L6
_L1:
		int i;
		Log.e("Chromium", (new StringBuilder()).append("Unknown motion event action: ").append(motionevent.getActionMasked()).toString());
		i = -1;
_L7:
		return i;
_L2:
		int j;
		i = TOUCH_EVENT_TYPE_START;
		j = TOUCH_POINT_STATE_PRESSED;
_L8:
		int k = 0;
		while (k < atouchpoint.length) 
		{
			int l = j;
			if (j == TOUCH_POINT_STATE_STATIONARY && motionevent.getActionIndex() == k)
				if (motionevent.getActionMasked() == 5)
					l = TOUCH_POINT_STATE_PRESSED;
				else
					l = TOUCH_POINT_STATE_RELEASED;
			atouchpoint[k] = new TouchPoint(l, motionevent.getPointerId(k), motionevent.getX(k), motionevent.getY(k), motionevent.getSize(k), motionevent.getPressure(k));
			k++;
		}
		if (true) goto _L7; else goto _L4
_L4:
		i = TOUCH_EVENT_TYPE_MOVE;
		j = TOUCH_POINT_STATE_MOVED;
		  goto _L8
_L3:
		i = TOUCH_EVENT_TYPE_END;
		j = TOUCH_POINT_STATE_RELEASED;
		  goto _L8
_L5:
		i = TOUCH_EVENT_TYPE_CANCEL;
		j = TOUCH_POINT_STATE_CANCELLED;
		  goto _L8
_L6:
		i = TOUCH_EVENT_TYPE_MOVE;
		j = TOUCH_POINT_STATE_STATIONARY;
		  goto _L8
	}

	private static void initializeConstants(int i, int j, int k, int l, int i1, int j1, int k1, int l1, 
			int i2, int j2)
	{
		TOUCH_EVENT_TYPE_START = i;
		TOUCH_EVENT_TYPE_MOVE = j;
		TOUCH_EVENT_TYPE_END = k;
		TOUCH_EVENT_TYPE_CANCEL = l;
		TOUCH_POINT_STATE_UNDEFINED = i1;
		TOUCH_POINT_STATE_RELEASED = j1;
		TOUCH_POINT_STATE_PRESSED = k1;
		TOUCH_POINT_STATE_MOVED = l1;
		TOUCH_POINT_STATE_STATIONARY = i2;
		TOUCH_POINT_STATE_CANCELLED = j2;
	}

	static void initializeConstantsForTesting()
	{
		if (TOUCH_EVENT_TYPE_START == TOUCH_EVENT_TYPE_MOVE)
			initializeConstants(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	}

	public int getId()
	{
		return mId;
	}

	public double getPressure()
	{
		return (double)mPressure;
	}

	public double getSize()
	{
		return (double)mSize;
	}

	public int getState()
	{
		return mState;
	}

	public int getX()
	{
		return (int)mX;
	}

	public int getY()
	{
		return (int)mY;
	}
}
