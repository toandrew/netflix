// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.content.Context;
import android.content.res.Resources;
import android.widget.NumberPicker;
import java.util.Calendar;

// Referenced classes of package org.chromium.content.browser.input:
//			TwoFieldDatePicker

public class WeekPicker extends TwoFieldDatePicker
{

	public WeekPicker(Context context, long l, long l1)
	{
		super(context, l, l1);
		//getPositionInYearSpinner().setContentDescription(getResources().getString(org.chromium.content.R.string.accessibility_date_picker_week));
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(2);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTimeInMillis(System.currentTimeMillis());
		init(getISOWeekYearForDate(calendar), getWeekForDate(calendar), null);
	}

	private Calendar createDateFromWeek(int i, int j)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setFirstDayOfWeek(2);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.set(7, 2);
		calendar.set(1, i);
		calendar.set(3, j);
		return calendar;
	}

	public static int getISOWeekYearForDate(Calendar calendar)
	{
		int i = calendar.get(1);
		int j = calendar.get(2);
		int k = calendar.get(3);
		if (j == 0 && k > 51)
			i--;
		else
		if (j == 11 && k == 1)
			return i + 1;
		return i;
	}

	private int getNumberOfWeeks()
	{
		return createDateFromWeek(getYear(), 20).getActualMaximum(3);
	}

	public static int getWeekForDate(Calendar calendar)
	{
		return calendar.get(3);
	}

	protected Calendar createDateFromValue(long l)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setFirstDayOfWeek(2);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTimeInMillis(l);
		return calendar;
	}

	protected int getMaxPositionInYear()
	{
		if (getYear() == getISOWeekYearForDate(getMaxDate()))
			return getWeekForDate(getMaxDate());
		else
			return getNumberOfWeeks();
	}

	protected int getMaxYear()
	{
		return getISOWeekYearForDate(getMaxDate());
	}

	protected int getMinPositionInYear()
	{
		if (getYear() == getISOWeekYearForDate(getMinDate()))
			return getWeekForDate(getMinDate());
		else
			return 1;
	}

	protected int getMinYear()
	{
		return getISOWeekYearForDate(getMinDate());
	}

	public int getPositionInYear()
	{
		return getWeek();
	}

	public int getWeek()
	{
		return getWeekForDate(getCurrentDate());
	}

	public int getYear()
	{
		return getISOWeekYearForDate(getCurrentDate());
	}

	protected void setCurrentDate(int i, int j)
	{
		Calendar calendar = createDateFromWeek(i, j);
		if (calendar.before(getMinDate()))
		{
			setCurrentDate(getMinDate());
			return;
		}
		if (calendar.after(getMaxDate()))
		{
			setCurrentDate(getMaxDate());
			return;
		} else
		{
			setCurrentDate(calendar);
			return;
		}
	}
}
