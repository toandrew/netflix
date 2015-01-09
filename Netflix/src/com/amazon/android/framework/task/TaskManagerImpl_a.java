package com.amazon.android.framework.task;

import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.android.framework.task.pipeline.ForegroundTaskPipeline_e;
import com.amazon.android.framework.task.pipeline.f;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.o.EventManager_g;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

// Referenced classes of package com.amazon.android.framework.task:
//			TaskManager, b, c, f, 
//			d, e, g, Task

public final class TaskManagerImpl_a implements b, TaskManager {

    private static final KiwiLogger a = new KiwiLogger("TaskManagerImpl");
    private com.amazon.android.framework.resource.ResourceManager_a b;
    private EventManager_g c;
    private final AtomicBoolean d = new AtomicBoolean(false);
    private final Map e = new HashMap();

    public TaskManagerImpl_a() {
        com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a a1 = com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                .a(TaskPipelineId.COMMAND.name());
        com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a a2 = com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                .a(TaskPipelineId.BACKGROUND.name());
        ForegroundTaskPipeline_e e1 = new ForegroundTaskPipeline_e(a2);
        e.put(TaskPipelineId.COMMAND, a1);
        e.put(TaskPipelineId.BACKGROUND, a2);
        e.put(TaskPipelineId.FOREGROUND, e1);
    }

    private void a(TaskPipelineId taskpipelineid, Task task,
            com.amazon.android.framework.task.b b1) {
        if (d.get()) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Task enqueued after TaskManager has been finished! Task: ")
                        .append(task).toString());
            return;
        }
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder()).append("Populating Task: ")
                    .append(task).toString());
        b.b(task);
        f f1 = (f) e.get(taskpipelineid);
        if (f1 == null) {
            throw new IllegalStateException((new StringBuilder())
                    .append("No pipeline registered with id: ")
                    .append(taskpipelineid).toString());
        } else {
            b1.a(task, f1);
            return;
        }
    }

    public final void a() {
        if (d.compareAndSet(false, true)) {
            if (KiwiLogger.TRACE_ON)
                a.trace("TaskManager finishing....");
            Iterator iterator = e.values().iterator();
            while (iterator.hasNext())
                ((f) iterator.next()).a();
        }
    }

    public final void enqueue(TaskPipelineId taskpipelineid, Task task) {
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder())
                    .append("Enqueue task on pipeline id: ")
                    .append(taskpipelineid).toString());
        a(taskpipelineid, task, new c(this));
    }

    public final void enqueueAfterDelay(TaskPipelineId taskpipelineid,
            Task task, long l) {
        a(taskpipelineid, task,
                new com.amazon.android.framework.task.f(this, l));
    }

    public final void enqueueAtFront(TaskPipelineId taskpipelineid, Task task) {
        a(taskpipelineid, task, new d(this));
    }

    public final void enqueueAtTime(TaskPipelineId taskpipelineid, Task task,
            Date date) {
        a(taskpipelineid, task, new com.amazon.android.framework.task.e(this,
                date));
    }

    public final void onResourcesPopulated() {
        f f1;
        for (Iterator iterator = e.values().iterator(); iterator.hasNext(); b
                .b(f1))
            f1 = (f) iterator.next();

        com.amazon.android.framework.task.g g1 = new com.amazon.android.framework.task.g(
                this);
        c.a(g1);
    }

}
