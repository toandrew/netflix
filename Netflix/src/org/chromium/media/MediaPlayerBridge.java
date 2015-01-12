// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import java.lang.reflect.*;
import java.util.HashMap;

public class MediaPlayerBridge
{
	private static class AllowedOperations
	{

		private final boolean mCanPause;
		private final boolean mCanSeekBackward;
		private final boolean mCanSeekForward;

		private boolean canPause()
		{
			return mCanPause;
		}

		private boolean canSeekBackward()
		{
			return mCanSeekBackward;
		}

		private boolean canSeekForward()
		{
			return mCanSeekForward;
		}

		private AllowedOperations(boolean flag, boolean flag1, boolean flag2)
		{
			mCanPause = flag;
			mCanSeekForward = flag1;
			mCanSeekBackward = flag2;
		}

	}


	private static final String TAG = "MediaPlayerBridge";
	private MediaPlayer mPlayer;

	public MediaPlayerBridge()
	{
	}

	private static MediaPlayerBridge create()
	{
		return new MediaPlayerBridge();
	}

	private static AllowedOperations getAllowedOperations(MediaPlayer mediaplayer)
	{
	    return null;
//		boolean flag;
//		boolean flag1;
//		boolean flag2;
//		flag = true;
//		flag1 = true;
//		flag2 = true;
//		Object obj;
//		Class class1 = mediaplayer.getClass();
//		Class aclass[] = new Class[2];
//		aclass[0] = Boolean.TYPE;
//		aclass[1] = Boolean.TYPE;
//		Method method = class1.getDeclaredMethod("getMetadata", aclass);
//		method.setAccessible(true);
//		Object aobj[] = new Object[2];
//		aobj[0] = Boolean.valueOf(false);
//		aobj[1] = Boolean.valueOf(false);
//		obj = method.invoke(mediaplayer, aobj);
//		if (obj == null) goto _L2; else goto _L1
//_L1:
//		Method method1;
//		Method method2;
//		int i;
//		int j;
//		int k;
//		Object aobj1[];
//		Class class2 = obj.getClass();
//		Class aclass1[] = new Class[1];
//		aclass1[0] = Integer.TYPE;
//		method1 = class2.getDeclaredMethod("has", aclass1);
//		Class aclass2[] = new Class[1];
//		aclass2[0] = Integer.TYPE;
//		method2 = class2.getDeclaredMethod("getBoolean", aclass2);
//		i = ((Integer)class2.getField("PAUSE_AVAILABLE").get(null)).intValue();
//		j = ((Integer)class2.getField("SEEK_FORWARD_AVAILABLE").get(null)).intValue();
//		k = ((Integer)class2.getField("SEEK_BACKWARD_AVAILABLE").get(null)).intValue();
//		method1.setAccessible(true);
//		method2.setAccessible(true);
//		aobj1 = new Object[1];
//		aobj1[0] = Integer.valueOf(i);
//		if (!((Boolean)method1.invoke(obj, aobj1)).booleanValue()) goto _L4; else goto _L3
//_L3:
//		Object aobj6[];
//		aobj6 = new Object[1];
//		aobj6[0] = Integer.valueOf(i);
//		if (!((Boolean)method2.invoke(obj, aobj6)).booleanValue()) goto _L5; else goto _L4
//_L9:
//		aobj3 = new Object[1];
//		aobj3[0] = Integer.valueOf(k);
//		if (!((Boolean)method1.invoke(obj, aobj3)).booleanValue()) goto _L7; else goto _L6
//_L6:
//		Object aobj4[] = new Object[1];
//		aobj4[0] = Integer.valueOf(k);
//		flag3 = ((Boolean)method2.invoke(obj, aobj4)).booleanValue();
//		if (!flag3) goto _L8; else goto _L7
//_L7:
//		flag2 = true;
//_L2:
//		return new AllowedOperations(flag, flag1, flag2);
//_L5:
//		flag = false;
//_L10:
//		Object aobj2[] = new Object[1];
//		aobj2[0] = Integer.valueOf(j);
//		if (!((Boolean)method1.invoke(obj, aobj2)).booleanValue())
//			break MISSING_BLOCK_LABEL_576;
//		Object aobj5[] = new Object[1];
//		aobj5[0] = Integer.valueOf(j);
//		Object aobj3[];
//		boolean flag3;
//		if (((Boolean)method2.invoke(obj, aobj5)).booleanValue())
//			break MISSING_BLOCK_LABEL_576;
//		flag1 = false;
//		  goto _L9
//_L8:
//		flag2 = false;
//		  goto _L2
//		NoSuchMethodException nosuchmethodexception;
//		nosuchmethodexception;
//		Log.e("MediaPlayerBridge", (new StringBuilder()).append("Cannot find getMetadata() method: ").append(nosuchmethodexception).toString());
//		  goto _L2
//		InvocationTargetException invocationtargetexception;
//		invocationtargetexception;
//		Log.e("MediaPlayerBridge", (new StringBuilder()).append("Cannot invoke MediaPlayer.getMetadata() method: ").append(invocationtargetexception).toString());
//		  goto _L2
//		IllegalAccessException illegalaccessexception;
//		illegalaccessexception;
//		Log.e("MediaPlayerBridge", (new StringBuilder()).append("Cannot access metadata: ").append(illegalaccessexception).toString());
//		  goto _L2
//		NoSuchFieldException nosuchfieldexception;
//		nosuchfieldexception;
//		Log.e("MediaPlayerBridge", (new StringBuilder()).append("Cannot find matching fields in Metadata class: ").append(nosuchfieldexception).toString());
//		  goto _L2
//_L4:
//		flag = true;
//		  goto _L10
//		flag1 = true;
//		  goto _L9
	}

	protected int getCurrentPosition()
	{
		return getLocalPlayer().getCurrentPosition();
	}

	protected int getDuration()
	{
		return getLocalPlayer().getDuration();
	}

	protected MediaPlayer getLocalPlayer()
	{
		if (mPlayer == null)
			mPlayer = new MediaPlayer();
		return mPlayer;
	}

	protected int getVideoHeight()
	{
		return getLocalPlayer().getVideoHeight();
	}

	protected int getVideoWidth()
	{
		return getLocalPlayer().getVideoWidth();
	}

	protected boolean isPlaying()
	{
		return getLocalPlayer().isPlaying();
	}

	protected void pause()
	{
		getLocalPlayer().pause();
	}

	protected void prepareAsync()
		throws IllegalStateException
	{
		getLocalPlayer().prepareAsync();
	}

	protected void release()
	{
		getLocalPlayer().release();
	}

	protected void seekTo(int i)
		throws IllegalStateException
	{
		getLocalPlayer().seekTo(i);
	}

	protected boolean setDataSource(Context context, String s, String s1, boolean flag)
	{
		Uri uri = Uri.parse(s);
		HashMap hashmap = new HashMap();
		if (flag)
			hashmap.put("x-hide-urls-from-log", "true");
		if (!TextUtils.isEmpty(s1))
			hashmap.put("Cookie", s1);
		try
		{
			getLocalPlayer().setDataSource(context, uri, hashmap);
		}
		catch (Exception exception)
		{
			return false;
		}
		return true;
	}

	protected void setOnBufferingUpdateListener(android.media.MediaPlayer.OnBufferingUpdateListener onbufferingupdatelistener)
	{
		getLocalPlayer().setOnBufferingUpdateListener(onbufferingupdatelistener);
	}

	protected void setOnCompletionListener(android.media.MediaPlayer.OnCompletionListener oncompletionlistener)
	{
		getLocalPlayer().setOnCompletionListener(oncompletionlistener);
	}

	protected void setOnErrorListener(android.media.MediaPlayer.OnErrorListener onerrorlistener)
	{
		getLocalPlayer().setOnErrorListener(onerrorlistener);
	}

	protected void setOnPreparedListener(android.media.MediaPlayer.OnPreparedListener onpreparedlistener)
	{
		getLocalPlayer().setOnPreparedListener(onpreparedlistener);
	}

	protected void setOnSeekCompleteListener(android.media.MediaPlayer.OnSeekCompleteListener onseekcompletelistener)
	{
		getLocalPlayer().setOnSeekCompleteListener(onseekcompletelistener);
	}

	protected void setOnVideoSizeChangedListener(android.media.MediaPlayer.OnVideoSizeChangedListener onvideosizechangedlistener)
	{
		getLocalPlayer().setOnVideoSizeChangedListener(onvideosizechangedlistener);
	}

	protected void setSurface(Surface surface)
	{
		getLocalPlayer().setSurface(surface);
	}

	protected void setVolume(double d)
	{
		getLocalPlayer().setVolume((float)d, (float)d);
	}

	protected void start()
	{
		getLocalPlayer().start();
	}
}
