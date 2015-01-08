// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.mas.kiwi.util;

import java.io.*;

// Referenced classes of package com.amazon.mas.kiwi.util:
//			Base64

public class KiwiVersionEncrypter
{

	private static final String SECRET_KEY = "Kiwi__Version__Obfuscator";

	private KiwiVersionEncrypter()
	{
	}

	private static void checkInput(String s, String s1)
	{
		if (s == null || s.isEmpty())
			throw new IllegalArgumentException((new StringBuilder()).append("input '").append(s1).append("' cannot be null or empty").toString());
		else
			return;
	}

	public static String decrypt(String s)
		throws IOException
	{
		checkInput(s, "text");
		return new String(performXOR(Base64.decode(s.getBytes())), "UTF-8");
	}

	public static String decryptFromFile(String s)
		throws IOException
	{
		BufferedReader bufferedreader;
		checkInput(s, "file");
		bufferedreader = null;
		BufferedReader bufferedreader1 = new BufferedReader(new FileReader(s));
		String s1 = bufferedreader1.readLine();
		Exception exception;
		if (bufferedreader1 != null)
			try
			{
				bufferedreader1.close();
			}
			catch (IOException ioexception1) { }
		return new String(performXOR(Base64.decode(s1.getBytes())), "UTF-8");
		exception;
_L2:
		if (bufferedreader != null)
			try
			{
				bufferedreader.close();
			}
			catch (IOException ioexception) { }
		throw exception;
		exception;
		bufferedreader = bufferedreader1;
		if (true) goto _L2; else goto _L1
_L1:
	}

	public static String encrypt(String s)
	{
		checkInput(s, "text");
		return Base64.encodeBytes(performXOR(s.getBytes()));
	}

	public static void encryptToFile(String s, String s1)
		throws IOException
	{
		String s2;
		BufferedWriter bufferedwriter;
		checkInput(s, "text");
		checkInput(s1, "file");
		s2 = Base64.encodeBytes(performXOR(s.getBytes()));
		bufferedwriter = null;
		BufferedWriter bufferedwriter1 = new BufferedWriter(new FileWriter(s1));
		bufferedwriter1.write(s2);
		bufferedwriter1.flush();
		if (bufferedwriter1 == null)
			break MISSING_BLOCK_LABEL_63;
		bufferedwriter1.close();
		return;
		Exception exception;
		exception;
_L2:
		IOException ioexception1;
		if (bufferedwriter != null)
			try
			{
				bufferedwriter.close();
			}
			catch (IOException ioexception) { }
		throw exception;
		ioexception1;
		return;
		exception;
		bufferedwriter = bufferedwriter1;
		if (true) goto _L2; else goto _L1
_L1:
	}

	public static void main(String args[])
		throws Exception
	{
		if (args == null || args.length == 0)
		{
			System.out.println("Usage: com.amazon.mas.kiwi.util.KiwiVersionEncrypter <textToBeEncrypted> [<encryptToFileName>]");
			return;
		}
		if (args.length > 1)
		{
			encryptToFile(args[0], args[1]);
			return;
		} else
		{
			System.out.println(encrypt(args[0]));
			return;
		}
	}

	private static byte[] performXOR(byte abyte0[])
	{
		byte abyte1[] = new byte[abyte0.length];
		byte abyte2[] = "Kiwi__Version__Obfuscator".getBytes();
		int i = 0;
		for (int j = 0; j < abyte0.length; j++)
		{
			abyte1[j] = (byte)(abyte0[j] ^ abyte2[i]);
			if (++i >= abyte2.length)
				i = 0;
		}

		return abyte1;
	}
}
