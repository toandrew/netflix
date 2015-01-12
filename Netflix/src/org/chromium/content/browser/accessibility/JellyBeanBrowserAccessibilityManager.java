// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;
import org.chromium.content.browser.ContentViewCore;

// Referenced classes of package org.chromium.content.browser.accessibility:
//			BrowserAccessibilityManager

public class JellyBeanBrowserAccessibilityManager extends BrowserAccessibilityManager
{

	private AccessibilityNodeProvider mAccessibilityNodeProvider;

	JellyBeanBrowserAccessibilityManager(int i, ContentViewCore contentviewcore)
	{
		super(i, contentviewcore);
		mAccessibilityNodeProvider = new AccessibilityNodeProvider() {

//			final JellyBeanBrowserAccessibilityManager this$0;
//			final BrowserAccessibilityManager val$delegate;
//
//			public AccessibilityNodeInfo createAccessibilityNodeInfo(int j)
//			{
//				return delegate.createAccessibilityNodeInfo(j);
//			}
//
//			public List findAccessibilityNodeInfosByText(String s, int j)
//			{
//				return delegate.findAccessibilityNodeInfosByText(s, j);
//			}
//
//			public boolean performAction(int j, int k, Bundle bundle)
//			{
//				return delegate.performAction(j, k, bundle);
//			}
		};
	}

	public AccessibilityNodeProvider getAccessibilityNodeProvider()
	{
		return mAccessibilityNodeProvider;
	}
}
