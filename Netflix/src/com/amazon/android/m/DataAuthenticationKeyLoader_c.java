// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.m;

import android.app.Application;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.h.CertFailedToLoadKiwiException_a;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.security.auth.x500.X500Principal;

// Referenced classes of package com.amazon.android.m:
//			b

public final class DataAuthenticationKeyLoader_c
{

	private static final KiwiLogger a = new KiwiLogger("DataAuthenticationKeyLoader");
	private Application b;
	private com.amazon.android.n.DataStore_a c;

	public DataAuthenticationKeyLoader_c()
	{
	}

	private static CertPath a(JarFile jarfile, JarEntry jarentry)
		throws CertFailedToLoadKiwiException_a
	{
		CertPath certpath;
		try
		{
			if (KiwiLogger.TRACE_ON)
				a.trace((new StringBuilder()).append("Extracting cert from entry: ").append(jarentry.getName()).toString());
			CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
			if (KiwiLogger.TRACE_ON)
				a.trace("Generating certificates from entry input stream");
			certpath = certificatefactory.generateCertPath(new ArrayList(certificatefactory.generateCertificates(jarfile.getInputStream(jarentry))));
		}
		catch (Exception exception)
		{
			throw com.amazon.android.h.CertFailedToLoadKiwiException_a.a(exception);
		}
		return certpath;
	}

	private static JarEntry a(JarFile jarfile)
		throws CertFailedToLoadKiwiException_a
	{
		if (KiwiLogger.TRACE_ON)
			a.trace("Searching for cert in apk");
		for (Enumeration enumeration = jarfile.entries(); enumeration.hasMoreElements();)
		{
			JarEntry jarentry = (JarEntry)enumeration.nextElement();
			if (!jarentry.isDirectory() && jarentry.getName().equals("kiwi"))
				return jarentry;
		}

		throw new CertFailedToLoadKiwiException_a("CERT_NOT_FOUND", null);
	}

	private JarFile b()
		throws CertFailedToLoadKiwiException_a
	{
		String s = b.getPackageCodePath();
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Opening apk: ").append(s).toString());
		JarFile jarfile;
		try
		{
			jarfile = new JarFile(s, false);
		}
		catch (IOException ioexception)
		{
			throw com.amazon.android.h.CertFailedToLoadKiwiException_a.a(ioexception);
		}
		return jarfile;
	}

	private static CertVerifier_b c()
		throws CertFailedToLoadKiwiException_a
	{
	    CertVerifier_b b1;
		try
		{
			b1 = new CertVerifier_b();
		}
		catch (GeneralSecurityException generalsecurityexception)
		{
			throw new CertFailedToLoadKiwiException_a("FAILED_TO_ESTABLISH_TRUST", generalsecurityexception);
		}
		return b1;
	}

	public final PublicKey a()
		throws CertFailedToLoadKiwiException_a
	{
		CertPath certpath;
		if (KiwiLogger.TRACE_ON)
			a.trace("Loading data authentication key...");
		if (KiwiLogger.TRACE_ON)
			a.trace("Checking KiwiDataStore for key...");
		PublicKey publickey = (PublicKey)c.a("DATA_AUTHENTICATION_KEY");
		if (KiwiLogger.TRACE_ON)
		{
			KiwiLogger kiwilogger = a;
			StringBuilder stringbuilder = (new StringBuilder()).append("Key was cached: ");
			boolean flag1;
			if (publickey != null)
				flag1 = true;
			else
				flag1 = false;
			kiwilogger.trace(stringbuilder.append(flag1).toString());
		}
		if (publickey != null)
			return publickey;
		if (KiwiLogger.TRACE_ON)
			a.trace("Loading authentication key from apk...");
		JarFile jarfile = b();
		certpath = a(jarfile, a(jarfile));
		if (certpath == null || certpath.getCertificates().size() <= 0) goto _L2; else goto _L1
_L1:
		Certificate certificate = (Certificate)certpath.getCertificates().get(0);
		if (!(certificate instanceof X509Certificate)) goto _L2; else goto _L3
_L3:
		boolean flag;
		String s = ((X509Certificate)certificate).getSubjectX500Principal().getName();
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Kiwi Cert Details: ").append(s).toString());
		if (s.contains("O=Amazon.com\\, Inc.") && s.contains("C=US") && s.contains("ST=Washington") && s.contains("L=Seattle"))
			flag = true;
		else
			flag = false;
_L5:
		if (!flag)
			throw new CertFailedToLoadKiwiException_a("CERT_INVALID", null);
		break; /* Loop/switch isn't completed */
_L2:
		flag = false;
		if (true) goto _L5; else goto _L4
_L4:
		if (!c().a(certpath))
			throw new CertFailedToLoadKiwiException_a("VERIFICATION_FAILED", null);
		PublicKey publickey1 = ((X509Certificate)certpath.getCertificates().get(0)).getPublicKey();
		if (KiwiLogger.TRACE_ON)
			a.trace("Placing auth key into storage");
		c.a("DATA_AUTHENTICATION_KEY", publickey1);
		return publickey1;
	}

}
