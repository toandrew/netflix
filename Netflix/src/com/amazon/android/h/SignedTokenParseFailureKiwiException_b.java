// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.h;

import com.amazon.android.framework.exception.KiwiException;

public class SignedTokenParseFailureKiwiException_b extends KiwiException
{

	private static final long serialVersionUID = 1L;

	public SignedTokenParseFailureKiwiException_b(String s, String s1)
	{
		super("SIGNED_TOKEN_PARSE_FAILURE", s, s1);
	}

	public static b a()
	{
		return new b("INVALID_FORMAT", null);
	}
}
