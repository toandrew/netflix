// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;
import java.util.HashMap;

public class WindowAndroid
{
	public static interface IntentCallback
	{

		public abstract void onIntentCompleted(WindowAndroid windowandroid, int i, ContentResolver contentresolver, Intent intent);
	}


	private static final int REQUEST_CODE_PREFIX = 1000;
	private static final int REQUEST_CODE_RANGE_SIZE = 100;
	static final String WINDOW_CALLBACK_ERRORS = "window_callback_errors";
	protected Activity mActivity;
	protected HashMap mIntentErrors;
	private int mNativeWindowAndroid;
	private int mNextRequestCode;
	protected SparseArray mOutstandingIntents;

	public WindowAndroid(Activity activity)
	{
		mNativeWindowAndroid = 0;
		mNextRequestCode = 0;
		mActivity = activity;
		mOutstandingIntents = new SparseArray();
		mIntentErrors = new HashMap();
	}

	private native void nativeDestroy(int i);

	private native int nativeInit();

	public void destroy()
	{
		if (mNativeWindowAndroid != 0)
		{
			nativeDestroy(mNativeWindowAndroid);
			mNativeWindowAndroid = 0;
		}
	}

	public Context getContext()
	{
		return mActivity;
	}

	public int getNativePointer()
	{
		if (mNativeWindowAndroid == 0)
			mNativeWindowAndroid = nativeInit();
		return mNativeWindowAndroid;
	}

	public boolean onActivityResult(int i, int j, Intent intent)
	{
		IntentCallback intentcallback = (IntentCallback)mOutstandingIntents.get(i);
		mOutstandingIntents.delete(i);
		String s = (String)mIntentErrors.remove(Integer.valueOf(i));
		if (intentcallback != null)
		{
			intentcallback.onIntentCompleted(this, j, mActivity.getContentResolver(), intent);
			return true;
		}
		if (s != null)
		{
			showCallbackNonExistentError(s);
			return true;
		} else
		{
			return false;
		}
	}

	public void restoreInstanceState(Bundle bundle)
	{
		java.io.Serializable serializable;
		if (bundle != null)
			if ((serializable = bundle.getSerializable("window_callback_errors")) instanceof HashMap)
			{
				mIntentErrors = (HashMap)serializable;
				return;
			}
	}

	public void saveInstanceState(Bundle bundle)
	{
		bundle.putSerializable("window_callback_errors", mIntentErrors);
	}

	public void sendBroadcast(Intent intent)
	{
		mActivity.sendBroadcast(intent);
	}

	protected void showCallbackNonExistentError(String s)
	{
		showError(s);
	}

	public void showError(int i)
	{
		showError(mActivity.getString(i));
	}

	public void showError(String s)
	{
		if (s != null)
			Toast.makeText(mActivity, s, 0).show();
	}

	public boolean showIntent(Intent intent, IntentCallback intentcallback, int i)
	{
		int j = 1000 + mNextRequestCode;
		mNextRequestCode = (1 + mNextRequestCode) % 100;
		try
		{
			mActivity.startActivityForResult(intent, j);
		}
		catch (ActivityNotFoundException activitynotfoundexception)
		{
			return false;
		}
		mOutstandingIntents.put(j, intentcallback);
		mIntentErrors.put(Integer.valueOf(j), mActivity.getString(i));
		return true;
	}
}
