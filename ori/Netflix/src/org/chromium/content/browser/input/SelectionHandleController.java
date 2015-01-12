// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.view.View;

// Referenced classes of package org.chromium.content.browser.input:
//			CursorController, HandleView

public abstract class SelectionHandleController
	implements CursorController
{

	private static final int TEXT_DIRECTION_DEFAULT = 0;
	private static final int TEXT_DIRECTION_LTR = 1;
	private static final int TEXT_DIRECTION_RTL = 2;
	private boolean mAllowAutomaticShowing;
	private HandleView mEndHandle;
	private int mFixedHandleX;
	private int mFixedHandleY;
	private boolean mIsShowing;
	private View mParent;
	private HandleView mStartHandle;

	public SelectionHandleController(View view)
	{
		mAllowAutomaticShowing = true;
		mParent = view;
	}

	private void createHandlesIfNeeded(int i, int j)
	{
		if (mStartHandle == null)
		{
			byte byte0;
			if (i == 2)
				byte0 = 2;
			else
				byte0 = 0;
			mStartHandle = new HandleView(this, byte0, mParent);
		}
		if (mEndHandle == null)
		{
			int k = 0;
			if (j != 2)
				k = 2;
			mEndHandle = new HandleView(this, k, mParent);
		}
	}

	private void showHandlesIfNeeded()
	{
		if (!mIsShowing)
		{
			mIsShowing = true;
			mStartHandle.show();
			mEndHandle.show();
			setHandleVisibility(0);
		}
	}

	public void allowAutomaticShowing()
	{
		mAllowAutomaticShowing = true;
	}

	public void beforeStartUpdatingPosition(HandleView handleview)
	{
		HandleView handleview1;
		if (handleview == mStartHandle)
			handleview1 = mEndHandle;
		else
			handleview1 = mStartHandle;
		mFixedHandleX = handleview1.getAdjustedPositionX();
		mFixedHandleY = handleview1.getLineAdjustedPositionY();
	}

	public void beginHandleFadeIn()
	{
		mStartHandle.beginFadeIn();
		mEndHandle.beginFadeIn();
	}

	void cancelFadeOutAnimation()
	{
		hide();
	}

	public HandleView getEndHandleViewForTest()
	{
		return mEndHandle;
	}

	public HandleView getStartHandleViewForTest()
	{
		return mStartHandle;
	}

	public void hide()
	{
		if (mIsShowing)
		{
			if (mStartHandle != null)
				mStartHandle.hide();
			if (mEndHandle != null)
				mEndHandle.hide();
			mIsShowing = false;
		}
	}

	public void hideAndDisallowAutomaticShowing()
	{
		hide();
		mAllowAutomaticShowing = false;
	}

	public boolean isDragging()
	{
		return mStartHandle != null && mStartHandle.isDragging() || mEndHandle != null && mEndHandle.isDragging();
	}

	boolean isSelectionStartDragged()
	{
		return mStartHandle != null && mStartHandle.isDragging();
	}

	public boolean isShowing()
	{
		return mIsShowing;
	}

	public void onDetached()
	{
	}

	public void onSelectionChanged(int i, int j)
	{
		if (mAllowAutomaticShowing)
			showHandles(i, j);
	}

	public void onTouchModeChanged(boolean flag)
	{
		if (!flag)
			hide();
	}

	protected abstract void selectBetweenCoordinates(int i, int j, int k, int l);

	public void setEndHandlePosition(float f, float f1)
	{
		mEndHandle.positionAt((int)f, (int)f1);
	}

	public void setHandleVisibility(int i)
	{
		mStartHandle.setVisibility(i);
		mEndHandle.setVisibility(i);
	}

	public void setStartHandlePosition(float f, float f1)
	{
		mStartHandle.positionAt((int)f, (int)f1);
	}

	public void showHandles(int i, int j)
	{
		createHandlesIfNeeded(i, j);
		showHandlesIfNeeded();
	}

	public void updatePosition(HandleView handleview, int i, int j)
	{
		selectBetweenCoordinates(mFixedHandleX, mFixedHandleY, i, j);
	}
}
