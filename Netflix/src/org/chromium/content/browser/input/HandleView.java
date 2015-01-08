// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.*;
import android.widget.PopupWindow;

// Referenced classes of package org.chromium.content.browser.input:
//			CursorController, InsertionHandleController

public class HandleView extends View
{

	static final int CENTER = 1;
	private static final float FADE_DURATION = 200F;
	static final int LEFT = 0;
	private static final float LINE_OFFSET_Y_DIP = 5F;
	static final int RIGHT = 2;
	private static final int TEXT_VIEW_HANDLE_ATTRS[] = {
		0x10102c5, 0x10102c7, 0x10102c6
	};
	private float mAlpha;
	private final PopupWindow mContainer;
	private int mContainerPositionX;
	private int mContainerPositionY;
	private final CursorController mController;
	private float mDownPositionX;
	private float mDownPositionY;
	private Drawable mDrawable;
	private long mFadeStartTime;
	private float mHotspotX;
	private float mHotspotY;
	private boolean mIsDragging;
	private boolean mIsInsertionHandle;
	private int mLastParentX;
	private int mLastParentY;
	private int mLineOffsetY;
	private View mParent;
	private InsertionHandleController.PastePopupMenu mPastePopupWindow;
	private int mPositionX;
	private int mPositionY;
	private Drawable mSelectHandleCenter;
	private Drawable mSelectHandleLeft;
	private Drawable mSelectHandleRight;
	private final int mTempCoords[] = new int[2];
	private final Rect mTempRect = new Rect();
	private final int mTextSelectHandleLeftRes;
	private final int mTextSelectHandleRes;
	private final int mTextSelectHandleRightRes;
	private long mTouchTimer;
	private float mTouchToWindowOffsetX;
	private float mTouchToWindowOffsetY;

	HandleView(CursorController cursorcontroller, int i, View view)
	{
		super(view.getContext());
		mIsInsertionHandle = false;
		Context context = view.getContext();
		mParent = view;
		mController = cursorcontroller;
		mContainer = new PopupWindow(context, null, 0x10102c8);
		mContainer.setSplitTouchEnabled(true);
		mContainer.setClippingEnabled(false);
		TypedArray typedarray = context.obtainStyledAttributes(TEXT_VIEW_HANDLE_ATTRS);
		mTextSelectHandleLeftRes = typedarray.getResourceId(typedarray.getIndex(0), 0);
		mTextSelectHandleRes = typedarray.getResourceId(typedarray.getIndex(1), 0);
		mTextSelectHandleRightRes = typedarray.getResourceId(typedarray.getIndex(2), 0);
		typedarray.recycle();
		setOrientation(i);
		mLineOffsetY = (int)TypedValue.applyDimension(1, 5F, context.getResources().getDisplayMetrics());
		mAlpha = 1.0F;
	}

	private boolean isPositionVisible()
	{
		if (!mIsDragging)
		{
			Rect rect = mTempRect;
			rect.left = 0;
			rect.top = 0;
			rect.right = mParent.getWidth();
			rect.bottom = mParent.getHeight();
			ViewParent viewparent = mParent.getParent();
			if (viewparent == null || !viewparent.getChildVisibleRect(mParent, rect, null))
				return false;
			int ai[] = mTempCoords;
			mParent.getLocationInWindow(ai);
			int i = ai[0] + mPositionX + (int)mHotspotX;
			int j = ai[1] + mPositionY + (int)mHotspotY;
			if (i < rect.left || i > rect.right || j < rect.top || j > rect.bottom)
				return false;
		}
		return true;
	}

	private void updateAlpha()
	{
		if (mAlpha == 1.0F)
		{
			return;
		} else
		{
			mAlpha = Math.min(1.0F, (float)(System.currentTimeMillis() - mFadeStartTime) / 200F);
			mDrawable.setAlpha((int)(255F * mAlpha));
			invalidate();
			return;
		}
	}

	private void updatePosition(float f, float f1)
	{
		float f2 = (f - mTouchToWindowOffsetX) + mHotspotX;
		float f3 = ((f1 - mTouchToWindowOffsetY) + mHotspotY) - (float)mLineOffsetY;
		mController.updatePosition(this, Math.round(f2), Math.round(f3));
	}

	void beginFadeIn()
	{
		if (getVisibility() == 0)
		{
			return;
		} else
		{
			mAlpha = 0.0F;
			mFadeStartTime = System.currentTimeMillis();
			setVisibility(0);
			return;
		}
	}

	int getAdjustedPositionX()
	{
		return (int)((float)mPositionX + mHotspotX);
	}

	int getAdjustedPositionY()
	{
		return (int)((float)mPositionY + mHotspotY);
	}

	Drawable getDrawable()
	{
		return mDrawable;
	}

	int getLineAdjustedPositionY()
	{
		return (int)(((float)mPositionY + mHotspotY) - (float)mLineOffsetY);
	}

	int getPositionX()
	{
		return mPositionX;
	}

	int getPositionY()
	{
		return mPositionY;
	}

	void hide()
	{
		mIsDragging = false;
		mContainer.dismiss();
		if (mPastePopupWindow != null)
			mPastePopupWindow.hide();
	}

	boolean isDragging()
	{
		return mIsDragging;
	}

	boolean isShowing()
	{
		return mContainer.isShowing();
	}

	void moveTo(int i, int j)
	{
		mPositionX = i;
		mPositionY = j;
		if (isPositionVisible())
		{
			int ai[];
			if (mContainer.isShowing())
			{
				ai = mTempCoords;
				mParent.getLocationInWindow(ai);
				int k = ai[0] + mPositionX;
				int l = ai[1] + mPositionY;
				if (k != mContainerPositionX || l != mContainerPositionY)
				{
					mContainerPositionX = k;
					mContainerPositionY = l;
					mContainer.update(mContainerPositionX, mContainerPositionY, getRight() - getLeft(), getBottom() - getTop());
					if (mPastePopupWindow != null)
						mPastePopupWindow.hide();
				}
			} else
			{
				show();
				ai = null;
			}
			if (mIsDragging)
			{
				if (ai == null)
				{
					ai = mTempCoords;
					mParent.getLocationInWindow(ai);
				}
				if (ai[0] != mLastParentX || ai[1] != mLastParentY)
				{
					mTouchToWindowOffsetX = mTouchToWindowOffsetX + (float)(ai[0] - mLastParentX);
					mTouchToWindowOffsetY = mTouchToWindowOffsetY + (float)(ai[1] - mLastParentY);
					mLastParentX = ai[0];
					mLastParentY = ai[1];
				}
				if (mPastePopupWindow != null)
					mPastePopupWindow.hide();
			}
			return;
		} else
		{
			hide();
			return;
		}
	}

	protected void onDraw(Canvas canvas)
	{
		updateAlpha();
		mDrawable.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
		mDrawable.draw(canvas);
	}

	protected void onMeasure(int i, int j)
	{
		setMeasuredDimension(mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		motionevent.getActionMasked();
		JVM INSTR tableswitch 0 3: default 36
	//	               0 38
	//	               1 148
	//	               2 133
	//	               3 210;
		   goto _L1 _L2 _L3 _L4 _L5
_L1:
		return false;
_L2:
		mDownPositionX = motionevent.getRawX();
		mDownPositionY = motionevent.getRawY();
		mTouchToWindowOffsetX = mDownPositionX - (float)mPositionX;
		mTouchToWindowOffsetY = mDownPositionY - (float)mPositionY;
		int ai[] = mTempCoords;
		mParent.getLocationInWindow(ai);
		mLastParentX = ai[0];
		mLastParentY = ai[1];
		mIsDragging = true;
		mController.beforeStartUpdatingPosition(this);
		mTouchTimer = SystemClock.uptimeMillis();
_L7:
		return true;
_L4:
		updatePosition(motionevent.getRawX(), motionevent.getRawY());
		continue; /* Loop/switch isn't completed */
_L3:
		if (mIsInsertionHandle && SystemClock.uptimeMillis() - mTouchTimer < (long)ViewConfiguration.getTapTimeout())
			if (mPastePopupWindow != null && mPastePopupWindow.isShowing())
				mPastePopupWindow.hide();
			else
				showPastePopupWindow();
		mIsDragging = false;
		continue; /* Loop/switch isn't completed */
_L5:
		mIsDragging = false;
		if (true) goto _L7; else goto _L6
_L6:
	}

	void positionAt(int i, int j)
	{
		moveTo((int)((float)i - mHotspotX), (int)((float)j - mHotspotY));
	}

	void setOrientation(int i)
	{
		i;
		JVM INSTR tableswitch 0 2: default 28
	//	               0 90
	//	               1 28
	//	               2 144;
		   goto _L1 _L2 _L1 _L3
_L1:
		if (mSelectHandleCenter == null)
			mSelectHandleCenter = getContext().getResources().getDrawable(mTextSelectHandleRes);
		mDrawable = mSelectHandleCenter;
		mHotspotX = (float)mDrawable.getIntrinsicWidth() / 2.0F;
		mIsInsertionHandle = true;
_L5:
		mHotspotY = 0.0F;
		invalidate();
		return;
_L2:
		if (mSelectHandleLeft == null)
			mSelectHandleLeft = getContext().getResources().getDrawable(mTextSelectHandleLeftRes);
		mDrawable = mSelectHandleLeft;
		mHotspotX = (float)(3 * mDrawable.getIntrinsicWidth()) / 4F;
		continue; /* Loop/switch isn't completed */
_L3:
		if (mSelectHandleRight == null)
			mSelectHandleRight = getContext().getResources().getDrawable(mTextSelectHandleRightRes);
		mDrawable = mSelectHandleRight;
		mHotspotX = (float)mDrawable.getIntrinsicWidth() / 4F;
		if (true) goto _L5; else goto _L4
_L4:
	}

	void show()
	{
		if (!isPositionVisible())
		{
			hide();
		} else
		{
			mContainer.setContentView(this);
			int ai[] = mTempCoords;
			mParent.getLocationInWindow(ai);
			mContainerPositionX = ai[0] + mPositionX;
			mContainerPositionY = ai[1] + mPositionY;
			mContainer.showAtLocation(mParent, 0, mContainerPositionX, mContainerPositionY);
			if (mPastePopupWindow != null)
			{
				mPastePopupWindow.hide();
				return;
			}
		}
	}

	void showPastePopupWindow()
	{
		InsertionHandleController insertionhandlecontroller = (InsertionHandleController)mController;
		if (mIsInsertionHandle && insertionhandlecontroller.canPaste())
		{
			if (mPastePopupWindow == null)
			{
				insertionhandlecontroller.getClass();
				mPastePopupWindow = new InsertionHandleController.PastePopupMenu(insertionhandlecontroller);
			}
			mPastePopupWindow.show();
		}
	}

}
