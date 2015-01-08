// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.context;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.task.TaskManager;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.j.a;
import com.amazon.android.j.c;
import com.amazon.android.o.EventManager_g;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

// Referenced classes of package com.amazon.android.framework.context:
//			ContextManager, a, c, b

public final class ContextManagerImpl_d
	implements ContextManager, b
{

	public static final KiwiLogger a = new KiwiLogger("ContextManagerImpl");
	private final AtomicReference b = new AtomicReference();
	private final com.amazon.android.framework.util.b c = new com.amazon.android.framework.util.b();
	private final com.amazon.android.framework.util.b d = new com.amazon.android.framework.util.b();
	private final com.amazon.android.framework.util.b e = new com.amazon.android.framework.util.b();
	private final AtomicBoolean f = new AtomicBoolean(false);
	private final AtomicBoolean g = new AtomicBoolean(false);
	private final AtomicBoolean h = new AtomicBoolean(false);
	private Application i;
	private TaskManager j;
	private EventManager_g k;
	private String l;
	private boolean m;

	public ContextManagerImpl_d()
	{
		m = false;
	}

	static void a(ContextManagerImpl_d d1)
	{
		if (KiwiLogger.TRACE_ON)
		{
			a.trace("----------- EXECUTING FINISH ACTIVITIES -----------");
			a.trace(d1.i.getPackageName());
			a.trace("---------------------------------------------------");
		}
		Iterator iterator = d1.c.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			Activity activity1 = (Activity)iterator.next();
			if (!a(activity1) && activity1 != null)
				if (activity1.isChild())
				{
					a.trace((new StringBuilder()).append("Not finishing activity: ").append(activity1).append(", it is a child of: ").append(activity1.getParent()).toString());
				} else
				{
					a.trace((new StringBuilder()).append("Finishing Activity: ").append(activity1).toString());
					activity1.finish();
				}
		} while (true);
		Activity activity = d1.getRoot();
		com.amazon.android.framework.context.a a1;
		if (activity == null)
		{
			a.trace("Shutdown found no root, no activities to pop off stack!");
		} else
		{
			a.trace("Moving task to background");
			activity.moveTaskToBack(true);
			a.trace((new StringBuilder()).append("Popping activity stack, root: ").append(activity).toString());
			Intent intent = new Intent(activity, activity.getClass());
			intent.addFlags(0x4000000);
			intent.addFlags(0x20000000);
			activity.startActivity(intent);
		}
		a1 = new com.amazon.android.framework.context.a(d1);
		d1.j.enqueue(TaskPipelineId.FOREGROUND, a1);
	}

	static void a(ContextManagerImpl_d d1, Intent intent)
	{
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Received broadcast intent: ").append(intent).toString());
		if (intent.getAction().equals(d1.l)) goto _L2; else goto _L1
_L1:
		if (KiwiLogger.ERROR_ON)
			a.error((new StringBuilder()).append("Received broadcast for unrequested action: ").append(intent.getAction()).toString());
_L4:
		return;
_L2:
		if (d1.g.get())
			break; /* Loop/switch isn't completed */
		if (KiwiLogger.ERROR_ON)
		{
			a.error((new StringBuilder()).append("Received intent to shutdown app when we are not in shutdown state: ").append(intent).toString());
			return;
		}
		if (true) goto _L4; else goto _L3
_L3:
		if (d1.h.compareAndSet(false, true))
			break; /* Loop/switch isn't completed */
		if (KiwiLogger.TRACE_ON)
		{
			a.trace("shutdown broadcast already received, ignoring");
			return;
		}
		if (true) goto _L4; else goto _L5
_L5:
		if (KiwiLogger.TRACE_ON)
		{
			a.trace("Stopping services in response to broadcast");
			a.trace((new StringBuilder()).append("Service to stop: ").append(d1.d.a.size()).toString());
		}
		Iterator iterator = d1.d.iterator();
		while (iterator.hasNext()) 
		{
			Service service = (Service)iterator.next();
			if (service != null)
			{
				if (KiwiLogger.TRACE_ON)
					a.trace((new StringBuilder()).append("Stopping service: ").append(service).toString());
				service.stopSelf();
			}
		}
		if (true) goto _L4; else goto _L6
_L6:
	}

	private void a(c c1, Activity activity)
	{
		com.amazon.android.j.b b1 = new com.amazon.android.j.b(c1, activity);
		k.a(b1);
	}

	private void a(com.amazon.android.j.d d1)
	{
		a a1 = new a(d1, i);
		k.a(a1);
	}

	private static boolean a(Activity activity)
	{
		if (activity == null)
			return false;
		else
			return activity.isTaskRoot();
	}

	private static Activity b(Activity activity)
	{
		Activity activity1;
		for (activity1 = activity; activity1.isChild(); activity1 = activity1.getParent());
		return activity1;
	}

	public final void finishActivities()
	{
		if (!f.compareAndSet(false, true))
			return;
		if (KiwiLogger.TRACE_ON)
		{
			a.trace("---------- SCHEDULING FINISH ACTIVITIES -----------");
			a.trace(i.getPackageName());
			a.trace(Thread.currentThread().toString());
			a.trace("---------------------------------------------------");
		}
		com.amazon.android.framework.context.c c1 = new com.amazon.android.framework.context.c(this);
		j.enqueue(TaskPipelineId.FOREGROUND, c1);
	}

	public final Activity getRoot()
	{
		com.amazon.android.d.a.a();
		for (Iterator iterator = c.iterator(); iterator.hasNext();)
		{
			Activity activity = (Activity)iterator.next();
			if (a(activity))
				return activity;
		}

		return null;
	}

	public final Activity getVisible()
	{
		com.amazon.android.d.a.a();
		return (Activity)b.get();
	}

	public final boolean hasAppShutdownBeenRequested()
	{
		return g.get() || f.get();
	}

	public final boolean isVisible()
	{
		return b.get() != null;
	}

	public final void onCreate(Activity activity)
	{
		com.amazon.android.d.a.a(activity, "activity");
		com.amazon.android.d.a.a();
		c.a(activity);
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Activity->onCreate.  Activity: ").append(activity).append(", Total Activities: ").append(c.a.size()).toString());
		a(c.a, activity);
		boolean flag;
		if (!m && c.a.size() == 1)
			flag = true;
		else
			flag = false;
		if (flag)
		{
			m = true;
			a(com.amazon.android.j.d.a);
		}
	}

	public final void onCreate(Service service)
	{
		com.amazon.android.d.a.a();
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Service->onCreate: ").append(service).toString());
		d.a(service);
	}

	public final void onDestroy(Activity activity)
	{
		com.amazon.android.d.a.a(activity, "activity");
		com.amazon.android.d.a.a();
		c.b(activity);
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Activity->onDestroy.  Activity: ").append(activity).append(", Total Activities: ").append(c.a.size()).toString());
		a(c.b, activity);
		if (KiwiLogger.TRACE_ON)
			a.trace("Checking if application is destroyed");
		if (!c.a.isEmpty()) goto _L2; else goto _L1
_L1:
		a.trace((new StringBuilder()).append("App is destroyed: ").append(activity.isTaskRoot()).append(", ").append(activity.isFinishing()).toString());
		if (!activity.isTaskRoot() || !activity.isFinishing()) goto _L2; else goto _L3
_L3:
		boolean flag = true;
_L5:
		if (flag)
		{
			m = false;
			a(com.amazon.android.j.d.b);
		}
		return;
_L2:
		flag = false;
		if (true) goto _L5; else goto _L4
_L4:
	}

	public final void onDestroy(Service service)
	{
		com.amazon.android.d.a.a();
		d.b(service);
	}

	public final void onPause(Activity activity)
	{
		com.amazon.android.d.a.a();
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Activity paused: ").append(activity).append(", visible activity: ").append(getVisible()).toString());
		if (getVisible() == activity)
		{
			if (KiwiLogger.TRACE_ON)
				a.trace("Setting visible activity to null");
			b.set(null);
			a(c.d, activity);
		}
	}

	public final void onResourcesPopulated()
	{
		String s = i.getPackageName();
		l = (new StringBuilder()).append("com.amazon.").append(s).append(".shutdown").toString();
		com.amazon.android.framework.context.b b1 = new com.amazon.android.framework.context.b(this);
		IntentFilter intentfilter = new IntentFilter(l);
		i.registerReceiver(b1, intentfilter);
	}

	public final void onResume(Activity activity)
	{
		com.amazon.android.d.a.a();
		a.trace((new StringBuilder()).append("Activity resumed: ").append(activity).append(", is child: ").append(activity.isChild()).toString());
		Activity activity1 = b(activity);
		a.trace((new StringBuilder()).append("Setting visible to: ").append(activity1).toString());
		b.set(activity1);
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Activity now visible: ").append(activity).append(", ").append("publishing resume event").toString());
		a(c.c, activity);
	}

	public final void onStart(Activity activity)
	{
		com.amazon.android.d.a.a(activity, "activity");
		com.amazon.android.d.a.a();
		a.trace((new StringBuilder()).append("Activity started: ").append(activity).toString());
		e.a(activity);
		a(c.e, activity);
		if (e.a.size() == 1)
			a(com.amazon.android.j.d.c);
	}

	public final void onStop(Activity activity)
	{
		com.amazon.android.d.a.a(activity, "activity");
		com.amazon.android.d.a.a();
		a.trace((new StringBuilder()).append("Activity stopped: ").append(activity).toString());
		e.b(activity);
		a(c.f, activity);
		if (e.a.isEmpty())
			a(com.amazon.android.j.d.d);
	}

	public final void stopServices()
	{
		if (!g.compareAndSet(false, true) && KiwiLogger.TRACE_ON)
			a.trace("Ignoring duplicate stopServices request");
		if (KiwiLogger.TRACE_ON)
		{
			a.trace("------------- STOPPING SERVICES -------------------");
			a.trace(i.getPackageName());
			a.trace("---------------------------------------------------");
		}
		Intent intent = new Intent(l);
		intent.setPackage(i.getPackageName());
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Sending Broadcast!!!!: ").append(intent).append(", Thread: ").append(Thread.currentThread()).toString());
		i.sendBroadcast(intent);
	}

}
