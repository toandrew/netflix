// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.media;

import android.media.*;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

class MediaCodecBridge
{
	private static class DequeueOutputResult
	{

		private final int mFlags;
		private final int mIndex;
		private final int mNumBytes;
		private final int mOffset;
		private final long mPresentationTimeMicroseconds;

		private int flags()
		{
			return mFlags;
		}

		private int index()
		{
			return mIndex;
		}

		private int numBytes()
		{
			return mNumBytes;
		}

		private int offset()
		{
			return mOffset;
		}

		private long presentationTimeMicroseconds()
		{
			return mPresentationTimeMicroseconds;
		}

		private DequeueOutputResult(int i, int j, int k, long l, int i1)
		{
			mIndex = i;
			mFlags = j;
			mOffset = k;
			mPresentationTimeMicroseconds = l;
			mNumBytes = i1;
		}

		DequeueOutputResult(int i, int j, int k, long l, int i1, 1 1_1)
		{
			this(i, j, k, l, i1);
		}
	}

	private static class PlayReadyDecryptionError
	{

		public static final long DRM_E_ARITHMETIC_OVERFLOW = 0x80070216L;
		public static final long DRM_E_BUFFERTOOSMALL = 0x8007007aL;
		public static final long DRM_E_FAIL = 0x80004005L;
		public static final long DRM_E_FAILED_TO_STORE_LICENSE = 0xc00d2712L;
		public static final long DRM_E_FILENOTFOUND = 0x80030002L;
		public static final long DRM_E_FILEOPEN = 0x8003006eL;
		public static final long DRM_E_HDCP_NON_SECURE = 0x8004dfffL;
		public static final long DRM_E_HDMI_READ_FAIL = 0x8000400eL;
		public static final long DRM_E_HLOS_TAMPERED = 0x80004006L;
		public static final long DRM_E_INVALIDARG = 0x80070057L;
		public static final long DRM_E_INVALID_COMMAND_LINE = 0x80070667L;
		public static final long DRM_E_LOAD_IMG = 0x80004008L;
		public static final long DRM_E_NOMORE = 0x80070103L;
		public static final long DRM_E_NOTIMPL = 0x80004001L;
		public static final long DRM_E_NOT_ALL_STORED = 0xc00d275fL;
		public static final long DRM_E_NOT_FOUND = 0x80070490L;
		public static final long DRM_E_OEM_CONSTRAINT_1_FAIL = 0x8004dffcL;
		public static final long DRM_E_OEM_CONSTRAINT_2_FAIL = 0x8004dffdL;
		public static final long DRM_E_OEM_CONSTRAINT_3_FAIL = 0x8004dffeL;
		public static final long DRM_E_OPL_BLOCKED = 0x80004007L;
		public static final long DRM_E_OUTOFMEMORY = 0x80000002L;
		public static final long DRM_E_OUT_OF_BOUND = 0x8000400bL;
		public static final long DRM_E_PARAMETERS_MISMATCHED = 0xc00d272fL;
		public static final long DRM_E_PLAY_ENABLER_BLOCKED = 0x8000400cL;
		public static final long DRM_E_SET_BANDWIDTH = 0x8000400aL;
		public static final long DRM_E_VER_MISMATCH = 0x80004009L;
		public static final long DRM_E_WIN32_FILE_NOT_FOUND = 0x80070002L;
		public static final long DRM_E_WIN32_NO_MORE_FILES = 0x80070012L;

		private PlayReadyDecryptionError()
		{
		}
	}


	private static final long MAX_PRESENTATION_TIMESTAMP_SHIFT_US = 0x186a0L;
	private static final int MEDIA_CODEC_ERROR = -1000;
	private static final int MEDIA_ERROR_DECODE = 1;
	private static final int MEDIA_ERROR_FORMAT = 0;
	private static final int MEDIA_ERROR_INVALID_CODE = 3;
	private static final int MEDIA_ERROR_NONE = -1;
	private static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 2;
	private static final String TAG = "MediaCodecBridge";
	private AudioTrack mAudioTrack;
	private boolean mAudioTrackTsAnchored;
	private long mAudioTrackTsBaseLine;
	private Method mAudioTrackTsMethod;
	private int mErrorState;
	private boolean mFlushed;
	private Handler mHandler;
	private ByteBuffer mInputBuffers[];
	private long mLastPresentationTimeUs;
	private MediaCodec mMediaCodec;
	private int mNativeMediaCodecBridge;
	private ByteBuffer mOutputBuffers[];

	private MediaCodecBridge(String s, boolean flag)
	{
		mNativeMediaCodecBridge = 0;
		mHandler = null;
		mAudioTrackTsMethod = null;
		mAudioTrackTsBaseLine = 0L;
		mAudioTrackTsAnchored = false;
		mErrorState = -1;
		if (flag)
		{
			String s1 = getCodecNameFromMimeSecure(s);
			mMediaCodec = MediaCodec.createByCodecName(s1);
			Log.i("AMZN", (new StringBuilder()).append("[MediaCodecBridge] Created decoder by name: ").append(s1).append(" mime: ").append(s).append(" mMediaCodec: ").append(mMediaCodec).toString());
		} else
		{
			mMediaCodec = MediaCodec.createDecoderByType(s);
			Log.i("AMZN", (new StringBuilder()).append("[MediaCodecBridge] Created decoder by mime type: ").append(s).append(" mMediaCodec: ").append(mMediaCodec).toString());
		}
		mLastPresentationTimeUs = 0L;
		mFlushed = true;
		mHandler = new Handler();
		mErrorState = -1;
	}

	private void OnDecodeError(final int lError_Code, final long lSystem_Code)
	{
label0:
		{
			if (mErrorState == -1)
			{
				mErrorState = lError_Code;
				Log.i("AMZN", (new StringBuilder()).append("Decode Error setting Error State to: ").append(mErrorState).append(" on instance ").append(this).toString());
				if (mNativeMediaCodecBridge == 0)
					break label0;
				mHandler.post(new Runnable() {

					final MediaCodecBridge this$0;
					final int val$lError_Code;
					final long val$lSystem_Code;

					public void run()
					{
						nativeOnDecodeError(mNativeMediaCodecBridge, lError_Code, lSystem_Code);
					}

			
			{
				this$0 = MediaCodecBridge.this;
				lError_Code = i;
				lSystem_Code = l;
				super();
			}
				});
			}
			return;
		}
		Log.e("AMZN", "We lost our native MediaCodecBridge instance cannot invoke callbacks");
	}

	private boolean configureAudio(MediaFormat mediaformat, MediaCrypto mediacrypto, int i, boolean flag)
	{
		int j;
		byte byte0;
		int k;
		Exception exception;
		try
		{
			mMediaCodec.configure(mediaformat, null, mediacrypto, i);
		}
		catch (IllegalStateException illegalstateexception)
		{
			Log.e("MediaCodecBridge", (new StringBuilder()).append("Cannot configure the audio codec ").append(illegalstateexception.toString()).toString());
			return false;
		}
		if (!flag)
			break MISSING_BLOCK_LABEL_131;
		j = mediaformat.getInteger("sample-rate");
		if (mediaformat.getInteger("channel-count") == 1)
			byte0 = 4;
		else
			byte0 = 12;
		k = getPlatfromAudioTrackFmt(mediaformat);
		mAudioTrack = new AudioTrack(3, j, byte0, k, 2 * AudioTrack.getMinBufferSize(j, byte0, k), 1);
		Log.d("AMZN", (new StringBuilder()).append("[MediaCodecBridge] Creating audiotrack ").append(mAudioTrack).append(" instance: ").append(this).toString());
		mAudioTrackTsMethod = mAudioTrack.getClass().getDeclaredMethod("getTimestamp", new Class[0]);
_L1:
		return true;
		exception;
		Log.e("AMZN", (new StringBuilder()).append("[MediaCodecBridge] Audio-track get timestamp API is not available: ").append(exception).toString());
		  goto _L1
	}

	private boolean configureVideo(MediaFormat mediaformat, Surface surface, MediaCrypto mediacrypto, int i)
	{
		try
		{
			mMediaCodec.configure(mediaformat, surface, mediacrypto, i);
		}
		catch (IllegalStateException illegalstateexception)
		{
			Log.e("MediaCodecBridge", (new StringBuilder()).append("Cannot configure the video codec ").append(illegalstateexception.toString()).toString());
			return false;
		}
		return true;
	}

	private static MediaCodecBridge create(String s, boolean flag)
	{
		return new MediaCodecBridge(s, flag);
	}

	private static MediaFormat createAudioFormat(String s, int i, int j)
	{
		return MediaFormat.createAudioFormat(s, i, j);
	}

	private static MediaFormat createVideoFormat(String s, int i, int j)
	{
		return MediaFormat.createVideoFormat(s, i, j);
	}

	private int dequeueInputBuffer(long l)
	{
		int i;
		try
		{
			i = mMediaCodec.dequeueInputBuffer(l);
		}
		catch (Exception exception)
		{
			Log.e("MediaCodecBridge", (new StringBuilder()).append("Cannot dequeue Input buffer ").append(exception.toString()).toString());
			return -1000;
		}
		return i;
	}

	private DequeueOutputResult dequeueOutputBuffer(long l)
	{
		android.media.MediaCodec.BufferInfo bufferinfo = new android.media.MediaCodec.BufferInfo();
		int i = -1000;
		try
		{
			i = mMediaCodec.dequeueOutputBuffer(bufferinfo, l);
			if (bufferinfo.presentationTimeUs < mLastPresentationTimeUs)
				bufferinfo.presentationTimeUs = mLastPresentationTimeUs;
			mLastPresentationTimeUs = bufferinfo.presentationTimeUs;
		}
		catch (IllegalStateException illegalstateexception)
		{
			Log.e("MediaCodecBridge", (new StringBuilder()).append("Cannot dequeue output buffer ").append(illegalstateexception.toString()).toString());
			OnDecodeError(1, 0L);
		}
		return new DequeueOutputResult(i, bufferinfo.flags, bufferinfo.offset, bufferinfo.presentationTimeUs, bufferinfo.size);
	}

	private void flush()
	{
		mMediaCodec.flush();
		mFlushed = true;
		if (mAudioTrack != null)
		{
			mAudioTrackTsBaseLine = 0L;
			mAudioTrackTsAnchored = false;
			mAudioTrack.flush();
		}
	}

	private long getAudioPlaybackTs()
	{
		Long long1 = new Long(0L);
		Long long2;
		if (mAudioTrackTsMethod != null)
			try
			{
				long1 = (Long)mAudioTrackTsMethod.invoke(mAudioTrack, new Object[0]);
			}
			catch (Exception exception)
			{
				Log.e("AMZN", (new StringBuilder()).append("[MediaCodecBridge] Exception querying the audio ts: ").append(exception).toString());
			}
		if (mAudioTrackTsAnchored)
		{
			long2 = Long.valueOf(mAudioTrackTsBaseLine + long1.longValue());
		} else
		{
			long2 = Long.valueOf(-1L);
			Log.e("AMZN", "[MediaCodecBridge] Audio track Ts being queried without being anchored Possible loss of AV sync ");
		}
		return long2.longValue();
	}

	private String getCodecNameFromMimeSecure(String s)
	{
		if (s.equals("video/avc"))
			return "OMX.qcom.video.decoder.avc.secure";
		else
			return null;
	}

	private ByteBuffer getInputBuffer(int i)
	{
		return mInputBuffers[i];
	}

	private ByteBuffer getOutputBuffer(int i)
	{
		return mOutputBuffers[i];
	}

	private void getOutputBuffers()
	{
		mOutputBuffers = mMediaCodec.getOutputBuffers();
	}

	private int getOutputHeight()
	{
		return mMediaCodec.getOutputFormat().getInteger("height");
	}

	private int getOutputWidth()
	{
		return mMediaCodec.getOutputFormat().getInteger("width");
	}

	private int getPlatfromAudioTrackFmt(MediaFormat mediaformat)
	{
		String s = mediaformat.getString("mime");
		if (s.equals("audio/mp4a-latm"))
			return 105;
		return !s.equals("audio/eac3") ? 2 : 108;
	}

	private native void nativeOnDecodeError(int i, int j, long l);

	private void pause()
	{
		Log.d("AMZN", (new StringBuilder()).append("[MediaCodecBridge] AudioTrack_pause current state: ").append(mAudioTrack.getPlayState()).toString());
		if (mAudioTrack != null && 2 != mAudioTrack.getPlayState())
		{
			mAudioTrack.pause();
			Log.d("AMZN", (new StringBuilder()).append("[MediaCodecBridge] AudioTrack after pause: ").append(mAudioTrack.getPlayState()).toString());
		}
	}

	private void playOutputBuffer(byte abyte0[], long l)
	{
		if (mAudioTrack != null)
		{
			if (3 != mAudioTrack.getPlayState())
				mAudioTrack.play();
			int i = mAudioTrack.write(abyte0, 0, abyte0.length);
			if (abyte0.length != i)
				Log.i("MediaCodecBridge", (new StringBuilder()).append("Failed to send all data to audio output, expected size: ").append(abyte0.length).append(", actual size: ").append(i).toString());
			if (!mAudioTrackTsAnchored)
			{
				mAudioTrackTsBaseLine = l;
				mAudioTrackTsAnchored = true;
				Log.d("AMZN", (new StringBuilder()).append("[MediaCodecBridge] Updating the AudioTrack anchor timestamp").append(mAudioTrackTsBaseLine).toString());
			}
		}
	}

	private void queueInputBuffer(int i, int j, int k, long l, int i1)
	{
		resetLastPresentationTimeIfNeeded(l);
		try
		{
			mMediaCodec.queueInputBuffer(i, j, k, l, i1);
			mErrorState = -1;
			return;
		}
		catch (IllegalStateException illegalstateexception)
		{
			Log.e("MediaCodecBridge", (new StringBuilder()).append("Failed to queue input buffer ").append(illegalstateexception.toString()).toString());
		}
	}

	private void queueSecureInputBuffer(int i, int j, byte abyte0[], byte abyte1[], int ai[], int ai1[], int k, 
			long l)
	{
		resetLastPresentationTimeIfNeeded(l);
		try
		{
			android.media.MediaCodec.CryptoInfo cryptoinfo = new android.media.MediaCodec.CryptoInfo();
			cryptoinfo.set(k, ai, ai1, abyte1, abyte0, 1);
			mMediaCodec.queueSecureInputBuffer(i, j, cryptoinfo, l, 0);
			mErrorState = -1;
			return;
		}
		catch (android.media.MediaCodec.CryptoException cryptoexception)
		{
			Log.e("MediaCodecBridge", (new StringBuilder()).append("Failed to queue secure input buffer ").append(cryptoexception.toString()).append(" due to Decryption related error").append(cryptoexception.getErrorCode()).toString());
			long l1 = cryptoexception.getErrorCode();
			if (l1 == 0x8004dfffL)
				Log.e("MediaCodecBridge", "HDCP authentication failed could be because display was turned off");
			OnDecodeError(1, l1);
			return;
		}
		catch (IllegalStateException illegalstateexception)
		{
			Log.e("MediaCodecBridge", (new StringBuilder()).append("Failed to queue secure input buffer ").append(illegalstateexception.toString()).toString());
		}
		OnDecodeError(1, 0L);
	}

	private void release()
	{
		Log.i("AMZN", (new StringBuilder()).append("[MediaCodecBridge] MediaCodec Release is called on instance ").append(this).append(" mediacodec: ").append(mMediaCodec).toString());
		mMediaCodec.release();
		if (mAudioTrack != null)
		{
			Log.i("AMZN", (new StringBuilder()).append("[MediaCodecBridge] Audio Track Release is called on instance ").append(this).append("audiotrack: ").append(mAudioTrack).toString());
			mAudioTrack.release();
			mAudioTrackTsMethod = null;
		}
		mNativeMediaCodecBridge = 0;
		mHandler = null;
	}

	private void releaseOutputBuffer(int i, boolean flag)
	{
		try
		{
			mMediaCodec.releaseOutputBuffer(i, flag);
			return;
		}
		catch (IllegalStateException illegalstateexception)
		{
			Log.e("MediaCodecBridge", (new StringBuilder()).append("Could not releaseOutputBuffer ").append(illegalstateexception.toString()).toString());
		}
	}

	private void resetLastPresentationTimeIfNeeded(long l)
	{
		if (mFlushed)
		{
			mLastPresentationTimeUs = Math.max(l - 0x186a0L, 0L);
			mFlushed = false;
		}
	}

	private void resume()
	{
		Log.d("AMZN", (new StringBuilder()).append("[MediaCodecBridge] AudioTrack_resume current state: ").append(mAudioTrack.getPlayState()).toString());
		if (mAudioTrack != null && 3 != mAudioTrack.getPlayState())
		{
			mAudioTrack.play();
			Log.d("AMZN", (new StringBuilder()).append("[MediaCodecBridge] AudioTrack after play: ").append(mAudioTrack.getPlayState()).toString());
		}
	}

	private static void setCodecSpecificData(MediaFormat mediaformat, int i, ByteBuffer bytebuffer)
	{
		if (i != 0) goto _L2; else goto _L1
_L1:
		String s = "csd-0";
_L4:
		if (s != null)
			mediaformat.setByteBuffer(s, bytebuffer);
		return;
_L2:
		s = null;
		if (i == 1)
			s = "csd-1";
		if (true) goto _L4; else goto _L3
_L3:
	}

	private static void setFrameHasADTSHeader(MediaFormat mediaformat)
	{
		mediaformat.setInteger("is-adts", 1);
	}

	private void setVolume(double d)
	{
		if (mAudioTrack != null)
			mAudioTrack.setStereoVolume((float)d, (float)d);
	}

	private void start()
	{
		mMediaCodec.start();
		mInputBuffers = mMediaCodec.getInputBuffers();
	}

	private void stop()
	{
		mMediaCodec.stop();
		if (mAudioTrack != null)
			mAudioTrack.pause();
	}

	public void registerForCallbacks(int i)
	{
		mNativeMediaCodecBridge = i;
	}


}
