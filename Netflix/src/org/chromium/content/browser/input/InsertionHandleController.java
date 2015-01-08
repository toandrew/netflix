// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

// Referenced classes of package org.chromium.content.browser.input:
//			CursorController, HandleView

public abstract class InsertionHandleController
	implements CursorController
{
	class PastePopupMenu
		implements android.view.View.OnClickListener
	{

		private final PopupWindow mContainer;
		private int mPasteViewLayouts[];
		private View mPasteViews[];
		private int mPositionX;
		private int mPositionY;
		final InsertionHandleController this$0;

		private void updateContent(boolean flag)
		{
			int i = viewIndex(flag);
			View view = mPasteViews[i];
			if (view == null)
			{
				int j = mPasteViewLayouts[i];
				LayoutInflater layoutinflater = (LayoutInflater)mContext.getSystemService("layout_inflater");
				if (layoutinflater != null)
					view = layoutinflater.inflate(j, null);
				if (view == null)
					throw new IllegalArgumentException("Unable to inflate TextEdit paste window");
				int k = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
				view.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
				view.measure(k, k);
				view.setOnClickListener(this);
				mPasteViews[i] = view;
			}
			mContainer.setContentView(view);
		}

		private int viewIndex(boolean flag)
		{
			int i;
			boolean flag1;
			int j;
			if (flag)
				i = 0;
			else
				i = 2;
			flag1 = canPaste();
			j = 0;
			if (!flag1)
				j = 1;
			return i + j;
		}

		void hide()
		{
			mContainer.dismiss();
		}

		boolean isShowing()
		{
			return mContainer.isShowing();
		}

		public void onClick(View view)
		{
			if (canPaste())
				paste();
			hide();
		}

		void positionAtCursor()
		{
			View view = mContainer.getContentView();
			int i = view.getMeasuredWidth();
			int j = view.getMeasuredHeight();
			int k = getLineHeight();
			mPositionX = (int)((float)mHandle.getAdjustedPositionX() - (float)i / 2.0F);
			mPositionY = mHandle.getAdjustedPositionY() - j - k;
			int ai[] = new int[2];
			mParent.getLocationInWindow(ai);
			ai[0] = ai[0] + mPositionX;
			ai[1] = ai[1] + mPositionY;
			int l = mContext.getResources().getDisplayMetrics().widthPixels;
			if (ai[1] < 0)
			{
				updateContent(false);
				View view1 = mContainer.getContentView();
				int i1 = view1.getMeasuredWidth();
				ai[1] = view1.getMeasuredHeight() + ai[1];
				ai[1] = k + ai[1];
				int j1 = mHandle.getDrawable().getIntrinsicWidth() / 2;
				if (i1 + mHandle.getAdjustedPositionX() < l)
					ai[0] = ai[0] + (j1 + i1 / 2);
				else
					ai[0] = ai[0] - (j1 + i1 / 2);
			} else
			{
				ai[0] = Math.max(0, ai[0]);
				ai[0] = Math.min(l - i, ai[0]);
			}
			mContainer.showAtLocation(mParent, 0, ai[0], ai[1]);
		}

		void show()
		{
			updateContent(true);
			positionAtCursor();
		}

		public PastePopupMenu()
		{
			this$0 = InsertionHandleController.this;
			super();
			mContainer = new PopupWindow(mContext, null, 0x10102c8);
			mContainer.setSplitTouchEnabled(true);
			mContainer.setClippingEnabled(false);
			mContainer.setWidth(-2);
			mContainer.setHeight(-2);
			int ai[] = {
				0x1010314, 0x1010315, 0x101035e, 0x101035f
			};
			mPasteViews = new View[ai.length];
			mPasteViewLayouts = new int[ai.length];
			TypedArray typedarray = mContext.obtainStyledAttributes(ai);
			for (int i = 0; i < typedarray.length(); i++)
				mPasteViewLayouts[i] = typedarray.getResourceId(typedarray.getIndex(i), 0);

			typedarray.recycle();
		}
	}


	private boolean mAllowAutomaticShowing;
	private Context mContext;
	private HandleView mHandle;
	private boolean mIsShowing;
	private View mParent;

	public InsertionHandleController(View view)
	{
		mParent = view;
		mContext = view.getContext();
	}

	private void createHandleIfNeeded()
	{
		if (mHandle == null)
			mHandle = new HandleView(this, 1, mParent);
	}

	private void showHandleIfNeeded()
	{
		if (!mIsShowing)
		{
			mIsShowing = true;
			mHandle.show();
			setHandleVisibility(0);
		}
	}

	public void allowAutomaticShowing()
	{
		mAllowAutomaticShowing = true;
	}

	public void beforeStartUpdatingPosition(HandleView handleview)
	{
	}

	public void beginHandleFadeIn()
	{
		mHandle.beginFadeIn();
	}

	boolean canPaste()
	{
		return ((ClipboardManager)mContext.getSystemService("clipboard")).hasPrimaryClip();
	}

	public HandleView getHandleViewForTest()
	{
		return mHandle;
	}

	int getHandleX()
	{
		return mHandle.getAdjustedPositionX();
	}

	int getHandleY()
	{
		return mHandle.getAdjustedPositionY();
	}

	protected abstract int getLineHeight();

	public void hide()
	{
		if (mIsShowing)
		{
			if (mHandle != null)
				mHandle.hide();
			mIsShowing = false;
		}
	}

	public void hideAndDisallowAutomaticShowing()
	{
		hide();
		mAllowAutomaticShowing = false;
	}

	public boolean isShowing()
	{
		return mIsShowing;
	}

	public void onCursorPositionChanged()
	{
		if (mAllowAutomaticShowing)
			showHandle();
	}

	public void onDetached()
	{
	}

	public void onTouchModeChanged(boolean flag)
	{
		if (!flag)
			hide();
	}

	protected abstract void paste();

	protected abstract void setCursorPosition(int i, int j);

	public void setHandlePosition(float f, float f1)
	{
		mHandle.positionAt((int)f, (int)f1);
	}

	public void setHandleVisibility(int i)
	{
		mHandle.setVisibility(i);
	}

	public void showHandle()
	{
		createHandleIfNeeded();
		showHandleIfNeeded();
	}

	public void showHandleWithPastePopup()
	{
		showHandle();
		showPastePopup();
	}

	void showPastePopup()
	{
		if (mIsShowing)
			mHandle.showPastePopupWindow();
	}

	public void updatePosition(HandleView handleview, int i, int j)
	{
		setCursorPosition(i, j);
	}



}
