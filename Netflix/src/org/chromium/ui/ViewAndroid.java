// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui;

import android.view.View;

// Referenced classes of package org.chromium.ui:
//			WindowAndroid, ViewAndroidDelegate

public class ViewAndroid
{

	static final boolean $assertionsDisabled;
	private int mKeepScreenOnCount;
	private View mKeepScreenOnView;
	private int mNativeViewAndroid;
	private final ViewAndroidDelegate mViewAndroidDelegate;
	private final WindowAndroid mWindowAndroid;

	public ViewAndroid(WindowAndroid windowandroid, ViewAndroidDelegate viewandroiddelegate)
	{
		mNativeViewAndroid = 0;
		mWindowAndroid = windowandroid;
		mViewAndroidDelegate = viewandroiddelegate;
		mNativeViewAndroid = nativeInit(mWindowAndroid.getNativePointer());
	}

	private native void nativeDestroy(int i);

	private native int nativeInit(int i);

	public void decrementKeepScreenOnCount()
	{
		if (!$assertionsDisabled && mKeepScreenOnCount <= 0)
			throw new AssertionError();
		mKeepScreenOnCount = -1 + mKeepScreenOnCount;
		if (mKeepScreenOnCount == 0)
		{
			mViewAndroidDelegate.releaseAnchorView(mKeepScreenOnView);
			mKeepScreenOnView = null;
		}
	}

	public void destroy()
	{
		if (mNativeViewAndroid != 0)
		{
			nativeDestroy(mNativeViewAndroid);
			mNativeViewAndroid = 0;
		}
	}

	public int getNativePointer()
	{
		return mNativeViewAndroid;
	}

	public ViewAndroidDelegate getViewAndroidDelegate()
	{
		return mViewAndroidDelegate;
	}

	public void incrementKeepScreenOnCount()
	{
		mKeepScreenOnCount = 1 + mKeepScreenOnCount;
		if (mKeepScreenOnCount == 1)
		{
			mKeepScreenOnView = mViewAndroidDelegate.acquireAnchorView();
			mKeepScreenOnView.setKeepScreenOn(true);
		}
	}

	static 
	{
//		boolean flag;
//		if (!org/chromium/ui/ViewAndroid.desiredAssertionStatus())
//			flag = true;
//		else
//			flag = false;
		$assertionsDisabled = false;
	}
}
