package com.amazon.android.framework.util;

import java.util.*;

public final class b implements Iterable {

    public WeakHashMap a;

    public b() {
        a = new WeakHashMap();
    }

    public final void a(Object obj) {
        a.put(obj, null);
    }

    public final void b(Object obj) {
        a.remove(obj);
    }

    public final Iterator iterator() {
        return a.keySet().iterator();
    }

    public final String toString() {
        return a.keySet().toString();
    }
}
