// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.*;
import android.graphics.RectF;
import android.util.Log;
import android.view.KeyEvent;
import java.net.URISyntaxException;

// Referenced classes of package org.chromium.content.browser:
//			SelectActionModeCallback, ContentVideoViewClient

public class ContentViewClient
{

	private static final String TAG = "ContentViewClient";

	public ContentViewClient()
	{
	}

	public ContentVideoViewClient getContentVideoViewClient()
	{
		return null;
	}

	public android.view.ActionMode.Callback getSelectActionModeCallback(Context context, SelectActionModeCallback.ActionHandler actionhandler, boolean flag)
	{
		return new SelectActionModeCallback(context, actionhandler, flag);
	}

	public void onBackgroundColorChanged(int i)
	{
	}

	public void onContextualActionBarHidden()
	{
	}

	public void onContextualActionBarShown()
	{
	}

	public void onExternalVideoSurfaceRequested(int i)
	{
	}

	public void onGeometryChanged(int i, RectF rectf)
	{
	}

	public void onImeEvent()
	{
	}

	public void onImeStateChangeRequested(boolean flag)
	{
	}

	public void onOffsetsForFullscreenChanged(float f, float f1, float f2)
	{
	}

	public void onPageFinished(String s)
	{
	}

	public void onPaintExternalSurface(int i, int j)
	{
	}

	public void onRendererCrash(boolean flag)
	{
	}

	public void onScaleChanged(float f, float f1)
	{
	}

	public void onStartContentIntent(Context context, String s)
	{
		Intent intent;
		try
		{
			intent = Intent.parseUri(s, 1);
		}
		catch (URISyntaxException urisyntaxexception)
		{
			Log.w("ContentViewClient", (new StringBuilder()).append("Bad URI ").append(s).append(": ").append(urisyntaxexception.getMessage()).toString());
			return;
		}
		try
		{
			context.startActivity(intent);
			return;
		}
		catch (ActivityNotFoundException activitynotfoundexception)
		{
			Log.w("ContentViewClient", (new StringBuilder()).append("No application can handle ").append(s).toString());
		}
	}

	public void onUpdateTitle(String s)
	{
	}

	public void onWebMediaPlayerCreated(int i)
	{
	}

	public boolean shouldOverrideKeyEvent(KeyEvent keyevent)
	{
		for (int i = keyevent.getKeyCode(); i == 82 || i == 3 || i == 5 || i == 6 || i == 26 || i == 79 || i == 27 || i == 80 || i == 25 || i == 164 || i == 24 || keyevent.isCtrlPressed() && (i == 61 || i == 51 || i == 134);)
			return true;

		return false;
	}

	public boolean shouldOverrideScroll(float f, float f1, float f2, float f3)
	{
		return false;
	}
}
