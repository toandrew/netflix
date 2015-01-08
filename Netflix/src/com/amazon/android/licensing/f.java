// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.licensing;

import com.amazon.android.framework.util.a;
import com.amazon.android.h.SignedTokenParseFailureKiwiException_b;
import com.amazon.android.m.SignedToken_d;
import java.util.Date;

public final class f
{

	final String a;
	final String b;
	final String c;
	final Date d;
	final String e;

	public f(SignedToken_d d1)
		throws SignedTokenParseFailureKiwiException_b
	{
		a = a("checksum", d1);
		b = a("customerId", d1);
		c = a("deviceId", d1);
		e = a("packageName", d1);
		d = b("expiration", d1);
	}

	private static String a(String s, SignedToken_d d1)
		throws SignedTokenParseFailureKiwiException_b
	{
		String s1 = d1.a(s);
		if (com.amazon.android.framework.util.a.a(s1))
			throw new SignedTokenParseFailureKiwiException_b("MISSING_FIELD", s);
		else
			return s1;
	}

	private static Date b(String s, SignedToken_d d1)
		throws SignedTokenParseFailureKiwiException_b
	{
		String s1 = a(s, d1);
		Date date;
		try
		{
			date = new Date(Long.parseLong(s1));
		}
		catch (NumberFormatException numberformatexception)
		{
			throw new SignedTokenParseFailureKiwiException_b("INVALID_FIELD_VALUE", (new StringBuilder()).append(s).append(":").append(s1).toString());
		}
		return date;
	}
}
