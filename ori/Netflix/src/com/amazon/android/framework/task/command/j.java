package com.amazon.android.framework.task.command;

import android.os.RemoteException;
import com.amazon.venezia.command.FailureResult;
import com.amazon.venezia.command.SuccessResult;
import com.amazon.venezia.command.o;
import com.amazon.venezia.command.r;
import java.util.concurrent.BlockingQueue;

// Referenced classes of package com.amazon.android.framework.task.command:
//			b, k

final class j extends o {

    private CommandServiceClient_b a;

    j(CommandServiceClient_b b1) {
        a = b1;
    }

    public final void a(FailureResult failureresult) throws RemoteException {
        CommandServiceClient_b.b(a).add(new CommandResult_k(failureresult));
    }

    public final void a(SuccessResult successresult) throws RemoteException {
        CommandServiceClient_b.b(a).add(new CommandResult_k(successresult));
    }

    public final void a(com.amazon.venezia.command.j j1) throws RemoteException {
        CommandServiceClient_b.b(a).add(new CommandResult_k(j1));
    }

    public final void a(r r) throws RemoteException {
        CommandServiceClient_b.b(a).add(new CommandResult_k(r));
    }
}
