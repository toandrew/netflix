// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.o;

import com.amazon.android.d.a;
import com.amazon.android.framework.util.KiwiLogger;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.amazon.android.o:
//			g, c, e, d

public final class EventManagerImpl_b
	implements EventManager_g
{

	private static final KiwiLogger a = new KiwiLogger("EventManagerImpl");
	private final Map b = new HashMap();

	public EventManagerImpl_b()
	{
	}

	public final void a(c c1)
	{
		com.amazon.android.d.a.a(c1, "listener");
		com.amazon.android.d.a.a();
		f f = c1.a();
		a.trace((new StringBuilder()).append("Registering listener for event: ").append(f).append(", ").append(c1).toString());
		EventListenerNotificationQueue_e e1 = (EventListenerNotificationQueue_e)b.get(f);
		if (e1 == null)
		{
			e1 = new EventListenerNotificationQueue_e();
			b.put(f, e1);
		}
		e1.a(c1);
	}

	public final void a(d d1)
	{
		f f = d1.a();
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Posting event: ").append(f).toString());
		if (!b.containsKey(f))
		{
			if (KiwiLogger.TRACE_ON)
				a.trace("No registered listeners, returning");
			return;
		} else
		{
			((EventListenerNotificationQueue_e)b.get(f)).a(d1);
			return;
		}
	}

}
