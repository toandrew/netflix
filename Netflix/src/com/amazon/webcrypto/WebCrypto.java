// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.webcrypto;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.crypto.Cipher;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.amazon.webcrypto:
//			TZInterface, WebCryptoKey, RsaSpki, JsonHelper

public class WebCrypto
{
	static final class Algorithm extends Enum
	{

		private static final Algorithm $VALUES[];
		public static final Algorithm AES_CBC;
		public static final Algorithm AES_CTR;
		public static final Algorithm AES_GCM;
		public static final Algorithm AES_KW;
		public static final Algorithm DH;
		public static final Algorithm HMAC;
		public static final Algorithm INVALID_ALGORITHM;
		public static final Algorithm RSAES_PKCS1_V1_5;
		public static final Algorithm RSASSA_PKCS1_V1_5;
		public static final Algorithm RSA_OAEP;
		public static final Algorithm SHA1;
		public static final Algorithm SHA224;
		public static final Algorithm SHA256;
		public static final Algorithm SHA384;
		public static final Algorithm SHA512;
		public static final Algorithm SYSTEM;

		public static Algorithm valueOf(String s)
		{
			return (Algorithm)Enum.valueOf(com/amazon/webcrypto/WebCrypto$Algorithm, s);
		}

		public static Algorithm[] values()
		{
			return (Algorithm[])$VALUES.clone();
		}

		static 
		{
			HMAC = new Algorithm("HMAC", 0);
			AES_CBC = new Algorithm("AES_CBC", 1);
			AES_GCM = new Algorithm("AES_GCM", 2);
			AES_CTR = new Algorithm("AES_CTR", 3);
			RSAES_PKCS1_V1_5 = new Algorithm("RSAES_PKCS1_V1_5", 4);
			RSASSA_PKCS1_V1_5 = new Algorithm("RSASSA_PKCS1_V1_5", 5);
			RSA_OAEP = new Algorithm("RSA_OAEP", 6);
			SHA1 = new Algorithm("SHA1", 7);
			SHA224 = new Algorithm("SHA224", 8);
			SHA256 = new Algorithm("SHA256", 9);
			SHA384 = new Algorithm("SHA384", 10);
			SHA512 = new Algorithm("SHA512", 11);
			AES_KW = new Algorithm("AES_KW", 12);
			DH = new Algorithm("DH", 13);
			SYSTEM = new Algorithm("SYSTEM", 14);
			INVALID_ALGORITHM = new Algorithm("INVALID_ALGORITHM", 15);
			Algorithm aalgorithm[] = new Algorithm[16];
			aalgorithm[0] = HMAC;
			aalgorithm[1] = AES_CBC;
			aalgorithm[2] = AES_GCM;
			aalgorithm[3] = AES_CTR;
			aalgorithm[4] = RSAES_PKCS1_V1_5;
			aalgorithm[5] = RSASSA_PKCS1_V1_5;
			aalgorithm[6] = RSA_OAEP;
			aalgorithm[7] = SHA1;
			aalgorithm[8] = SHA224;
			aalgorithm[9] = SHA256;
			aalgorithm[10] = SHA384;
			aalgorithm[11] = SHA512;
			aalgorithm[12] = AES_KW;
			aalgorithm[13] = DH;
			aalgorithm[14] = SYSTEM;
			aalgorithm[15] = INVALID_ALGORITHM;
			$VALUES = aalgorithm;
		}

		private Algorithm(String s, int i)
		{
			super(s, i);
		}
	}

	public static interface HandleWebcryptoResponseInterface
	{

		public abstract void onHandleResponse(String s);
	}

	static final class WebCryptoErr extends Enum
	{

		private static final WebCryptoErr $VALUES[];
		public static final WebCryptoErr WC_ERR_ALREADY_INITIALIZED;
		public static final WebCryptoErr WC_ERR_BADARG;
		public static final WebCryptoErr WC_ERR_BADCONTEXTNAME;
		public static final WebCryptoErr WC_ERR_BADENCODING;
		public static final WebCryptoErr WC_ERR_BADIV;
		public static final WebCryptoErr WC_ERR_BADKEYINDEX;
		public static final WebCryptoErr WC_ERR_BADKEYNAME;
		public static final WebCryptoErr WC_ERR_CIPHERERROR;
		public static final WebCryptoErr WC_ERR_DHERROR;
		public static final WebCryptoErr WC_ERR_END;
		public static final WebCryptoErr WC_ERR_HMACERROR;
		public static final WebCryptoErr WC_ERR_INTERNAL;
		public static final WebCryptoErr WC_ERR_KEYDERIVE;
		public static final WebCryptoErr WC_ERR_KEYGEN;
		public static final WebCryptoErr WC_ERR_KEY_USAGE;
		public static final WebCryptoErr WC_ERR_NOMETHOD;
		public static final WebCryptoErr WC_ERR_NOT_IMPLEMENTED;
		public static final WebCryptoErr WC_ERR_NOT_INITIALIZED;
		public static final WebCryptoErr WC_ERR_OK;
		public static final WebCryptoErr WC_ERR_REGERROR;
		public static final WebCryptoErr WC_ERR_REGISTERED;
		public static final WebCryptoErr WC_ERR_STORE;
		public static final WebCryptoErr WC_ERR_UNKNOWN;
		public static final WebCryptoErr WC_ERR_UNKNOWN_ALGO;
		public static final WebCryptoErr WC_ERR_UNSUPPORTED_KEY_ENCODING;

		public static WebCryptoErr valueOf(String s)
		{
			return (WebCryptoErr)Enum.valueOf(com/amazon/webcrypto/WebCrypto$WebCryptoErr, s);
		}

		public static WebCryptoErr[] values()
		{
			return (WebCryptoErr[])$VALUES.clone();
		}

		static 
		{
			WC_ERR_OK = new WebCryptoErr("WC_ERR_OK", 0);
			WC_ERR_BADARG = new WebCryptoErr("WC_ERR_BADARG", 1);
			WC_ERR_KEYDERIVE = new WebCryptoErr("WC_ERR_KEYDERIVE", 2);
			WC_ERR_NOMETHOD = new WebCryptoErr("WC_ERR_NOMETHOD", 3);
			WC_ERR_BADKEYINDEX = new WebCryptoErr("WC_ERR_BADKEYINDEX", 4);
			WC_ERR_BADENCODING = new WebCryptoErr("WC_ERR_BADENCODING", 5);
			WC_ERR_BADKEYNAME = new WebCryptoErr("WC_ERR_BADKEYNAME", 6);
			WC_ERR_STORE = new WebCryptoErr("WC_ERR_STORE", 7);
			WC_ERR_CIPHERERROR = new WebCryptoErr("WC_ERR_CIPHERERROR", 8);
			WC_ERR_BADIV = new WebCryptoErr("WC_ERR_BADIV", 9);
			WC_ERR_DHERROR = new WebCryptoErr("WC_ERR_DHERROR", 10);
			WC_ERR_UNKNOWN = new WebCryptoErr("WC_ERR_UNKNOWN", 11);
			WC_ERR_NOT_INITIALIZED = new WebCryptoErr("WC_ERR_NOT_INITIALIZED", 12);
			WC_ERR_NOT_IMPLEMENTED = new WebCryptoErr("WC_ERR_NOT_IMPLEMENTED", 13);
			WC_ERR_BADCONTEXTNAME = new WebCryptoErr("WC_ERR_BADCONTEXTNAME", 14);
			WC_ERR_HMACERROR = new WebCryptoErr("WC_ERR_HMACERROR", 15);
			WC_ERR_REGISTERED = new WebCryptoErr("WC_ERR_REGISTERED", 16);
			WC_ERR_REGERROR = new WebCryptoErr("WC_ERR_REGERROR", 17);
			WC_ERR_UNKNOWN_ALGO = new WebCryptoErr("WC_ERR_UNKNOWN_ALGO", 18);
			WC_ERR_UNSUPPORTED_KEY_ENCODING = new WebCryptoErr("WC_ERR_UNSUPPORTED_KEY_ENCODING", 19);
			WC_ERR_KEYGEN = new WebCryptoErr("WC_ERR_KEYGEN", 20);
			WC_ERR_ALREADY_INITIALIZED = new WebCryptoErr("WC_ERR_ALREADY_INITIALIZED", 21);
			WC_ERR_INTERNAL = new WebCryptoErr("WC_ERR_INTERNAL", 22);
			WC_ERR_KEY_USAGE = new WebCryptoErr("WC_ERR_KEY_USAGE", 23);
			WC_ERR_END = new WebCryptoErr("WC_ERR_END", 24);
			WebCryptoErr awebcryptoerr[] = new WebCryptoErr[25];
			awebcryptoerr[0] = WC_ERR_OK;
			awebcryptoerr[1] = WC_ERR_BADARG;
			awebcryptoerr[2] = WC_ERR_KEYDERIVE;
			awebcryptoerr[3] = WC_ERR_NOMETHOD;
			awebcryptoerr[4] = WC_ERR_BADKEYINDEX;
			awebcryptoerr[5] = WC_ERR_BADENCODING;
			awebcryptoerr[6] = WC_ERR_BADKEYNAME;
			awebcryptoerr[7] = WC_ERR_STORE;
			awebcryptoerr[8] = WC_ERR_CIPHERERROR;
			awebcryptoerr[9] = WC_ERR_BADIV;
			awebcryptoerr[10] = WC_ERR_DHERROR;
			awebcryptoerr[11] = WC_ERR_UNKNOWN;
			awebcryptoerr[12] = WC_ERR_NOT_INITIALIZED;
			awebcryptoerr[13] = WC_ERR_NOT_IMPLEMENTED;
			awebcryptoerr[14] = WC_ERR_BADCONTEXTNAME;
			awebcryptoerr[15] = WC_ERR_HMACERROR;
			awebcryptoerr[16] = WC_ERR_REGISTERED;
			awebcryptoerr[17] = WC_ERR_REGERROR;
			awebcryptoerr[18] = WC_ERR_UNKNOWN_ALGO;
			awebcryptoerr[19] = WC_ERR_UNSUPPORTED_KEY_ENCODING;
			awebcryptoerr[20] = WC_ERR_KEYGEN;
			awebcryptoerr[21] = WC_ERR_ALREADY_INITIALIZED;
			awebcryptoerr[22] = WC_ERR_INTERNAL;
			awebcryptoerr[23] = WC_ERR_KEY_USAGE;
			awebcryptoerr[24] = WC_ERR_END;
			$VALUES = awebcryptoerr;
		}

		private WebCryptoErr(String s, int i)
		{
			super(s, i);
		}
	}


	private static final boolean DEBUG = false;
	private static final String TAG = "WebCrypto Class";
	private static int mKeyMapCounter = 0;
	private HandleWebcryptoResponseInterface mHandleWebcryptoResponseInterface;
	private Map mKeyMap;
	private BlockingQueue mReqQueue;
	private volatile boolean mStop;
	private TZInterface mTZInterface;
	private Thread mThread;

	public WebCrypto(Context context, HandleWebcryptoResponseInterface handlewebcryptoresponseinterface)
	{
		mStop = true;
		mKeyMap = new HashMap();
		mReqQueue = new LinkedBlockingQueue();
		mHandleWebcryptoResponseInterface = handlewebcryptoresponseinterface;
		mTZInterface = new TZInterface(context);
		mThread = new Thread(new Runnable() {

			final WebCrypto this$0;

			public void run()
			{
_L2:
				if (!mStop)
					break MISSING_BLOCK_LABEL_40;
				String s;
				boolean flag;
				s = (String)mReqQueue.take();
				flag = mStop;
				if (flag)
					break MISSING_BLOCK_LABEL_49;
				Log.w("WebCrypto Class", "Thread Joined");
				return;
				try
				{
					invoke(s);
				}
				catch (Exception exception) { }
				if (true) goto _L2; else goto _L1
_L1:
			}

			
			{
				this$0 = WebCrypto.this;
				super();
			}
		});
		mThread.start();
	}

	private byte[] base64Decode(String s)
	{
		return Base64.decode(s.getBytes(), 2);
	}

	private byte[] base64DecodeUrlSafeStr(String s)
	{
		String s1 = s.replace('-', '+').replace('_', '/');
		s1.length() % 4;
		JVM INSTR tableswitch 0 3: default 52
	//	               0 52
	//	               1 52
	//	               2 58
	//	               3 101;
		   goto _L1 _L1 _L1 _L2 _L3
_L1:
		return Base64.decode(s1, 2);
_L2:
		String s2 = (new StringBuilder()).append(s1).append("=").toString();
		s1 = (new StringBuilder()).append(s2).append("=").toString();
		continue; /* Loop/switch isn't completed */
_L3:
		s1 = (new StringBuilder()).append(s1).append("=").toString();
		if (true) goto _L1; else goto _L4
_L4:
	}

	private String base64EncodeStr(byte abyte0[])
	{
		return new String(Base64.encode(abyte0, 2));
	}

	private String base64EncodeUrlSafe(byte abyte0[])
	{
		return (new String(Base64.encode(abyte0, 3))).replace('+', '-').replace('/', '_');
	}

	private boolean decrypt(String s, HashMap hashmap, HashMap hashmap1)
	{
		HashMap hashmap3;
		Integer integer;
		String s1;
		byte abyte0[];
		WebCryptoKey webcryptokey;
		HashMap hashmap2 = (HashMap)hashmap.get("algorithm");
		hashmap3 = (HashMap)hashmap2.get("params");
		integer = Integer.valueOf(hashmap.get("keyHandle").toString());
		s1 = hashmap2.get("name").toString();
		abyte0 = base64Decode(hashmap.get("buffer").toString());
		webcryptokey = (WebCryptoKey)mKeyMap.get(integer);
		if (!s1.equals("RSAES-PKCS1-v1_5")) goto _L2; else goto _L1
_L1:
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		if (webcryptokey.getPublicKey() != null) goto _L4; else goto _L3
_L3:
		cipher.init(2, webcryptokey.getPrivateKey());
_L5:
		byte abyte4[] = cipher.doFinal(abyte0);
		byte abyte3[] = abyte4;
_L6:
		String s2 = base64EncodeStr(abyte3);
		((HashMap)hashmap1.get("payload")).put("buffer", s2);
		setSuccess(hashmap1);
		return true;
_L4:
		try
		{
			cipher.init(2, webcryptokey.getPublicKey());
		}
		catch (Exception exception1)
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		  goto _L5
_L2:
		byte abyte1[] = base64DecodeUrlSafeStr(hashmap3.get("iv").toString());
		byte abyte2[] = mTZInterface.encryptDecryptAESCBC(false, integer.intValue(), abyte1, abyte0);
		abyte3 = abyte2;
		  goto _L6
		Exception exception;
		exception;
		sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
		return false;
		  goto _L5
	}

	private boolean digest(String s, HashMap hashmap, HashMap hashmap1)
	{
		String s1 = hashmap.get("algorithm").toString();
		if (!s1.equals("SHA-256") && !s1.equals("SHA-384"))
		{
			sendError(s, WebCryptoErr.WC_ERR_UNKNOWN_ALGO.ordinal());
			return false;
		}
		if (!hashmap.containsKey("buffer"))
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		String s2 = hashmap.get("buffer").toString();
		byte abyte0[];
		String s3;
		try
		{
			abyte0 = mTZInterface.digestSHA256(Base64.decode(s2.getBytes(), 2));
		}
		catch (Exception exception)
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		s3 = Base64.encodeToString(abyte0, 2);
		((HashMap)hashmap1.get("payload")).put("buffer", s3);
		setSuccess(hashmap1);
		return true;
	}

	private boolean encrypt(String s, HashMap hashmap, HashMap hashmap1)
	{
		HashMap hashmap3;
		Integer integer;
		String s1;
		byte abyte0[];
		WebCryptoKey webcryptokey;
		HashMap hashmap2 = (HashMap)hashmap.get("algorithm");
		hashmap3 = (HashMap)hashmap2.get("params");
		integer = Integer.valueOf(hashmap.get("keyHandle").toString());
		s1 = hashmap2.get("name").toString();
		abyte0 = base64Decode(hashmap.get("buffer").toString());
		webcryptokey = (WebCryptoKey)mKeyMap.get(integer);
		if (!s1.equals("RSAES-PKCS1-v1_5")) goto _L2; else goto _L1
_L1:
		Cipher cipher;
		cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		if (webcryptokey.getPrivateKey() != null)
			break MISSING_BLOCK_LABEL_201;
		cipher.init(1, webcryptokey.getPublicKey());
_L3:
		cipher.doFinal(abyte0);
_L2:
		if (s1.equals("AES-CBC"))
		{
			byte abyte1[] = base64Decode(hashmap3.get("iv").toString());
			byte abyte2[];
			String s2;
			Exception exception1;
			try
			{
				abyte2 = mTZInterface.encryptDecryptAESCBC(true, integer.intValue(), abyte1, abyte0);
			}
			catch (Exception exception)
			{
				sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
				return false;
			}
			s2 = base64EncodeStr(abyte2);
			((HashMap)hashmap1.get("payload")).put("buffer", s2);
			setSuccess(hashmap1);
			return true;
		} else
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		try
		{
			cipher.init(1, webcryptokey.getPrivateKey());
		}
		// Misplaced declaration of an exception variable
		catch (Exception exception1)
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		  goto _L3
	}

	private boolean exportJwk(String s, Integer integer, String s1, HashMap hashmap)
	{
		WebCryptoKey webcryptokey = (WebCryptoKey)mKeyMap.get(integer);
		if (webcryptokey.getPrivateKey() != null || webcryptokey.getPublicKey() != null)
		{
			HashMap hashmap1 = new HashMap();
			hashmap1.put("alg", "RSA1_5");
			hashmap1.put("kty", "RSA");
			hashmap1.put("extractable", Boolean.valueOf(true));
			hashmap1.put("n", new String(webcryptokey.getPublicKey().getModulus().toByteArray()));
			hashmap1.put("e", new String(webcryptokey.getPublicKey().getPublicExponent().toByteArray()));
			String s2 = (new JSONObject(hashmap1)).toString();
			((HashMap)hashmap.get("payload")).put("buffer", base64EncodeStr(s2.getBytes()));
			setSuccess(hashmap);
			return true;
		} else
		{
			HashMap hashmap2 = new HashMap();
			hashmap2.put("alg", "A128CBC");
			hashmap2.put("kty", "oct");
			hashmap2.put("use", "enc");
			hashmap2.put("extractable", Boolean.valueOf(webcryptokey.isExtractable()));
			hashmap2.put("k", webcryptokey.getKeyData64());
			String s3 = (new JSONObject(hashmap2)).toString();
			((HashMap)hashmap.get("payload")).put("buffer", base64EncodeUrlSafe(s3.getBytes()));
			setSuccess(hashmap);
			return true;
		}
	}

	private boolean exportKey(String s, HashMap hashmap, HashMap hashmap1)
	{
		HashMap hashmap2;
		String s2;
		Integer integer = Integer.valueOf(hashmap.get("keyHandle").toString());
		String s1 = hashmap.get("format").toString();
		if (!isValidKeyFormat(s1))
		{
			sendError(s, WebCryptoErr.WC_ERR_UNSUPPORTED_KEY_ENCODING.ordinal());
			return false;
		}
		if (s1.equals("jwk"))
			return exportJwk(s, integer, s1, hashmap1);
		hashmap2 = (HashMap)hashmap1.get("payload");
		WebCryptoKey webcryptokey = (WebCryptoKey)mKeyMap.get(integer);
		if (s1.equals("spki"))
		{
			WebCryptoKey webcryptokey1 = (WebCryptoKey)mKeyMap.get(integer);
			RsaSpki rsaspki = new RsaSpki();
			rsaspki.setRawModulus(webcryptokey1.getPublicKey().getModulus());
			rsaspki.setRawExponent(webcryptokey1.getPublicKey().getPublicExponent());
			hashmap2.put("buffer", rsaspki.getBase64Encoded());
			setSuccess(hashmap1);
			return true;
		}
		if (s1.equals("pkcs8"))
		{
			hashmap2.put("buffer", base64EncodeStr(((WebCryptoKey)mKeyMap.get(integer)).getPrivateKey().getEncoded()));
			setSuccess(hashmap1);
			return true;
		}
		if (!webcryptokey.isExtractable())
		{
			sendError(s, WebCryptoErr.WC_ERR_CIPHERERROR.ordinal());
			return false;
		}
		s2 = webcryptokey.getKeyData64().replace('-', '+').replace('_', '/');
		s2.length() % 4;
		JVM INSTR tableswitch 0 3: default 316
	//	               0 316
	//	               1 316
	//	               2 333
	//	               3 380;
		   goto _L1 _L1 _L1 _L2 _L3
_L1:
		hashmap2.put("buffer", s2);
		setSuccess(hashmap1);
		return true;
_L2:
		String s3 = (new StringBuilder()).append(s2).append("=").toString();
		s2 = (new StringBuilder()).append(s3).append("=").toString();
		continue; /* Loop/switch isn't completed */
_L3:
		s2 = (new StringBuilder()).append(s2).append("=").toString();
		if (true) goto _L1; else goto _L4
_L4:
	}

	private boolean generateKey(String s, HashMap hashmap, HashMap hashmap1)
	{
		boolean flag1;
		String s1;
		HashMap hashmap3;
		boolean flag = hashmap.containsKey("extractable");
		flag1 = false;
		if (flag)
			flag1 = ((Boolean)hashmap.get("extractable")).booleanValue();
		HashMap hashmap2 = (HashMap)hashmap.get("algorithm");
		s1 = hashmap2.get("name").toString();
		boolean flag2 = hashmap2.containsKey("params");
		hashmap3 = null;
		if (flag2)
			hashmap3 = (HashMap)hashmap2.get("params");
		if (s1.equals("SYSTEM"))
		{
			ArrayList arraylist = new ArrayList();
			arraylist.add("wrap");
			arraylist.add("unwrap");
			HashMap hashmap4 = (HashMap)hashmap1.get("payload");
			HashMap hashmap5 = new HashMap();
			hashmap5.put("name", "AES-KW");
			hashmap4.put("handle", Integer.valueOf(804));
			hashmap4.put("type", "secret");
			hashmap4.put("extractable", Boolean.valueOf(false));
			hashmap4.put("algorithm", hashmap5);
			hashmap4.put("keyUsage", arraylist);
			setSuccess(hashmap1);
			return true;
		}
		if (!s1.equals("AES-CBC")) goto _L2; else goto _L1
_L1:
		String s5 = hashmap3.get("length").toString();
		byte abyte3[];
		String s6;
		WebCryptoKey.KeyType keytype1;
		Map map3;
		Integer integer3;
		WebCryptoKey webcryptokey3;
		HashMap hashmap10;
		try
		{
			abyte3 = mTZInterface.generateRandom(Integer.valueOf(s5).intValue() / 8);
		}
		catch (Exception exception2)
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		s6 = base64EncodeStr(abyte3);
		keytype1 = WebCryptoKey.KeyType.secret;
		mKeyMapCounter = 1 + mKeyMapCounter;
		map3 = mKeyMap;
		integer3 = Integer.valueOf(mKeyMapCounter);
		webcryptokey3 = new WebCryptoKey(s6, keytype1, flag1);
		map3.put(integer3, webcryptokey3);
		hashmap10 = (HashMap)hashmap1.get("payload");
		hashmap10.put("handle", Integer.valueOf(mKeyMapCounter));
		hashmap10.put("type", keytype1.toString());
		hashmap10.put("extractable", Boolean.valueOf(flag1));
		hashmap10.put("algorithm", hashmap.get("algorithm"));
		hashmap10.put("keyUsage", new ArrayList());
		setSuccess(hashmap1);
_L4:
		return true;
_L2:
		if (!s1.equals("HMAC"))
			break MISSING_BLOCK_LABEL_688;
		((HashMap)hashmap3.get("hash")).get("name").toString();
		NoSuchAlgorithmException nosuchalgorithmexception;
		byte abyte2[];
		MessageDigest messagedigest;
		String s4;
		WebCryptoKey.KeyType keytype;
		Map map2;
		Integer integer2;
		WebCryptoKey webcryptokey2;
		HashMap hashmap9;
		try
		{
			abyte2 = mTZInterface.generateRandom(Integer.valueOf(32).intValue());
		}
		catch (Exception exception1)
		{
			try
			{
				sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			}
			// Misplaced declaration of an exception variable
			catch (NoSuchAlgorithmException nosuchalgorithmexception)
			{
				sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
				return false;
			}
			return false;
		}
		messagedigest = MessageDigest.getInstance("SHA-256");
		messagedigest.reset();
		messagedigest.update(abyte2);
		s4 = base64EncodeUrlSafe(messagedigest.digest(abyte2));
		keytype = WebCryptoKey.KeyType.secret;
		mKeyMapCounter = 1 + mKeyMapCounter;
		map2 = mKeyMap;
		integer2 = Integer.valueOf(mKeyMapCounter);
		webcryptokey2 = new WebCryptoKey(s4, keytype, flag1);
		map2.put(integer2, webcryptokey2);
		hashmap9 = (HashMap)hashmap1.get("payload");
		hashmap9.put("handle", Integer.valueOf(mKeyMapCounter));
		hashmap9.put("type", keytype.toString());
		hashmap9.put("extractable", Boolean.valueOf(flag1));
		hashmap9.put("algorithm", hashmap.get("algorithm"));
		hashmap9.put("keyUsage", new ArrayList());
		setSuccess(hashmap1);
		continue; /* Loop/switch isn't completed */
		if (s1.equals("RSAES-PKCS1-v1_5") || s1.equals("RSASSA-PKCS1-v1_5"))
		{
			String s2 = hashmap3.get("modulusLength").toString();
			String s3 = hashmap3.get("publicExponent").toString();
			try
			{
				KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA", "BC");
				keypairgenerator.initialize(new RSAKeyGenParameterSpec(Integer.valueOf(s2).intValue(), new BigInteger(base64Decode(s3))));
				KeyPair keypair = keypairgenerator.generateKeyPair();
				PrivateKey privatekey = keypair.getPrivate();
				PublicKey publickey = keypair.getPublic();
				byte abyte0[] = privatekey.getEncoded();
				byte abyte1[] = publickey.getEncoded();
				KeyFactory keyfactory = KeyFactory.getInstance("RSA");
				X509EncodedKeySpec x509encodedkeyspec = new X509EncodedKeySpec(abyte1);
				RSAPublicKey rsapublickey = (RSAPublicKey)keyfactory.generatePublic(x509encodedkeyspec);
				KeyFactory keyfactory1 = KeyFactory.getInstance("RSA");
				PKCS8EncodedKeySpec pkcs8encodedkeyspec = new PKCS8EncodedKeySpec(abyte0);
				RSAPrivateKey rsaprivatekey = (RSAPrivateKey)keyfactory1.generatePrivate(pkcs8encodedkeyspec);
				mKeyMapCounter = 1 + mKeyMapCounter;
				Map map = mKeyMap;
				Integer integer = Integer.valueOf(mKeyMapCounter);
				WebCryptoKey webcryptokey = new WebCryptoKey(rsaprivatekey, rsapublickey, WebCryptoKey.KeyType.PUBLIC, flag1);
				map.put(integer, webcryptokey);
				HashMap hashmap6 = new HashMap();
				hashmap6.put("handle", Integer.valueOf(mKeyMapCounter));
				hashmap6.put("type", WebCryptoKey.KeyType.PUBLIC.toString().toLowerCase());
				hashmap6.put("extractable", Boolean.valueOf(true));
				hashmap6.put("algorithm", hashmap.get("algorithm"));
				hashmap6.put("keyUsage", new ArrayList());
				mKeyMapCounter = 1 + mKeyMapCounter;
				Map map1 = mKeyMap;
				Integer integer1 = Integer.valueOf(mKeyMapCounter);
				WebCryptoKey webcryptokey1 = new WebCryptoKey(rsaprivatekey, rsapublickey, WebCryptoKey.KeyType.PRIVATE, flag1);
				map1.put(integer1, webcryptokey1);
				HashMap hashmap7 = new HashMap();
				hashmap7.put("handle", Integer.valueOf(mKeyMapCounter));
				hashmap7.put("type", WebCryptoKey.KeyType.PRIVATE.toString().toLowerCase());
				hashmap7.put("extractable", Boolean.valueOf(false));
				hashmap7.put("algorithm", hashmap.get("algorithm"));
				hashmap7.put("keyUsage", new ArrayList());
				HashMap hashmap8 = (HashMap)hashmap1.get("payload");
				hashmap8.put("privateKey", hashmap7);
				hashmap8.put("publicKey", hashmap6);
				setSuccess(hashmap1);
			}
			catch (Exception exception)
			{
				sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
				return false;
			}
		}
		if (true) goto _L4; else goto _L3
_L3:
	}

	private boolean getKeyByName(String s, HashMap hashmap, HashMap hashmap1)
	{
		String s1;
		ArrayList arraylist;
		HashMap hashmap3;
		s1 = hashmap.get("keyName").toString();
		if (!s1.equals("Kde") && !s1.equals("Kdh") && !s1.equals("Kdw") && !s1.equals("Kds"))
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		String as[];
		HashMap hashmap2;
		try
		{
			as = mTZInterface.getKeyByName(s1);
		}
		catch (Exception exception)
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		hashmap2 = (HashMap)hashmap1.get("payload");
		arraylist = new ArrayList();
		hashmap3 = new HashMap();
		if (!s1.equals("Kde")) goto _L2; else goto _L1
_L1:
		arraylist.add("encrypt");
		arraylist.add("decrypt");
		hashmap3.put("name", "AES-CBC");
_L4:
		if (s1.equals("Kdw"))
		{
			arraylist.add("wrap");
			arraylist.add("unwrap");
			hashmap3.put("name", "AES-KW");
		}
		if (s1.equals("Kds"))
		{
			arraylist.add("wrap");
			arraylist.add("unwrap");
			hashmap3.put("name", "AES-KW");
		}
		hashmap2.put("name", s1);
		hashmap2.put("handle", as[0]);
		hashmap2.put("id", Base64.encodeToString(as[1].getBytes(), 2));
		hashmap2.put("type", "secret");
		hashmap2.put("extractable", Boolean.valueOf(false));
		hashmap2.put("algorithm", hashmap3);
		hashmap2.put("keyUsage", arraylist);
		setSuccess(hashmap1);
		return true;
_L2:
		if (s1.equals("Kdh"))
		{
			arraylist.add("sign");
			arraylist.add("verify");
			hashmap3.put("name", "HMAC");
		}
		if (true) goto _L4; else goto _L3
_L3:
	}

	private boolean importJwk(String s, String s1, boolean flag, HashMap hashmap)
	{
		String s2 = new String(Base64.decode(s1.getBytes(), 0));
		HashMap hashmap1;
		JSONObject jsonobject = new JSONObject(s2);
		hashmap1 = (HashMap)JsonHelper.toMap(jsonobject);
		if (hashmap1.containsKey("kty"))
			break MISSING_BLOCK_LABEL_61;
		sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
		return false;
		String s3;
		HashMap hashmap2;
		WebCryptoKey.KeyType keytype;
		BigInteger biginteger;
		BigInteger biginteger1;
		RSAPublicKeySpec rsapublickeyspec;
		RSAPublicKey rsapublickey;
		Map map;
		Integer integer;
		WebCryptoKey webcryptokey;
		try
		{
			s3 = hashmap1.get("alg").toString();
			hashmap1.get("extractable").toString();
			hashmap2 = new HashMap();
			if (s3.equals("A128CBC"))
			{
				hashmap2.put("name", "AES-CBC");
				String s4 = hashmap1.get("use").toString();
				String s5 = hashmap1.get("kty").toString();
				ArrayList arraylist = new ArrayList();
				if (s4.equals("enc"))
				{
					arraylist.add("encrypt");
					arraylist.add("decrypt");
				}
				if (s5.equals("oct"))
				{
					String s6 = hashmap1.get("k").toString();
					WebCryptoKey.KeyType keytype1 = WebCryptoKey.KeyType.secret;
					mKeyMapCounter = 1 + mKeyMapCounter;
					Map map1 = mKeyMap;
					Integer integer1 = Integer.valueOf(mKeyMapCounter);
					WebCryptoKey webcryptokey1 = new WebCryptoKey(s6, keytype1, flag);
					map1.put(integer1, webcryptokey1);
					HashMap hashmap4 = (HashMap)hashmap.get("payload");
					hashmap4.put("handle", Integer.valueOf(mKeyMapCounter));
					hashmap4.put("type", keytype1.toString());
					hashmap4.put("extractable", Boolean.valueOf(flag));
					hashmap4.put("algorithm", hashmap2);
					hashmap4.put("keyUsage", arraylist);
					setSuccess(hashmap);
				}
				break MISSING_BLOCK_LABEL_635;
			}
		}
		catch (JSONException jsonexception)
		{
			return false;
		}
		if (!s3.equals("RSA1_5"))
			break MISSING_BLOCK_LABEL_635;
		keytype = WebCryptoKey.KeyType.PRIVATE;
		mKeyMapCounter = 1 + mKeyMapCounter;
		if (!hashmap1.containsKey("n") || !hashmap1.containsKey("e"))
			break MISSING_BLOCK_LABEL_523;
		keytype = WebCryptoKey.KeyType.PUBLIC;
		biginteger = new BigInteger(hashmap1.get("n").toString().getBytes());
		biginteger1 = new BigInteger(hashmap1.get("e").toString().getBytes());
		rsapublickeyspec = new RSAPublicKeySpec(biginteger, biginteger1);
		try
		{
			rsapublickey = (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(rsapublickeyspec);
		}
		catch (InvalidKeySpecException invalidkeyspecexception)
		{
			rsapublickey = null;
		}
		catch (NoSuchAlgorithmException nosuchalgorithmexception)
		{
			rsapublickey = null;
		}
		map = mKeyMap;
		integer = Integer.valueOf(mKeyMapCounter);
		webcryptokey = new WebCryptoKey(rsapublickey, keytype, flag);
		map.put(integer, webcryptokey);
		hashmap2 = new HashMap();
		hashmap2.put("name", "RSAES-PKCS1-v1_5");
		HashMap hashmap3 = (HashMap)hashmap.get("payload");
		hashmap3.put("handle", Integer.valueOf(mKeyMapCounter));
		hashmap3.put("type", keytype.toString().toLowerCase());
		hashmap3.put("extractable", Boolean.valueOf(true));
		hashmap3.put("algorithm", hashmap2);
		hashmap3.put("keyUsage", new ArrayList());
		setSuccess(hashmap);
		return true;
	}

	private boolean importKey(String s, HashMap hashmap, HashMap hashmap1)
	{
		String s1 = hashmap.get("format").toString();
		if (!isValidKeyFormat(s1))
		{
			sendError(s, WebCryptoErr.WC_ERR_UNSUPPORTED_KEY_ENCODING.ordinal());
			return false;
		}
		String s2 = hashmap.get("keyData").toString();
		boolean flag = hashmap.containsKey("extractable");
		boolean flag1 = false;
		if (flag)
			flag1 = ((Boolean)hashmap.get("extractable")).booleanValue();
		String s3 = ((HashMap)hashmap.get("algorithm")).get("name").toString();
		WebCryptoKey.KeyType  = WebCryptoKey.KeyType.secret;
		if (s1.equals("jwk"))
			return importJwk(s, s2, flag1, hashmap1);
		if (s1.equals("spki") && (s3.equals("RSAES-PKCS1-v1_5") || s3.equals("RSASSA-PKCS1-v1_5")))
		{
			RsaSpki rsaspki = new RsaSpki();
			if (!rsaspki.setBase64Encoded(s2))
				return false;
			WebCryptoKey.KeyType keytype2 = WebCryptoKey.KeyType.PUBLIC;
			RSAPublicKeySpec rsapublickeyspec = new RSAPublicKeySpec(rsaspki.getRawModulus(), rsaspki.getRawExponent());
			WebCryptoKey.KeyType keytype;
			Map map;
			Integer integer;
			WebCryptoKey webcryptokey;
			Exception exception;
			HashMap hashmap2;
			WebCryptoKey.KeyType keytype1;
			byte abyte0[];
			InvalidKeySpecException invalidkeyspecexception;
			RSAPrivateKey rsaprivatekey;
			Map map1;
			Integer integer1;
			WebCryptoKey webcryptokey1;
			HashMap hashmap3;
			NoSuchAlgorithmException nosuchalgorithmexception;
			KeyFactory keyfactory;
			PKCS8EncodedKeySpec pkcs8encodedkeyspec;
			RSAPublicKey rsapublickey;
			Map map2;
			Integer integer2;
			WebCryptoKey webcryptokey2;
			HashMap hashmap4;
			ArrayList arraylist;
			try
			{
				rsapublickey = (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(rsapublickeyspec);
			}
			catch (InvalidKeySpecException invalidkeyspecexception1)
			{
				rsapublickey = null;
			}
			catch (NoSuchAlgorithmException nosuchalgorithmexception1)
			{
				rsapublickey = null;
			}
			mKeyMapCounter = 1 + mKeyMapCounter;
			map2 = mKeyMap;
			integer2 = Integer.valueOf(mKeyMapCounter);
			webcryptokey2 = new WebCryptoKey(rsapublickey, keytype2, flag1);
			map2.put(integer2, webcryptokey2);
			hashmap4 = (HashMap)hashmap1.get("payload");
			arraylist = (ArrayList)hashmap.get("keyUsage");
			hashmap4.put("handle", Integer.valueOf(mKeyMapCounter));
			hashmap4.put("type", keytype2.toString().toLowerCase());
			hashmap4.put("extractable", Boolean.valueOf(true));
			hashmap4.put("algorithm", hashmap.get("algorithm"));
			hashmap4.put("keyUsage", arraylist);
			Log.e("WebCrypto Class", (new StringBuilder()).append("import success ").append(mKeyMapCounter).toString());
			setSuccess(hashmap1);
			return true;
		}
		if (s1.equals("pkcs8") && s3.equals("RSAES-PKCS1-v1_5"))
		{
			keytype1 = WebCryptoKey.KeyType.PRIVATE;
			abyte0 = base64Decode(s2);
			try
			{
				keyfactory = KeyFactory.getInstance("RSA");
				pkcs8encodedkeyspec = new PKCS8EncodedKeySpec(abyte0);
				rsaprivatekey = (RSAPrivateKey)keyfactory.generatePrivate(pkcs8encodedkeyspec);
			}
			// Misplaced declaration of an exception variable
			catch (InvalidKeySpecException invalidkeyspecexception)
			{
				rsaprivatekey = null;
			}
			// Misplaced declaration of an exception variable
			catch (NoSuchAlgorithmException nosuchalgorithmexception)
			{
				rsaprivatekey = null;
			}
			mKeyMapCounter = 1 + mKeyMapCounter;
			map1 = mKeyMap;
			integer1 = Integer.valueOf(mKeyMapCounter);
			webcryptokey1 = new WebCryptoKey(rsaprivatekey, keytype1, flag1);
			map1.put(integer1, webcryptokey1);
			hashmap3 = (HashMap)hashmap1.get("payload");
			hashmap3.put("handle", Integer.valueOf(mKeyMapCounter));
			hashmap3.put("type", keytype1.toString().toLowerCase());
			hashmap3.put("extractable", Boolean.valueOf(flag1));
			hashmap3.put("algorithm", hashmap.get("algorithm"));
			hashmap3.put("keyUsage", new ArrayList());
			setSuccess(hashmap1);
			return true;
		}
		if (s3.equals("HMAC") || s3.equals("AES-CBC") || s3.equals("AES-GCM") || s3.equals("AES-CTR") || s3.equals("AES-KW"))
		{
			if (!s1.equals("raw"))
				return false;
			keytype = WebCryptoKey.KeyType.secret;
			mKeyMapCounter = 1 + mKeyMapCounter;
			map = mKeyMap;
			integer = Integer.valueOf(mKeyMapCounter);
			webcryptokey = new WebCryptoKey(s2, keytype, flag1);
			map.put(integer, webcryptokey);
			try
			{
				mTZInterface.importAESKey(mKeyMapCounter, base64Decode(s2));
			}
			// Misplaced declaration of an exception variable
			catch (Exception exception)
			{
				sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
				return false;
			}
			hashmap2 = (HashMap)hashmap1.get("payload");
			hashmap2.put("handle", Integer.valueOf(mKeyMapCounter));
			hashmap2.put("type", keytype.toString());
			hashmap2.put("extractable", Boolean.valueOf(flag1));
			hashmap2.put("algorithm", hashmap.get("algorithm"));
			hashmap2.put("keyUsage", new ArrayList());
			setSuccess(hashmap1);
			return true;
		} else
		{
			return false;
		}
	}

	private void invoke(String s)
	{
		HashMap hashmap;
		hashmap = (HashMap)JsonHelper.toMap(new JSONObject(s));
		if (!hashmap.containsKey("idx"))
			return;
		String s1;
		String s2;
		HashMap hashmap1;
		HashMap hashmap2;
		if (!hashmap.containsKey("method"))
			break MISSING_BLOCK_LABEL_479;
		s1 = hashmap.get("method").toString();
		s2 = hashmap.get("idx").toString();
		hashmap1 = new HashMap();
		hashmap1.put("method", s1);
		hashmap1.put("idx", s2);
		hashmap1.put("success", Boolean.valueOf(false));
		hashmap1.put("errorMessage", "unknown error");
		hashmap1.put("errorCode", Integer.valueOf(WebCryptoErr.WC_ERR_UNKNOWN.ordinal()));
		hashmap1.put("payload", new HashMap());
		hashmap2 = (HashMap)hashmap.get("argsObj");
		if (!s1.equals("digest")) goto _L2; else goto _L1
_L1:
		boolean flag1 = digest(s2, hashmap2, hashmap1);
		if (!flag1)
			break MISSING_BLOCK_LABEL_479;
_L4:
		try
		{
			send(hashmap1);
			return;
		}
		catch (JSONException jsonexception)
		{
			sendError("0", WebCryptoErr.WC_ERR_BADARG.ordinal());
		}
		return;
_L2:
		if (!s1.equals("import"))
			break MISSING_BLOCK_LABEL_236;
		if (importKey(s2, hashmap2, hashmap1)) goto _L4; else goto _L3
_L3:
		return;
		if (!s1.equals("export"))
			break MISSING_BLOCK_LABEL_261;
		if (exportKey(s2, hashmap2, hashmap1)) goto _L4; else goto _L5
_L5:
		return;
		if (!s1.equals("generate"))
			break MISSING_BLOCK_LABEL_286;
		if (generateKey(s2, hashmap2, hashmap1)) goto _L4; else goto _L6
_L6:
		return;
		if (!s1.equals("encrypt"))
			break MISSING_BLOCK_LABEL_311;
		if (encrypt(s2, hashmap2, hashmap1)) goto _L4; else goto _L7
_L7:
		return;
		if (!s1.equals("decrypt"))
			break MISSING_BLOCK_LABEL_336;
		if (decrypt(s2, hashmap2, hashmap1)) goto _L4; else goto _L8
_L8:
		return;
		if (!s1.equals("sign"))
			break MISSING_BLOCK_LABEL_361;
		if (sign(s2, hashmap2, hashmap1)) goto _L4; else goto _L9
_L9:
		return;
		if (!s1.equals("verify"))
			break MISSING_BLOCK_LABEL_386;
		if (verify(s2, hashmap2, hashmap1)) goto _L4; else goto _L10
_L10:
		return;
		if (!s1.equals("unwrapKey"))
			break MISSING_BLOCK_LABEL_411;
		if (unwrapKey(s2, hashmap2, hashmap1)) goto _L4; else goto _L11
_L11:
		return;
		if (!s1.equals("wrapKey"))
			continue; /* Loop/switch isn't completed */
		if (wrapKey(s2, hashmap2, hashmap1)) goto _L4; else goto _L12
_L12:
		return;
		if (!s1.equals("getKeyByName")) goto _L4; else goto _L13
_L13:
		boolean flag = getKeyByName(s2, hashmap2, hashmap1);
		if (!flag)
			return;
		  goto _L4
		Exception exception;
		exception;
		sendError(s2, WebCryptoErr.WC_ERR_BADARG.ordinal());
	}

	private boolean isValidKeyFormat(String s)
	{
		while (s.equals("raw") || s.equals("pkcs8") || s.equals("spki") || s.equals("jwk")) 
			return true;
		return false;
	}

	private void send(Map map)
	{
		try
		{
			String s = JsonHelper.toJSON(map).toString();
			mHandleWebcryptoResponseInterface.onHandleResponse(s);
			return;
		}
		catch (JSONException jsonexception)
		{
			jsonexception.printStackTrace();
		}
	}

	private void sendError(String s, int i)
	{
		HashMap hashmap = new HashMap();
		hashmap.put("idx", s);
		hashmap.put("success", Boolean.valueOf(false));
		hashmap.put("errorMessage", "TODO get real string");
		hashmap.put("errorCode", Integer.valueOf(i));
		hashmap.put("payload", new HashMap());
		send(hashmap);
	}

	private void setSuccess(Map map)
	{
		map.put("success", Boolean.valueOf(true));
		map.put("errorMessage", "");
		map.put("errorCode", Integer.valueOf(WebCryptoErr.WC_ERR_OK.ordinal()));
	}

	private boolean sign(String s, HashMap hashmap, HashMap hashmap1)
	{
		Integer integer;
		String s1;
		byte abyte0[];
		WebCryptoKey webcryptokey;
		HashMap hashmap2 = (HashMap)hashmap.get("algorithm");
		HashMap hashmap3 = (HashMap)hashmap2.get("params");
		integer = Integer.valueOf(hashmap.get("keyHandle").toString());
		s1 = hashmap2.get("name").toString();
		hashmap3.get("hash").toString();
		abyte0 = base64Decode(hashmap.get("buffer").toString());
		webcryptokey = (WebCryptoKey)mKeyMap.get(integer);
		if (!s1.equals("RSASSA-PKCS1-v1_5")) goto _L2; else goto _L1
_L1:
		byte abyte1[];
		String s2;
		byte abyte3[];
		try
		{
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(webcryptokey.getPrivateKey());
			signature.update(abyte0);
			abyte3 = signature.sign();
		}
		catch (Exception exception1)
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		abyte1 = abyte3;
_L4:
		s2 = base64EncodeStr(abyte1);
		((HashMap)hashmap1.get("payload")).put("buffer", s2);
		setSuccess(hashmap1);
		return true;
_L2:
		boolean flag = s1.equals("HMAC");
		abyte1 = null;
		if (flag)
		{
			byte abyte2[];
			try
			{
				abyte2 = mTZInterface.digestHmacSHA256(integer.intValue(), abyte0);
			}
			catch (Exception exception)
			{
				sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
				return false;
			}
			abyte1 = abyte2;
		}
		if (true) goto _L4; else goto _L3
_L3:
	}

	private boolean unwrapKey(String s, HashMap hashmap, HashMap hashmap1)
	{
		if (hashmap.get("format") != null)
			return false;
		Integer integer;
		String s3;
		String s4;
		String s5;
		String s7;
		String s8;
		String s1 = hashmap.get("keyData").toString();
		String s2 = new String(Base64.decode(s1, 2));
		integer = Integer.valueOf(hashmap.get("keyHandle").toString());
		HashMap hashmap2 = (HashMap)JsonHelper.toMap(new JSONObject(s2));
		s3 = hashmap2.get("ciphertext").toString();
		s4 = hashmap2.get("initialization_vector").toString();
		HashMap hashmap3 = (HashMap)((ArrayList)hashmap2.get("recipients")).get(0);
		s5 = (String)hashmap3.get("encrypted_key");
		String s6 = (String)hashmap3.get("header");
		s7 = (String)hashmap3.get("integrity_value");
		s8 = (new StringBuilder()).append(s6).append(".").append(s5).append(".").append(s4).toString();
		byte abyte0[];
		byte abyte1[];
		byte abyte2[];
		byte abyte3[];
		byte abyte4[];
		ArrayList arraylist;
		HashMap hashmap4;
		Object obj;
		HashMap hashmap5;
		try
		{
			abyte0 = Base64.decode(s5, 10);
			abyte1 = Base64.decode(s4, 10);
			abyte2 = Base64.decode(s7, 10);
			abyte3 = Base64.decode(s3, 10);
		}
		catch (Exception exception)
		{
			Exception exception1;
			try
			{
				Log.e("WebCrypto Class", (new StringBuilder()).append("Unwrap exception").append(exception.getMessage()).toString());
				sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			}
			catch (JSONException jsonexception)
			{
				sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
				Log.e("WebCrypto Class", "Unwrap failed JSONException ");
				return false;
			}
			return false;
		}
		mKeyMapCounter = 1 + mKeyMapCounter;
		abyte4 = mTZInterface.unwrapKey(integer.intValue(), mKeyMapCounter, abyte0, abyte1, s8.getBytes(), abyte2, abyte3);
		arraylist = new ArrayList();
		if (abyte4[1] != 3) goto _L2; else goto _L1
_L1:
		arraylist.add("encrypt");
		arraylist.add("decrypt");
_L7:
		hashmap4 = new HashMap();
		if (abyte4[2] != 1) goto _L4; else goto _L3
_L3:
		hashmap4.put("name", "HMAC");
_L11:
		obj = null;
		if (hashmap == null)
			break MISSING_BLOCK_LABEL_346;
		obj = hashmap.get("algorithm");
		hashmap5 = (HashMap)hashmap1.get("payload");
		hashmap5.put("handle", Integer.valueOf(mKeyMapCounter));
		hashmap5.put("type", "secret");
		hashmap5.put("extractable", Boolean.valueOf(false));
		if (obj == null)
			break MISSING_BLOCK_LABEL_626;
		hashmap5.put("algorithm", obj);
_L13:
		hashmap5.put("keyUsage", arraylist);
		setSuccess(hashmap1);
		return true;
		exception1;
		sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
		Log.e("WebCrypto Class", "Unwrap failed TZ exception");
		return false;
_L2:
		if (abyte4[1] != 12) goto _L6; else goto _L5
_L5:
		arraylist.add("sign");
		arraylist.add("verify");
		  goto _L7
_L6:
		if (abyte4[1] != 48) goto _L7; else goto _L8
_L8:
		arraylist.add("wrap");
		arraylist.add("unwrap");
		  goto _L7
_L4:
		if (abyte4[2] != 2) goto _L10; else goto _L9
_L9:
		hashmap4.put("name", "AES-CBC");
		  goto _L11
_L10:
		if (abyte4[2] != 3) goto _L11; else goto _L12
_L12:
		hashmap4.put("name", "AES-KW");
		  goto _L11
		hashmap5.put("algorithm", hashmap4);
		  goto _L13
	}

	private boolean verify(String s, HashMap hashmap, HashMap hashmap1)
	{
		Integer integer;
		String s1;
		String s3;
		byte abyte0[];
		byte abyte1[];
		WebCryptoKey webcryptokey;
		HashMap hashmap2 = (HashMap)hashmap.get("algorithm");
		HashMap hashmap3 = (HashMap)hashmap2.get("params");
		integer = Integer.valueOf(hashmap.get("keyHandle").toString());
		s1 = hashmap2.get("name").toString();
		hashmap3.get("hash").toString();
		String s2 = hashmap.get("buffer").toString();
		s3 = hashmap.get("signature").toString();
		abyte0 = base64Decode(s2);
		abyte1 = base64Decode(s3);
		webcryptokey = (WebCryptoKey)mKeyMap.get(integer);
		if (!s1.equals("RSASSA-PKCS1-v1_5")) goto _L2; else goto _L1
_L1:
		Signature signature;
		signature = Signature.getInstance("SHA256withRSA");
		signature.initVerify(webcryptokey.getPublicKey());
		signature.update(abyte0);
		if (!signature.verify(abyte1)) goto _L4; else goto _L3
_L3:
		hashmap1.put("payload", Boolean.valueOf(true));
_L6:
		setSuccess(hashmap1);
		return true;
_L4:
		try
		{
			hashmap1.put("payload", Boolean.valueOf(false));
		}
		catch (Exception exception1)
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		continue; /* Loop/switch isn't completed */
_L2:
		if (s1.equals("HMAC"))
		{
			byte abyte2[];
			try
			{
				abyte2 = mTZInterface.digestHmacSHA256(integer.intValue(), abyte0);
			}
			catch (Exception exception)
			{
				sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
				return false;
			}
			if (s3.equals(base64EncodeStr(abyte2)))
				hashmap1.put("payload", Boolean.valueOf(true));
			else
				hashmap1.put("payload", Boolean.valueOf(false));
		}
		if (true) goto _L6; else goto _L5
_L5:
	}

	private boolean wrapKey(String s, HashMap hashmap, HashMap hashmap1)
	{
		Integer integer = Integer.valueOf(hashmap.get("keyHandle").toString());
		Integer integer1 = Integer.valueOf(hashmap.get("baseKeyHandle").toString());
		String s1;
		try
		{
			s1 = mTZInterface.wrapAESKey(integer.intValue(), integer1.intValue());
		}
		catch (Exception exception)
		{
			sendError(s, WebCryptoErr.WC_ERR_BADARG.ordinal());
			return false;
		}
		((HashMap)hashmap1.get("payload")).put("buffer", s1);
		setSuccess(hashmap1);
		return true;
	}

	public void processReq(String s)
	{
		try
		{
			mReqQueue.put(s);
			return;
		}
		catch (InterruptedException interruptedexception)
		{
			return;
		}
	}

	public void stop()
	{
		if (mTZInterface != null)
		{
			mStop = false;
			try
			{
				mReqQueue.put("{}");
				mThread.join();
			}
			catch (InterruptedException interruptedexception) { }
			mTZInterface.close();
		}
	}




}
