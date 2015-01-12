package com.amazon.android.licensing;

import com.amazon.android.e.SubmitMetricsTask_a;
import com.amazon.android.l.TaskWorkflow_b;

// Referenced classes of package com.amazon.android.licensing:
//			l, b

public final class LicenseEnforcementTaskWorkflow_e extends TaskWorkflow_b {

    private com.amazon.android.framework.task.command.CommandServiceClient_b a;

    public LicenseEnforcementTaskWorkflow_e() {
        a(new VerifyApplicationEntitlmentTask_l());
        a(new SubmitMetricsTask_a());
        a(new com.amazon.android.licensing.LicenseKillTask_b());
    }

    protected final void a() {
        if (!isWorkflowChild())
            a.a();
    }

    protected final String b() {
        return "LicenseEnforcementTaskWorkflow";
    }
}
