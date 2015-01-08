// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android;

import android.app.Application;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.j.d;
import com.amazon.android.o.a;
import com.amazon.android.o.c;
import com.amazon.android.o.f;

// Referenced classes of package com.amazon.android:
//			Kiwi

final class e
	implements c
{

	private Kiwi a;

	e(Kiwi kiwi)
	{
		a = kiwi;
	}

	public final f a()
	{
		return d.b;
	}

	public final volatile void a(com.amazon.android.o.d d1)
	{
		if (KiwiLogger.TRACE_ON)
		{
			com.amazon.android.Kiwi.a().trace("------------ Kiwi Killing Self ------------");
			com.amazon.android.Kiwi.a().trace((new StringBuilder()).append("Package: ").append(Kiwi.e(a).getPackageName()).toString());
			com.amazon.android.Kiwi.a().trace("-------------------------------------------");
		}
		Kiwi.b();
	}

	public final a b()
	{
		return a.c;
	}
}
