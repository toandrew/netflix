// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.graphics.Bitmap;
import android.view.View;

public interface PageInfo
{

	public abstract int getBackgroundColor();

	public abstract Bitmap getBitmap();

	public abstract Bitmap getBitmap(int i, int j);

	public abstract String getTitle();

	public abstract String getUrl();

	public abstract View getView();

	public abstract boolean isReadyForSnapshot();
}
