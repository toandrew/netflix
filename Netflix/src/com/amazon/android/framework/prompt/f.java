package com.amazon.android.framework.prompt;

import com.amazon.android.j.b;
import com.amazon.android.o.Order_a;
import com.amazon.android.o.c;
import com.amazon.android.o.d;

// Referenced classes of package com.amazon.android.framework.prompt:
//			PromptManagerImpl

final class f implements c {

    private PromptManagerImpl a;

    f(PromptManagerImpl promptmanagerimpl) {
        a = promptmanagerimpl;
    }

    public final com.amazon.android.o.f a() {
        return com.amazon.android.j.ActivityState_c.RESUME_c;
    }

    public final void a(d d) {
        b b1 = (b) d;
        com.amazon.android.framework.prompt.PromptManagerImpl.a(a, b1.a);
    }

    public final Order_a b() {
        return Order_a.MIDDLE_b;
    }
}
