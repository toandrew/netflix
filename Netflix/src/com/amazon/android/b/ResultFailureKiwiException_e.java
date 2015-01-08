// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.b;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.venezia.command.j;

public class ResultFailureKiwiException_e extends KiwiException
{

	private static final long serialVersionUID = 1L;

	public ResultFailureKiwiException_e(j j1)
		throws RemoteException
	{
		super("EXCEPTION_RESULT_FAILURE", j1.a());
	}
}
