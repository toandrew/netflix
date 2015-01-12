// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.accessibility;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.*;
import android.view.accessibility.*;
import java.util.ArrayList;
import java.util.List;
import org.chromium.content.browser.ContentViewCore;
import org.chromium.content.browser.RenderCoordinates;

// Referenced classes of package org.chromium.content.browser.accessibility:
//			JellyBeanBrowserAccessibilityManager

public class BrowserAccessibilityManager
{

	private static final String TAG = "BrowserAccessibilityManager";
	private int mAccessibilityFocusId;
	private AccessibilityManager mAccessibilityManager;
	private ContentViewCore mContentViewCore;
	private int mCurrentHoverId;
	private boolean mFocusPageOnLoad;
	private boolean mFrameInfoInitialized;
	private int mNativeObj;
	private RenderCoordinates mRenderCoordinates;
	private final int mTempLocation[] = new int[2];
	private boolean mUserHasTouchExplored;
	private View mView;

	protected BrowserAccessibilityManager(int i, ContentViewCore contentviewcore)
	{
		mNativeObj = i;
		mContentViewCore = contentviewcore;
		mContentViewCore.setBrowserAccessibilityManager(this);
		mAccessibilityFocusId = -1;
		mCurrentHoverId = -1;
		mView = mContentViewCore.getContainerView();
		mRenderCoordinates = mContentViewCore.getRenderCoordinates();
		mAccessibilityManager = (AccessibilityManager)mContentViewCore.getContext().getSystemService("accessibility");
	}

	private void addAccessibilityNodeInfoChild(AccessibilityNodeInfo accessibilitynodeinfo, int i)
	{
		accessibilitynodeinfo.addChild(mView, i);
	}

	private void announceLiveRegionText(String s)
	{
		mView.announceForAccessibility(s);
	}

	private static BrowserAccessibilityManager create(int i, ContentViewCore contentviewcore)
	{
		if (android.os.Build.VERSION.SDK_INT >= 16)
			return new JellyBeanBrowserAccessibilityManager(i, contentviewcore);
		else
			return new BrowserAccessibilityManager(i, contentviewcore);
	}

	private void handleCheckStateChanged(int i)
	{
		sendAccessibilityEvent(i, 1);
	}

	private void handleContentChanged(int i)
	{
		sendAccessibilityEvent(i, 2048);
	}

	private void handleEditableTextChanged(int i)
	{
		sendAccessibilityEvent(i, 2048);
		sendAccessibilityEvent(i, 16);
	}

	private void handleFocusChanged(int i)
	{
		if (mAccessibilityFocusId == i)
		{
			return;
		} else
		{
			mAccessibilityFocusId = i;
			sendAccessibilityEvent(i, 8);
			return;
		}
	}

	private void handleNavigate()
	{
		mAccessibilityFocusId = -1;
		mUserHasTouchExplored = false;
		mFrameInfoInitialized = false;
	}

	private void handlePageLoaded(int i)
	{
		while (mUserHasTouchExplored || !mFocusPageOnLoad) 
			return;
		mAccessibilityFocusId = i;
		sendAccessibilityEvent(i, 8);
	}

	private void handleScrolledToAnchor(int i)
	{
		if (mAccessibilityFocusId == i)
		{
			return;
		} else
		{
			mAccessibilityFocusId = i;
			sendAccessibilityEvent(i, 32768);
			return;
		}
	}

	private void handleTextSelectionChanged(int i)
	{
		sendAccessibilityEvent(i, 2048);
		sendAccessibilityEvent(i, 8192);
	}

	private native void nativeBlur(int i);

	private native void nativeClick(int i, int j);

	private native void nativeFocus(int i, int j);

	private native int nativeGetRootId(int i);

	private native int nativeHitTest(int i, int j, int k);

	private native boolean nativePopulateAccessibilityEvent(int i, AccessibilityEvent accessibilityevent, int j, int k);

	private native boolean nativePopulateAccessibilityNodeInfo(int i, AccessibilityNodeInfo accessibilitynodeinfo, int j);

	private void onNativeObjectDestroyed()
	{
		if (mContentViewCore.getBrowserAccessibilityManager() == this)
			mContentViewCore.setBrowserAccessibilityManager(null);
		mNativeObj = 0;
		mContentViewCore = null;
	}

	private void sendAccessibilityEvent(int i, int j)
	{
		if (mAccessibilityManager.isEnabled() && mNativeObj != 0)
		{
			AccessibilityEvent accessibilityevent = AccessibilityEvent.obtain(j);
			accessibilityevent.setPackageName(mContentViewCore.getContext().getPackageName());
			if (i == nativeGetRootId(mNativeObj))
				i = -1;
			accessibilityevent.setSource(mView, i);
			if (nativePopulateAccessibilityEvent(mNativeObj, accessibilityevent, i, j))
			{
				mContentViewCore.getContainerView().postInvalidate();
				mContentViewCore.getContainerView().requestSendAccessibilityEvent(mView, accessibilityevent);
				return;
			}
		}
	}

	private void setAccessibilityEventBooleanAttributes(AccessibilityEvent accessibilityevent, boolean flag, boolean flag1, boolean flag2, boolean flag3)
	{
		accessibilityevent.setChecked(flag);
		accessibilityevent.setEnabled(flag1);
		accessibilityevent.setPassword(flag2);
		accessibilityevent.setScrollable(flag3);
	}

	private void setAccessibilityEventClassName(AccessibilityEvent accessibilityevent, String s)
	{
		accessibilityevent.setClassName(s);
	}

	private void setAccessibilityEventListAttributes(AccessibilityEvent accessibilityevent, int i, int j)
	{
		accessibilityevent.setCurrentItemIndex(i);
		accessibilityevent.setItemCount(j);
	}

	private void setAccessibilityEventScrollAttributes(AccessibilityEvent accessibilityevent, int i, int j, int k, int l)
	{
		accessibilityevent.setScrollX(i);
		accessibilityevent.setScrollY(j);
		accessibilityevent.setMaxScrollX(k);
		accessibilityevent.setMaxScrollY(l);
	}

	private void setAccessibilityEventSelectionAttrs(AccessibilityEvent accessibilityevent, int i, int j, int k, String s)
	{
		accessibilityevent.setFromIndex(i);
		accessibilityevent.setAddedCount(j);
		accessibilityevent.setItemCount(k);
		accessibilityevent.getText().add(s);
	}

	private void setAccessibilityEventTextChangedAttrs(AccessibilityEvent accessibilityevent, int i, int j, int k, String s, String s1)
	{
		accessibilityevent.setFromIndex(i);
		accessibilityevent.setAddedCount(j);
		accessibilityevent.setRemovedCount(k);
		accessibilityevent.setBeforeText(s);
		accessibilityevent.getText().add(s1);
	}

	private void setAccessibilityNodeInfoBooleanAttributes(AccessibilityNodeInfo accessibilitynodeinfo, int i, boolean flag, boolean flag1, boolean flag2, boolean flag3, boolean flag4, 
			boolean flag5, boolean flag6, boolean flag7, boolean flag8, boolean flag9)
	{
		accessibilitynodeinfo.setCheckable(flag);
		accessibilitynodeinfo.setChecked(flag1);
		accessibilitynodeinfo.setClickable(flag2);
		accessibilitynodeinfo.setEnabled(flag3);
		accessibilitynodeinfo.setFocusable(flag4);
		accessibilitynodeinfo.setFocused(flag5);
		accessibilitynodeinfo.setPassword(flag6);
		accessibilitynodeinfo.setScrollable(flag7);
		accessibilitynodeinfo.setSelected(flag8);
		accessibilitynodeinfo.setVisibleToUser(flag9);
		if (flag4)
			if (flag5)
				accessibilitynodeinfo.addAction(2);
			else
				accessibilitynodeinfo.addAction(1);
		if (mAccessibilityFocusId == i)
		{
			accessibilitynodeinfo.setAccessibilityFocused(true);
			accessibilitynodeinfo.addAction(128);
		} else
		{
			accessibilitynodeinfo.setAccessibilityFocused(false);
			accessibilitynodeinfo.addAction(64);
		}
		if (flag2)
			accessibilitynodeinfo.addAction(16);
	}

	private void setAccessibilityNodeInfoLocation(AccessibilityNodeInfo accessibilitynodeinfo, int i, int j, int k, int l, int i1, int j1, 
			boolean flag)
	{
		Rect rect = new Rect(k, l, k + i1, l + j1);
		if (flag)
			rect.offset(0, (int)mRenderCoordinates.getContentOffsetYPix());
		accessibilitynodeinfo.setBoundsInParent(rect);
		Rect rect1 = new Rect(i, j, i + i1, j + j1);
		rect1.offset(-(int)mRenderCoordinates.getScrollX(), -(int)mRenderCoordinates.getScrollY());
		rect1.left = (int)mRenderCoordinates.fromLocalCssToPix(rect1.left);
		rect1.top = (int)mRenderCoordinates.fromLocalCssToPix(rect1.top);
		rect1.bottom = (int)mRenderCoordinates.fromLocalCssToPix(rect1.bottom);
		rect1.right = (int)mRenderCoordinates.fromLocalCssToPix(rect1.right);
		rect1.offset(0, (int)mRenderCoordinates.getContentOffsetYPix());
		int ai[] = new int[2];
		mView.getLocationOnScreen(ai);
		rect1.offset(ai[0], ai[1]);
		accessibilitynodeinfo.setBoundsInScreen(rect1);
	}

	private void setAccessibilityNodeInfoParent(AccessibilityNodeInfo accessibilitynodeinfo, int i)
	{
		accessibilitynodeinfo.setParent(mView, i);
	}

	private void setAccessibilityNodeInfoStringAttributes(AccessibilityNodeInfo accessibilitynodeinfo, String s, String s1)
	{
		accessibilitynodeinfo.setClassName(s);
		accessibilitynodeinfo.setContentDescription(s1);
	}

	protected AccessibilityNodeInfo createAccessibilityNodeInfo(int i)
	{
		AccessibilityNodeInfo accessibilitynodeinfo;
		if (!mAccessibilityManager.isEnabled() || mNativeObj == 0 || !mFrameInfoInitialized)
		{
			accessibilitynodeinfo = null;
		} else
		{
			int j = nativeGetRootId(mNativeObj);
			if (i == -1)
				i = j;
			if (mAccessibilityFocusId == -1)
				mAccessibilityFocusId = j;
			accessibilitynodeinfo = AccessibilityNodeInfo.obtain(mView);
			accessibilitynodeinfo.setPackageName(mContentViewCore.getContext().getPackageName());
			accessibilitynodeinfo.setSource(mView, i);
			if (!nativePopulateAccessibilityNodeInfo(mNativeObj, accessibilitynodeinfo, i))
				return null;
		}
		return accessibilitynodeinfo;
	}

	protected List findAccessibilityNodeInfosByText(String s, int i)
	{
		return new ArrayList();
	}

	public AccessibilityNodeProvider getAccessibilityNodeProvider()
	{
		return null;
	}

	public void notifyFrameInfoInitialized()
	{
		if (!mFrameInfoInitialized)
		{
			mFrameInfoInitialized = true;
			if (mAccessibilityFocusId != -1)
			{
				sendAccessibilityEvent(mAccessibilityFocusId, 32768);
				return;
			}
		}
	}

	public boolean onHoverEvent(MotionEvent motionevent)
	{
		boolean flag = true;
		if (!mAccessibilityManager.isEnabled() || mNativeObj == 0)
			flag = false;
		else
		if (motionevent.getAction() != 10)
		{
			mUserHasTouchExplored = flag;
			float f = motionevent.getX();
			float f1 = motionevent.getY();
			int i = (int)(mRenderCoordinates.fromPixToLocalCss(f) + mRenderCoordinates.getScrollX());
			int j = (int)(mRenderCoordinates.fromPixToLocalCss(f1) + mRenderCoordinates.getScrollY());
			int k = nativeHitTest(mNativeObj, i, j);
			if (mCurrentHoverId != k)
			{
				sendAccessibilityEvent(mCurrentHoverId, 256);
				sendAccessibilityEvent(k, 128);
				mCurrentHoverId = k;
				return flag;
			}
		}
		return flag;
	}

	protected boolean performAction(int i, int j, Bundle bundle)
	{
	    return true;
//		boolean flag = true;
//		if (mAccessibilityManager.isEnabled() && mNativeObj != 0) goto _L2; else goto _L1
//_L1:
//		flag = false;
//_L9:
//		return flag;
//_L2:
//		j;
//		JVM INSTR lookupswitch 5: default 76
//	//	               1: 132
//	//	               2: 144
//	//	               16: 120
//	//	               64: 78
//	//	               128: 104;
//		   goto _L3 _L4 _L5 _L6 _L7 _L8
//_L3:
//		break; /* Loop/switch isn't completed */
//_L5:
//		break MISSING_BLOCK_LABEL_144;
//_L10:
//		return false;
//_L7:
//		if (mAccessibilityFocusId != i)
//		{
//			mAccessibilityFocusId = i;
//			sendAccessibilityEvent(mAccessibilityFocusId, 32768);
//			return flag;
//		}
//		  goto _L9
//_L8:
//		if (mAccessibilityFocusId == i)
//		{
//			mAccessibilityFocusId = -1;
//			return flag;
//		}
//		  goto _L9
//_L6:
//		nativeClick(mNativeObj, i);
//		  goto _L10
//_L4:
//		nativeFocus(mNativeObj, i);
//		  goto _L10
//		nativeBlur(mNativeObj);
//		  goto _L10
	}
}
