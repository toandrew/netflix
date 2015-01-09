package com.amazon.android.framework.prompt;

import com.amazon.android.c_a;
import com.amazon.android.o.c;
import com.amazon.android.o.d;
import com.amazon.android.o.f;

// Referenced classes of package com.amazon.android.framework.prompt:
//			PromptManagerImpl, Prompt

final class j implements c {

    private PromptManagerImpl a;

    j(PromptManagerImpl promptmanagerimpl) {
        a = promptmanagerimpl;
    }

    public final f a() {
        return c_a.a;
    }

    public final void a(d d) {
        if (PromptManagerImpl.b(a) != null)
            PromptManagerImpl.b(a).expire();
    }

    public final com.amazon.android.o.Order_a b() {
        return com.amazon.android.o.Order_a.MIDDLE_b;
    }
}
