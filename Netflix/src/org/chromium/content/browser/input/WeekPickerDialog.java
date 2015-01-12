// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.content.Context;

// Referenced classes of package org.chromium.content.browser.input:
//			TwoFieldDatePickerDialog, WeekPicker, TwoFieldDatePicker

public class WeekPickerDialog extends TwoFieldDatePickerDialog
{

	public WeekPickerDialog(Context context, int i, TwoFieldDatePickerDialog.OnValueSetListener onvaluesetlistener, int j, int k, long l, 
			long l1)
	{
		super(context, i, onvaluesetlistener, j, k, l, l1);
		//setTitle(org.chromium.content.R.string.week_picker_dialog_title);
	}

	public WeekPickerDialog(Context context, TwoFieldDatePickerDialog.OnValueSetListener onvaluesetlistener, int i, int j, long l, long l1)
	{
		this(context, 0, onvaluesetlistener, i, j, l, l1);
	}

	protected TwoFieldDatePicker createPicker(Context context, long l, long l1)
	{
		return new WeekPicker(context, l, l1);
	}

	public WeekPicker getWeekPicker()
	{
		return (WeekPicker)mPicker;
	}

	protected void tryNotifyDateSet()
	{
		if (mCallBack != null)
		{
			WeekPicker weekpicker = getWeekPicker();
			weekpicker.clearFocus();
			mCallBack.onValueSet(weekpicker.getYear(), weekpicker.getWeek());
		}
	}
}
