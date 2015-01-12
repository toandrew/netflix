package com.amazon.android.n;

import com.amazon.android.framework.util.KiwiLogger;
import java.util.Date;

// Referenced classes of package com.amazon.android.n:
//			b, d

final class c extends b {

    private ExpirableValueDataStore_d b;

    c(ExpirableValueDataStore_d d1, Object obj, Date date) {
        super(obj, date);
        b = d1;
    }

    protected final void doExpiration() {
        if (KiwiLogger.ERROR_ON)
            ExpirableValueDataStore_d.a().error(
                    "Woah, non-expirable value was expired!!!!");
    }
}
