// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.n;

import java.util.Date;

public abstract class b extends com.amazon.android.i.ResultManager_b
{

	final Object a;
	private final Date b;

	public b(Object obj, Date date)
	{
		a = obj;
		b = date;
	}

	public final Date getExpiration()
	{
		return b;
	}
}
