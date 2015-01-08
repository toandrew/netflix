// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.*;
import android.util.Log;
import android.view.Surface;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.chromium.content.common.IChildProcessCallback;
import org.chromium.content.common.ProcessInitException;

// Referenced classes of package org.chromium.content.app:
//			LibraryLoader, ContentMain

public class ChildProcessService extends Service
{

	private static final String MAIN_THREAD_NAME = "ChildProcessMain";
	private static final String TAG = "ChildProcessService";
	private static AtomicReference sContext = new AtomicReference(null);
	private final org.chromium.content.common.IChildProcessService.Stub mBinder = new org.chromium.content.common.IChildProcessService.Stub() {

		static final boolean $assertionsDisabled;
		final ChildProcessService this$0;

		public int setupConnection(Bundle bundle, IChildProcessCallback ichildprocesscallback)
		{
			mCallback = ichildprocesscallback;
			Thread thread = mMainThread;
			thread;
			JVM INSTR monitorenter ;
			if (mCommandLineParams == null)
				mCommandLineParams = bundle.getStringArray("com.google.android.apps.chrome.extra.command_line");
			if (!$assertionsDisabled && mCommandLineParams == null)
				throw new AssertionError();
			break MISSING_BLOCK_LABEL_77;
			Exception exception;
			exception;
			thread;
			JVM INSTR monitorexit ;
			throw exception;
			mCpuCount = bundle.getInt("com.google.android.apps.chrome.extra.cpu_count");
			mCpuFeatures = bundle.getLong("com.google.android.apps.chrome.extra.cpu_features");
			if (!$assertionsDisabled && mCpuCount <= 0)
				throw new AssertionError();
			mFileIds = new ArrayList();
			mFileFds = new ArrayList();
			int i = 0;
_L2:
			ParcelFileDescriptor parcelfiledescriptor = (ParcelFileDescriptor)bundle.getParcelable((new StringBuilder()).append("com.google.android.apps.chrome.extra.extraFile_").append(i).append("_fd").toString());
			if (parcelfiledescriptor != null)
				break MISSING_BLOCK_LABEL_218;
			mMainThread.notifyAll();
			thread;
			JVM INSTR monitorexit ;
			return Process.myPid();
			mFileFds.add(parcelfiledescriptor);
			String s = (new StringBuilder()).append("com.google.android.apps.chrome.extra.extraFile_").append(i).append("_id").toString();
			mFileIds.add(Integer.valueOf(bundle.getInt(s)));
			i++;
			if (true) goto _L2; else goto _L1
_L1:
		}

		static 
		{
			boolean flag;
			if (!org/chromium/content/app/ChildProcessService.desiredAssertionStatus())
				flag = true;
			else
				flag = false;
			$assertionsDisabled = flag;
		}

			
			{
				this$0 = ChildProcessService.this;
				super();
			}
	};
	private IChildProcessCallback mCallback;
	private String mCommandLineParams[];
	private int mCpuCount;
	private long mCpuFeatures;
	private ArrayList mFileFds;
	private ArrayList mFileIds;
	private boolean mLibraryInitialized;
	private Thread mMainThread;

	public ChildProcessService()
	{
		mLibraryInitialized = false;
	}

	private void establishSurfaceTexturePeer(int i, Object obj, int j, int k)
	{
		if (mCallback != null) goto _L2; else goto _L1
_L1:
		Log.e("ChildProcessService", "No callback interface has been provided.");
_L3:
		return;
_L2:
		boolean flag;
		Surface surface;
		flag = false;
		if (obj instanceof Surface)
			surface = (Surface)obj;
		else
		if (obj instanceof SurfaceTexture)
		{
			surface = new Surface((SurfaceTexture)obj);
			flag = true;
		} else
		{
			Log.e("ChildProcessService", (new StringBuilder()).append("Not a valid surfaceObject: ").append(obj).toString());
			return;
		}
		mCallback.establishSurfacePeer(i, surface, j, k);
		if (flag)
		{
			surface.release();
			return;
		}
		  goto _L3
		RemoteException remoteexception;
		remoteexception;
		Log.e("ChildProcessService", (new StringBuilder()).append("Unable to call establishSurfaceTexturePeer: ").append(remoteexception).toString());
		if (!flag) goto _L3; else goto _L4
_L4:
		surface.release();
		return;
		Exception exception;
		exception;
		if (flag)
			surface.release();
		throw exception;
	}

	static Context getContext()
	{
		return (Context)sContext.get();
	}

	private Surface getViewSurface(int i)
	{
		if (mCallback == null)
		{
			Log.e("ChildProcessService", "No callback interface has been provided.");
			return null;
		}
		Surface surface;
		try
		{
			surface = mCallback.getViewSurface(i);
		}
		catch (RemoteException remoteexception)
		{
			Log.e("ChildProcessService", (new StringBuilder()).append("Unable to call establishSurfaceTexturePeer: ").append(remoteexception).toString());
			return null;
		}
		return surface;
	}

	private static native void nativeExitChildProcess();

	private static native void nativeInitChildProcess(Context context, ChildProcessService childprocessservice, int ai[], int ai1[], int i, long l);

	private native void nativeShutdownMainThread();

	public IBinder onBind(Intent intent)
	{
		stopSelf();
		synchronized (mMainThread)
		{
			mCommandLineParams = intent.getStringArrayExtra("com.google.android.apps.chrome.extra.command_line");
			mMainThread.notifyAll();
		}
		return mBinder;
		exception;
		thread;
		JVM INSTR monitorexit ;
		throw exception;
	}

	public void onCreate()
	{
		Log.i("ChildProcessService", (new StringBuilder()).append("Creating new ChildProcessService pid=").append(Process.myPid()).toString());
		if (sContext.get() != null)
			Log.e("ChildProcessService", "ChildProcessService created again in process!");
		sContext.set(this);
		super.onCreate();
		mMainThread = new Thread(new Runnable() {

			static final boolean $assertionsDisabled;
			final ChildProcessService this$0;

			public void run()
			{
				LibraryLoader.loadNow();
				Thread thread = mMainThread;
				thread;
				JVM INSTR monitorenter ;
				while (mCommandLineParams == null) 
					mMainThread.wait();
				break MISSING_BLOCK_LABEL_113;
				Exception exception;
				exception;
				thread;
				JVM INSTR monitorexit ;
				ProcessInitException processinitexception;
				try
				{
					throw exception;
				}
				catch (InterruptedException interruptedexception)
				{
					Log.w("ChildProcessService", (new StringBuilder()).append("ChildProcessMain startup failed: ").append(interruptedexception).toString());
					return;
				}
				catch (ProcessInitException processinitexception1)
				{
					Log.w("ChildProcessService", (new StringBuilder()).append("ChildProcessMain startup failed: ").append(processinitexception1).toString());
				}
				break MISSING_BLOCK_LABEL_112;
				processinitexception;
				Log.e("ChildProcessService", "Failed to load native library, exiting child process", processinitexception);
				return;
				return;
				thread;
				JVM INSTR monitorexit ;
				LibraryLoader.initialize(mCommandLineParams);
				Thread thread1 = mMainThread;
				thread1;
				JVM INSTR monitorenter ;
				mLibraryInitialized = true;
				mMainThread.notifyAll();
				while (mFileIds == null) 
					mMainThread.wait();
				break MISSING_BLOCK_LABEL_188;
				Exception exception1;
				exception1;
				thread1;
				JVM INSTR monitorexit ;
				throw exception1;
				thread1;
				JVM INSTR monitorexit ;
				int ai[];
				int ai1[];
				if (!$assertionsDisabled && mFileIds.size() != mFileFds.size())
					throw new AssertionError();
				ai = new int[mFileIds.size()];
				ai1 = new int[mFileFds.size()];
				int i = 0;
_L2:
				if (i >= mFileIds.size())
					break; /* Loop/switch isn't completed */
				ai[i] = ((Integer)mFileIds.get(i)).intValue();
				ai1[i] = ((ParcelFileDescriptor)mFileFds.get(i)).detachFd();
				i++;
				if (true) goto _L2; else goto _L1
_L1:
				ContentMain.initApplicationContext(((Context)ChildProcessService.sContext.get()).getApplicationContext());
				ChildProcessService.nativeInitChildProcess(((Context)ChildProcessService.sContext.get()).getApplicationContext(), ChildProcessService.this, ai, ai1, mCpuCount, mCpuFeatures);
				ContentMain.start();
				ChildProcessService.nativeExitChildProcess();
				return;
			}

			static 
			{
				boolean flag;
				if (!org/chromium/content/app/ChildProcessService.desiredAssertionStatus())
					flag = true;
				else
					flag = false;
				$assertionsDisabled = flag;
			}

			
			{
				this$0 = ChildProcessService.this;
				super();
			}
		}, "ChildProcessMain");
		mMainThread.start();
	}

	public void onDestroy()
	{
		Log.i("ChildProcessService", (new StringBuilder()).append("Destroying ChildProcessService pid=").append(Process.myPid()).toString());
		super.onDestroy();
		if (mCommandLineParams == null)
			return;
		Thread thread = mMainThread;
		thread;
		JVM INSTR monitorenter ;
		Exception exception;
		try
		{
			while (!mLibraryInitialized) 
				mMainThread.wait();
		}
		catch (InterruptedException interruptedexception) { }
		finally { }
		thread;
		JVM INSTR monitorexit ;
		nativeShutdownMainThread();
		return;
		thread;
		JVM INSTR monitorexit ;
		throw exception;
	}



/*
	static IChildProcessCallback access$002(ChildProcessService childprocessservice, IChildProcessCallback ichildprocesscallback)
	{
		childprocessservice.mCallback = ichildprocesscallback;
		return ichildprocesscallback;
	}

*/





/*
	static String[] access$202(ChildProcessService childprocessservice, String as[])
	{
		childprocessservice.mCommandLineParams = as;
		return as;
	}

*/



/*
	static int access$302(ChildProcessService childprocessservice, int i)
	{
		childprocessservice.mCpuCount = i;
		return i;
	}

*/



/*
	static long access$402(ChildProcessService childprocessservice, long l)
	{
		childprocessservice.mCpuFeatures = l;
		return l;
	}

*/



/*
	static ArrayList access$502(ChildProcessService childprocessservice, ArrayList arraylist)
	{
		childprocessservice.mFileIds = arraylist;
		return arraylist;
	}

*/



/*
	static ArrayList access$602(ChildProcessService childprocessservice, ArrayList arraylist)
	{
		childprocessservice.mFileFds = arraylist;
		return arraylist;
	}

*/


/*
	static boolean access$702(ChildProcessService childprocessservice, boolean flag)
	{
		childprocessservice.mLibraryInitialized = flag;
		return flag;
	}

*/


}
