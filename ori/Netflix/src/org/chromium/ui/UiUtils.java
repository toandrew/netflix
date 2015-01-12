// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

public class UiUtils
{

	private static float KEYBOARD_DETECT_BOTTOM_THRESHOLD_DP = 100F;

	private UiUtils()
	{
	}

	public static boolean hideKeyboard(View view)
	{
		return ((InputMethodManager)view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static int insertAfter(ViewGroup viewgroup, View view, View view1)
	{
		return insertView(viewgroup, view, view1, true);
	}

	public static int insertBefore(ViewGroup viewgroup, View view, View view1)
	{
		return insertView(viewgroup, view, view1, false);
	}

	private static int insertView(ViewGroup viewgroup, View view, View view1, boolean flag)
	{
		int i = viewgroup.indexOfChild(view);
		if (i >= 0)
			return i;
		int j = viewgroup.indexOfChild(view1);
		if (j < 0)
			return -1;
		if (flag)
			j++;
		viewgroup.addView(view, j);
		return j;
	}

	public static boolean isKeyboardShowing(Context context, View view)
	{
		View view1 = view.getRootView();
		if (view1 != null)
		{
			Rect rect = new Rect();
			view1.getWindowVisibleDisplayFrame(rect);
			float f = context.getResources().getDisplayMetrics().heightPixels;
			if (Math.abs((float)rect.bottom - f) > context.getResources().getDisplayMetrics().density * KEYBOARD_DETECT_BOTTOM_THRESHOLD_DP)
				return true;
		}
		return false;
	}

	public static void showKeyboard(View view)
	{
		((InputMethodManager)view.getContext().getSystemService("input_method")).showSoftInput(view, 1);
	}

}
