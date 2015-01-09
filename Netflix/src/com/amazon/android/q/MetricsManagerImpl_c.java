package com.amazon.android.q;

import com.amazon.android.framework.util.KiwiLogger;
import java.util.List;

// Referenced classes of package com.amazon.android.q:
//			d, a, b

public final class MetricsManagerImpl_c implements MetricsManager_d {

    private static final KiwiLogger a = new KiwiLogger("MetricsManagerImpl");
    private MetricBatch_a b;

    public MetricsManagerImpl_c() {
        b = new MetricBatch_a();
    }

    public final MetricBatch_a a() {
        synchronized (this) {

            if (b.a()) {
                return b;
            }

            b = new MetricBatch_a();
            return b;

        }
    }

    public final void a(Metric_b b1) {
        synchronized (this) {
            if (KiwiLogger.TRACE_ON)
                a.trace((new StringBuilder()).append("Recording Metric: ")
                        .append(b1).toString());
            b.a.add(b1);
        }
    }

}
