package com.amazon.android;

import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.j.b;
import com.amazon.android.o.c;
import com.amazon.android.o.d;
import com.amazon.android.t.LifeCycleEventsTask_a;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.amazon.android:
//			Kiwi

final class f implements c {

    private Kiwi a;

    f(Kiwi kiwi) {
        a = kiwi;
    }

    public final com.amazon.android.o.f a() {
        return com.amazon.android.j.ActivityState_c.RESUME_c;
    }

    public final void a(d d) {
        b b1 = (b) d;
        HashMap hashmap = new HashMap();
        hashmap.put("EventName",
                com.amazon.android.j.ActivityState_c.RESUME_c.name());
        hashmap.put("ActivityName", b1.a.getClass().getName());
        hashmap.put("Timestamp", Long.valueOf(System.currentTimeMillis()));
        LifeCycleEventsTask_a a1 = new LifeCycleEventsTask_a(hashmap);
        if (KiwiLogger.TRACE_ON)
            com.amazon.android.Kiwi.a().trace(
                    "Adding lifecycle RESUME command to pipeline");
        Kiwi.addCommandToCommandTaskPipeline(a1);
    }

    public final com.amazon.android.o.Order_a b() {
        return com.amazon.android.o.Order_a.MIDDLE_b;
    }
}
