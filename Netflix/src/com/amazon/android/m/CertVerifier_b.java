// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.m;

import com.amazon.android.framework.util.KiwiLogger;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.cert.*;
import java.util.*;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

public final class CertVerifier_b
{

	private static final KiwiLogger a = new KiwiLogger("CertVerifier");
	private static final Set e;
	private final PKIXParameters b;
	private final CertPathValidator c = CertPathValidator.getInstance("PKIX");
	private final Set d = new HashSet();

	public CertVerifier_b()
		throws GeneralSecurityException
	{
		TrustManagerFactory trustmanagerfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustmanagerfactory.init(null);
		javax.net.ssl.TrustManager atrustmanager[] = trustmanagerfactory.getTrustManagers();
		int i = atrustmanager.length;
		for (int j = 0; j < i; j++)
		{
			javax.net.ssl.TrustManager trustmanager = atrustmanager[j];
			if (!(trustmanager instanceof X509TrustManager))
				continue;
			X509Certificate ax509certificate[] = ((X509TrustManager)trustmanager).getAcceptedIssuers();
			int k = ax509certificate.length;
			int l = 0;
			int i1 = 0;
			for (; l < k; l++)
			{
				X509Certificate x509certificate = ax509certificate[l];
				if (!a(x509certificate))
					continue;
				if (KiwiLogger.TRACE_ON)
					a.trace((new StringBuilder()).append("Trusted Cert: ").append(x509certificate.getSubjectX500Principal().getName()).toString());
				TrustAnchor trustanchor = new TrustAnchor(x509certificate, null);
				d.add(trustanchor);
				i1++;
			}

			if (KiwiLogger.TRACE_ON)
			{
				KiwiLogger kiwilogger = a;
				Object aobj[] = new Object[1];
				aobj[0] = Integer.valueOf(i1);
				kiwilogger.trace(String.format("loaded %d certs\n", aobj));
			}
		}

		b = new PKIXParameters(d);
		b.setRevocationEnabled(false);
	}

	private static boolean a(X509Certificate x509certificate)
	{
		String s = x509certificate.getSubjectDN().getName().toLowerCase();
		for (Iterator iterator = e.iterator(); iterator.hasNext();)
			if (s.contains((String)iterator.next()))
				return true;

		return false;
	}

	public final boolean a(CertPath certpath)
	{
		c.validate(certpath, b);
		boolean flag1 = true;
_L2:
		return flag1;
		CertPathValidatorException certpathvalidatorexception;
		certpathvalidatorexception;
		boolean flag2 = certpathvalidatorexception.getCause() instanceof CertificateExpiredException;
		flag1 = false;
		if (flag2)
		{
			if (KiwiLogger.TRACE_ON)
				a.trace("CertVerifier doesn't care about an out of date certificate.");
			return true;
		}
		continue; /* Loop/switch isn't completed */
		Exception exception;
		exception;
		boolean flag = KiwiLogger.TRACE_ON;
		flag1 = false;
		if (flag)
		{
			a.error((new StringBuilder()).append("Failed to verify cert path: ").append(exception).toString(), exception);
			return false;
		}
		if (true) goto _L2; else goto _L1
_L1:
	}

	static 
	{
		HashSet hashset = new HashSet();
		e = hashset;
		hashset.add("verisign");
		e.add("thawte");
	}
}
