// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.j;

import com.amazon.android.o.f;

public final class c extends Enum
	implements f
{

	public static final c a;
	public static final c b;
	public static final c c;
	public static final c d;
	public static final c e;
	public static final c f;
	private static final c g[];

	private c(String s, int i)
	{
		super(s, i);
	}

	public static c valueOf(String s)
	{
		return (c)Enum.valueOf(com/amazon/android/j/c, s);
	}

	public static c[] values()
	{
		return (c[])g.clone();
	}

	public final String toString()
	{
		return (new StringBuilder()).append("ACTIVITY_").append(name()).toString();
	}

	static 
	{
		a = new c("CREATE", 0);
		b = new c("DESTROY", 1);
		c = new c("RESUME", 2);
		d = new c("PAUSE", 3);
		e = new c("START", 4);
		f = new c("STOP", 5);
		c ac[] = new c[6];
		ac[0] = a;
		ac[1] = b;
		ac[2] = c;
		ac[3] = d;
		ac[4] = e;
		ac[5] = f;
		g = ac;
	}
}
