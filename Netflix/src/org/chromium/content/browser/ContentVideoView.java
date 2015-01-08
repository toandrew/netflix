// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.*;
import android.widget.*;
import org.chromium.base.ThreadUtils;

// Referenced classes of package org.chromium.content.browser:
//			ContentVideoViewClient, ContentVideoViewControls

public class ContentVideoView extends FrameLayout
	implements ContentVideoViewControls.Delegate, android.view.SurfaceHolder.Callback, android.view.View.OnTouchListener, android.view.View.OnKeyListener
{
	private static class FullScreenControls
		implements ContentVideoViewControls
	{

		MediaController mMediaController;
		View mVideoView;

		public void hide()
		{
			if (mVideoView != null)
				mVideoView.setSystemUiVisibility(1);
			mMediaController.hide();
		}

		public boolean isShowing()
		{
			return mMediaController.isShowing();
		}

		public void setAnchorView(View view)
		{
			mMediaController.setAnchorView(view);
		}

		public void setDelegate(ContentVideoViewControls.Delegate delegate1)
		{
			mMediaController.setMediaPlayer(delegate1);
		}

		public void setEnabled(boolean flag)
		{
			mMediaController.setEnabled(flag);
		}

		public void show()
		{
			mMediaController.show();
			if (mVideoView != null)
				mVideoView.setSystemUiVisibility(0);
		}

		public void show(int i)
		{
			mMediaController.show(i);
		}

		public FullScreenControls(Context context, View view)
		{
			mMediaController = new MediaController(context);
			mVideoView = view;
		}
	}

	private static class ProgressView extends LinearLayout
	{

		private ProgressBar mProgressBar;
		private TextView mTextView;

		public ProgressView(Context context, String s)
		{
			super(context);
			setOrientation(1);
			setLayoutParams(new android.widget.LinearLayout.LayoutParams(-2, -2));
			mProgressBar = new ProgressBar(context, null, 0x101007a);
			mTextView = new TextView(context);
			mTextView.setText(s);
			addView(mProgressBar);
			addView(mTextView);
		}
	}

	private class VideoSurfaceView extends SurfaceView
	{

		final ContentVideoView this$0;

		protected void onMeasure(int i, int j)
		{
			int k;
			int l;
			if (mVideoWidth == 0 && mVideoHeight == 0)
			{
				setMeasuredDimension(1, 1);
				return;
			}
			k = getDefaultSize(mVideoWidth, i);
			l = getDefaultSize(mVideoHeight, j);
			if (mVideoWidth <= 0 || mVideoHeight <= 0) goto _L2; else goto _L1
_L1:
			if (l * mVideoWidth <= k * mVideoHeight) goto _L4; else goto _L3
_L3:
			l = (k * mVideoHeight) / mVideoWidth;
_L2:
			setMeasuredDimension(k, l);
			return;
_L4:
			if (l * mVideoWidth < k * mVideoHeight)
				k = (l * mVideoWidth) / mVideoHeight;
			if (true) goto _L2; else goto _L5
_L5:
		}

		public VideoSurfaceView(Context context)
		{
			this$0 = ContentVideoView.this;
			super(context);
		}
	}


	private static final int MEDIA_BUFFERING_UPDATE = 3;
	private static final int MEDIA_ERROR = 100;
	public static final int MEDIA_ERROR_INVALID_CODE = 3;
	public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 2;
	private static final int MEDIA_INFO = 200;
	private static final int MEDIA_NOP = 0;
	private static final int MEDIA_PLAYBACK_COMPLETE = 2;
	private static final int MEDIA_PREPARED = 1;
	private static final int MEDIA_SEEK_COMPLETE = 4;
	private static final int MEDIA_SET_VIDEO_SIZE = 5;
	private static final int STATE_ERROR = -1;
	private static final int STATE_IDLE = 0;
	private static final int STATE_PAUSED = 2;
	private static final int STATE_PLAYBACK_COMPLETED = 3;
	private static final int STATE_PLAYING = 1;
	private static final String TAG = "ContentVideoView";
	private boolean mCanPause;
	private boolean mCanSeekBack;
	private boolean mCanSeekForward;
	private ContentVideoViewClient mClient;
	private ContentVideoViewControls mControls;
	private int mCurrentBufferPercentage;
	private int mCurrentState;
	private int mDuration;
	private String mErrorButton;
	private String mErrorTitle;
	private Runnable mExitFullscreenRunnable;
	private int mNativeContentVideoView;
	private String mPlaybackErrorText;
	private View mProgressView;
	private Surface mSurface;
	private SurfaceHolder mSurfaceHolder;
	private String mUnknownErrorText;
	private int mVideoHeight;
	private String mVideoLoadingText;
	private VideoSurfaceView mVideoSurfaceView;
	private int mVideoWidth;

	private ContentVideoView(Context context, int i, ContentVideoViewClient contentvideoviewclient)
	{
		super(context);
		mCurrentState = 0;
		mExitFullscreenRunnable = new Runnable() {

			final ContentVideoView this$0;

			public void run()
			{
				exitFullscreen(true);
			}

			
			{
				this$0 = ContentVideoView.this;
				super();
			}
		};
		mNativeContentVideoView = i;
		mClient = contentvideoviewclient;
		initResources(context);
		mCurrentBufferPercentage = 0;
		mVideoSurfaceView = new VideoSurfaceView(context);
		setBackgroundColor(0xff000000);
		showContentVideoView();
		setVisibility(0);
		mClient.onShowCustomView(this);
	}

	private void attachControls()
	{
		if (mControls != null)
		{
			mControls.setDelegate(this);
			mControls.setAnchorView(mVideoSurfaceView);
			mControls.setEnabled(false);
		}
	}

	private static ContentVideoView createContentVideoView(Context context, int i, ContentVideoViewClient contentvideoviewclient)
	{
		ThreadUtils.assertOnUiThread();
		if (!(context instanceof Activity))
		{
			Log.w("ContentVideoView", "Wrong type of context, can't create fullscreen video");
			return null;
		} else
		{
			return new ContentVideoView(context, i, contentvideoviewclient);
		}
	}

	private void destroyContentVideoView(boolean flag)
	{
		if (mVideoSurfaceView != null)
		{
			mClient.onDestroyContentVideoView();
			removeControls();
			removeSurfaceView();
			setVisibility(8);
		}
		if (flag)
			mNativeContentVideoView = 0;
	}

	public static ContentVideoView getContentVideoView()
	{
		return nativeGetSingletonJavaContentVideoView();
	}

	private void initResources(Context context)
	{
		if (mPlaybackErrorText != null)
		{
			return;
		} else
		{
			mPlaybackErrorText = context.getString(org.chromium.content.R.string.media_player_error_text_invalid_progressive_playback);
			mUnknownErrorText = context.getString(org.chromium.content.R.string.media_player_error_text_unknown);
			mErrorButton = context.getString(org.chromium.content.R.string.media_player_error_button);
			mErrorTitle = context.getString(org.chromium.content.R.string.media_player_error_title);
			mVideoLoadingText = context.getString(org.chromium.content.R.string.media_player_loading_video);
			return;
		}
	}

	private boolean isInPlaybackState()
	{
		return mCurrentState != -1 && mCurrentState != 0;
	}

	private native void nativeExitFullscreen(int i, boolean flag);

	private native int nativeGetCurrentPosition(int i);

	private native int nativeGetDurationInMilliSeconds(int i);

	private static native ContentVideoView nativeGetSingletonJavaContentVideoView();

	private native int nativeGetVideoHeight(int i);

	private native int nativeGetVideoWidth(int i);

	private native boolean nativeIsPlaying(int i);

	private native void nativePause(int i);

	private native void nativePlay(int i);

	private native void nativeSeekTo(int i, int j);

	private native void nativeSetSurface(int i, Surface surface);

	private native void nativeUpdateMediaMetadata(int i);

	private void onBufferingUpdate(int i)
	{
		mCurrentBufferPercentage = i;
	}

	private void onCompletion()
	{
		mCurrentState = 3;
		if (mControls != null)
			mControls.hide();
	}

	private void onPlaybackComplete()
	{
		onCompletion();
	}

	private void onUpdateMediaMetadata(int i, int j, int k, boolean flag, boolean flag1, boolean flag2)
	{
		mProgressView.setVisibility(8);
		mDuration = k;
		mCanPause = flag;
		mCanSeekBack = flag1;
		mCanSeekForward = flag2;
		int l;
		if (isPlaying())
			l = 1;
		else
			l = 2;
		mCurrentState = l;
		if (mControls != null)
		{
			mControls.setEnabled(true);
			if (isPlaying())
				mControls.show();
			else
				mControls.show(0);
		}
		onVideoSizeChanged(i, j);
	}

	private void onVideoSizeChanged(int i, int j)
	{
		mVideoWidth = i;
		mVideoHeight = j;
		if (mVideoWidth != 0 && mVideoHeight != 0)
			mVideoSurfaceView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
	}

	private void openVideo()
	{
		if (mSurfaceHolder != null)
		{
			mCurrentState = 0;
			mCurrentBufferPercentage = 0;
			Object obj = mClient.createControls();
			if (obj == null)
				obj = new FullScreenControls(getContext(), this);
			setControls(((ContentVideoViewControls) (obj)));
			if (mNativeContentVideoView != 0)
			{
				nativeUpdateMediaMetadata(mNativeContentVideoView);
				nativeSetSurface(mNativeContentVideoView, mSurfaceHolder.getSurface());
			}
		}
	}

	private void removeControls()
	{
		if (mControls != null)
		{
			mControls.setEnabled(false);
			mControls.hide();
			mControls = null;
		}
	}

	private void setControls(ContentVideoViewControls contentvideoviewcontrols)
	{
		if (mControls != null)
			mControls.hide();
		mControls = contentvideoviewcontrols;
		attachControls();
	}

	private void showContentVideoView()
	{
		android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(-2, -2, 17);
		addView(mVideoSurfaceView, layoutparams);
		View view = mClient.getVideoLoadingProgressView();
		if (view != null)
			mProgressView = view;
		else
			mProgressView = new ProgressView(getContext(), mVideoLoadingText);
		addView(mProgressView, layoutparams);
		mVideoSurfaceView.setZOrderOnTop(true);
		mVideoSurfaceView.setOnKeyListener(this);
		mVideoSurfaceView.setOnTouchListener(this);
		mVideoSurfaceView.getHolder().addCallback(this);
		mVideoSurfaceView.setFocusable(true);
		mVideoSurfaceView.setFocusableInTouchMode(true);
		mVideoSurfaceView.requestFocus();
	}

	private void toggleMediaControlsVisiblity()
	{
		if (mControls.isShowing())
		{
			mControls.hide();
			return;
		} else
		{
			mControls.show();
			return;
		}
	}

	public boolean canPause()
	{
		return mCanPause;
	}

	public boolean canSeekBackward()
	{
		return mCanSeekBack;
	}

	public boolean canSeekForward()
	{
		return mCanSeekForward;
	}

	public void exitFullscreen(boolean flag)
	{
		destroyContentVideoView(false);
		if (mNativeContentVideoView != 0)
		{
			nativeExitFullscreen(mNativeContentVideoView, flag);
			mNativeContentVideoView = 0;
		}
	}

	public int getAudioSessionId()
	{
		return 0;
	}

	public int getBufferPercentage()
	{
		return mCurrentBufferPercentage;
	}

	public int getCurrentPosition()
	{
		if (isInPlaybackState() && mNativeContentVideoView != 0)
			return nativeGetCurrentPosition(mNativeContentVideoView);
		else
			return 0;
	}

	public int getDuration()
	{
		if (isInPlaybackState())
		{
			if (mDuration > 0)
				return mDuration;
			if (mNativeContentVideoView != 0)
				mDuration = nativeGetDurationInMilliSeconds(mNativeContentVideoView);
			else
				mDuration = 0;
			return mDuration;
		} else
		{
			mDuration = -1;
			return mDuration;
		}
	}

	public boolean isPlaying()
	{
		return mNativeContentVideoView != 0 && nativeIsPlaying(mNativeContentVideoView);
	}

	public boolean onKey(View view, int i, KeyEvent keyevent)
	{
		boolean flag;
		if (i != 4 && i != 24 && i != 25 && i != 164 && i != 5 && i != 82 && i != 84 && i != 6)
			flag = true;
		else
			flag = false;
		if (!isInPlaybackState() || !flag || mControls == null) goto _L2; else goto _L1
_L1:
		if (i != 79 && i != 85) goto _L4; else goto _L3
_L3:
		if (!isPlaying()) goto _L6; else goto _L5
_L5:
		pause();
		mControls.show();
_L8:
		return true;
_L6:
		start();
		mControls.hide();
		return true;
_L4:
		if (i != 126)
			break; /* Loop/switch isn't completed */
		if (!isPlaying())
		{
			start();
			mControls.hide();
			return true;
		}
		if (true) goto _L8; else goto _L7
_L7:
		if (i != 86 && i != 127)
			break; /* Loop/switch isn't completed */
		if (isPlaying())
		{
			pause();
			mControls.show();
			return true;
		}
		if (true) goto _L8; else goto _L9
_L9:
		toggleMediaControlsVisiblity();
_L11:
		return super.onKeyDown(i, keyevent);
_L2:
		if (i == 4 && keyevent.getAction() == 1)
		{
			exitFullscreen(false);
			return true;
		}
		if (i == 82)
			continue; /* Loop/switch isn't completed */
		if (i == 84)
			return true;
		if (true) goto _L11; else goto _L10
_L10:
		if (true) goto _L8; else goto _L12
_L12:
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		if (i == 4 && keyevent.getAction() == 1)
		{
			exitFullscreen(false);
			return true;
		} else
		{
			return super.onKeyDown(i, keyevent);
		}
	}

	public void onMediaPlayerError(int i)
	{
		Log.d("ContentVideoView", (new StringBuilder()).append("OnMediaPlayerError: ").append(i).toString());
		break MISSING_BLOCK_LABEL_26;
		if (mCurrentState != -1 && mCurrentState != 3 && i != 3)
		{
			mCurrentState = -1;
			if (mControls != null)
				mControls.hide();
			if (getWindowToken() != null)
			{
				String s;
				if (i == 2)
					s = mPlaybackErrorText;
				else
					s = mUnknownErrorText;
				(new android.app.AlertDialog.Builder(getContext())).setTitle(mErrorTitle).setMessage(s).setPositiveButton(mErrorButton, new android.content.DialogInterface.OnClickListener() {

					final ContentVideoView this$0;

					public void onClick(DialogInterface dialoginterface, int j)
					{
						onCompletion();
					}

			
			{
				this$0 = ContentVideoView.this;
				super();
			}
				}).setCancelable(false).show();
				return;
			}
		}
		return;
	}

	public boolean onTouch(View view, MotionEvent motionevent)
	{
		if (isInPlaybackState() && mControls != null && motionevent.getAction() == 0)
			toggleMediaControlsVisiblity();
		return true;
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		return true;
	}

	public boolean onTrackballEvent(MotionEvent motionevent)
	{
		if (isInPlaybackState() && mControls != null)
			toggleMediaControlsVisiblity();
		return false;
	}

	public void pause()
	{
		if (isInPlaybackState() && isPlaying())
		{
			if (mNativeContentVideoView != 0)
				nativePause(mNativeContentVideoView);
			mCurrentState = 2;
		}
	}

	public void removeSurfaceView()
	{
		removeView(mVideoSurfaceView);
		removeView(mProgressView);
		mVideoSurfaceView = null;
		mProgressView = null;
	}

	public void seekTo(int i)
	{
		if (mNativeContentVideoView != 0)
			nativeSeekTo(mNativeContentVideoView, i);
	}

	public void start()
	{
		if (isInPlaybackState())
		{
			if (mNativeContentVideoView != 0)
				nativePlay(mNativeContentVideoView);
			mCurrentState = 1;
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k)
	{
		mVideoSurfaceView.setFocusable(true);
		mVideoSurfaceView.setFocusableInTouchMode(true);
		if (isInPlaybackState() && mControls != null)
			mControls.show();
	}

	public void surfaceCreated(SurfaceHolder surfaceholder)
	{
		mSurfaceHolder = surfaceholder;
		openVideo();
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder)
	{
		if (mNativeContentVideoView != 0)
			nativeSetSurface(mNativeContentVideoView, null);
		mSurfaceHolder = null;
		post(mExitFullscreenRunnable);
	}



}
