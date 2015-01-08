package com.amazon.android.framework.task;

import com.amazon.android.j.ApplicationState_d;
import com.amazon.android.o.Order_a;
import com.amazon.android.o.c;
import com.amazon.android.o.f;

// Referenced classes of package com.amazon.android.framework.task:
//			a

final class g implements c {

    private com.amazon.android.framework.task.TaskManagerImpl_a a;

    g(com.amazon.android.framework.task.TaskManagerImpl_a a1) {
        a = a1;
    }

    public final f a() {
        return ApplicationState_d.DESTROY;
    }

    public final void a(com.amazon.android.o.d d1) {
        a.a();
    }

    public final Order_a b() {
        return Order_a.MIDDLE_b;
    }
}
