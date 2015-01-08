// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.b;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;

public class CommandServiceKiwiException_a extends KiwiException
{

	private static final long serialVersionUID = 1L;

	public CommandServiceKiwiException_a(RemoteException remoteexception)
	{
		String s;
		if (remoteexception instanceof DeadObjectException)
			s = "COMMAND_SERVICE_DEAD_OBJECT_EXCEPTION";
		else
			s = "COMMAND_SERVICE_REMOTE_EXCEPTION";
		super(s);
	}
}
