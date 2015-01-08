// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.net;

import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.*;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class X509Util
{

	private static final String OID_ANY_EKU = "2.5.29.37.0";
	private static final String OID_SERVER_GATED_MICROSOFT = "1.3.6.1.4.1.311.10.3.3";
	private static final String OID_SERVER_GATED_NETSCAPE = "2.16.840.1.113730.4.1";
	private static final String OID_TLS_SERVER_AUTH = "1.3.6.1.5.5.7.3.1";
	private static final String TAG = "X509Util";
	private static CertificateFactory sCertificateFactory;
	private static X509TrustManager sDefaultTrustManager;
	private static final Object sLock = new Object();
	private static KeyStore sTestKeyStore;
	private static X509TrustManager sTestTrustManager;

	public X509Util()
	{
	}

	public static void addTestRootCertificate(byte abyte0[])
		throws CertificateException, KeyStoreException, NoSuchAlgorithmException
	{
		ensureInitialized();
		X509Certificate x509certificate = createCertificateFromBytes(abyte0);
		synchronized (sLock)
		{
			sTestKeyStore.setCertificateEntry((new StringBuilder()).append("root_cert_").append(Integer.toString(sTestKeyStore.size())).toString(), x509certificate);
			reloadTestTrustManager();
		}
		return;
		exception;
		obj;
		JVM INSTR monitorexit ;
		throw exception;
	}

	public static void clearTestRootCertificates()
		throws NoSuchAlgorithmException, CertificateException, KeyStoreException
	{
		ensureInitialized();
		synchronized (sLock)
		{
			try
			{
				sTestKeyStore.load(null);
				reloadTestTrustManager();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ioexception) { }
		}
		return;
		exception;
		obj;
		JVM INSTR monitorexit ;
		IOException ioexception;
		throw exception;
	}

	public static X509Certificate createCertificateFromBytes(byte abyte0[])
		throws CertificateException, KeyStoreException, NoSuchAlgorithmException
	{
		ensureInitialized();
		return (X509Certificate)sCertificateFactory.generateCertificate(new ByteArrayInputStream(abyte0));
	}

	private static X509TrustManager createTrustManager(KeyStore keystore)
		throws KeyStoreException, NoSuchAlgorithmException
	{
		TrustManagerFactory trustmanagerfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustmanagerfactory.init(keystore);
		javax.net.ssl.TrustManager atrustmanager[] = trustmanagerfactory.getTrustManagers();
		int i = atrustmanager.length;
		for (int j = 0; j < i; j++)
		{
			javax.net.ssl.TrustManager trustmanager = atrustmanager[j];
			if (trustmanager instanceof X509TrustManager)
				return (X509TrustManager)trustmanager;
		}

		return null;
	}

	private static void ensureInitialized()
		throws CertificateException, KeyStoreException, NoSuchAlgorithmException
	{
		Object obj = sLock;
		obj;
		JVM INSTR monitorenter ;
		if (sCertificateFactory == null)
			sCertificateFactory = CertificateFactory.getInstance("X.509");
		if (sDefaultTrustManager == null)
			sDefaultTrustManager = createTrustManager(null);
		if (sTestKeyStore != null)
			break MISSING_BLOCK_LABEL_55;
		sTestKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		Exception exception;
		try
		{
			sTestKeyStore.load(null);
		}
		catch (IOException ioexception) { }
		if (sTestTrustManager == null)
			sTestTrustManager = createTrustManager(sTestKeyStore);
		return;
		exception;
		obj;
		JVM INSTR monitorexit ;
		throw exception;
	}

	private static void reloadTestTrustManager()
		throws KeyStoreException, NoSuchAlgorithmException
	{
		sTestTrustManager = createTrustManager(sTestKeyStore);
	}

	static boolean verifyKeyUsage(X509Certificate x509certificate)
		throws CertificateException
	{
		List list;
		try
		{
			list = x509certificate.getExtendedKeyUsage();
		}
		catch (NullPointerException nullpointerexception)
		{
			return false;
		}
		if (list != null) goto _L2; else goto _L1
_L1:
		return true;
_L2:
		Iterator iterator = list.iterator();
label0:
		do
		{
label1:
			{
				if (!iterator.hasNext())
					break label1;
				String s = (String)iterator.next();
				if (s.equals("1.3.6.1.5.5.7.3.1") || s.equals("2.5.29.37.0") || s.equals("2.16.840.1.113730.4.1"))
					continue;
				if (s.equals("1.3.6.1.4.1.311.10.3.3"))
					return true;
			}
		} while (true);
		if (true) goto _L1; else goto _L3
_L3:
		return false;
	}

	public static int verifyServerCertificates(byte abyte0[][], String s)
		throws KeyStoreException, NoSuchAlgorithmException
	{
		X509Certificate ax509certificate[];
		if (abyte0 == null || abyte0.length == 0 || abyte0[0] == null)
			throw new IllegalArgumentException((new StringBuilder()).append("Expected non-null and non-empty certificate chain passed as |certChain|. |certChain|=").append(abyte0).toString());
		int i;
		try
		{
			ensureInitialized();
		}
		catch (CertificateException certificateexception)
		{
			return -1;
		}
		ax509certificate = new X509Certificate[abyte0.length];
		i = 0;
		do
		{
			try
			{
				if (i >= abyte0.length)
					break;
				ax509certificate[i] = createCertificateFromBytes(abyte0[i]);
			}
			catch (CertificateException certificateexception1)
			{
				return -5;
			}
			i++;
		} while (true);
		boolean flag;
		try
		{
			ax509certificate[0].checkValidity();
			flag = verifyKeyUsage(ax509certificate[0]);
		}
		catch (CertificateExpiredException certificateexpiredexception)
		{
			return -3;
		}
		catch (CertificateNotYetValidException certificatenotyetvalidexception)
		{
			return -4;
		}
		catch (CertificateException certificateexception2)
		{
			return -1;
		}
		if (!flag)
			return -6;
		if (true) goto _L2; else goto _L1
_L1:
		obj;
		JVM INSTR monitorenter ;
_L2:
		synchronized (sLock)
		{
			sDefaultTrustManager.checkServerTrusted(ax509certificate, s);
		}
		return 0;
		exception;
		obj;
		JVM INSTR monitorexit ;
		throw exception;
		CertificateException certificateexception3;
		certificateexception3;
		sTestTrustManager.checkServerTrusted(ax509certificate, s);
		obj;
		JVM INSTR monitorexit ;
		return 0;
		CertificateException certificateexception4;
		certificateexception4;
		Log.i("X509Util", (new StringBuilder()).append("Failed to validate the certificate chain, error: ").append(certificateexception3.getMessage()).toString());
		obj;
		JVM INSTR monitorexit ;
		return -2;
	}

}
