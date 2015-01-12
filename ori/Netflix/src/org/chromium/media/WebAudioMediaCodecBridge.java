// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.media;

import android.content.Context;
import android.media.*;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

class WebAudioMediaCodecBridge
{

	private static final boolean DEBUG = true;
	static final String LOG_TAG = "WebAudioMediaCodec";
	static final long TIMEOUT_MICROSECONDS = 500L;

	WebAudioMediaCodecBridge()
	{
	}

	private static String CreateTempFile(Context context)
		throws IOException
	{
		return File.createTempFile("webaudio", ".dat", context.getCacheDir()).getAbsolutePath();
	}

	private static boolean decodeAudioFile(Context context, int i, int j, long l)
	{
		if (l < 0L || l > 0x7fffffffL)
			return false;
		MediaExtractor mediaextractor = new MediaExtractor();
		ParcelFileDescriptor parcelfiledescriptor = ParcelFileDescriptor.adoptFd(j);
		try
		{
			mediaextractor.setDataSource(parcelfiledescriptor.getFileDescriptor(), 0L, l);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			parcelfiledescriptor.detachFd();
			return false;
		}
		if (mediaextractor.getTrackCount() <= 0)
		{
			parcelfiledescriptor.detachFd();
			return false;
		}
		MediaFormat mediaformat = mediaextractor.getTrackFormat(0);
		int k = mediaformat.getInteger("channel-count");
		int i1 = k;
		int j1 = mediaformat.getInteger("sample-rate");
		String s = mediaformat.getString("mime");
		long l1 = 0L;
		if (mediaformat.containsKey("durationUs"))
		{
			ByteBuffer abytebuffer[];
			boolean flag;
			boolean flag1;
			android.media.MediaCodec.BufferInfo bufferinfo;
			ByteBuffer bytebuffer;
			int i2;
			int j2;
			long l3;
			try
			{
				l3 = mediaformat.getLong("durationUs");
			}
			catch (Exception exception1)
			{
				Log.d("WebAudioMediaCodec", "Cannot get duration");
				continue;
			}
			l1 = l3;
		}
		do
		{
			Log.d("WebAudioMediaCodec", (new StringBuilder()).append("Tracks: ").append(mediaextractor.getTrackCount()).append(" Rate: ").append(j1).append(" Channels: ").append(k).append(" Mime: ").append(s).append(" Duration: ").append(l1).append(" microsec").toString());
			nativeInitializeDestination(i, k, j1, l1);
			MediaCodec mediacodec = MediaCodec.createDecoderByType(s);
			mediacodec.configure(mediaformat, null, null, 0);
			mediacodec.start();
			abytebuffer = mediacodec.getInputBuffers();
			ByteBuffer abytebuffer1[] = mediacodec.getOutputBuffers();
			mediaextractor.selectTrack(0);
			flag = false;
			flag1 = false;
			do
			{
				if (flag1)
					break;
				int k1;
				if (!flag)
				{
					i2 = mediacodec.dequeueInputBuffer(500L);
					if (i2 >= 0)
					{
						j2 = mediaextractor.readSampleData(abytebuffer[i2], 0);
						long l2 = 0L;
						byte byte0;
						if (j2 < 0)
						{
							flag = true;
							j2 = 0;
						} else
						{
							l2 = mediaextractor.getSampleTime();
						}
						if (flag)
							byte0 = 4;
						else
							byte0 = 0;
						mediacodec.queueInputBuffer(i2, 0, j2, l2, byte0);
						if (!flag)
							mediaextractor.advance();
					}
				}
				bufferinfo = new android.media.MediaCodec.BufferInfo();
				k1 = mediacodec.dequeueOutputBuffer(bufferinfo, 500L);
				if (k1 >= 0)
				{
					bytebuffer = abytebuffer1[k1];
					if (bufferinfo.size > 0)
						nativeOnChunkDecoded(i, bytebuffer, bufferinfo.size, k, i1);
					bytebuffer.clear();
					mediacodec.releaseOutputBuffer(k1, false);
					if ((4 & bufferinfo.flags) != 0)
						flag1 = true;
				} else
				if (k1 == -3)
					abytebuffer1 = mediacodec.getOutputBuffers();
				else
				if (k1 == -2)
				{
					MediaFormat mediaformat1 = mediacodec.getOutputFormat();
					i1 = mediaformat1.getInteger("channel-count");
					Log.d("WebAudioMediaCodec", (new StringBuilder()).append("output format changed to ").append(mediaformat1).toString());
				}
			} while (true);
			parcelfiledescriptor.detachFd();
			mediacodec.stop();
			mediacodec.release();
			return true;
		} while (true);
	}

	private static native void nativeInitializeDestination(int i, int j, int k, long l);

	private static native void nativeOnChunkDecoded(int i, ByteBuffer bytebuffer, int j, int k, int l);
}
