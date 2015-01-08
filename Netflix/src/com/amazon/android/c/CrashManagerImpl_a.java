// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.c;

import android.app.Application;
import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.o.EventManager_g;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

// Referenced classes of package com.amazon.android.c:
//			d, b, c

public final class CrashManagerImpl_a
	implements d, b, Thread.UncaughtExceptionHandler
{

	private static final KiwiLogger a = new KiwiLogger("CrashManagerImpl");
	private EventManager_g b;
	private com.amazon.android.g.b c;
	private Application d;
	private Thread.UncaughtExceptionHandler e;
	private Map f;

	public CrashManagerImpl_a()
	{
		f = new HashMap();
	}

	static Map a(CrashManagerImpl_a a1)
	{
		return a1.f;
	}

	private void a(String s)
	{
		this;
		JVM INSTR monitorenter ;
		String s1;
		int i = (new Random()).nextInt(0x1869f);
		s1 = (new StringBuilder()).append("s-").append(i).append(".amzst").toString();
		FileOutputStream fileoutputstream = d.openFileOutput(s1, 0);
		fileoutputstream.write(s.getBytes());
		fileoutputstream.close();
_L2:
		this;
		JVM INSTR monitorexit ;
		return;
		Exception exception1;
		exception1;
		if (KiwiLogger.ERROR_ON)
			a.error("Coud not save crash report to file", exception1);
		if (true) goto _L2; else goto _L1
_L1:
		Exception exception;
		exception;
		throw exception;
	}

	private com.amazon.android.c.CrashReport_b b(String s)
	{
		com.amazon.android.c.CrashReport_b b1;
		try
		{
			String s1 = c(s);
			b1 = (com.amazon.android.c.CrashReport_b)com.amazon.android.u.a.a(c.b(s1));
		}
		catch (Exception exception)
		{
			if (KiwiLogger.ERROR_ON)
				a.error((new StringBuilder()).append("Failed to load crash report: ").append(s).toString());
			return null;
		}
		return b1;
	}

	private boolean b()
	{
		return f.size() >= 5;
	}

	private static String c(String s)
		throws IOException
	{
		StringBuilder stringbuilder = new StringBuilder();
		BufferedReader bufferedreader = new BufferedReader(new FileReader(s));
		while (bufferedreader.ready()) 
			stringbuilder.append(bufferedreader.readLine());
		  goto _L1
		Exception exception;
		exception;
		BufferedReader bufferedreader1 = bufferedreader;
_L3:
		if (bufferedreader1 != null)
			bufferedreader1.close();
		throw exception;
_L1:
		bufferedreader.close();
		return stringbuilder.toString();
		exception;
		bufferedreader1 = null;
		if (true) goto _L3; else goto _L2
_L2:
	}

	private static void d(String s)
	{
		(new File(s)).delete();
_L1:
		return;
		Exception exception;
		exception;
		if (KiwiLogger.ERROR_ON)
		{
			a.error((new StringBuilder()).append("Cannot delete file: ").append(s).toString(), exception);
			return;
		}
		  goto _L1
	}

	public final List a()
	{
		Object obj;
		if (b())
		{
			obj = Collections.emptyList();
		} else
		{
			obj = new ArrayList();
			String as[] = (new File((new StringBuilder()).append(d.getFilesDir().getAbsolutePath()).append("/").toString())).list(new c(this));
			int i = 0;
			while (i < as.length && !b()) 
			{
				String s = as[i];
				String s1 = (new StringBuilder()).append(d.getFilesDir().getAbsolutePath()).append("/").append(s).toString();
				com.amazon.android.c.CrashReport_b b1 = b(s1);
				if (b1 != null)
				{
					f.put(b1, s1);
					((List) (obj)).add(b1);
				} else
				{
					d(s1);
				}
				i++;
			}
		}
		return ((List) (obj));
	}

	public final void a(List list)
	{
		com.amazon.android.c.CrashReport_b b1;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); f.remove(b1))
		{
			b1 = (com.amazon.android.c.CrashReport_b)iterator.next();
			d((String)f.get(b1));
		}

	}

	public final void onResourcesPopulated()
	{
		com.amazon.android.d.a.a();
		if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof d))
		{
			if (KiwiLogger.TRACE_ON)
				a.trace("Registering Crash Handler!!!!");
			e = Thread.getDefaultUncaughtExceptionHandler();
			Thread.setDefaultUncaughtExceptionHandler(this);
		}
	}

	public final void uncaughtException(Thread thread, Throwable throwable)
	{
		if (KiwiLogger.TRACE_ON)
		{
			a.trace("---------------------------------------------------");
			a.trace("Crash detected", throwable);
			a.trace("---------------------------------------------------");
		}
		String s = com.amazon.android.u.Serializer_a.a(new com.amazon.android.c.CrashReport_b(d, throwable));
		a(c.a(s));
_L2:
		Throwable throwable1;
		try
		{
			b.a(new com.amazon.android.a.b());
		}
		catch (Throwable throwable2)
		{
			if (KiwiLogger.TRACE_ON)
				a.trace("Error occured while handling exception", throwable2);
		}
		if (KiwiLogger.TRACE_ON)
			a.trace("Calling previous handler");
		if (e != null)
			e.uncaughtException(thread, throwable);
		return;
		throwable1;
		if (!KiwiLogger.ERROR_ON) goto _L2; else goto _L1
_L1:
		a.error("Could not handle uncaught exception", throwable1);
		  goto _L2
	}

}
