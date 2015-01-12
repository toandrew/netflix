// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

// Referenced classes of package org.chromium.content.browser:
//			ContentViewCore, ContentViewGestureHandler

class ZoomManager
{
	private class ScaleGestureListener
		implements android.view.ScaleGestureDetector.OnScaleGestureListener
	{

		private boolean mPermanentlyIgnoreDetectorEvents;
		private boolean mPinchEventSent;
		private boolean mTemporarilyIgnoreDetectorEvents;

		private boolean ignoreDetectorEvents()
		{
			return mPermanentlyIgnoreDetectorEvents || mTemporarilyIgnoreDetectorEvents || !mContentViewCore.isAlive();
		}

		boolean getPermanentlyIgnoreDetectorEvents()
		{
			return mPermanentlyIgnoreDetectorEvents;
		}

		public boolean onScale(ScaleGestureDetector scalegesturedetector)
		{
			if (ignoreDetectorEvents())
				return false;
			if (!mPinchEventSent)
			{
				mContentViewCore.getContentViewGestureHandler().pinchBegin(scalegesturedetector.getEventTime(), (int)scalegesturedetector.getFocusX(), (int)scalegesturedetector.getFocusY());
				mPinchEventSent = true;
			}
			mContentViewCore.getContentViewGestureHandler().pinchBy(scalegesturedetector.getEventTime(), (int)scalegesturedetector.getFocusX(), (int)scalegesturedetector.getFocusY(), scalegesturedetector.getScaleFactor());
			return true;
		}

		public boolean onScaleBegin(ScaleGestureDetector scalegesturedetector)
		{
			if (ignoreDetectorEvents())
			{
				return false;
			} else
			{
				mPinchEventSent = false;
				mContentViewCore.getContentViewGestureHandler().setIgnoreSingleTap(true);
				return true;
			}
		}

		public void onScaleEnd(ScaleGestureDetector scalegesturedetector)
		{
			if (!mPinchEventSent || !mContentViewCore.isAlive())
			{
				return;
			} else
			{
				mContentViewCore.getContentViewGestureHandler().pinchEnd(scalegesturedetector.getEventTime());
				mPinchEventSent = false;
				return;
			}
		}

		void setPermanentlyIgnoreDetectorEvents(boolean flag)
		{
			mPermanentlyIgnoreDetectorEvents = flag;
		}

		void setTemporarilyIgnoreDetectorEvents(boolean flag)
		{
			mTemporarilyIgnoreDetectorEvents = flag;
		}

		private ScaleGestureListener()
		{
			mPermanentlyIgnoreDetectorEvents = false;
			mTemporarilyIgnoreDetectorEvents = false;
		}

	}


	static final boolean $assertionsDisabled = false;
	private static final String TAG = "ContentViewZoom";
	private ContentViewCore mContentViewCore;
	private ScaleGestureDetector mMultiTouchDetector;
	private ScaleGestureListener mMultiTouchListener;

	ZoomManager(Context context, ContentViewCore contentviewcore)
	{
		mContentViewCore = contentviewcore;
		mMultiTouchListener = new ScaleGestureListener();
		mMultiTouchDetector = new ScaleGestureDetector(context, mMultiTouchListener);
	}

	boolean isScaleGestureDetectionInProgress()
	{
		return !mMultiTouchListener.getPermanentlyIgnoreDetectorEvents() && mMultiTouchDetector.isInProgress();
	}

	void passTouchEventThrough(MotionEvent motionevent)
	{
		mMultiTouchListener.setTemporarilyIgnoreDetectorEvents(true);
		mMultiTouchDetector.onTouchEvent(motionevent);
//_L1:
//		return;
//		Exception exception;
//		exception;
//		Log.e("ContentViewZoom", "ScaleGestureDetector got into a bad state!", exception);
//		if (!$assertionsDisabled)
//			throw new AssertionError();
//		  goto _L1
	}

	boolean processTouchEvent(MotionEvent motionevent)
	{
		mMultiTouchListener.setTemporarilyIgnoreDetectorEvents(false);
		boolean flag;
		boolean flag1;
		int i;
		try
		{
			flag = isScaleGestureDetectionInProgress();
			flag1 = mMultiTouchDetector.onTouchEvent(motionevent);
			i = motionevent.getActionMasked();
		}
		catch (Exception exception)
		{
			Log.e("ContentViewZoom", "ScaleGestureDetector got into a bad state!", exception);
			if (!$assertionsDisabled)
				throw new AssertionError();
			else
				return false;
		}
		if (i == 1 && !flag)
			flag1 = false;
		return flag1;
	}

	void updateMultiTouchSupport(boolean flag)
	{
		ScaleGestureListener scalegesturelistener = mMultiTouchListener;
		boolean flag1;
		if (!flag)
			flag1 = true;
		else
			flag1 = false;
		scalegesturelistener.setPermanentlyIgnoreDetectorEvents(flag1);
	}

	static 
	{
//		boolean flag;
//		if (!org/chromium/content/browser/ZoomManager.desiredAssertionStatus())
//			flag = true;
//		else
//			flag = false;
		//$assertionsDisabled = false;
	}

}
