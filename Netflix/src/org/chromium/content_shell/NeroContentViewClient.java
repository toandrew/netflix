// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content_shell;

import android.graphics.RectF;
import org.chromium.content.browser.ContentViewClient;

// Referenced classes of package org.chromium.content_shell:
//			MediaSurfaceDelegate

public class NeroContentViewClient extends ContentViewClient
{

	private MediaSurfaceDelegate mDelegate;

	public NeroContentViewClient(MediaSurfaceDelegate mediasurfacedelegate)
	{
		mDelegate = mediasurfacedelegate;
	}

	public void onExternalVideoSurfaceRequested(int i)
	{
		if (mDelegate != null)
			mDelegate.onExternalVideoSurfaceRequested(i);
	}

	public void onGeometryChanged(int i, RectF rectf)
	{
		if (mDelegate != null)
			mDelegate.onGeometryChanged(i, rectf);
	}

	public void onPaintExternalSurface(int i, int j)
	{
		if (mDelegate != null)
			mDelegate.onPaintExternalVideoSurface(i, j);
	}
}
