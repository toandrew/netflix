// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.*;
import android.net.Uri;
import android.os.*;
import android.text.Editable;
import android.util.*;
import android.view.*;
import android.view.accessibility.*;
import android.view.inputmethod.*;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import org.chromium.base.WeakContext;
import org.chromium.content.browser.accessibility.AccessibilityInjector;
import org.chromium.content.browser.accessibility.BrowserAccessibilityManager;
import org.chromium.content.browser.input.AdapterInputConnection;
import org.chromium.content.browser.input.ImeAdapter;
import org.chromium.content.browser.input.InputMethodManagerWrapper;
import org.chromium.content.browser.input.InsertionHandleController;
import org.chromium.content.browser.input.SelectPopupDialog;
import org.chromium.content.browser.input.SelectionHandleController;
import org.chromium.content.common.TraceEvent;
import org.chromium.ui.*;
import org.chromium.ui.gfx.DeviceDisplayInfo;

// Referenced classes of package org.chromium.content.browser:
//			NavigationClient, HeapStatsLogger, RenderCoordinates, NavigationEntry, 
//			NavigationHistory, ContentViewGestureHandler, PopupZoomer, SmoothScroller, 
//			ContentViewClient, ZoomManager, ChildProcessLauncher, JavascriptInterface, 
//			WebCryptoJavascriptInterface, DeviceUtils, ContentSettings, LoadUrlParams, 
//			InterstitialPageDelegateAndroid, ContentViewDownloadDelegate, WebContentsObserverAndroid, ContentVideoViewClient, 
//			TouchPoint

public class ContentViewCore
	implements ContentViewGestureHandler.MotionEventDelegate, NavigationClient, android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
{
	public static interface GestureStateListener
	{

		public abstract void onFlingCancelGesture();

		public abstract void onFlingStartGesture(int i, int j);

		public abstract void onPinchGestureEnd();

		public abstract void onPinchGestureStart();

		public abstract void onUnhandledFlingStartEvent();
	}

	public static interface InternalAccessDelegate
	{

		public abstract boolean awakenScrollBars();

		public abstract boolean drawChild(Canvas canvas, View view, long l);

		public abstract boolean super_awakenScrollBars(int i, boolean flag);

		public abstract boolean super_dispatchKeyEvent(KeyEvent keyevent);

		public abstract boolean super_dispatchKeyEventPreIme(KeyEvent keyevent);

		public abstract void super_onConfigurationChanged(Configuration configuration);

		public abstract boolean super_onGenericMotionEvent(MotionEvent motionevent);

		public abstract boolean super_onKeyUp(int i, KeyEvent keyevent);
	}

	public static interface JavaScriptCallback
	{

		public abstract void handleJavaScriptResult(String s);
	}

	public static interface UpdateFrameInfoListener
	{

		public abstract void onFrameInfoUpdated(float f, float f1, float f2);
	}

	public static interface ZoomControlsDelegate
	{

		public abstract void dismissZoomPicker();

		public abstract void invokeZoomPicker();

		public abstract void updateZoomControls();
	}


	static final boolean $assertionsDisabled = false;
	public static final int INPUT_EVENTS_DELIVERED_AT_VSYNC = 1;
	public static final int INPUT_EVENTS_DELIVERED_IMMEDIATELY = 0;
	private static final int IS_LONG_PRESS = 1;
	private static final int IS_LONG_TAP = 2;
	private static final String TAG = "ContentViewCore";
	private static final int TEXT_HANDLE_FADE_IN_DELAY = 300;
	private static final float ZOOM_CONTROLS_EPSILON = 0.007F;
	private AccessibilityInjector mAccessibilityInjector;
	private final AccessibilityManager mAccessibilityManager = (AccessibilityManager)getContext().getSystemService("accessibility");
	private ContentObserver mAccessibilityScriptInjectionObserver;
	private ActionMode mActionMode;
	private org.chromium.content.browser.input.ImeAdapter.AdapterInputConnectionFactory mAdapterInputConnectionFactory;
	private boolean mAttachedToWindow;
	private BrowserAccessibilityManager mBrowserAccessibilityManager;
	private ViewGroup mContainerView;
	private InternalAccessDelegate mContainerViewInternals;
	private ContentSettings mContentSettings;
	private ContentViewClient mContentViewClient;
	private ContentViewGestureHandler mContentViewGestureHandler;
	private final Context mContext;
	private Runnable mDeferredHandleFadeInRunnable;
	private boolean mDidSignalVSyncUsingInputEvent;
	private ContentViewDownloadDelegate mDownloadDelegate;
	private final RenderCoordinates.NormalizedPoint mEndHandlePoint;
	private Runnable mFakeMouseMoveRunnable;
	private final Rect mFocusPreOSKViewportRect = new Rect();
	private GestureStateListener mGestureStateListener;
	private boolean mHardwareAccelerated;
	private boolean mHasSelection;
	private ImeAdapter mImeAdapter;
	private AdapterInputConnection mInputConnection;
	private InsertionHandleController mInsertionHandleController;
	private final RenderCoordinates.NormalizedPoint mInsertionHandlePoint;
	private final Map mJavaScriptInterfaces = new HashMap();
	private String mLastSelectedText;
	private int mNativeContentViewCore;
	private boolean mNeedAnimate;
	private boolean mNeedUpdateOrientationChanged;
	private int mOverdrawBottomHeightPix;
	private boolean mPendingRendererFrame;
	private int mPhysicalBackingHeightPix;
	private int mPhysicalBackingWidthPix;
	private int mPid;
	private PopupZoomer mPopupZoomer;
	private final RenderCoordinates mRenderCoordinates = new RenderCoordinates();
	private final HashSet mRetainedJavaScriptObjects = new HashSet();
	private boolean mScrolledAndZoomedFocusedEditableNode;
	private boolean mSelectionEditable;
	private SelectionHandleController mSelectionHandleController;
	private final RenderCoordinates.NormalizedPoint mStartHandlePoint;
	private boolean mUnfocusOnNextSizeChanged;
	private boolean mUnselectAllOnActionModeDismiss;
	private UpdateFrameInfoListener mUpdateFrameInfoListener;
	private VSyncManager.Listener mVSyncListener;
	private boolean mVSyncListenerRegistered;
	private VSyncManager.Provider mVSyncProvider;
	private int mVSyncSubscriberCount;
	private ViewAndroid mViewAndroid;
	private int mViewportHeightPix;
	private int mViewportSizeOffsetHeightPix;
	private int mViewportSizeOffsetWidthPix;
	private int mViewportWidthPix;
	private WebContentsObserverAndroid mWebContentsObserver;
	private WebCryptoJavascriptInterface mWebCryptoJavascriptInterface;
	private ZoomControlsDelegate mZoomControlsDelegate;
	private ZoomManager mZoomManager;

	public ContentViewCore(Context context)
	{
		mNativeContentViewCore = 0;
		mAttachedToWindow = false;
		mPid = 0;
		mFakeMouseMoveRunnable = null;
		mUnfocusOnNextSizeChanged = false;
		mScrolledAndZoomedFocusedEditableNode = false;
		mHardwareAccelerated = false;
		mPendingRendererFrame = false;
		mNeedAnimate = false;
		mContext = context;
		WeakContext.initializeWeakContext(context);
		HeapStatsLogger.init(mContext.getApplicationContext());
		mAdapterInputConnectionFactory = new org.chromium.content.browser.input.ImeAdapter.AdapterInputConnectionFactory();
		mRenderCoordinates.setDeviceScaleFactor(getContext().getResources().getDisplayMetrics().density);
		mStartHandlePoint = mRenderCoordinates.createNormalizedPoint();
		mEndHandlePoint = mRenderCoordinates.createNormalizedPoint();
		mInsertionHandlePoint = mRenderCoordinates.createNormalizedPoint();
	}

	private void addToNavigationHistory(Object obj, int i, String s, String s1, String s2, String s3, Bitmap bitmap)
	{
		NavigationEntry navigationentry = new NavigationEntry(i, s, s1, s2, s3, bitmap);
		((NavigationHistory)obj).addEntry(navigationentry);
	}

	private boolean allowTextHandleFadeIn()
	{
		while (mContentViewGestureHandler.isNativeScrolling() || mContentViewGestureHandler.isNativePinching() || mPopupZoomer.isShowing()) 
			return false;
		return true;
	}

	private void animateIfNecessary(long l)
	{
		if (mNeedAnimate)
		{
			mNeedAnimate = onAnimate(l);
			if (!mNeedAnimate)
				setVSyncNotificationEnabled(false);
		}
	}

	private void confirmTouchEvent(int i)
	{
		mContentViewGestureHandler.confirmTouchEvent(i);
	}

	private ImeAdapter createImeAdapter(Context context)
	{
		return new ImeAdapter(new InputMethodManagerWrapper(context), new org.chromium.content.browser.input.ImeAdapter.ImeAdapterDelegate() {

			public View getAttachedView()
			{
				return mContainerView;
			}

			public ResultReceiver getNewShowKeyboardReceiver()
			{
				return new ResultReceiver(new Handler()) {
					public void onReceiveResult(int i, Bundle bundle)
					{
						ContentViewClient contentviewclient = getContentViewClient();
						boolean flag;
						if (i == 2 || i == 0)
							flag = true;
						else
							flag = false;
						contentviewclient.onImeStateChangeRequested(flag);
						if (i == 2)
						{
							getContainerView().getWindowVisibleDisplayFrame(mFocusPreOSKViewportRect);
							return;
						}
						if (i == 0)
						{
							scrollFocusedEditableNodeIntoView();
							return;
						} else
						{
							undoScrollFocusedEditableNodeIntoViewIfNeeded(false);
							return;
						}
					}
				};
			}

			public void onDismissInput()
			{
				getContentViewClient().onImeStateChangeRequested(false);
			}

			public void onImeEvent(boolean flag)
			{
				getContentViewClient().onImeEvent();
				if (!flag)
				{
					hideHandles();
					undoScrollFocusedEditableNodeIntoViewIfNeeded(false);
				}
			}

			public void onSetFieldValue()
			{
				scrollFocusedEditableNodeIntoView();
			}
		});
	}

	private static Rect createRect(int i, int j, int k, int l)
	{
		return new Rect(i, j, k, l);
	}

	private SmoothScroller createSmoothScroller(boolean flag, int i, int j)
	{
		return new SmoothScroller(this, flag, i, j);
	}

	private ContentVideoViewClient getContentVideoViewClient()
	{
		return mContentViewClient.getContentVideoViewClient();
	}

	private InsertionHandleController getInsertionHandleController()
	{
		if (mInsertionHandleController == null)
		{
			mInsertionHandleController = new InsertionHandleController(getContainerView()) {

				private static final int AVERAGE_LINE_HEIGHT = 14;

				public int getLineHeight()
				{
					return (int)Math.ceil(mRenderCoordinates.fromLocalCssToPix(14F));
				}

				public void paste()
				{
					mImeAdapter.paste();
					hideHandles();
				}

				public void setCursorPosition(int i, int j)
				{
					if (mNativeContentViewCore != 0)
						nativeMoveCaret(mNativeContentViewCore, i, (float)j - mRenderCoordinates.getContentOffsetYPix());
				}

				public void showHandle()
				{
					super.showHandle();
				}

			
			};
			mInsertionHandleController.hideAndDisallowAutomaticShowing();
		}
		return mInsertionHandleController;
	}

	private SelectionHandleController getSelectionHandleController()
	{
		if (mSelectionHandleController == null)
		{
			mSelectionHandleController = new SelectionHandleController(getContainerView()) {

			
				public void selectBetweenCoordinates(int i, int j, int k, int l)
				{
					if (mNativeContentViewCore != 0 && (i != k || j != l))
						nativeSelectBetweenCoordinates(mNativeContentViewCore, i, (float)j - mRenderCoordinates.getContentOffsetYPix(), k, (float)l - mRenderCoordinates.getContentOffsetYPix());
				}

				public void showHandles(int i, int j)
				{
					super.showHandles(i, j);
					showSelectActionBar();
				}

			
			};
			mSelectionHandleController.hideAndDisallowAutomaticShowing();
		}
		return mSelectionHandleController;
	}

	private void handleTapOrPress(long l, float f, float f1, int i, boolean flag)
	{
//		if (!mContainerView.isFocused())
//			mContainerView.requestFocus();
//		if (!mPopupZoomer.isShowing())
//			mPopupZoomer.setLastTouch(f, f1);
//		if (i != 1) goto _L2; else goto _L1
//_L1:
//		getInsertionHandleController().allowAutomaticShowing();
//		getSelectionHandleController().allowAutomaticShowing();
//		if (mNativeContentViewCore != 0)
//			nativeLongPress(mNativeContentViewCore, l, f, f1, false);
//_L4:
//		return;
//_L2:
//		if (i != 2)
//			break; /* Loop/switch isn't completed */
//		getInsertionHandleController().allowAutomaticShowing();
//		getSelectionHandleController().allowAutomaticShowing();
//		if (mNativeContentViewCore != 0)
//		{
//			nativeLongTap(mNativeContentViewCore, l, f, f1, false);
//			return;
//		}
//		if (true) goto _L4; else goto _L3
//_L3:
//		if (!flag && mNativeContentViewCore != 0)
//			nativeShowPressState(mNativeContentViewCore, l, f, f1);
//		if (mSelectionEditable)
//			getInsertionHandleController().allowAutomaticShowing();
//		if (mNativeContentViewCore != 0)
//		{
//			nativeSingleTap(mNativeContentViewCore, l, f, f1, false);
//			return;
//		}
//		if (true) goto _L4; else goto _L5
//_L5:
	}

	public static boolean hasHardwareAcceleration(Activity activity)
	{
	    return false;
//		Window window = activity.getWindow();
//		if (window == null || (0x1000000 & window.getAttributes().flags) == 0) goto _L2; else goto _L1
//_L1:
//		int i;
//		return true;
//_L2:
//		if (((i = activity.getPackageManager().getActivityInfo(activity.getComponentName(), 0).flags) & 0x200) != 0) goto _L1; else goto _L3
//_L3:
//		return false;
//		android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
//		namenotfoundexception;
//		Log.e("Chrome", "getActivityInfo(self) should not fail");
//		if (true) goto _L3; else goto _L4
//_L4:
	}

	private static boolean hasHardwareAcceleration(Context context)
	{
		if (context instanceof Activity)
			return hasHardwareAcceleration((Activity)context);
		else
			return false;
	}

	private void hasTouchEventHandlers(boolean flag)
	{
		mContentViewGestureHandler.hasTouchEventHandlers(flag);
	}

	private void hideHandles()
	{
		if (mSelectionHandleController != null)
			mSelectionHandleController.hideAndDisallowAutomaticShowing();
		if (mInsertionHandleController != null)
			mInsertionHandleController.hideAndDisallowAutomaticShowing();
	}

	private void hidePopupDialog()
	{
		SelectPopupDialog.hide(this);
		hideHandles();
		hideSelectActionBar();
	}

	private void initPopupZoomer(Context context)
	{
		mPopupZoomer = new PopupZoomer(context);
		mPopupZoomer.setOnVisibilityChangedListener(new PopupZoomer.OnVisibilityChangedListener() {

			public void onPopupZoomerHidden(PopupZoomer popupzoomer)
			{
				mContainerView.post(new Runnable() {

					public void run()
					{
//						if (mContainerView.indexOfChild(popupzoomer) != -1)
//						{
//							mContainerView.removeView(popupzoomer);
//							mContainerView.invalidate();
//						} else
//						if (!$assertionsDisabled)
//							throw new AssertionError("PopupZoomer should never be hidden without being shown");
					}

//					static 
//					{
//						boolean flag;
//						if (!org/chromium/content/browser/ContentViewCore.desiredAssertionStatus())
//							flag = true;
//						else
//							flag = false;
//						$assertionsDisabled = flag;
//					}

				});
			}

			public void onPopupZoomerShown(PopupZoomer popupzoomer)
			{
				mContainerView.post(new Runnable() {

			
					public void run()
					{
//						if (mContainerView.indexOfChild(zoomer) == -1)
//							mContainerView.addView(zoomer);
//						else
//						if (!$assertionsDisabled)
//							throw new AssertionError("PopupZoomer should never be shown without being hidden");
					}

//					static 
//					{
//						boolean flag;
//						if (!org/chromium/content/browser/ContentViewCore.desiredAssertionStatus())
//							flag = true;
//						else
//							flag = false;
//						$assertionsDisabled = flag;
//					}

			
				});
			}
		});
		PopupZoomer.OnTapListener ontaplistener = new PopupZoomer.OnTapListener() {

			
			public boolean onLongPress(View view, MotionEvent motionevent)
			{
				if (mNativeContentViewCore != 0)
					nativeLongPress(mNativeContentViewCore, motionevent.getEventTime(), motionevent.getX(), motionevent.getY(), true);
				return true;
			}

			public boolean onSingleTap(View view, MotionEvent motionevent)
			{
				mContainerView.requestFocus();
				if (mNativeContentViewCore != 0)
					nativeSingleTap(mNativeContentViewCore, motionevent.getEventTime(), motionevent.getX(), motionevent.getY(), true);
				return true;
			}


		};
		mPopupZoomer.setOnTapListener(ontaplistener);
	}

	private void initializeContainerView(InternalAccessDelegate internalaccessdelegate, int i)
	{
		TraceEvent.begin();
		mContainerViewInternals = internalaccessdelegate;
		mContainerView.setWillNotDraw(false);
		mContainerView.setFocusable(true);
		mContainerView.setFocusableInTouchMode(true);
		mContainerView.setClickable(true);
		mZoomManager = new ZoomManager(mContext, this);
		mContentViewGestureHandler = new ContentViewGestureHandler(mContext, this, mZoomManager, i);
		mZoomControlsDelegate = new ZoomControlsDelegate() {

			public void dismissZoomPicker()
			{
			}

			public void invokeZoomPicker()
			{
			}

			public void updateZoomControls()
			{
			}

		
		};
		mRenderCoordinates.reset();
		initPopupZoomer(mContext);
		mImeAdapter = createImeAdapter(mContext);
		TraceEvent.end();
	}

	private boolean isInsertionHandleShowing()
	{
		return mInsertionHandleController != null && mInsertionHandleController.isShowing();
	}

	private boolean isSelectionHandleShowing()
	{
		return mSelectionHandleController != null && mSelectionHandleController.isShowing();
	}

	private boolean isVSyncNotificationEnabled()
	{
		return mVSyncProvider != null && mVSyncListenerRegistered;
	}

	private native void nativeAddJavascriptInterface(int i, Object obj, String s, Class class1, HashSet hashset);

	private native void nativeAttachExternalVideoSurface(int i, int j, Surface surface);

	private native boolean nativeCanGoBack(int i);

	private native boolean nativeCanGoForward(int i);

	private native boolean nativeCanGoToOffset(int i, int j);

	private native void nativeCancelPendingReload(int i);

	private native void nativeClearHistory(int i);

	private native void nativeClearSslPreferences(int i);

	private native void nativeContinuePendingReload(int i);

	private native boolean nativeCrashed(int i);

	private native void nativeDetachExternalVideoSurface(int i, int j);

	private native void nativeDoubleTap(int i, long l, float f, float f1);

	private native void nativeEvaluateJavaScript(int i, String s, JavaScriptCallback javascriptcallback);

	private native void nativeExitFullscreen(int i);

	private native void nativeFlingCancel(int i, long l);

	private native void nativeFlingStart(int i, long l, float f, float f1, float f2, float f3);

	private native int nativeGetBackgroundColor(int i);

	private native int nativeGetCurrentRenderProcessId(int i);

	private native void nativeGetDirectedNavigationHistory(int i, Object obj, boolean flag, int j);

	private native int nativeGetNativeImeAdapter(int i);

	private native int nativeGetNavigationHistory(int i, Object obj);

	private native String nativeGetOriginalUrlForActiveNavigationEntry(int i);

	private native int nativeGetPlayerCount(int i);

	private native String nativeGetTitle(int i);

	private native String nativeGetURL(int i);

	private native boolean nativeGetUseDesktopUserAgent(int i);

	private native void nativeGoBack(int i);

	private native void nativeGoForward(int i);

	private native void nativeGoToNavigationIndex(int i, int j);

	private native void nativeGoToOffset(int i, int j);

	private native int nativeInit(boolean flag, int i, int j, int k);

	private native boolean nativeIsIncognito(int i);

	private native boolean nativeIsRenderWidgetHostViewReady(int i);

	private native boolean nativeIsShowingInterstitialPage(int i);

	private native boolean nativeIsVideoPlaying(int i, int j);

	private native void nativeLoadUrl(int i, String s, int j, int k, int l, String s1, byte abyte0[], 
			String s2, String s3, boolean flag);

	private native void nativeLongPress(int i, long l, float f, float f1, boolean flag);

	private native void nativeLongTap(int i, long l, float f, float f1, boolean flag);

	private native void nativeMoveCaret(int i, float f, float f1);

	private native boolean nativeNeedsReload(int i);

	private native boolean nativeOnAnimate(int i, long l);

	private native void nativeOnHide(int i);

	private native void nativeOnJavaContentViewCoreDestroyed(int i);

	private native void nativeOnShow(int i);

	private native void nativeOnVSync(int i, long l);

	private native void nativePinchBegin(int i, long l, float f, float f1);

	private native void nativePinchBy(int i, long l, float f, float f1, float f2, boolean flag);

	private native void nativePinchEnd(int i, long l);

	private native boolean nativePopulateBitmapFromCompositor(int i, Bitmap bitmap);

	private native void nativeReload(int i);

	private native void nativeRemoveJavascriptInterface(int i, String s);

	private native void nativeScrollBegin(int i, long l, float f, float f1);

	private native void nativeScrollBy(int i, long l, float f, float f1, float f2, float f3, 
			boolean flag);

	private native void nativeScrollEnd(int i, long l);

	private native void nativeScrollFocusedEditableNodeIntoView(int i);

	private native void nativeSelectBetweenCoordinates(int i, float f, float f1, float f2, float f3);

	private native void nativeSelectPopupMenuItems(int i, int ai[]);

	private native int nativeSendMouseMoveEvent(int i, long l, float f, float f1);

	private native int nativeSendMouseWheelEvent(int i, long l, float f, float f1, float f2);

	private native void nativeSendOrientationChangeEvent(int i, int j);

	private native boolean nativeSendTouchEvent(int i, long l, int j, TouchPoint atouchpoint[]);

	private native void nativeSetAccessibilityEnabled(int i, boolean flag);

	private native void nativeSetFocus(int i, boolean flag);

	private native void nativeSetUseDesktopUserAgent(int i, boolean flag, boolean flag1);

	private native void nativeShowImeIfNeeded(int i);

	private native void nativeShowInterstitialPage(int i, String s, int j);

	private native void nativeShowPressCancel(int i, long l, float f, float f1);

	private native void nativeShowPressState(int i, long l, float f, float f1);

	private native void nativeSingleTap(int i, long l, float f, float f1, boolean flag);

	private native void nativeSingleTapUnconfirmed(int i, long l, float f, float f1);

	private native void nativeStopLoading(int i);

	private native void nativeUndoScrollFocusedEditableNodeIntoView(int i);

	private native void nativeUpdateTopControlsState(int i, boolean flag, boolean flag1, boolean flag2);

	private native void nativeUpdateVSyncParameters(int i, long l, long l1);

	private native void nativeWasResized(int i);

	private void notifyExternalSurface(int i, boolean flag, float f, float f1, float f2, float f3)
	{
		if (flag)
			getContentViewClient().onExternalVideoSurfaceRequested(i);
		getContentViewClient().onGeometryChanged(i, new RectF(f, f1, f + f2, f1 + f3));
	}

	private boolean offerGestureToEmbedder(int i)
	{
		if (i == 5)
			return mContainerView.performLongClick();
		else
			return false;
	}

	private boolean onAnimate(long l)
	{
		if (mNativeContentViewCore == 0)
			return false;
		else
			return nativeOnAnimate(mNativeContentViewCore, l);
	}

	private void onBackgroundColorChanged(int i)
	{
		getContentViewClient().onBackgroundColorChanged(i);
	}

	private static void onEvaluateJavaScriptResult(String s, JavaScriptCallback javascriptcallback)
	{
		javascriptcallback.handleJavaScriptResult(s);
	}

	private void onRenderProcessSwap(int i, int j)
	{
		if (!$assertionsDisabled && mPid != i && mPid != j)
			throw new AssertionError();
		if (mAttachedToWindow && i != j)
		{
			ChildProcessLauncher.unbindAsHighPriority(i);
			ChildProcessLauncher.bindAsHighPriority(j);
		}
		ChildProcessLauncher.removeInitialBinding(j);
		mPid = j;
	}

	private void onSelectionBoundsChanged(Rect rect, int i, Rect rect1, int j, boolean flag)
	{
//		int k;
//		int l;
//		k = rect.left;
//		l = rect.bottom;
//		int i1 = rect1.left;
//		int j1 = rect1.bottom;
//		if (k != i1 || l != j1 || mSelectionHandleController != null && mSelectionHandleController.isDragging())
//		{
//			if (mInsertionHandleController != null)
//				mInsertionHandleController.hide();
//			if (flag)
//			{
//				mStartHandlePoint.setLocalDip(k, l);
//				mEndHandlePoint.setLocalDip(i1, j1);
//			} else
//			{
//				mStartHandlePoint.setLocalDip(i1, j1);
//				mEndHandlePoint.setLocalDip(k, l);
//			}
//			getSelectionHandleController().onSelectionChanged(i, j);
//			updateHandleScreenPositions();
//			mHasSelection = true;
//			return;
//		}
//		mUnselectAllOnActionModeDismiss = false;
//		hideSelectActionBar();
//		if (k == 0 || l == 0 || !mSelectionEditable) goto _L2; else goto _L1
//_L1:
//		if (mSelectionHandleController != null)
//			mSelectionHandleController.hide();
//		mInsertionHandlePoint.setLocalDip(k, l);
//		getInsertionHandleController().onCursorPositionChanged();
//		updateHandleScreenPositions();
//		InputMethodManager inputmethodmanager = (InputMethodManager)getContext().getSystemService("input_method");
//		if (inputmethodmanager.isWatchingCursor(mContainerView))
//		{
//			int k1 = (int)mInsertionHandlePoint.getXPix();
//			int l1 = (int)mInsertionHandlePoint.getYPix();
//			inputmethodmanager.updateCursor(mContainerView, k1, l1, k1, l1);
//		}
//_L4:
//		mHasSelection = false;
//		return;
//_L2:
//		if (mSelectionHandleController != null)
//			mSelectionHandleController.hideAndDisallowAutomaticShowing();
//		if (mInsertionHandleController != null)
//			mInsertionHandleController.hideAndDisallowAutomaticShowing();
//		if (true) goto _L4; else goto _L3
//_L3:
	}

	private void onSelectionChanged(String s)
	{
		mLastSelectedText = s;
	}

	private void onTabCrash()
	{
		if (!$assertionsDisabled && mPid == 0)
		{
			throw new AssertionError();
		} else
		{
			getContentViewClient().onRendererCrash(ChildProcessLauncher.isOomProtected(mPid));
			mPid = 0;
			return;
		}
	}

	private void onWebContentsConnected()
	{
		if (mImeAdapter != null && !mImeAdapter.isNativeImeAdapterAttached() && mNativeContentViewCore != 0)
			mImeAdapter.attach(nativeGetNativeImeAdapter(mNativeContentViewCore));
	}

	private void onWebContentsSwapped()
	{
		if (mImeAdapter != null && mNativeContentViewCore != 0)
			mImeAdapter.attach(nativeGetNativeImeAdapter(mNativeContentViewCore));
	}

	private void onWebMediaPlayerCreated(int i)
	{
		getContentViewClient().onWebMediaPlayerCreated(i);
	}

	private void paintExternalSurface(int i, int j)
	{
		getContentViewClient().onPaintExternalSurface(i, j);
	}

	private void processImeBatchStateAck(boolean flag)
	{
		if (mInputConnection == null)
		{
			return;
		} else
		{
			mInputConnection.setIgnoreTextInputStateUpdates(flag);
			return;
		}
	}

	private void resetGestureDetectors()
	{
		mContentViewGestureHandler.resetGestureHandlers();
	}

	private void resetVSyncNotification()
	{
		for (; isVSyncNotificationEnabled(); setVSyncNotificationEnabled(false));
		mVSyncSubscriberCount = 0;
		mVSyncListenerRegistered = false;
		mNeedAnimate = false;
	}

	private void scheduleTextHandleFadeIn()
	{
		if (!isInsertionHandleShowing() && !isSelectionHandleShowing())
			return;
		if (mDeferredHandleFadeInRunnable == null)
			mDeferredHandleFadeInRunnable = new Runnable() {

				public void run()
				{
					if (!allowTextHandleFadeIn())
					{
						scheduleTextHandleFadeIn();
					} else
					{
						if (isSelectionHandleShowing())
							mSelectionHandleController.beginHandleFadeIn();
						if (isInsertionHandleShowing())
						{
							mInsertionHandleController.beginHandleFadeIn();
							return;
						}
					}
				}

			};
		mContainerView.removeCallbacks(mDeferredHandleFadeInRunnable);
		mContainerView.postDelayed(mDeferredHandleFadeInRunnable, 300L);
	}

	private void scrollFocusedEditableNodeIntoView()
	{
		if (mNativeContentViewCore != 0)
		{
			(new Runnable() {

				public void run()
				{
					if (mNativeContentViewCore != 0)
						nativeScrollFocusedEditableNodeIntoView(mNativeContentViewCore);
				}

	
			}).run();
			mScrolledAndZoomedFocusedEditableNode = true;
		}
	}

	private void sendOrientationChangeEvent()
	{
		if (mNativeContentViewCore == 0)
			return;
		switch (((WindowManager)getContext().getSystemService("window")).getDefaultDisplay().getRotation())
		{
		default:
			Log.w("ContentViewCore", "Unknown rotation!");
			return;

		case 1: // '\001'
			nativeSendOrientationChangeEvent(mNativeContentViewCore, 90);
			return;

		case 2: // '\002'
			nativeSendOrientationChangeEvent(mNativeContentViewCore, 180);
			return;

		case 3: // '\003'
			nativeSendOrientationChangeEvent(mNativeContentViewCore, -90);
			return;

		case 0: // '\0'
			nativeSendOrientationChangeEvent(mNativeContentViewCore, 0);
			return;
		}
	}

	private void setNeedsAnimate()
	{
		if (!mNeedAnimate)
		{
			mNeedAnimate = true;
			setVSyncNotificationEnabled(true);
		}
	}

	private void setTitle(String s)
	{
		getContentViewClient().onUpdateTitle(s);
	}

	private void showDisambiguationPopup(Rect rect, Bitmap bitmap)
	{
		mPopupZoomer.setBitmap(bitmap);
		mPopupZoomer.show(rect);
		temporarilyHideTextHandles();
	}

	private void showPastePopup(int i, int j)
	{
		mInsertionHandlePoint.setLocalDip(i, j);
		getInsertionHandleController().showHandle();
		updateHandleScreenPositions();
		getInsertionHandleController().showHandleWithPastePopup();
	}

	private void showSelectActionBar()
	{
		if (mActionMode != null)
		{
			mActionMode.invalidate();
			return;
		}
		SelectActionModeCallback.ActionHandler actionhandler = new SelectActionModeCallback.ActionHandler() {

			public boolean copy()
			{
				return mImeAdapter.copy();
			}

			public boolean cut()
			{
				return mImeAdapter.cut();
			}

			public String getSelectedText()
			{
				return ContentViewCore.this.getSelectedText();
			}

			public boolean isSelectionEditable()
			{
				return mSelectionEditable;
			}

			public void onDestroyActionMode()
			{
				mActionMode = null;
				if (mUnselectAllOnActionModeDismiss)
					mImeAdapter.unselect();
				getContentViewClient().onContextualActionBarHidden();
			}

			public boolean paste()
			{
				return mImeAdapter.paste();
			}

			public boolean selectAll()
			{
				return mImeAdapter.selectAll();
			}

		};
		mActionMode = null;
		if (mContainerView.getParent() != null)
			mActionMode = mContainerView.startActionMode(getContentViewClient().getSelectActionModeCallback(getContext(), actionhandler, nativeIsIncognito(mNativeContentViewCore)));
		mUnselectAllOnActionModeDismiss = true;
		if (mActionMode == null)
		{
			mImeAdapter.unselect();
			return;
		} else
		{
			getContentViewClient().onContextualActionBarShown();
			return;
		}
	}

	private void showSelectPopup(String as[], int ai[], boolean flag, int ai1[])
	{
		SelectPopupDialog.show(this, as, ai, flag, ai1);
	}

	private void startContentIntent(String s)
	{
		getContentViewClient().onStartContentIntent(getContext(), s);
	}

	private void temporarilyHideTextHandles()
	{
		if (isSelectionHandleShowing())
			mSelectionHandleController.setHandleVisibility(4);
		if (isInsertionHandleShowing())
			mInsertionHandleController.setHandleVisibility(4);
		scheduleTextHandleFadeIn();
	}

	private void undoScrollFocusedEditableNodeIntoViewIfNeeded(boolean flag)
	{
		if (mScrolledAndZoomedFocusedEditableNode && flag && mNativeContentViewCore != 0)
			(new Runnable() {

				public void run()
				{
					if (mNativeContentViewCore != 0)
						nativeUndoScrollFocusedEditableNodeIntoView(mNativeContentViewCore);
				}

			}).run();
		mScrolledAndZoomedFocusedEditableNode = false;
	}

	private void unhandledFlingStartEvent()
	{
		if (mGestureStateListener != null)
			mGestureStateListener.onUnhandledFlingStartEvent();
	}

	private void unregisterAccessibilityContentObserver()
	{
		if (mAccessibilityScriptInjectionObserver == null)
		{
			return;
		} else
		{
			getContext().getContentResolver().unregisterContentObserver(mAccessibilityScriptInjectionObserver);
			mAccessibilityScriptInjectionObserver = null;
			return;
		}
	}

	private void updateAfterSizeChanged()
	{
//		mPopupZoomer.hide(false);
//		if (mFocusPreOSKViewportRect.isEmpty()) goto _L2; else goto _L1
//_L1:
//		Rect rect = new Rect();
//		getContainerView().getWindowVisibleDisplayFrame(rect);
//		if (!rect.equals(mFocusPreOSKViewportRect))
//		{
//			scrollFocusedEditableNodeIntoView();
//			mFocusPreOSKViewportRect.setEmpty();
//		}
//_L4:
//		if (mNeedUpdateOrientationChanged)
//		{
//			sendOrientationChangeEvent();
//			mNeedUpdateOrientationChanged = false;
//		}
//		return;
//_L2:
//		if (mUnfocusOnNextSizeChanged)
//		{
//			undoScrollFocusedEditableNodeIntoViewIfNeeded(true);
//			mUnfocusOnNextSizeChanged = false;
//		}
//		if (true) goto _L4; else goto _L3
//_L3:
	}

	private void updateFrameInfo(float f, float f1, float f2, float f3, float f4, float f5, float f6, 
			float f7, float f8, float f9, float f10, float f11)
	{
		TraceEvent.instant("ContentViewCore:updateFrameInfo");
		float f12 = Math.max(f5, mRenderCoordinates.fromPixToLocalCss(mViewportWidthPix));
		float f13 = Math.max(f6, mRenderCoordinates.fromPixToLocalCss(mViewportHeightPix));
		float f14 = mRenderCoordinates.fromDipToPix(f10);
		boolean flag;
		boolean flag1;
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		boolean flag6;
		boolean flag7;
		float f15;
		float f16;
		float f17;
		if (f12 != mRenderCoordinates.getContentWidthCss() || f13 != mRenderCoordinates.getContentHeightCss())
			flag = true;
		else
			flag = false;
		if (f3 != mRenderCoordinates.getMinPageScaleFactor() || f4 != mRenderCoordinates.getMaxPageScaleFactor())
			flag1 = true;
		else
			flag1 = false;
		if (f2 != mRenderCoordinates.getPageScaleFactor())
			flag2 = true;
		else
			flag2 = false;
		if (flag2 || f != mRenderCoordinates.getScrollX() || f1 != mRenderCoordinates.getScrollY())
			flag3 = true;
		else
			flag3 = false;
		if (f14 != mRenderCoordinates.getContentOffsetYPix())
			flag4 = true;
		else
			flag4 = false;
		if (flag || flag3)
			flag5 = true;
		else
			flag5 = false;
		if (flag1 || flag3)
			flag6 = true;
		else
			flag6 = false;
		flag7 = flag3;
		if (flag5)
			mPopupZoomer.hide(true);
		if (flag2)
			getContentViewClient().onScaleChanged(mRenderCoordinates.getPageScaleFactor(), f2);
		mRenderCoordinates.updateFrameInfo(f, f1, f12, f13, f7, f8, f2, f3, f4, f14);
		if ((flag || flag2) && mUpdateFrameInfoListener != null)
			mUpdateFrameInfoListener.onFrameInfoUpdated(f12, f13, f2);
		if (flag7)
			temporarilyHideTextHandles();
		if (flag6)
			mZoomControlsDelegate.updateZoomControls();
		if (flag4)
			updateHandleScreenPositions();
		f15 = mRenderCoordinates.getDeviceScaleFactor();
		f16 = f9 * f15;
		f17 = f11 * f15;
		getContentViewClient().onOffsetsForFullscreenChanged(f16, f14, f17);
		mPendingRendererFrame = true;
		if (mBrowserAccessibilityManager != null)
			mBrowserAccessibilityManager.notifyFrameInfoInitialized();
		getContentViewClient().onGeometryChanged(-1, null);
	}

	private void updateHandleScreenPositions()
	{
		if (isSelectionHandleShowing())
		{
			mSelectionHandleController.setStartHandlePosition(mStartHandlePoint.getXPix(), mStartHandlePoint.getYPix());
			mSelectionHandleController.setEndHandlePosition(mEndHandlePoint.getXPix(), mEndHandlePoint.getYPix());
		}
		if (isInsertionHandleShowing())
			mInsertionHandleController.setHandlePosition(mInsertionHandlePoint.getXPix(), mInsertionHandlePoint.getYPix());
	}

	private void updateImeAdapter(int i, int j, String s, int k, int l, int i1, int j1, 
			boolean flag)
	{
		TraceEvent.begin();
		boolean flag1;
		if (j != ImeAdapter.getTextInputTypeNone())
			flag1 = true;
		else
			flag1 = false;
		mSelectionEditable = flag1;
		if (mActionMode != null)
			mActionMode.invalidate();
		mImeAdapter.attachAndShowIfNeeded(i, j, k, l, flag);
		if (mInputConnection != null)
			mInputConnection.setEditableText(s, k, l, i1, j1);
		TraceEvent.end();
	}

	private void updateTextHandlesForGesture(int i)
	{
		switch (i)
		{
		default:
			return;

		case 1: // '\001'
		case 6: // '\006'
		case 9: // '\t'
		case 11: // '\013'
			temporarilyHideTextHandles();
			break;
		}
	}

	private boolean zoomByDelta(float f)
	{
		if (mNativeContentViewCore == 0)
		{
			return false;
		} else
		{
			long l = System.currentTimeMillis();
			int i = getViewportWidthPix() / 2;
			int j = getViewportHeightPix() / 2;
			getContentViewGestureHandler().pinchBegin(l, i, j);
			getContentViewGestureHandler().pinchBy(l, i, j, f);
			getContentViewGestureHandler().pinchEnd(l);
			return true;
		}
	}

	public void addJavascriptInterface(Object obj, String s)
	{
		addPossiblyUnsafeJavascriptInterface(obj, s, org.chromium.content.browser.JavascriptInterface.class);
	}

	public void addPossiblyUnsafeJavascriptInterface(Object obj, String s, Class class1)
	{
		if (mNativeContentViewCore != 0 && obj != null)
		{
			mJavaScriptInterfaces.put(s, obj);
			nativeAddJavascriptInterface(mNativeContentViewCore, obj, s, class1, mRetainedJavaScriptObjects);
		}
	}

	public void attachExternalVideoSurface(int i, Surface surface)
	{
		if (mNativeContentViewCore != 0)
			nativeAttachExternalVideoSurface(mNativeContentViewCore, i, surface);
	}

	public boolean awakenScrollBars(int i, boolean flag)
	{
		if (mContainerView.getScrollBarStyle() == 0)
			return false;
		else
			return mContainerViewInternals.super_awakenScrollBars(i, flag);
	}

	public boolean canGoBack()
	{
		return mNativeContentViewCore != 0 && nativeCanGoBack(mNativeContentViewCore);
	}

	public boolean canGoForward()
	{
		return mNativeContentViewCore != 0 && nativeCanGoForward(mNativeContentViewCore);
	}

	public boolean canGoToOffset(int i)
	{
		return mNativeContentViewCore != 0 && nativeCanGoToOffset(mNativeContentViewCore, i);
	}

	public boolean canZoomIn()
	{
		return mRenderCoordinates.getMaxPageScaleFactor() - mRenderCoordinates.getPageScaleFactor() > 0.007F;
	}

	public boolean canZoomOut()
	{
		return mRenderCoordinates.getPageScaleFactor() - mRenderCoordinates.getMinPageScaleFactor() > 0.007F;
	}

	public void cancelPendingReload()
	{
		if (mNativeContentViewCore != 0)
			nativeCancelPendingReload(mNativeContentViewCore);
	}

	void checkIsAlive()
		throws IllegalStateException
	{
		if (!isAlive())
			throw new IllegalStateException("ContentView used after destroy() was called");
		else
			return;
	}

	public void clearHistory()
	{
		if (mNativeContentViewCore != 0)
			nativeClearHistory(mNativeContentViewCore);
	}

	public void clearSslPreferences()
	{
		nativeClearSslPreferences(mNativeContentViewCore);
	}

	public int computeHorizontalScrollExtent()
	{
		return mRenderCoordinates.getLastFrameViewportWidthPixInt();
	}

	public int computeHorizontalScrollOffset()
	{
		return mRenderCoordinates.getScrollXPixInt();
	}

	public int computeHorizontalScrollRange()
	{
		return mRenderCoordinates.getContentWidthPixInt();
	}

	public int computeVerticalScrollExtent()
	{
		return mRenderCoordinates.getLastFrameViewportHeightPixInt();
	}

	public int computeVerticalScrollOffset()
	{
		return mRenderCoordinates.getScrollYPixInt();
	}

	public int computeVerticalScrollRange()
	{
		return mRenderCoordinates.getContentHeightPixInt();
	}

	public boolean consumePendingRendererFrame()
	{
		boolean flag = mPendingRendererFrame;
		mPendingRendererFrame = false;
		return flag;
	}

	public void continuePendingReload()
	{
		if (mNativeContentViewCore != 0)
			nativeContinuePendingReload(mNativeContentViewCore);
	}

	public void destroy()
	{
		if (mNativeContentViewCore != 0)
			nativeOnJavaContentViewCoreDestroyed(mNativeContentViewCore);
		resetVSyncNotification();
		mVSyncProvider = null;
		if (mViewAndroid != null)
			mViewAndroid.destroy();
		mNativeContentViewCore = 0;
		mContentSettings = null;
		mJavaScriptInterfaces.clear();
		mRetainedJavaScriptObjects.clear();
		unregisterAccessibilityContentObserver();
		Log.d("ContentViewCore", "destroy called in ContentViewcore");
		mWebCryptoJavascriptInterface.close();
	}

	public void detachExternalVideoSurface(int i)
	{
		if (mNativeContentViewCore != 0)
			nativeDetachExternalVideoSurface(mNativeContentViewCore, i);
	}

	public boolean didUIStealScroll(float f, float f1)
	{
		return getContentViewClient().shouldOverrideScroll(f, f1, computeHorizontalScrollOffset(), computeVerticalScrollOffset());
	}

	public boolean dispatchKeyEvent(KeyEvent keyevent)
	{
		if (getContentViewClient().shouldOverrideKeyEvent(keyevent))
			return mContainerViewInternals.super_dispatchKeyEvent(keyevent);
		if (mImeAdapter.dispatchKeyEvent(keyevent))
			return true;
		else
			return mContainerViewInternals.super_dispatchKeyEvent(keyevent);
	}

	public boolean dispatchKeyEventPreIme(KeyEvent keyevent)
	{
	    return true;
//		TraceEvent.begin();
//		if (keyevent.getKeyCode() != 4 || !mImeAdapter.isActive())
//			break MISSING_BLOCK_LABEL_42;
//		mUnfocusOnNextSizeChanged = true;
//_L1:
//		boolean flag = mContainerViewInternals.super_dispatchKeyEventPreIme(keyevent);
//		TraceEvent.end();
//		return flag;
//		undoScrollFocusedEditableNodeIntoViewIfNeeded(false);
//		  goto _L1
//		Exception exception;
//		exception;
//		TraceEvent.end();
//		throw exception;
	}

	public void evaluateJavaScript(String s, JavaScriptCallback javascriptcallback)
		throws IllegalStateException
	{
		checkIsAlive();
		nativeEvaluateJavaScript(mNativeContentViewCore, s, javascriptcallback);
	}

	public void exitFullscreen()
	{
		nativeExitFullscreen(mNativeContentViewCore);
	}

	public AccessibilityNodeProvider getAccessibilityNodeProvider()
	{
		if (mBrowserAccessibilityManager != null)
			return mBrowserAccessibilityManager.getAccessibilityNodeProvider();
		else
			return null;
	}

	public int getBackgroundColor()
	{
		if (mNativeContentViewCore != 0)
			return nativeGetBackgroundColor(mNativeContentViewCore);
		else
			return -1;
	}

	public Bitmap getBitmap()
	{
		return getBitmap(getViewportWidthPix(), getViewportHeightPix());
	}

	public Bitmap getBitmap(int i, int j)
	{
		Bitmap bitmap;
		if (i == 0 || j == 0 || getViewportWidthPix() == 0 || getViewportHeightPix() == 0)
		{
			bitmap = null;
		} else
		{
			bitmap = Bitmap.createBitmap(i, j, android.graphics.Bitmap.Config.ARGB_8888);
			if (mNativeContentViewCore != 0 && nativePopulateBitmapFromCompositor(mNativeContentViewCore, bitmap))
			{
				if (mContainerView.getChildCount() > 0)
				{
					Canvas canvas = new Canvas(bitmap);
					canvas.scale((float)i / (float)getViewportWidthPix(), (float)j / (float)getViewportHeightPix());
					mContainerView.draw(canvas);
					return bitmap;
				}
			} else
			{
				return null;
			}
		}
		return bitmap;
	}

	public BrowserAccessibilityManager getBrowserAccessibilityManager()
	{
		return mBrowserAccessibilityManager;
	}

	public ViewGroup getContainerView()
	{
		return mContainerView;
	}

	public float getContentHeightCss()
	{
		return mRenderCoordinates.getContentHeightCss();
	}

	public ContentSettings getContentSettings()
	{
		return mContentSettings;
	}

	ContentViewClient getContentViewClient()
	{
		if (mContentViewClient == null)
			mContentViewClient = new ContentViewClient();
		return mContentViewClient;
	}

	ContentViewGestureHandler getContentViewGestureHandler()
	{
		return mContentViewGestureHandler;
	}

	public float getContentWidthCss()
	{
		return mRenderCoordinates.getContentWidthCss();
	}

	public Context getContext()
	{
		return mContext;
	}

	public NavigationHistory getDirectedNavigationHistory(boolean flag, int i)
	{
		NavigationHistory navigationhistory = new NavigationHistory();
		nativeGetDirectedNavigationHistory(mNativeContentViewCore, navigationhistory, flag, i);
		return navigationhistory;
	}

	ContentViewDownloadDelegate getDownloadDelegate()
	{
		return mDownloadDelegate;
	}

	public Editable getEditableForTest()
	{
		return mInputConnection.getEditable();
	}

	public ImeAdapter getImeAdapterForTest()
	{
		return mImeAdapter;
	}

	public AdapterInputConnection getInputConnectionForTest()
	{
		return mInputConnection;
	}

	public InsertionHandleController getInsertionHandleControllerForTest()
	{
		return mInsertionHandleController;
	}

	public int getNativeContentViewCore()
	{
		return mNativeContentViewCore;
	}

	public int getNativeScrollXForTest()
	{
		return mRenderCoordinates.getScrollXPixInt();
	}

	public int getNativeScrollYForTest()
	{
		return mRenderCoordinates.getScrollYPixInt();
	}

	public NavigationHistory getNavigationHistory()
	{
		NavigationHistory navigationhistory = new NavigationHistory();
		navigationhistory.setCurrentEntryIndex(nativeGetNavigationHistory(mNativeContentViewCore, navigationhistory));
		return navigationhistory;
	}

	public String getOriginalUrlForActiveNavigationEntry()
	{
		return nativeGetOriginalUrlForActiveNavigationEntry(mNativeContentViewCore);
	}

	public int getOverdrawBottomHeightPix()
	{
		return mOverdrawBottomHeightPix;
	}

	public int getPhysicalBackingHeightPix()
	{
		return mPhysicalBackingHeightPix;
	}

	public int getPhysicalBackingWidthPix()
	{
		return mPhysicalBackingWidthPix;
	}

	public int getPlayerCount()
	{
		if (mNativeContentViewCore != 0)
			return nativeGetPlayerCount(mNativeContentViewCore);
		else
			return 0;
	}

	public RenderCoordinates getRenderCoordinates()
	{
		return mRenderCoordinates;
	}

	public float getScale()
	{
		return mRenderCoordinates.getPageScaleFactor();
	}

	public Pair getScaledPerformanceOptimizedBitmap(int i, int j)
	{
		float f = 1.0F;
		if (DeviceUtils.isTablet(getContext()))
			f = getContext().getResources().getDisplayMetrics().density;
		return Pair.create(getBitmap((int)((float)i / f), (int)((float)j / f)), Float.valueOf(f));
	}

	public String getSelectedText()
	{
		if (mHasSelection)
			return mLastSelectedText;
		else
			return "";
	}

	public SelectionHandleController getSelectionHandleControllerForTest()
	{
		return mSelectionHandleController;
	}

	public String getTitle()
	{
		if (mNativeContentViewCore != 0)
			return nativeGetTitle(mNativeContentViewCore);
		else
			return null;
	}

	public String getUrl()
	{
		if (mNativeContentViewCore != 0)
			return nativeGetURL(mNativeContentViewCore);
		else
			return null;
	}

	public boolean getUseDesktopUserAgent()
	{
		if (mNativeContentViewCore != 0)
			return nativeGetUseDesktopUserAgent(mNativeContentViewCore);
		else
			return false;
	}

	public VSyncManager.Listener getVSyncListener(VSyncManager.Provider provider)
	{
		if (mVSyncProvider != null && mVSyncListenerRegistered)
		{
			mVSyncProvider.unregisterVSyncListener(mVSyncListener);
			mVSyncListenerRegistered = false;
		}
		mVSyncProvider = provider;
		mVSyncListener = new VSyncManager.Listener() {

			public void onVSync(long l)
			{
				animateIfNecessary(l);
				if (mDidSignalVSyncUsingInputEvent)
				{
					TraceEvent.instant("ContentViewCore::onVSync ignored");
					mDidSignalVSyncUsingInputEvent = false;
				} else
				if (mNativeContentViewCore != 0)
				{
					nativeOnVSync(mNativeContentViewCore, l);
					return;
				}
			}

			public void updateVSync(long l, long l1)
			{
				if (mNativeContentViewCore != 0)
					nativeUpdateVSyncParameters(mNativeContentViewCore, l, l1);
			}

		};
		if (mVSyncSubscriberCount > 0)
		{
			provider.registerVSyncListener(mVSyncListener);
			mVSyncListenerRegistered = true;
		}
		return mVSyncListener;
	}

	public ViewAndroidDelegate getViewAndroidDelegate()
	{
		return new ViewAndroidDelegate() {

			static final boolean $assertionsDisabled = false;

			public View acquireAnchorView()
			{
				View view = new View(getContext());
				mContainerView.addView(view);
				return view;
			}

			public void releaseAnchorView(View view)
			{
				mContainerView.removeView(view);
			}

			public void setAnchorViewPosition(View view, float f, float f1, float f2, float f3)
			{
				if (!$assertionsDisabled && view.getParent() != mContainerView)
					throw new AssertionError();
				float f4 = (float)DeviceDisplayInfo.create(getContext()).getDIPScale();
				int i = Math.round(f * f4);
				int j = Math.round(mRenderCoordinates.getContentOffsetYPix() + f1 * f4);
				if (mContainerView instanceof FrameLayout)
				{
					int i1 = Math.round(f2 * f4);
					if (i1 + i > mContainerView.getWidth())
						i1 = mContainerView.getWidth() - i;
					android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(i1, Math.round(f3 * f4));
					layoutparams.leftMargin = i;
					layoutparams.topMargin = j;
					view.setLayoutParams(layoutparams);
					return;
				}
				if (mContainerView instanceof AbsoluteLayout)
				{
					int k = i + mRenderCoordinates.getScrollXPixInt();
					int l = j + mRenderCoordinates.getScrollYPixInt();
					view.setLayoutParams(new android.widget.AbsoluteLayout.LayoutParams((int)f2, (int)(f3 * f4), k, l));
					return;
				} else
				{
					Log.e("ContentViewCore", (new StringBuilder()).append("Unknown layout ").append(mContainerView.getClass().getName()).toString());
					return;
				}
			}

//			static 
//			{
////				boolean flag;
////				if (!org/chromium/content/browser/ContentViewCore.desiredAssertionStatus())
////					flag = true;
////				else
////					flag = false;
////				$assertionsDisabled = false;
//			}
		};
	}

	public int getViewportHeightPix()
	{
		return mViewportHeightPix;
	}

	public int getViewportSizeOffsetHeightPix()
	{
		return mViewportSizeOffsetHeightPix;
	}

	public int getViewportSizeOffsetWidthPix()
	{
		return mViewportSizeOffsetWidthPix;
	}

	public int getViewportWidthPix()
	{
		return mViewportWidthPix;
	}

	public void goBack()
	{
		if (mNativeContentViewCore != 0)
			nativeGoBack(mNativeContentViewCore);
	}

	public void goForward()
	{
		if (mNativeContentViewCore != 0)
			nativeGoForward(mNativeContentViewCore);
	}

	public void goToNavigationIndex(int i)
	{
		if (mNativeContentViewCore != 0)
			nativeGoToNavigationIndex(mNativeContentViewCore, i);
	}

	public void goToOffset(int i)
	{
		if (mNativeContentViewCore != 0)
			nativeGoToOffset(mNativeContentViewCore, i);
	}

	public boolean hasFixedPageScale()
	{
		return mRenderCoordinates.hasFixedPageScale();
	}

	public boolean hasFocus()
	{
		return mContainerView.hasFocus();
	}

	void hideSelectActionBar()
	{
		if (mActionMode != null)
		{
			mActionMode.finish();
			mActionMode = null;
		}
	}

	public void initialize(ViewGroup viewgroup, InternalAccessDelegate internalaccessdelegate, int i, WindowAndroid windowandroid, int j)
	{
		mHardwareAccelerated = hasHardwareAcceleration(mContext);
		mContainerView = viewgroup;
		int k;
		int l;
		String s;
		if (windowandroid != null)
			k = windowandroid.getNativePointer();
		else
			k = 0;
		l = 0;
		if (k != 0)
		{
			mViewAndroid = new ViewAndroid(windowandroid, getViewAndroidDelegate());
			l = mViewAndroid.getNativePointer();
		}
		mNativeContentViewCore = nativeInit(mHardwareAccelerated, i, l, k);
		mContentSettings = new ContentSettings(this, mNativeContentViewCore);
		initializeContainerView(internalaccessdelegate, j);
		mAccessibilityInjector = AccessibilityInjector.newInstance(this);
		if ((new File("/system/lib/libwebcryptotz.so")).exists())
		{
			mWebCryptoJavascriptInterface = WebCryptoJavascriptInterface.newInstance(this);
			Log.w("ContentViewCore", "mWebCryptoJavascriptInterface new instance");
		} else
		{
			Log.w("ContentViewCore", "TZ based NfWebcrpyto is missing from /system/lib/libwebcryptotz.so");
		}
		s = "Web View";
//		if (org.chromium.content.R.string.accessibility_content_view == 0)
//			Log.w("ContentViewCore", "Setting contentDescription to 'Web View' as no value was specified.");
//		else
//			s = mContext.getResources().getString(org.chromium.content.R.string.accessibility_content_view);
		mContainerView.setContentDescription(s);
		mWebContentsObserver = new WebContentsObserverAndroid(this) {

			public void didFinishLoad(long l1, String s1, boolean flag)
			{
				if (flag)
					getContentViewClient().onPageFinished(s1);
			}

			public void didStartLoading(String s1)
			{
				hidePopupDialog();
				resetGestureDetectors();
			}

		};
		mPid = nativeGetCurrentRenderProcessId(mNativeContentViewCore);
	}

	public void invokeZoomPicker()
	{
		mZoomControlsDelegate.invokeZoomPicker();
	}

	public boolean isAlive()
	{
		return mNativeContentViewCore != 0;
	}

	public boolean isCrashed()
	{
		if (mNativeContentViewCore == 0)
			return false;
		else
			return nativeCrashed(mNativeContentViewCore);
	}

	public boolean isDeviceAccessibilityScriptInjectionEnabled()
	{
		boolean flag = true;
		if (!mContentSettings.getJavaScriptEnabled())
			return false;
		int i;
		if (getContext().checkCallingOrSelfPermission("android.permission.INTERNET") != 0)
			return true;
		
		Field field = null;//android/provider/Settings$Secure.getField("ACCESSIBILITY_SCRIPT_INJECTION");
		field.setAccessible(true);
		String s = null;//(String)field.get(null);
		ContentResolver contentresolver = getContext().getContentResolver();
		if (mAccessibilityScriptInjectionObserver == null)
		{
			ContentObserver contentobserver = new ContentObserver(new Handler()) {
				public void onChange(boolean flag1, Uri uri)
				{
					setAccessibilityState(mAccessibilityManager.isEnabled());
				}
			};
			contentresolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(s), false, contentobserver);
			mAccessibilityScriptInjectionObserver = contentobserver;
		}
		i = android.provider.Settings.Secure.getInt(contentresolver, s, 0);

		return flag;
//		IllegalAccessException illegalaccessexception;
//		illegalaccessexception;
//		return false;
//		NoSuchFieldException nosuchfieldexception;
//		nosuchfieldexception;
//		return false;
	}

	public boolean isInjectingAccessibilityScript()
	{
		return mAccessibilityInjector.accessibilityIsAvailable();
	}

	public boolean isReady()
	{
		return nativeIsRenderWidgetHostViewReady(mNativeContentViewCore);
	}

	public boolean isSelectActionBarShowing()
	{
		return mActionMode != null;
	}

	public boolean isSelectionEditable()
	{
		if (mHasSelection)
			return mSelectionEditable;
		else
			return false;
	}

	public boolean isShowingInterstitialPage()
	{
		if (mNativeContentViewCore == 0)
			return false;
		else
			return nativeIsShowingInterstitialPage(mNativeContentViewCore);
	}

	public boolean isVideoPlaying(int i)
	{
		if (mNativeContentViewCore != 0)
			return nativeIsVideoPlaying(mNativeContentViewCore, i);
		else
			return false;
	}

	public void loadUrl(LoadUrlParams loadurlparams)
	{
		if (mNativeContentViewCore == 0)
		{
			return;
		} else
		{
			nativeLoadUrl(mNativeContentViewCore, loadurlparams.mUrl, loadurlparams.mLoadUrlType, loadurlparams.mTransitionType, loadurlparams.mUaOverrideOption, loadurlparams.getExtraHeadersString(), loadurlparams.mPostData, loadurlparams.mBaseUrlForDataUrl, loadurlparams.mVirtualUrlForDataUrl, loadurlparams.mCanLoadLocalResources);
			return;
		}
	}

	public boolean needsReload()
	{
		return mNativeContentViewCore != 0 && nativeNeedsReload(mNativeContentViewCore);
	}

	public void onAccessibilityStateChanged(boolean flag)
	{
		setAccessibilityState(flag);
	}

	public void onActivityPause()
	{
		TraceEvent.begin();
		hidePopupDialog();
		nativeOnHide(mNativeContentViewCore);
		TraceEvent.end();
	}

	public void onActivityResume()
	{
		nativeOnShow(mNativeContentViewCore);
		setAccessibilityState(mAccessibilityManager.isEnabled());
	}

	public void onAttachedToWindow()
	{
		mAttachedToWindow = true;
		if (mNativeContentViewCore != 0)
		{
			if (!$assertionsDisabled && mPid != nativeGetCurrentRenderProcessId(mNativeContentViewCore))
				throw new AssertionError();
			ChildProcessLauncher.bindAsHighPriority(mPid);
			ChildProcessLauncher.removeInitialBinding(mPid);
		}
		setAccessibilityState(mAccessibilityManager.isEnabled());
	}

	public boolean onCheckIsTextEditor()
	{
		return mImeAdapter.hasTextInputType();
	}

	public void onConfigurationChanged(Configuration configuration)
	{
		TraceEvent.begin();
		if (configuration.keyboard != 1)
		{
			mImeAdapter.attach(nativeGetNativeImeAdapter(mNativeContentViewCore), ImeAdapter.getTextInputTypeNone(), -1, -1);
			((InputMethodManager)getContext().getSystemService("input_method")).restartInput(mContainerView);
		}
		mContainerViewInternals.super_onConfigurationChanged(configuration);
		mNeedUpdateOrientationChanged = true;
		TraceEvent.end();
	}

	public InputConnection onCreateInputConnection(EditorInfo editorinfo)
	{
		if (!mImeAdapter.hasTextInputType())
			editorinfo.imeOptions = 0x2000000;
		mInputConnection = mAdapterInputConnectionFactory.get(mContainerView, mImeAdapter, editorinfo);
		return mInputConnection;
	}

	public void onDetachedFromWindow()
	{
		mAttachedToWindow = false;
		if (mNativeContentViewCore != 0)
		{
			if (!$assertionsDisabled && mPid != nativeGetCurrentRenderProcessId(mNativeContentViewCore))
				throw new AssertionError();
			ChildProcessLauncher.unbindAsHighPriority(mPid);
		}
		setInjectedAccessibility(false);
		hidePopupDialog();
		mZoomControlsDelegate.dismissZoomPicker();
		unregisterAccessibilityContentObserver();
	}

	public void onFocusChanged(boolean flag)
	{
		if (!flag)
			getContentViewClient().onImeStateChangeRequested(false);
		if (mNativeContentViewCore != 0)
			nativeSetFocus(mNativeContentViewCore, flag);
	}

	public boolean onGenericMotionEvent(MotionEvent motionevent)
	{
	    return true;
//		if ((2 & motionevent.getSource()) == 0) goto _L2; else goto _L1
//_L1:
//		motionevent.getAction();
//		JVM INSTR tableswitch 8 8: default 32
//	//	               8 43;
//		   goto _L2 _L3
//_L2:
//		return mContainerViewInternals.super_onGenericMotionEvent(motionevent);
//_L3:
//		nativeSendMouseWheelEvent(mNativeContentViewCore, motionevent.getEventTime(), motionevent.getX(), motionevent.getY(), motionevent.getAxisValue(9));
//		mContainerView.removeCallbacks(mFakeMouseMoveRunnable);
//		mFakeMouseMoveRunnable = new Runnable() {
//
//			public void run()
//			{
//				onHoverEvent(eventFakeMouseMove);
//			}
//
//			
//			{
//				this$0 = ContentViewCore.this;
//				eventFakeMouseMove = motionevent;
//				super();
//			}
//		};
//		mContainerView.postDelayed(mFakeMouseMoveRunnable, 250L);
//		return true;
	}

	public void onHide()
	{
		hidePopupDialog();
		setInjectedAccessibility(false);
		Log.d("ContentViewCore", "onhide called for ContentViewCore");
		mWebCryptoJavascriptInterface.close();
		nativeOnHide(mNativeContentViewCore);
	}

	public boolean onHoverEvent(MotionEvent motionevent)
	{
		TraceEvent.begin("onHoverEvent");
		mContainerView.removeCallbacks(mFakeMouseMoveRunnable);
		if (mBrowserAccessibilityManager != null)
			return mBrowserAccessibilityManager.onHoverEvent(motionevent);
		if (mNativeContentViewCore != 0)
			nativeSendMouseMoveEvent(mNativeContentViewCore, motionevent.getEventTime(), motionevent.getX(), motionevent.getY());
		TraceEvent.end("onHoverEvent");
		return true;
	}

	public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityevent)
	{
		int i;
		int j;
		boolean flag;
label0:
		{
			accessibilityevent.setClassName(getClass().getName());
			accessibilityevent.setScrollX(mRenderCoordinates.getScrollXPixInt());
			accessibilityevent.setScrollY(mRenderCoordinates.getScrollYPixInt());
			i = Math.max(0, mRenderCoordinates.getMaxHorizontalScrollPixInt());
			j = Math.max(0, mRenderCoordinates.getMaxVerticalScrollPixInt());
			if (i <= 0)
			{
				flag = false;
				if (j <= 0)
					break label0;
			}
			flag = true;
		}
		accessibilityevent.setScrollable(flag);
		if (android.os.Build.VERSION.SDK_INT >= 15)
		{
			accessibilityevent.setMaxScrollX(i);
			accessibilityevent.setMaxScrollY(j);
		}
	}

	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilitynodeinfo)
	{
		mAccessibilityInjector.onInitializeAccessibilityNodeInfo(accessibilitynodeinfo);
	}

	public boolean onKeyUp(int i, KeyEvent keyevent)
	{
		if (mPopupZoomer.isShowing() && i == 4)
		{
			mPopupZoomer.hide(true);
			return true;
		} else
		{
			return mContainerViewInternals.super_onKeyUp(i, keyevent);
		}
	}

	void onNativeContentViewCoreDestroyed(int i)
	{
		if (!$assertionsDisabled && i != mNativeContentViewCore)
		{
			throw new AssertionError();
		} else
		{
			mNativeContentViewCore = 0;
			return;
		}
	}

	public void onOverdrawBottomHeightChanged(int i)
	{
		if (mOverdrawBottomHeightPix != i)
		{
			mOverdrawBottomHeightPix = i;
			if (mNativeContentViewCore != 0)
			{
				nativeWasResized(mNativeContentViewCore);
				return;
			}
		}
	}

	public void onPhysicalBackingSizeChanged(int i, int j)
	{
		if (mPhysicalBackingWidthPix != i || mPhysicalBackingHeightPix != j)
		{
			mPhysicalBackingWidthPix = i;
			mPhysicalBackingHeightPix = j;
			if (mNativeContentViewCore != 0)
			{
				nativeWasResized(mNativeContentViewCore);
				return;
			}
		}
	}

	public void onShow()
	{
		nativeOnShow(mNativeContentViewCore);
		setAccessibilityState(mAccessibilityManager.isEnabled());
	}

	public void onSizeChanged(int i, int j, int k, int l)
	{
		if (getViewportWidthPix() == i && getViewportHeightPix() == j)
			return;
		mViewportWidthPix = i;
		mViewportHeightPix = j;
		if (mNativeContentViewCore != 0)
			nativeWasResized(mNativeContentViewCore);
		updateAfterSizeChanged();
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		undoScrollFocusedEditableNodeIntoViewIfNeeded(false);
		return mContentViewGestureHandler.onTouchEvent(motionevent);
	}

	public void onVisibilityChanged(View view, int i)
	{
		if (i != 0)
			mZoomControlsDelegate.dismissZoomPicker();
	}

	public boolean performAccessibilityAction(int i, Bundle bundle)
	{
		if (mAccessibilityInjector.supportsAccessibilityAction(i))
			return mAccessibilityInjector.performAccessibilityAction(i, bundle);
		else
			return false;
	}

	public void reload()
	{
		mAccessibilityInjector.addOrRemoveAccessibilityApisIfNecessary();
		mWebCryptoJavascriptInterface.addOrRemoveWebCryptoJavascriptInterface();
		if (mNativeContentViewCore != 0)
			nativeReload(mNativeContentViewCore);
	}

	public void removeJavascriptInterface(String s)
	{
		mJavaScriptInterfaces.remove(s);
		if (mNativeContentViewCore != 0)
			nativeRemoveJavascriptInterface(mNativeContentViewCore, s);
	}

	public void scrollBy(int i, int j)
	{
		if (mNativeContentViewCore != 0)
			nativeScrollBy(mNativeContentViewCore, System.currentTimeMillis(), 0.0F, 0.0F, i, j, false);
	}

	public void scrollTo(int i, int j)
	{
		if (mNativeContentViewCore != 0)
		{
			float f = mRenderCoordinates.getScrollXPix();
			float f1 = mRenderCoordinates.getScrollYPix();
			float f2 = (float)i - f;
			float f3 = (float)j - f1;
			if (f2 != 0.0F || f3 != 0.0F)
			{
				long l = System.currentTimeMillis();
				nativeScrollBegin(mNativeContentViewCore, l, f, f1);
				nativeScrollBy(mNativeContentViewCore, l, f, f1, f2, f3, false);
				nativeScrollEnd(mNativeContentViewCore, l);
				return;
			}
		}
	}

	public void selectPopupMenuItems(int ai[])
	{
		if (mNativeContentViewCore != 0)
			nativeSelectPopupMenuItems(mNativeContentViewCore, ai);
	}

	public boolean sendGesture(int i, long l, int j, int k, boolean flag, Bundle bundle)
	{
		if (offerGestureToEmbedder(i))
			return false;
		if (mNativeContentViewCore == 0)
			return false;
		updateTextHandlesForGesture(i);
		updateGestureStateListener(i, bundle);
		if (flag && isVSyncNotificationEnabled())
		{
			if (!$assertionsDisabled && i != 7 && i != 12)
				throw new AssertionError();
			mDidSignalVSyncUsingInputEvent = true;
		}
		switch (i)
		{
		default:
			return false;

		case 0: // '\0'
			nativeShowPressState(mNativeContentViewCore, l, j, k);
			return true;

		case 14: // '\016'
			nativeShowPressCancel(mNativeContentViewCore, l, j, k);
			return true;

		case 1: // '\001'
			nativeDoubleTap(mNativeContentViewCore, l, j, k);
			return true;

		case 2: // '\002'
			nativeSingleTap(mNativeContentViewCore, l, j, k, false);
			return true;

		case 3: // '\003'
			handleTapOrPress(l, j, k, 0, bundle.getBoolean("ShowPress", false));
			return true;

		case 4: // '\004'
			nativeSingleTapUnconfirmed(mNativeContentViewCore, l, j, k);
			return true;

		case 5: // '\005'
			handleTapOrPress(l, j, k, 1, false);
			return true;

		case 15: // '\017'
			handleTapOrPress(l, j, k, 2, false);
			return true;

		case 6: // '\006'
			nativeScrollBegin(mNativeContentViewCore, l, j, k);
			return true;

		case 7: // '\007'
			int i1 = bundle.getInt("Distance X");
			int j1 = bundle.getInt("Distance Y");
			nativeScrollBy(mNativeContentViewCore, l, j, k, i1, j1, flag);
			return true;

		case 8: // '\b'
			nativeScrollEnd(mNativeContentViewCore, l);
			return true;

		case 9: // '\t'
			nativeFlingStart(mNativeContentViewCore, l, j, k, bundle.getInt("Velocity X", 0), bundle.getInt("Velocity Y", 0));
			return true;

		case 10: // '\n'
			nativeFlingCancel(mNativeContentViewCore, l);
			return true;

		case 11: // '\013'
			nativePinchBegin(mNativeContentViewCore, l, j, k);
			return true;

		case 12: // '\f'
			nativePinchBy(mNativeContentViewCore, l, j, k, bundle.getFloat("Delta", 0.0F), flag);
			return true;

		case 13: // '\r'
			nativePinchEnd(mNativeContentViewCore, l);
			return true;
		}
	}

	public boolean sendTouchEvent(long l, int i, TouchPoint atouchpoint[])
	{
		if (mNativeContentViewCore != 0)
			return nativeSendTouchEvent(mNativeContentViewCore, l, i, atouchpoint);
		else
			return false;
	}

	public void setAccessibilityState(boolean flag)
	{
		boolean flag1 = false;
		boolean flag2 = false;
		if (flag)
			if (isDeviceAccessibilityScriptInjectionEnabled())
			{
				flag1 = true;
			} else
			{
				flag2 = true;
				flag1 = false;
			}
		setInjectedAccessibility(flag1);
		setNativeAccessibilityState(flag2);
	}

	public void setAdapterInputConnectionFactory(org.chromium.content.browser.input.ImeAdapter.AdapterInputConnectionFactory adapterinputconnectionfactory)
	{
		mAdapterInputConnectionFactory = adapterinputconnectionfactory;
	}

	public void setBrowserAccessibilityManager(BrowserAccessibilityManager browseraccessibilitymanager)
	{
		mBrowserAccessibilityManager = browseraccessibilitymanager;
	}

	public void setContentViewClient(ContentViewClient contentviewclient)
	{
		if (contentviewclient == null)
		{
			throw new IllegalArgumentException("The client can't be null.");
		} else
		{
			mContentViewClient = contentviewclient;
			return;
		}
	}

	public void setDownloadDelegate(ContentViewDownloadDelegate contentviewdownloaddelegate)
	{
		mDownloadDelegate = contentviewdownloaddelegate;
	}

	public void setGestureStateListener(GestureStateListener gesturestatelistener)
	{
		mGestureStateListener = gesturestatelistener;
	}

	public void setInjectedAccessibility(boolean flag)
	{
		mAccessibilityInjector.addOrRemoveAccessibilityApisIfNecessary();
		mAccessibilityInjector.setScriptEnabled(flag);
	}

	public void setNativeAccessibilityState(boolean flag)
	{
		if (android.os.Build.VERSION.SDK_INT >= 16)
			nativeSetAccessibilityEnabled(mNativeContentViewCore, flag);
	}

	public void setUpdateFrameInfoListener(UpdateFrameInfoListener updateframeinfolistener)
	{
		mUpdateFrameInfoListener = updateframeinfolistener;
	}

	public void setUseDesktopUserAgent(boolean flag, boolean flag1)
	{
		if (mNativeContentViewCore != 0)
			nativeSetUseDesktopUserAgent(mNativeContentViewCore, flag, flag1);
	}

	void setVSyncNotificationEnabled(boolean flag)
	{
//		boolean flag1 = true;
//		if (!isVSyncNotificationEnabled() && flag)
//			mDidSignalVSyncUsingInputEvent = false;
//		int i;
//		if (mVSyncProvider != null)
//			if (!mVSyncListenerRegistered && flag)
//			{
//				mVSyncProvider.registerVSyncListener(mVSyncListener);
//				mVSyncListenerRegistered = flag1;
//			} else
//			if (mVSyncSubscriberCount == flag1 && !flag)
//			{
//				if (!$assertionsDisabled && !mVSyncListenerRegistered)
//					throw new AssertionError();
//				mVSyncProvider.unregisterVSyncListener(mVSyncListener);
//				mVSyncListenerRegistered = false;
//			}
//		i = mVSyncSubscriberCount;
//		if (!flag)
//			flag1 = -1;
//		mVSyncSubscriberCount = flag1 + i;
//		if (!$assertionsDisabled && mVSyncSubscriberCount < 0)
//			throw new AssertionError();
//		else
//			return;
	}

	public void setViewportSizeOffset(int i, int j)
	{
		if (i != mViewportSizeOffsetWidthPix || j != mViewportSizeOffsetHeightPix)
		{
			mViewportSizeOffsetWidthPix = i;
			mViewportSizeOffsetHeightPix = j;
			if (mNativeContentViewCore != 0)
				nativeWasResized(mNativeContentViewCore);
		}
	}

	public void setZoomControlsDelegate(ZoomControlsDelegate zoomcontrolsdelegate)
	{
		mZoomControlsDelegate = zoomcontrolsdelegate;
	}

	public void showImeIfNeeded()
	{
		if (mNativeContentViewCore != 0)
			nativeShowImeIfNeeded(mNativeContentViewCore);
	}

	public void showInterstitialPage(String s, InterstitialPageDelegateAndroid interstitialpagedelegateandroid)
	{
		if (mNativeContentViewCore == 0)
		{
			return;
		} else
		{
			nativeShowInterstitialPage(mNativeContentViewCore, s, interstitialpagedelegateandroid.getNative());
			return;
		}
	}

	public void stopCurrentAccessibilityNotifications()
	{
		mAccessibilityInjector.onPageLostFocus();
	}

	public void stopLoading()
	{
		if (mNativeContentViewCore != 0)
			nativeStopLoading(mNativeContentViewCore);
	}

	public boolean supportsAccessibilityAction(int i)
	{
		return mAccessibilityInjector.supportsAccessibilityAction(i);
	}

	void updateGestureStateListener(int i, Bundle bundle)
	{
		if (mGestureStateListener == null)
			return;
		switch (i)
		{
		case 12: // '\f'
		default:
			return;

		case 9: // '\t'
			mGestureStateListener.onFlingStartGesture(bundle.getInt("Velocity X", 0), bundle.getInt("Velocity Y", 0));
			return;

		case 11: // '\013'
			mGestureStateListener.onPinchGestureStart();
			return;

		case 13: // '\r'
			mGestureStateListener.onPinchGestureEnd();
			return;

		case 10: // '\n'
			mGestureStateListener.onFlingCancelGesture();
			return;
		}
	}

	public void updateMultiTouchZoomSupport(boolean flag)
	{
		mZoomManager.updateMultiTouchSupport(flag);
	}

	public void updateTopControlsState(boolean flag, boolean flag1, boolean flag2)
	{
		nativeUpdateTopControlsState(mNativeContentViewCore, flag, flag1, flag2);
	}

	public boolean zoomIn()
	{
		if (!canZoomIn())
			return false;
		else
			return zoomByDelta(1.25F);
	}

	public boolean zoomOut()
	{
		if (!canZoomOut())
			return false;
		else
			return zoomByDelta(0.8F);
	}

	public boolean zoomReset()
	{
		if (!canZoomOut())
			return false;
		else
			return zoomByDelta(mRenderCoordinates.getMinPageScaleFactor() / mRenderCoordinates.getPageScaleFactor());
	}

	static 
	{
//		boolean flag;
//		if (!org/chromium/content/browser/ContentViewCore.desiredAssertionStatus())
//			flag = true;
//		else
//			flag = false;
		//$assertionsDisabled = false;
	}

















/*
	static ActionMode access$2202(ContentViewCore contentviewcore, ActionMode actionmode)
	{
		contentviewcore.mActionMode = actionmode;
		return actionmode;
	}

*/











/*
	static boolean access$302(ContentViewCore contentviewcore, boolean flag)
	{
		contentviewcore.mDidSignalVSyncUsingInputEvent = flag;
		return flag;
	}

*/






}
