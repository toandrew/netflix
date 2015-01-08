// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.net;

import android.util.Log;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.ECParameterSpec;

public class AndroidKeyStore
{

	private static final String TAG = "AndroidKeyStore";

	public AndroidKeyStore()
	{
	}

	public static byte[] getDSAKeyParamQ(PrivateKey privatekey)
	{
		if (privatekey instanceof DSAKey)
		{
			return ((DSAKey)privatekey).getParams().getQ().toByteArray();
		} else
		{
			Log.w("AndroidKeyStore", "Not a DSAKey instance!");
			return null;
		}
	}

	public static byte[] getECKeyOrder(PrivateKey privatekey)
	{
		if (privatekey instanceof ECKey)
		{
			return ((ECKey)privatekey).getParams().getOrder().toByteArray();
		} else
		{
			Log.w("AndroidKeyStore", "Not an ECKey instance!");
			return null;
		}
	}

	public static int getOpenSSLHandleForPrivateKey(PrivateKey privatekey)
	{
		if (privatekey != null) goto _L2; else goto _L1
_L1:
		int j;
		Log.e("AndroidKeyStore", "privateKey == null");
		j = 0;
_L4:
		return j;
_L2:
		Class class1;
		if (!(privatekey instanceof RSAPrivateKey))
		{
			Log.e("AndroidKeyStore", "does not implement RSAPrivateKey");
			return 0;
		}
		try
		{
			class1 = Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLRSAPrivateKey");
		}
		catch (Exception exception)
		{
			Log.e("AndroidKeyStore", (new StringBuilder()).append("Cannot find system OpenSSLRSAPrivateKey class: ").append(exception).toString());
			return 0;
		}
		if (!class1.isInstance(privatekey))
		{
			Log.e("AndroidKeyStore", (new StringBuilder()).append("Private key is not an OpenSSLRSAPrivateKey instance, its class name is:").append(privatekey.getClass().getCanonicalName()).toString());
			return 0;
		}
		Method method;
		method = class1.getDeclaredMethod("getOpenSSLKey", new Class[0]);
		method.setAccessible(true);
		Object obj = method.invoke(privatekey, new Object[0]);
		method.setAccessible(false);
		if (obj != null)
			break MISSING_BLOCK_LABEL_202;
		Log.e("AndroidKeyStore", "getOpenSSLKey() returned null");
		return 0;
		Exception exception2;
		exception2;
		try
		{
			method.setAccessible(false);
			throw exception2;
		}
		catch (Exception exception1)
		{
			Log.e("AndroidKeyStore", (new StringBuilder()).append("Exception while trying to retrieve system EVP_PKEY handle: ").append(exception1).toString());
		}
		return 0;
		Method method1 = obj.getClass().getDeclaredMethod("getPkeyContext", new Class[0]);
		method1.setAccessible(true);
		int i = ((Integer)method1.invoke(obj, new Object[0])).intValue();
		j = i;
		method1.setAccessible(false);
		if (j != 0) goto _L4; else goto _L3
_L3:
		Log.e("AndroidKeyStore", "getPkeyContext() returned null");
		return j;
		Exception exception3;
		exception3;
		Log.e("AndroidKeyStore", (new StringBuilder()).append("No getPkeyContext() method on OpenSSLKey member:").append(exception3).toString());
		return 0;
		Exception exception4;
		exception4;
		method1.setAccessible(false);
		throw exception4;
	}

	public static byte[] getPrivateKeyEncodedBytes(PrivateKey privatekey)
	{
		return privatekey.getEncoded();
	}

	public static int getPrivateKeyType(PrivateKey privatekey)
	{
		if (privatekey instanceof RSAPrivateKey)
			return 0;
		if (privatekey instanceof DSAPrivateKey)
			return 1;
		return !(privatekey instanceof ECPrivateKey) ? 255 : 2;
	}

	public static byte[] getRSAKeyModulus(PrivateKey privatekey)
	{
		if (privatekey instanceof RSAKey)
		{
			return ((RSAKey)privatekey).getModulus().toByteArray();
		} else
		{
			Log.w("AndroidKeyStore", "Not a RSAKey instance!");
			return null;
		}
	}

	public static byte[] rawSignDigestWithPrivateKey(PrivateKey privatekey, byte abyte0[])
	{
		if (!(privatekey instanceof RSAPrivateKey)) goto _L2; else goto _L1
_L1:
		Signature signature2 = Signature.getInstance("NONEwithRSA");
		Signature signature;
		signature = signature2;
		break MISSING_BLOCK_LABEL_17;
_L2:
		if (privatekey instanceof DSAPrivateKey)
		{
			signature = Signature.getInstance("NONEwithDSA");
			continue; /* Loop/switch isn't completed */
		}
		flag = privatekey instanceof ECPrivateKey;
		signature = null;
		if (!flag)
			continue; /* Loop/switch isn't completed */
		signature1 = Signature.getInstance("NONEwithECDSA");
		signature = signature1;
		continue; /* Loop/switch isn't completed */
_L4:
		boolean flag;
		Signature signature1;
		if (signature == null)
		{
			Log.e("AndroidKeyStore", (new StringBuilder()).append("Unsupported private key algorithm: ").append(privatekey.getAlgorithm()).toString());
			return null;
		}
		byte abyte1[];
		try
		{
			signature.initSign(privatekey);
			signature.update(abyte0);
			abyte1 = signature.sign();
		}
		catch (Exception exception)
		{
			Log.e("AndroidKeyStore", (new StringBuilder()).append("Exception while signing message with ").append(privatekey.getAlgorithm()).append(" private key: ").append(exception).toString());
			return null;
		}
		return abyte1;
		NoSuchAlgorithmException nosuchalgorithmexception;
		nosuchalgorithmexception;
		signature = null;
		if (true) goto _L4; else goto _L3
_L3:
	}
}
