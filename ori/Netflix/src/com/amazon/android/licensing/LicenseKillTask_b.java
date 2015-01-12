package com.amazon.android.licensing;

import com.amazon.android.framework.prompt.PromptContent;
import com.amazon.android.framework.prompt.PromptManager;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.l.c;
import com.amazon.android.n.DataStore_a;
import com.amazon.android.n.ExpirableValueDataStore_d;

// Referenced classes of package com.amazon.android.licensing:
//			i

public final class LicenseKillTask_b extends c {

    private static final KiwiLogger a = new KiwiLogger("LicenseKillTask");
    private DataStore_a b;
    private PromptManager c;

    public LicenseKillTask_b() {
    }

    public final void execute() {
        if (KiwiLogger.TRACE_ON)
            a.trace("License Kill Task Executing!!!");
        if (b.b("APPLICATION_LICENSE")) {
            a.test("license verification succeeded");
            return;
        }
        if (KiwiLogger.TRACE_ON)
            a.trace("License Kill Task determined app is not licensed, killing app");
        if (isWorkflowChild())
            quitParentWorkflow();
        PromptContent promptcontent = (PromptContent) b
                .a("LICENSE_FAILURE_CONTENT");
        com.amazon.android.framework.prompt.ShutdownPrompt_c c1;
        if (promptcontent != null) {
            if (KiwiLogger.TRACE_ON)
                a.trace((new StringBuilder())
                        .append("Fetched failure content from store: ")
                        .append(promptcontent).toString());
            b.a.c("LICENSE_FAILURE_CONTENT");
        } else {
            promptcontent = AllPromptContents_i.e;
        }
        c1 = new com.amazon.android.framework.prompt.ShutdownPrompt_c(
                promptcontent);
        c.present(c1);
    }

}
