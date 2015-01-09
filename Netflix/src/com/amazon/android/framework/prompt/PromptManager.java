package com.amazon.android.framework.prompt;

import android.app.Activity;
import android.app.Dialog;
import com.amazon.android.i.d;

// Referenced classes of package com.amazon.android.framework.prompt:
//			Prompt

public interface PromptManager extends d {

    public abstract Dialog onCreateDialog(Activity activity, int i);

    public abstract void onWindowFocusChanged(Activity activity, boolean flag);

    public abstract void present(Prompt prompt);
}
