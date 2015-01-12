package com.amazon.android.k;

import java.util.*;

// Referenced classes of package com.amazon.android.k:
//			b, c

public final class Verifier_a implements Iterable {

    public Map a;

    public Verifier_a() {
        a = new HashMap();
    }

    public final Verifier_a a(Object obj, Object obj1, c c) {
        boolean flag;
        if (obj == null) {
            if (obj1 == null)
                flag = true;
            else
                flag = false;
        } else {
            flag = obj.equals(obj1);
        }
        if (!flag) {
            b b1 = new b(c, (new StringBuilder()).append("'").append(obj)
                    .append("' != '").append(obj1).append("'").toString());
            a.put(c, b1);
        }
        return this;
    }

    public final boolean a() {
        return !a.isEmpty();
    }

    public final Iterator iterator() {
        return a.values().iterator();
    }

    public final String toString() {
        StringBuilder stringbuilder = new StringBuilder("Verifier:");
        b b1;
        for (Iterator iterator1 = a.values().iterator(); iterator1.hasNext(); stringbuilder
                .append((new StringBuilder()).append("\n\t").append(b1)
                        .toString()))
            b1 = (b) iterator1.next();

        return stringbuilder.toString();
    }
}
