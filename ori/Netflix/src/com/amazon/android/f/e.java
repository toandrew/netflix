package com.amazon.android.f;

import android.app.Activity;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.j.b;
import com.amazon.android.o.Order_a;
import com.amazon.android.o.c;
import com.amazon.android.o.d;
import com.amazon.android.o.f;
import java.util.concurrent.atomic.AtomicReference;

// Referenced classes of package com.amazon.android.f:
//			c, a

final class e implements c {

    private com.amazon.android.f.ActivityResultManagerImpl_c a;

    e(com.amazon.android.f.ActivityResultManagerImpl_c c1) {
        a = c1;
    }

    public final f a() {
        return com.amazon.android.j.ActivityState_c.RESUME_c;
    }

    public final void a(d d) {
        b b1 = (b) d;
        com.amazon.android.f.a a1 = (com.amazon.android.f.a) com.amazon.android.f.ActivityResultManagerImpl_c
                .a(a).get();
        if (a1 != null) {
            Activity activity = b1.a;
            com.amazon.android.f.ActivityResultManagerImpl_c.a().error(
                    "Context changed while awaiting result!");
            if (a1.b != null) {
                com.amazon.android.f.ActivityResultManagerImpl_c
                        .a()
                        .error((new StringBuilder())
                                .append("Finishing activity from old context: ")
                                .append(a1.b).toString());
                a1.b.finishActivity(a1.a);
            }
            a1.a(activity);
        }
    }

    public final Order_a b() {
        return Order_a.MIDDLE_b;
    }
}
