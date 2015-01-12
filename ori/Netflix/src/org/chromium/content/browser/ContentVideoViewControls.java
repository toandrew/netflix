// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.view.View;

public interface ContentVideoViewControls
{
	public static interface Delegate
		extends android.widget.MediaController.MediaPlayerControl
	{
	}


	public abstract void hide();

	public abstract boolean isShowing();

	public abstract void setAnchorView(View view);

	public abstract void setDelegate(Delegate delegate1);

	public abstract void setEnabled(boolean flag);

	public abstract void show();

	public abstract void show(int i);
}
