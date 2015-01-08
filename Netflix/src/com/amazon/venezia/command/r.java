// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.IInterface;
import android.os.RemoteException;
import java.util.Map;

// Referenced classes of package com.amazon.venezia.command:
//			s, n

public interface r
	extends IInterface
{

	public abstract String a()
		throws RemoteException;

	public abstract void a(s s)
		throws RemoteException;

	public abstract String b()
		throws RemoteException;

	public abstract String c()
		throws RemoteException;

	public abstract long d()
		throws RemoteException;

	public abstract n e()
		throws RemoteException;

	public abstract n f()
		throws RemoteException;

	public abstract n g()
		throws RemoteException;

	public abstract Map h()
		throws RemoteException;
}
