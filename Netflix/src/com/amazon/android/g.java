package com.amazon.android;

import com.amazon.android.j.ApplicationState_d;
import com.amazon.android.o.Order_a;
import com.amazon.android.o.c;
import com.amazon.android.o.f;

// Referenced classes of package com.amazon.android:
//			Kiwi

final class g implements c {

    private Kiwi a;

    g(Kiwi kiwi) {
        a = kiwi;
    }

    public final f a() {
        return ApplicationState_d.CREATE;
    }

    public final void a(com.amazon.android.o.d d1) {
        com.amazon.android.Kiwi.d(a);
    }

    public final Order_a b() {
        return Order_a.LAST_c;
    }
}
