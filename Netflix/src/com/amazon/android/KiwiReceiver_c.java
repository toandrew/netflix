package com.amazon.android;

import android.content.*;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.n.DataStore_a;
import com.amazon.android.o.EventManager_g;

// Referenced classes of package com.amazon.android:
//			Kiwi, a

final class KiwiReceiver_c extends BroadcastReceiver {

    private Kiwi a;

    KiwiReceiver_c(Kiwi kiwi) {
        a = kiwi;
    }

    public final void onReceive(Context context, Intent intent) {
        if (com.amazon.android.Kiwi.a(a).b("TEST_MODE"))
            return;
        if (KiwiLogger.TRACE_ON)
            com.amazon.android.Kiwi.a().trace(
                    "Enable test mode broadcast received!");
        KiwiLogger.enableTest();
        com.amazon.android.Kiwi.a().test("Enabling test mode!");
        com.amazon.android.Kiwi.a().test(
                (new StringBuilder()).append("drm enabled: ").append(Kiwi.b(a))
                        .toString());
        com.amazon.android.Kiwi.a(a).a("TEST_MODE", Boolean.valueOf(true));
        Kiwi.c(a).a(new com.amazon.android.c_a());
    }
}
