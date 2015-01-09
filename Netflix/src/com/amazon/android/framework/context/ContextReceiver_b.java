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
