// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.net;

import android.content.*;

public class ProxyChangeListener
{
	public static interface Delegate
	{

		public abstract void proxySettingsChanged();
	}

	private class ProxyReceiver extends BroadcastReceiver
	{

		final ProxyChangeListener this$0;

		public void onReceive(Context context, Intent intent)
		{
			if (intent.getAction().equals("android.intent.action.PROXY_CHANGE"))
				proxySettingsChanged();
		}

		private ProxyReceiver()
		{
			this$0 = ProxyChangeListener.this;
			super();
		}

	}


	static final boolean $assertionsDisabled = false;
	private static final String TAG = "ProxyChangeListener";
	private static boolean sEnabled = true;
	private Context mContext;
	private Delegate mDelegate;
	private int mNativePtr;
	private ProxyReceiver mProxyReceiver;

	private ProxyChangeListener(Context context)
	{
		mContext = context;
	}

	public static ProxyChangeListener create(Context context)
	{
		return new ProxyChangeListener(context);
	}

	public static String getProperty(String s)
	{
		return System.getProperty(s);
	}

	private native void nativeProxySettingsChanged(int i);

	private void proxySettingsChanged()
	{
		if (sEnabled)
		{
			if (mDelegate != null)
				mDelegate.proxySettingsChanged();
			if (mNativePtr != 0)
			{
				nativeProxySettingsChanged(mNativePtr);
				return;
			}
		}
	}

	private void registerReceiver()
	{
		if (mProxyReceiver != null)
		{
			return;
		} else
		{
			IntentFilter intentfilter = new IntentFilter();
			intentfilter.addAction("android.intent.action.PROXY_CHANGE");
			mProxyReceiver = new ProxyReceiver();
			mContext.getApplicationContext().registerReceiver(mProxyReceiver, intentfilter);
			return;
		}
	}

	public static void setEnabled(boolean flag)
	{
		sEnabled = flag;
	}

	private void unregisterReceiver()
	{
		if (mProxyReceiver == null)
		{
			return;
		} else
		{
			mContext.unregisterReceiver(mProxyReceiver);
			mProxyReceiver = null;
			return;
		}
	}

	public void setDelegateForTesting(Delegate delegate1)
	{
		mDelegate = delegate1;
	}

	public void start(int i)
	{
		if (!$assertionsDisabled && mNativePtr != 0)
		{
			throw new AssertionError();
		} else
		{
			mNativePtr = i;
			registerReceiver();
			return;
		}
	}

	public void stop()
	{
		mNativePtr = 0;
		unregisterReceiver();
	}

	static 
	{
		boolean flag;
		if (!org/chromium/net/ProxyChangeListener.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}

}
