package com.amazon.android;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptContent;
import com.amazon.android.framework.prompt.PromptManager;
import com.amazon.android.framework.prompt.ShutdownPrompt_c;
import com.amazon.android.framework.task.command.AbstractCommandTask;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.venezia.command.FailureResult;
import com.amazon.venezia.command.SuccessResult;
import java.util.Map;

public final class CheckIfAppisBlockedTask_i extends AbstractCommandTask {

    private static final KiwiLogger a = new KiwiLogger(
            "CheckIfAppisBlockedTask");
    private PromptManager b;

    public CheckIfAppisBlockedTask_i() {
    }

    protected final Map getCommandData() {
        return null;
    }

    protected final String getCommandName() {
        return "check_blocked_status";
    }

    protected final String getCommandVersion() {
        return "1.0";
    }

    protected final boolean isExecutionNeeded() {
        return true;
    }

    protected final void onFailure(FailureResult failureresult)
            throws RemoteException, KiwiException {
        if (isWorkflowChild())
            quitParentWorkflow();
        a.test("app is blocked, killing");
        ShutdownPrompt_c c1 = new ShutdownPrompt_c(new PromptContent(
                failureresult.getDisplayableName(),
                failureresult.getDisplayableMessage(),
                failureresult.getButtonLabel(), failureresult.show()));
        b.present(c1);
    }

    protected final void onSuccess(SuccessResult successresult)
            throws RemoteException, KiwiException {
        if (successresult.getData() != null
                && successresult.getData().containsKey("verbose")) {
            boolean flag = ((Boolean) successresult.getData().get("verbose"))
                    .booleanValue();
            KiwiLogger.ERROR_ON = flag;
            KiwiLogger.TRACE_ON = flag;
        }
    }

}
