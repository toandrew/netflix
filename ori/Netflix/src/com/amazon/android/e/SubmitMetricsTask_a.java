package com.amazon.android.e;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.task.command.AbstractCommandTask;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.q.Metric_b;
import com.amazon.android.q.MetricsManager_d;
import com.amazon.venezia.command.FailureResult;
import com.amazon.venezia.command.SuccessResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class SubmitMetricsTask_a extends AbstractCommandTask {

    private static final KiwiLogger a = new KiwiLogger("SubmitMetricsTask");
    private MetricsManager_d b;
    private com.amazon.android.q.MetricBatch_a c;

    public SubmitMetricsTask_a() {
    }

    protected final Map getCommandData() {
        HashMap hashmap = new HashMap();
        ArrayList arraylist = new ArrayList(c.b());
        for (Iterator iterator = c.iterator(); iterator.hasNext(); arraylist
                .add(((Metric_b) iterator.next()).a))
            ;
        hashmap.put("metrics", arraylist);
        return hashmap;
    }

    protected final String getCommandName() {
        return "submit_metrics";
    }

    protected final String getCommandVersion() {
        return "1.0";
    }

    protected final boolean isExecutionNeeded() {
        return !c.a();
    }

    protected final void onFailure(FailureResult failureresult)
            throws RemoteException, KiwiException {
    }

    protected final void onSuccess(SuccessResult successresult)
            throws RemoteException, KiwiException {
    }

    protected final void preExecution() throws KiwiException {
        c = b.a();
        if (KiwiLogger.TRACE_ON) {
            a.trace("--------------- SUBMIT METRICS -------------------");
            a.trace((new StringBuilder()).append("Size: ").append(c.b())
                    .toString());
            a.trace("--------------------------------------------------");
        }
    }

}
