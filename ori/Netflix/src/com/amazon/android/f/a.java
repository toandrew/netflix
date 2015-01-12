package com.amazon.android.f;

import android.app.Activity;
import android.content.Intent;
import com.amazon.android.framework.util.KiwiLogger;

// Referenced classes of package com.amazon.android.f:
//			c

final class a {

    final int a;
    Activity b;
    private final Intent c;

    public a(Intent intent, int i) {
        c = intent;
        a = i;
    }

    public final void a(Activity activity) {
        com.amazon.android.f.ActivityResultManagerImpl_c.a().trace(
                (new StringBuilder())
                        .append("Calling startActivityForResult from: ")
                        .append(activity).toString());
        activity.startActivityForResult(c, a);
        b = activity;
    }
}
