// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;


// Referenced classes of package org.chromium.content.browser:
//			ContentViewCore

public abstract class WebContentsObserverAndroid
{

	private int mNativeWebContentsObserverAndroid;

	public WebContentsObserverAndroid(ContentViewCore contentviewcore)
	{
		mNativeWebContentsObserverAndroid = nativeInit(contentviewcore.getNativeContentViewCore());
	}

	private native void nativeDestroy(int i);

	private native int nativeInit(int i);

	public void detachFromWebContents()
	{
		if (mNativeWebContentsObserverAndroid != 0)
		{
			nativeDestroy(mNativeWebContentsObserverAndroid);
			mNativeWebContentsObserverAndroid = 0;
		}
	}

	public void didChangeVisibleSSLState()
	{
	}

	public void didCommitProvisionalLoadForFrame(long l, boolean flag, String s, int i)
	{
	}

	public void didFailLoad(boolean flag, boolean flag1, int i, String s, String s1)
	{
	}

	public void didFinishLoad(long l, String s, boolean flag)
	{
	}

	public void didNavigateAnyFrame(String s, String s1, boolean flag)
	{
	}

	public void didNavigateMainFrame(String s, String s1, boolean flag)
	{
	}

	public void didStartLoading(String s)
	{
	}

	public void didStartProvisionalLoadForFrame(long l, long l1, boolean flag, String s, boolean flag1, 
			boolean flag2)
	{
	}

	public void didStopLoading(String s)
	{
	}
}
