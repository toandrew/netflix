// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.context;

import android.content.*;

// Referenced classes of package com.amazon.android.framework.context:
//			d

final class ContextReceiver_b extends BroadcastReceiver
{

	private ContextManagerImpl_d a;

	ContextReceiver_b(ContextManagerImpl_d d1)
	{
		a = d1;
	}

	public final void onReceive(Context context, Intent intent)
	{
	    ContextManagerImpl_d.a(a, intent);
	}
}
