// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.content.Context;
import android.content.res.Resources;
import android.widget.NumberPicker;
import java.text.DateFormatSymbols;
import java.util.*;

// Referenced classes of package org.chromium.content.browser.input:
//			TwoFieldDatePicker

public class MonthPicker extends TwoFieldDatePicker
{

	private static final int MONTHS_NUMBER = 12;
	private String mShortMonths[];

	public MonthPicker(Context context, long l, long l1)
	{
		super(context, l, l1);
		getPositionInYearSpinner().setContentDescription(getResources().getString(org.chromium.content.R.string.accessibility_date_picker_month));
		mShortMonths = DateFormatSymbols.getInstance(Locale.getDefault()).getShortMonths();
		Calendar calendar = Calendar.getInstance();
		init(calendar.get(1), calendar.get(2), null);
	}

	protected Calendar createDateFromValue(long l)
	{
		int i = (int)Math.min(1970L + l / 12L, 0x7fffffffL);
		int j = (int)(l % 12L);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(i, j, 1);
		return calendar;
	}

	protected int getMaxPositionInYear()
	{
		if (getYear() == getMaxDate().get(1))
			return getMaxDate().get(2);
		else
			return 11;
	}

	protected int getMaxYear()
	{
		return getMaxDate().get(1);
	}

	protected int getMinPositionInYear()
	{
		if (getYear() == getMinDate().get(1))
			return getMinDate().get(2);
		else
			return 0;
	}

	protected int getMinYear()
	{
		return getMinDate().get(1);
	}

	public int getMonth()
	{
		return getCurrentDate().get(2);
	}

	public int getPositionInYear()
	{
		return getMonth();
	}

	protected void setCurrentDate(int i, int j)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(i, j, 1);
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

	protected void updateSpinners()
	{
		super.updateSpinners();
		String as[] = (String[])Arrays.copyOfRange(mShortMonths, getPositionInYearSpinner().getMinValue(), 1 + getPositionInYearSpinner().getMaxValue());
		getPositionInYearSpinner().setDisplayedValues(as);
	}
}
