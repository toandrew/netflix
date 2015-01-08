// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.util;

import java.util.*;

public final class b
	implements Iterable
{

	public WeakHashMap a;

	public b()
	{
		a = new WeakHashMap();
	}

	public final void a(Object obj)
	{
		a.put(obj, null);
	}

	public final void b(Object obj)
	{
		a.remove(obj);
	}

	public final Iterator iterator()
	{
		return a.keySet().iterator();
	}

	public final String toString()
	{
		return a.keySet().toString();
	}
}
