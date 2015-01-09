package com.amazon.android.framework.task.pipeline;

import android.os.*;
import com.amazon.android.framework.task.Task;
import com.amazon.android.framework.util.KiwiLogger;
import java.util.*;

// Referenced classes of package com.amazon.android.framework.task.pipeline:
//			f, d

public final class SimpleTaskPipeline_a implements f {

    private static final KiwiLogger a = new KiwiLogger("SimpleTaskPipeline");
    private final Handler b;
    private final Set c;
    private final String d;

    private SimpleTaskPipeline_a(HandlerThread handlerthread) {
        c = Collections.synchronizedSet(new HashSet());
        d = handlerthread.getName();
        handlerthread.start();
        b = new Handler(handlerthread.getLooper());
    }

    private SimpleTaskPipeline_a(String s) {
        c = Collections.synchronizedSet(new HashSet());
        d = s;
        b = new Handler();
    }

    public static SimpleTaskPipeline_a a(String s) {
        return new SimpleTaskPipeline_a(new HandlerThread((new StringBuilder())
                .append("KIWI_").append(s).toString()));
    }

    static Set a(SimpleTaskPipeline_a a1) {
        return a1.c;
    }

    public static SimpleTaskPipeline_a b(String s) {
        return new SimpleTaskPipeline_a(s);
    }

    static KiwiLogger b() {
        return a;
    }

    static String b(SimpleTaskPipeline_a a1) {
        return a1.d;
    }

    private Runnable c(Task task) {
        d d1 = new d(this, task);
        c.add(d1);
        return d1;
    }

    public final void a() {
        Runnable runnable;
        for (Iterator iterator = c.iterator(); iterator.hasNext(); b
                .removeCallbacks(runnable)) {
            runnable = (Runnable) iterator.next();
            if (KiwiLogger.TRACE_ON)
                a.trace((new StringBuilder()).append(d)
                        .append(": Removing callback: ").append(runnable)
                        .toString());
        }

        c.clear();
        if (b.getLooper() != Looper.getMainLooper()
                && b.getLooper().getThread().isAlive()) {
            a.trace("Interrupting looper thread!");
            b.getLooper().getThread().interrupt();
            a.trace((new StringBuilder()).append("Quitting looper: ")
                    .append(b.getLooper().getThread()).append(", ")
                    .append(b.getLooper().getThread().isAlive()).toString());
            b.getLooper().quit();
        }
    }

    public final void a(Task task) {
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder()).append("Scheduling task: ")
                    .append(task).toString());
        b.post(c(task));
    }

    public final void a(Task task, long l) {
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder()).append(d)
                    .append(": Scheduling task: ").append(task)
                    .append(", with delay: ").append(l).toString());
        b.postDelayed(c(task), l);
    }

    public final void a(Task task, Date date) {
        long l = SystemClock.uptimeMillis()
                + (date.getTime() - System.currentTimeMillis());
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder()).append(d)
                    .append(": Scheduling task: ").append(task)
                    .append(", at time: ").append(date)
                    .append(", System uptimeMillis: ")
                    .append(System.currentTimeMillis())
                    .append(", uptimeMillis: ").append(l).toString());
        b.postAtTime(c(task), l);
    }

    public final void b(Task task) {
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder()).append(d)
                    .append(": Scheduling task immediately: ").append(task)
                    .toString());
        b.postAtFrontOfQueue(c(task));
    }

}
