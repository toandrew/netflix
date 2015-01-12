package com.amazon.android.b;

import com.amazon.android.framework.task.command.FailedReason_a;

public final class MiscKiwiException_c extends Exception {

    private static final long serialVersionUID = 1L;
    public final FailedReason_a a;

    public MiscKiwiException_c(FailedReason_a a1) {
        a = a1;
    }
}
