// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.venezia.command;

import android.os.IInterface;
import android.os.RemoteException;
import java.util.Map;

public interface s extends IInterface {

    public abstract String a() throws RemoteException;

    public abstract Map b() throws RemoteException;
}
