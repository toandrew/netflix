// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.os.*;
import java.util.concurrent.*;

public class ThreadUtils
{
	private static class LazyHolder
	{

		private static Handler sUiThreadHandler = new Handler(Looper.getMainLooper());



		private LazyHolder()
		{
		}
	}


	static final boolean $assertionsDisabled;

	public ThreadUtils()
	{
	}

	public static void assertOnUiThread()
	{
		if (!$assertionsDisabled && !runningOnUiThread())
			throw new AssertionError();
		else
			return;
	}

	public static FutureTask postOnUiThread(FutureTask futuretask)
	{
		LazyHolder.sUiThreadHandler.post(futuretask);
		return futuretask;
	}

	public static void postOnUiThread(Runnable runnable)
	{
		LazyHolder.sUiThreadHandler.post(runnable);
	}

	public static void postOnUiThreadDelayed(Runnable runnable, long l)
	{
		LazyHolder.sUiThreadHandler.postDelayed(runnable, l);
	}

	public static FutureTask runOnUiThread(Callable callable)
	{
		return runOnUiThread(new FutureTask(callable));
	}

	public static FutureTask runOnUiThread(FutureTask futuretask)
	{
		if (runningOnUiThread())
		{
			futuretask.run();
			return futuretask;
		} else
		{
			postOnUiThread(futuretask);
			return futuretask;
		}
	}

	public static void runOnUiThread(Runnable runnable)
	{
		if (runningOnUiThread())
		{
			runnable.run();
			return;
		} else
		{
			LazyHolder.sUiThreadHandler.post(runnable);
			return;
		}
	}

	public static Object runOnUiThreadBlocking(Callable callable)
		throws ExecutionException
	{
		FutureTask futuretask = new FutureTask(callable);
		runOnUiThread(futuretask);
		Object obj;
		try
		{
			obj = futuretask.get();
		}
		catch (InterruptedException interruptedexception)
		{
			throw new RuntimeException("Interrupted waiting for callable", interruptedexception);
		}
		return obj;
	}

	public static void runOnUiThreadBlocking(Runnable runnable)
	{
		if (runningOnUiThread())
		{
			runnable.run();
			return;
		}
		FutureTask futuretask = new FutureTask(runnable, null);
		postOnUiThread(futuretask);
		try
		{
			futuretask.get();
			return;
		}
		catch (Exception exception)
		{
			throw new RuntimeException("Exception occured while waiting for runnable", exception);
		}
	}

	public static Object runOnUiThreadBlockingNoException(Callable callable)
	{
		Object obj;
		try
		{
			obj = runOnUiThreadBlocking(callable);
		}
		catch (ExecutionException executionexception)
		{
			throw new RuntimeException("Error occured waiting for callable", executionexception);
		}
		return obj;
	}

	public static boolean runningOnUiThread()
	{
		return Looper.getMainLooper() == Looper.myLooper();
	}

	public static void setThreadPriorityAudio(int i)
	{
		Process.setThreadPriority(i, -16);
	}

	static 
	{
		boolean flag;
		if (!org/chromium/base/ThreadUtils.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}
}
