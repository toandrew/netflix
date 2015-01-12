// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.content.res.Resources;
import android.os.*;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import java.util.ArrayDeque;
import java.util.Deque;
import org.chromium.content.browser.third_party.GestureDetector;
import org.chromium.content.common.TraceEvent;

// Referenced classes of package org.chromium.content.browser:
//			LongPressDetector, SnapScrollController, ZoomManager, TouchPoint

class ContentViewGestureHandler
	implements LongPressDetector.LongPressDelegate
{
	public static interface MotionEventDelegate
	{

		public abstract boolean didUIStealScroll(float f, float f1);

		public abstract boolean hasFixedPageScale();

		public abstract void invokeZoomPicker();

		public abstract boolean sendGesture(int i, long l, int j, int k, boolean flag, Bundle bundle);

		public abstract boolean sendTouchEvent(long l, int i, TouchPoint atouchpoint[]);
	}

	private class TouchEventTimeoutHandler
		implements Runnable
	{

		static final boolean $assertionsDisabled = false;
		private static final int PENDING_ACK_CANCEL_EVENT = 2;
		private static final int PENDING_ACK_NONE = 0;
		private static final int PENDING_ACK_ORIGINAL_EVENT = 1;
		private static final int TOUCH_EVENT_TIMEOUT = 200;
		private long mEventTime;
		private Handler mHandler;
		private int mPendingAckState;
		private TouchPoint mTouchPoints[];
		final ContentViewGestureHandler this$0;

		public boolean confirmTouchEvent()
		{
label0:
			{
label1:
				{
					switch (mPendingAckState)
					{
					default:
						if (!$assertionsDisabled)
							throw new AssertionError("Never reached");
						break;

					case 1: // '\001'
						break label1;

					case 2: // '\002'
						break label0;

					case 0: // '\0'
						mHandler.removeCallbacks(this);
						mTouchPoints = null;
						break;
					}
					return false;
				}
				TraceEvent.instant("TouchEventTimeout:ConfirmOriginalEvent");
				mPendingAckState = 2;
				mMotionEventDelegate.sendTouchEvent(200L + mEventTime, TouchPoint.TOUCH_EVENT_TYPE_CANCEL, mTouchPoints);
				mTouchPoints = null;
				return true;
			}
			TraceEvent.instant("TouchEventTimeout:ConfirmCancelEvent");
			mPendingAckState = 0;
			drainAllPendingEventsUntilNextDown();
			return true;
		}

		public boolean hasScheduledTimeoutEventForTesting()
		{
			return mTouchPoints != null && mPendingAckState == 0;
		}

		public boolean hasTimeoutEvent()
		{
			return mPendingAckState != 0;
		}

		public void mockTimeout()
		{
			if (!$assertionsDisabled && hasTimeoutEvent())
			{
				throw new AssertionError();
			} else
			{
				mHandler.removeCallbacks(this);
				run();
				return;
			}
		}

		public void run()
		{
			TraceEvent.begin("TouchEventTimeout");
			MotionEvent motionevent;
			for (; !mPendingMotionEvents.isEmpty(); recycleEvent(motionevent))
			{
				motionevent = (MotionEvent)mPendingMotionEvents.removeFirst();
				processTouchEvent(motionevent);
			}

			mPendingAckState = 1;
			TraceEvent.end();
		}

		public void start(long l, TouchPoint atouchpoint[])
		{
			if (!$assertionsDisabled && mTouchPoints != null)
				throw new AssertionError();
			if (!$assertionsDisabled && mPendingAckState != 0)
			{
				throw new AssertionError();
			} else
			{
				mEventTime = l;
				mTouchPoints = atouchpoint;
				mHandler.postDelayed(this, 200L);
				return;
			}
		}

		static 
		{
			boolean flag;
			if (!org/chromium/content/browser/ContentViewGestureHandler.desiredAssertionStatus())
				flag = true;
			else
				flag = false;
			$assertionsDisabled = flag;
		}

		private TouchEventTimeoutHandler()
		{
			this$0 = ContentViewGestureHandler.this;
			super();
			mHandler = new Handler();
		}

	}


	static final boolean $assertionsDisabled = false;
	static final String DELTA = "Delta";
	static final String DISTANCE_X = "Distance X";
	static final String DISTANCE_Y = "Distance Y";
	static final int DOUBLE_TAP_DRAG_MODE_DETECTION_IN_PROGRESS = 1;
	static final int DOUBLE_TAP_DRAG_MODE_NONE = 0;
	static final int DOUBLE_TAP_DRAG_MODE_ZOOM = 2;
	private static final float DOUBLE_TAP_DRAG_ZOOM_SPEED = 0.005F;
	private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
	static final int EVENT_CONVERTED_TO_CANCEL = 1;
	static final int EVENT_FORWARDED_TO_NATIVE = 0;
	static final int EVENT_NOT_FORWARDED = 2;
	static final int GESTURE_DOUBLE_TAP = 1;
	static final int GESTURE_FLING_CANCEL = 10;
	static final int GESTURE_FLING_START = 9;
	static final int GESTURE_LONG_PRESS = 5;
	static final int GESTURE_LONG_TAP = 15;
	static final int GESTURE_PINCH_BEGIN = 11;
	static final int GESTURE_PINCH_BY = 12;
	static final int GESTURE_PINCH_END = 13;
	static final int GESTURE_SCROLL_BY = 7;
	static final int GESTURE_SCROLL_END = 8;
	static final int GESTURE_SCROLL_START = 6;
	static final int GESTURE_SHOW_PRESSED_STATE = 0;
	static final int GESTURE_SHOW_PRESS_CANCEL = 14;
	static final int GESTURE_SINGLE_TAP_CONFIRMED = 3;
	static final int GESTURE_SINGLE_TAP_UNCONFIRMED = 4;
	static final int GESTURE_SINGLE_TAP_UP = 2;
	static final int INPUT_EVENT_ACK_STATE_CONSUMED = 1;
	static final int INPUT_EVENT_ACK_STATE_NOT_CONSUMED = 2;
	static final int INPUT_EVENT_ACK_STATE_NO_CONSUMER_EXISTS = 3;
	static final int INPUT_EVENT_ACK_STATE_UNKNOWN = 0;
	static final String SHOW_PRESS = "ShowPress";
	private static final String TAG = "ContentViewGestureHandler";
	static final String VELOCITY_X = "Velocity X";
	static final String VELOCITY_Y = "Velocity Y";
	private float mAccumulatedScrollErrorX;
	private float mAccumulatedScrollErrorY;
	private MotionEvent mCurrentDownEvent;
	private int mDoubleTapDragMode;
	private float mDoubleTapDragZoomAnchorX;
	private float mDoubleTapDragZoomAnchorY;
	private float mDoubleTapY;
	private final Bundle mExtraParamBundle = new Bundle();
	private boolean mFlingMayBeActive;
	private GestureDetector mGestureDetector;
	private boolean mHasTouchHandlers;
	private boolean mIgnoreSingleTap;
	private final boolean mInputEventsDeliveredAtVSync;
	private boolean mJavaScriptIsConsumingGesture;
	private MotionEvent mLastCancelledEvent;
	private float mLastRawX;
	private float mLastRawY;
	private org.chromium.content.browser.third_party.GestureDetector.OnGestureListener mListener;
	private LongPressDetector mLongPressDetector;
	private final MotionEventDelegate mMotionEventDelegate;
	private boolean mNoTouchHandlerForGesture;
	private final Deque mPendingMotionEvents = new ArrayDeque();
	private boolean mPinchInProgress;
	private final float mPxToDp;
	private int mScaledTouchSlopSquare;
	private boolean mSeenFirstScrollEvent;
	private boolean mShowPressIsCalled;
	private int mSingleTapX;
	private int mSingleTapY;
	private SnapScrollController mSnapScrollController;
	private boolean mTouchCancelEventSent;
	private TouchEventTimeoutHandler mTouchEventTimeoutHandler;
	private boolean mTouchScrolling;
	private final ZoomManager mZoomManager;

	ContentViewGestureHandler(Context context, MotionEventDelegate motioneventdelegate, ZoomManager zoommanager, int i)
	{
		boolean flag = true;
		super();
		mHasTouchHandlers = false;
		mNoTouchHandlerForGesture = false;
		mJavaScriptIsConsumingGesture = false;
		mPinchInProgress = false;
		mTouchCancelEventSent = false;
		mLastCancelledEvent = null;
		mDoubleTapDragMode = 0;
		mLastRawX = 0.0F;
		mLastRawY = 0.0F;
		mAccumulatedScrollErrorX = 0.0F;
		mAccumulatedScrollErrorY = 0.0F;
		mTouchEventTimeoutHandler = new TouchEventTimeoutHandler();
		mLongPressDetector = new LongPressDetector(context, this);
		mMotionEventDelegate = motioneventdelegate;
		mZoomManager = zoommanager;
		mSnapScrollController = new SnapScrollController(context, mZoomManager);
		if (i != flag)
			flag = false;
		mInputEventsDeliveredAtVSync = flag;
		mPxToDp = 1.0F / context.getResources().getDisplayMetrics().density;
		initGestureDetectors(context);
	}

	private void drainAllPendingEventsUntilNextDown()
	{
		MotionEvent motionevent;
		for (motionevent = (MotionEvent)mPendingMotionEvents.peekFirst(); motionevent != null && motionevent.getActionMasked() != 0; motionevent = (MotionEvent)mPendingMotionEvents.peekFirst())
		{
			processTouchEvent(motionevent);
			mPendingMotionEvents.removeFirst();
			recycleEvent(motionevent);
		}

		if (motionevent == null)
		{
			return;
		} else
		{
			mNoTouchHandlerForGesture = false;
			trySendPendingEventsToNative();
			return;
		}
	}

	private void endTouchScrollIfNecessary(long l, boolean flag)
	{
		if (mTouchScrolling)
		{
			mTouchScrolling = false;
			if (flag)
			{
				sendGesture(8, l, 0, 0, null);
				return;
			}
		}
	}

	private void initGestureDetectors(Context context)
	{
		final int scaledTouchSlop;
		scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mScaledTouchSlopSquare = scaledTouchSlop * scaledTouchSlop;
		TraceEvent.begin();
		org.chromium.content.browser.third_party.GestureDetector.SimpleOnGestureListener simpleongesturelistener = new org.chromium.content.browser.third_party.GestureDetector.SimpleOnGestureListener() {

			final ContentViewGestureHandler this$0;
			final int val$scaledTouchSlop;

			private boolean isDistanceBetweenDownAndUpTooLong(float f, float f1)
			{
				double d = mLastRawX - f;
				double d1 = mLastRawY - f1;
				return d * d + d1 * d1 > (double)mScaledTouchSlopSquare;
			}

			public boolean onDoubleTapEvent(MotionEvent motionevent)
			{
				motionevent.getActionMasked();
				JVM INSTR tableswitch 0 3: default 36
			//			               0 50
			//			               1 380
			//			               2 94
			//			               3 413;
				   goto _L1 _L2 _L3 _L4 _L5
_L1:
				mDoubleTapY = motionevent.getY();
				return true;
_L2:
				sendShowPressCancelIfNecessary(motionevent);
				mDoubleTapDragZoomAnchorX = motionevent.getX();
				mDoubleTapDragZoomAnchorY = motionevent.getY();
				mDoubleTapDragMode = 1;
				continue; /* Loop/switch isn't completed */
_L4:
				if (mDoubleTapDragMode == 1)
				{
					float f1 = mDoubleTapDragZoomAnchorX - motionevent.getX();
					float f2 = mDoubleTapDragZoomAnchorY - motionevent.getY();
					if (f1 * f1 + f2 * f2 > (float)mScaledTouchSlopSquare)
					{
						sendGesture(6, motionevent.getEventTime(), (int)motionevent.getX(), (int)motionevent.getY(), null);
						pinchBegin(motionevent.getEventTime(), Math.round(mDoubleTapDragZoomAnchorX), Math.round(mDoubleTapDragZoomAnchorY));
						mDoubleTapDragMode = 2;
					}
				} else
				if (mDoubleTapDragMode == 2)
				{
					mExtraParamBundle.clear();
					sendGesture(7, motionevent.getEventTime(), (int)motionevent.getX(), (int)motionevent.getY(), mExtraParamBundle);
					float f = mDoubleTapY - motionevent.getY();
					ContentViewGestureHandler contentviewgesturehandler = ContentViewGestureHandler.this;
					long l = motionevent.getEventTime();
					int i = Math.round(mDoubleTapDragZoomAnchorX);
					int j = Math.round(mDoubleTapDragZoomAnchorY);
					double d;
					if (f < 0.0F)
						d = 0.99500000476837158D;
					else
						d = 1.0049999952316284D;
					contentviewgesturehandler.pinchBy(l, i, j, (float)Math.pow(d, Math.abs(f * mPxToDp)));
				}
				continue; /* Loop/switch isn't completed */
_L3:
				if (mDoubleTapDragMode != 2)
					sendMotionEventAsGesture(1, motionevent, null);
				endDoubleTapDragMode(motionevent);
				continue; /* Loop/switch isn't completed */
_L5:
				endDoubleTapDragMode(motionevent);
				if (true) goto _L1; else goto _L6
_L6:
			}

			public boolean onDown(MotionEvent motionevent)
			{
				mShowPressIsCalled = false;
				mIgnoreSingleTap = false;
				mTouchScrolling = false;
				mSeenFirstScrollEvent = false;
				mSnapScrollController.resetSnapScrollMode();
				mLastRawX = motionevent.getRawX();
				mLastRawY = motionevent.getRawY();
				mAccumulatedScrollErrorX = 0.0F;
				mAccumulatedScrollErrorY = 0.0F;
				return true;
			}

			public boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
			{
				if (mSnapScrollController.isSnappingScrolls())
					if (mSnapScrollController.isSnapHorizontal())
						f1 = 0.0F;
					else
						f = 0.0F;
				fling(motionevent.getEventTime(), (int)motionevent.getX(0), (int)motionevent.getY(0), (int)f, (int)f1);
				return true;
			}

			public void onLongPress(MotionEvent motionevent)
			{
				if (!mZoomManager.isScaleGestureDetectionInProgress() && mDoubleTapDragMode == 0)
				{
					sendShowPressCancelIfNecessary(motionevent);
					sendMotionEventAsGesture(5, motionevent, null);
				}
			}

			public boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
			{
				if (!mSeenFirstScrollEvent)
				{
					mSeenFirstScrollEvent = true;
					double d = Math.sqrt(f * f + f1 * f1);
					if (d > 0.001D)
					{
						double d1 = Math.max(0.0D, d - (double)scaledTouchSlop) / d;
						f = (float)(d1 * (double)f);
						f1 = (float)(d1 * (double)f1);
					}
				}
				mSnapScrollController.updateSnapScrollMode(f, f1);
				boolean flag;
				if (mSnapScrollController.isSnappingScrolls())
					if (mSnapScrollController.isSnapHorizontal())
						f1 = 0.0F;
					else
						f = 0.0F;
				flag = mMotionEventDelegate.didUIStealScroll(motionevent1.getRawX() - mLastRawX, motionevent1.getRawY() - mLastRawY);
				mLastRawX = motionevent1.getRawX();
				mLastRawY = motionevent1.getRawY();
				if (flag)
					return true;
				if (!mTouchScrolling)
				{
					sendShowPressCancelIfNecessary(motionevent);
					endFlingIfNecessary(motionevent1.getEventTime());
					if (sendMotionEventAsGesture(6, motionevent, null))
						mTouchScrolling = true;
				}
				int i = (int)motionevent1.getX();
				int j = (int)motionevent1.getY();
				int k = (int)(f + mAccumulatedScrollErrorX);
				int l = (int)(f1 + mAccumulatedScrollErrorY);
				mAccumulatedScrollErrorX = (f + mAccumulatedScrollErrorX) - (float)k;
				mAccumulatedScrollErrorY = (f1 + mAccumulatedScrollErrorY) - (float)l;
				mExtraParamBundle.clear();
				mExtraParamBundle.putInt("Distance X", k);
				mExtraParamBundle.putInt("Distance Y", l);
				if ((k | l) != 0)
					sendLastGestureForVSync(7, motionevent1.getEventTime(), i, j, mExtraParamBundle);
				mMotionEventDelegate.invokeZoomPicker();
				return true;
			}

			public void onShowPress(MotionEvent motionevent)
			{
				mShowPressIsCalled = true;
				sendMotionEventAsGesture(0, motionevent, null);
			}

			public boolean onSingleTapConfirmed(MotionEvent motionevent)
			{
				if (mLongPressDetector.isInLongPress() || mIgnoreSingleTap)
				{
					return true;
				} else
				{
					int i = (int)motionevent.getX();
					int j = (int)motionevent.getY();
					mExtraParamBundle.clear();
					mExtraParamBundle.putBoolean("ShowPress", mShowPressIsCalled);
					sendMotionEventAsGesture(3, motionevent, mExtraParamBundle);
					setClickXAndY(i, j);
					return true;
				}
			}

			public boolean onSingleTapUp(MotionEvent motionevent)
			{
				if (isDistanceBetweenDownAndUpTooLong(motionevent.getRawX(), motionevent.getRawY()))
				{
					mIgnoreSingleTap = true;
					return true;
				}
				if (!mIgnoreSingleTap && !mLongPressDetector.isInLongPress())
				{
					if (motionevent.getEventTime() - motionevent.getDownTime() > (long)ContentViewGestureHandler.DOUBLE_TAP_TIMEOUT)
					{
						float f2 = motionevent.getX();
						float f3 = motionevent.getY();
						if (sendMotionEventAsGesture(2, motionevent, null))
							mIgnoreSingleTap = true;
						setClickXAndY((int)f2, (int)f3);
						return true;
					}
					if (mMotionEventDelegate.hasFixedPageScale())
					{
						float f = motionevent.getX();
						float f1 = motionevent.getY();
						mExtraParamBundle.clear();
						mExtraParamBundle.putBoolean("ShowPress", mShowPressIsCalled);
						if (sendMotionEventAsGesture(3, motionevent, mExtraParamBundle))
							mIgnoreSingleTap = true;
						setClickXAndY((int)f, (int)f1);
					} else
					{
						sendMotionEventAsGesture(4, motionevent, null);
					}
				}
				return triggerLongTapIfNeeded(motionevent);
			}

			
			{
				this$0 = ContentViewGestureHandler.this;
				scaledTouchSlop = i;
				super();
			}
		};
		mListener = simpleongesturelistener;
		mGestureDetector = new GestureDetector(context, simpleongesturelistener);
		mGestureDetector.setIsLongpressEnabled(false);
		TraceEvent.end();
		return;
		Exception exception;
		exception;
		TraceEvent.end();
		throw exception;
	}

	private MotionEvent obtainActionCancelMotionEvent()
	{
		return MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 3, 0.0F, 0.0F, 0);
	}

	private boolean offerTouchEventToJavaScript(MotionEvent motionevent)
	{
		boolean flag;
		flag = true;
		mLongPressDetector.onOfferTouchEventToJavaScript(motionevent);
		if (mHasTouchHandlers && !mNoTouchHandlerForGesture) goto _L2; else goto _L1
_L1:
		flag = false;
_L4:
		return flag;
_L2:
		if (motionevent.getActionMasked() != 2)
			break; /* Loop/switch isn't completed */
		if (!mLongPressDetector.confirmOfferMoveEventToJavaScript(motionevent))
			continue; /* Loop/switch isn't completed */
		MotionEvent motionevent3 = (MotionEvent)mPendingMotionEvents.peekLast();
		if (motionevent3 != null && motionevent3 != mPendingMotionEvents.peekFirst() && motionevent3.getActionMasked() == 2 && motionevent3.getPointerCount() == motionevent.getPointerCount())
		{
			TraceEvent.instant("offerTouchEventToJavaScript:EventCoalesced", (new StringBuilder()).append("QueueSize = ").append(mPendingMotionEvents.size()).toString());
			android.view.MotionEvent.PointerCoords apointercoords[] = new android.view.MotionEvent.PointerCoords[motionevent.getPointerCount()];
			for (int j = 0; j < apointercoords.length; j++)
			{
				apointercoords[j] = new android.view.MotionEvent.PointerCoords();
				motionevent.getPointerCoords(j, apointercoords[j]);
			}

			motionevent3.addBatch(motionevent.getEventTime(), apointercoords, motionevent.getMetaState());
			return flag;
		}
		break; /* Loop/switch isn't completed */
		if (true) goto _L4; else goto _L3
_L3:
		if (mPendingMotionEvents.isEmpty())
		{
			MotionEvent motionevent2 = MotionEvent.obtain(motionevent);
			mPendingMotionEvents.add(motionevent2);
			int i = sendPendingEventToNative();
			if (i == 2)
				mPendingMotionEvents.remove(motionevent2);
			if (i == 2)
				return false;
		} else
		{
			TraceEvent.instant("offerTouchEventToJavaScript:EventQueued", (new StringBuilder()).append("QueueSize = ").append(mPendingMotionEvents.size()).toString());
			MotionEvent motionevent1 = MotionEvent.obtain(motionevent);
			mPendingMotionEvents.add(motionevent1);
			return flag;
		}
		if (true) goto _L4; else goto _L5
_L5:
	}

	private boolean processTouchEvent(MotionEvent motionevent)
	{
		int i = motionevent.getAction();
		boolean flag = false;
		if (i == 1)
		{
			boolean flag4 = mTouchScrolling;
			flag = false;
			if (flag4)
				flag = true;
		}
		mLongPressDetector.cancelLongPressIfNeeded(motionevent);
		mLongPressDetector.startLongPressTimerIfNeeded(motionevent);
		boolean flag1 = canHandle(motionevent);
		boolean flag2 = false;
		if (flag1)
		{
			flag2 = false | mGestureDetector.onTouchEvent(motionevent);
			if (motionevent.getAction() == 0)
				mCurrentDownEvent = MotionEvent.obtain(motionevent);
		}
		boolean flag3 = flag2 | mZoomManager.processTouchEvent(motionevent);
		if (flag && !flag3)
			endTouchScrollIfNecessary(motionevent.getEventTime(), true);
		return flag3;
	}

	private void recycleEvent(MotionEvent motionevent)
	{
		if (motionevent == mLastCancelledEvent)
			mLastCancelledEvent = null;
		motionevent.recycle();
	}

	private boolean sendGesture(int i, long l, int j, int k, Bundle bundle)
	{
		return mMotionEventDelegate.sendGesture(i, l, j, k, false, bundle);
	}

	private boolean sendLastGestureForVSync(int i, long l, int j, int k, Bundle bundle)
	{
		return mMotionEventDelegate.sendGesture(i, l, j, k, mInputEventsDeliveredAtVSync, bundle);
	}

	private boolean sendMotionEventAsGesture(int i, MotionEvent motionevent, Bundle bundle)
	{
		return mMotionEventDelegate.sendGesture(i, motionevent.getEventTime(), (int)motionevent.getX(), (int)motionevent.getY(), false, bundle);
	}

	private int sendPendingEventToNative()
	{
		MotionEvent motionevent = (MotionEvent)mPendingMotionEvents.peekFirst();
		if (motionevent != null) goto _L2; else goto _L1
_L1:
		if (!$assertionsDisabled)
			throw new AssertionError("Cannot send from an empty pending event queue");
		  goto _L3
_L2:
		if (!mTouchEventTimeoutHandler.hasTimeoutEvent()) goto _L4; else goto _L3
_L3:
		return 2;
_L4:
		TouchPoint atouchpoint[];
		int i;
		atouchpoint = new TouchPoint[motionevent.getPointerCount()];
		i = TouchPoint.createTouchPoints(motionevent, atouchpoint);
		if (i == -1) goto _L3; else goto _L5
_L5:
		if (mTouchScrolling || mPinchInProgress)
			continue; /* Loop/switch isn't completed */
		mTouchCancelEventSent = false;
		if (!mMotionEventDelegate.sendTouchEvent(motionevent.getEventTime(), i, atouchpoint)) goto _L3; else goto _L6
_L6:
		if (!mJavaScriptIsConsumingGesture && motionevent == mPendingMotionEvents.peekFirst() && motionevent.getAction() != 1 && motionevent.getAction() != 3)
			mTouchEventTimeoutHandler.start(motionevent.getEventTime(), atouchpoint);
		return 0;
		if (mTouchCancelEventSent) goto _L3; else goto _L7
_L7:
		mTouchCancelEventSent = true;
		MotionEvent motionevent1 = mLastCancelledEvent;
		mLastCancelledEvent = motionevent;
		if (mMotionEventDelegate.sendTouchEvent(motionevent.getEventTime(), TouchPoint.TOUCH_EVENT_TYPE_CANCEL, atouchpoint))
		{
			return 1;
		} else
		{
			mLastCancelledEvent = motionevent1;
			return 2;
		}
	}

	private void setClickXAndY(int i, int j)
	{
		mSingleTapX = i;
		mSingleTapY = j;
	}

	private void trySendPendingEventsToNative()
	{
		do
		{
			if (mPendingMotionEvents.isEmpty() || sendPendingEventToNative() != 2)
				return;
			MotionEvent motionevent = (MotionEvent)mPendingMotionEvents.removeFirst();
			if (!mJavaScriptIsConsumingGesture)
				processTouchEvent(motionevent);
			recycleEvent(motionevent);
		} while (true);
	}

	boolean canHandle(MotionEvent motionevent)
	{
		return motionevent.getAction() == 0 || mCurrentDownEvent != null && mCurrentDownEvent.getDownTime() == motionevent.getDownTime();
	}

	void cancelLongPress()
	{
		mLongPressDetector.cancelLongPress();
	}

	void confirmTouchEvent(int i)
	{
		MotionEvent motionevent;
		if (mTouchEventTimeoutHandler.confirmTouchEvent())
			return;
		if (mPendingMotionEvents.isEmpty())
		{
			Log.w("ContentViewGestureHandler", "confirmTouchEvent with Empty pending list!");
			return;
		}
		TraceEvent.begin("confirmTouchEvent");
		motionevent = (MotionEvent)mPendingMotionEvents.removeFirst();
		if (motionevent == mLastCancelledEvent)
		{
			i = 3;
			TraceEvent.instant("confirmTouchEvent:CanceledEvent");
		}
		i;
		JVM INSTR tableswitch 0 3: default 100
	//	               0 128
	//	               1 142
	//	               2 162
	//	               3 182;
		   goto _L1 _L2 _L3 _L4 _L5
_L1:
		mLongPressDetector.cancelLongPressIfNeeded(mPendingMotionEvents.iterator());
		recycleEvent(motionevent);
		TraceEvent.end("confirmTouchEvent");
		return;
_L2:
		if (!$assertionsDisabled)
			throw new AssertionError();
		continue; /* Loop/switch isn't completed */
_L3:
		mJavaScriptIsConsumingGesture = true;
		mZoomManager.passTouchEventThrough(motionevent);
		trySendPendingEventsToNative();
		continue; /* Loop/switch isn't completed */
_L4:
		if (!mJavaScriptIsConsumingGesture)
			processTouchEvent(motionevent);
		trySendPendingEventsToNative();
		continue; /* Loop/switch isn't completed */
_L5:
		mNoTouchHandlerForGesture = true;
		processTouchEvent(motionevent);
		drainAllPendingEventsUntilNextDown();
		if (true) goto _L1; else goto _L6
_L6:
	}

	void endDoubleTapDragMode(MotionEvent motionevent)
	{
		if (mDoubleTapDragMode == 2)
		{
			if (motionevent == null)
				motionevent = obtainActionCancelMotionEvent();
			pinchEnd(motionevent.getEventTime());
			sendGesture(8, motionevent.getEventTime(), (int)motionevent.getX(), (int)motionevent.getY(), null);
		}
		mDoubleTapDragMode = 0;
	}

	void endFlingIfNecessary(long l)
	{
		if (!mFlingMayBeActive)
		{
			return;
		} else
		{
			mFlingMayBeActive = false;
			sendGesture(10, l, 0, 0, null);
			return;
		}
	}

	void fling(long l, int i, int j, int k, int i1)
	{
		endFlingIfNecessary(l);
		if (!mTouchScrolling)
			sendGesture(6, l, i, j, null);
		endTouchScrollIfNecessary(l, false);
		mFlingMayBeActive = true;
		mExtraParamBundle.clear();
		mExtraParamBundle.putInt("Velocity X", k);
		mExtraParamBundle.putInt("Velocity Y", i1);
		sendGesture(9, l, i, j, mExtraParamBundle);
	}

	LongPressDetector getLongPressDetector()
	{
		return mLongPressDetector;
	}

	int getNumberOfPendingMotionEventsForTesting()
	{
		return mPendingMotionEvents.size();
	}

	public int getSingleTapX()
	{
		return mSingleTapX;
	}

	public int getSingleTapY()
	{
		return mSingleTapY;
	}

	boolean hasScheduledTouchTimeoutEventForTesting()
	{
		return mTouchEventTimeoutHandler.hasScheduledTimeoutEventForTesting();
	}

	void hasTouchEventHandlers(boolean flag)
	{
		mHasTouchHandlers = flag;
		if (!mHasTouchHandlers)
			mPendingMotionEvents.clear();
	}

	boolean isEventCancelledForTesting(MotionEvent motionevent)
	{
		return motionevent != null && motionevent == mLastCancelledEvent;
	}

	boolean isNativePinching()
	{
		return mPinchInProgress;
	}

	boolean isNativeScrolling()
	{
		return mTouchScrolling;
	}

	void mockTouchEventTimeout()
	{
		mTouchEventTimeoutHandler.mockTimeout();
	}

	public void onLongPress(MotionEvent motionevent)
	{
		mListener.onLongPress(motionevent);
	}

	boolean onTouchEvent(MotionEvent motionevent)
	{
		TraceEvent.begin("onTouchEvent");
		mLongPressDetector.cancelLongPressIfNeeded(motionevent);
		mSnapScrollController.setSnapScrollingMode(motionevent);
		if (motionevent.getActionMasked() != 0) goto _L2; else goto _L1
_L1:
		mNoTouchHandlerForGesture = false;
		mJavaScriptIsConsumingGesture = false;
		endFlingIfNecessary(motionevent.getEventTime());
_L4:
		boolean flag = offerTouchEventToJavaScript(motionevent);
		if (flag)
		{
			TraceEvent.end("onTouchEvent");
			return true;
		}
		break MISSING_BLOCK_LABEL_90;
_L2:
		if (motionevent.getActionMasked() != 5) goto _L4; else goto _L3
_L3:
		endDoubleTapDragMode(null);
		  goto _L4
		Exception exception;
		exception;
		TraceEvent.end("onTouchEvent");
		throw exception;
		boolean flag1 = processTouchEvent(motionevent);
		TraceEvent.end("onTouchEvent");
		return flag1;
	}

	MotionEvent peekFirstInPendingMotionEventsForTesting()
	{
		return (MotionEvent)mPendingMotionEvents.peekFirst();
	}

	void pinchBegin(long l, int i, int j)
	{
		sendGesture(11, l, i, j, null);
	}

	void pinchBy(long l, int i, int j, float f)
	{
		mExtraParamBundle.clear();
		mExtraParamBundle.putFloat("Delta", f);
		sendLastGestureForVSync(12, l, i, j, mExtraParamBundle);
		mPinchInProgress = true;
	}

	void pinchEnd(long l)
	{
		sendGesture(13, l, 0, 0, null);
		mPinchInProgress = false;
	}

	void resetGestureHandlers()
	{
		MotionEvent motionevent = obtainActionCancelMotionEvent();
		mGestureDetector.onTouchEvent(motionevent);
		motionevent.recycle();
		MotionEvent motionevent1 = obtainActionCancelMotionEvent();
		mZoomManager.processTouchEvent(motionevent1);
		motionevent1.recycle();
		mLongPressDetector.cancelLongPress();
	}

	void sendShowPressCancelIfNecessary(MotionEvent motionevent)
	{
		while (!mShowPressIsCalled || !sendMotionEventAsGesture(14, motionevent, null)) 
			return;
		mShowPressIsCalled = false;
	}

	void sendShowPressedStateGestureForTesting()
	{
		if (mCurrentDownEvent == null)
		{
			return;
		} else
		{
			mListener.onShowPress(mCurrentDownEvent);
			return;
		}
	}

	void setIgnoreSingleTap(boolean flag)
	{
		mIgnoreSingleTap = flag;
	}

	void setTestDependencies(LongPressDetector longpressdetector, GestureDetector gesturedetector, org.chromium.content.browser.third_party.GestureDetector.OnGestureListener ongesturelistener)
	{
		mLongPressDetector = longpressdetector;
		mGestureDetector = gesturedetector;
		mListener = ongesturelistener;
	}

	boolean triggerLongTapIfNeeded(MotionEvent motionevent)
	{
		if (mLongPressDetector.isInLongPress() && motionevent.getAction() == 1 && !mZoomManager.isScaleGestureDetectionInProgress())
		{
			sendShowPressCancelIfNecessary(motionevent);
			sendMotionEventAsGesture(15, motionevent, null);
			return true;
		} else
		{
			return false;
		}
	}

	static 
	{
		boolean flag;
		if (!org/chromium/content/browser/ContentViewGestureHandler.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}






/*
	static float access$1102(ContentViewGestureHandler contentviewgesturehandler, float f)
	{
		contentviewgesturehandler.mLastRawX = f;
		return f;
	}

*/



/*
	static float access$1202(ContentViewGestureHandler contentviewgesturehandler, float f)
	{
		contentviewgesturehandler.mLastRawY = f;
		return f;
	}

*/



/*
	static float access$1302(ContentViewGestureHandler contentviewgesturehandler, float f)
	{
		contentviewgesturehandler.mAccumulatedScrollErrorX = f;
		return f;
	}

*/



/*
	static float access$1402(ContentViewGestureHandler contentviewgesturehandler, float f)
	{
		contentviewgesturehandler.mAccumulatedScrollErrorY = f;
		return f;
	}

*/










/*
	static float access$2102(ContentViewGestureHandler contentviewgesturehandler, float f)
	{
		contentviewgesturehandler.mDoubleTapDragZoomAnchorX = f;
		return f;
	}

*/



/*
	static float access$2202(ContentViewGestureHandler contentviewgesturehandler, float f)
	{
		contentviewgesturehandler.mDoubleTapDragZoomAnchorY = f;
		return f;
	}

*/



/*
	static int access$2302(ContentViewGestureHandler contentviewgesturehandler, int i)
	{
		contentviewgesturehandler.mDoubleTapDragMode = i;
		return i;
	}

*/





/*
	static float access$2602(ContentViewGestureHandler contentviewgesturehandler, float f)
	{
		contentviewgesturehandler.mDoubleTapY = f;
		return f;
	}

*/







/*
	static boolean access$602(ContentViewGestureHandler contentviewgesturehandler, boolean flag)
	{
		contentviewgesturehandler.mShowPressIsCalled = flag;
		return flag;
	}

*/



/*
	static boolean access$702(ContentViewGestureHandler contentviewgesturehandler, boolean flag)
	{
		contentviewgesturehandler.mIgnoreSingleTap = flag;
		return flag;
	}

*/



/*
	static boolean access$802(ContentViewGestureHandler contentviewgesturehandler, boolean flag)
	{
		contentviewgesturehandler.mTouchScrolling = flag;
		return flag;
	}

*/



/*
	static boolean access$902(ContentViewGestureHandler contentviewgesturehandler, boolean flag)
	{
		contentviewgesturehandler.mSeenFirstScrollEvent = flag;
		return flag;
	}

*/
}
