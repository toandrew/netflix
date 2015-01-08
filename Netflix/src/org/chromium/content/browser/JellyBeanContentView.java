// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeProvider;
import org.chromium.ui.WindowAndroid;

// Referenced classes of package org.chromium.content.browser:
//			ContentView, ContentViewCore

class JellyBeanContentView extends ContentView
{

	JellyBeanContentView(Context context, int i, WindowAndroid windowandroid, AttributeSet attributeset, int j)
	{
		super(context, i, windowandroid, attributeset, j);
	}

	public AccessibilityNodeProvider getAccessibilityNodeProvider()
	{
		AccessibilityNodeProvider accessibilitynodeprovider = getContentViewCore().getAccessibilityNodeProvider();
		if (accessibilitynodeprovider != null)
			return accessibilitynodeprovider;
		else
			return super.getAccessibilityNodeProvider();
	}

	public boolean performAccessibilityAction(int i, Bundle bundle)
	{
		if (getContentViewCore().supportsAccessibilityAction(i))
			return getContentViewCore().performAccessibilityAction(i, bundle);
		else
			return super.performAccessibilityAction(i, bundle);
	}
}
