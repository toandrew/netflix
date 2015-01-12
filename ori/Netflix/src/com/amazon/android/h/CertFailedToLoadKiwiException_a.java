package com.amazon.android.h;

import com.amazon.android.framework.exception.KiwiException;

public class CertFailedToLoadKiwiException_a extends KiwiException {

    private static final long serialVersionUID = 1L;

    public CertFailedToLoadKiwiException_a(String s, Throwable throwable) {
        super("DATA_AUTH_KEY_LOAD_FAILURE", s, throwable);
    }

    public static CertFailedToLoadKiwiException_a a(Throwable throwable) {
        return new CertFailedToLoadKiwiException_a("CERT_FAILED_TO_LOAD",
                throwable);
    }
}
