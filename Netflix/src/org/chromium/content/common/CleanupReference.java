// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.common;

import android.os.*;
import android.util.Log;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

// Referenced classes of package org.chromium.content.common:
//			TraceEvent

public class CleanupReference extends WeakReference
{

	private static final int ADD_REF = 1;
	private static final boolean DEBUG = false;
	private static final int REMOVE_REF = 2;
	private static final String TAG = "CleanupReference";
	private static Object sCleanupMonitor = new Object();
	private static ReferenceQueue sGcQueue = new ReferenceQueue();
	private static Handler sHandler = new Handler(Looper.getMainLooper()) {

		public void handleMessage(Message message)
		{
			CleanupReference cleanupreference;
			TraceEvent.begin();
			cleanupreference = (CleanupReference)message.obj;
			message.what;
			JVM INSTR tableswitch 1 2: default 36
		//		               1 101
		//		               2 114;
			   goto _L1 _L2 _L3
_L1:
			Log.e("CleanupReference", (new StringBuilder()).append("Bad message=").append(message.what).toString());
_L6:
			Object obj = CleanupReference.sCleanupMonitor;
			obj;
			JVM INSTR monitorenter ;
_L4:
			CleanupReference cleanupreference1 = (CleanupReference)CleanupReference.sGcQueue.poll();
			if (cleanupreference1 == null)
				break; /* Loop/switch isn't completed */
			cleanupreference1.runCleanupTaskInternal();
			  goto _L4
			Exception exception;
			exception;
			obj;
			JVM INSTR monitorexit ;
			throw exception;
_L2:
			CleanupReference.sRefs.add(cleanupreference);
			continue; /* Loop/switch isn't completed */
_L3:
			cleanupreference.runCleanupTaskInternal();
			if (true) goto _L6; else goto _L5
_L5:
			CleanupReference.sCleanupMonitor.notifyAll();
			obj;
			JVM INSTR monitorexit ;
			TraceEvent.end();
			return;
		}

	};
	private static final Thread sReaperThread;
	private static Set sRefs = new HashSet();
	private Runnable mCleanupTask;

	public CleanupReference(Object obj, Runnable runnable)
	{
		super(obj, sGcQueue);
		mCleanupTask = runnable;
		handleOnUiThread(1);
	}

	private void handleOnUiThread(int i)
	{
		Message message = Message.obtain(sHandler, i, this);
		if (Looper.myLooper() == sHandler.getLooper())
		{
			sHandler.handleMessage(message);
			message.recycle();
			return;
		} else
		{
			message.sendToTarget();
			return;
		}
	}

	private void runCleanupTaskInternal()
	{
		sRefs.remove(this);
		if (mCleanupTask != null)
		{
			mCleanupTask.run();
			mCleanupTask = null;
		}
		clear();
	}

	public void cleanupNow()
	{
		handleOnUiThread(2);
	}

	static 
	{
		sReaperThread = new Thread("CleanupReference") {

			public void run()
			{
_L2:
				CleanupReference cleanupreference = (CleanupReference)CleanupReference.sGcQueue.remove();
				synchronized (CleanupReference.sCleanupMonitor)
				{
					Message.obtain(CleanupReference.sHandler, 2, cleanupreference).sendToTarget();
					CleanupReference.sCleanupMonitor.wait(500L);
				}
				continue; /* Loop/switch isn't completed */
				exception1;
				obj;
				JVM INSTR monitorexit ;
				try
				{
					throw exception1;
				}
				catch (Exception exception)
				{
					Log.e("CleanupReference", "Queue remove exception:", exception);
				}
				if (true) goto _L2; else goto _L1
_L1:
			}

		};
		sReaperThread.setDaemon(true);
		sReaperThread.start();
	}





}
