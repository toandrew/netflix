// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.n;

import com.amazon.android.framework.resource.b;

// Referenced classes of package com.amazon.android.n:
//			d

public final class DataStore_a
	implements b
{

	public d a;
	private com.amazon.android.framework.resource.a b;

	public DataStore_a()
	{
		a = new d();
	}

	public final Object a(String s)
	{
		return a.b(s);
	}

	public final void a(String s, Object obj)
	{
		a.a(s, obj);
	}

	public final boolean b(String s)
	{
		return a.a(s);
	}

	public final void onResourcesPopulated()
	{
		b.b(a);
	}
}
