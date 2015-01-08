// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.*;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;
import org.chromium.content.common.TraceEvent;
import org.chromium.ui.WindowAndroid;

// Referenced classes of package org.chromium.content.browser:
//			PageInfo, ContentViewCore, JellyBeanContentView, ContentViewGestureHandler, 
//			ContentSettings, ContentViewClient, ContentViewDownloadDelegate, RenderCoordinates, 
//			LoadUrlParams

public class ContentView extends FrameLayout
	implements ContentViewCore.InternalAccessDelegate, PageInfo
{

	private final ContentViewCore mContentViewCore;
	private float mCurrentTouchOffsetX;
	private float mCurrentTouchOffsetY;

	protected ContentView(Context context, int i, WindowAndroid windowandroid, AttributeSet attributeset, int j)
	{
		FrameLayout(context, attributeset, j);
		if (getScrollBarStyle() == 0)
		{
			setHorizontalScrollBarEnabled(false);
			setVerticalScrollBarEnabled(false);
		}
		mContentViewCore = new ContentViewCore(context);
		ContentViewCore contentviewcore = mContentViewCore;
		int k = android.os.Build.VERSION.SDK_INT;
		int l = 0;
		if (k >= 16)
			l = 1;
		contentviewcore.initialize(this, this, i, windowandroid, l);
	}

	private MotionEvent createOffsetMotionEvent(MotionEvent motionevent)
	{
		MotionEvent motionevent1 = MotionEvent.obtain(motionevent);
		motionevent1.offsetLocation(mCurrentTouchOffsetX, mCurrentTouchOffsetY);
		return motionevent1;
	}

	public static boolean hasHardwareAcceleration(Activity activity)
	{
		return ContentViewCore.hasHardwareAcceleration(activity);
	}

	public static ContentView newInstance(Context context, int i, WindowAndroid windowandroid)
	{
		return newInstance(context, i, windowandroid, null, 0x1010085);
	}

	public static ContentView newInstance(Context context, int i, WindowAndroid windowandroid, AttributeSet attributeset)
	{
		return newInstance(context, i, windowandroid, attributeset, 0x1010085);
	}

	public static ContentView newInstance(Context context, int i, WindowAndroid windowandroid, AttributeSet attributeset, int j)
	{
		if (android.os.Build.VERSION.SDK_INT < 16)
			return new ContentView(context, i, windowandroid, attributeset, j);
		else
			return new JellyBeanContentView(context, i, windowandroid, attributeset, j);
	}

	public void attachExternalVideoSurface(int i, Surface surface)
	{
		mContentViewCore.attachExternalVideoSurface(i, surface);
	}

	public boolean awakenScrollBars()
	{
		return awakenScrollBars();
	}

	public boolean awakenScrollBars(int i, boolean flag)
	{
		return mContentViewCore.awakenScrollBars(i, flag);
	}

	public boolean canGoBack()
	{
		return mContentViewCore.canGoBack();
	}

	public boolean canGoForward()
	{
		return mContentViewCore.canGoForward();
	}

	public boolean canGoToOffset(int i)
	{
		return mContentViewCore.canGoToOffset(i);
	}

	public boolean canZoomIn()
	{
		return mContentViewCore.canZoomIn();
	}

	public boolean canZoomOut()
	{
		return mContentViewCore.canZoomOut();
	}

	void checkIsAlive()
		throws IllegalStateException
	{
		mContentViewCore.checkIsAlive();
	}

	public void clearHistory()
	{
		mContentViewCore.clearHistory();
	}

	protected int computeHorizontalScrollExtent()
	{
		return mContentViewCore.computeHorizontalScrollExtent();
	}

	protected int computeHorizontalScrollOffset()
	{
		return mContentViewCore.computeHorizontalScrollOffset();
	}

	protected int computeHorizontalScrollRange()
	{
		return mContentViewCore.computeHorizontalScrollRange();
	}

	protected int computeVerticalScrollExtent()
	{
		return mContentViewCore.computeVerticalScrollExtent();
	}

	protected int computeVerticalScrollOffset()
	{
		return mContentViewCore.computeVerticalScrollOffset();
	}

	protected int computeVerticalScrollRange()
	{
		return mContentViewCore.computeVerticalScrollRange();
	}

	public void destroy()
	{
		mContentViewCore.destroy();
	}

	public void detachExternalVideoSurface(int i)
	{
		mContentViewCore.detachExternalVideoSurface(i);
	}

	public boolean dispatchKeyEvent(KeyEvent keyevent)
	{
		if (isFocused())
		{
			if (keyevent.getKeyCode() == 96)
			{
				KeyEvent keyevent1 = new KeyEvent(keyevent.getAction(), 23);
				return mContentViewCore.dispatchKeyEvent(keyevent1);
			} else
			{
				return mContentViewCore.dispatchKeyEvent(keyevent);
			}
		} else
		{
			return dispatchKeyEvent(keyevent);
		}
	}

	public boolean dispatchKeyEventPreIme(KeyEvent keyevent)
	{
		return mContentViewCore.dispatchKeyEventPreIme(keyevent);
	}

	public boolean drawChild(Canvas canvas, View view, long l)
	{
		return drawChild(canvas, view, l);
	}

	public void evaluateJavaScript(String s)
		throws IllegalStateException
	{
		mContentViewCore.evaluateJavaScript(s, null);
	}

	public void exitFullscreen()
	{
		mContentViewCore.exitFullscreen();
	}

	public void fling(long l, int i, int j, int k, int i1)
	{
		mContentViewCore.getContentViewGestureHandler().fling(l, i, j, k, i1);
	}

	public int getBackgroundColor()
	{
		return mContentViewCore.getBackgroundColor();
	}

	public Bitmap getBitmap()
	{
		return getBitmap(getWidth(), getHeight());
	}

	public Bitmap getBitmap(int i, int j)
	{
		return mContentViewCore.getBitmap(i, j);
	}

	public int getContentHeight()
	{
		return mContentViewCore.computeVerticalScrollRange();
	}

	public int getContentScrollY()
	{
		return mContentViewCore.computeVerticalScrollOffset();
	}

	public ContentSettings getContentSettings()
	{
		return mContentViewCore.getContentSettings();
	}

	public ContentViewClient getContentViewClient()
	{
		return mContentViewCore.getContentViewClient();
	}

	public ContentViewCore getContentViewCore()
	{
		return mContentViewCore;
	}

	ContentViewDownloadDelegate getDownloadDelegate()
	{
		return mContentViewCore.getDownloadDelegate();
	}

	public int getPlayerCount()
	{
		return mContentViewCore.getPlayerCount();
	}

	public RenderCoordinates getRenderCoordinates()
	{
		return mContentViewCore.getRenderCoordinates();
	}

	public float getScale()
	{
		return mContentViewCore.getScale();
	}

	public int getSingleTapX()
	{
		return mContentViewCore.getContentViewGestureHandler().getSingleTapX();
	}

	public int getSingleTapY()
	{
		return mContentViewCore.getContentViewGestureHandler().getSingleTapY();
	}

	public String getTitle()
	{
		return mContentViewCore.getTitle();
	}

	public String getUrl()
	{
		return mContentViewCore.getUrl();
	}

	public boolean getUseDesktopUserAgent()
	{
		return mContentViewCore.getUseDesktopUserAgent();
	}

	public View getView()
	{
		return this;
	}

	public void goBack()
	{
		mContentViewCore.goBack();
	}

	public void goForward()
	{
		mContentViewCore.goForward();
	}

	public void goToOffset(int i)
	{
		mContentViewCore.goToOffset(i);
	}

	public void hideSelectActionBar()
	{
		mContentViewCore.hideSelectActionBar();
	}

	public boolean isAlive()
	{
		return mContentViewCore.isAlive();
	}

	public boolean isCrashed()
	{
		return mContentViewCore.isCrashed();
	}

	public boolean isInjectingAccessibilityScript()
	{
		return mContentViewCore.isInjectingAccessibilityScript();
	}

	public boolean isReady()
	{
		return mContentViewCore.isReady();
	}

	public boolean isReadyForSnapshot()
	{
		return !isCrashed() && isReady();
	}

	public boolean isVideoPlaying(int i)
	{
		return mContentViewCore.isVideoPlaying(i);
	}

	public void loadUrl(LoadUrlParams loadurlparams)
	{
		mContentViewCore.loadUrl(loadurlparams);
	}

	public boolean needsReload()
	{
		return mContentViewCore.needsReload();
	}

	public void onActivityPause()
	{
		mContentViewCore.onActivityPause();
	}

	public void onActivityResume()
	{
		mContentViewCore.onActivityResume();
	}

	protected void onAttachedToWindow()
	{
		onAttachedToWindow();
		mContentViewCore.onAttachedToWindow();
	}

	public boolean onCheckIsTextEditor()
	{
		return mContentViewCore.onCheckIsTextEditor();
	}

	protected void onConfigurationChanged(Configuration configuration)
	{
		mContentViewCore.onConfigurationChanged(configuration);
	}

	public InputConnection onCreateInputConnection(EditorInfo editorinfo)
	{
		return mContentViewCore.onCreateInputConnection(editorinfo);
	}

	protected void onDetachedFromWindow()
	{
		onDetachedFromWindow();
		mContentViewCore.onDetachedFromWindow();
	}

	protected void onFocusChanged(boolean flag, int i, Rect rect)
	{
		TraceEvent.begin();
		onFocusChanged(flag, i, rect);
		mContentViewCore.onFocusChanged(flag);
		TraceEvent.end();
	}

	public boolean onGenericMotionEvent(MotionEvent motionevent)
	{
		return mContentViewCore.onGenericMotionEvent(motionevent);
	}

	public void onHide()
	{
		mContentViewCore.onHide();
	}

	public boolean onHoverEvent(MotionEvent motionevent)
	{
		MotionEvent motionevent1 = createOffsetMotionEvent(motionevent);
		boolean flag = mContentViewCore.onHoverEvent(motionevent1);
		motionevent1.recycle();
		return flag;
	}

	public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityevent)
	{
		onInitializeAccessibilityEvent(accessibilityevent);
		mContentViewCore.onInitializeAccessibilityEvent(accessibilityevent);
	}

	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilitynodeinfo)
	{
		onInitializeAccessibilityNodeInfo(accessibilitynodeinfo);
		mContentViewCore.onInitializeAccessibilityNodeInfo(accessibilitynodeinfo);
	}

	public boolean onKeyUp(int i, KeyEvent keyevent)
	{
		return mContentViewCore.onKeyUp(i, keyevent);
	}

	public void onShow()
	{
		mContentViewCore.onShow();
	}

	protected void onSizeChanged(int i, int j, int k, int l)
	{
		TraceEvent.begin();
		onSizeChanged(i, j, k, l);
		mContentViewCore.onSizeChanged(i, j, k, l);
		TraceEvent.end();
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		MotionEvent motionevent1 = createOffsetMotionEvent(motionevent);
		boolean flag = mContentViewCore.onTouchEvent(motionevent1);
		motionevent1.recycle();
		return flag;
	}

	protected void onVisibilityChanged(View view, int i)
	{
		onVisibilityChanged(view, i);
		mContentViewCore.onVisibilityChanged(view, i);
	}

	public boolean performLongClick()
	{
		return false;
	}

	public void pinchBegin(long l, int i, int j)
	{
		mContentViewCore.getContentViewGestureHandler().pinchBegin(l, i, j);
	}

	public void pinchBy(long l, int i, int j, float f)
	{
		mContentViewCore.getContentViewGestureHandler().pinchBy(l, i, j, f);
	}

	public void pinchEnd(long l)
	{
		mContentViewCore.getContentViewGestureHandler().pinchEnd(l);
	}

	public void reload()
	{
		mContentViewCore.reload();
	}

	public void scrollBy(int i, int j)
	{
		mContentViewCore.scrollBy(i, j);
	}

	public void scrollTo(int i, int j)
	{
		mContentViewCore.scrollTo(i, j);
	}

	public void setAccessibilityState(boolean flag)
	{
		mContentViewCore.setAccessibilityState(flag);
	}

	public void setContentViewClient(ContentViewClient contentviewclient)
	{
		mContentViewCore.setContentViewClient(contentviewclient);
	}

	public void setCurrentMotionEventOffsets(float f, float f1)
	{
		mCurrentTouchOffsetX = f;
		mCurrentTouchOffsetY = f1;
	}

	public void setDownloadDelegate(ContentViewDownloadDelegate contentviewdownloaddelegate)
	{
		mContentViewCore.setDownloadDelegate(contentviewdownloaddelegate);
	}

	void setIgnoreSingleTap(boolean flag)
	{
		mContentViewCore.getContentViewGestureHandler().setIgnoreSingleTap(flag);
	}

	public void setUseDesktopUserAgent(boolean flag, boolean flag1)
	{
		mContentViewCore.setUseDesktopUserAgent(flag, flag1);
	}

	public void startFpsProfiling()
	{
	}

	public void stopCurrentAccessibilityNotifications()
	{
		mContentViewCore.stopCurrentAccessibilityNotifications();
	}

	public float stopFpsProfiling()
	{
		return 0.0F;
	}

	public void stopLoading()
	{
		mContentViewCore.stopLoading();
	}

	public boolean super_awakenScrollBars(int i, boolean flag)
	{
		return awakenScrollBars(i, flag);
	}

	public boolean super_dispatchKeyEvent(KeyEvent keyevent)
	{
		return dispatchKeyEvent(keyevent);
	}

	public boolean super_dispatchKeyEventPreIme(KeyEvent keyevent)
	{
		return dispatchKeyEventPreIme(keyevent);
	}

	public void super_onConfigurationChanged(Configuration configuration)
	{
		onConfigurationChanged(configuration);
	}

	public boolean super_onGenericMotionEvent(MotionEvent motionevent)
	{
		return onGenericMotionEvent(motionevent);
	}

	public boolean super_onKeyUp(int i, KeyEvent keyevent)
	{
		return onKeyUp(i, keyevent);
	}

	public boolean zoomIn()
	{
		return mContentViewCore.zoomIn();
	}

	public boolean zoomOut()
	{
		return mContentViewCore.zoomOut();
	}

	public boolean zoomReset()
	{
		return mContentViewCore.zoomReset();
	}
}
