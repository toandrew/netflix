// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

public class ColorPickerMoreButton extends Button
{

	private Paint mBorderPaint;

	public ColorPickerMoreButton(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		init();
	}

	public ColorPickerMoreButton(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
		init();
	}

	public void init()
	{
		mBorderPaint = new Paint();
		mBorderPaint.setStyle(android.graphics.Paint.Style.STROKE);
		mBorderPaint.setColor(-1);
		mBorderPaint.setStrokeWidth(1.0F);
		mBorderPaint.setAntiAlias(false);
	}

	protected void onDraw(Canvas canvas)
	{
		canvas.drawRect(0.5F, 0.5F, (float)getWidth() - 1.5F, (float)getHeight() - 1.5F, mBorderPaint);
		super.onDraw(canvas);
	}
}
