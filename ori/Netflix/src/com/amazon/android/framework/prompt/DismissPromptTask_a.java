package com.amazon.android.framework.prompt;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.framework.prompt:
//			Prompt

final class DismissPromptTask_a implements Task {

    private Prompt a;

    DismissPromptTask_a(Prompt prompt) {
        a = prompt;
    }

    public final void execute() {
        a.dismiss();
    }

    public final String toString() {
        return (new StringBuilder()).append("DismissPromptTask: ")
                .append(a.toString()).toString();
    }
}
