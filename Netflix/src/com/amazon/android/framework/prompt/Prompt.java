// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.prompt;

import android.app.Activity;
import android.app.Dialog;
import com.amazon.android.framework.task.TaskManager;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.i.RelativeExpirable_c;
import com.amazon.android.n.DataStore_a;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

// Referenced classes of package com.amazon.android.framework.prompt:
//			b, a, d

public abstract class Prompt extends RelativeExpirable_c
{

	private static final KiwiLogger LOGGER = new KiwiLogger("Prompt");
	private Activity context;
	private DataStore_a dataStore;
	private Dialog dialog;
	private AtomicBoolean dismissed;
	private final int identifier = createIdentifier();
	private PromptFailedReason_d manualExpirationReason;

	public Prompt()
	{
		dismissed = new AtomicBoolean(false);
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Creating Prompt: ").append(identifier).toString());
	}

	private int createIdentifier()
	{
		int i = 0x12d687 + (new Random()).nextInt(0x7fed2977);
		if (i <= 0x12d687)
			i = 0x12d687;
		return i;
	}

	private void dismissDialog()
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.error((new StringBuilder()).append("Dismissing dialog: ").append(identifier).toString());
		context.dismissDialog(identifier);
		context.removeDialog(identifier);
		context = null;
		dialog = null;
	}

	private void expire(PromptFailedReason_d d1)
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Expiring prompt pre-maturely: id: ").append(getIdentifier()).append(", prompt: ").append(this).append(",").append(", reason: ").append(d1).toString());
		manualExpirationReason = d1;
		expire();
	}

	private boolean isCompatible(Activity activity)
	{
		if (dataStore.b("TEST_MODE"))
			return false;
		else
			return doCompatibilityCheck(activity);
	}

	private void showDialog(Activity activity)
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Showing prompt, id: ").append(getIdentifier()).append(", prompt: ").append(this).append(", activity: ").append(activity).toString());
		if (context != null)
			dismissDialog();
		activity.showDialog(getIdentifier());
	}

	public final Dialog create(Activity activity)
	{
		context = activity;
		dialog = doCreate(activity);
		dialog.setCancelable(false);
		dialog.setOnKeyListener(new PromptKeyListener_b(this));
		return dialog;
	}

	protected final boolean dismiss()
	{
		com.amazon.android.d.a.a();
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Dismissing Prompt: ").append(identifier).toString());
		if (!dismissed.compareAndSet(false, true))
		{
			if (KiwiLogger.TRACE_ON)
				LOGGER.error("We already dismissed this prompt, nothing to do here");
			return false;
		}
		if (context != null)
			dismissDialog();
		discard();
		return true;
	}

	protected boolean doCompatibilityCheck(Activity activity)
	{
		return true;
	}

	protected abstract Dialog doCreate(Activity activity);

	protected final void doExpiration()
	{
		if (KiwiLogger.TRACE_ON)
			LOGGER.trace((new StringBuilder()).append("Expiring prompt: ").append(this).toString());
		com.amazon.android.framework.prompt.DismissPromptTask_a a1 = new com.amazon.android.framework.prompt.DismissPromptTask_a(this);
		taskManager.enqueue(TaskPipelineId.FOREGROUND, a1);
		doExpiration(getExpirationReason());
	}

	protected abstract void doExpiration(PromptFailedReason_d d1);

	protected PromptFailedReason_d getExpirationReason()
	{
		if (!isExpired())
			return null;
		if (manualExpirationReason == null)
			return PromptFailedReason_d.EXPIRATION_DURATION_ELAPSED_b;
		else
			return manualExpirationReason;
	}

	public int getIdentifier()
	{
		return identifier;
	}

	public void onFocusChanged(Activity activity, boolean flag)
	{
		if (activity != context)
		{
			if (KiwiLogger.TRACE_ON)
				LOGGER.trace("Not my context!");
		} else
		if (flag && !dialog.isShowing())
		{
			if (KiwiLogger.TRACE_ON)
				LOGGER.trace("showing dialog because it was not showing");
			dialog.show();
			return;
		}
	}

	public final void show(Activity activity)
	{
		com.amazon.android.d.a.a(activity, "activity");
		com.amazon.android.d.a.a();
		if (isCompatible(activity))
		{
			showDialog(activity);
			return;
		} else
		{
			expire(com.amazon.android.framework.prompt.PromptFailedReason_d.NOT_COMPATIBLE_a);
			return;
		}
	}

}
