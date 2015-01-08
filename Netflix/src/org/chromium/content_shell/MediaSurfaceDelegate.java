// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content_shell;

import android.graphics.RectF;

public interface MediaSurfaceDelegate
{

	public abstract void onExternalVideoSurfaceRequested(int i);

	public abstract void onGeometryChanged(int i, RectF rectf);

	public abstract void onPaintExternalVideoSurface(int i, int j);
}
