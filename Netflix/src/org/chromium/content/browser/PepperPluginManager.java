// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.content.Intent;
import android.content.pm.*;
import android.os.Bundle;
import android.util.Log;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.ContextTypes;

// Referenced classes of package org.chromium.content.browser:
//			DeviceUtils

public class PepperPluginManager
{

	private static final String DESCRIPTION = "description";
	private static final String FILENAME = "filename";
	private static final String LOGTAG = "PepperPluginManager";
	private static final String MIMETYPE = "mimetype";
	private static final String NAME = "name";
	public static final String PEPPER_PLUGIN_ACTION = "org.chromium.intent.PEPPERPLUGIN";
	public static final String PEPPER_PLUGIN_ROOT = "/system/lib/pepperplugin/";
	private static final String VERSION = "version";

	public PepperPluginManager()
	{
	}

	private static String getPluginDescription(Bundle bundle)
	{
		String s = bundle.getString("filename");
		String s1;
		if (s != null && !s.isEmpty())
			if ((s1 = bundle.getString("mimetype")) != null && !s1.isEmpty())
			{
				StringBuffer stringbuffer = new StringBuffer("/system/lib/pepperplugin/");
				stringbuffer.append(s);
				String s2 = bundle.getString("name");
				String s3 = bundle.getString("description");
				String s4 = bundle.getString("version");
				if (s2 != null && !s2.isEmpty())
				{
					stringbuffer.append("#");
					stringbuffer.append(s2);
					if (s3 != null && !s3.isEmpty())
					{
						stringbuffer.append("#");
						stringbuffer.append(s3);
						if (s4 != null && !s4.isEmpty())
						{
							stringbuffer.append("#");
							stringbuffer.append(s4);
						}
					}
				}
				stringbuffer.append(';');
				stringbuffer.append(s1);
				return stringbuffer.toString();
			}
		return null;
	}

	public static String getPlugins(Context context)
	{
		StringBuffer stringbuffer;
		PackageManager packagemanager;
		Iterator iterator;
		if (DeviceUtils.isTv(context) && !ContextTypes.isRunningInWebapp(context))
			return null;
		stringbuffer = new StringBuffer();
		packagemanager = context.getPackageManager();
		iterator = packagemanager.queryIntentServices(new Intent("org.chromium.intent.PEPPERPLUGIN"), 132).iterator();
_L2:
		ServiceInfo serviceinfo;
		if (!iterator.hasNext())
			break; /* Loop/switch isn't completed */
		ResolveInfo resolveinfo = (ResolveInfo)iterator.next();
		serviceinfo = resolveinfo.serviceInfo;
		if (serviceinfo == null || serviceinfo.metaData == null || serviceinfo.packageName == null)
		{
			Log.e("PepperPluginManager", (new StringBuilder()).append("Can't get service information from ").append(resolveinfo).toString());
			continue; /* Loop/switch isn't completed */
		}
		PackageInfo packageinfo = packagemanager.getPackageInfo(serviceinfo.packageName, 0);
		if (packageinfo != null && (1 & packageinfo.applicationInfo.flags) != 0)
		{
			Log.i("PepperPluginManager", (new StringBuilder()).append("The given plugin package is preloaded: ").append(serviceinfo.packageName).toString());
			String s = getPluginDescription(serviceinfo.metaData);
			if (s != null)
			{
				if (stringbuffer.length() > 0)
					stringbuffer.append(',');
				stringbuffer.append(s);
			}
		}
		continue; /* Loop/switch isn't completed */
		android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
		namenotfoundexception;
		Log.e("PepperPluginManager", (new StringBuilder()).append("Can't find plugin: ").append(serviceinfo.packageName).toString());
		if (true) goto _L2; else goto _L1
_L1:
		return stringbuffer.toString();
	}
}
