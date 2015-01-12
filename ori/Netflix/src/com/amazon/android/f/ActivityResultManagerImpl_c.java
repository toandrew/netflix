package com.amazon.android.f;

import android.content.Intent;
import com.amazon.android.framework.context.ContextManager;
import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.task.TaskManager;
import com.amazon.android.framework.task.pipeline.TaskPipelineId;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.o.EventManager_g;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

// Referenced classes of package com.amazon.android.f:
//			b, a, d, f, 
//			e

public final class ActivityResultManagerImpl_c implements
        com.amazon.android.f.ResultManager_b, b {

    private static final KiwiLogger a = new KiwiLogger(
            "ActivityResultManagerImpl");
    private static final Random b = new Random();
    private TaskManager c;
    private ContextManager d;
    private EventManager_g e;
    private AtomicReference f;
    private BlockingQueue g;

    public ActivityResultManagerImpl_c() {
        f = new AtomicReference();
        g = new LinkedBlockingQueue();
    }

    static KiwiLogger a() {
        return a;
    }

    static AtomicReference a(ActivityResultManagerImpl_c c1) {
        return c1.f;
    }

    static ContextManager b(ActivityResultManagerImpl_c c1) {
        return c1.d;
    }

    public final MyActivtyResult_f a(Intent intent) {
        a a1;
        a1 = new a(intent, 1 + b.nextInt(65535));
        try {
            if (!f.compareAndSet(null, a1)) {
                a.error("StartActivityForResult called while ActivityResultManager is already awaiting a result");
                return null;
            }
            a.trace((new StringBuilder())
                    .append("Starting activity for result: ").append(intent)
                    .append(", ").append(intent.getFlags())
                    .append(", requestId: ").append(a1.a).toString());
            d d1 = new d(this, a1);
            c.enqueueAtFront(TaskPipelineId.FOREGROUND, d1);
            MyActivtyResult_f f1;
            a.trace((new StringBuilder()).append("Blocking for request: ")
                    .append(a1.a).toString());
            f1 = (MyActivtyResult_f) g.take();
            a.trace((new StringBuilder()).append("Received Response: ")
                    .append(a1.a).toString());
            f.set(null);
            return f1;
        } catch (InterruptedException interruptedexception) {
            a.trace("Interrupted while awaiting for request, returning null");
            a.trace((new StringBuilder()).append("Received Response: ")
                    .append(a1.a).toString());
            f.set(null);

        } catch (Exception exception) {
            a.trace((new StringBuilder()).append("Received Response: ")
                    .append(a1.a).toString());
            f.set(null);
        }
        return null;
    }

    public final boolean a(MyActivtyResult_f f1) {
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder()).append("Recieved ActivityResult: ")
                    .append(f1).toString());
        a a1 = (a) f.get();
        if (a1 == null) {
            if (KiwiLogger.TRACE_ON)
                a.trace("We don't have a current open request, returning");
            return false;
        }
        if (a1.a != f1.a) {
            if (KiwiLogger.TRACE_ON)
                a.trace((new StringBuilder())
                        .append("We don't have a request with code: ")
                        .append(f1.a).append(", returning").toString());
            return false;
        }
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder())
                    .append("Signaling thread waiting for request: ")
                    .append(f1.a).toString());
        g.add(f1);
        return true;
    }

    public final void onResourcesPopulated() {
        e.a(new e(this));
    }

}
