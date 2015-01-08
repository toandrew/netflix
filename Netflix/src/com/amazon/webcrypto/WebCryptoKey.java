// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.webcrypto;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

public class WebCryptoKey
{
	static final class KeyType extends Enum
	{

		private static final KeyType $VALUES[];
		public static final KeyType PRIVATE;
		public static final KeyType PUBLIC;
		public static final KeyType secret;

		public static KeyType valueOf(String s)
		{
			return (KeyType)Enum.valueOf(com/amazon/webcrypto/WebCryptoKey$KeyType, s);
		}

		public static KeyType[] values()
		{
			return (KeyType[])$VALUES.clone();
		}

		static 
		{
			secret = new KeyType("secret", 0);
			PUBLIC = new KeyType("PUBLIC", 1);
			PRIVATE = new KeyType("PRIVATE", 2);
			KeyType akeytype[] = new KeyType[3];
			akeytype[0] = secret;
			akeytype[1] = PUBLIC;
			akeytype[2] = PRIVATE;
			$VALUES = akeytype;
		}

		private KeyType(String s, int i)
		{
			super(s, i);
		}
	}

	static final class KeyUsage extends Enum
	{

		private static final KeyUsage $VALUES[];
		public static final KeyUsage DECRYPT;
		public static final KeyUsage DERIVE;
		public static final KeyUsage ENCRYPT;
		public static final KeyUsage SIGN;
		public static final KeyUsage UNWRAP;
		public static final KeyUsage VERIFY;
		public static final KeyUsage WRAP;

		public static KeyUsage valueOf(String s)
		{
			return (KeyUsage)Enum.valueOf(com/amazon/webcrypto/WebCryptoKey$KeyUsage, s);
		}

		public static KeyUsage[] values()
		{
			return (KeyUsage[])$VALUES.clone();
		}

		static 
		{
			ENCRYPT = new KeyUsage("ENCRYPT", 0);
			DECRYPT = new KeyUsage("DECRYPT", 1);
			SIGN = new KeyUsage("SIGN", 2);
			VERIFY = new KeyUsage("VERIFY", 3);
			DERIVE = new KeyUsage("DERIVE", 4);
			WRAP = new KeyUsage("WRAP", 5);
			UNWRAP = new KeyUsage("UNWRAP", 6);
			KeyUsage akeyusage[] = new KeyUsage[7];
			akeyusage[0] = ENCRYPT;
			akeyusage[1] = DECRYPT;
			akeyusage[2] = SIGN;
			akeyusage[3] = VERIFY;
			akeyusage[4] = DERIVE;
			akeyusage[5] = WRAP;
			akeyusage[6] = UNWRAP;
			$VALUES = akeyusage;
		}

		private KeyUsage(String s, int i)
		{
			super(s, i);
		}
	}


	private boolean mExtractable;
	private String mKeyData64;
	private RSAPrivateKey mPrivateKey;
	private RSAPublicKey mPublicKey;
	private KeyType mType;
	private List mUsages;

	public WebCryptoKey(String s, KeyType keytype, boolean flag)
	{
		mType = KeyType.PRIVATE;
		mExtractable = false;
		mType = keytype;
		mExtractable = flag;
		mKeyData64 = s;
	}

	public WebCryptoKey(RSAPrivateKey rsaprivatekey, KeyType keytype, boolean flag)
	{
		mType = KeyType.PRIVATE;
		mExtractable = false;
		mType = keytype;
		mExtractable = flag;
		mPrivateKey = rsaprivatekey;
	}

	public WebCryptoKey(RSAPrivateKey rsaprivatekey, RSAPublicKey rsapublickey, KeyType keytype, boolean flag)
	{
		mType = KeyType.PRIVATE;
		mExtractable = false;
		mType = keytype;
		mExtractable = flag;
		mPrivateKey = rsaprivatekey;
		mPublicKey = rsapublickey;
	}

	public WebCryptoKey(RSAPublicKey rsapublickey, KeyType keytype, boolean flag)
	{
		mType = KeyType.PRIVATE;
		mExtractable = false;
		mType = keytype;
		mExtractable = flag;
		mPublicKey = rsapublickey;
	}

	public String getKeyData64()
	{
		if (mExtractable)
			return mKeyData64;
		else
			return null;
	}

	public RSAPrivateKey getPrivateKey()
	{
		return mPrivateKey;
	}

	public RSAPublicKey getPublicKey()
	{
		return mPublicKey;
	}

	public boolean isExtractable()
	{
		return mExtractable;
	}

	public void setPrivateKey(RSAPrivateKey rsaprivatekey)
	{
		mPrivateKey = rsaprivatekey;
	}

	public void setPublicKey(RSAPublicKey rsapublickey)
	{
		mPublicKey = rsapublickey;
	}
}
