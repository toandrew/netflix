// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;

// Referenced classes of package org.chromium.content.browser:
//			ContentViewCore, ContentViewDownloadDelegate

public class DownloadController
{
	public static interface DownloadNotificationService
	{

		public abstract void onDownloadCompleted(Context context, String s, String s1, String s2, String s3, long l, 
				boolean flag);
	}


	private static final String LOGTAG = "DownloadController";
	private static DownloadNotificationService sDownloadNotificationService;
	private static DownloadController sInstance;

	private DownloadController()
	{
		nativeInit();
	}

	private static ContentViewDownloadDelegate downloadDelegateFromView(ContentViewCore contentviewcore)
	{
		return contentviewcore.getDownloadDelegate();
	}

	public static DownloadController getInstance()
	{
		if (sInstance == null)
			sInstance = new DownloadController();
		return sInstance;
	}

	private native void nativeInit();

	public static void setDownloadNotificationService(DownloadNotificationService downloadnotificationservice)
	{
		sDownloadNotificationService = downloadnotificationservice;
	}

	public void newHttpGetDownload(ContentViewCore contentviewcore, String s, String s1, String s2, String s3, String s4, String s5, 
			long l)
	{
		ContentViewDownloadDelegate contentviewdownloaddelegate = downloadDelegateFromView(contentviewcore);
		if (contentviewdownloaddelegate != null)
			contentviewdownloaddelegate.requestHttpGetDownload(s, s1, s2, s3, s4, s5, l);
	}

	public void onDangerousDownload(ContentViewCore contentviewcore, String s, int i)
	{
		ContentViewDownloadDelegate contentviewdownloaddelegate = downloadDelegateFromView(contentviewcore);
		if (contentviewdownloaddelegate != null)
			contentviewdownloaddelegate.onDangerousDownload(s, i);
	}

	public void onDownloadCompleted(Context context, String s, String s1, String s2, String s3, long l, 
			boolean flag)
	{
		if (sDownloadNotificationService != null)
			sDownloadNotificationService.onDownloadCompleted(context, s, s1, s3, s2, l, flag);
	}

	public void onDownloadStarted(ContentViewCore contentviewcore, String s, String s1)
	{
		ContentViewDownloadDelegate contentviewdownloaddelegate = downloadDelegateFromView(contentviewcore);
		if (contentviewdownloaddelegate != null)
			contentviewdownloaddelegate.onDownloadStarted(s, s1);
	}
}
