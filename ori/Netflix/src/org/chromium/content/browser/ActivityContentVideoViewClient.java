// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

// Referenced classes of package org.chromium.content.browser:
//			ContentVideoViewClient, ContentVideoViewControls

public class ActivityContentVideoViewClient
	implements ContentVideoViewClient
{

	private Activity mActivity;
	private View mView;

	public ActivityContentVideoViewClient(Activity activity)
	{
		mActivity = activity;
	}

	public ContentVideoViewControls createControls()
	{
		return null;
	}

	public View getVideoLoadingProgressView()
	{
		return null;
	}

	public void onDestroyContentVideoView()
	{
		mActivity.getWindow().clearFlags(1024);
		((FrameLayout)mActivity.getWindow().getDecorView()).removeView(mView);
		mView = null;
	}

	public void onShowCustomView(View view)
	{
		mActivity.getWindow().setFlags(1024, 1024);
		mActivity.getWindow().addContentView(view, new android.widget.FrameLayout.LayoutParams(-1, -1, 17));
		mView = view;
	}
}
