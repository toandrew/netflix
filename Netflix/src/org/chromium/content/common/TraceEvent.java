// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.common;

import android.os.*;
import android.util.Log;
import android.util.Printer;
import java.lang.reflect.*;

public class TraceEvent
{
	private static final class LooperMonitor
		implements Printer, android.os.MessageQueue.IdleHandler
	{

		static final boolean $assertionsDisabled = false;
		private static final String DISPATCH_EVENT_NAME = "Looper.dispatchMessage";
		private static final long FRAME_DURATION_MILLIS = 16L;
		private static final String IDLE_EVENT_NAME = "Looper.queueIdle";
		private static final long MIN_INTERESTING_BURST_DURATION_MILLIS = 48L;
		private static final long MIN_INTERESTING_DURATION_MILLIS = 16L;
		private static final String TAG = "TraceEvent.LooperMonitor";
		private boolean mIdleMonitorAttached;
		private long mLastIdleStartedAt;
		private long mLastWorkStartedAt;
		private int mNumIdlesSeen;
		private int mNumTasksSeen;
		private int mNumTasksSinceLastIdle;

		private final void begin(String s)
		{
			if (mNumTasksSinceLastIdle == 0)
				TraceEvent.end("Looper.queueIdle");
			TraceEvent.begin("Looper.dispatchMessage", s);
			mLastWorkStartedAt = SystemClock.elapsedRealtime();
			syncIdleMonitoring();
		}

		private final void end(String s)
		{
			long l = SystemClock.elapsedRealtime() - mLastWorkStartedAt;
			if (l > 16L)
				traceAndLog(5, (new StringBuilder()).append("observed a task that took ").append(l).append("ms: ").append(s).toString());
			TraceEvent.end("Looper.dispatchMessage");
			syncIdleMonitoring();
			mNumTasksSeen = 1 + mNumTasksSeen;
			mNumTasksSinceLastIdle = 1 + mNumTasksSinceLastIdle;
		}

		public static final LooperMonitor getInstance()
		{
		    return null;
			//return Holder.sInstance;
		}

		private final void syncIdleMonitoring()
		{
			if (TraceEvent.sEnabled && !mIdleMonitorAttached)
			{
				mLastIdleStartedAt = SystemClock.elapsedRealtime();
				Looper.myQueue().addIdleHandler(getInstance());
				mIdleMonitorAttached = true;
				Log.v("TraceEvent.LooperMonitor", "attached idle handler");
			} else
			if (mIdleMonitorAttached && !TraceEvent.sEnabled)
			{
				Looper.myQueue().removeIdleHandler(getInstance());
				mIdleMonitorAttached = false;
				Log.v("TraceEvent.LooperMonitor", "detached idle handler");
				return;
			}
		}

		private static void traceAndLog(int i, String s)
		{
			TraceEvent.instant("TraceEvent.LooperMonitor:IdleStats", s);
			Log.println(i, "TraceEvent.LooperMonitor", s);
		}

		public void println(String s)
		{
			if (s.startsWith(">"))
			{
				begin(s);
				return;
			}
			if (!$assertionsDisabled && !s.startsWith("<"))
			{
				throw new AssertionError();
			} else
			{
				end(s);
				return;
			}
		}

		public final boolean queueIdle()
		{
			long l = SystemClock.elapsedRealtime();
			if (mLastIdleStartedAt == 0L)
				mLastIdleStartedAt = l;
			long l1 = l - mLastIdleStartedAt;
			mNumIdlesSeen = 1 + mNumIdlesSeen;
			TraceEvent.begin("Looper.queueIdle", (new StringBuilder()).append(mNumTasksSinceLastIdle).append(" tasks since last idle.").toString());
			if (l1 > 48L)
				traceAndLog(3, (new StringBuilder()).append(mNumTasksSeen).append(" tasks and ").append(mNumIdlesSeen).append(" idles processed so far, ").append(mNumTasksSinceLastIdle).append(" tasks bursted and ").append(l1).append("ms elapsed since last idle").toString());
			mLastIdleStartedAt = l;
			mNumTasksSinceLastIdle = 0;
			return true;
		}

		static 
		{
//			boolean flag;
//			if (!org/chromium/content/common/TraceEvent.desiredAssertionStatus())
//				flag = true;
//			else
//				flag = false;
//			$assertionsDisabled = false;
		}

		private LooperMonitor()
		{
			mLastIdleStartedAt = 0L;
			mLastWorkStartedAt = 0L;
			mNumTasksSeen = 0;
			mNumIdlesSeen = 0;
			mNumTasksSinceLastIdle = 0;
			mIdleMonitorAttached = false;
		}

	}

//	private static final class LooperMonitor.Holder
//	{
//
//		private static final LooperMonitor sInstance = new LooperMonitor();
//
//
//
//		private LooperMonitor.Holder()
//		{
//		}
//	};


	private static final String PROPERTY_TRACE_TAG_ENABLEFLAGS = "debug.atrace.tags.enableflags";
	private static volatile boolean sEnabled;
	private static Method sSystemPropertiesGetLongMethod;
	private static long sTraceTagView;

	public TraceEvent()
	{
	}

	public static void begin()
	{
		if (sEnabled)
			nativeBegin(getCallerName(), null);
	}

	public static void begin(String s)
	{
		if (sEnabled)
			nativeBegin(s, null);
	}

	public static void begin(String s, String s1)
	{
		if (sEnabled)
			nativeBegin(s, s1);
	}

	public static boolean enabled()
	{
		return sEnabled;
	}

	public static void end()
	{
		if (sEnabled)
			nativeEnd(getCallerName(), null);
	}

	public static void end(String s)
	{
		if (sEnabled)
			nativeEnd(s, null);
	}

	public static void end(String s, String s1)
	{
		if (sEnabled)
			nativeEnd(s, s1);
	}

	public static void finishAsync(long l)
	{
		if (sEnabled)
			nativeFinishAsync(getCallerName(), l, null);
	}

	public static void finishAsync(String s, long l)
	{
		if (sEnabled)
			nativeFinishAsync(s, l, null);
	}

	public static void finishAsync(String s, long l, String s1)
	{
		if (sEnabled)
			nativeFinishAsync(s, l, s1);
	}

	private static String getCallerName()
	{
		StackTraceElement astacktraceelement[] = Thread.currentThread().getStackTrace();
		return (new StringBuilder()).append(astacktraceelement[4].getClassName()).append(".").append(astacktraceelement[4].getMethodName()).toString();
	}

	public static void instant(String s)
	{
		if (sEnabled)
			nativeInstant(s, null);
	}

	public static void instant(String s, String s1)
	{
		if (sEnabled)
			nativeInstant(s, s1);
	}

	private static native void nativeBegin(String s, String s1);

	private static native void nativeEnd(String s, String s1);

	private static native void nativeFinishAsync(String s, long l, String s1);

	private static native void nativeInstant(String s, String s1);

	private static native void nativeStartATrace();

	private static native void nativeStartAsync(String s, long l, String s1);

	private static native void nativeStopATrace();

	private static native boolean nativeTraceEnabled();

	public static void setEnabled(boolean flag)
	{
//		org/chromium/content/common/TraceEvent;
//		JVM INSTR monitorenter ;
//		boolean flag1 = sEnabled;
//		if (flag1 != flag) goto _L2; else goto _L1
//_L1:
//		org/chromium/content/common/TraceEvent;
//		JVM INSTR monitorexit ;
//		return;
//_L2:
//		Looper looper;
//		sEnabled = flag;
//		looper = Looper.getMainLooper();
//		if (!flag)
//			break; /* Loop/switch isn't completed */
//		LooperMonitor loopermonitor = LooperMonitor.getInstance();
//_L4:
//		looper.setMessageLogging(loopermonitor);
//		if (true) goto _L1; else goto _L3
//		Exception exception;
//		exception;
//		throw exception;
//_L3:
//		loopermonitor = null;
//		  goto _L4
	}

	public static void setEnabledToMatchNative()
	{
//		boolean flag = nativeTraceEnabled();
//		if (sSystemPropertiesGetLongMethod == null) goto _L2; else goto _L1
//_L1:
//		Method method;
//		Object aobj[];
//		method = sSystemPropertiesGetLongMethod;
//		aobj = new Object[2];
//		aobj[0] = "debug.atrace.tags.enableflags";
//		aobj[1] = Integer.valueOf(0);
//		if ((((Long)method.invoke(null, aobj)).longValue() & sTraceTagView) == 0L) goto _L4; else goto _L3
//_L3:
//		nativeStartATrace();
//		flag = true;
//_L2:
//		setEnabled(flag);
//		return;
//_L4:
//		try
//		{
//			nativeStopATrace();
//		}
//		catch (IllegalArgumentException illegalargumentexception)
//		{
//			Log.e("TraceEvent", "setEnabledToMatchNative", illegalargumentexception);
//		}
//		catch (IllegalAccessException illegalaccessexception)
//		{
//			Log.e("TraceEvent", "setEnabledToMatchNative", illegalaccessexception);
//		}
//		catch (InvocationTargetException invocationtargetexception)
//		{
//			Log.e("TraceEvent", "setEnabledToMatchNative", invocationtargetexception);
//		}
//		if (true) goto _L2; else goto _L5
//_L5:
	}

	public static void startAsync(long l)
	{
		if (sEnabled)
			nativeStartAsync(getCallerName(), l, null);
	}

	public static void startAsync(String s, long l)
	{
		if (sEnabled)
			nativeStartAsync(s, l, null);
	}

	public static void startAsync(String s, long l, String s1)
	{
		if (sEnabled)
			nativeStartAsync(s, l, s1);
	}

	static 
	{
//		sEnabled = false;
//		if (android.os.Build.VERSION.SDK_INT < 16)
//			break MISSING_BLOCK_LABEL_111;
//		sTraceTagView = Class.forName("android.os.Trace").getField("TRACE_TAG_WEBVIEW").getLong(null);
//		Class class1 = Class.forName("android.os.SystemProperties");
//		Class aclass[] = new Class[2];
//		aclass[0] = java/lang/String;
//		aclass[1] = Long.TYPE;
//		sSystemPropertiesGetLongMethod = class1.getDeclaredMethod("getLong", aclass);
//		Method method = class1.getDeclaredMethod("addChangeCallback", new Class[] {
//			java/lang/Runnable
//		});
//		Object aobj[] = new Object[1];
//		aobj[0] = new Runnable() {
//
//			public void run()
//			{
//				TraceEvent.setEnabledToMatchNative();
//			}
//
//		};
//		method.invoke(null, aobj);
//		return;
//		ClassNotFoundException classnotfoundexception;
//		classnotfoundexception;
//		Log.e("TraceEvent", "init", classnotfoundexception);
//		break MISSING_BLOCK_LABEL_185;
//		NoSuchMethodException nosuchmethodexception;
//		nosuchmethodexception;
//		Log.e("TraceEvent", "init", nosuchmethodexception);
//		break MISSING_BLOCK_LABEL_185;
//		IllegalArgumentException illegalargumentexception;
//		illegalargumentexception;
//		Log.e("TraceEvent", "init", illegalargumentexception);
//		break MISSING_BLOCK_LABEL_185;
//		IllegalAccessException illegalaccessexception;
//		illegalaccessexception;
//		Log.e("TraceEvent", "init", illegalaccessexception);
//		break MISSING_BLOCK_LABEL_185;
//		InvocationTargetException invocationtargetexception;
//		invocationtargetexception;
//		Log.e("TraceEvent", "init", invocationtargetexception);
//		break MISSING_BLOCK_LABEL_185;
//		NoSuchFieldException nosuchfieldexception;
//		nosuchfieldexception;
//		Log.e("TraceEvent", "init", nosuchfieldexception);
	}

}
