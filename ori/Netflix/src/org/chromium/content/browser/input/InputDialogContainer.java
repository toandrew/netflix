// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.util.Calendar;

// Referenced classes of package org.chromium.content.browser.input:
//			DateDialogNormalizer, TimeDialog, DateTimePickerDialog, MonthPickerDialog, 
//			WeekPicker, WeekPickerDialog

public class InputDialogContainer
{
	private class DateListener
		implements android.app.DatePickerDialog.OnDateSetListener
	{

		private final int mDialogType;
		final InputDialogContainer this$0;

		public void onDateSet(DatePicker datepicker, int i, int j, int k)
		{
			if (!mDialogAlreadyDismissed)
				setFieldDateTimeValue(mDialogType, i, j, k, 0, 0, 0, "%Y-%m-%d");
		}

		DateListener(int i)
		{
			this$0 = InputDialogContainer.this;
			super();
			mDialogType = i;
		}
	}

	private class DateTimeListener
		implements DateTimePickerDialog.OnDateTimeSetListener
	{

		private final int mDialogType;
		private final boolean mLocal;
		final InputDialogContainer this$0;

		public void onDateTimeSet(DatePicker datepicker, TimePicker timepicker, int i, int j, int k, int l, int i1)
		{
			if (!mDialogAlreadyDismissed)
			{
				InputDialogContainer inputdialogcontainer = InputDialogContainer.this;
				int j1 = mDialogType;
				String s;
				if (mLocal)
					s = "%Y-%m-%dT%H:%M";
				else
					s = "%Y-%m-%dT%H:%MZ";
				inputdialogcontainer.setFieldDateTimeValue(j1, i, j, k, l, i1, 0, s);
			}
		}

		public DateTimeListener(int i)
		{
			this$0 = InputDialogContainer.this;
			super();
			boolean flag;
			if (i == InputDialogContainer.sTextInputTypeDateTimeLocal)
				flag = true;
			else
				flag = false;
			mLocal = flag;
			mDialogType = i;
		}
	}

	static interface InputActionDelegate
	{

		public abstract void cancelDateTimeDialog();

		public abstract void replaceDateTime(int i, int j, int k, int l, int i1, int j1, int k1, 
				int l1);
	}

	private class MonthOrWeekListener
		implements TwoFieldDatePickerDialog.OnValueSetListener
	{

		private final int mDialogType;
		final InputDialogContainer this$0;

		public void onValueSet(int i, int j)
		{
label0:
			{
				if (!mDialogAlreadyDismissed)
				{
					if (mDialogType != InputDialogContainer.sTextInputTypeMonth)
						break label0;
					setFieldDateTimeValue(mDialogType, i, j, 1, 0, 0, 0, "%Y-%m");
				}
				return;
			}
			setFieldDateTimeValue(mDialogType, i, 0, 1, 0, 0, j, "%Y-%w");
		}

		MonthOrWeekListener(int i)
		{
			this$0 = InputDialogContainer.this;
			super();
			mDialogType = i;
		}
	}

	private class TimeListener
		implements android.app.TimePickerDialog.OnTimeSetListener
	{

		private final int mDialogType;
		final InputDialogContainer this$0;

		public void onTimeSet(TimePicker timepicker, int i, int j)
		{
			if (!mDialogAlreadyDismissed)
				setFieldDateTimeValue(mDialogType, 1970, 0, 1, i, j, 0, "%H:%M");
		}

		TimeListener(int i)
		{
			this$0 = InputDialogContainer.this;
			super();
			mDialogType = i;
		}
	}


	private static final int HOUR_DEFAULT = 0;
	private static final String HTML_DATE_FORMAT = "%Y-%m-%d";
	private static final String HTML_DATE_TIME_FORMAT = "%Y-%m-%dT%H:%MZ";
	private static final String HTML_DATE_TIME_LOCAL_FORMAT = "%Y-%m-%dT%H:%M";
	private static final String HTML_MONTH_FORMAT = "%Y-%m";
	private static final String HTML_TIME_FORMAT = "%H:%M";
	private static final String HTML_WEEK_FORMAT = "%Y-%w";
	private static final int MINUTE_DEFAULT = 0;
	private static final int MONTHDAY_DEFAULT = 1;
	private static final int MONTH_DEFAULT = 0;
	private static final int WEEK_DEFAULT = 0;
	private static final int YEAR_DEFAULT = 1970;
	private static int sTextInputTypeDate;
	private static int sTextInputTypeDateTime;
	private static int sTextInputTypeDateTimeLocal;
	private static int sTextInputTypeMonth;
	private static int sTextInputTypeTime;
	private static int sTextInputTypeWeek;
	private Context mContext;
	private AlertDialog mDialog;
	private boolean mDialogAlreadyDismissed;
	private InputActionDelegate mInputActionDelegate;

	InputDialogContainer(Context context, InputActionDelegate inputactiondelegate)
	{
		mContext = context;
		mInputActionDelegate = inputactiondelegate;
	}

	static void initializeInputTypes(int i, int j, int k, int l, int i1, int j1)
	{
		sTextInputTypeDate = i;
		sTextInputTypeDateTime = j;
		sTextInputTypeDateTimeLocal = k;
		sTextInputTypeMonth = l;
		sTextInputTypeTime = i1;
		sTextInputTypeWeek = j1;
	}

	static boolean isDialogInputType(int i)
	{
		return i == sTextInputTypeDate || i == sTextInputTypeTime || i == sTextInputTypeDateTime || i == sTextInputTypeDateTimeLocal || i == sTextInputTypeMonth || i == sTextInputTypeWeek;
	}

	private Time normalizeTime(int i, int j, int k, int l, int i1, int j1)
	{
		Time time = new Time();
		if (i == 0 && j == 0 && k == 0 && l == 0 && i1 == 0 && j1 == 0)
		{
			Calendar calendar = Calendar.getInstance();
			time.set(calendar.get(13), calendar.get(12), calendar.get(11), calendar.get(5), calendar.get(2), calendar.get(1));
			return time;
		} else
		{
			time.set(j1, i1, l, k, j, i);
			return time;
		}
	}

	private void setFieldDateTimeValue(int i, int j, int k, int l, int i1, int j1, int k1, 
			String s)
	{
		mDialogAlreadyDismissed = true;
		mInputActionDelegate.replaceDateTime(i, j, k, l, i1, j1, 0, k1);
	}

	void dismissDialog()
	{
		if (isDialogShowing())
			mDialog.dismiss();
	}

	boolean isDialogShowing()
	{
		return mDialog != null && mDialog.isShowing();
	}

	void showDialog(final int dialogType, int i, int j, int k, int l, int i1, int j1, 
			int k1, double d, double d1)
	{
		long l1;
		long l2;
		Time time;
		if (isDialogShowing())
			mDialog.dismiss();
		l1 = (long)d;
		l2 = (long)d1;
		time = normalizeTime(i, j, k, l, i1, j1);
		if (dialogType != sTextInputTypeDate) goto _L2; else goto _L1
_L1:
		DatePickerDialog datepickerdialog = new DatePickerDialog(mContext, new DateListener(dialogType), time.year, time.month, time.monthDay);
		DateDialogNormalizer.normalize(datepickerdialog.getDatePicker(), datepickerdialog, time.year, time.month, time.monthDay, 0, 0, l1, l2);
		datepickerdialog.setTitle(mContext.getText(org.chromium.content.R.string.date_picker_dialog_title));
		mDialog = datepickerdialog;
_L4:
		mDialog.setButton(-1, mContext.getText(org.chromium.content.R.string.date_picker_dialog_set), (android.content.DialogInterface.OnClickListener)mDialog);
		mDialog.setButton(-2, mContext.getText(0x1040000), new android.content.DialogInterface.OnClickListener() {

			final InputDialogContainer this$0;

			public void onClick(DialogInterface dialoginterface, int i2)
			{
				mDialogAlreadyDismissed = true;
				mInputActionDelegate.cancelDateTimeDialog();
			}

			
			{
				this$0 = InputDialogContainer.this;
				super();
			}
		});
		mDialog.setButton(-3, mContext.getText(org.chromium.content.R.string.date_picker_dialog_clear), new android.content.DialogInterface.OnClickListener() {

			final InputDialogContainer this$0;
			final int val$dialogType;

			public void onClick(DialogInterface dialoginterface, int i2)
			{
				mDialogAlreadyDismissed = true;
				mInputActionDelegate.replaceDateTime(dialogType, 0, 0, 0, 0, 0, 0, 0);
			}

			
			{
				this$0 = InputDialogContainer.this;
				dialogType = i;
				super();
			}
		});
		mDialogAlreadyDismissed = false;
		mDialog.show();
		return;
_L2:
		if (dialogType == sTextInputTypeTime)
			mDialog = TimeDialog.create(mContext, new TimeListener(dialogType), time.hour, time.minute, DateFormat.is24HourFormat(mContext), l1, l2);
		else
		if (dialogType == sTextInputTypeDateTime || dialogType == sTextInputTypeDateTimeLocal)
		{
			Context context = mContext;
			DateTimeListener datetimelistener = new DateTimeListener(dialogType);
			mDialog = new DateTimePickerDialog(context, datetimelistener, time.year, time.month, time.monthDay, time.hour, time.minute, DateFormat.is24HourFormat(mContext), l1, l2);
		} else
		if (dialogType == sTextInputTypeMonth)
			mDialog = new MonthPickerDialog(mContext, new MonthOrWeekListener(dialogType), time.year, time.month, l1, l2);
		else
		if (dialogType == sTextInputTypeWeek)
		{
			if (k1 == 0)
			{
				Calendar calendar = Calendar.getInstance();
				i = WeekPicker.getISOWeekYearForDate(calendar);
				k1 = WeekPicker.getWeekForDate(calendar);
			}
			mDialog = new WeekPickerDialog(mContext, new MonthOrWeekListener(dialogType), i, k1, l1, l2);
		}
		if (true) goto _L4; else goto _L3
_L3:
	}



/*
	static boolean access$002(InputDialogContainer inputdialogcontainer, boolean flag)
	{
		inputdialogcontainer.mDialogAlreadyDismissed = flag;
		return flag;
	}

*/




}
