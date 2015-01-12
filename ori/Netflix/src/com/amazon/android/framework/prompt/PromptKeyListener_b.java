package com.amazon.android.framework.prompt;

import android.content.DialogInterface;
import android.view.KeyEvent;

// Referenced classes of package com.amazon.android.framework.prompt:
//			Prompt

final class PromptKeyListener_b implements
        android.content.DialogInterface.OnKeyListener {

    private Prompt a;

    PromptKeyListener_b(Prompt prompt) {
        a = prompt;
    }

    public final boolean onKey(DialogInterface dialoginterface, int i,
            KeyEvent keyevent) {
        return i == 84;
    }
}
