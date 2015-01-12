// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import java.io.File;

// Referenced classes of package org.chromium.base:
//			ContextTypes

public abstract class PathUtils
{

	private static String sDataDirectorySuffix;
	private static String sWebappCacheDirectory;
	private static String sWebappDirectorySuffix;

	private PathUtils()
	{
	}

	public static String getCacheDirectory(Context context)
	{
		if (ContextTypes.getInstance().getType(context) == 1)
			return context.getCacheDir().getPath();
		if (sWebappDirectorySuffix == null || sWebappCacheDirectory == null)
			throw new IllegalStateException("setWebappDirectoryInfo must be called before getCacheDirectory");
		else
			return (new File(context.getDir(sWebappDirectorySuffix, 0), sWebappCacheDirectory)).getPath();
	}

	public static String getDataDirectory(Context context)
	{
		if (sDataDirectorySuffix == null)
			throw new IllegalStateException("setDataDirectorySuffix must be called before getDataDirectory");
		else
			return context.getDir(sDataDirectorySuffix, 0).getPath();
	}

	private static String getDownloadsDirectory(Context context)
	{
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
	}

	public static String getExternalStorageDirectory()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	private static String getNativeLibraryDirectory(Context context)
	{
		ApplicationInfo applicationinfo = context.getApplicationInfo();
		if ((0x80 & applicationinfo.flags) != 0 || (1 & applicationinfo.flags) == 0)
			return applicationinfo.nativeLibraryDir;
		else
			return "/system/lib/";
	}

	public static void setPrivateDataDirectorySuffix(String s)
	{
		sDataDirectorySuffix = s;
	}

	public static void setWebappDirectoryInfo(String s, String s1)
	{
		sWebappDirectorySuffix = s;
		sWebappCacheDirectory = s1;
	}
}
