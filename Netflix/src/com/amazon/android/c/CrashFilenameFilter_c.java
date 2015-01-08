// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.c;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

// Referenced classes of package com.amazon.android.c:
//			a

final class CrashFilenameFilter_c
	implements FilenameFilter
{

	private CrashManagerImpl_a a;

	CrashFilenameFilter_c(CrashManagerImpl_a a1)
	{
		a = a1;
	}

	public final boolean accept(File file, String s)
	{
		return s.endsWith(".amzst") && !com.amazon.android.c.CrashManagerImpl_a.a(a).containsValue(s);
	}
}
