// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.n;

import com.amazon.android.d.a;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.i.b;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.amazon.android.n:
//			b, c

public final class ExpirableValueDataStore_d
	implements com.amazon.android.i.d
{

	private static final KiwiLogger a = new KiwiLogger("ExpirableValueDataStore");
	private com.amazon.android.framework.resource.a b;
	private final Map c = new HashMap();

	public ExpirableValueDataStore_d()
	{
	}

	static KiwiLogger a()
	{
		return a;
	}

	private void a(com.amazon.android.n.b b1)
	{
		this;
		JVM INSTR monitorenter ;
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Observed expiration: ").append(b1).append(" removing from store!").toString());
		Iterator iterator = c.entrySet().iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			if (((java.util.Map.Entry)iterator.next()).getValue() == b1)
			{
				if (KiwiLogger.TRACE_ON)
					a.trace((new StringBuilder()).append("Removing entry from store: ").append(b1).toString());
				iterator.remove();
			}
		} while (true);
		break MISSING_BLOCK_LABEL_125;
		Exception exception;
		exception;
		throw exception;
		this;
		JVM INSTR monitorexit ;
	}

	public final void a(String s, com.amazon.android.n.b b1)
	{
		this;
		JVM INSTR monitorenter ;
		com.amazon.android.d.a.a(s, "key");
		com.amazon.android.d.a.a(b1, "value");
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Placing value into store with key: ").append(s).append(", expiration: ").append(b1.getExpiration()).toString());
		b.b(b1);
		b1.register(this);
		c.put(s, b1);
		this;
		JVM INSTR monitorexit ;
		return;
		Exception exception;
		exception;
		throw exception;
	}

	public final void a(String s, Object obj)
	{
		this;
		JVM INSTR monitorenter ;
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Placing non-expiring value into store with key: ").append(s).toString());
		c c1 = new c(this, obj, new Date());
		c.put(s, c1);
		this;
		JVM INSTR monitorexit ;
		return;
		Exception exception;
		exception;
		throw exception;
	}

	public final boolean a(String s)
	{
		this;
		JVM INSTR monitorenter ;
		Object obj = b(s);
		boolean flag;
		if (obj != null)
			flag = true;
		else
			flag = false;
		this;
		JVM INSTR monitorexit ;
		return flag;
		Exception exception;
		exception;
		throw exception;
	}

	public final Object b(String s)
	{
		this;
		JVM INSTR monitorenter ;
		com.amazon.android.n.b b1;
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Fetching value: ").append(s).toString());
		b1 = (com.amazon.android.n.b)c.get(s);
		if (b1 != null) goto _L2; else goto _L1
_L1:
		if (KiwiLogger.TRACE_ON)
			a.trace("Value not present in store, returning null");
		Object obj = null;
_L4:
		this;
		JVM INSTR monitorexit ;
		return obj;
_L2:
		obj = b1.a;
		if (true) goto _L4; else goto _L3
_L3:
		Exception exception;
		exception;
		throw exception;
	}

	public final void c(String s)
	{
		this;
		JVM INSTR monitorenter ;
		com.amazon.android.n.b b1 = (com.amazon.android.n.b)c.get(s);
		if (b1 != null) goto _L2; else goto _L1
_L1:
		this;
		JVM INSTR monitorexit ;
		return;
_L2:
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Removing value associated with key: ").append(s).append(", value: ").append(b1).toString());
		c.remove(s);
		b1.discard();
		if (true) goto _L1; else goto _L3
_L3:
		Exception exception;
		exception;
		throw exception;
	}

	public final volatile void observe(b b1)
	{
		a((com.amazon.android.n.b)b1);
	}

}
