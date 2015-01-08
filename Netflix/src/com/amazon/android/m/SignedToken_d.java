// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.m;

import com.amazon.android.d.a;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.h.SignedTokenParseFailureKiwiException_b;
import com.amazon.android.h.SignedTokenVerFailureKiwiException_c;
import com.amazon.mas.kiwi.util.Base64;
import java.io.IOException;
import java.security.PublicKey;
import java.util.*;

// Referenced classes of package com.amazon.android.m:
//			a

public final class SignedToken_d
{

	private static final KiwiLogger a = new KiwiLogger("SignedToken");
	private final Map b = new HashMap();

	public SignedToken_d(String s, PublicKey publickey)
		throws SignedTokenParseFailureKiwiException_b, SignedTokenVerFailureKiwiException_c
	{
		String s1 = b(s);
		int i = s1.lastIndexOf("|");
		if (i == -1)
			throw com.amazon.android.h.SignedTokenParseFailureKiwiException_b.a();
		String s2 = s1.substring(0, i);
		String s3 = s1.substring(i + 1);
		if (KiwiLogger.TRACE_ON)
		{
			a.trace((new StringBuilder()).append("Token data: ").append(s2).toString());
			a.trace((new StringBuilder()).append("Signature: ").append(s3).toString());
		}
		if (!com.amazon.android.m.SignatureVerifier_a.a(s2, s3, publickey))
		{
			throw new SignedTokenVerFailureKiwiException_c();
		} else
		{
			c(s2);
			return;
		}
	}

	private static String b(String s)
		throws SignedTokenParseFailureKiwiException_b
	{
		String s1;
		try
		{
			s1 = new String(Base64.decode(s.getBytes()));
		}
		catch (IOException ioexception)
		{
			throw new SignedTokenParseFailureKiwiException_b("DECODE", ioexception.getMessage());
		}
		return s1;
	}

	private void c(String s)
		throws SignedTokenParseFailureKiwiException_b
	{
		String s2;
		String s3;
		for (StringTokenizer stringtokenizer = new StringTokenizer(s, ","); stringtokenizer.hasMoreElements(); b.put(s2, s3))
		{
			String s1 = stringtokenizer.nextToken();
			a.trace((new StringBuilder()).append("Field: ").append(s1).toString());
			int i = s1.indexOf("=");
			if (i == -1)
				throw com.amazon.android.h.SignedTokenParseFailureKiwiException_b.a();
			s2 = s1.substring(0, i);
			s3 = s1.substring(i + 1);
			a.trace((new StringBuilder()).append("FieldName: ").append(s2).toString());
			a.trace((new StringBuilder()).append("FieldValue: ").append(s3).toString());
		}

	}

	public final String a(String s)
	{
		com.amazon.android.d.a.a(s, "key");
		return (String)b.get(s);
	}

	public final String toString()
	{
		return (new StringBuilder()).append("Signed Token: ").append(b).toString();
	}

}
