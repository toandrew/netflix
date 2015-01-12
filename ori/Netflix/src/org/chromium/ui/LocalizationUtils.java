// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui;

import android.text.TextUtils;
import java.util.Locale;

public class LocalizationUtils
{

	public static final int LEFT_TO_RIGHT = 2;
	public static final int RIGHT_TO_LEFT = 1;
	public static final int UNKNOWN_DIRECTION;

	private LocalizationUtils()
	{
	}

	public static String getDefaultLocale()
	{
		Locale locale = Locale.getDefault();
		String s = locale.getLanguage();
		String s1 = locale.getCountry();
		if ("iw".equals(s))
			s = "he";
		else
		if ("in".equals(s))
			s = "id";
		else
		if ("tl".equals(s))
			s = "fil";
		if (s1.isEmpty())
			return s;
		else
			return (new StringBuilder()).append(s).append("-").append(s1).toString();
	}

	private static String getDisplayNameForLocale(Locale locale, Locale locale1)
	{
		return locale.getDisplayName(locale1);
	}

	public static int getFirstStrongCharacterDirection(String s)
	{
		return nativeGetFirstStrongCharacterDirection(s);
	}

	private static Locale getJavaLocale(String s, String s1, String s2)
	{
		return new Locale(s, s1, s2);
	}

	public static boolean isRtl()
	{
		return nativeIsRTL();
	}

	public static boolean isSystemLayoutDirectionRtl()
	{
		if (android.os.Build.VERSION.SDK_INT >= 17)
			return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
		else
			return false;
	}

	private static native int nativeGetFirstStrongCharacterDirection(String s);

	private static native boolean nativeIsRTL();
}
