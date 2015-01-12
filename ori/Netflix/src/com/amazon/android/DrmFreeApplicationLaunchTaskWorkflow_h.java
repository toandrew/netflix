package com.amazon.android;

import com.amazon.android.l.TaskWorkflow_b;
import com.amazon.android.r.SubmitCrashReportsTask_a;

// Referenced classes of package com.amazon.android:
//			i

public final class DrmFreeApplicationLaunchTaskWorkflow_h extends
        TaskWorkflow_b {

    public DrmFreeApplicationLaunchTaskWorkflow_h() {
        a(new CheckIfAppisBlockedTask_i());
        a(new SubmitCrashReportsTask_a());
        a(new com.amazon.android.e.SubmitMetricsTask_a());
    }

    protected final String b() {
        return "DrmFreeApplicationLaunchTaskWorkflow";
    }
}
