// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.common;


public class ProcessInitException extends Exception
{

	private int mErrorCode;

	public ProcessInitException(int i)
	{
		mErrorCode = 0;
		mErrorCode = i;
	}

	public ProcessInitException(int i, Throwable throwable)
	{
		super(null, throwable);
		mErrorCode = 0;
		mErrorCode = i;
	}

	public int getErrorCode()
	{
		return mErrorCode;
	}
}
