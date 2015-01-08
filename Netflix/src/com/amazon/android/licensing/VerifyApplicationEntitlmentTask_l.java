// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.licensing;

import android.app.Application;
import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptContent;
import com.amazon.android.framework.task.TaskManager;
import com.amazon.android.framework.task.command.AbstractCommandTask;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.k.b;
import com.amazon.android.m.c;
import com.amazon.android.m.d;
import com.amazon.android.n.DataStore_a;
import com.amazon.mas.kiwi.util.BC1;
import com.amazon.venezia.command.FailureResult;
import com.amazon.venezia.command.SuccessResult;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

// Referenced classes of package com.amazon.android.licensing:
//			LicenseFailurePromptContentMapper, c, f, k, 
//			d, n

public final class VerifyApplicationEntitlmentTask_l extends AbstractCommandTask
{

	private static final KiwiLogger a = new KiwiLogger("VerifyApplicationEntitlmentTask");
	private LicenseFailurePromptContentMapper b;
	private com.amazon.android.q.d c;
	private Application d;
	private TaskManager e;
	private DataStore_a f;
	private c g;

	public VerifyApplicationEntitlmentTask_l()
	{
		b = new LicenseFailurePromptContentMapper();
	}

	protected final Map getCommandData()
	{
		return null;
	}

	protected final String getCommandName()
	{
		return "get_license";
	}

	protected final String getCommandVersion()
	{
		return "1.0";
	}

	protected final boolean isExecutionNeeded()
	{
		return f.a("APPLICATION_LICENSE") == null;
	}

	protected final void onException(KiwiException kiwiexception)
	{
		PromptContent promptcontent = b.map(kiwiexception);
		if (promptcontent == null && KiwiLogger.ERROR_ON)
			a.error((new StringBuilder()).append("No mapping specified for exception: ").append(kiwiexception).toString(), kiwiexception);
		f.a("LICENSE_FAILURE_CONTENT", promptcontent);
	}

	protected final void onFailure(FailureResult failureresult)
		throws RemoteException, KiwiException
	{
		PromptContent promptcontent = new PromptContent(failureresult.getDisplayableName(), failureresult.getDisplayableMessage(), failureresult.getButtonLabel(), failureresult.show());
		a.trace((new StringBuilder()).append("onFailure: ").append(promptcontent).toString());
		f.a("LICENSE_FAILURE_CONTENT", promptcontent);
	}

	protected final void onSuccess(SuccessResult successresult)
		throws RemoteException, KiwiException
	{
		com.amazon.android.licensing.c c1 = new com.amazon.android.licensing.c(successresult.getData());
		java.security.PublicKey publickey = g.a();
		f f1 = new f(new d(c1.a, publickey));
		com.amazon.android.k.a a1 = new com.amazon.android.k.a();
		a1.a(c1.b, f1.b, com.amazon.android.licensing.k.b);
		a1.a(c1.c, f1.c, com.amazon.android.licensing.k.c);
		a1.a(f1.e, d.getPackageName(), com.amazon.android.licensing.k.d);
		Date date = f1.d;
		Date date1 = new Date();
		k k1 = com.amazon.android.licensing.k.a;
		if (date.compareTo(date1) <= 0)
		{
			b b1 = new b(k1, (new StringBuilder()).append("'").append(date).append("' <= '").append(date1).append("'").toString());
			a1.a.put(k1, b1);
		}
		try
		{
			String s = BC1.getBC1ChecksumBase64(d.getPackageCodePath());
			a1.a(f1.a, s, k.e);
		}
		catch (IOException ioexception)
		{
			k k2 = k.e;
			b b2 = new b(k2, (new StringBuilder()).append("Exception: ").append(ioexception).toString());
			a1.a.put(k2, b2);
		}
		if (a1.a())
		{
			throw new com.amazon.android.s.a(a1);
		} else
		{
			a.trace("License Verification succeeded!");
			com.amazon.android.licensing.d d1 = new com.amazon.android.licensing.d(this, f1, f1.d);
			f.a.a("APPLICATION_LICENSE", d1);
			c.a(new com.amazon.android.p.a());
			e.enqueue(TaskPipelineId.BACKGROUND, new n());
			return;
		}
	}

}
