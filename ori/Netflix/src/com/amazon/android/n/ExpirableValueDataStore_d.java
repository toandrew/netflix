package com.amazon.android.n;

import com.amazon.android.d.a;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.i.Expirable_b;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.amazon.android.n:
//			b, c

public final class ExpirableValueDataStore_d implements com.amazon.android.i.d {

    private static final KiwiLogger a = new KiwiLogger(
            "ExpirableValueDataStore");
    private com.amazon.android.framework.resource.ResourceManager_a b;
    private final Map c = new HashMap();

    public ExpirableValueDataStore_d() {
    }

    static KiwiLogger a() {
        return a;
    }

    private void a(com.amazon.android.n.b b1) {
        synchronized (this) {
            if (KiwiLogger.TRACE_ON)
                a.trace((new StringBuilder()).append("Observed expiration: ")
                        .append(b1).append(" removing from store!").toString());

            try {
                Iterator iterator = c.entrySet().iterator();
                do {
                    if (!iterator.hasNext())
                        break;
                    if (((java.util.Map.Entry) iterator.next()).getValue() == b1) {
                        if (KiwiLogger.TRACE_ON)
                            a.trace((new StringBuilder())
                                    .append("Removing entry from store: ")
                                    .append(b1).toString());
                        iterator.remove();
                    }
                } while (true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void a(String s, com.amazon.android.n.b b1) {
        synchronized (this) {
            try {
                com.amazon.android.d.a.a(s, "key");
                com.amazon.android.d.a.a(b1, "value");
                if (KiwiLogger.TRACE_ON)
                    a.trace((new StringBuilder())
                            .append("Placing value into store with key: ")
                            .append(s).append(", expiration: ")
                            .append(b1.getExpiration()).toString());
                b.b(b1);
                b1.register(this);
                c.put(s, b1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void a(String s, Object obj) {
        synchronized (this) {
            if (KiwiLogger.TRACE_ON)
                a.trace((new StringBuilder())
                        .append("Placing non-expiring value into store with key: ")
                        .append(s).toString());
            try {
                c c1 = new c(this, obj, new Date());
                c.put(s, c1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public final boolean a(String s) {
        boolean flag = false;

        synchronized (this) {
            try {
                Object obj = b(s);

                if (obj != null)
                    flag = true;
                else
                    flag = false;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return flag;
    }

    public final Object b(String s) {
        synchronized (this) {
            try {
                com.amazon.android.n.b b1;
                if (KiwiLogger.TRACE_ON)
                    a.trace((new StringBuilder()).append("Fetching value: ")
                            .append(s).toString());
                b1 = (com.amazon.android.n.b) c.get(s);
                Object obj = null;
                if (b1 != null) {
                    return b1.a;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (KiwiLogger.TRACE_ON)
            a.trace("Value not present in store, returning null");

        return null;

    }

    public final void c(String s) {
        synchronized (this) {
            com.amazon.android.n.b b1 = (com.amazon.android.n.b) c.get(s);
            if (b1 == null) {
                return;
            }
            try {
                if (KiwiLogger.TRACE_ON)
                    a.trace((new StringBuilder())
                            .append("Removing value associated with key: ")
                            .append(s).append(", value: ").append(b1)
                            .toString());
                c.remove(s);
                b1.discard();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public final void observe(Expirable_b b1) {
        a((com.amazon.android.n.b) b1);
    }

}
