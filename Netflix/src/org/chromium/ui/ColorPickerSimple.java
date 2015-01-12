// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

// Referenced classes of package org.chromium.ui:
//			OnColorChangedListener

public class ColorPickerSimple extends View
{

	private static final int COLORS[] = {
		0xffff0000, 0xff00ffff, 0xff0000ff, 0xff00ff00, -65281, -256, 0xff000000, -1
	};
	private static final int COLUMN_COUNT = 4;
	private static final int GRID_CELL_COUNT = 8;
	private static final int ROW_COUNT = 2;
	private Paint mBorderPaint;
	private Rect mBounds[];
	private int mLastTouchedXPosition;
	private int mLastTouchedYPosition;
	private OnColorChangedListener mOnColorTouchedListener;
	private Paint mPaints[];

	public ColorPickerSimple(Context context)
	{
		super(context);
	}

	public ColorPickerSimple(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
	}

	public ColorPickerSimple(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
	}

	private void calculateGrid(int i, int j)
	{
		mBounds = new Rect[8];
		for (int k = 0; k < 2; k++)
		{
			for (int l = 0; l < 4; l++)
			{
				int i1 = 1 + (l * (i + 1)) / 4;
				int j1 = -2 + ((l + 1) * (i + 1)) / 4;
				Rect rect = new Rect(i1, 1 + (k * (j + 1)) / 2, j1, -2 + ((k + 1) * (j + 1)) / 2);
				mBounds[l + k * 4] = rect;
			}

		}

	}

	public void init(OnColorChangedListener oncolorchangedlistener)
	{
		mOnColorTouchedListener = oncolorchangedlistener;
		mBounds = null;
		mPaints = new Paint[8];
		for (int i = 0; i < 8; i++)
		{
			Paint paint = new Paint();
			paint.setColor(COLORS[i]);
			mPaints[i] = paint;
		}

		mBorderPaint = new Paint();
		int j = 0;//getContext().getResources().getColor(R.color.color_picker_border_color);
		mBorderPaint.setColor(j);
		setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View view)
			{
				if (mOnColorTouchedListener != null && getWidth() > 0 && getHeight() > 0)
				{
					int k = (4 * mLastTouchedXPosition) / getWidth() + 4 * ((2 * mLastTouchedYPosition) / getHeight());
					if (k >= 0 && k < ColorPickerSimple.COLORS.length)
						mOnColorTouchedListener.onColorChanged(ColorPickerSimple.COLORS[k]);
				}
			}
		});
	}

	public void onDraw(Canvas canvas)
	{
		if (mBounds != null && mPaints != null)
		{
			canvas.drawColor(-1);
			for (int i = 0; i < 8; i++)
				canvas.drawRect(mBounds[i], mPaints[i]);

			for (int j = 0; j < 1; j++)
				canvas.drawLine(0.0F, 1 + mBounds[j * 4].bottom, getWidth(), 1 + mBounds[j * 4].bottom, mBorderPaint);

			int k = 0;
			while (k < 3) 
			{
				canvas.drawLine(1 + mBounds[k].right, 0.0F, 1 + mBounds[k].right, getHeight(), mBorderPaint);
				k++;
			}
		}
	}

	protected void onSizeChanged(int i, int j, int k, int l)
	{
		calculateGrid(i, j);
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		if (motionevent.getAction() == 0)
		{
			mLastTouchedXPosition = (int)motionevent.getX();
			mLastTouchedYPosition = (int)motionevent.getY();
		}
		return super.onTouchEvent(motionevent);
	}





}
