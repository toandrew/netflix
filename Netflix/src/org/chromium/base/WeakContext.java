// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

// Referenced classes of package org.chromium.base:
//			ThreadUtils

public class WeakContext
{

	private static WeakReference sWeakContext;

	public WeakContext()
	{
	}

	public static Context getContext()
	{
		return (Context)sWeakContext.get();
	}

	public static Object getSystemService(final String s)
	{
	    return null;
	    //TODO
	    /*
		Context context = (Context)sWeakContext.get();
		if (context == null)
			return null;
		if (ThreadUtils.runningOnUiThread())
			return context.getSystemService(s);
		else
			return ThreadUtils.runOnUiThreadBlockingNoException(new Callable(context, s) {
				public Object call()
				{
					return context.getSystemService(s);
				}
			});
			*/
	}

	public static void initializeWeakContext(Context context)
	{
		sWeakContext = new WeakReference(context);
	}
}
