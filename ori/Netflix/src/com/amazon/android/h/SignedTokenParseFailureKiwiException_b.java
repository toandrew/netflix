package com.amazon.android.h;

import com.amazon.android.framework.exception.KiwiException;

public class SignedTokenParseFailureKiwiException_b extends KiwiException {

    private static final long serialVersionUID = 1L;

    public SignedTokenParseFailureKiwiException_b(String s, String s1) {
        super("SIGNED_TOKEN_PARSE_FAILURE", s, s1);
    }

    public static SignedTokenParseFailureKiwiException_b a() {
        return new SignedTokenParseFailureKiwiException_b("INVALID_FORMAT",
                null);
    }
}
