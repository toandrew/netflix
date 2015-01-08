// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.t;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.task.command.AbstractCommandTask;
import com.amazon.venezia.command.FailureResult;
import com.amazon.venezia.command.SuccessResult;
import java.util.Map;

public final class a extends AbstractCommandTask
{

	private Map a;

	public a(Map map)
	{
		a = map;
	}

	protected final Map getCommandData()
	{
		return a;
	}

	protected final String getCommandName()
	{
		return "lifeCycle_Events";
	}

	protected final String getCommandVersion()
	{
		return "1.0";
	}

	protected final boolean isExecutionNeeded()
	{
		return true;
	}

	protected final void onFailure(FailureResult failureresult)
		throws RemoteException, KiwiException
	{
	}

	protected final void onSuccess(SuccessResult successresult)
		throws RemoteException, KiwiException
	{
	}
}
