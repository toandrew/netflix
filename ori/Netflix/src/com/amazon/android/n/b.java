package com.amazon.android.n;

import java.util.Date;

public abstract class b extends com.amazon.android.i.Expirable_b {

    final Object a;
    private final Date b;

    public b(Object obj, Date date) {
        a = obj;
        b = date;
    }

    public final Date getExpiration() {
        return b;
    }
}
