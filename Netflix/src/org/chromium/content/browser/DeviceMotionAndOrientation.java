// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.hardware.*;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
//import com.google.common.collect.ImmutableSet;
//import com.google.common.collect.Sets;
import java.util.*;
import org.chromium.base.WeakContext;

class DeviceMotionAndOrientation
	implements SensorEventListener
{
	static interface SensorManagerProxy
	{

		public abstract boolean registerListener(SensorEventListener sensoreventlistener, int i, int j, Handler handler);

		public abstract void unregisterListener(SensorEventListener sensoreventlistener, int i);
	}

	static class SensorManagerProxyImpl
		implements SensorManagerProxy
	{

		private final SensorManager mSensorManager;

		public boolean registerListener(SensorEventListener sensoreventlistener, int i, int j, Handler handler)
		{
			List list = mSensorManager.getSensorList(i);
			if (list.isEmpty())
				return false;
			else
				return mSensorManager.registerListener(sensoreventlistener, (Sensor)list.get(0), j, handler);
		}

		public void unregisterListener(SensorEventListener sensoreventlistener, int i)
		{
			List list = mSensorManager.getSensorList(i);
			if (!list.isEmpty())
				mSensorManager.unregisterListener(sensoreventlistener, (Sensor)list.get(0));
		}

		SensorManagerProxyImpl(SensorManager sensormanager)
		{
			mSensorManager = sensormanager;
		}
	}


	static final int DEVICE_MOTION = 1;
	//static final ImmutableSet DEVICE_MOTION_SENSORS = ImmutableSet.of(Integer.valueOf(1), Integer.valueOf(10), Integer.valueOf(4));
	static final int DEVICE_ORIENTATION = 0;
	//static final ImmutableSet DEVICE_ORIENTATION_SENSORS = ImmutableSet.of(Integer.valueOf(1), Integer.valueOf(2));
	private static final String TAG = "DeviceMotionAndOrientation";
	private static DeviceMotionAndOrientation sSingleton;
	private static Object sSingletonLock = new Object();
	private float mAccelerationIncludingGravityVector[];
	//final Set mActiveSensors = Sets.newHashSet();
	boolean mDeviceMotionIsActive;
	boolean mDeviceOrientationIsActive;
	private Handler mHandler;
	private Object mHandlerLock;
	private float mMagneticFieldVector[];
	private int mNativePtr;
	private Object mNativePtrLock;
	private SensorManagerProxy mSensorManagerProxy;
	private Thread mThread;

	protected DeviceMotionAndOrientation()
	{
		mHandlerLock = new Object();
		mNativePtrLock = new Object();
		mDeviceMotionIsActive = false;
		mDeviceOrientationIsActive = false;
	}

	private Handler getHandler()
	{
		Handler handler;
		synchronized (mHandlerLock)
		{
			if (mHandler == null)
			{
				HandlerThread handlerthread = new HandlerThread("DeviceMotionAndOrientation");
				handlerthread.start();
				mHandler = new Handler(handlerthread.getLooper());
			}
			handler = mHandler;
		}
		return handler;
	}

	static DeviceMotionAndOrientation getInstance()
	{
		DeviceMotionAndOrientation devicemotionandorientation;
		synchronized (sSingletonLock)
		{
			if (sSingleton == null)
				sSingleton = new DeviceMotionAndOrientation();
			devicemotionandorientation = sSingleton;
		}
		return devicemotionandorientation;
	}

	private void getOrientationUsingGetRotationMatrix()
	{
		float af[];
		if (mAccelerationIncludingGravityVector != null && mMagneticFieldVector != null)
			if (SensorManager.getRotationMatrix(af = new float[9], null, mAccelerationIncludingGravityVector, mMagneticFieldVector))
			{
				float af1[] = new float[3];
				SensorManager.getOrientation(af, af1);
				double d;
				for (d = Math.toDegrees(-af1[0]); d < 0.0D; d += 360D);
				double d1;
				for (d1 = Math.toDegrees(-af1[1]); d1 < -180D; d1 += 360D);
				double d2;
				for (d2 = Math.toDegrees(af1[2]); d2 < -90D; d2 += 360D);
				gotOrientation(d, d1, d2);
				return;
			}
	}

	private SensorManagerProxy getSensorManagerProxy()
	{
		if (mSensorManagerProxy != null)
			return mSensorManagerProxy;
		SensorManager sensormanager = (SensorManager)WeakContext.getSystemService("sensor");
		if (sensormanager != null)
			mSensorManagerProxy = new SensorManagerProxyImpl(sensormanager);
		return mSensorManagerProxy;
	}

	private native void nativeGotAcceleration(int i, double d, double d1, double d2);

	private native void nativeGotAccelerationIncludingGravity(int i, double d, double d1, double d2);

	private native void nativeGotOrientation(int i, double d, double d1, double d2);

	private native void nativeGotRotationRate(int i, double d, double d1, double d2);

	private boolean registerForSensorType(int i, int j)
	{
		SensorManagerProxy sensormanagerproxy = getSensorManagerProxy();
		if (sensormanagerproxy == null)
			return false;
		else
			return sensormanagerproxy.registerListener(this, i, j * 1000, getHandler());
	}

	private boolean registerSensors(Iterable iterable, int i, boolean flag)
	{
	    return true;
//		java.util.HashSet hashset = Sets.newHashSet(iterable);
//		hashset.removeAll(mActiveSensors);
//		boolean flag1 = false;
//		Iterator iterator = hashset.iterator();
//		do
//		{
//			Integer integer;
//			boolean flag2;
//label0:
//			{
//				if (iterator.hasNext())
//				{
//					integer = (Integer)iterator.next();
//					flag2 = registerForSensorType(integer.intValue(), i);
//					if (flag2 || !flag)
//						break label0;
//					unregisterSensors(hashset);
//					flag1 = false;
//				}
//				return flag1;
//			}
//			if (flag2)
//			{
//				mActiveSensors.add(integer);
//				flag1 = true;
//			}
//		} while (true);
	}

	private void setEventTypeActive(int i, boolean flag)
	{
		switch (i)
		{
		default:
			return;

		case 0: // '\0'
			mDeviceOrientationIsActive = flag;
			return;

		case 1: // '\001'
			mDeviceMotionIsActive = flag;
			break;
		}
	}

	private void unregisterSensors(Iterable iterable)
	{
		Iterator iterator = iterable.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			Integer integer = (Integer)iterator.next();
//			if (mActiveSensors.contains(integer))
//			{
//				getSensorManagerProxy().unregisterListener(this, integer.intValue());
//				mActiveSensors.remove(integer);
//			}
		} while (true);
	}

	public int getNumberActiveDeviceMotionSensors()
	{
	    return 0;
	    
//		java.util.HashSet hashset = Sets.newHashSet(DEVICE_MOTION_SENSORS);
//		hashset.removeAll(mActiveSensors);
//		return DEVICE_MOTION_SENSORS.size() - hashset.size();
	}

	protected void gotAcceleration(double d, double d1, double d2)
	{
		synchronized (mNativePtrLock)
		{
			if (mNativePtr != 0)
				nativeGotAcceleration(mNativePtr, d, d1, d2);
		}
		return;
	}

	protected void gotAccelerationIncludingGravity(double d, double d1, double d2)
	{
		synchronized (mNativePtrLock)
		{
			if (mNativePtr != 0)
				nativeGotAccelerationIncludingGravity(mNativePtr, d, d1, d2);
		}
		return;
	}

	protected void gotOrientation(double d, double d1, double d2)
	{
		synchronized (mNativePtrLock)
		{
			if (mNativePtr != 0)
				nativeGotOrientation(mNativePtr, d, d1, d2);
		}
		return;
	}

	protected void gotRotationRate(double d, double d1, double d2)
	{
		synchronized (mNativePtrLock)
		{
			if (mNativePtr != 0)
				nativeGotRotationRate(mNativePtr, d, d1, d2);
		}
		return;
	}

	public void onAccuracyChanged(Sensor sensor, int i)
	{
	}

	public void onSensorChanged(SensorEvent sensorevent)
	{
		sensorChanged(sensorevent.sensor.getType(), sensorevent.values);
	}

	void sensorChanged(int i, float af[])
	{
//		i;
//		JVM INSTR lookupswitch 4: default 44
//	//	               1: 45
//	//	               2: 166
//	//	               4: 142
//	//	               10: 118;
//		   goto _L1 _L2 _L3 _L4 _L5
//_L1:
//		return;
//_L2:
//		if (mAccelerationIncludingGravityVector == null)
//			mAccelerationIncludingGravityVector = new float[3];
//		System.arraycopy(af, 0, mAccelerationIncludingGravityVector, 0, mAccelerationIncludingGravityVector.length);
//		if (mDeviceMotionIsActive)
//			gotAccelerationIncludingGravity(mAccelerationIncludingGravityVector[0], mAccelerationIncludingGravityVector[1], mAccelerationIncludingGravityVector[2]);
//		if (mDeviceOrientationIsActive)
//		{
//			getOrientationUsingGetRotationMatrix();
//			return;
//		}
//		continue; /* Loop/switch isn't completed */
//_L5:
//		if (mDeviceMotionIsActive)
//		{
//			gotAcceleration(af[0], af[1], af[2]);
//			return;
//		}
//		continue; /* Loop/switch isn't completed */
//_L4:
//		if (mDeviceMotionIsActive)
//		{
//			gotRotationRate(af[0], af[1], af[2]);
//			return;
//		}
//		if (true) goto _L1; else goto _L3
//_L3:
//		if (mMagneticFieldVector == null)
//			mMagneticFieldVector = new float[3];
//		System.arraycopy(af, 0, mMagneticFieldVector, 0, mMagneticFieldVector.length);
//		if (mDeviceOrientationIsActive)
//		{
//			getOrientationUsingGetRotationMatrix();
//			return;
//		}
//		if (true) goto _L1; else goto _L6
//_L6:
	}

	void setSensorManagerProxy(SensorManagerProxy sensormanagerproxy)
	{
		mSensorManagerProxy = sensormanagerproxy;
	}

	public boolean start(int i, int j, int k)
	{
	    return true;
	    
//		Object obj = mNativePtrLock;
//		obj;
//		JVM INSTR monitorenter ;
//		j;
//		JVM INSTR tableswitch 0 1: default 32
//	//	               0 63
//	//	               1 96;
//		   goto _L1 _L2 _L3
//_L1:
//		Log.e("DeviceMotionAndOrientation", (new StringBuilder()).append("Unknown event type: ").append(j).toString());
//		obj;
//		JVM INSTR monitorexit ;
//		return false;
//_L2:
//		boolean flag = registerSensors(DEVICE_ORIENTATION_SENSORS, k, true);
//_L7:
//		if (!flag) goto _L5; else goto _L4
//_L4:
//		mNativePtr = i;
//		setEventTypeActive(j, true);
//_L5:
//		obj;
//		JVM INSTR monitorexit ;
//		return flag;
//_L3:
//		flag = registerSensors(DEVICE_MOTION_SENSORS, k, false);
//		if (true) goto _L7; else goto _L6
//_L6:
//		Exception exception;
//		exception;
//		obj;
//		JVM INSTR monitorexit ;
//		throw exception;
	}

	public void stop(int i)
	{
	    return;
	    
//		java.util.HashSet hashset = Sets.newHashSet();
//		Object obj = mNativePtrLock;
//		obj;
//		JVM INSTR monitorenter ;
//		i;
//		JVM INSTR tableswitch 0 1: default 36
//	//	               0 65
//	//	               1 139;
//		   goto _L1 _L2 _L3
//_L1:
//		Log.e("DeviceMotionAndOrientation", (new StringBuilder()).append("Unknown event type: ").append(i).toString());
//		obj;
//		JVM INSTR monitorexit ;
//		return;
//_L2:
//		if (mDeviceMotionIsActive)
//			hashset.addAll(DEVICE_MOTION_SENSORS);
//_L5:
//		java.util.HashSet hashset1 = Sets.newHashSet(mActiveSensors);
//		hashset1.removeAll(hashset);
//		unregisterSensors(hashset1);
//		setEventTypeActive(i, false);
//		if (mActiveSensors.isEmpty())
//			mNativePtr = 0;
//		obj;
//		JVM INSTR monitorexit ;
//		return;
//		Exception exception;
//		exception;
//		obj;
//		JVM INSTR monitorexit ;
//		throw exception;
//_L3:
//		if (!mDeviceOrientationIsActive) goto _L5; else goto _L4
//_L4:
//		hashset.addAll(DEVICE_ORIENTATION_SENSORS);
//		  goto _L5
	}

}
