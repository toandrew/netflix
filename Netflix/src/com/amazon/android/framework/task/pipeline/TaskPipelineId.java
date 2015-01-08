// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.pipeline;


public enum TaskPipelineId
{
    FOREGROUND,
    COMMAND,
    BACKGROUND
    /*
	private static final TaskPipelineId $VALUES[];
	public static final TaskPipelineId BACKGROUND;
	public static final TaskPipelineId COMMAND;
	public static final TaskPipelineId FOREGROUND;

	private TaskPipelineId(String s, int i)
	{
		super(s, i);
	}

	public static TaskPipelineId valueOf(String s)
	{
		return (TaskPipelineId)Enum.valueOf(com/amazon/android/framework/task/pipeline/TaskPipelineId, s);
	}

	public static TaskPipelineId[] values()
	{
		return (TaskPipelineId[])$VALUES.clone();
	}

	static 
	{
		FOREGROUND = new TaskPipelineId("FOREGROUND", 0);
		COMMAND = new TaskPipelineId("COMMAND", 1);
		BACKGROUND = new TaskPipelineId("BACKGROUND", 2);
		TaskPipelineId ataskpipelineid[] = new TaskPipelineId[3];
		ataskpipelineid[0] = FOREGROUND;
		ataskpipelineid[1] = COMMAND;
		ataskpipelineid[2] = BACKGROUND;
		$VALUES = ataskpipelineid;
	}
	*/
}
