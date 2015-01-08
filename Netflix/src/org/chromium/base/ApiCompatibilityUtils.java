// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.app.Notification;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewTreeObserver;

public class ApiCompatibilityUtils
{

	private ApiCompatibilityUtils()
	{
	}

	public static Notification buildNotification(android.app.Notification.Builder builder)
	{
		if (android.os.Build.VERSION.SDK_INT >= 16)
			return builder.build();
		else
			return builder.getNotification();
	}

	public static int getMarginStart(android.view.ViewGroup.MarginLayoutParams marginlayoutparams)
	{
		if (android.os.Build.VERSION.SDK_INT >= 17)
			return marginlayoutparams.getMarginStart();
		else
			return marginlayoutparams.leftMargin;
	}

	public static boolean isLayoutRtl(View view)
	{
		if (android.os.Build.VERSION.SDK_INT >= 17)
			return view.getLayoutDirection() == 1;
		else
			return false;
	}

	public static void postInvalidateOnAnimation(View view)
	{
		if (android.os.Build.VERSION.SDK_INT >= 16)
		{
			view.postInvalidateOnAnimation();
			return;
		} else
		{
			view.postInvalidate();
			return;
		}
	}

	public static void removeOnGlobalLayoutListener(View view, android.view.ViewTreeObserver.OnGlobalLayoutListener ongloballayoutlistener)
	{
		if (android.os.Build.VERSION.SDK_INT >= 16)
		{
			view.getViewTreeObserver().removeOnGlobalLayoutListener(ongloballayoutlistener);
			return;
		} else
		{
			view.getViewTreeObserver().removeGlobalOnLayoutListener(ongloballayoutlistener);
			return;
		}
	}

	public static void setBackgroundForView(View view, Drawable drawable)
	{
		if (android.os.Build.VERSION.SDK_INT >= 16)
		{
			view.setBackground(drawable);
			return;
		} else
		{
			view.setBackgroundDrawable(drawable);
			return;
		}
	}

	public static void setLayoutDirection(View view, int i)
	{
		if (android.os.Build.VERSION.SDK_INT >= 17)
			view.setLayoutDirection(i);
	}

	public static void setMarginEnd(android.view.ViewGroup.MarginLayoutParams marginlayoutparams, int i)
	{
		if (android.os.Build.VERSION.SDK_INT >= 17)
		{
			marginlayoutparams.setMarginEnd(i);
			return;
		} else
		{
			marginlayoutparams.rightMargin = i;
			return;
		}
	}

	public static void setMarginStart(android.view.ViewGroup.MarginLayoutParams marginlayoutparams, int i)
	{
		if (android.os.Build.VERSION.SDK_INT >= 17)
		{
			marginlayoutparams.setMarginStart(i);
			return;
		} else
		{
			marginlayoutparams.leftMargin = i;
			return;
		}
	}
}
