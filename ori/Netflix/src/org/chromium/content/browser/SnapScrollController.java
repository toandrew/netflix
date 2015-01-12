// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

// Referenced classes of package org.chromium.content.browser:
//			ZoomManager

class SnapScrollController
{

	private static final int SNAP_BOUND = 16;
	private static final int SNAP_HORIZ = 1;
	private static final int SNAP_NONE = 0;
	private static final int SNAP_VERT = 2;
	private static final String TAG = "SnapScrollController";
	private float mChannelDistance;
	private float mDistanceX;
	private float mDistanceY;
	private int mFirstTouchX;
	private int mFirstTouchY;
	private int mSnapScrollMode;
	private ZoomManager mZoomManager;

	SnapScrollController(Context context, ZoomManager zoommanager)
	{
		mChannelDistance = 16F;
		mSnapScrollMode = 0;
		mFirstTouchX = -1;
		mFirstTouchY = -1;
		mDistanceX = 0.0F;
		mDistanceY = 0.0F;
		calculateChannelDistance(context);
		mZoomManager = zoommanager;
	}

	private void calculateChannelDistance(Context context)
	{
		DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
		double d = Math.hypot((double)displaymetrics.widthPixels / (double)displaymetrics.densityDpi, (double)displaymetrics.heightPixels / (double)displaymetrics.densityDpi);
		if (d < 3D)
			mChannelDistance = 16F;
		else
		if (d < 5D)
			mChannelDistance = 22F;
		else
		if (d < 7D)
			mChannelDistance = 28F;
		else
			mChannelDistance = 34F;
		mChannelDistance = mChannelDistance * displaymetrics.density;
		if (mChannelDistance < 16F)
			mChannelDistance = 16F;
	}

	boolean isSnapHorizontal()
	{
		return mSnapScrollMode == 1;
	}

	boolean isSnapVertical()
	{
		return mSnapScrollMode == 2;
	}

	boolean isSnappingScrolls()
	{
		return mSnapScrollMode != 0;
	}

	void resetSnapScrollMode()
	{
		mSnapScrollMode = 0;
	}

	void setSnapScrollingMode(MotionEvent motionevent)
	{
		motionevent.getAction();
		JVM INSTR tableswitch 0 3: default 36
	//	               0 45
	//	               1 152
	//	               2 69
	//	               3 152;
		   goto _L1 _L2 _L3 _L4 _L3
_L1:
		Log.i("SnapScrollController", "setSnapScrollingMode case-default no-op");
_L6:
		return;
_L2:
		mSnapScrollMode = 0;
		mFirstTouchX = (int)motionevent.getX();
		mFirstTouchY = (int)motionevent.getY();
		return;
_L4:
		if (mZoomManager.isScaleGestureDetectionInProgress() || mSnapScrollMode != 0) goto _L6; else goto _L5
_L5:
		int i;
		int j;
		i = (int)Math.abs(motionevent.getX() - (float)mFirstTouchX);
		j = (int)Math.abs(motionevent.getY() - (float)mFirstTouchY);
		if (i > 16 && j < 16)
		{
			mSnapScrollMode = 1;
			return;
		}
		if (i >= 16 || j <= 16) goto _L6; else goto _L7
_L7:
		mSnapScrollMode = 2;
		return;
_L3:
		mFirstTouchX = -1;
		mFirstTouchY = -1;
		mDistanceX = 0.0F;
		mDistanceY = 0.0F;
		return;
	}

	void updateSnapScrollMode(float f, float f1)
	{
		if (mSnapScrollMode != 1 && mSnapScrollMode != 2) goto _L2; else goto _L1
_L1:
		mDistanceX = mDistanceX + Math.abs(f);
		mDistanceY = mDistanceY + Math.abs(f1);
		if (mSnapScrollMode != 1) goto _L4; else goto _L3
_L3:
		if (mDistanceY <= mChannelDistance) goto _L6; else goto _L5
_L5:
		mSnapScrollMode = 0;
_L2:
		return;
_L6:
		if (mDistanceX > mChannelDistance)
		{
			mDistanceX = 0.0F;
			mDistanceY = 0.0F;
			return;
		}
		continue; /* Loop/switch isn't completed */
_L4:
		if (mDistanceX > mChannelDistance)
		{
			mSnapScrollMode = 0;
			return;
		}
		if (mDistanceY > mChannelDistance)
		{
			mDistanceX = 0.0F;
			mDistanceY = 0.0F;
			return;
		}
		if (true) goto _L2; else goto _L7
_L7:
	}
}
