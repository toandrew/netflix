// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.q;

import java.io.Serializable;
import java.util.*;

public final class MetricBatch_a
	implements Serializable, Iterable
{

	private static final long serialVersionUID = 1L;
	final List a = new ArrayList();

	public MetricBatch_a()
	{
	}

	public final boolean a()
	{
		return a.isEmpty();
	}

	public final int b()
	{
		return a.size();
	}

	public final Iterator iterator()
	{
		return a.iterator();
	}

	public final String toString()
	{
		return (new StringBuilder()).append("MetricBatch: [").append(a).append("]").toString();
	}
}
