// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.chromium.base.ThreadUtils;
import org.chromium.content.app.PrivilegedProcessService;
import org.chromium.content.app.SandboxedProcessService;
import org.chromium.content.common.IChildProcessCallback;
import org.chromium.content.common.IChildProcessService;

// Referenced classes of package org.chromium.content.browser:
//			ChildProcessConnection, FileDescriptorInfo

public class ChildProcessLauncher
{
	private static class ChildConnectionAllocator
	{

		static final boolean $assertionsDisabled;
		private Class mChildClass;
		private ChildProcessConnection mChildProcessConnections[];
		private final Object mConnectionLock = new Object();
		private ArrayList mFreeConnectionIndices;
		private final boolean mInSandbox;

		public ChildProcessConnection allocate(Context context, ChildProcessConnection.DeathCallback deathcallback)
		{
label0:
			{
				synchronized (mConnectionLock)
				{
					if (!mFreeConnectionIndices.isEmpty())
						break label0;
					Log.w(ChildProcessLauncher.TAG, "Ran out of service.");
				}
				return null;
			}
			int i;
			i = ((Integer)mFreeConnectionIndices.remove(0)).intValue();
			if (!$assertionsDisabled && mChildProcessConnections[i] != null)
				throw new AssertionError();
			break MISSING_BLOCK_LABEL_77;
			exception;
			obj;
			JVM INSTR monitorexit ;
			throw exception;
			ChildProcessConnection childprocessconnection;
			mChildProcessConnections[i] = new ChildProcessConnection(context, i, mInSandbox, deathcallback, mChildClass);
			childprocessconnection = mChildProcessConnections[i];
			obj;
			JVM INSTR monitorexit ;
			return childprocessconnection;
		}

		public void free(ChildProcessConnection childprocessconnection)
		{
			Object obj = mConnectionLock;
			obj;
			JVM INSTR monitorenter ;
			int i;
			i = childprocessconnection.getServiceNumber();
			if (mChildProcessConnections[i] == childprocessconnection)
				break MISSING_BLOCK_LABEL_108;
			if (mChildProcessConnections[i] != null)
				break MISSING_BLOCK_LABEL_93;
			int j = -1;
_L1:
			Log.e(ChildProcessLauncher.TAG, (new StringBuilder()).append("Unable to find connection to free in slot: ").append(i).append(" already occupied by service: ").append(j).toString());
			if (!$assertionsDisabled)
				throw new AssertionError();
			break MISSING_BLOCK_LABEL_158;
			Exception exception;
			exception;
			obj;
			JVM INSTR monitorexit ;
			throw exception;
			j = mChildProcessConnections[i].getServiceNumber();
			  goto _L1
			mChildProcessConnections[i] = null;
			if (!$assertionsDisabled && mFreeConnectionIndices.contains(Integer.valueOf(i)))
				throw new AssertionError();
			mFreeConnectionIndices.add(Integer.valueOf(i));
			obj;
			JVM INSTR monitorexit ;
		}

		public void setServiceClass(Class class1)
		{
			mChildClass = class1;
		}

		static 
		{
			boolean flag;
			if (!org/chromium/content/browser/ChildProcessLauncher.desiredAssertionStatus())
				flag = true;
			else
				flag = false;
			$assertionsDisabled = flag;
		}

		public ChildConnectionAllocator(boolean flag)
		{
			byte byte0;
			if (flag)
				byte0 = 13;
			else
				byte0 = 3;
			mChildProcessConnections = new ChildProcessConnection[byte0];
			mFreeConnectionIndices = new ArrayList(byte0);
			for (int i = 0; i < byte0; i++)
				mFreeConnectionIndices.add(Integer.valueOf(i));

			Object obj;
			if (flag)
				obj = org/chromium/content/app/SandboxedProcessService;
			else
				obj = org/chromium/content/app/PrivilegedProcessService;
			setServiceClass(((Class) (obj)));
			mInSandbox = flag;
		}
	}


	static final boolean $assertionsDisabled = false;
	private static final int CALLBACK_FOR_GPU_PROCESS = 1;
	private static final int CALLBACK_FOR_RENDERER_PROCESS = 2;
	private static final int CALLBACK_FOR_UNKNOWN_PROCESS = 0;
	static final int MAX_REGISTERED_PRIVILEGED_SERVICES = 3;
	static final int MAX_REGISTERED_SANDBOXED_SERVICES = 13;
	private static final int NULL_PROCESS_HANDLE = 0;
	private static final String SWITCH_GPU_PROCESS = "gpu-process";
	private static final String SWITCH_PPAPI_BROKER_PROCESS = "ppapi-broker";
	private static final String SWITCH_PROCESS_TYPE = "type";
	private static final String SWITCH_RENDERER_PROCESS = "renderer";
	private static String TAG = "ChildProcessLauncher";
	private static boolean sConnectionAllocated = false;
	private static SparseIntArray sOomBindingCount = new SparseIntArray();
	private static final ChildConnectionAllocator sPrivilegedChildConnectionAllocator = new ChildConnectionAllocator(false);
	private static final ChildConnectionAllocator sSandboxedChildConnectionAllocator = new ChildConnectionAllocator(true);
	private static Map sServiceMap = new ConcurrentHashMap();
	private static ChildProcessConnection sSpareSandboxedConnection = null;

	public ChildProcessLauncher()
	{
	}

	private static void LogPidWarning(int i, String s)
	{
		if (i > 0 && !nativeIsSingleProcess())
			Log.w(TAG, (new StringBuilder()).append(s).append(", pid=").append(i).toString());
	}

	private static ChildProcessConnection allocateBoundConnection(Context context, String as[], boolean flag)
	{
		ChildProcessConnection childprocessconnection = allocateConnection(context, flag);
		if (childprocessconnection != null)
			childprocessconnection.start(as);
		return childprocessconnection;
	}

	private static ChildProcessConnection allocateConnection(Context context, boolean flag)
	{
		ChildProcessConnection.DeathCallback deathcallback = new ChildProcessConnection.DeathCallback() {

			public void onChildProcessDied(int i)
			{
				ChildProcessLauncher.stop(i);
			}

		};
		sConnectionAllocated = true;
		return getConnectionAllocator(flag).allocate(context, deathcallback);
	}

	static void bindAsHighPriority(int i)
	{
		ChildProcessConnection childprocessconnection = (ChildProcessConnection)sServiceMap.get(Integer.valueOf(i));
		if (childprocessconnection == null)
		{
			LogPidWarning(i, "Tried to bind a non-existent connection");
			return;
		} else
		{
			childprocessconnection.attachAsActive();
			return;
		}
	}

	private static IChildProcessCallback createCallback(int i)
	{
		return new org.chromium.content.common.IChildProcessCallback.Stub(i) {

			final int val$callbackType;

			public void establishSurfacePeer(int j, Surface surface, int k, int l)
			{
				if (callbackType != 1)
				{
					Log.e(ChildProcessLauncher.TAG, "Illegal callback for non-GPU process.");
					return;
				} else
				{
					ChildProcessLauncher.nativeEstablishSurfacePeer(j, surface, k, l);
					return;
				}
			}

			public Surface getViewSurface(int j)
			{
				if (callbackType != 1)
				{
					Log.e(ChildProcessLauncher.TAG, "Illegal callback for non-GPU process.");
					return null;
				} else
				{
					return ChildProcessLauncher.nativeGetViewSurface(j);
				}
			}

			
			{
				callbackType = i;
				super();
			}
		};
	}

	private static void freeConnection(ChildProcessConnection childprocessconnection)
	{
		if (childprocessconnection == null)
		{
			return;
		} else
		{
			getConnectionAllocator(childprocessconnection.isInSandbox()).free(childprocessconnection);
			return;
		}
	}

	public static IChildProcessService getChildService(int i)
	{
		ChildProcessConnection childprocessconnection = (ChildProcessConnection)sServiceMap.get(Integer.valueOf(i));
		if (childprocessconnection != null)
			return childprocessconnection.getService();
		else
			return null;
	}

	private static ChildConnectionAllocator getConnectionAllocator(boolean flag)
	{
		if (flag)
			return sSandboxedChildConnectionAllocator;
		else
			return sPrivilegedChildConnectionAllocator;
	}

	private static String getSwitchValue(String as[], String s)
	{
		if (as != null && s != null)
		{
			String s1 = (new StringBuilder()).append("--").append(s).append("=").toString();
			int i = as.length;
			int j = 0;
			while (j < i) 
			{
				String s2 = as[j];
				if (s2 != null && s2.startsWith(s1))
					return s2.substring(s1.length());
				j++;
			}
		}
		return null;
	}

	static boolean isOomProtected(int i)
	{
		return sOomBindingCount.get(i) > 0;
	}

	private static native void nativeEstablishSurfacePeer(int i, Surface surface, int j, int k);

	private static native Surface nativeGetViewSurface(int i);

	private static native boolean nativeIsSingleProcess();

	private static native void nativeOnChildProcessStarted(int i, int j);

	static void removeInitialBinding(int i)
	{
		ChildProcessConnection childprocessconnection = (ChildProcessConnection)sServiceMap.get(Integer.valueOf(i));
		if (childprocessconnection == null)
		{
			LogPidWarning(i, "Tried to remove a binding for a non-existent connection");
			return;
		} else
		{
			childprocessconnection.removeInitialBinding();
			return;
		}
	}

	public static void setChildProcessClass(Class class1, Class class2)
	{
		if (!$assertionsDisabled && sConnectionAllocated)
		{
			throw new AssertionError();
		} else
		{
			sSandboxedChildConnectionAllocator.setServiceClass(class1);
			sPrivilegedChildConnectionAllocator.setServiceClass(class2);
			return;
		}
	}

	static void start(Context context, String as[], int ai[], int ai1[], boolean aflag[], int i)
	{
		FileDescriptorInfo afiledescriptorinfo[];
		boolean flag;
		String s;
		if (!$assertionsDisabled && (ai.length != ai1.length || ai1.length != aflag.length))
			throw new AssertionError();
		afiledescriptorinfo = new FileDescriptorInfo[ai1.length];
		for (int j = 0; j < ai1.length; j++)
			afiledescriptorinfo[j] = new FileDescriptorInfo(ai[j], ai1[j], aflag[j]);

		if (!$assertionsDisabled && i == 0)
			throw new AssertionError();
		flag = true;
		s = getSwitchValue(as, "type");
		if (!"renderer".equals(s)) goto _L2; else goto _L1
_L1:
		byte byte0 = 2;
_L4:
		org/chromium/content/browser/ChildProcessLauncher;
		JVM INSTR monitorenter ;
		ChildProcessConnection childprocessconnection;
		childprocessconnection = null;
		if (!flag)
			break MISSING_BLOCK_LABEL_140;
		childprocessconnection = sSpareSandboxedConnection;
		sSpareSandboxedConnection = null;
		org/chromium/content/browser/ChildProcessLauncher;
		JVM INSTR monitorexit ;
		if (childprocessconnection == null)
		{
			childprocessconnection = allocateBoundConnection(context, as, flag);
			if (childprocessconnection == null)
			{
				nativeOnChildProcessStarted(i, 0);
				return;
			}
		}
		break; /* Loop/switch isn't completed */
_L2:
		if ("gpu-process".equals(s))
		{
			byte0 = 1;
		} else
		{
			boolean flag1 = "ppapi-broker".equals(s);
			byte0 = 0;
			if (flag1)
			{
				byte0 = 0;
				flag = false;
			}
		}
		if (true) goto _L4; else goto _L3
		Exception exception;
		exception;
		org/chromium/content/browser/ChildProcessLauncher;
		JVM INSTR monitorexit ;
		throw exception;
_L3:
		ChildProcessConnection childprocessconnection1 = childprocessconnection;
		Log.d(TAG, (new StringBuilder()).append("Setting up connection to process: slot=").append(childprocessconnection1.getServiceNumber()).toString());
		ChildProcessConnection.ConnectionCallbacks connectioncallbacks = new ChildProcessConnection.ConnectionCallbacks(i, childprocessconnection1) {

			static final boolean $assertionsDisabled;
			final int val$clientContext;
			final ChildProcessConnection val$connection;

			public void onConnected(int k, int l)
			{
				Log.d(ChildProcessLauncher.TAG, (new StringBuilder()).append("on connect callback, pid=").append(k).append(" context=").append(clientContext).toString());
				if (k != 0)
				{
					ChildProcessLauncher.sOomBindingCount.put(k, l);
					ChildProcessLauncher.sServiceMap.put(Integer.valueOf(k), connection);
				} else
				{
					ChildProcessLauncher.freeConnection(connection);
				}
				ChildProcessLauncher.nativeOnChildProcessStarted(clientContext, k);
			}

			public void onOomBindingAdded(int k)
			{
				if (k != 0)
					ChildProcessLauncher.sOomBindingCount.put(k, 1 + ChildProcessLauncher.sOomBindingCount.get(k));
			}

			public void onOomBindingRemoved(int k)
			{
label0:
				{
					if (k != 0)
					{
						int l = ChildProcessLauncher.sOomBindingCount.get(k, -1);
						if (!$assertionsDisabled && l <= 0)
							throw new AssertionError();
						int i1 = l - 1;
						if (i1 <= 0)
							break label0;
						ChildProcessLauncher.sOomBindingCount.put(k, i1);
					}
					return;
				}
				ChildProcessLauncher.sOomBindingCount.delete(k);
			}

			static 
			{
				boolean flag2;
				if (!org/chromium/content/browser/ChildProcessLauncher.desiredAssertionStatus())
					flag2 = true;
				else
					flag2 = false;
				$assertionsDisabled = flag2;
			}

			
			{
				clientContext = i;
				connection = childprocessconnection;
				super();
			}
		};
		childprocessconnection1.setupConnection(as, afiledescriptorinfo, createCallback(byte0), connectioncallbacks);
		return;
	}

	static void stop(int i)
	{
		Log.d(TAG, (new StringBuilder()).append("stopping child connection: pid=").append(i).toString());
		ChildProcessConnection childprocessconnection = (ChildProcessConnection)sServiceMap.remove(Integer.valueOf(i));
		if (childprocessconnection == null)
		{
			LogPidWarning(i, "Tried to stop non-existent connection");
			return;
		} else
		{
			childprocessconnection.stop();
			freeConnection(childprocessconnection);
			return;
		}
	}

	static void unbindAsHighPriority(int i)
	{
		ChildProcessConnection childprocessconnection = (ChildProcessConnection)sServiceMap.get(Integer.valueOf(i));
		if (childprocessconnection == null)
		{
			LogPidWarning(i, "Tried to unbind non-existent connection");
			return;
		} else
		{
			childprocessconnection.detachAsActive();
			return;
		}
	}

	public static void warmUp(Context context)
	{
		org/chromium/content/browser/ChildProcessLauncher;
		JVM INSTR monitorenter ;
		if (!$assertionsDisabled && ThreadUtils.runningOnUiThread())
			throw new AssertionError();
		break MISSING_BLOCK_LABEL_29;
		Exception exception;
		exception;
		org/chromium/content/browser/ChildProcessLauncher;
		JVM INSTR monitorexit ;
		throw exception;
		if (sSpareSandboxedConnection == null)
			sSpareSandboxedConnection = allocateBoundConnection(context, null, true);
		org/chromium/content/browser/ChildProcessLauncher;
		JVM INSTR monitorexit ;
	}

	static 
	{
		boolean flag;
		if (!org/chromium/content/browser/ChildProcessLauncher.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}







}
