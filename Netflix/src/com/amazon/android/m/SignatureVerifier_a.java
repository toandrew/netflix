// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.m;

import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.mas.kiwi.util.Base64;
import java.io.IOException;
import java.security.*;

public final class SignatureVerifier_a
{

	private static final KiwiLogger a = new KiwiLogger("SignatureVerifier");

	public SignatureVerifier_a()
	{
	}

	public static boolean a(String s, String s1, PublicKey publickey)
	{
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Verifying signature of data: '").append(s).append("', signature: '").append(s1).append("', with key: '").append(publickey.toString()).toString());
		boolean flag;
		try
		{
			byte abyte0[] = Base64.decode(s1.getBytes());
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(publickey);
			signature.update(s.getBytes());
			flag = signature.verify(abyte0);
		}
		catch (GeneralSecurityException generalsecurityexception)
		{
			return false;
		}
		catch (IOException ioexception)
		{
			return false;
		}
		return flag;
	}

}
