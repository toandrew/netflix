// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui;

import android.view.View;

public interface ViewAndroidDelegate
{

	public abstract View acquireAnchorView();

	public abstract void releaseAnchorView(View view);

	public abstract void setAnchorViewPosition(View view, float f, float f1, float f2, float f3);
}
