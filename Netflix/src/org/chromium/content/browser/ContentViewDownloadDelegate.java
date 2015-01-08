// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;


public interface ContentViewDownloadDelegate
{

	public abstract void onDangerousDownload(String s, int i);

	public abstract void onDownloadStarted(String s, String s1);

	public abstract void requestHttpGetDownload(String s, String s1, String s2, String s3, String s4, String s5, long l);
}
