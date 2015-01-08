// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.h;

import com.amazon.android.framework.exception.KiwiException;

public class CertFailedToLoadKiwiException_a extends KiwiException
{

	private static final long serialVersionUID = 1L;

	public CertFailedToLoadKiwiException_a(String s, Throwable throwable)
	{
		super("DATA_AUTH_KEY_LOAD_FAILURE", s, throwable);
	}

	public static a a(Throwable throwable)
	{
		return new a("CERT_FAILED_TO_LOAD", throwable);
	}
}
