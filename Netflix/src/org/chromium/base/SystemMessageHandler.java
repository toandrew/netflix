// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.os.Handler;
import android.os.Message;

class SystemMessageHandler extends Handler
{

	private static final int DELAYED_TIMER_MESSAGE = 2;
	private static final int TIMER_MESSAGE = 1;
	private int mMessagePumpDelegateNative;

	private SystemMessageHandler(int i)
	{
		mMessagePumpDelegateNative = 0;
		mMessagePumpDelegateNative = i;
	}

	private static SystemMessageHandler create(int i)
	{
		return new SystemMessageHandler(i);
	}

	private native void nativeDoRunLoopOnce(int i);

	private void removeTimer()
	{
		removeMessages(1);
	}

	private void setDelayedTimer(long l)
	{
		removeMessages(2);
		sendEmptyMessageDelayed(2, l);
	}

	private void setTimer()
	{
		sendEmptyMessage(1);
	}

	public void handleMessage(Message message)
	{
		nativeDoRunLoopOnce(mMessagePumpDelegateNative);
	}
}
