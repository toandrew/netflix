// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui.gfx;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DeviceDisplayInfo
{

	private final Context mAppContext;
	private final WindowManager mWinManager;

	private DeviceDisplayInfo(Context context)
	{
		mAppContext = context.getApplicationContext();
		mWinManager = (WindowManager)mAppContext.getSystemService("window");
	}

	public static DeviceDisplayInfo create(Context context)
	{
		return new DeviceDisplayInfo(context);
	}

	private Display getDisplay()
	{
		return mWinManager.getDefaultDisplay();
	}

	private DisplayMetrics getMetrics()
	{
		return mAppContext.getResources().getDisplayMetrics();
	}

	private int getPixelFormat()
	{
		if (android.os.Build.VERSION.SDK_INT < 17)
			return getDisplay().getPixelFormat();
		else
			return 1;
	}

	public int getBitsPerComponent()
	{
		byte byte0 = 5;
		switch (getPixelFormat())
		{
		case 5: // '\005'
		default:
			byte0 = 8;
			// fall through

		case 4: // '\004'
		case 6: // '\006'
			return byte0;

		case 7: // '\007'
			return 4;

		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
			return 8;

		case 11: // '\013'
			return 2;

		case 8: // '\b'
		case 9: // '\t'
		case 10: // '\n'
			return 0;
		}
	}

	public int getBitsPerPixel()
	{
		int i = getPixelFormat();
		PixelFormat pixelformat = new PixelFormat();
		PixelFormat.getPixelFormatInfo(i, pixelformat);
		return pixelformat.bitsPerPixel;
	}

	public double getDIPScale()
	{
		return (double)getMetrics().density;
	}

	public int getDisplayHeight()
	{
		return getMetrics().heightPixels;
	}

	public int getDisplayWidth()
	{
		return getMetrics().widthPixels;
	}
}
