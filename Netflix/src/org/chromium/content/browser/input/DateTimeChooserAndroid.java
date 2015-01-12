// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;

import android.content.Context;
import org.chromium.content.browser.ContentViewCore;

// Referenced classes of package org.chromium.content.browser.input:
//			InputDialogContainer

class DateTimeChooserAndroid
{

	private final InputDialogContainer mInputDialogContainer;
	private final int mNativeDateTimeChooserAndroid;

	private DateTimeChooserAndroid(Context context, int i)
	{
		mNativeDateTimeChooserAndroid = i;
		mInputDialogContainer = new InputDialogContainer(context, new InputDialogContainer.InputActionDelegate() {

			public void cancelDateTimeDialog()
			{
				nativeCancelDialog(mNativeDateTimeChooserAndroid);
			}

			public void replaceDateTime(int j, int k, int l, int i1, int j1, int k1, int l1, 
					int i2)
			{
				nativeReplaceDateTime(mNativeDateTimeChooserAndroid, j, k, l, i1, j1, k1, l1, i2);
			}
		});
	}

	private static DateTimeChooserAndroid createDateTimeChooser(ContentViewCore contentviewcore, int i, int j, int k, int l, int i1, int j1, int k1, 
			int l1, int i2, double d, double d1)
	{
		DateTimeChooserAndroid datetimechooserandroid = new DateTimeChooserAndroid(contentviewcore.getContext(), i);
		datetimechooserandroid.showDialog(j, k, l, i1, j1, k1, l1, i2, d, d1);
		return datetimechooserandroid;
	}

	private static void initializeDateInputTypes(int i, int j, int k, int l, int i1, int j1)
	{
		InputDialogContainer.initializeInputTypes(i, j, k, l, i1, j1);
	}

	private native void nativeCancelDialog(int i);

	private native void nativeReplaceDateTime(int i, int j, int k, int l, int i1, int j1, int k1, 
			int l1, int i2);

	private void showDialog(int i, int j, int k, int l, int i1, int j1, int k1, 
			int l1, double d, double d1)
	{
		mInputDialogContainer.showDialog(i, j, k, l, i1, j1, k1, l1, d, d1);
	}



}
