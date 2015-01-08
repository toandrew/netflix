// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.n;

import com.amazon.android.framework.util.KiwiLogger;
import java.util.Date;

// Referenced classes of package com.amazon.android.n:
//			b, d

final class c extends b
{

	private d b;

	c(d d1, Object obj, Date date)
	{
		b = d1;
		super(obj, date);
	}

	protected final void doExpiration()
	{
		if (KiwiLogger.ERROR_ON)
			d.a().error("Woah, non-expirable value was expired!!!!");
	}
}
