// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import org.chromium.base.ThreadUtils;

// Referenced classes of package org.chromium.content.browser:
//			ContentViewCore

public class ContentSettings
{

	static final boolean $assertionsDisabled = false;
	private static final String TAG = "ContentSettings";
	private ContentViewCore mContentViewCore;
	private int mNativeContentSettings;

	ContentSettings(ContentViewCore contentviewcore, int i)
	{
		mNativeContentSettings = 0;
		ThreadUtils.assertOnUiThread();
		mContentViewCore = contentviewcore;
		mNativeContentSettings = nativeInit(i);
		if (!$assertionsDisabled && mNativeContentSettings == 0)
			throw new AssertionError();
		else
			return;
	}

	private native boolean nativeGetJavaScriptEnabled(int i);

	private native int nativeInit(int i);

	private void onNativeContentSettingsDestroyed(int i)
	{
		if (!$assertionsDisabled && mNativeContentSettings != i)
		{
			throw new AssertionError();
		} else
		{
			mNativeContentSettings = 0;
			return;
		}
	}

	public boolean getJavaScriptEnabled()
	{
		ThreadUtils.assertOnUiThread();
		if (mNativeContentSettings != 0)
			return nativeGetJavaScriptEnabled(mNativeContentSettings);
		else
			return false;
	}

	static 
	{
		boolean flag;
		if (!org/chromium/content/browser/ContentSettings.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}
}
