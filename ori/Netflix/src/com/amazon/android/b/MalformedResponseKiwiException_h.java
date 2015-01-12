package com.amazon.android.b;

import com.amazon.android.framework.exception.KiwiException;

public class MalformedResponseKiwiException_h extends KiwiException {

    private static final long serialVersionUID = 1L;

    public MalformedResponseKiwiException_h(String s, String s1) {
        super("MALFORMED_RESPONSE", s, s1);
    }
}
