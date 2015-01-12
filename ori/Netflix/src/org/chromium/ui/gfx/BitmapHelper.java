// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui.gfx;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapHelper
{

	public BitmapHelper()
	{
	}

	private static int calculateInSampleSize(android.graphics.BitmapFactory.Options options, int i, int j)
	{
		int k1;
label0:
		{
			int k = options.outHeight;
			int l = options.outWidth;
			int i1 = 1;
			if (k > j || l > i)
			{
				int j1 = Math.round((float)k / (float)j);
				k1 = Math.round((float)l / (float)i);
				if (j1 >= k1)
					break label0;
				i1 = j1;
			}
			return i1;
		}
		return k1;
	}

	public static Bitmap createBitmap(int i, int j)
	{
		return Bitmap.createBitmap(i, j, android.graphics.Bitmap.Config.ARGB_8888);
	}

	private static Bitmap decodeDrawableResource(String s, int i, int j)
	{
		Resources resources = Resources.getSystem();
		int k = resources.getIdentifier(s, null, null);
		android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resources, k, options);
		options.inSampleSize = calculateInSampleSize(options, i, j);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(resources, k, options);
	}
}
