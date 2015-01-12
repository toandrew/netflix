package com.amazon.android.i;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.i:
//			b

final class ExpireTask_a implements Task {

    private Expirable_b a;

    ExpireTask_a(Expirable_b b1) {
        a = b1;
    }

    public final void execute() {
        a.expire();
    }

    public final String toString() {
        return (new StringBuilder()).append("Expire: ").append(a.toString())
                .toString();
    }
}
