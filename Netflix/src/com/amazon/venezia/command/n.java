// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.content.Intent;
import android.os.IInterface;
import android.os.RemoteException;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			y

public interface n extends IInterface {

    public abstract String a() throws RemoteException;

    public abstract void a(y y) throws RemoteException;

    public abstract Intent b() throws RemoteException;

    public abstract Map c() throws RemoteException;
}
