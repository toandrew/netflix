package com.amazon.android.framework.task.command;

import android.content.DialogInterface;
import com.amazon.android.framework.util.KiwiLogger;
import java.util.concurrent.BlockingQueue;

// Referenced classes of package com.amazon.android.framework.task.command:
//			c, l

final class OnChoiceClickListener_d implements
        android.content.DialogInterface.OnClickListener {

    private l a;
    private DecisionDialog_c b;

    OnChoiceClickListener_d(DecisionDialog_c c1, l l) {
        b = c1;
        a = l;
    }

    public final void onClick(DialogInterface dialoginterface, int i) {
        if (KiwiLogger.TRACE_ON)
            DecisionDialog_c.b().trace("Choice selected!");
        if (DecisionDialog_c.a(b))
            DecisionDialog_c.b(b).add(a);
    }
}
