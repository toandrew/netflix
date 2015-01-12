package com.amazon.android.framework.prompt;

import com.amazon.android.j.ApplicationState_d;
import com.amazon.android.o.Order_a;
import com.amazon.android.o.c;
import com.amazon.android.o.f;

// Referenced classes of package com.amazon.android.framework.prompt:
//			PromptManagerImpl

final class i implements c {

    private PromptManagerImpl a;

    i(PromptManagerImpl promptmanagerimpl) {
        a = promptmanagerimpl;
    }

    public final f a() {
        return ApplicationState_d.DESTROY;
    }

    public final void a(com.amazon.android.o.d d1) {
        com.amazon.android.framework.prompt.PromptManagerImpl.a(a);
    }

    public final Order_a b() {
        return Order_a.FIRST_a;
    }
}
