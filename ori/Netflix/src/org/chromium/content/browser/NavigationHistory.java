// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import java.util.ArrayList;

// Referenced classes of package org.chromium.content.browser:
//			NavigationEntry

public class NavigationHistory
{

	private ArrayList entries;
	private int mCurrentEntryIndex;

	public NavigationHistory()
	{
		entries = new ArrayList();
	}

	protected void addEntry(NavigationEntry navigationentry)
	{
		entries.add(navigationentry);
	}

	public int getCurrentEntryIndex()
	{
		return mCurrentEntryIndex;
	}

	public NavigationEntry getEntryAtIndex(int i)
	{
		return (NavigationEntry)entries.get(i);
	}

	public int getEntryCount()
	{
		return entries.size();
	}

	void setCurrentEntryIndex(int i)
	{
		mCurrentEntryIndex = i;
	}
}
