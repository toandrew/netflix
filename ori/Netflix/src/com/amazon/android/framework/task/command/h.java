package com.amazon.android.framework.task.command;

import android.os.RemoteException;
import com.amazon.venezia.command.p;
import java.util.Map;

// Referenced classes of package com.amazon.android.framework.task.command:
//			a, b

final class h extends p {

    private FailedReason_a a;
    private CommandServiceClient_b b;

    h(CommandServiceClient_b b1, FailedReason_a a1) {
        b = b1;
        a = a1;
    }

    public final String a() throws RemoteException {
        return a.name();
    }

    public final Map b() throws RemoteException {
        return null;
    }
}
