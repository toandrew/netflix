// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.task.command;


public final class a extends Enum
{

	public static final a a;
	public static final a b;
	public static final a c;
	private static final a d[];

	private a(String s, int i)
	{
		super(s, i);
	}

	public static a valueOf(String s)
	{
		return (a)Enum.valueOf(com/amazon/android/framework/task/command/a, s);
	}

	public static a[] values()
	{
		return (a[])d.clone();
	}

	static 
	{
		a = new a("EXPIRATION_DURATION_ELAPSED", 0);
		b = new a("APP_NOT_COMPATIBLE", 1);
		c = new a("ACTION_CANCELED", 2);
		a aa[] = new a[3];
		aa[0] = a;
		aa[1] = b;
		aa[2] = c;
		d = aa;
	}
}
