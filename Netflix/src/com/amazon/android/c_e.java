package com.amazon.android;

import android.app.Application;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.j.ApplicationState_d;
import com.amazon.android.o.Order_a;
import com.amazon.android.o.c;
import com.amazon.android.o.f;

// Referenced classes of package com.amazon.android:
//			Kiwi

final class c_e implements c {

    private Kiwi a;

    c_e(Kiwi kiwi) {
        a = kiwi;
    }

    public final f a() {
        return ApplicationState_d.DESTROY;
    }

    public final void a(com.amazon.android.o.d d1) {
        if (KiwiLogger.TRACE_ON) {
            com.amazon.android.Kiwi.a().trace(
                    "------------ Kiwi Killing Self ------------");
            com.amazon.android.Kiwi.a().trace(
                    (new StringBuilder()).append("Package: ")
                            .append(Kiwi.e(a).getPackageName()).toString());
            com.amazon.android.Kiwi.a().trace(
                    "-------------------------------------------");
        }
        Kiwi.b();
    }

    public final Order_a b() {
        return Order_a.LAST_c;
    }
}
