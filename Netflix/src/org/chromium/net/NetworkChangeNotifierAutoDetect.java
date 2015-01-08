// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.net;

import android.content.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import org.chromium.base.ActivityStatus;

public class NetworkChangeNotifierAutoDetect extends BroadcastReceiver
	implements org.chromium.base.ActivityStatus.StateListener
{
	static class ConnectivityManagerDelegate
	{

		private final ConnectivityManager mConnectivityManager;

		boolean activeNetworkExists()
		{
			return mConnectivityManager.getActiveNetworkInfo() != null;
		}

		int getNetworkSubtype()
		{
			return mConnectivityManager.getActiveNetworkInfo().getSubtype();
		}

		int getNetworkType()
		{
			return mConnectivityManager.getActiveNetworkInfo().getType();
		}

		boolean isConnected()
		{
			return mConnectivityManager.getActiveNetworkInfo().isConnected();
		}

		ConnectivityManagerDelegate()
		{
			mConnectivityManager = null;
		}

		ConnectivityManagerDelegate(Context context)
		{
			mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
		}
	}

	private static class NetworkConnectivityIntentFilter extends IntentFilter
	{

		NetworkConnectivityIntentFilter()
		{
			addAction("android.net.conn.CONNECTIVITY_CHANGE");
		}
	}

	public static interface Observer
	{

		public abstract void onConnectionTypeChanged(int i);
	}


	private static final String TAG = "NetworkChangeNotifierAutoDetect";
	private int mConnectionType;
	private ConnectivityManagerDelegate mConnectivityManagerDelegate;
	private final Context mContext;
	private final NetworkConnectivityIntentFilter mIntentFilter = new NetworkConnectivityIntentFilter();
	private final Observer mObserver;
	private boolean mRegistered;

	public NetworkChangeNotifierAutoDetect(Observer observer, Context context)
	{
		mObserver = observer;
		mContext = context.getApplicationContext();
		mConnectivityManagerDelegate = new ConnectivityManagerDelegate(context);
		mConnectionType = getCurrentConnectionType();
		ActivityStatus.registerStateListener(this);
	}

	private void connectionTypeChanged()
	{
		int i = getCurrentConnectionType();
		if (i == mConnectionType)
		{
			return;
		} else
		{
			mConnectionType = i;
			Log.d("NetworkChangeNotifierAutoDetect", (new StringBuilder()).append("Network connectivity changed, type is: ").append(mConnectionType).toString());
			mObserver.onConnectionTypeChanged(i);
			return;
		}
	}

	private void registerReceiver()
	{
		if (!mRegistered)
		{
			mRegistered = true;
			mContext.registerReceiver(this, mIntentFilter);
		}
	}

	private void unregisterReceiver()
	{
		if (mRegistered)
		{
			mRegistered = false;
			mContext.unregisterReceiver(this);
		}
	}

	public void destroy()
	{
		unregisterReceiver();
	}

	public int getCurrentConnectionType()
	{
		byte byte0 = 5;
		if (mConnectivityManagerDelegate.activeNetworkExists() && mConnectivityManagerDelegate.isConnected()) goto _L2; else goto _L1
_L1:
		byte0 = 6;
_L4:
		return byte0;
_L2:
		switch (mConnectivityManagerDelegate.getNetworkType())
		{
		default:
			return 0;

		case 6: // '\006'
			break;

		case 9: // '\t'
			return 1;

		case 1: // '\001'
			return 2;

		case 0: // '\0'
			switch (mConnectivityManagerDelegate.getNetworkSubtype())
			{
			default:
				return 0;

			case 1: // '\001'
			case 2: // '\002'
			case 4: // '\004'
			case 7: // '\007'
			case 11: // '\013'
				return 3;

			case 3: // '\003'
			case 5: // '\005'
			case 6: // '\006'
			case 8: // '\b'
			case 9: // '\t'
			case 10: // '\n'
			case 12: // '\f'
			case 14: // '\016'
			case 15: // '\017'
				return 4;

			case 13: // '\r'
				break;
			}
			break;
		}
		if (true) goto _L4; else goto _L3
_L3:
	}

	public void onActivityStateChange(int i)
	{
		if (i == 3)
		{
			connectionTypeChanged();
			registerReceiver();
		} else
		if (i == 4)
		{
			unregisterReceiver();
			return;
		}
	}

	public void onReceive(Context context, Intent intent)
	{
		connectionTypeChanged();
	}

	void setConnectivityManagerDelegateForTests(ConnectivityManagerDelegate connectivitymanagerdelegate)
	{
		mConnectivityManagerDelegate = connectivitymanagerdelegate;
	}
}
