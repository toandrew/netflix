// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.os.Handler;
import android.os.HandlerThread;

class JavaHandlerThread
{

	final HandlerThread mThread;

	private JavaHandlerThread(String s)
	{
		mThread = new HandlerThread(s);
	}

	private static JavaHandlerThread create(String s)
	{
		return new JavaHandlerThread(s);
	}

	private native void nativeInitializeThread(int i, int j);

	private void start(final int nativeThread, final int nativeEvent)
	{
		mThread.start();
		(new Handler(mThread.getLooper())).post(new Runnable() {

			final JavaHandlerThread this$0;
			final int val$nativeEvent;
			final int val$nativeThread;

			public void run()
			{
				nativeInitializeThread(nativeThread, nativeEvent);
			}

			
			{
				this$0 = JavaHandlerThread.this;
				nativeThread = i;
				nativeEvent = j;
				super();
			}
		});
	}

}
