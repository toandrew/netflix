// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import java.util.Calendar;
import java.util.List;

public abstract class TwoFieldDatePicker extends FrameLayout
{
	public static interface OnMonthOrWeekChangedListener
	{

		public abstract void onMonthOrWeekChanged(TwoFieldDatePicker twofielddatepicker, int i, int j);
	}


	private Calendar mCurrentDate;
	private Calendar mMaxDate;
	private Calendar mMinDate;
	private OnMonthOrWeekChangedListener mMonthOrWeekChangedListener;
	private NumberPicker mPositionInYearSpinner;
	private NumberPicker mYearSpinner;

	public TwoFieldDatePicker(Context context, long l, long l1)
	{
		super(context, null, 0x101035c);
		//((LayoutInflater)context.getSystemService("layout_inflater")).inflate(org.chromium.content.R.layout.two_field_date_picker, this, true);
		android.widget.NumberPicker.OnValueChangeListener onvaluechangelistener = new android.widget.NumberPicker.OnValueChangeListener() {

			public void onValueChange(NumberPicker numberpicker, int i, int j)
			{
//				int k;
//				int i1;
//				k = getYear();
//				i1 = getPositionInYear();
//				if (numberpicker != mPositionInYearSpinner) goto _L2; else goto _L1
//_L1:
//				i1 = j;
//				if (i != numberpicker.getMaxValue() || j != numberpicker.getMinValue()) goto _L4; else goto _L3
//_L3:
//				k++;
//_L6:
//				setCurrentDate(k, i1);
//				updateSpinners();
//				notifyDateChanged();
//				return;
//_L4:
//				if (i == numberpicker.getMinValue() && j == numberpicker.getMaxValue())
//					k--;
//				continue; /* Loop/switch isn't completed */
//_L2:
//				if (numberpicker == mYearSpinner)
//					k = j;
//				else
//					throw new IllegalArgumentException();
//				if (true) goto _L6; else goto _L5
//_L5:
			}
		};
		mCurrentDate = Calendar.getInstance();
		if (l >= l1)
		{
			mMinDate = Calendar.getInstance();
			mMinDate.set(0, 0, 1);
			mMaxDate = Calendar.getInstance();
			mMaxDate.set(9999, 0, 1);
		} else
		{
			mMinDate = createDateFromValue(l);
			mMaxDate = createDateFromValue(l1);
		}
		mPositionInYearSpinner = null;//(NumberPicker)findViewById(org.chromium.content.R.id.position_in_year);
		mPositionInYearSpinner.setOnLongPressUpdateInterval(200L);
		mPositionInYearSpinner.setOnValueChangedListener(onvaluechangelistener);
		mYearSpinner = null;//(NumberPicker)findViewById(org.chromium.content.R.id.year);
		mYearSpinner.setOnLongPressUpdateInterval(100L);
		mYearSpinner.setOnValueChangedListener(onvaluechangelistener);
	}

	protected abstract Calendar createDateFromValue(long l);

	public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityevent)
	{
		onPopulateAccessibilityEvent(accessibilityevent);
		return true;
	}

	protected Calendar getCurrentDate()
	{
		return mCurrentDate;
	}

	protected Calendar getMaxDate()
	{
		return mMaxDate;
	}

	protected abstract int getMaxPositionInYear();

	protected abstract int getMaxYear();

	protected Calendar getMinDate()
	{
		return mMinDate;
	}

	protected abstract int getMinPositionInYear();

	protected abstract int getMinYear();

	public abstract int getPositionInYear();

	protected NumberPicker getPositionInYearSpinner()
	{
		return mPositionInYearSpinner;
	}

	public int getYear()
	{
		return mCurrentDate.get(1);
	}

	protected NumberPicker getYearSpinner()
	{
		return mYearSpinner;
	}

	public void init(int i, int j, OnMonthOrWeekChangedListener onmonthorweekchangedlistener)
	{
		setCurrentDate(i, j);
		updateSpinners();
		mMonthOrWeekChangedListener = onmonthorweekchangedlistener;
	}

	public boolean isNewDate(int i, int j)
	{
		return getYear() != i || getPositionInYear() != j;
	}

	protected void notifyDateChanged()
	{
		sendAccessibilityEvent(4);
		if (mMonthOrWeekChangedListener != null)
			mMonthOrWeekChangedListener.onMonthOrWeekChanged(this, getYear(), getPositionInYear());
	}

	public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityevent)
	{
		super.onPopulateAccessibilityEvent(accessibilityevent);
		String s = DateUtils.formatDateTime(getContext(), mCurrentDate.getTimeInMillis(), 20);
		accessibilityevent.getText().add(s);
	}

	protected abstract void setCurrentDate(int i, int j);

	protected void setCurrentDate(Calendar calendar)
	{
		mCurrentDate = calendar;
	}

	public void updateDate(int i, int j)
	{
		if (!isNewDate(i, j))
		{
			return;
		} else
		{
			setCurrentDate(i, j);
			updateSpinners();
			notifyDateChanged();
			return;
		}
	}

	protected void updateSpinners()
	{
		mPositionInYearSpinner.setDisplayedValues(null);
		mPositionInYearSpinner.setMinValue(getMinPositionInYear());
		mPositionInYearSpinner.setMaxValue(getMaxPositionInYear());
		NumberPicker numberpicker = mPositionInYearSpinner;
		boolean flag;
		if (!mCurrentDate.equals(mMinDate) && !mCurrentDate.equals(mMaxDate))
			flag = true;
		else
			flag = false;
		numberpicker.setWrapSelectorWheel(flag);
		mYearSpinner.setMinValue(getMinYear());
		mYearSpinner.setMaxValue(getMaxYear());
		mYearSpinner.setWrapSelectorWheel(false);
		mYearSpinner.setValue(getYear());
		mPositionInYearSpinner.setValue(getPositionInYear());
	}


}
