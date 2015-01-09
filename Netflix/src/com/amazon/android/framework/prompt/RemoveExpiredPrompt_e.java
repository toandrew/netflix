package com.amazon.android.framework.prompt;

import com.amazon.android.framework.task.Task;

// Referenced classes of package com.amazon.android.framework.prompt:
//			PromptManagerImpl, Prompt

final class RemoveExpiredPrompt_e implements Task {

    private Prompt a;
    private PromptManagerImpl b;

    RemoveExpiredPrompt_e(PromptManagerImpl promptmanagerimpl, Prompt prompt) {
        b = promptmanagerimpl;
        a = prompt;
    }

    public final void execute() {
        PromptManagerImpl.b(b, a);
    }

    public final String toString() {
        return (new StringBuilder())
                .append("PromptManager:removeExpiredPrompt: ").append(a)
                .toString();
    }
}
