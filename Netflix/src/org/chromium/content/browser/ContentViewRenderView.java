// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.view.*;
import android.widget.FrameLayout;

// Referenced classes of package org.chromium.content.browser:
//			ContentView, ContentViewCore, VSyncMonitor

public class ContentViewRenderView extends FrameLayout
{
	private static class VSyncAdapter
		implements VSyncManager.Provider, VSyncMonitor.Listener
	{

		private static final long INPUT_EVENT_LAG_FROM_VSYNC_MICROSECONDS = 3200L;
		private VSyncManager.Listener mVSyncListener;
		private final VSyncMonitor mVSyncMonitor;
		private boolean mVSyncNotificationEnabled;

		public void onVSync(VSyncMonitor vsyncmonitor, long l)
		{
			if (mVSyncListener == null)
				return;
			if (mVSyncNotificationEnabled)
			{
				mVSyncListener.onVSync(l);
				mVSyncMonitor.requestUpdate();
				return;
			}
			if (android.os.Build.VERSION.SDK_INT >= 16)
				l += 3200L;
			mVSyncListener.updateVSync(l, mVSyncMonitor.getVSyncPeriodInMicroseconds());
		}

		public void registerVSyncListener(VSyncManager.Listener listener)
		{
			if (!mVSyncNotificationEnabled)
				mVSyncMonitor.requestUpdate();
			mVSyncNotificationEnabled = true;
		}

		void setVSyncListener(VSyncManager.Listener listener)
		{
			mVSyncListener = listener;
			if (mVSyncListener != null)
				mVSyncMonitor.requestUpdate();
		}

		public void unregisterVSyncListener(VSyncManager.Listener listener)
		{
			mVSyncNotificationEnabled = false;
		}

		VSyncAdapter(Context context)
		{
			mVSyncMonitor = new VSyncMonitor(context, this);
		}
	}


	static final boolean $assertionsDisabled;
	private ContentView mCurrentContentView;
	private int mNativeContentViewRenderView;
	private final android.view.SurfaceHolder.Callback mSurfaceCallback;
	private SurfaceView mSurfaceView;
	private VSyncAdapter mVSyncAdapter;

	public ContentViewRenderView(Context context)
	{
		super(context);
		mNativeContentViewRenderView = 0;
		mNativeContentViewRenderView = nativeInit();
		if (!$assertionsDisabled && mNativeContentViewRenderView == 0)
		{
			throw new AssertionError();
		} else
		{
			mSurfaceView = createSurfaceView(getContext());
			mSurfaceCallback = new android.view.SurfaceHolder.Callback() {

				static final boolean $assertionsDisabled;
				final ContentViewRenderView this$0;

				public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k)
				{
					if (!$assertionsDisabled && mNativeContentViewRenderView == 0)
						throw new AssertionError();
					nativeSurfaceSetSize(mNativeContentViewRenderView, j, k);
					if (mCurrentContentView != null)
						mCurrentContentView.getContentViewCore().onPhysicalBackingSizeChanged(j, k);
				}

				public void surfaceCreated(SurfaceHolder surfaceholder)
				{
					if (!$assertionsDisabled && mNativeContentViewRenderView == 0)
					{
						throw new AssertionError();
					} else
					{
						nativeSurfaceCreated(mNativeContentViewRenderView, surfaceholder.getSurface());
						onReadyToRender();
						return;
					}
				}

				public void surfaceDestroyed(SurfaceHolder surfaceholder)
				{
					if (!$assertionsDisabled && mNativeContentViewRenderView == 0)
					{
						throw new AssertionError();
					} else
					{
						nativeSurfaceDestroyed(mNativeContentViewRenderView);
						return;
					}
				}

				static 
				{
					boolean flag;
					if (!org/chromium/content/browser/ContentViewRenderView.desiredAssertionStatus())
						flag = true;
					else
						flag = false;
					$assertionsDisabled = flag;
				}

			
			{
				this$0 = ContentViewRenderView.this;
				super();
			}
			};
			mSurfaceView.getHolder().addCallback(mSurfaceCallback);
			mSurfaceView.getHolder().setFormat(-3);
			mSurfaceView.setZOrderOnTop(true);
			mVSyncAdapter = new VSyncAdapter(getContext());
			addView(mSurfaceView, new android.widget.FrameLayout.LayoutParams(-1, -1));
			return;
		}
	}

	private native void nativeDestroy(int i);

	private static native int nativeInit();

	private native void nativeSetCurrentContentView(int i, int j);

	private native void nativeSurfaceCreated(int i, Surface surface);

	private native void nativeSurfaceDestroyed(int i);

	private native void nativeSurfaceSetSize(int i, int j, int k);

	protected SurfaceView createSurfaceView(Context context)
	{
		return new SurfaceView(context);
	}

	public void destroy()
	{
		mSurfaceView.getHolder().removeCallback(mSurfaceCallback);
		nativeDestroy(mNativeContentViewRenderView);
		mNativeContentViewRenderView = 0;
	}

	public boolean isInitialized()
	{
		return mSurfaceView.getHolder().getSurface() != null;
	}

	protected void onReadyToRender()
	{
	}

	public void setCurrentContentView(ContentView contentview)
	{
		if (!$assertionsDisabled && mNativeContentViewRenderView == 0)
		{
			throw new AssertionError();
		} else
		{
			ContentViewCore contentviewcore = contentview.getContentViewCore();
			nativeSetCurrentContentView(mNativeContentViewRenderView, contentviewcore.getNativeContentViewCore());
			mCurrentContentView = contentview;
			contentviewcore.onPhysicalBackingSizeChanged(getWidth(), getHeight());
			mVSyncAdapter.setVSyncListener(contentviewcore.getVSyncListener(mVSyncAdapter));
			return;
		}
	}

	static 
	{
		boolean flag;
		if (!org/chromium/content/browser/ContentViewRenderView.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}





}
