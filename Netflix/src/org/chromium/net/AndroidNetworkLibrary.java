// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.net;

import android.content.*;
import android.security.KeyChain;
import android.util.Log;
import java.net.*;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

// Referenced classes of package org.chromium.net:
//			X509Util

class AndroidNetworkLibrary
{

	private static final String TAG = "AndroidNetworkLibrary";

	AndroidNetworkLibrary()
	{
	}

	public static void addTestRootCertificate(byte abyte0[])
		throws CertificateException, KeyStoreException, NoSuchAlgorithmException
	{
		X509Util.addTestRootCertificate(abyte0);
	}

	public static void clearTestRootCertificates()
		throws NoSuchAlgorithmException, CertificateException, KeyStoreException
	{
		X509Util.clearTestRootCertificates();
	}

	public static String getMimeTypeFromExtension(String s)
	{
		return URLConnection.guessContentTypeFromName((new StringBuilder()).append("foo.").append(s).toString());
	}

	public static String getNetworkList()
	{
		Enumeration enumeration;
		try
		{
			enumeration = NetworkInterface.getNetworkInterfaces();
		}
		catch (SocketException socketexception)
		{
			Log.w("AndroidNetworkLibrary", (new StringBuilder()).append("Unable to get network interfaces: ").append(socketexception).toString());
			return "";
		}
		if (enumeration == null)
			return "";
		StringBuilder stringbuilder = new StringBuilder();
label0:
		do
		{
			if (!enumeration.hasMoreElements())
				break;
			NetworkInterface networkinterface = (NetworkInterface)enumeration.nextElement();
			try
			{
				if (!networkinterface.isUp() || networkinterface.isLoopback())
					continue;
				Enumeration enumeration1 = networkinterface.getInetAddresses();
				do
				{
					InetAddress inetaddress;
					do
					{
						if (!enumeration1.hasMoreElements())
							continue label0;
						inetaddress = (InetAddress)enumeration1.nextElement();
					} while (inetaddress.isLoopbackAddress());
					StringBuilder stringbuilder1 = new StringBuilder();
					stringbuilder1.append(networkinterface.getName());
					stringbuilder1.append(",");
					String s = inetaddress.getHostAddress();
					if ((inetaddress instanceof Inet6Address) && s.contains("%"))
						s = s.substring(0, s.lastIndexOf("%"));
					stringbuilder1.append(s);
					if (stringbuilder.length() != 0)
						stringbuilder.append(";");
					stringbuilder.append(stringbuilder1.toString());
				} while (true);
			}
			catch (SocketException socketexception1) { }
		} while (true);
		return stringbuilder.toString();
	}

	public static boolean haveOnlyLoopbackAddresses()
	{
//		Enumeration enumeration;
//		try
//		{
//			enumeration = NetworkInterface.getNetworkInterfaces();
//		}
//		catch (Exception exception)
//		{
//			Log.w("AndroidNetworkLibrary", (new StringBuilder()).append("could not get network interfaces: ").append(exception).toString());
//			return false;
//		}
//		if (enumeration == null)
//			return false;
//		break MISSING_BLOCK_LABEL_40;
//		SocketException socketexception;
//		socketexception;
//_L2:
//		NetworkInterface networkinterface;
//		if (!enumeration.hasMoreElements())
//			break; /* Loop/switch isn't completed */
//		networkinterface = (NetworkInterface)enumeration.nextElement();
//		boolean flag;
//		if (!networkinterface.isUp())
//			continue; /* Loop/switch isn't completed */
//		flag = networkinterface.isLoopback();
//		if (!flag)
//			return false;
//		if (true) goto _L2; else goto _L1
//_L1:
		return true;
	}

	public static boolean storeCertificate(Context context, int i, byte abyte0[])
	{
	    return true;
//		Intent intent;
//		try
//		{
//			intent = KeyChain.createInstallIntent();
//			intent.addFlags(0x10000000);
//		}
//		catch (ActivityNotFoundException activitynotfoundexception)
//		{
//			Log.w("AndroidNetworkLibrary", (new StringBuilder()).append("could not store crypto file: ").append(activitynotfoundexception).toString());
//			return false;
//		}
//		i;
//		JVM INSTR tableswitch 1 3: default 40
//	//	               1 67
//	//	               2 67
//	//	               3 84;
//		   goto _L1 _L2 _L2 _L3
//_L3:
//		break MISSING_BLOCK_LABEL_84;
//_L1:
//		Log.w("AndroidNetworkLibrary", (new StringBuilder()).append("invalid certificate type: ").append(i).toString());
//		return false;
//_L2:
//		intent.putExtra("CERT", abyte0);
//_L4:
//		context.startActivity(intent);
//		return true;
//		intent.putExtra("PKCS12", abyte0);
//		  goto _L4
	}

	public static boolean storeKeyPair(Context context, byte abyte0[], byte abyte1[])
	{
		try
		{
			Intent intent = KeyChain.createInstallIntent();
			intent.putExtra("PKEY", abyte1);
			intent.putExtra("KEY", abyte0);
			intent.addFlags(0x10000000);
			context.startActivity(intent);
		}
		catch (ActivityNotFoundException activitynotfoundexception)
		{
			Log.w("AndroidNetworkLibrary", (new StringBuilder()).append("could not store key pair: ").append(activitynotfoundexception).toString());
			return false;
		}
		return true;
	}

	public static int verifyServerCertificates(byte abyte0[][], String s)
	{
		int i;
		try
		{
			i = X509Util.verifyServerCertificates(abyte0, s);
		}
		catch (KeyStoreException keystoreexception)
		{
			return -1;
		}
		catch (NoSuchAlgorithmException nosuchalgorithmexception)
		{
			return -1;
		}
		return i;
	}
}
