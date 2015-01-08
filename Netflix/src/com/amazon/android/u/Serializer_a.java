// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.u;

import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.mas.kiwi.util.Base64;
import java.io.*;

public final class Serializer_a
{

	private static final KiwiLogger a = new KiwiLogger("Serializer");

	public Serializer_a()
	{
	}

	public static Object a(String s)
	{
		ObjectInputStream objectinputstream;
		if (s == null || s.length() == 0)
			return null;
		byte abyte0[];
		Object obj;
		IOException ioexception3;
		try
		{
			abyte0 = Base64.decode(s.getBytes());
		}
		catch (IOException ioexception)
		{
			if (KiwiLogger.ERROR_ON)
				a.error("Could not decode string", ioexception);
			return null;
		}
		objectinputstream = new ObjectInputStream(new ByteArrayInputStream(abyte0));
		obj = objectinputstream.readObject();
		try
		{
			objectinputstream.close();
		}
		// Misplaced declaration of an exception variable
		catch (IOException ioexception3)
		{
			return obj;
		}
		return obj;
		Exception exception;
		exception;
		objectinputstream = null;
_L4:
		if (KiwiLogger.ERROR_ON)
			a.error((new StringBuilder()).append("Could not read object from string: ").append(s).toString(), exception);
		Exception exception1;
		if (objectinputstream != null)
		{
			try
			{
				objectinputstream.close();
			}
			catch (IOException ioexception2)
			{
				return null;
			}
			return null;
		} else
		{
			return null;
		}
		exception1;
		objectinputstream = null;
_L2:
		if (objectinputstream != null)
			try
			{
				objectinputstream.close();
			}
			catch (IOException ioexception1) { }
		throw exception1;
		exception1;
		if (true) goto _L2; else goto _L1
_L1:
		exception;
		if (true) goto _L4; else goto _L3
_L3:
	}

	public static String a(Serializable serializable)
	{
		ByteArrayOutputStream bytearrayoutputstream;
		if (serializable == null)
			return null;
		bytearrayoutputstream = new ByteArrayOutputStream();
		ObjectOutputStream objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);
		String s;
		objectoutputstream.writeObject(serializable);
		s = new String(Base64.encodeBytes(bytearrayoutputstream.toByteArray()));
		IOException ioexception;
		Exception exception;
		try
		{
			objectoutputstream.close();
		}
		catch (IOException ioexception3) { }
		return s;
		ioexception;
		objectoutputstream = null;
_L4:
		if (KiwiLogger.ERROR_ON)
			a.error((new StringBuilder()).append("Could not serialize object: ").append(serializable).toString(), ioexception);
		if (objectoutputstream != null)
			try
			{
				objectoutputstream.close();
			}
			catch (IOException ioexception2) { }
		return null;
		exception;
		objectoutputstream = null;
_L2:
		if (objectoutputstream != null)
			try
			{
				objectoutputstream.close();
			}
			catch (IOException ioexception1) { }
		throw exception;
		exception;
		if (true) goto _L2; else goto _L1
_L1:
		ioexception;
		if (true) goto _L4; else goto _L3
_L3:
	}

}
