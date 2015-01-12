// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

// Referenced classes of package org.chromium.content.browser.input:
//			DateDialogNormalizer

class DateTimePickerDialog extends AlertDialog
	implements android.content.DialogInterface.OnClickListener, android.widget.DatePicker.OnDateChangedListener, android.widget.TimePicker.OnTimeChangedListener
{
	public static interface OnDateTimeSetListener
	{

		public abstract void onDateTimeSet(DatePicker datepicker, TimePicker timepicker, int i, int j, int k, int l, int i1);
	}


	private static final String DAY = "day";
	private static final String HOUR = "hour";
	private static final String IS_24_HOUR = "is24hour";
	private static final String MINUTE = "minute";
	private static final String MONTH = "month";
	private static final String YEAR = "year";
	private final OnDateTimeSetListener mCallBack;
	private final DatePicker mDatePicker;
	private final long mMaxTimeMillis;
	private final long mMinTimeMillis;
	private final TimePicker mTimePicker;

	public DateTimePickerDialog(Context context, OnDateTimeSetListener ondatetimesetlistener, int i, int j, int k, int l, int i1, 
			boolean flag, long l1, long l2)
	{
		super(context, 0);
		mMinTimeMillis = l1;
		mMaxTimeMillis = l2;
		mCallBack = ondatetimesetlistener;
		setButton(-1, context.getText(org.chromium.content.R.string.date_picker_dialog_set), this);
		setButton(-2, context.getText(0x1040000), (android.content.DialogInterface.OnClickListener)null);
		setIcon(0);
		setTitle(context.getText(org.chromium.content.R.string.date_time_picker_dialog_title));
		View view = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(org.chromium.content.R.layout.date_time_picker_dialog, null);
		setView(view);
		mDatePicker = (DatePicker)view.findViewById(org.chromium.content.R.id.date_picker);
		DateDialogNormalizer.normalize(mDatePicker, this, i, j, k, l, i1, l1, l2);
		mTimePicker = (TimePicker)view.findViewById(org.chromium.content.R.id.time_picker);
		mTimePicker.setIs24HourView(Boolean.valueOf(flag));
		mTimePicker.setCurrentHour(Integer.valueOf(l));
		mTimePicker.setCurrentMinute(Integer.valueOf(i1));
		mTimePicker.setOnTimeChangedListener(this);
		onTimeChanged(mTimePicker, mTimePicker.getCurrentHour().intValue(), mTimePicker.getCurrentMinute().intValue());
	}

	private void tryNotifyDateTimeSet()
	{
		if (mCallBack != null)
		{
			mDatePicker.clearFocus();
			mCallBack.onDateTimeSet(mDatePicker, mTimePicker, mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth(), mTimePicker.getCurrentHour().intValue(), mTimePicker.getCurrentMinute().intValue());
		}
	}

	public void onClick(DialogInterface dialoginterface, int i)
	{
		tryNotifyDateTimeSet();
	}

	public void onDateChanged(DatePicker datepicker, int i, int j, int k)
	{
		if (mTimePicker != null)
			onTimeChanged(mTimePicker, mTimePicker.getCurrentHour().intValue(), mTimePicker.getCurrentMinute().intValue());
	}

	protected void onStop()
	{
		if (android.os.Build.VERSION.SDK_INT >= 16)
			tryNotifyDateTimeSet();
		super.onStop();
	}

	public void onTimeChanged(TimePicker timepicker, int i, int j)
	{
		Time time;
		time = new Time();
		time.set(0, mTimePicker.getCurrentMinute().intValue(), mTimePicker.getCurrentHour().intValue(), mDatePicker.getDayOfMonth(), mDatePicker.getMonth(), mDatePicker.getYear());
		if (time.toMillis(true) >= mMinTimeMillis) goto _L2; else goto _L1
_L1:
		time.set(mMinTimeMillis);
_L4:
		mTimePicker.setCurrentHour(Integer.valueOf(time.hour));
		mTimePicker.setCurrentMinute(Integer.valueOf(time.minute));
		return;
_L2:
		if (time.toMillis(true) > mMaxTimeMillis)
			time.set(mMaxTimeMillis);
		if (true) goto _L4; else goto _L3
_L3:
	}

	public void updateDateTime(int i, int j, int k, int l, int i1)
	{
		mDatePicker.updateDate(i, j, k);
		mTimePicker.setCurrentHour(Integer.valueOf(l));
		mTimePicker.setCurrentMinute(Integer.valueOf(i1));
	}
}
