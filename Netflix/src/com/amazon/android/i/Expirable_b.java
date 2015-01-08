// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.i;

import com.amazon.android.framework.task.TaskManager;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.android.framework.util.KiwiLogger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

// Referenced classes of package com.amazon.android.i:
//			d, a

public abstract class Expirable_b
	implements com.amazon.android.framework.resource.ResultManager_b
{

	private static final KiwiLogger LOGGER = new KiwiLogger("Expirable");
	private AtomicBoolean expired;
	private final List observers = new Vector();
	protected TaskManager taskManager;

	public Expirable_b()
	{
		expired = new AtomicBoolean(false);
	}

	private void notifyObservers()
	{
		for (Iterator iterator = observers.iterator(); iterator.hasNext(); ((d)iterator.next()).observe(this));
	}

	private void scheduleExpiration()
	{
	    ExpireTask_a a1 = new ExpireTask_a(this);
		taskManager.enqueueAtTime(TaskPipelineId.BACKGROUND, a1, getExpiration());
	}

	public final void discard()
	{
		if (!expired.compareAndSet(false, true))
		{
			return;
		} else
		{
			notifyObservers();
			return;
		}
	}

	protected abstract void doExpiration();

	public void expire()
	{
		if (!expired.compareAndSet(false, true))
			return;
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Expiring: ").append(this).toString());
		doExpiration();
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Notifying Observers of expiration: ").append(this).toString());
		notifyObservers();
	}

	protected abstract Date getExpiration();

	protected boolean isExpired()
	{
		return expired.get();
	}

	public final void onResourcesPopulated()
	{
		scheduleExpiration();
		onResourcesPopulatedImpl();
	}

	protected void onResourcesPopulatedImpl()
	{
	}

	public final void register(d d1)
	{
		observers.add(d1);
	}

}
