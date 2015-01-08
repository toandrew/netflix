// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.q;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Metric_b
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	public final Map a = new HashMap();

	public Metric_b(String s)
	{
		a.put("name", s);
		a.put("time", String.valueOf(System.currentTimeMillis()));
	}

	public final Metric_b a(String s, String s1)
	{
		a.put(s, s1);
		return this;
	}

	public String toString()
	{
		return (new StringBuilder()).append("Metric: [").append(a.toString()).append("]").toString();
	}
}
