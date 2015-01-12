package com.amazon.android.b;

import com.amazon.android.framework.exception.KiwiException;

public class UnhandledKiwiException_b extends KiwiException {

    private static final long serialVersionUID = 1L;

    public UnhandledKiwiException_b(Throwable throwable) {
        super("UNHANDLED_EXCEPTION", throwable);
    }
}
