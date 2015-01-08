// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.exception;


public abstract class KiwiException extends Exception
{

	private static final long serialVersionUID = 1L;
	private final String context;
	private final String reason;
	private final String type;

	public KiwiException(String s)
	{
		this(s, ((String) (null)));
	}

	public KiwiException(String s, String s1)
	{
		this(s, s1, ((String) (null)));
	}

	public KiwiException(String s, String s1, String s2)
	{
		super((new StringBuilder()).append(s).append(": ").append(s1).append(": ").append(s2).toString());
		type = s;
		reason = s1;
		context = s2;
	}

	public KiwiException(String s, String s1, Throwable throwable)
	{
		this(s, s1, getContext(throwable));
	}

	public KiwiException(String s, Throwable throwable)
	{
		this(s, getName(throwable), throwable);
	}

	private static String getContext(Throwable throwable)
	{
		if (throwable == null)
			return null;
		Throwable throwable1 = getRootCause(throwable);
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append(getName(throwable)).append(":").append(throwable.getMessage());
		if (throwable != throwable1)
			stringbuilder.append("/").append(getName(throwable1)).append(":").append(throwable1.getMessage());
		return stringbuilder.toString();
	}

	private static String getName(Throwable throwable)
	{
		return throwable.getClass().getName();
	}

	private static Throwable getRootCause(Throwable throwable)
	{
		Throwable throwable1;
		for (throwable1 = throwable; throwable1.getCause() != null; throwable1 = throwable1.getCause());
		return throwable1;
	}

	public final String getContext()
	{
		return context;
	}

	public final String getReason()
	{
		return reason;
	}

	public final String getType()
	{
		return type;
	}
}
