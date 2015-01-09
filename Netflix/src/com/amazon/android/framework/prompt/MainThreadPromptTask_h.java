package com.amazon.android.framework.prompt;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.framework.prompt:
//			PromptManagerImpl, Prompt

final class MainThreadPromptTask_h implements Task {

    private Prompt a;
    private PromptManagerImpl b;

    MainThreadPromptTask_h(PromptManagerImpl promptmanagerimpl, Prompt prompt) {
        b = promptmanagerimpl;
        a = prompt;
    }

    public final void execute() {
        PromptManagerImpl.a(b, a);
    }

    public final String toString() {
        return (new StringBuilder())
                .append("Prompt Presentation on Main Thread: ").append(a)
                .append(", ").append(a.getExpiration()).toString();
    }
}
