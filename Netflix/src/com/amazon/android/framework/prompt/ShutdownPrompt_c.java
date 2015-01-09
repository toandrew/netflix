package com.amazon.android.framework.prompt;

import com.amazon.android.framework.context.ContextManager;
import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.util.KiwiLogger;

// Referenced classes of package com.amazon.android.framework.prompt:
//			SimplePrompt, PromptContent

public final class ShutdownPrompt_c extends SimplePrompt implements b {

    private static final KiwiLogger a = new KiwiLogger("ShutdownPrompt");
    private ContextManager b;

    public ShutdownPrompt_c(PromptContent promptcontent) {
        super(promptcontent);
    }

    protected final void doAction() {
        b.finishActivities();
        a.test("license verification failed");
        a.test("Killing application");
    }

    protected final long getExpirationDurationInSeconds() {
        return 0x1e13380L;
    }

    public final void onResourcesPopulatedImpl() {
        b.stopServices();
    }

    public final String toString() {
        return "ShutdownPrompt";
    }

}
