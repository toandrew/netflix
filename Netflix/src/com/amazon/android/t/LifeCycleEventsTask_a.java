package com.amazon.android.t;

import android.os.RemoteException;

import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.task.command.AbstractCommandTask;
import com.amazon.venezia.command.FailureResult;
import com.amazon.venezia.command.SuccessResult;

import java.util.HashMap;
import java.util.Map;

public final class LifeCycleEventsTask_a extends AbstractCommandTask {

    private Map a;

    public LifeCycleEventsTask_a(HashMap map) {
        a = map;
    }

    protected final Map getCommandData() {
        return a;
    }

    protected final String getCommandName() {
        return "lifeCycle_Events";
    }

    protected final String getCommandVersion() {
        return "1.0";
    }

    protected final boolean isExecutionNeeded() {
        return true;
    }

    protected final void onFailure(FailureResult failureresult)
            throws RemoteException, KiwiException {
    }

    protected final void onSuccess(SuccessResult successresult)
            throws RemoteException, KiwiException {
    }
}
