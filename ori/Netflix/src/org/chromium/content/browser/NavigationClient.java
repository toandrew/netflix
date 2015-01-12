// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;


// Referenced classes of package org.chromium.content.browser:
//			NavigationHistory

public interface NavigationClient
{

	public abstract NavigationHistory getDirectedNavigationHistory(boolean flag, int i);

	public abstract void goToNavigationIndex(int i);
}
