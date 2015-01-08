// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

class PopupZoomer extends View
{
	public static interface OnTapListener
	{

		public abstract boolean onLongPress(View view, MotionEvent motionevent);

		public abstract boolean onSingleTap(View view, MotionEvent motionevent);
	}

	public static interface OnVisibilityChangedListener
	{

		public abstract void onPopupZoomerHidden(PopupZoomer popupzoomer);

		public abstract void onPopupZoomerShown(PopupZoomer popupzoomer);
	}

	private static class ReverseInterpolator
		implements Interpolator
	{

		private final Interpolator mInterpolator;

		public float getInterpolation(float f)
		{
			float f1 = 1.0F - f;
			if (mInterpolator == null)
				return f1;
			else
				return mInterpolator.getInterpolation(f1);
		}

		public ReverseInterpolator(Interpolator interpolator)
		{
			mInterpolator = interpolator;
		}
	}


	private static final long ANIMATION_DURATION = 300L;
	private static String LOGTAG = "PopupZoomer";
	private static final int ZOOM_BOUNDS_MARGIN = 25;
	private static float sOverlayCornerRadius;
	private static Drawable sOverlayDrawable;
	private static Rect sOverlayPadding;
	private boolean mAnimating;
	private long mAnimationStartTime;
	private float mBottomExtrusion;
	private RectF mClipRect;
	private GestureDetector mGestureDetector;
	private final Interpolator mHideInterpolator;
	private float mLeftExtrusion;
	private float mMaxScrollX;
	private float mMaxScrollY;
	private float mMinScrollX;
	private float mMinScrollY;
	private boolean mNeedsToInitDimensions;
	private OnTapListener mOnTapListener;
	private OnVisibilityChangedListener mOnVisibilityChangedListener;
	private float mPopupScrollX;
	private float mPopupScrollY;
	private float mRightExtrusion;
	private float mScale;
	private float mShiftX;
	private float mShiftY;
	private final Interpolator mShowInterpolator = new OvershootInterpolator();
	private boolean mShowing;
	private Rect mTargetBounds;
	private long mTimeLeft;
	private float mTopExtrusion;
	private final PointF mTouch = new PointF();
	private RectF mViewClipRect;
	private Bitmap mZoomedBitmap;

	public PopupZoomer(Context context)
	{
		super(context);
		mOnTapListener = null;
		mOnVisibilityChangedListener = null;
		mHideInterpolator = new ReverseInterpolator(mShowInterpolator);
		mAnimating = false;
		mShowing = false;
		mAnimationStartTime = 0L;
		mTimeLeft = 0L;
		mShiftX = 0.0F;
		mShiftY = 0.0F;
		mScale = 1.0F;
		setVisibility(4);
		setFocusable(true);
		setFocusableInTouchMode(true);
		mGestureDetector = new GestureDetector(context, new android.view.GestureDetector.SimpleOnGestureListener() {

			final PopupZoomer this$0;

			private boolean handleTapOrPress(MotionEvent motionevent, boolean flag)
			{
				if (!mAnimating)
				{
					float f = motionevent.getX();
					float f1 = motionevent.getY();
					if (isTouchOutsideArea(f, f1))
					{
						hide(true);
						return true;
					}
					if (mOnTapListener != null)
					{
						PointF pointf = convertTouchPoint(f, f1);
						MotionEvent motionevent1 = MotionEvent.obtainNoHistory(motionevent);
						motionevent1.setLocation(pointf.x, pointf.y);
						if (flag)
							mOnTapListener.onLongPress(PopupZoomer.this, motionevent1);
						else
							mOnTapListener.onSingleTap(PopupZoomer.this, motionevent1);
						hide(true);
						return true;
					}
				}
				return true;
			}

			public void onLongPress(MotionEvent motionevent)
			{
				handleTapOrPress(motionevent, true);
			}

			public boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
			{
				if (mAnimating)
					return true;
				if (isTouchOutsideArea(motionevent.getX(), motionevent.getY()))
				{
					hide(true);
					return true;
				} else
				{
					scroll(f, f1);
					return true;
				}
			}

			public boolean onSingleTapUp(MotionEvent motionevent)
			{
				return handleTapOrPress(motionevent, false);
			}

			
			{
				this$0 = PopupZoomer.this;
				super();
			}
		});
	}

	private static float constrain(float f, float f1, float f2)
	{
		if (f < f1)
			return f1;
		if (f > f2)
			return f2;
		else
			return f;
	}

	private static int constrain(int i, int j, int k)
	{
		if (i < j)
			return j;
		if (i > k)
			return k;
		else
			return i;
	}

	private PointF convertTouchPoint(float f, float f1)
	{
		float f2 = f - mShiftX;
		float f3 = f1 - mShiftY;
		return new PointF(mTouch.x + (f2 - mTouch.x - mPopupScrollX) / mScale, mTouch.y + (f3 - mTouch.y - mPopupScrollY) / mScale);
	}

	private static float getOverlayCornerRadius(Context context)
	{
		if (sOverlayCornerRadius == 0.0F)
			try
			{
				sOverlayCornerRadius = context.getResources().getDimension(org.chromium.content.R.dimen.link_preview_overlay_radius);
			}
			catch (android.content.res.Resources.NotFoundException notfoundexception)
			{
				Log.w(LOGTAG, "No corner radius resource for PopupZoomer overlay found.");
				sOverlayCornerRadius = 1.0F;
			}
		return sOverlayCornerRadius;
	}

	private static Drawable getOverlayDrawable(Context context)
	{
		if (sOverlayDrawable == null)
		{
			try
			{
				sOverlayDrawable = context.getResources().getDrawable(org.chromium.content.R.drawable.ondemand_overlay);
			}
			catch (android.content.res.Resources.NotFoundException notfoundexception)
			{
				Log.w(LOGTAG, "No drawable resource for PopupZoomer overlay found.");
				sOverlayDrawable = new ColorDrawable();
			}
			sOverlayPadding = new Rect();
			sOverlayDrawable.getPadding(sOverlayPadding);
		}
		return sOverlayDrawable;
	}

	private void hideImmediately()
	{
		mAnimating = false;
		mShowing = false;
		mTimeLeft = 0L;
		if (mOnVisibilityChangedListener != null)
			mOnVisibilityChangedListener.onPopupZoomerHidden(this);
		setVisibility(4);
		mZoomedBitmap.recycle();
		mZoomedBitmap = null;
	}

	private void initDimensions()
	{
		if (mTargetBounds == null || mTouch == null)
			return;
		mScale = (float)mZoomedBitmap.getWidth() / (float)mTargetBounds.width();
		float f = mTouch.x - mScale * (mTouch.x - (float)mTargetBounds.left);
		float f1 = mTouch.y - mScale * (mTouch.y - (float)mTargetBounds.top);
		mClipRect = new RectF(f, f1, f + (float)mZoomedBitmap.getWidth(), f1 + (float)mZoomedBitmap.getHeight());
		int i = getWidth();
		int j = getHeight();
		mViewClipRect = new RectF(25F, 25F, i - 25, j - 25);
		mShiftX = 0.0F;
		mShiftY = 0.0F;
		float f2;
		float f3;
		float f4;
		float f5;
		if (mClipRect.left < 25F)
		{
			mShiftX = 25F - mClipRect.left;
			RectF rectf6 = mClipRect;
			rectf6.left = rectf6.left + mShiftX;
			RectF rectf7 = mClipRect;
			rectf7.right = rectf7.right + mShiftX;
		} else
		if (mClipRect.right > (float)(i - 25))
		{
			mShiftX = (float)(i - 25) - mClipRect.right;
			RectF rectf = mClipRect;
			rectf.right = rectf.right + mShiftX;
			RectF rectf1 = mClipRect;
			rectf1.left = rectf1.left + mShiftX;
		}
		if (mClipRect.top < 25F)
		{
			mShiftY = 25F - mClipRect.top;
			RectF rectf4 = mClipRect;
			rectf4.top = rectf4.top + mShiftY;
			RectF rectf5 = mClipRect;
			rectf5.bottom = rectf5.bottom + mShiftY;
		} else
		if (mClipRect.bottom > (float)(j - 25))
		{
			mShiftY = (float)(j - 25) - mClipRect.bottom;
			RectF rectf2 = mClipRect;
			rectf2.bottom = rectf2.bottom + mShiftY;
			RectF rectf3 = mClipRect;
			rectf3.top = rectf3.top + mShiftY;
		}
		mMaxScrollY = 0.0F;
		mMinScrollY = 0.0F;
		mMaxScrollX = 0.0F;
		mMinScrollX = 0.0F;
		if (mViewClipRect.right + mShiftX < mClipRect.right)
			mMinScrollX = mViewClipRect.right - mClipRect.right;
		if (mViewClipRect.left + mShiftX > mClipRect.left)
			mMaxScrollX = mViewClipRect.left - mClipRect.left;
		if (mViewClipRect.top + mShiftY > mClipRect.top)
			mMaxScrollY = mViewClipRect.top - mClipRect.top;
		if (mViewClipRect.bottom + mShiftY < mClipRect.bottom)
			mMinScrollY = mViewClipRect.bottom - mClipRect.bottom;
		mClipRect.intersect(mViewClipRect);
		mLeftExtrusion = mTouch.x - mClipRect.left;
		mRightExtrusion = mClipRect.right - mTouch.x;
		mTopExtrusion = mTouch.y - mClipRect.top;
		mBottomExtrusion = mClipRect.bottom - mTouch.y;
		f2 = 0.5F + (mTouch.x - (float)mTargetBounds.centerX()) / ((float)mTargetBounds.width() / 2.0F);
		f3 = 0.5F + (mTouch.y - (float)mTargetBounds.centerY()) / ((float)mTargetBounds.height() / 2.0F);
		f4 = mMaxScrollX - mMinScrollX;
		f5 = mMaxScrollY - mMinScrollY;
		mPopupScrollX = -1F * (f4 * f2);
		mPopupScrollY = -1F * (f5 * f3);
		mPopupScrollX = constrain(mPopupScrollX, mMinScrollX, mMaxScrollX);
		mPopupScrollY = constrain(mPopupScrollY, mMinScrollY, mMaxScrollY);
	}

	private boolean isTouchOutsideArea(float f, float f1)
	{
		return !mClipRect.contains(f, f1);
	}

	private void scroll(float f, float f1)
	{
		mPopupScrollX = constrain(mPopupScrollX - f, mMinScrollX, mMaxScrollX);
		mPopupScrollY = constrain(mPopupScrollY - f1, mMinScrollY, mMaxScrollY);
		invalidate();
	}

	private void setTargetBounds(Rect rect)
	{
		mTargetBounds = rect;
	}

	private void startAnimation(boolean flag)
	{
		mAnimating = true;
		mShowing = flag;
		mTimeLeft = 0L;
		if (!flag) goto _L2; else goto _L1
_L1:
		setVisibility(0);
		mNeedsToInitDimensions = true;
		if (mOnVisibilityChangedListener != null)
			mOnVisibilityChangedListener.onPopupZoomerShown(this);
_L4:
		mAnimationStartTime = SystemClock.uptimeMillis();
		invalidate();
		return;
_L2:
		mTimeLeft = (300L + mAnimationStartTime) - SystemClock.uptimeMillis();
		if (mTimeLeft < 0L)
			mTimeLeft = 0L;
		if (true) goto _L4; else goto _L3
_L3:
	}

	protected boolean acceptZeroSizeView()
	{
		return false;
	}

	public void hide(boolean flag)
	{
		if (!mShowing)
			return;
		if (flag)
		{
			startAnimation(false);
			return;
		} else
		{
			hideImmediately();
			return;
		}
	}

	public boolean isShowing()
	{
		return mShowing || mAnimating;
	}

	protected void onDraw(Canvas canvas)
	{
		while (!isShowing() || mZoomedBitmap == null || !acceptZeroSizeView() && (getWidth() == 0 || getHeight() == 0)) 
			return;
		if (mNeedsToInitDimensions)
		{
			mNeedsToInitDimensions = false;
			initDimensions();
		}
		canvas.save();
		float f = constrain((float)((SystemClock.uptimeMillis() - mAnimationStartTime) + mTimeLeft) / 300F, 0.0F, 1.0F);
		if (f >= 1.0F)
		{
			mAnimating = false;
			if (!isShowing())
			{
				hideImmediately();
				return;
			}
		} else
		{
			invalidate();
		}
		float f1;
		float f2;
		float f3;
		float f4;
		RectF rectf;
		Drawable drawable;
		if (mShowing)
			f1 = mShowInterpolator.getInterpolation(f);
		else
			f1 = mHideInterpolator.getInterpolation(f);
		canvas.drawARGB((int)(80F * f1), 0, 0, 0);
		canvas.save();
		f2 = (f1 * (mScale - 1.0F)) / mScale + 1.0F / mScale;
		f3 = (-mShiftX * (1.0F - f1)) / mScale;
		f4 = (-mShiftY * (1.0F - f1)) / mScale;
		rectf = new RectF();
		rectf.left = f3 + (mTouch.x - f2 * mLeftExtrusion);
		rectf.top = f4 + (mTouch.y - f2 * mTopExtrusion);
		rectf.right = f3 + (mTouch.x + f2 * mRightExtrusion);
		rectf.bottom = f4 + (mTouch.y + f2 * mBottomExtrusion);
		canvas.clipRect(rectf);
		canvas.scale(f2, f2, rectf.left, rectf.top);
		canvas.translate(mPopupScrollX, mPopupScrollY);
		canvas.drawBitmap(mZoomedBitmap, rectf.left, rectf.top, null);
		canvas.restore();
		drawable = getOverlayDrawable(getContext());
		drawable.setBounds((int)rectf.left - sOverlayPadding.left, (int)rectf.top - sOverlayPadding.top, (int)rectf.right + sOverlayPadding.right, (int)rectf.bottom + sOverlayPadding.bottom);
		drawable.setAlpha(constrain((int)(255F * f1), 0, 255));
		drawable.draw(canvas);
		canvas.restore();
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		mGestureDetector.onTouchEvent(motionevent);
		return true;
	}

	public void setBitmap(Bitmap bitmap)
	{
		if (mZoomedBitmap != null)
		{
			mZoomedBitmap.recycle();
			mZoomedBitmap = null;
		}
		mZoomedBitmap = bitmap;
		Canvas canvas = new Canvas(mZoomedBitmap);
		Path path = new Path();
		RectF rectf = new RectF(0.0F, 0.0F, canvas.getWidth(), canvas.getHeight());
		float f = getOverlayCornerRadius(getContext());
		path.addRoundRect(rectf, f, f, android.graphics.Path.Direction.CCW);
		canvas.clipPath(path, android.graphics.Region.Op.XOR);
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC));
		paint.setColor(0);
		canvas.drawPaint(paint);
	}

	public void setLastTouch(float f, float f1)
	{
		mTouch.x = f;
		mTouch.y = f1;
	}

	public void setOnTapListener(OnTapListener ontaplistener)
	{
		mOnTapListener = ontaplistener;
	}

	public void setOnVisibilityChangedListener(OnVisibilityChangedListener onvisibilitychangedlistener)
	{
		mOnVisibilityChangedListener = onvisibilitychangedlistener;
	}

	public void show(Rect rect)
	{
		if (mShowing || mZoomedBitmap == null)
		{
			return;
		} else
		{
			setTargetBounds(rect);
			startAnimation(true);
			return;
		}
	}






}
