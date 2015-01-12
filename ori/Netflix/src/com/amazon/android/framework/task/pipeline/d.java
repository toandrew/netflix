package com.amazon.android.framework.task.pipeline;

import android.os.SystemClock;
import com.amazon.android.framework.task.Task;
import com.amazon.android.framework.util.KiwiLogger;
import java.util.Date;
import java.util.Set;

// Referenced classes of package com.amazon.android.framework.task.pipeline:
//			a

final class d implements Runnable {

    private Task a;
    private SimpleTaskPipeline_a b;

    d(SimpleTaskPipeline_a a1, Task task) {
        b = a1;
        a = task;
    }

    public final void run() {
        try {
            com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                    .a(b).remove(this);
            if (KiwiLogger.TRACE_ON)
                com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                        .b()
                        .trace((new StringBuilder())
                                .append(com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                                        .b(b)).append(": Executing Task: ")
                                .append(a).append(", current time: ")
                                .append(new Date()).append(", uptime: ")
                                .append(SystemClock.uptimeMillis()).toString());
            a.execute();
        } catch (Throwable throwable) {
            if (KiwiLogger.ERROR_ON)
                com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                        .b()
                        .error((new StringBuilder())
                                .append("Task Failed with unhandled exception: ")
                                .append(throwable).toString(), throwable);
            if (KiwiLogger.TRACE_ON)
                com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                        .b()
                        .trace((new StringBuilder())
                                .append(com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                                        .b(b))
                                .append(": Task finished executing: ")
                                .append(a).toString());
        }
        if (KiwiLogger.TRACE_ON)
            com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                    .b()
                    .trace((new StringBuilder())
                            .append(com.amazon.android.framework.task.pipeline.SimpleTaskPipeline_a
                                    .b(b))
                            .append(": Task finished executing: ").append(a)
                            .toString());
    }

    public final String toString() {
        return a.toString();
    }
}
