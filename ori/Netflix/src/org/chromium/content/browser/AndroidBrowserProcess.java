// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.util.Log;
import org.chromium.content.app.ContentMain;
import org.chromium.content.app.LibraryLoader;
import org.chromium.content.common.ProcessInitException;

// Referenced classes of package org.chromium.content.browser:
//			PepperPluginManager, ResourceExtractor, DeviceUtils

public class AndroidBrowserProcess
{

	static final boolean $assertionsDisabled = false;
	public static final int MAX_RENDERERS_LIMIT = 9;
	public static final int MAX_RENDERERS_SINGLE_PROCESS = 0;
	private static final String TAG = "BrowserProcessMain";
	private static boolean sInitialized = false;

	public AndroidBrowserProcess()
	{
	}

	private static String getPlugins(Context context)
	{
		return PepperPluginManager.getPlugins(context);
	}

	public static boolean init(Context context, int i)
		throws ProcessInitException
	{
		if (!$assertionsDisabled && i < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && i > 9)
			throw new AssertionError();
		if (sInitialized)
			return false;
		sInitialized = true;
		Log.i("BrowserProcessMain", (new StringBuilder()).append("Initializing chromium process, renderers=").append(i).toString());
		ResourceExtractor resourceextractor = ResourceExtractor.get(context);
		resourceextractor.startExtractingResources();
		LibraryLoader.ensureInitialized();
		DeviceUtils.addDeviceSpecificUserAgentSwitch(context);
		Context context1 = context.getApplicationContext();
		resourceextractor.waitForCompletion();
		String s;
		int j;
		if (nativeIsPluginEnabled())
			s = getPlugins(context);
		else
			s = null;
		nativeSetCommandLineFlags(i, s);
		ContentMain.initApplicationContext(context1);
		j = ContentMain.start();
		if (j > 0)
			throw new ProcessInitException(j);
		else
			return true;
	}

	public static void initChromiumBrowserProcessForTests(Context context)
	{
		ResourceExtractor resourceextractor = ResourceExtractor.get(context);
		resourceextractor.startExtractingResources();
		resourceextractor.waitForCompletion();
		nativeSetCommandLineFlags(1, null);
	}

	private static native boolean nativeIsOfficialBuild();

	private static native boolean nativeIsPluginEnabled();

	private static native void nativeSetCommandLineFlags(int i, String s);

//	static 
//	{
//		$assertionsDisabled = false;
//	}
}
