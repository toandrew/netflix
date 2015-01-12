// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import java.util.List;
import java.util.concurrent.FutureTask;
import org.chromium.base.ActivityStatus;
import org.chromium.base.ThreadUtils;

class LocationProvider
{
	private static class LocationProviderImpl
		implements LocationListener, org.chromium.base.ActivityStatus.StateListener
	{

		static final boolean $assertionsDisabled;
		private Context mContext;
		private boolean mIsGpsEnabled;
		private boolean mIsRunning;
		private LocationManager mLocationManager;
		private boolean mShouldRunAfterActivityResume;

		private void ensureLocationManagerCreated()
		{
			if (mLocationManager == null)
			{
				mLocationManager = (LocationManager)mContext.getSystemService("location");
				if (mLocationManager == null)
				{
					Log.e("LocationProvider", "Could not get location manager.");
					return;
				}
			}
		}

		private boolean isOnlyPassiveLocationProviderEnabled()
		{
			List list = mLocationManager.getProviders(true);
			return list != null && list.size() == 1 && ((String)list.get(0)).equals("passive");
		}

		private boolean isRunning()
		{
			return mIsRunning;
		}

		private void registerForLocationUpdates()
		{
			ensureLocationManagerCreated();
			if (!usePassiveOneShotLocation())
			{
				if (!$assertionsDisabled && mIsRunning)
					throw new AssertionError();
				mIsRunning = true;
				try
				{
					Criteria criteria = new Criteria();
					mLocationManager.requestLocationUpdates(0L, 0.0F, criteria, this, Looper.getMainLooper());
					if (mIsGpsEnabled)
					{
						criteria.setAccuracy(1);
						mLocationManager.requestLocationUpdates(0L, 0.0F, criteria, this, Looper.getMainLooper());
						return;
					}
				}
				catch (SecurityException securityexception)
				{
					Log.e("LocationProvider", "Caught security exception registering for location updates from system. This should only happen in DumpRenderTree.");
					return;
				}
				catch (IllegalArgumentException illegalargumentexception)
				{
					Log.e("LocationProvider", "Caught IllegalArgumentException registering for location updates.");
					return;
				}
			}
		}

		private void start(boolean flag)
		{
			if (!mIsRunning && !mShouldRunAfterActivityResume)
				ActivityStatus.registerStateListener(this);
			mIsGpsEnabled = flag;
			if (ActivityStatus.isPaused())
			{
				mShouldRunAfterActivityResume = true;
				return;
			} else
			{
				unregisterFromLocationUpdates();
				registerForLocationUpdates();
				return;
			}
		}

		private void stop()
		{
			unregisterFromLocationUpdates();
			ActivityStatus.unregisterStateListener(this);
			mShouldRunAfterActivityResume = false;
		}

		private void unregisterFromLocationUpdates()
		{
			if (mIsRunning)
			{
				mIsRunning = false;
				mLocationManager.removeUpdates(this);
			}
		}

		private void updateNewLocation(Location location)
		{
			LocationProvider.nativeNewLocationAvailable(location.getLatitude(), location.getLongitude(), (double)location.getTime() / 1000D, location.hasAltitude(), location.getAltitude(), location.hasAccuracy(), location.getAccuracy(), location.hasBearing(), location.getBearing(), location.hasSpeed(), location.getSpeed());
		}

		private boolean usePassiveOneShotLocation()
		{
			if (!isOnlyPassiveLocationProviderEnabled())
				return false;
			Location location = mLocationManager.getLastKnownLocation("passive");
			if (location != null)
				ThreadUtils.runOnUiThread(location. new Runnable() {

					final LocationProviderImpl this$0;
					final Location val$location;

					public void run()
					{
						updateNewLocation(location);
					}

			
			{
				this$0 = final_locationproviderimpl;
				location = Location.this;
				super();
			}
				});
			return true;
		}

		public void onActivityStateChange(int i)
		{
			if (i == 4)
			{
				mShouldRunAfterActivityResume = mIsRunning;
				unregisterFromLocationUpdates();
			} else
			if (i == 3)
			{
				if (!$assertionsDisabled && mIsRunning)
					throw new AssertionError();
				if (mShouldRunAfterActivityResume)
				{
					registerForLocationUpdates();
					return;
				}
			}
		}

		public void onLocationChanged(Location location)
		{
			if (mIsRunning)
				updateNewLocation(location);
		}

		public void onProviderDisabled(String s)
		{
		}

		public void onProviderEnabled(String s)
		{
		}

		public void onStatusChanged(String s, int i, Bundle bundle)
		{
		}

		static 
		{
			boolean flag;
			if (!org/chromium/content/browser/LocationProvider.desiredAssertionStatus())
				flag = true;
			else
				flag = false;
			$assertionsDisabled = flag;
		}





		LocationProviderImpl(Context context)
		{
			mContext = context;
		}
	}


	static final boolean $assertionsDisabled = false;
	private static final String TAG = "LocationProvider";
	private LocationProviderImpl mImpl;

	private LocationProvider(Context context)
	{
		mImpl = new LocationProviderImpl(context);
	}

	static LocationProvider create(Context context)
	{
		return new LocationProvider(context);
	}

	public static native void nativeNewErrorAvailable(String s);

	public static native void nativeNewLocationAvailable(double d, double d1, double d2, boolean flag, double d3, boolean flag1, double d4, boolean flag2, double d5, 
			boolean flag3, double d6);

	public boolean isRunning()
	{
		if (!$assertionsDisabled && Looper.myLooper() != Looper.getMainLooper())
			throw new AssertionError();
		else
			return mImpl.isRunning();
	}

	public boolean start(final boolean gpsEnabled)
	{
		ThreadUtils.runOnUiThread(new FutureTask(new Runnable() {

			final LocationProvider this$0;
			final boolean val$gpsEnabled;

			public void run()
			{
				mImpl.start(gpsEnabled);
			}

			
			{
				this$0 = LocationProvider.this;
				gpsEnabled = flag;
				super();
			}
		}, null));
		return true;
	}

	public void stop()
	{
		ThreadUtils.runOnUiThread(new FutureTask(new Runnable() {

			final LocationProvider this$0;

			public void run()
			{
				mImpl.stop();
			}

			
			{
				this$0 = LocationProvider.this;
				super();
			}
		}, null));
	}

	static 
	{
		boolean flag;
		if (!org/chromium/content/browser/LocationProvider.desiredAssertionStatus())
			flag = true;
		else
			flag = false;
		$assertionsDisabled = flag;
	}

}
