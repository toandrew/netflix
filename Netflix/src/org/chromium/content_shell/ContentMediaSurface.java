// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content_shell;

import android.content.Context;
import android.graphics.Rect;
import android.view.*;
import android.widget.FrameLayout;

// Referenced classes of package org.chromium.content_shell:
//			ContentMediaSurfaceClient

public class ContentMediaSurface extends FrameLayout
	implements android.view.SurfaceHolder.Callback
{
	private class MediaSurface extends SurfaceView
	{
		public MediaSurface(Context context)
		{
			super(context);
		}
	}


	private boolean bNeedsLayout;
	private ContentMediaSurfaceClient mClient;
	private Context mContext;
	private SurfaceHolder mHolder;
	private MediaSurface mMediaSurface;
	private int mPlayerId;
	private Rect mRect;

	public ContentMediaSurface(Context context, ContentMediaSurfaceClient contentmediasurfaceclient, int i)
	{
		super(context);
		bNeedsLayout = false;
		mPlayerId = i;
		mContext = context;
		mClient = contentmediasurfaceclient;
		mMediaSurface = new MediaSurface(mContext);
		setBackgroundColor(0xff000000);
		android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(-2, -2, 17);
		addView(mMediaSurface, layoutparams);
		mMediaSurface.getHolder().addCallback(this);
		mMediaSurface.setZOrderOnTop(false);
		setVisibility(0);
	}

	private void layout()
	{
		mMediaSurface.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
		setNeedLayout(false);
	}

	private boolean needsLayout()
	{
		return bNeedsLayout;
	}

	private void setNeedLayout(boolean flag)
	{
		bNeedsLayout = flag;
	}

	public void destroy()
	{
		removeView(mMediaSurface);
		mMediaSurface = null;
		setVisibility(8);
	}

	public Surface getSurface()
	{
		if (mHolder != null)
			return mHolder.getSurface();
		else
			return null;
	}

	public SurfaceHolder getSurfaceHolder()
	{
		return mHolder;
	}

	public SurfaceView getSurfaceView()
	{
		return mMediaSurface;
	}

	public int playerId()
	{
		return mPlayerId;
	}

	public void resizeSurface(Rect rect)
	{
		mRect = rect;
		if (mHolder != null)
		{
			layout();
			return;
		} else
		{
			setNeedLayout(true);
			return;
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k)
	{
	}

	public void surfaceCreated(SurfaceHolder surfaceholder)
	{
		mHolder = surfaceholder;
		if (needsLayout())
			layout();
		if (mClient != null)
			mClient.onMediaSurfaceCreated();
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder)
	{
		mHolder = null;
		if (mClient != null)
			mClient.onMediaSurfaceLost();
	}

	public void transferOwnership(int i)
	{
		mPlayerId = i;
	}
}
