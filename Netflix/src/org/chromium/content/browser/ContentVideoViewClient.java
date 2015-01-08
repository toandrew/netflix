// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.view.View;

// Referenced classes of package org.chromium.content.browser:
//			ContentVideoViewControls

public interface ContentVideoViewClient
{

	public abstract ContentVideoViewControls createControls();

	public abstract View getVideoLoadingProgressView();

	public abstract void onDestroyContentVideoView();

	public abstract void onShowCustomView(View view);
}
