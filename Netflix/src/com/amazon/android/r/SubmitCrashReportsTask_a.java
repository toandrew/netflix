// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.r;

import android.os.RemoteException;
import com.amazon.android.c.b;
import com.amazon.android.c.d;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.task.command.AbstractCommandTask;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.venezia.command.FailureResult;
import com.amazon.venezia.command.SuccessResult;
import java.util.*;

public final class SubmitCrashReportsTask_a extends AbstractCommandTask
{

	private static final KiwiLogger a = new KiwiLogger("SubmitCrashReportsTask");
	private d b;
	private List c;

	public SubmitCrashReportsTask_a()
	{
	}

	protected final Map getCommandData()
	{
		HashMap hashmap = new HashMap();
		ArrayList arraylist = new ArrayList();
		for (Iterator iterator = c.iterator(); iterator.hasNext(); arraylist.add(((ResultManager_b)iterator.next()).a()));
		hashmap.put("reports", arraylist);
		return hashmap;
	}

	protected final String getCommandName()
	{
		return "submit_crash_reports";
	}

	protected final String getCommandVersion()
	{
		return "1.0";
	}

	protected final boolean isExecutionNeeded()
	{
		return !c.isEmpty();
	}

	protected final void onFailure(FailureResult failureresult)
		throws RemoteException, KiwiException
	{
	}

	protected final void onSuccess(SuccessResult successresult)
		throws RemoteException, KiwiException
	{
		b.a(c);
	}

	protected final void preExecution()
		throws KiwiException
	{
		c = b.a();
		if (KiwiLogger.TRACE_ON)
		{
			a.trace("--------------- Crash Reports -------------------");
			a.trace((new StringBuilder()).append("Size: ").append(c.size()).toString());
			a.trace("--------------------------------------------------");
		}
	}

}
