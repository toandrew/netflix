// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;


public class InterstitialPageDelegateAndroid
{

	private int mNativePtr;

	public InterstitialPageDelegateAndroid(String s)
	{
		mNativePtr = nativeInit(s);
	}

	private native void nativeDontProceed(int i);

	private native int nativeInit(String s);

	private native void nativeProceed(int i);

	private void onNativeDestroyed()
	{
		mNativePtr = 0;
	}

	protected void commandReceived(String s)
	{
	}

	protected void dontProceed()
	{
		if (mNativePtr != 0)
			nativeDontProceed(mNativePtr);
	}

	public int getNative()
	{
		return mNativePtr;
	}

	protected void onDontProceed()
	{
	}

	protected void onProceed()
	{
	}

	protected void proceed()
	{
		if (mNativePtr != 0)
			nativeProceed(mNativePtr);
	}
}
