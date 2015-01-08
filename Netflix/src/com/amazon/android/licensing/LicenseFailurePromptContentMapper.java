// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.licensing;

import com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b.a;
import com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b.b;
import com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b.d;
import com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b.e;
import com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b.f;
import com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b.g;
import com.amazon.android.DrmFullApplicationLaunchTaskWorkflow_b.h;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.prompt.PromptContent;
import com.amazon.android.DrmFreeApplicationLaunchTaskWorkflow_h.c;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.amazon.android.licensing:
//			i, m, j, g, 
//			h

public class LicenseFailurePromptContentMapper
{

	private final Map mappings = new HashMap();

	public LicenseFailurePromptContentMapper()
	{
		register(com/amazon/android/ResultManager_b/EventManager_g, com.amazon.android.licensing.i.a);
		register(com/amazon/android/ResultManager_b/f, com.amazon.android.licensing.i.a);
		register(com/amazon/android/ResultManager_b/DataStore_a, com.amazon.android.licensing.i.b);
		register(com/amazon/android/ResultManager_b/d, com.amazon.android.licensing.i.c);
		register(com/amazon/android/ResultManager_b/CommandResultVerifier_e, new m());
		register(com/amazon/android/ResultManager_b/DrmFreeApplicationLaunchTaskWorkflow_h, com.amazon.android.licensing.i.e);
		register(com/amazon/android/ResultManager_b/ResultManager_b, com.amazon.android.licensing.i.e);
		register(com/amazon/android/s/DataStore_a, new j());
		register(com/amazon/android/DrmFreeApplicationLaunchTaskWorkflow_h/DataStore_a, com.amazon.android.licensing.i.e);
		register(com/amazon/android/DrmFreeApplicationLaunchTaskWorkflow_h/ResultManager_b, com.amazon.android.licensing.i.e);
		register(com/amazon/android/DrmFreeApplicationLaunchTaskWorkflow_h/ResourceManagerImpl_c, com.amazon.android.licensing.i.e);
	}

	private void register(Class class1, PromptContent promptcontent)
	{
		register(class1, ((com.amazon.android.licensing.h) (new com.amazon.android.licensing.g(this, promptcontent))));
	}

	private void register(Class class1, com.amazon.android.licensing.h h1)
	{
		com.amazon.android.d.a.b(mappings.containsKey(class1), (new StringBuilder()).append("mapping exists for type: ").append(class1).toString());
		mappings.put(class1, h1);
	}

	public PromptContent map(KiwiException kiwiexception)
	{
		com.amazon.android.licensing.h h1 = (com.amazon.android.licensing.h)mappings.get(kiwiexception.getClass());
		if (h1 == null)
			return null;
		else
			return h1.a(kiwiexception);
	}
}
