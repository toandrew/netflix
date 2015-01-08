// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.mas.kiwi.util;


public class ApkInvalidException extends RuntimeException
{

	ApkInvalidException()
	{
	}

	ApkInvalidException(String s)
	{
		super(s);
	}

	ApkInvalidException(String s, Throwable throwable)
	{
		super(s, throwable);
	}

	ApkInvalidException(Throwable throwable)
	{
		super(throwable);
	}
}
