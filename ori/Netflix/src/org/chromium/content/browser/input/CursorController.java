// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.input;


// Referenced classes of package org.chromium.content.browser.input:
//			HandleView

interface CursorController
	extends android.view.ViewTreeObserver.OnTouchModeChangeListener
{

	public abstract void beforeStartUpdatingPosition(HandleView handleview);

	public abstract void hide();

	public abstract boolean isShowing();

	public abstract void onDetached();

	public abstract void updatePosition(HandleView handleview, int i, int j);
}
