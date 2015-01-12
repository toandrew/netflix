// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.media;

import android.content.*;
import android.content.pm.PackageManager;
import android.media.*;
import android.os.Build;
import android.util.Log;

class AudioManagerAndroid
{

	private static final int DEFAULT_FRAME_PER_BUFFER = 256;
	private static final int DEFAULT_SAMPLING_RATE = 44100;
	private static final String TAG = "AudioManagerAndroid";
	private final AudioManager mAudioManager;
	private final Context mContext;
	private boolean mOriginalSpeakerStatus;
	private BroadcastReceiver mReceiver;

	private AudioManagerAndroid(Context context)
	{
		mContext = context;
		mAudioManager = (AudioManager)mContext.getSystemService("audio");
	}

	private static AudioManagerAndroid createAudioManagerAndroid(Context context)
	{
		return new AudioManagerAndroid(context);
	}

	private int getAudioLowLatencyOutputFrameSize()
	{
		String s = mAudioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER");
		if (s == null)
			return 256;
		else
			return Integer.parseInt(s);
	}

	private static int getMinInputFrameSize(int i, int j)
	{
		byte byte0;
		if (j == 1)
			byte0 = 16;
		else
		if (j == 2)
			byte0 = 12;
		else
			return -1;
		return AudioRecord.getMinBufferSize(i, byte0, 2) / 2 / j;
	}

	private static int getMinOutputFrameSize(int i, int j)
	{
		byte byte0;
		if (j == 1)
			byte0 = 4;
		else
		if (j == 2)
			byte0 = 12;
		else
			return -1;
		return AudioTrack.getMinBufferSize(i, byte0, 2) / 2 / j;
	}

	private int getNativeOutputSampleRate()
	{
		String s;
label0:
		{
			if (android.os.Build.VERSION.SDK_INT >= 17)
			{
				s = mAudioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE");
				if (s != null)
					break label0;
			}
			return 44100;
		}
		return Integer.parseInt(s);
	}

	private boolean isAudioLowLatencySupported()
	{
		return mContext.getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
	}

	private void logDeviceInfo()
	{
		Log.i("AudioManagerAndroid", (new StringBuilder()).append("Manufacturer:").append(Build.MANUFACTURER).append(" Board: ").append(Build.BOARD).append(" Device: ").append(Build.DEVICE).append(" Model: ").append(Build.MODEL).append(" PRODUCT: ").append(Build.PRODUCT).toString());
	}

	public void registerHeadsetReceiver()
	{
		if (mReceiver != null)
		{
			return;
		} else
		{
			mOriginalSpeakerStatus = mAudioManager.isSpeakerphoneOn();
			IntentFilter intentfilter = new IntentFilter("android.intent.action.HEADSET_PLUG");
			mReceiver = new BroadcastReceiver() {

				final AudioManagerAndroid this$0;

				public void onReceive(Context context, Intent intent)
				{
					if (!"android.intent.action.HEADSET_PLUG".equals(intent.getAction()))
						break MISSING_BLOCK_LABEL_48;
					AudioManager audiomanager;
					int i;
					boolean flag;
					try
					{
						audiomanager = mAudioManager;
						i = intent.getIntExtra("state", 0);
					}
					catch (SecurityException securityexception)
					{
						Log.e("AudioManagerAndroid", (new StringBuilder()).append("setMode exception: ").append(securityexception.getMessage()).toString());
						logDeviceInfo();
						return;
					}
					flag = false;
					if (i == 0)
						flag = true;
					audiomanager.setSpeakerphoneOn(flag);
				}

			
			{
				this$0 = AudioManagerAndroid.this;
				super();
			}
			};
			mContext.registerReceiver(mReceiver, intentfilter);
			return;
		}
	}

	public void setMode(int i)
	{
		try
		{
			mAudioManager.setMode(i);
		}
		catch (SecurityException securityexception)
		{
			Log.e("AudioManagerAndroid", (new StringBuilder()).append("setMode exception: ").append(securityexception.getMessage()).toString());
			logDeviceInfo();
			return;
		}
		if (i != 3)
			break MISSING_BLOCK_LABEL_21;
		mAudioManager.setSpeakerphoneOn(true);
	}

	public void unregisterHeadsetReceiver()
	{
		mContext.unregisterReceiver(mReceiver);
		mReceiver = null;
		mAudioManager.setSpeakerphoneOn(mOriginalSpeakerStatus);
	}


}
