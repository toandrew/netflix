// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content_shell;

import android.content.Context;
import java.io.File;

class ShellLayoutTestUtils
{

	ShellLayoutTestUtils()
	{
	}

	private static String getApplicationFilesDirectory(Context context)
	{
		return context.getFilesDir().getAbsolutePath();
	}
}
