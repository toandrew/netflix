package com.amazon.android.framework.task;

import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import java.util.Date;

public interface TaskManager {

    public abstract void enqueue(TaskPipelineId taskpipelineid, Task task);

    public abstract void enqueueAfterDelay(TaskPipelineId taskpipelineid,
            Task task, long l);

    public abstract void enqueueAtFront(TaskPipelineId taskpipelineid, Task task);

    public abstract void enqueueAtTime(TaskPipelineId taskpipelineid,
            Task task, Date date);
}
