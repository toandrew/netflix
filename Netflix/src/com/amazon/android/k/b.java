// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.k;


// Referenced classes of package com.amazon.android.k:
//			c

public final class b
{

	public final c a;
	private final String b;

	public b(c c1, String s)
	{
		a = c1;
		b = s;
	}

	public final String toString()
	{
		return (new StringBuilder()).append(a.a()).append(": ").append(b).toString();
	}
}
