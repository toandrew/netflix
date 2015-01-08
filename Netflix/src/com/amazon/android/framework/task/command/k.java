// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;

import android.os.Binder;
import android.os.RemoteException;
import com.amazon.venezia.command.*;

public final class k
{

	String a;
	RemoteException b;
	SuccessResult c;
	FailureResult d;
	r e;
	j f;
	private int g;

	public k(FailureResult failureresult)
	{
		d = failureresult;
		g = Binder.getCallingUid();
		try
		{
			a = failureresult.getAuthToken();
			return;
		}
		catch (RemoteException remoteexception)
		{
			b = remoteexception;
		}
	}

	public k(SuccessResult successresult)
	{
		c = successresult;
		g = Binder.getCallingUid();
		try
		{
			a = successresult.getAuthToken();
			return;
		}
		catch (RemoteException remoteexception)
		{
			b = remoteexception;
		}
	}

	public k(j j)
	{
		f = j;
		g = Binder.getCallingUid();
	}

	public k(r r1)
	{
		e = r1;
		g = Binder.getCallingUid();
		try
		{
			a = r1.a();
			return;
		}
		catch (RemoteException remoteexception)
		{
			b = remoteexception;
		}
	}

	public final String toString()
	{
		StringBuilder stringbuilder = new StringBuilder("CommandResult: [");
		stringbuilder.append("CallingUid: ").append(g).append(", SuccessResult: ").append(c).append(", FailureResult: ").append(d).append(", DecisionResult: ").append(e).append(", ExceptionResult: ").append(f).append("]");
		return stringbuilder.toString();
	}
}
