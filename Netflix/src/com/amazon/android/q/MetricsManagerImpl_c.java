// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.q;

import com.amazon.android.framework.util.KiwiLogger;
import java.util.List;

// Referenced classes of package com.amazon.android.q:
//			d, a, b

public final class MetricsManagerImpl_c
	implements MetricsManager_d
{

	private static final KiwiLogger a = new KiwiLogger("MetricsManagerImpl");
	private MetricBatch_a b;

	public MetricsManagerImpl_c()
	{
		b = new MetricBatch_a();
	}

	public final MetricBatch_a a()
	{
		this;
		JVM INSTR monitorenter ;
		if (!b.a()) goto _L2; else goto _L1
_L1:
        MetricBatch_a a1 = b;
_L4:
		this;
		JVM INSTR monitorexit ;
		return a1;
_L2:
		a1 = b;
		b = new MetricBatch_a();
		if (true) goto _L4; else goto _L3
_L3:
		Exception exception;
		exception;
		throw exception;
	}

	public final void a(Metric_b b1)
	{
		this;
		JVM INSTR monitorenter ;
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Recording Metric: ").append(b1).toString());
		b.a.add(b1);
		this;
		JVM INSTR monitorexit ;
		return;
		Exception exception;
		exception;
		throw exception;
	}

}
