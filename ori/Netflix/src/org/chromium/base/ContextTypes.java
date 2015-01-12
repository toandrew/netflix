// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.content.Context;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContextTypes
{
	private static class ContextTypesHolder
	{

		private static final ContextTypes INSTANCE = new ContextTypes();



		private ContextTypesHolder()
		{
		}
	}


	public static final int CONTEXT_TYPE_NORMAL = 1;
	public static final int CONTEXT_TYPE_WEBAPP = 2;
	private final Map mContextMap;

	private ContextTypes()
	{
		mContextMap = new ConcurrentHashMap();
	}


	public static ContextTypes getInstance()
	{
		return ContextTypesHolder.INSTANCE;
	}

	public static boolean isRunningInWebapp(Context context)
	{
		return getInstance().getType(context) == 2;
	}

	public boolean contains(Context context)
	{
		return mContextMap.containsKey(context);
	}

	public int getType(Context context)
	{
		Integer integer = (Integer)mContextMap.get(context);
		if (integer == null)
			return 1;
		else
			return integer.intValue();
	}

	public void put(Context context, int i)
		throws IllegalArgumentException
	{
		if (i != 1 && i != 2)
		{
			throw new IllegalArgumentException("Wrong context type");
		} else
		{
			mContextMap.put(context, Integer.valueOf(i));
			return;
		}
	}

	public void remove(Context context)
	{
		mContextMap.remove(context);
	}
}
