// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import java.util.*;

public class LoadUrlParams
{

	static final boolean $assertionsDisabled;
	public static int LOAD_TYPE_BROWSER_INITIATED_HTTP_POST;
	public static int LOAD_TYPE_DATA;
	public static int LOAD_TYPE_DEFAULT;
	public static int UA_OVERRIDE_FALSE;
	public static int UA_OVERRIDE_INHERIT;
	public static int UA_OVERRIDE_TRUE;
	String mBaseUrlForDataUrl;
	boolean mCanLoadLocalResources;
	private Map mExtraHeaders;
	int mLoadUrlType;
	byte mPostData[];
	int mTransitionType;
	int mUaOverrideOption;
	final String mUrl;
	String mVirtualUrlForDataUrl;

	public LoadUrlParams(String s)
	{
		if (!$assertionsDisabled && LOAD_TYPE_DEFAULT == LOAD_TYPE_BROWSER_INITIATED_HTTP_POST)
		{
			throw new AssertionError();
		} else
		{
			mUrl = s;
			mLoadUrlType = LOAD_TYPE_DEFAULT;
			mTransitionType = 0;
			mUaOverrideOption = UA_OVERRIDE_INHERIT;
			mPostData = null;
			mBaseUrlForDataUrl = null;
			mVirtualUrlForDataUrl = null;
			return;
		}
	}

	public static LoadUrlParams createLoadDataParams(String s, String s1, boolean flag)
	{
		return createLoadDataParams(s, s1, flag, null);
	}

	public static LoadUrlParams createLoadDataParams(String s, String s1, boolean flag, String s2)
	{
		StringBuilder stringbuilder = new StringBuilder("data:");
		stringbuilder.append(s1);
		if (s2 != null && !s2.isEmpty())
			stringbuilder.append((new StringBuilder()).append(";charset=").append(s2).toString());
		if (flag)
			stringbuilder.append(";base64");
		stringbuilder.append(",");
		stringbuilder.append(s);
		LoadUrlParams loadurlparams = new LoadUrlParams(stringbuilder.toString());
		loadurlparams.setLoadType(LOAD_TYPE_DATA);
		loadurlparams.setTransitionType(1);
		return loadurlparams;
	}

	public static LoadUrlParams createLoadDataParamsWithBaseUrl(String s, String s1, boolean flag, String s2, String s3)
	{
		return createLoadDataParamsWithBaseUrl(s, s1, flag, s2, s3, null);
	}

	public static LoadUrlParams createLoadDataParamsWithBaseUrl(String s, String s1, boolean flag, String s2, String s3, String s4)
	{
		LoadUrlParams loadurlparams = createLoadDataParams(s, s1, flag, s4);
		if (s2 == null || !s2.toLowerCase().startsWith("data:"))
		{
			if (s2 == null)
				s2 = "about:blank";
			loadurlparams.setBaseUrlForDataUrl(s2);
			if (s3 == null)
				s3 = "about:blank";
			loadurlparams.setVirtualUrlForDataUrl(s3);
		}
		return loadurlparams;
	}

	public static LoadUrlParams createLoadHttpPostParams(String s, byte abyte0[])
	{
		LoadUrlParams loadurlparams = new LoadUrlParams(s);
		loadurlparams.setLoadType(LOAD_TYPE_BROWSER_INITIATED_HTTP_POST);
		loadurlparams.setTransitionType(1);
		loadurlparams.setPostData(abyte0);
		return loadurlparams;
	}

	private static void initializeConstants(int i, int j, int k, int l, int i1, int j1)
	{
		LOAD_TYPE_DEFAULT = i;
		LOAD_TYPE_BROWSER_INITIATED_HTTP_POST = j;
		LOAD_TYPE_DATA = k;
		UA_OVERRIDE_INHERIT = l;
		UA_OVERRIDE_FALSE = i1;
		UA_OVERRIDE_TRUE = j1;
	}

	private static native boolean nativeIsDataScheme(String s);

	String getExtraHeadersString()
	{
		if (mExtraHeaders == null)
			return null;
		StringBuilder stringbuilder = new StringBuilder();
		java.util.Map.Entry entry;
		for (Iterator iterator = mExtraHeaders.entrySet().iterator(); iterator.hasNext(); stringbuilder.append((String)entry.getValue()))
		{
			entry = (java.util.Map.Entry)iterator.next();
			if (stringbuilder.length() > 0)
				stringbuilder.append("\n");
			stringbuilder.append(((String)entry.getKey()).toLowerCase());
			stringbuilder.append(":");
		}

		return stringbuilder.toString();
	}

	public int getLoadUrlType()
	{
		return mLoadUrlType;
	}

	public int getTransitionType()
	{
		return mTransitionType;
	}

	public String getUrl()
	{
		return mUrl;
	}

	public boolean isBaseUrlDataScheme()
	{
		if (mBaseUrlForDataUrl == null && mLoadUrlType == LOAD_TYPE_DATA)
			return true;
		else
			return nativeIsDataScheme(mBaseUrlForDataUrl);
	}

	public void setBaseUrlForDataUrl(String s)
	{
		mBaseUrlForDataUrl = s;
	}

	public void setCanLoadLocalResources(boolean flag)
	{
		mCanLoadLocalResources = flag;
	}

	public void setExtraHeaders(Map map)
	{
		mExtraHeaders = map;
	}

	public void setLoadType(int i)
	{
		mLoadUrlType = i;
	}

	public void setOverrideUserAgent(int i)
	{
		mUaOverrideOption = i;
	}

	public void setPostData(byte abyte0[])
	{
		mPostData = abyte0;
	}

	public void setTransitionType(int i)
	{
		mTransitionType = i;
	}

	public void setVirtualUrlForDataUrl(String s)
	{
		mVirtualUrlForDataUrl = s;
	}

	static 
	{
//		boolean flag;
//		if (!org/chromium/content/browser/LoadUrlParams.desiredAssertionStatus())
//			flag = true;
//		else
//			flag = false;
		$assertionsDisabled = false;
	}
}
