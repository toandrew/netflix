// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.*;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.util.HashMap;
import org.chromium.base.PathUtils;

class MediaResourceGetter
{
	private static class MediaMetadata
	{

		private final int mDurationInMilliseconds;
		private final int mHeight;
		private final boolean mSuccess;
		private final int mWidth;

		private int getDurationInMilliseconds()
		{
			return mDurationInMilliseconds;
		}

		private int getHeight()
		{
			return mHeight;
		}

		private int getWidth()
		{
			return mWidth;
		}

		private boolean isSuccess()
		{
			return mSuccess;
		}

		private MediaMetadata(int i, int j, int k, boolean flag)
		{
			mDurationInMilliseconds = i;
			mWidth = j;
			mHeight = k;
			mSuccess = flag;
		}

	}


	private static final String TAG = "MediaResourceGetter";

	MediaResourceGetter()
	{
	}

	private static MediaMetadata extractMediaMetadata(Context context, String s, String s1)
	{
	    return null;
//		int i;
//		int j;
//		MediaMetadataRetriever mediametadataretriever;
//		i = 0;
//		j = 0;
//		ConnectivityManager connectivitymanager = (ConnectivityManager)context.getSystemService("connectivity");
//		if (connectivitymanager != null)
//		{
//			NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
//			if (networkinfo == null)
//				return new MediaMetadata(0, 0, 0, false);
//			switch (networkinfo.getType())
//			{
//			default:
//				return new MediaMetadata(0, 0, 0, false);
//
//			case 1: // '\001'
//			case 9: // '\t'
//				break;
//			}
//		}
//		mediametadataretriever = new MediaMetadataRetriever();
//		Uri uri;
//		String s2;
//		uri = Uri.parse(s);
//		s2 = uri.getScheme();
//		if (s2 == null) goto _L2; else goto _L1
//_L1:
//		if (!s2.equals("file")) goto _L3; else goto _L2
//_L2:
//		File file;
//		String s3;
//		file = new File(uri.getPath());
//		s3 = file.getAbsolutePath();
//		if (!file.exists()) goto _L5; else goto _L4
//_L4:
//		if (s3.startsWith("/mnt/sdcard/")) goto _L7; else goto _L6
//_L6:
//		boolean flag1 = s3.startsWith("/sdcard/");
//		i = 0;
//		j = 0;
//		if (flag1) goto _L7; else goto _L8
//_L8:
//		if (!s3.startsWith(PathUtils.getExternalStorageDirectory())) goto _L5; else goto _L7
//_L7:
//		mediametadataretriever.setDataSource(s3);
//_L10:
//		int l;
//		i = Integer.parseInt(mediametadataretriever.extractMetadata(9));
//		j = Integer.parseInt(mediametadataretriever.extractMetadata(18));
//		l = Integer.parseInt(mediametadataretriever.extractMetadata(19));
//		int k;
//		boolean flag;
//		k = l;
//		flag = true;
//_L9:
//		return new MediaMetadata(i, j, k, flag);
//_L5:
//		MediaMetadata mediametadata;
//		Log.e("MediaResourceGetter", (new StringBuilder()).append("Unable to read file: ").append(s).toString());
//		mediametadata = new MediaMetadata(0, 0, 0, false);
//		return mediametadata;
//		IllegalArgumentException illegalargumentexception;
//		illegalargumentexception;
//		Log.e("MediaResourceGetter", (new StringBuilder()).append("Invalid url: ").append(illegalargumentexception).toString());
//		k = 0;
//		flag = false;
//		  goto _L9
//_L3:
//		HashMap hashmap = new HashMap();
//		if (!TextUtils.isEmpty(s1))
//			hashmap.put("Cookie", s1);
//		mediametadataretriever.setDataSource(s, hashmap);
//		  goto _L10
//		RuntimeException runtimeexception;
//		runtimeexception;
//		Log.e("MediaResourceGetter", (new StringBuilder()).append("Invalid url: ").append(runtimeexception).toString());
//		k = 0;
//		flag = false;
//		  goto _L9
	}
}
