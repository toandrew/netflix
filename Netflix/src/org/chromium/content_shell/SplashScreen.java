// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content_shell;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class SplashScreen extends FrameLayout
{

	private ImageView mImage;

	public SplashScreen(Context context)
	{
		super(context);
		mImage = new ImageView(context);
		setBackgroundColor(0xff000000);
		android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(-2, -2, 17);
		addView(mImage, layoutparams);
		mImage.setImageResource(R.drawable.netflix_splash_screen_1920x1080);
	}

	public void destroy()
	{
		removeView(mImage);
		mImage = null;
		setVisibility(8);
	}
}
