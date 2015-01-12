// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.Time;
import android.widget.TimePicker;

public class TimeDialog extends TimePickerDialog
{

	private Time mMaxTime;
	private Time mMinTime;

	private TimeDialog(Context context, android.app.TimePickerDialog.OnTimeSetListener ontimesetlistener, int i, int j, boolean flag, long l, 
			long l1)
	{
		super(context, ontimesetlistener, i, j, flag);
		if (l >= l1)
		{
			mMinTime = getTimeForHourAndMinute(0, 0);
			mMaxTime = getTimeForHourAndMinute(23, 59);
			return;
		} else
		{
			mMinTime = getTimeForMillis(l);
			mMaxTime = getTimeForMillis(l1);
			return;
		}
	}

	public static TimeDialog create(Context context, android.app.TimePickerDialog.OnTimeSetListener ontimesetlistener, int i, int j, boolean flag, long l, long l1)
	{
		Time time = getBoundedTime(i, j, l, l1);
		return new TimeDialog(context, ontimesetlistener, time.hour, time.minute, flag, l, l1);
	}

	private static Time getBoundedTime(int i, int j, long l, long l1)
	{
		Time time = getTimeForHourAndMinute(i, j);
		if (time.toMillis(true) < l)
			time = getTimeForMillis(l);
		else
		if (time.toMillis(true) > l1)
			return getTimeForMillis(l1);
		return time;
	}

	private static Time getTimeForHourAndMinute(int i, int j)
	{
		Time time = new Time("GMT");
		time.set(0, j, i, 1, 0, 1970);
		return time;
	}

	private static Time getTimeForMillis(long l)
	{
		Time time = new Time("GMT");
		time.set(l);
		return time;
	}

	public void onTimeChanged(TimePicker timepicker, int i, int j)
	{
		Time time = getBoundedTime(i, j, mMinTime.toMillis(true), mMaxTime.toMillis(true));
		super.onTimeChanged(timepicker, time.hour, time.minute);
		updateTime(time.hour, time.minute);
	}
}
