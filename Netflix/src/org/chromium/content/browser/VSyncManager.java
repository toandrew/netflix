// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;


public abstract class VSyncManager
{
	public static interface Listener
	{

		public abstract void onVSync(long l);

		public abstract void updateVSync(long l, long l1);
	}

	public static interface Provider
	{

		public abstract void registerVSyncListener(Listener listener);

		public abstract void unregisterVSyncListener(Listener listener);
	}


	public VSyncManager()
	{
	}
}
