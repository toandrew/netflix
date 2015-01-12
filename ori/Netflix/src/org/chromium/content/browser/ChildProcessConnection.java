// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.*;
import android.os.*;
import android.util.Log;
import java.io.IOException;
import org.chromium.base.*;
import org.chromium.content.common.*;

// Referenced classes of package org.chromium.content.browser:
//			FileDescriptorInfo

public class ChildProcessConnection
{
	private class ChildServiceConnection
		implements ServiceConnection
	{

		private final int mBindFlags;
		private boolean mBound;
		private final boolean mProtectsFromOom;
		final ChildProcessConnection this$0;

		boolean bind(String as[])
		{
			if (!mBound)
			{
				Intent intent = createServiceBindIntent();
				if (as != null)
					intent.putExtra("com.google.android.apps.chrome.extra.command_line", as);
				mBound = mContext.bindService(intent, this, mBindFlags);
				if (mBound && mProtectsFromOom && mConnectionCallbacks != null)
					mConnectionCallbacks.onOomBindingAdded(getPid());
			}
			return mBound;
		}

		boolean isBound()
		{
			return mBound;
		}

		public void onServiceConnected(ComponentName componentname, IBinder ibinder)
		{
label0:
			{
				synchronized (mUiThreadLock)
				{
					if (!mServiceConnectComplete)
						break label0;
				}
				return;
			}
			TraceEvent.begin();
			mServiceConnectComplete = true;
			mService = org.chromium.content.common.IChildProcessService.Stub.asInterface(ibinder);
			if (mConnectionParams != null)
				doConnectionSetup();
			TraceEvent.end();
//			obj;
//			JVM INSTR monitorexit ;
//			return;
//			exception;
//			obj;
//			JVM INSTR monitorexit ;
//			throw exception;
		}

		public void onServiceDisconnected(ComponentName componentname)
		{
			boolean flag = true;
			if (!mServiceDisconnected)
			{
				mServiceDisconnected = flag;
				int i = mPID;
				if (mConnectionParams == null)
					flag = false;
				Log.w("ChildProcessConnection", (new StringBuilder()).append("onServiceDisconnected (crash or killed by oom): pid=").append(i).toString());
				stop();
				if (i != 0)
					mDeathCallback.onChildProcessDied(i);
				if (flag && mConnectionCallbacks != null)
				{
					mConnectionCallbacks.onConnected(0, 0);
					return;
				}
			}
		}

		void unbind()
		{
			if (mBound)
			{
				mContext.unbindService(this);
				mBound = false;
				if (mProtectsFromOom && mConnectionCallbacks != null && !mServiceDisconnected)
					mConnectionCallbacks.onOomBindingRemoved(getPid());
			}
		}

		public ChildServiceConnection(int i, boolean flag)
		{
			mBound = false;
			mBindFlags = i;
			mProtectsFromOom = flag;
		}
	}

	static interface ConnectionCallbacks
	{

		public abstract void onConnected(int i, int j);

		public abstract void onOomBindingAdded(int i);

		public abstract void onOomBindingRemoved(int i);
	}

	private static class ConnectionParams
	{

		final IChildProcessCallback mCallback;
		final String mCommandLine[];
		final FileDescriptorInfo mFilesToBeMapped[];

		ConnectionParams(String as[], FileDescriptorInfo afiledescriptorinfo[], IChildProcessCallback ichildprocesscallback)
		{
			mCommandLine = as;
			mFilesToBeMapped = afiledescriptorinfo;
			mCallback = ichildprocesscallback;
		}
	}

	static interface DeathCallback
	{

		public abstract void onChildProcessDied(int i);
	}


	static final boolean $assertionsDisabled = false;
	private static final long DETACH_AS_ACTIVE_HIGH_END_DELAY_MILLIS = 5000L;
	public static final String EXTRA_COMMAND_LINE = "com.google.android.apps.chrome.extra.command_line";
	public static final String EXTRA_CPU_COUNT = "com.google.android.apps.chrome.extra.cpu_count";
	public static final String EXTRA_CPU_FEATURES = "com.google.android.apps.chrome.extra.cpu_features";
	public static final String EXTRA_FILES_FD_SUFFIX = "_fd";
	public static final String EXTRA_FILES_ID_SUFFIX = "_id";
	public static final String EXTRA_FILES_PREFIX = "com.google.android.apps.chrome.extra.extraFile_";
	private static final long REMOVE_INITIAL_BINDING_DELAY_MILLIS = 1000L;
	private static final String TAG = "ChildProcessConnection";
	private int mAttachAsActiveCount;
	private ConnectionCallbacks mConnectionCallbacks;
	private ConnectionParams mConnectionParams;
	private final Context mContext;
	private final DeathCallback mDeathCallback;
	private final boolean mInSandbox;
	private ChildServiceConnection mInitialBinding;
	private int mPID;
	private IChildProcessService mService;
	private final Class mServiceClass;
	private boolean mServiceConnectComplete;
	private boolean mServiceDisconnected;
	private final int mServiceNumber;
	private ChildServiceConnection mStrongBinding;
	private final Object mUiThreadLock = new Object();
	private ChildServiceConnection mWaivedBinding;

	ChildProcessConnection(Context context, int i, boolean flag, DeathCallback deathcallback, Class class1)
	{
		mService = null;
		mServiceConnectComplete = false;
		mServiceDisconnected = false;
		mPID = 0;
		mInitialBinding = null;
		mStrongBinding = null;
		mWaivedBinding = null;
		mAttachAsActiveCount = 0;
		mContext = context;
		mServiceNumber = i;
		mInSandbox = flag;
		mDeathCallback = deathcallback;
		mServiceClass = class1;
		mInitialBinding = new ChildServiceConnection(1, true);
		mStrongBinding = new ChildServiceConnection(65, true);
		mWaivedBinding = new ChildServiceConnection(33, false);
	}

	private Intent createServiceBindIntent()
	{
		Intent intent = new Intent();
		intent.setClassName(mContext, (new StringBuilder()).append(mServiceClass.getName()).append(mServiceNumber).toString());
		intent.setPackage(mContext.getPackageName());
		return intent;
	}

	private void doConnectionSetup()
	{
//		TraceEvent.begin();
//		if (!$assertionsDisabled && (!mServiceConnectComplete || mConnectionParams == null))
//			throw new AssertionError();
//		if (mService == null) goto _L2; else goto _L1
//_L1:
//		Bundle bundle = new Bundle();
//		bundle.putStringArray("com.google.android.apps.chrome.extra.command_line", mConnectionParams.mCommandLine);
//		FileDescriptorInfo afiledescriptorinfo[] = mConnectionParams.mFilesToBeMapped;
//		ParcelFileDescriptor aparcelfiledescriptor[] = new ParcelFileDescriptor[afiledescriptorinfo.length];
//		int i = 0;
//		while (i < afiledescriptorinfo.length) 
//		{
//			if (afiledescriptorinfo[i].mFd == -1)
//			{
//				Log.e("ChildProcessConnection", (new StringBuilder()).append("Invalid FD (id=").append(afiledescriptorinfo[i].mId).append(") for process connection, ").append("aborting connection.").toString());
//				return;
//			}
//			String s = (new StringBuilder()).append("com.google.android.apps.chrome.extra.extraFile_").append(i).append("_id").toString();
//			String s1 = (new StringBuilder()).append("com.google.android.apps.chrome.extra.extraFile_").append(i).append("_fd").toString();
//			if (afiledescriptorinfo[i].mAutoClose)
//				aparcelfiledescriptor[i] = ParcelFileDescriptor.adoptFd(afiledescriptorinfo[i].mFd);
//			else
//				try
//				{
//					aparcelfiledescriptor[i] = ParcelFileDescriptor.fromFd(afiledescriptorinfo[i].mFd);
//				}
//				catch (IOException ioexception1)
//				{
//					Log.e("ChildProcessConnection", "Invalid FD provided for process connection, aborting connection.", ioexception1);
//					return;
//				}
//			bundle.putParcelable(s1, aparcelfiledescriptor[i]);
//			bundle.putInt(s, afiledescriptorinfo[i].mId);
//			i++;
//		}
//		bundle.putInt("com.google.android.apps.chrome.extra.cpu_count", CpuFeatures.getCount());
//		bundle.putLong("com.google.android.apps.chrome.extra.cpu_features", CpuFeatures.getMask());
//		int i1;
//		int j1;
//		ParcelFileDescriptor parcelfiledescriptor;
//		try
//		{
//			mPID = mService.setupConnection(bundle, mConnectionParams.mCallback);
//		}
//		catch (RemoteException remoteexception)
//		{
//			Log.e("ChildProcessConnection", "Failed to setup connection.", remoteexception);
//		}
//		i1 = aparcelfiledescriptor.length;
//		j1 = 0;
//_L3:
//		if (j1 >= i1)
//			break; /* Loop/switch isn't completed */
//		parcelfiledescriptor = aparcelfiledescriptor[j1];
//		if (parcelfiledescriptor == null)
//			break MISSING_BLOCK_LABEL_342;
//		parcelfiledescriptor.close();
//		j1++;
//		if (true) goto _L3; else goto _L2
//		IOException ioexception;
//		ioexception;
//		Log.w("ChildProcessConnection", "Failed to close FD.", ioexception);
//_L2:
//		mConnectionParams = null;
//		if (mConnectionCallbacks != null)
//		{
//			int j;
//			int k;
//			int l;
//			if (mInitialBinding.isBound())
//				j = 1;
//			else
//				j = 0;
//			if (mStrongBinding.isBound())
//				k = 1;
//			else
//				k = 0;
//			l = j + k;
//			mConnectionCallbacks.onConnected(getPid(), l);
//		}
//		TraceEvent.end();
//		return;
	}

	private void onBindFailed()
	{
		mServiceConnectComplete = true;
		if (mConnectionParams != null)
			doConnectionSetup();
	}

	void attachAsActive()
	{
//label0:
//		{
//			synchronized (mUiThreadLock)
//			{
//				if (mService != null)
//					break label0;
//				Log.w("ChildProcessConnection", (new StringBuilder()).append("The connection is not bound for ").append(mPID).toString());
//			}
//			return;
//		}
//		if (mAttachAsActiveCount == 0)
//			mStrongBinding.bind(null);
//		mAttachAsActiveCount = 1 + mAttachAsActiveCount;
//		obj;
//		JVM INSTR monitorexit ;
//		return;
//		exception;
//		obj;
//		JVM INSTR monitorexit ;
//		throw exception;
	}

	void detachAsActive()
	{
//		Runnable runnable = new Runnable() ;
//		long l;
//		if (SysUtils.isLowEndDevice())
//			l = 0L;
//		else
//			l = 5000L;
//		ThreadUtils.postOnUiThreadDelayed(runnable, l);
	}

	int getPid()
	{
		int i;
		synchronized (mUiThreadLock)
		{
			i = mPID;
		}
		return i;
	}

	IChildProcessService getService()
	{
		IChildProcessService ichildprocessservice;
		synchronized (mUiThreadLock)
		{
			ichildprocessservice = mService;
		}
		return ichildprocessservice;
	}

	int getServiceNumber()
	{
		return mServiceNumber;
	}

	boolean isInSandbox()
	{
		return mInSandbox;
	}

	void removeInitialBinding()
	{
//label0:
//		{
//			synchronized (mUiThreadLock)
//			{
//				if (mInitialBinding.isBound())
//					break label0;
//			}
//			return;
//		}
//		obj;
//		JVM INSTR monitorexit ;
//		ThreadUtils.postOnUiThreadDelayed(new Runnable() {
//
//			final ChildProcessConnection this$0;
//
//			public void run()
//			{
//				synchronized (mUiThreadLock)
//				{
//					mInitialBinding.unbind();
//				}
//				return;
//				exception1;
//				obj1;
//				JVM INSTR monitorexit ;
//				throw exception1;
//			}
//
//			
//			{
//				this$0 = ChildProcessConnection.this;
//				super();
//			}
//		}, 1000L);
//		return;
//		exception;
//		obj;
//		JVM INSTR monitorexit ;
//		throw exception;
	}

	void setupConnection(String as[], FileDescriptorInfo afiledescriptorinfo[], IChildProcessCallback ichildprocesscallback, ConnectionCallbacks connectioncallbacks)
	{
//		Object obj = mUiThreadLock;
//		obj;
//		JVM INSTR monitorenter ;
//		TraceEvent.begin();
//		if (!$assertionsDisabled && mConnectionParams != null)
//			throw new AssertionError();
//		break MISSING_BLOCK_LABEL_41;
//		Exception exception;
//		exception;
//		obj;
//		JVM INSTR monitorexit ;
//		throw exception;
//		mConnectionCallbacks = connectioncallbacks;
//		mConnectionParams = new ConnectionParams(as, afiledescriptorinfo, ichildprocesscallback);
//		if (mServiceConnectComplete)
//			doConnectionSetup();
//		TraceEvent.end();
//		obj;
//		JVM INSTR monitorexit ;
	}

	void start(String as[])
	{
//		Object obj = mUiThreadLock;
//		obj;
//		JVM INSTR monitorenter ;
//		TraceEvent.begin();
//		if (!$assertionsDisabled && ThreadUtils.runningOnUiThread())
//			throw new AssertionError();
//		break MISSING_BLOCK_LABEL_35;
//		Exception exception;
//		exception;
//		obj;
//		JVM INSTR monitorexit ;
//		throw exception;
//		if (mInitialBinding.bind(as))
//			break MISSING_BLOCK_LABEL_56;
//		onBindFailed();
//_L2:
//		TraceEvent.end();
//		obj;
//		JVM INSTR monitorexit ;
//		return;
//		mWaivedBinding.bind(null);
//		if (true) goto _L2; else goto _L1
//_L1:
	}

	void stop()
	{
		synchronized (mUiThreadLock)
		{
			mInitialBinding.unbind();
			mStrongBinding.unbind();
			mWaivedBinding.unbind();
			mAttachAsActiveCount = 0;
			if (mService != null)
			{
				mService = null;
				mPID = 0;
			}
			mConnectionParams = null;
			mServiceConnectComplete = false;
		}
		return;
	}

//	static 
//	{
//		boolean flag;
//		if (!org/chromium/content/browser/ChildProcessConnection.desiredAssertionStatus())
//			flag = true;
//		else
//			flag = false;
//		$assertionsDisabled = flag;
//	}







/*
	static int access$1210(ChildProcessConnection childprocessconnection)
	{
		int i = childprocessconnection.mAttachAsActiveCount;
		childprocessconnection.mAttachAsActiveCount = i - 1;
		return i;
	}

*/





/*
	static boolean access$302(ChildProcessConnection childprocessconnection, boolean flag)
	{
		childprocessconnection.mServiceDisconnected = flag;
		return flag;
	}

*/




/*
	static boolean access$502(ChildProcessConnection childprocessconnection, boolean flag)
	{
		childprocessconnection.mServiceConnectComplete = flag;
		return flag;
	}

*/



/*
	static IChildProcessService access$602(ChildProcessConnection childprocessconnection, IChildProcessService ichildprocessservice)
	{
		childprocessconnection.mService = ichildprocessservice;
		return ichildprocessservice;
	}

*/



}
