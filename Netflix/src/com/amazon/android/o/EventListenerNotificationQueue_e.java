// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.o;

import com.amazon.android.framework.util.KiwiLogger;
import java.util.*;

// Referenced classes of package com.amazon.android.o:
//			c, a, d

public final class EventListenerNotificationQueue_e
	implements Comparator
{

	private static final KiwiLogger a = new KiwiLogger("EventListenerNotificationQueue");
	private List b;

	public EventListenerNotificationQueue_e()
	{
		b = new ArrayList();
	}

	public final void a(c c1)
	{
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Adding listener: ").append(c1).toString());
		b.add(c1);
		Collections.sort(b, this);
	}

	public final void a(d d)
	{
		c c1;
		for (Iterator iterator = b.iterator(); iterator.hasNext(); c1.a(d))
		{
			c1 = (c)iterator.next();
			if (KiwiLogger.TRACE_ON)
				a.trace((new StringBuilder()).append("Notifying listener: ").append(c1).toString());
		}

	}

	public final volatile int compare(Object obj, Object obj1)
	{
		c c1 = (c)obj;
		c c2 = (c)obj1;
		return c1.b().compareTo(c2.b());
	}

}
