package com.amazon.android.framework.task.command;

import android.content.Intent;
import android.os.RemoteException;
import com.amazon.venezia.command.n;

final class l {

    final n a;
    final String b;
    final Intent c;

    public l(n n1) throws RemoteException {
        a = n1;
        b = n1.a();
        c = n1.b();
    }
}
