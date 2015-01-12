package com.amazon.android.s;

import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.k.b;
import com.amazon.android.k.c;
import java.util.Iterator;

public class LicenseVerFailureKiwiException_a extends KiwiException {

    private static final long serialVersionUID = 1L;
    public final com.amazon.android.k.Verifier_a a;

    public LicenseVerFailureKiwiException_a(com.amazon.android.k.Verifier_a a1) {
        super("LICENSE_VERIFICATION_FAILURE", "VERIFICATION_ERRORS", a(a1));
        com.amazon.android.d.a
                .a(a1.a(),
                        "Created a verification exception with a Verifier that has no errors");
        a = a1;
    }

    private static String a(com.amazon.android.k.Verifier_a a1) {
        StringBuilder stringbuilder = new StringBuilder();
        b b1;
        for (Iterator iterator = a1.iterator(); iterator.hasNext(); stringbuilder
                .append(b1.a.a())) {
            b1 = (b) iterator.next();
            if (stringbuilder.length() != 0)
                stringbuilder.append(",");
        }

        return stringbuilder.toString();
    }

    public String toString() {
        return a.toString();
    }
}
