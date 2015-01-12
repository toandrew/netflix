// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui.gfx;

import android.graphics.SurfaceTexture;

class SurfaceTextureListener
	implements android.graphics.SurfaceTexture.OnFrameAvailableListener
{

	static final boolean $assertionsDisabled;
	private int mNativeSurfaceTextureListener;

	private SurfaceTextureListener(int i)
	{
		mNativeSurfaceTextureListener = 0;
		if (!$assertionsDisabled && i == 0)
		{
			throw new AssertionError();
		} else
		{
			mNativeSurfaceTextureListener = i;
			return;
		}
	}

	private static SurfaceTextureListener create(int i)
	{
		return new SurfaceTextureListener(i);
	}

	private native void nativeDestroy(int i);

	private native void nativeFrameAvailable(int i);

	protected void finalize()
		throws Throwable
	{
		nativeDestroy(mNativeSurfaceTextureListener);
		super.finalize();
		return;
	}

	public void onFrameAvailable(SurfaceTexture surfacetexture)
	{
		nativeFrameAvailable(mNativeSurfaceTextureListener);
	}

	static 
	{
//		boolean flag;
//		if (!org/chromium/ui/gfx/SurfaceTextureListener.desiredAssertionStatus())
//			flag = true;
//		else
//			flag = false;
		$assertionsDisabled = false;
	}
}
