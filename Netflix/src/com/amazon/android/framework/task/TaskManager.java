// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task;

import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import java.util.Date;

// Referenced classes of package com.amazon.android.framework.task:
//			Task

public interface TaskManager
{

	public abstract void enqueue(TaskPipelineId taskpipelineid, Task task);

	public abstract void enqueueAfterDelay(TaskPipelineId taskpipelineid, Task task, long l);

	public abstract void enqueueAtFront(TaskPipelineId taskpipelineid, Task task);

	public abstract void enqueueAtTime(TaskPipelineId taskpipelineid, Task task, Date date);
}
