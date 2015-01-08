// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;

import android.app.Application;
import android.os.RemoteException;
import com.amazon.android.b.CommandServiceKiwiException_a;
import com.amazon.android.b.ResultFailureKiwiException_e;
import com.amazon.android.f.MyActivtyResult_f;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptManager;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.l.c;
import com.amazon.android.q.Metric_b;
import com.amazon.android.q.MetricsManager_d;
import com.amazon.venezia.command.FailureResult;
import com.amazon.venezia.command.SuccessResult;
import com.amazon.venezia.command.j;
import com.amazon.venezia.command.n;
import com.amazon.venezia.command.r;
import com.amazon.venezia.command.w;
import java.util.Map;

// Referenced classes of package com.amazon.android.framework.task.command:
//			b, g, a, k, 
//			e, c

public abstract class AbstractCommandTask extends c
{

	private static final KiwiLogger LOGGER = new KiwiLogger("AbstractCommandTask");
	private Application application;
	private com.amazon.android.framework.task.command.CommandResultVerifier_e authTokenVerifier;
	private com.amazon.android.framework.task.command.CommandServiceClient_b client;
	private MetricsManager_d metricsManager;
	private PromptManager promptManager;
	protected com.amazon.android.f.ResultManager_b resultManager;

	public AbstractCommandTask()
	{
	}

	static Application a(AbstractCommandTask abstractcommandtask)
	{
		return abstractcommandtask.application;
	}

	private void expire(r r, com.amazon.android.framework.task.command.FailedReason_a a1)
		throws RemoteException, KiwiException
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Expiring Decision: ").append(r).append(", reason: ").append(a1).toString());
		handleCommandResult(client.a(r, a1));
	}

	private Metric_b extractMetric(KiwiException kiwiexception)
	{
	    Metric_b b1 = new Metric_b(getFailureMetricName());
		b1.a("subType", kiwiexception.getType()).a("reason", kiwiexception.getReason()).a("context", kiwiexception.getContext());
		return b1;
	}

	private w getCommand()
	{
		return new g(this);
	}

	private String getFailureMetricName()
	{
		return (new StringBuilder()).append(getCommandName()).append("_failure").toString();
	}

	private void handleChoice(r r, n n1)
		throws RemoteException, KiwiException
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Handling customer choice: ").append(n1).toString());
		android.content.Intent intent = n1.b();
		if (intent != null)
		{
			if (KiwiLogger.TRACE_ON)
				LOGGER.trace("Choice has intent, scheduling it to be fired!!");
			MyActivtyResult_f f1 = resultManager.a(intent);
			if (f1 == null)
			{
				if (KiwiLogger.TRACE_ON)
					LOGGER.trace("No result recived, expiring decision");
				expire(r, com.amazon.android.framework.task.command.FailedReason_a.EXPIRATION_DURATION_ELAPSED_a);
				return;
			}
			if (f1.b == 0)
			{
				if (KiwiLogger.TRACE_ON)
					LOGGER.trace("Result canceled, expiring decision");
				expire(r, com.amazon.android.framework.task.command.FailedReason_a.ACTION_CANCELED_c);
				return;
			}
			if (KiwiLogger.TRACE_ON)
				LOGGER.trace("Result received!!!, notifying service");
			handleCommandResult(client.a(n1));
			return;
		}
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace("No intent given, choosing now");
		handleCommandResult(client.a(n1));
	}

	private void handleCommandResult(k k1)
		throws KiwiException, RemoteException
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Received result from CommandService: ").append(k1).toString());
		if (k1 == null)
		{
			if (KiwiLogger.TRACE_ON)
				LOGGER.trace("Received null result from command service, exiting task");
			return;
		}
		if (k1.f != null)
		{
			handleException(k1.f);
			return;
		}
		if (k1.b != null)
			throw k1.b;
		String s = k1.a;
		authTokenVerifier.a(s);
		if (k1.c != null)
		{
			handleSuccess(k1.c);
			return;
		}
		if (k1.d != null)
		{
			handleFailure(k1.d);
			return;
		} else
		{
			handleDecision(k1.e);
			return;
		}
	}

	private void handleDecision(r r)
		throws RemoteException, KiwiException
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace("Handling Decision");
		n n1;
		com.amazon.android.framework.task.command.DecisionDialog_c c1 = new com.amazon.android.framework.task.command.DecisionDialog_c(r);
		promptManager.present(c1);
		n1 = c1.a();
		if (n1 == null)
		{
			try
			{
				if (KiwiLogger.TRACE_ON)
					LOGGER.trace("DecisionChooser returned null!!, expiring");
				expire(r, com.amazon.android.framework.task.command.FailedReason_a.EXPIRATION_DURATION_ELAPSED_a);
				return;
			}
			catch (com.amazon.android.b.MiscKiwiException_c c2)
			{
				expire(r, c2.a);
			}
			return;
		}
		handleChoice(r, n1);
		return;
	}

	private void handleException(j j)
		throws RemoteException, KiwiException
	{
		throw new ResultFailureKiwiException_e(j);
	}

	private void handleExecutionException(Throwable throwable)
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Exception occurred while processing task: ").append(throwable).toString(), throwable);
		KiwiException kiwiexception = translate(throwable);
		onException(kiwiexception);
		Metric_b b1 = extractMetric(kiwiexception);
		metricsManager.a(b1);
	}

	private void handleFailure(FailureResult failureresult)
		throws RemoteException, KiwiException
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Command failed execution: ").append(failureresult.getDisplayableName()).toString());
		onFailure(failureresult);
	}

	private void handleSuccess(SuccessResult successresult)
		throws RemoteException, KiwiException
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace("Command executed successfully");
		onSuccess(successresult);
	}

	private void postExecution()
	{
		if (!isWorkflowChild())
			client.a();
	}

	private KiwiException translate(Throwable throwable)
	{
		if (throwable instanceof KiwiException)
			return (KiwiException)throwable;
		if (throwable instanceof RemoteException)
			return new CommandServiceKiwiException_a((RemoteException)throwable);
		else
			return new com.amazon.android.b.UnhandledKiwiException_b(throwable);
	}

	public final void execute()
	{
		if (KiwiLogger.TRACE_ON)
		{
			LOGGER.trace("----------------------------------------------");
			LOGGER.trace((new StringBuilder()).append("Executing: ").append(getCommandName()).toString());
			LOGGER.trace("----------------------------------------------");
		}
		preExecution();
		if (isExecutionNeeded())
			break MISSING_BLOCK_LABEL_99;
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace("Execution not needed, quitting");
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace("Task finished");
		postExecution();
		return;
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Executing Command: ").append(getCommandName()).toString());
		handleCommandResult(client.a(getCommand()));
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace("Task finished");
		postExecution();
		return;
		Throwable throwable;
		throwable;
		handleExecutionException(throwable);
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace("Task finished");
		postExecution();
		return;
		Exception exception;
		exception;
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace("Task finished");
		postExecution();
		throw exception;
	}

	protected abstract Map getCommandData();

	protected abstract String getCommandName();

	protected abstract String getCommandVersion();

	protected abstract boolean isExecutionNeeded();

	protected void onException(KiwiException kiwiexception)
	{
		LOGGER.error((new StringBuilder()).append("On Exception!!!!: ").append(kiwiexception).toString());
	}

	protected abstract void onFailure(FailureResult failureresult)
		throws RemoteException, KiwiException;

	protected abstract void onSuccess(SuccessResult successresult)
		throws RemoteException, KiwiException;

	protected void preExecution()
		throws KiwiException
	{
	}

}
