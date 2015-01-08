package com.amazon.android.b;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.venezia.command.j;

public class ResultFailureKiwiException_e extends KiwiException {

    private static final long serialVersionUID = 1L;

    public ResultFailureKiwiException_e(j j1) throws RemoteException {
        super("EXCEPTION_RESULT_FAILURE", j1.a());
    }
}
