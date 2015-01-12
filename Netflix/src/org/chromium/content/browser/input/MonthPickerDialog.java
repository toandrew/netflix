// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.content.Context;

// Referenced classes of package org.chromium.content.browser.input:
//			TwoFieldDatePickerDialog, MonthPicker, TwoFieldDatePicker

public class MonthPickerDialog extends TwoFieldDatePickerDialog
{

	public MonthPickerDialog(Context context, TwoFieldDatePickerDialog.OnValueSetListener onvaluesetlistener, int i, int j, long l, long l1)
	{
		super(context, onvaluesetlistener, i, j, l, l1);
//		setTitle(org.chromium.content.R.string.month_picker_dialog_title);
	}

	protected TwoFieldDatePicker createPicker(Context context, long l, long l1)
	{
		return new MonthPicker(context, l, l1);
	}

	public MonthPicker getMonthPicker()
	{
		return (MonthPicker)mPicker;
	}

	protected void tryNotifyDateSet()
	{
		if (mCallBack != null)
		{
			MonthPicker monthpicker = getMonthPicker();
			monthpicker.clearFocus();
			mCallBack.onValueSet(monthpicker.getYear(), monthpicker.getMonth());
		}
	}
}
