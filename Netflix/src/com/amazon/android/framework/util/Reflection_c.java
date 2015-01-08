// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.util;

import com.amazon.android.d.a;
import java.lang.reflect.Method;

// Referenced classes of package com.amazon.android.framework.util:
//			KiwiLogger

public final class Reflection_c
{

	private static final KiwiLogger a = new KiwiLogger("Reflection");

	public Reflection_c()
	{
	}

	public static Class a(String s)
	{
		com.amazon.android.d.a.a(s, "String className");
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		Class class1;
		try
		{
			class1 = classloader.loadClass(s);
		}
		catch (ClassNotFoundException classnotfoundexception)
		{
			if (KiwiLogger.TRACE_ON)
				a.trace((new StringBuilder()).append("ClassNoFound: ").append(s).toString());
			return null;
		}
		return class1;
	}

	public static boolean a(Method method)
	{
		com.amazon.android.d.a.a(method, "Method m");
		return method.getParameterTypes().length > 0;
	}

	public static boolean b(Method method)
	{
		com.amazon.android.d.a.a(method, "Method m");
		return method.getReturnType().equals(Void.TYPE);
	}

	public static boolean c(Method method)
	{
		com.amazon.android.d.a.a(method, "Method m");
		return (8 & method.getModifiers()) != 0;
	}

}
