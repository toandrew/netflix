// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.b;

import com.amazon.android.framework.exception.KiwiException;

public class MalformedResponseKiwiException_h extends KiwiException
{

	private static final long serialVersionUID = 1L;

	public MalformedResponseKiwiException_h(String s, String s1)
	{
		super("MALFORMED_RESPONSE", s, s1);
	}
}
