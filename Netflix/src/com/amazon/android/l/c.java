package com.amazon.android.l;

import com.amazon.android.d.a;

// Referenced classes of package com.amazon.android.l:
//			a, b

public abstract class c implements com.amazon.android.l.a {

    private TaskWorkflow_b workflow;

    public c() {
    }

    protected final boolean isWorkflowChild() {
        return workflow != null;
    }

    protected final void quitParentWorkflow() {
        a.a(isWorkflowChild(), "task is no a workflow child");
        workflow.c();
    }

    public final void setWorkflow(TaskWorkflow_b b1) {
        a.a(b1, "workflow");
        boolean flag;
        if (workflow == null)
            flag = true;
        else
            flag = false;
        a.a(flag, "workflow instance can only be set once");
        workflow = b1;
    }
}
