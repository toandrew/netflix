// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.j;

import com.amazon.android.o.f;

public final class d extends Enum
	implements f
{

	public static final d a;
	public static final d b;
	public static final d c;
	public static final d d;
	private static final d e[];

	private d(String s, int i)
	{
		super(s, i);
	}

	public static d valueOf(String s)
	{
		return (d)Enum.valueOf(com/amazon/android/j/d, s);
	}

	public static d[] values()
	{
		return (d[])e.clone();
	}

	public final String toString()
	{
		return (new StringBuilder()).append("APPLICATION_").append(name()).toString();
	}

	static 
	{
		a = new d("CREATE", 0);
		b = new d("DESTROY", 1);
		c = new d("START", 2);
		d = new d("STOP", 3);
		d ad[] = new d[4];
		ad[0] = a;
		ad[1] = b;
		ad[2] = c;
		ad[3] = d;
		e = ad;
	}
}
