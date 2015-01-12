// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.app;

import android.util.Log;
import org.chromium.content.common.*;

// Referenced classes of package org.chromium.content.app:
//			NativeLibraries

public class LibraryLoader
{

	static final boolean $assertionsDisabled = false;
	private static final String TAG = "LibraryLoader";
	private static boolean sInitialized = false;
	private static boolean sLoaded = false;
	private static final Object sLock = new Object();

	public LibraryLoader()
	{
	}

	public static void ensureInitialized()
		throws ProcessInitException
	{
//label0:
//		{
//			synchronized (sLock)
//			{
//				if (!sInitialized)
//					break label0;
//			}
//			return;
//		}
//		loadAlreadyLocked();
//		initializeAlreadyLocked(CommandLine.getJavaSwitchesOrNull());
//		obj;
//		JVM INSTR monitorexit ;
//		return;
//		exception;
//		obj;
//		JVM INSTR monitorexit ;
//		throw exception;
	}

	static void initialize(String as[])
		throws ProcessInitException
	{
//		synchronized (sLock)
//		{
//			initializeAlreadyLocked(as);
//		}
//		return;
//		exception;
//		obj;
//		JVM INSTR monitorexit ;
//		throw exception;
	}

	private static void initializeAlreadyLocked(String as[])
		throws ProcessInitException
	{
		if (sInitialized)
			return;
		int i = nativeLibraryLoaded(as);
		if (i != 0)
		{
			Log.e("LibraryLoader", "error calling nativeLibraryLoaded");
			throw new ProcessInitException(i);
		} else
		{
			sInitialized = true;
			CommandLine.enableNativeProxy();
			TraceEvent.setEnabledToMatchNative();
			return;
		}
	}

	private static void loadAlreadyLocked()
		throws ProcessInitException
	{
//		UnsatisfiedLinkError unsatisfiedlinkerror;
//		if (sLoaded)
//			break MISSING_BLOCK_LABEL_123;
//		if (!$assertionsDisabled && sInitialized)
//			throw new AssertionError();
//		String as[];
//		int i;
//		int j;
//		try
//		{
//			as = NativeLibraries.libraries;
//			i = as.length;
//		}
//		// Misplaced declaration of an exception variable
//		catch (UnsatisfiedLinkError unsatisfiedlinkerror)
//		{
//			throw new ProcessInitException(5, unsatisfiedlinkerror);
//		}
//		j = 0;
//_L2:
//		if (j >= i)
//			break; /* Loop/switch isn't completed */
//		String s = as[j];
//		Log.i("LibraryLoader", (new StringBuilder()).append("loading: ").append(s).toString());
//		System.loadLibrary(s);
//		Log.i("LibraryLoader", (new StringBuilder()).append("loaded: ").append(s).toString());
//		j++;
//		if (true) goto _L2; else goto _L1
//_L1:
//		sLoaded = true;
	}

	public static void loadNow()
		throws ProcessInitException
	{
//		synchronized (sLock)
//		{
//			loadAlreadyLocked();
//		}
//		return;
//		exception;
//		obj;
//		JVM INSTR monitorexit ;
//		throw exception;
	}

	private static native int nativeLibraryLoaded(String as[]);

	public static void setLibraryToLoad(String s)
	{
	}

//	static 
//	{
//		boolean flag;
//		if (!org/chromium/content/app/LibraryLoader.desiredAssertionStatus())
//			flag = true;
//		else
//			flag = false;
//		$assertionsDisabled = flag;
//	}
}
