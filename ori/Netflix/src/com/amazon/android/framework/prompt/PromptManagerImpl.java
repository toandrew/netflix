package com.amazon.android.framework.prompt;

import android.app.Activity;
import android.app.Dialog;
import com.amazon.android.framework.context.ContextManager;
import com.amazon.android.framework.resource.ResourceManager_a;
import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.task.TaskManager;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.o.EventManager_g;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

// Referenced classes of package com.amazon.android.framework.prompt:
//			PromptManager, Prompt, f, i, 
//			j, e, h

public class PromptManagerImpl implements PromptManager, b {

    public static final KiwiLogger LOGGER = new KiwiLogger("PromptManagerImpl");
    private ContextManager contextManager;
    private EventManager_g eventManager;
    private final AtomicBoolean finished = new AtomicBoolean(false);
    private Set pending;
    private ResourceManager_a resourceManager;
    private Prompt showing;
    private TaskManager taskManager;

    public PromptManagerImpl() {
        pending = new LinkedHashSet();
    }

    static void a(PromptManagerImpl promptmanagerimpl) {
        promptmanagerimpl.finish();
    }

    static void a(PromptManagerImpl promptmanagerimpl, Activity activity) {
        promptmanagerimpl.onResume(activity);
    }

    static void a(PromptManagerImpl promptmanagerimpl, Prompt prompt) {
        promptmanagerimpl.presentImpl(prompt);
    }

    static Prompt b(PromptManagerImpl promptmanagerimpl) {
        return promptmanagerimpl.showing;
    }

    static void b(PromptManagerImpl promptmanagerimpl, Prompt prompt) {
        promptmanagerimpl.removeExpiredPrompt(prompt);
    }

    private void finish() {
        if (finished.compareAndSet(false, true)) {
            if (KiwiLogger.TRACE_ON)
                LOGGER.trace("PromptManager finishing....");
            Prompt prompt;
            for (Iterator iterator = pending.iterator(); iterator.hasNext(); prompt
                    .expire()) {
                prompt = (Prompt) iterator.next();
                iterator.remove();
            }

            if (showing != null) {
                showing.dismiss();
                return;
            }
        }
    }

    private Prompt getNextPending() {
        if (pending.isEmpty())
            return null;
        else
            return (Prompt) pending.iterator().next();
    }

    private void onResume(Activity activity) {
        if (showing != null) {
            show(showing, activity);
            return;
        } else {
            presentNextPending(activity);
            return;
        }
    }

    private void presentImpl(Prompt prompt) {
        if (finished.get()) {
            if (KiwiLogger.ERROR_ON)
                LOGGER.error((new StringBuilder()).append("Prompt: ")
                        .append(prompt).append(" presented after app")
                        .append(" destruction expiring it now!").toString());
            prompt.expire();
            return;
        }

        if (KiwiLogger.TRACE_ON)
            LOGGER.trace((new StringBuilder()).append("Presening Prompt: ")
                    .append(prompt).toString());
        prompt.register(this);
        pending.add(prompt);
        if (showing != null) {
            if (KiwiLogger.TRACE_ON) {
                LOGGER.trace("Dialog currently showing, not presenting given dialog");
                return;
            }
            return;
        }

        Activity activity = contextManager.getVisible();
        if (activity != null) {
            presentNextPending(activity);
            return;
        }
    }

    private void presentNextPending(Activity activity) {
        Prompt prompt = getNextPending();
        if (prompt == null) {
            return;
        } else {
            show(prompt, activity);
            return;
        }
    }

    private void registerActivityResumedListener() {
        eventManager.a(new f(this));
    }

    private void registerAppDestructionListener() {
        i k = new i(this);
        eventManager.a(k);
    }

    private void registerTestModeListener() {
        j j1 = new j(this);
        eventManager.a(j1);
    }

    private void removeExpiredPrompt(Prompt prompt) {
        pending.remove(prompt);
        if (showing == prompt) {
            showing = null;
            Activity activity = contextManager.getVisible();
            if (activity != null)
                presentNextPending(activity);
        }
    }

    private void show(Prompt prompt, Activity activity) {
        showing = prompt;
        prompt.show(activity);
    }

    public void observe(Prompt prompt) {
        RemoveExpiredPrompt_e e1 = new RemoveExpiredPrompt_e(this, prompt);
        taskManager.enqueue(TaskPipelineId.FOREGROUND, e1);
    }

    public void observe(com.amazon.android.i.Expirable_b b1) {
        observe((Prompt) b1);
    }

    public Dialog onCreateDialog(Activity activity, int k) {
        if (KiwiLogger.TRACE_ON)
            LOGGER.trace((new StringBuilder()).append("onCreateDialog, id: ")
                    .append(k).append(", activity: ").append(activity)
                    .toString());
        if (showing == null) {
            if (KiwiLogger.TRACE_ON)
                LOGGER.trace("Showing dialog is null, returning");
            return null;
        }
        if (showing.getIdentifier() != k) {
            if (KiwiLogger.TRACE_ON)
                LOGGER.trace((new StringBuilder())
                        .append("Showing dialog id does not match given id: ")
                        .append(k).append(", returning").toString());
            return null;
        }
        if (KiwiLogger.TRACE_ON)
            LOGGER.trace((new StringBuilder())
                    .append("Creating dialog prompt: ").append(showing)
                    .toString());
        return showing.create(activity);
    }

    public void onResourcesPopulated() {
        registerActivityResumedListener();
        registerAppDestructionListener();
        registerTestModeListener();
    }

    public void onWindowFocusChanged(Activity activity, boolean flag) {
        if (showing != null)
            showing.onFocusChanged(activity, flag);
    }

    public void present(Prompt prompt) {
        if (KiwiLogger.TRACE_ON)
            LOGGER.trace((new StringBuilder())
                    .append("Scheduling presentation: ").append(prompt)
                    .toString());
        resourceManager.b(prompt);
        if (finished.get()) {
            if (KiwiLogger.ERROR_ON)
                LOGGER.error((new StringBuilder()).append("Prompt: ")
                        .append(prompt).append(" presented after app")
                        .append(" destruction expiring it now!").toString());
            prompt.expire();
            return;
        } else {
            MainThreadPromptTask_h h1 = new MainThreadPromptTask_h(this, prompt);
            taskManager.enqueue(TaskPipelineId.FOREGROUND, h1);
            return;
        }
    }

}
