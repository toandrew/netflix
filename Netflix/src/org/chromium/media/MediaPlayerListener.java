// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

// Referenced classes of package org.chromium.media:
//			MediaPlayerBridge

class MediaPlayerListener
	implements android.media.MediaPlayer.OnPreparedListener, android.media.MediaPlayer.OnCompletionListener, android.media.MediaPlayer.OnBufferingUpdateListener, android.media.MediaPlayer.OnSeekCompleteListener, android.media.MediaPlayer.OnVideoSizeChangedListener, android.media.MediaPlayer.OnErrorListener, android.media.AudioManager.OnAudioFocusChangeListener
{

	private static final int MEDIA_ERROR_DECODE = 1;
	private static final int MEDIA_ERROR_FORMAT = 0;
	private static final int MEDIA_ERROR_INVALID_CODE = 3;
	public static final int MEDIA_ERROR_MALFORMED = -1007;
	private static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 2;
	public static final int MEDIA_ERROR_TIMED_OUT = -110;
	private final Context mContext;
	private int mNativeMediaPlayerListener;

	private MediaPlayerListener(int i, Context context)
	{
		mNativeMediaPlayerListener = 0;
		mNativeMediaPlayerListener = i;
		mContext = context;
	}

	private static MediaPlayerListener create(int i, Context context, MediaPlayerBridge mediaplayerbridge)
	{
		MediaPlayerListener mediaplayerlistener = new MediaPlayerListener(i, context);
		mediaplayerbridge.setOnBufferingUpdateListener(mediaplayerlistener);
		mediaplayerbridge.setOnCompletionListener(mediaplayerlistener);
		mediaplayerbridge.setOnErrorListener(mediaplayerlistener);
		mediaplayerbridge.setOnPreparedListener(mediaplayerlistener);
		mediaplayerbridge.setOnSeekCompleteListener(mediaplayerlistener);
		mediaplayerbridge.setOnVideoSizeChangedListener(mediaplayerlistener);
		((AudioManager)context.getSystemService("audio")).requestAudioFocus(mediaplayerlistener, 3, 3);
		return mediaplayerlistener;
	}

	private native void nativeOnBufferingUpdate(int i, int j);

	private native void nativeOnMediaError(int i, int j);

	private native void nativeOnMediaInterrupted(int i);

	private native void nativeOnMediaPrepared(int i);

	private native void nativeOnPlaybackComplete(int i);

	private native void nativeOnSeekComplete(int i);

	private native void nativeOnVideoSizeChanged(int i, int j, int k);

	public void onAudioFocusChange(int i)
	{
		if (i == -1 || i == -2)
			nativeOnMediaInterrupted(mNativeMediaPlayerListener);
	}

	public void onBufferingUpdate(MediaPlayer mediaplayer, int i)
	{
		nativeOnBufferingUpdate(mNativeMediaPlayerListener, i);
	}

	public void onCompletion(MediaPlayer mediaplayer)
	{
		nativeOnPlaybackComplete(mNativeMediaPlayerListener);
	}

	public boolean onError(MediaPlayer mediaplayer, int i, int j)
	{
	    return true;
//		i;
//		JVM INSTR lookupswitch 3: default 36
//	//	               1: 51
//	//	               100: 98
//	//	               200: 104;
//		   goto _L1 _L2 _L3 _L4
//_L1:
//		byte byte0 = 3;
//_L6:
//		nativeOnMediaError(mNativeMediaPlayerListener, byte0);
//		return true;
//_L2:
//		switch (j)
//		{
//		default:
//			byte0 = 0;
//			break;
//
//		case -1007: 
//			byte0 = 1;
//			break;
//
//		case -110: 
//			byte0 = 3;
//			break;
//		}
//		if (false)
//			;
//		continue; /* Loop/switch isn't completed */
//_L3:
//		byte0 = 1;
//		continue; /* Loop/switch isn't completed */
//_L4:
//		byte0 = 2;
//		if (true) goto _L6; else goto _L5
//_L5:
	}

	public void onPrepared(MediaPlayer mediaplayer)
	{
		nativeOnMediaPrepared(mNativeMediaPlayerListener);
	}

	public void onSeekComplete(MediaPlayer mediaplayer)
	{
		nativeOnSeekComplete(mNativeMediaPlayerListener);
	}

	public void onVideoSizeChanged(MediaPlayer mediaplayer, int i, int j)
	{
		nativeOnVideoSizeChanged(mNativeMediaPlayerListener, i, j);
	}

	public void releaseResources()
	{
		if (mContext != null)
		{
			AudioManager audiomanager = (AudioManager)mContext.getSystemService("audio");
			if (audiomanager != null)
				audiomanager.abandonAudioFocus(this);
		}
	}
}
