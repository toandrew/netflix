package com.amazon.android.framework.task.command;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.venezia.command.q;
import java.util.concurrent.BlockingQueue;

// Referenced classes of package com.amazon.android.framework.task.command:
//			b

final class CommandServiceClientConnection_i implements ServiceConnection {

    private CommandServiceClient_b a;

    CommandServiceClientConnection_i(CommandServiceClient_b b1) {
        a = b1;
    }

    public final void onServiceConnected(ComponentName componentname,
            IBinder ibinder) {
        CommandServiceClient_b.b().trace("onServiceConnected");
        CommandServiceClient_b.a(a).add(q.a(ibinder));
    }

    public final void onServiceDisconnected(ComponentName componentname) {
        CommandServiceClient_b.b().trace("onServiceDisconnected!!!");
    }
}
