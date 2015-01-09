package com.amazon.android.licensing;

import com.amazon.android.b.MalformedResponseKiwiException_h;
import java.util.Map;

public final class c {

    final String a;
    final String b;
    final String c;

    public c(Map map) throws MalformedResponseKiwiException_h {
        if (map == null) {
            throw new MalformedResponseKiwiException_h("EMPTY", null);
        } else {
            a = a("license", map);
            b = a("customerId", map);
            c = a("deviceId", map);
            return;
        }
    }

    private static String a(String s, Map map)
            throws MalformedResponseKiwiException_h {
        String s1 = (String) map.get(s);
        boolean flag;
        if (s1 == null || s1.length() == 0)
            flag = true;
        else
            flag = false;
        if (flag)
            throw new MalformedResponseKiwiException_h("MISSING_FIELD", s);
        else
            return s1;
    }
}
