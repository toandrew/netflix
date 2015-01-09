package com.amazon.android.framework.task.pipeline;

import com.amazon.android.o.Order_a;
import com.amazon.android.o.c;
import com.amazon.android.o.d;
import com.amazon.android.o.f;

// Referenced classes of package com.amazon.android.framework.task.pipeline:
//			e

final class b
	implements c
{

	private ForegroundTaskPipeline_e a;

	b(ForegroundTaskPipeline_e e1)
	{
		a = e1;
	}

	public final f a()
	{
		return com.amazon.android.j.ActivityState_c.RESUME_c;
	}

	public final void a(d d)
	{
		com.amazon.android.framework.task.pipeline.ForegroundTaskPipeline_e.a(a);
	}

	public final Order_a b()
	{
		return Order_a.MIDDLE_b;
	}

	public final String toString()
	{
		return "ForegroundTaskPipeline:onResume listener";
	}
}
