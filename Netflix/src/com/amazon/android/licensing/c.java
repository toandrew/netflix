// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.licensing;

import com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b.h;
import java.util.Map;

public final class c
{

	final String a;
	final String b;
	final String c;

	public c(Map map)
		throws h
	{
		if (map == null)
		{
			throw new h("EMPTY", null);
		} else
		{
			a = a("license", map);
			b = a("customerId", map);
			c = a("deviceId", map);
			return;
		}
	}

	private static String a(String s, Map map)
		throws h
	{
		String s1 = (String)map.get(s);
		boolean flag;
		if (s1 == null || s1.length() == 0)
			flag = true;
		else
			flag = false;
		if (flag)
			throw new h("MISSING_FIELD", s);
		else
			return s1;
	}
}
