package com.amazon.android.framework.task.pipeline;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.framework.task.pipeline:
//			e

final class PostToUITask_c implements Task {

    private Task a;
    private ForegroundTaskPipeline_e b;

    PostToUITask_c(ForegroundTaskPipeline_e e1, Task task) {
        b = e1;
        a = task;
    }

    public final void execute() {
        ForegroundTaskPipeline_e.a(b, a);
    }

    public final String toString() {
        return (new StringBuilder()).append("Future:PostToUITask: ")
                .append(a.toString()).toString();
    }
}
