package com.amazon.android;

import com.amazon.android.licensing.LicenseEnforcementTaskWorkflow_e;
import com.amazon.android.r.SubmitCrashReportsTask_a;

// Referenced classes of package com.amazon.android:
//			i

public final class DrmFullApplicationLaunchTaskWorkflow_b extends
        com.amazon.android.l.TaskWorkflow_b {

    private com.amazon.android.framework.task.command.CommandServiceClient_b a;

    public DrmFullApplicationLaunchTaskWorkflow_b() {
        a(new CheckIfAppisBlockedTask_i());
        a(new SubmitCrashReportsTask_a());
        a(new LicenseEnforcementTaskWorkflow_e());
    }

    protected final void a() {
        if (!isWorkflowChild())
            a.a();
    }

    protected final String b() {
        return "DrmFullApplicationLaunchTaskWorkflow";
    }
}
