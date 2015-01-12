// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.media;

import android.content.Context;
import android.drm.*;
import android.media.MediaCrypto;
import android.media.MediaCryptoException;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.UUID;

public class MediaDRMBridge
	implements android.drm.DrmManagerClient.OnEventListener, android.drm.DrmManagerClient.OnErrorListener
{
	private static final class LicenseAcquisitionState extends Enum
	{

		private static final LicenseAcquisitionState $VALUES[];
		public static final LicenseAcquisitionState FAILED_TO_PROCESS_RESPONSE;
		public static final LicenseAcquisitionState IDLE;
		public static final LicenseAcquisitionState PROCESSED_RESPONSE;
		public static final LicenseAcquisitionState WAITING_FOR_RESPONSE;

		public static LicenseAcquisitionState valueOf(String s)
		{
			return (LicenseAcquisitionState)Enum.valueOf(org/chromium/media/MediaDRMBridge$LicenseAcquisitionState, s);
		}

		public static LicenseAcquisitionState[] values()
		{
			return (LicenseAcquisitionState[])$VALUES.clone();
		}

		static 
		{
			IDLE = new LicenseAcquisitionState("IDLE", 0);
			WAITING_FOR_RESPONSE = new LicenseAcquisitionState("WAITING_FOR_RESPONSE", 1);
			PROCESSED_RESPONSE = new LicenseAcquisitionState("PROCESSED_RESPONSE", 2);
			FAILED_TO_PROCESS_RESPONSE = new LicenseAcquisitionState("FAILED_TO_PROCESS_RESPONSE", 3);
			LicenseAcquisitionState alicenseacquisitionstate[] = new LicenseAcquisitionState[4];
			alicenseacquisitionstate[0] = IDLE;
			alicenseacquisitionstate[1] = WAITING_FOR_RESPONSE;
			alicenseacquisitionstate[2] = PROCESSED_RESPONSE;
			alicenseacquisitionstate[3] = FAILED_TO_PROCESS_RESPONSE;
			$VALUES = alicenseacquisitionstate;
		}

		private LicenseAcquisitionState(String s, int i)
		{
			super(s, i);
		}
	}


	private static final int MEDIA_KEYERR_CLIENT = 2;
	private static final int MEDIA_KEYERR_DOMAIN = 6;
	private static final int MEDIA_KEYERR_HARDWARECHANGE = 5;
	private static final int MEDIA_KEYERR_OUTPUT = 4;
	private static final int MEDIA_KEYERR_SERVICE = 3;
	private static final long MEDIA_KEYERR_SYSTEMCODE_DRMBRIDGE = 0x80050000L;
	private static final int MEDIA_KEYERR_UNKNOWN = 1;
	private static final int PLAYREADY_BINARY_PREFIX_BYTES = 10;
	public static final String PLAYREADY_DRM_POST_MIME_TYPE = "application/vnd.ms-playready.media.pyv";
	public static final String PLAYREADY_HEADER_KEY = "Header";
	private static final long PLAYREADY_HIGHEST_64_BITS = 0x11ef8ba979d64aceL;
	private static final long PLAYREADY_LOWEST_64_BITS = 0xa3c827dcd51d2111L;
	private static final int PLAYREADY_MINIMUM_LICENSE_CHALLENGE = 32;
	public static final String PLAYREADY_RESPONSE_KEY = "Response";
	private static final UUID PLAYREADY_SYSTEMID = new UUID(0x9a04f07998404286L, 0xab92e65be0885f95L);
	private static final long PLAYREADY_SYSTEMID_HIGHEST_64_BITS = 0x9a04f07998404286L;
	private static final long PLAYREADY_SYSTEMID_LOWEST_64_BITS = 0xab92e65be0885f95L;
	public static final String PLAYREADY_URI_ALL_LICENSES = "playready://AllLicenses.playready";
	public static final String PLAYREADY_URI_EXPIRED_LICENSES = "playready://AllExpiredLicenses.playready";
	public static final String PLAYREADY_URI_HEAD = "playready://";
	public static final String PLAYREADY_URI_TAIL = ".playready";
	private static final UUID PLAYREADY_UUID = new UUID(0x11ef8ba979d64aceL, 0xa3c827dcd51d2111L);
	public static final String QCOM_PLAYREADY_CONTEXT_SESSION_ID_HEAD = "<sessionid>";
	public static final String QCOM_PLAYREADY_CONTEXT_SESSION_ID_TAIL = "</sessionid>";
	public static final String QCOM_PLAYREADY_ENTER_PERSISTENT_TZ_MODE = "EnterPersistentTZMode";
	public static final String QCOM_PLAYREADY_HEADER_HEAD = "<header>";
	public static final String QCOM_PLAYREADY_HEADER_TAIL = "</header>";
	public static final String QCOM_PLAYREADY_LEAVE_PERSISTENT_TZ_MODE = "LeavePersistentTZMode";
	private static final String TAG = "MediaDRMBridge";
	private static final long mMaximumPlayReadyWaitMillis = 30000L;
	private static HashMap mSessionID_MediaCrypto_Map = new HashMap(4);
	private Context mContext;
	private DrmManagerClient mDrmManagerClient;
	private Handler mHandler;
	private LicenseAcquisitionState mLicenseAcquisitionState;
	private MediaCrypto mMediaCryptoInstance;
	private int mMedia_Keys_Id;
	private int mNativeMediaDrmBridge;
	private int mPRDRMContextID;
	private final Object mProcessDrmInfoSyncObject;
	private byte mUUID[];

	public MediaDRMBridge(int i, byte abyte0[], Context context)
	{
		String s1;
		mNativeMediaDrmBridge = 0;
		mContext = null;
		mHandler = null;
		mDrmManagerClient = null;
		mMediaCryptoInstance = null;
		mPRDRMContextID = 0;
		mLicenseAcquisitionState = LicenseAcquisitionState.IDLE;
		mProcessDrmInfoSyncObject = new Object();
		mMedia_Keys_Id = i;
		mUUID = abyte0;
		mContext = context;
		mHandler = new Handler();
		mDrmManagerClient = new DrmManagerClient(mContext);
		if (mDrmManagerClient != null)
		{
			mDrmManagerClient.setOnEventListener(this);
			mDrmManagerClient.setOnErrorListener(this);
		}
		DrmInfoRequest drminforequest = new DrmInfoRequest(3, "application/vnd.ms-playready.media.pyv");
		drminforequest.put("Header", "EnterPersistentTZMode");
		Log.i("MediaDRMBridge", "Trying to Get a DRM Context Session ID and enter persistent TZ Mode");
		String s = (new String(mDrmManagerClient.acquireDrmInfo(drminforequest).getData())).trim();
		s.toUpperCase();
		if (s.startsWith("8") || s.startsWith("FF"))
		{
			Log.e("MediaDRMBridge", (new StringBuilder()).append("Acquire DRM Info failed to get DRM Session ID, error = ").append(s).toString());
			return;
		}
		s1 = s.substring(4, 8);
		int j = Integer.parseInt(s1);
		if (j > 0)
		{
			try
			{
				mPRDRMContextID = j;
				Log.e("MediaDRMBridge", (new StringBuilder()).append("DRM Session ID returned by the PR Stack is ").append(j).toString());
				return;
			}
			catch (NumberFormatException numberformatexception)
			{
				Log.e("MediaDRMBridge", (new StringBuilder()).append("Invalid DRM Session ID ").append(s1).toString());
			}
			return;
		}
		Log.e("MediaDRMBridge", (new StringBuilder()).append("Invalid DRM Session ID ").append(s1).toString());
		return;
	}

	private void AddKey(byte abyte0[], int i, byte abyte1[], int j, String s)
	{
label0:
		{
			String s1;
			DrmInfo drminfo;
			try
			{
				s1 = new String(abyte0, 0, abyte0.length, "UTF-8");
				Log.i("MediaDRMBridge", (new StringBuilder()).append("License Response String is : ").append(s1).toString());
			}
			catch (UnsupportedEncodingException unsupportedencodingexception)
			{
				Log.e("MediaDRMBridge", "Impossible exception while initializing lLicResponse, UTF-8 is a valid encoding");
				OnKeyError(s, 1, 0x80050000L);
				return;
			}
			drminfo = new DrmInfo(3, new byte[1], "application/vnd.ms-playready.media.pyv");
			drminfo.put("Response", s1);
			synchronized (mProcessDrmInfoSyncObject)
			{
				mLicenseAcquisitionState = LicenseAcquisitionState.WAITING_FOR_RESPONSE;
				if (mDrmManagerClient.processDrmInfo(drminfo) == 0)
					break label0;
				Log.e("MediaDRMBridge", "Error while adding key");
				OnKeyError(s, 3, 0x80050000L);
				mLicenseAcquisitionState = LicenseAcquisitionState.FAILED_TO_PROCESS_RESPONSE;
			}
			return;
		}
		waitForPlayReadyCallback();
		static class 5
		{

			static final int $SwitchMap$org$chromium$media$MediaDRMBridge$LicenseAcquisitionState[];

			static 
			{
				$SwitchMap$org$chromium$media$MediaDRMBridge$LicenseAcquisitionState = new int[LicenseAcquisitionState.values().length];
				try
				{
					$SwitchMap$org$chromium$media$MediaDRMBridge$LicenseAcquisitionState[LicenseAcquisitionState.PROCESSED_RESPONSE.ordinal()] = 1;
				}
				catch (NoSuchFieldError nosuchfielderror) { }
				try
				{
					$SwitchMap$org$chromium$media$MediaDRMBridge$LicenseAcquisitionState[LicenseAcquisitionState.FAILED_TO_PROCESS_RESPONSE.ordinal()] = 2;
				}
				catch (NoSuchFieldError nosuchfielderror1) { }
				try
				{
					$SwitchMap$org$chromium$media$MediaDRMBridge$LicenseAcquisitionState[LicenseAcquisitionState.WAITING_FOR_RESPONSE.ordinal()] = 3;
				}
				catch (NoSuchFieldError nosuchfielderror2)
				{
					return;
				}
			}
		}

		5..SwitchMap.org.chromium.media.MediaDRMBridge.LicenseAcquisitionState[mLicenseAcquisitionState.ordinal()];
		JVM INSTR tableswitch 1 3: default 188
	//	               1 226
	//	               2 244
	//	               3 266;
		   goto _L1 _L2 _L3 _L4
_L4:
		break MISSING_BLOCK_LABEL_266;
_L1:
		Log.e("MediaDRMBridge", "Impossible License Acquisition State");
		OnKeyError(s, 1, 0x80050000L);
_L5:
		mLicenseAcquisitionState = LicenseAcquisitionState.IDLE;
		obj;
		JVM INSTR monitorexit ;
		return;
		exception;
		obj;
		JVM INSTR monitorexit ;
		throw exception;
_L2:
		Log.v("MediaDRMBridge", "Processed License Response Successfully");
		OnKeyAdded(s);
		  goto _L5
_L3:
		Log.e("MediaDRMBridge", "Error while processing License Response");
		OnKeyError(s, 3, 0x80050000L);
		  goto _L5
		Log.e("MediaDRMBridge", "PlayReady timed out while processing License Response");
		OnKeyError(s, 3, 0x80050000L);
		  goto _L5
	}

	private void CancelKeyRequest(String s)
	{
	}

	private static MediaDRMBridge Create(int i, byte abyte0[], Context context)
	{
		return new MediaDRMBridge(i, abyte0, context);
	}

	private MediaCrypto CreateMediaCrypto(byte abyte0[], String s, byte abyte1[], int i)
	{
		boolean flag = MediaCrypto.isCryptoSchemeSupported(PLAYREADY_UUID);
		boolean flag1 = false;
		if (!flag)
		{
			if (!MediaCrypto.isCryptoSchemeSupported(PLAYREADY_SYSTEMID))
			{
				Log.e("MediaDRMBridge", "Playready crypto scheme is not supported!");
				throw new RuntimeException("Playready crypto scheme is not supported");
			}
			flag1 = true;
		}
		if (s.isEmpty())
		{
			Log.e("MediaDRMBridge", "Session ID is empty, app either doesn't have a session ID or sent us an invalid session ID");
			OnKeyError(s, 1, 0x80050000L);
			return null;
		}
		String s1;
		byte abyte2[];
		int j;
		try
		{
			s1 = new String(abyte1, 10, i - 10, "UTF-16LE");
		}
		catch (UnsupportedEncodingException unsupportedencodingexception1)
		{
			Log.e("MediaDRMBridge", "Impossible exception while initializing lLicResponse, UTF-8 is a valid encoding");
			OnKeyError(s, 1, 0x80050000L);
			return null;
		}
		Log.i("MediaDRMBridge", (new StringBuilder()).append("DRM Scheme Protection Header is").append(s1).toString());
		String s2;
		byte abyte3[];
		ByteBuffer bytebuffer;
		byte abyte4[];
		UnsupportedEncodingException unsupportedencodingexception;
		try
		{
			abyte2 = new byte[-10 + abyte1.length];
		}
		catch (MediaCryptoException mediacryptoexception)
		{
			Log.e("MediaDRMBridge", "Exception while creating MediaCrypto Instance");
			OnKeyError(s, 1, 0x80050000L);
			return null;
		}
		j = 0;
		if (j >= abyte2.length)
			break; /* Loop/switch isn't completed */
		abyte2[j] = abyte1[j + 10];
		j++;
		if (true) goto _L2; else goto _L1
_L2:
		break MISSING_BLOCK_LABEL_134;
_L1:
		s2 = new String(abyte2, 0, abyte2.length, "UTF-16LE");
		Log.i("MediaDRMBridge", (new StringBuilder()).append("DRM Scheme Protection Header Byte Array being used to instantiate a Media Crypto object").append(s2).toString());
		abyte3 = new byte[4];
		abyte3[0] = (byte)mPRDRMContextID;
		abyte3[1] = (byte)(mPRDRMContextID >>> 8);
		abyte3[2] = (byte)(mPRDRMContextID >>> 16);
		abyte3[3] = (byte)(mPRDRMContextID >>> 24);
		bytebuffer = ByteBuffer.allocate("<header>".getBytes().length + abyte2.length + "</header>".getBytes().length + "<sessionid>".getBytes().length + abyte3.length + "</sessionid>".getBytes().length);
		bytebuffer.put("<header>".getBytes());
		bytebuffer.put(abyte2);
		bytebuffer.put("</header>".getBytes());
		bytebuffer.put("<sessionid>".getBytes());
		bytebuffer.put(abyte3);
		bytebuffer.put("</sessionid>".getBytes());
		abyte4 = bytebuffer.array();
		if (!flag1)
			break MISSING_BLOCK_LABEL_477;
		mMediaCryptoInstance = new MediaCrypto(PLAYREADY_SYSTEMID, abyte4);
_L3:
		if (mMediaCryptoInstance != null)
		{
			Log.i("MediaDRMBridge", (new StringBuilder()).append("Successfully created the Media Crypto Object for Session ID ").append(s).toString());
			mSessionID_MediaCrypto_Map.put(s, mMediaCryptoInstance);
		}
		return mMediaCryptoInstance;
		unsupportedencodingexception;
		Log.e("MediaDRMBridge", "Impossible exception while initializing lPsshByteArrayDataString, UTF-16LE is a valid encoding");
		OnKeyError(s, 1, 0x80050000L);
		return null;
		mMediaCryptoInstance = new MediaCrypto(PLAYREADY_UUID, abyte4);
		  goto _L3
	}

	private void DeleteKey(String s)
	{
		if (s.isEmpty())
		{
			Log.e("MediaDRMBridge", "Session ID is empty, app either doesn't have a session ID or sent us an invalid session ID");
			return;
		}
		String s1 = (new StringBuilder()).append("playready://").append(s).append(".playready").toString();
		if (mDrmManagerClient.removeRights(s1) != 0)
		{
			Log.e("MediaDRMBridge", "Error occurred when trying to delete license");
			OnKeyError(s, 1, 0x80050000L);
			return;
		} else
		{
			Log.i("MediaDRMBridge", (new StringBuilder()).append("Successfully deleted license for session : ").append(s).toString());
			return;
		}
	}

	private MediaCrypto DestroyMediaCrypto(String s)
	{
		MediaCrypto mediacrypto = (MediaCrypto)mSessionID_MediaCrypto_Map.get(s);
		if (mediacrypto != null)
		{
			Log.i("MediaDRMBridge", (new StringBuilder()).append("Releasing Media Crypto Object with corresponding Session ID ").append(s).toString());
			mediacrypto.release();
			mSessionID_MediaCrypto_Map.remove(s);
		} else
		{
			Log.e("MediaDRMBridge", (new StringBuilder()).append("Did not find the Media Crypto Object with corresponding session_id ").append(s).append(" it might already be freed please refer to earlier logs on the same session_id").append(" or we might not have created the Media Crypto object in the first place").append(" or we may be leaking MediaCrypto objects").toString());
		}
		return null;
	}

	private boolean GenerateKeyRequest(String s, byte abyte0[], int i)
	{
		if (i <= 10)
			return false;
		String s1;
		String as[];
		try
		{
			s1 = new String(abyte0, 10, i - 10, "UTF-16LE");
		}
		catch (UnsupportedEncodingException unsupportedencodingexception1)
		{
			Log.e("MediaDRMBridge", "Impossible exception while UTF-16LE is a valid encoding");
			OnKeyError(null, 1, 0x80050000L);
			return false;
		}
		as = s1.split("</?KID>", 0);
		if (as.length < 2)
			return false;
		String s2 = as[1];
		String s3;
		String s4;
		DrmInfoRequest drminforequest;
		byte abyte1[];
		try
		{
			s3 = new String(abyte0, 10, i - 10, "UTF-16LE");
		}
		catch (UnsupportedEncodingException unsupportedencodingexception)
		{
			Log.e("MediaDRMBridge", "Impossible exception while creating lPRHeader, UTF-16LE is a valid encoding");
			OnKeyError(s2, 1, 0x80050000L);
			return false;
		}
		Log.i("MediaDRMBridge", (new StringBuilder()).append("PlayReady Header as obtained from pssh data is : ").append(s3).toString());
		s4 = Base64.encodeToString(abyte0, 10, i - 10, 2);
		drminforequest = new DrmInfoRequest(3, "application/vnd.ms-playready.media.pyv");
		drminforequest.put("Header", s4);
		Log.i("MediaDRMBridge", "Trying to Generate License Challenge");
		abyte1 = mDrmManagerClient.acquireDrmInfo(drminforequest).getData();
		if (abyte1.length == 4)
		{
			int j = (0xff & abyte1[0]) << 24 | (0xff & abyte1[1]) << 16 | (0xff & abyte1[2]) << 8 | (0xff & abyte1[3]) << 0;
			if (j == 0)
				Log.e("MediaDRMBridge", "License already exists, we shouldn't have the licenseif we are generating a keyrequest something is wrong ! ! !");
			else
				Log.e("MediaDRMBridge", "Error while generating license challenge ! ! !");
			OnKeyError(s2, 3, j);
			return false;
		}
		if (abyte1.length < 32)
		{
			Log.e("MediaDRMBridge", "Invalid License Challenge");
			OnKeyError(s2, 3, 0x80050000L);
			return false;
		} else
		{
			Log.i("MediaDRMBridge", "Generated a valid license challenge");
			String s5 = (new StringBuilder()).append("playready://").append(s2).append(".playready").toString();
			(new StringBuilder()).append("playready://").append(s1).append(".playready").toString();
			OnKeyMessage(s2, abyte1, s5);
			return true;
		}
	}

	private void OnDrmEvent(String s, int i, int j, String s1)
	{
		nativeOnDrmEvent(mNativeMediaDrmBridge, s, i, j, s1);
	}

	private void OnKeyAdded(final String lSession_Id)
	{
		if (mNativeMediaDrmBridge != 0)
		{
			mHandler.post(new Runnable() {

				final MediaDRMBridge this$0;
				final String val$lSession_Id;

				public void run()
				{
					nativeOnKeyAdded(mNativeMediaDrmBridge, lSession_Id);
				}

			
			{
				this$0 = MediaDRMBridge.this;
				lSession_Id = s;
				super();
			}
			});
			return;
		} else
		{
			Log.e("MediaDRMBridge", "We lost our native MediaDrmBridge instance cannot invoke callbacks");
			return;
		}
	}

	private void OnKeyDeleted(final String lSession_Id)
	{
		if (mNativeMediaDrmBridge != 0)
		{
			mHandler.post(new Runnable() {

				final MediaDRMBridge this$0;
				final String val$lSession_Id;

				public void run()
				{
					nativeOnKeyDeleted(mNativeMediaDrmBridge, lSession_Id);
				}

			
			{
				this$0 = MediaDRMBridge.this;
				lSession_Id = s;
				super();
			}
			});
			return;
		} else
		{
			Log.e("MediaDRMBridge", "We lost our native MediaDrmBridge instance cannot invoke callbacks");
			return;
		}
	}

	private void OnKeyError(final String lSession_Id, final int lError_Code, final long lSystem_Code)
	{
		if (mNativeMediaDrmBridge != 0)
		{
			mHandler.post(new Runnable() {

				final MediaDRMBridge this$0;
				final int val$lError_Code;
				final String val$lSession_Id;
				final long val$lSystem_Code;

				public void run()
				{
					nativeOnKeyError(mNativeMediaDrmBridge, lSession_Id, lError_Code, lSystem_Code);
				}

			
			{
				this$0 = MediaDRMBridge.this;
				lSession_Id = s;
				lError_Code = i;
				lSystem_Code = l;
				super();
			}
			});
			return;
		} else
		{
			Log.e("MediaDRMBridge", "We lost our native MediaDrmBridge instance cannot invoke callbacks");
			return;
		}
	}

	private void OnKeyMessage(final String lSession_Id, final byte lMessage[], final String lDestination_Url)
	{
		if (mNativeMediaDrmBridge != 0)
		{
			mHandler.post(new Runnable() {

				final MediaDRMBridge this$0;
				final String val$lDestination_Url;
				final byte val$lMessage[];
				final String val$lSession_Id;

				public void run()
				{
					nativeOnKeyMessage(mNativeMediaDrmBridge, lSession_Id, lMessage, lDestination_Url);
				}

			
			{
				this$0 = MediaDRMBridge.this;
				lSession_Id = s;
				lMessage = abyte0;
				lDestination_Url = s1;
				super();
			}
			});
			return;
		} else
		{
			Log.e("MediaDRMBridge", "We lost our native MediaDrmBridge instance cannot invoke callbacks");
			return;
		}
	}

	private void Release(int i)
	{
		DrmInfoRequest drminforequest = new DrmInfoRequest(3, "application/vnd.ms-playready.media.pyv");
		drminforequest.put("Header", "LeavePersistentTZMode");
		Log.i("MediaDRMBridge", "Trying to exit persistent TZ Mode");
		byte abyte0[] = mDrmManagerClient.acquireDrmInfo(drminforequest).getData();
		int j = 0;
		if (abyte0.length == 10)
		{
			for (int k = 4; k < 8; k++)
			{
				if (k > 4)
					j <<= 8;
				j += abyte0[k];
			}

		}
		mDrmManagerClient.release();
		mDrmManagerClient = null;
		mHandler = null;
		mContext = null;
		mUUID = null;
		mPRDRMContextID = 0;
	}

	private static UUID getUUIDFromBytes(byte abyte0[])
	{
		if (abyte0.length != 16)
			return null;
		long l = 0L;
		long l1 = 0L;
		for (int i = 0; i < 8; i++)
			l = l << 8 | (long)(0xff & abyte0[i]);

		for (int j = 8; j < 16; j++)
			l1 = l1 << 8 | (long)(0xff & abyte0[j]);

		return new UUID(l, l1);
	}

	private static int littleEndianBytesToInt(byte abyte0[])
	{
		return (0xff & abyte0[0]) << 24 | (0xff & abyte0[1]) << 16 | (0xff & abyte0[2]) << 8 | (0xff & abyte0[3]) << 0;
	}

	private native void nativeOnDrmEvent(int i, String s, int j, int k, String s1);

	private native void nativeOnKeyAdded(int i, String s);

	private native void nativeOnKeyDeleted(int i, String s);

	private native void nativeOnKeyError(int i, String s, int j, long l);

	private native void nativeOnKeyMessage(int i, String s, byte abyte0[], String s1);

	private void waitForPlayReadyCallback()
	{
		long l = System.currentTimeMillis();
		long l1 = l + 30000L;
		boolean flag = false;
		Log.i("MediaDRMBridge", "Waiting for PlayReady to process the license response for 30000 milliseconds");
		do
		{
			if (mLicenseAcquisitionState != LicenseAcquisitionState.WAITING_FOR_RESPONSE || l >= l1)
				break;
			try
			{
				mProcessDrmInfoSyncObject.wait(l1 - l);
			}
			catch (InterruptedException interruptedexception)
			{
				Log.v("MediaDRMBridge", (new StringBuilder()).append(interruptedexception.getMessage()).append(" ignoring, but preserving thread's interrupted state").toString());
				flag = true;
			}
			l = System.currentTimeMillis();
		} while (true);
		if (mLicenseAcquisitionState == LicenseAcquisitionState.WAITING_FOR_RESPONSE || mLicenseAcquisitionState == LicenseAcquisitionState.FAILED_TO_PROCESS_RESPONSE)
			Log.e("MediaDRMBridge", "PlayReady did not process license response within 30000milliseconds");
		if (flag)
			Thread.currentThread().interrupt();
	}

	public void RegisterForCallbacks(int i)
	{
		mNativeMediaDrmBridge = i;
	}

	public void onError(DrmManagerClient drmmanagerclient, DrmErrorEvent drmerrorevent)
	{
		Object obj;
		StringBuilder stringbuilder = (new StringBuilder()).append("Received DRM manager error:");
		String s;
		if (drmerrorevent.getType() == 2008)
			s = "TYPE_ACQUIRE_DRM_INFO_FAILED";
		else
		if (drmerrorevent.getType() == 2003)
			s = "TYPE_NOT_SUPPORTED";
		else
		if (drmerrorevent.getType() == 2005)
			s = "TYPE_NO_INTERNET_CONNECTION";
		else
		if (drmerrorevent.getType() == 2004)
			s = "TYPE_OUT_OF_MEMORY";
		else
		if (drmerrorevent.getType() == 2006)
			s = "TYPE_PROCESS_DRM_INFO_FAILED";
		else
		if (drmerrorevent.getType() == 2007)
			s = "TYPE_REMOVE_ALL_RIGHTS_FAILED";
		else
		if (drmerrorevent.getType() == 2001)
			s = "TYPE_RIGHTS_NOT_INSTALLED";
		else
		if (drmerrorevent.getType() == 2002)
			s = "TYPE_RIGHTS_RENEWAL_NOT_ALLOWED";
		else
			s = "UNKNOWN_ERROR";
_L3:
		Log.e("MediaDRMBridge", stringbuilder.append(s).append(drmerrorevent.getMessage()).toString());
		if (drmerrorevent.getType() != 2006)
			break MISSING_BLOCK_LABEL_96;
		obj = mProcessDrmInfoSyncObject;
		obj;
		JVM INSTR monitorenter ;
		if (mLicenseAcquisitionState != LicenseAcquisitionState.WAITING_FOR_RESPONSE)
			break MISSING_BLOCK_LABEL_232;
		mLicenseAcquisitionState = LicenseAcquisitionState.FAILED_TO_PROCESS_RESPONSE;
		mProcessDrmInfoSyncObject.notifyAll();
_L1:
		return;
		Log.e("MediaDRMBridge", "PROCESS_DRM_INFO_FAILED error unexpected at this time");
		  goto _L1
		Exception exception;
		exception;
		obj;
		JVM INSTR monitorexit ;
		throw exception;
		if (true) goto _L3; else goto _L2
_L2:
	}

	public void onEvent(DrmManagerClient drmmanagerclient, DrmEvent drmevent)
	{
		Object obj;
		StringBuilder stringbuilder = (new StringBuilder()).append("Received DRM manager event:");
		String s;
		if (drmevent.getType() == 1002)
			s = "TYPE_DRM_INFO_PROCESSED";
		else
		if (drmevent.getType() == 1001)
			s = "TYPE_ALL_RIGHTS_REMOVED";
		else
			s = "UNKNOWN_ERROR";
		Log.v("MediaDRMBridge", stringbuilder.append(s).append(drmevent.getMessage()).toString());
		if (drmevent.getType() != 1002)
			break MISSING_BLOCK_LABEL_96;
		obj = mProcessDrmInfoSyncObject;
		obj;
		JVM INSTR monitorenter ;
		if (mLicenseAcquisitionState != LicenseAcquisitionState.WAITING_FOR_RESPONSE)
			break MISSING_BLOCK_LABEL_124;
		mLicenseAcquisitionState = LicenseAcquisitionState.PROCESSED_RESPONSE;
		mProcessDrmInfoSyncObject.notifyAll();
_L1:
		return;
		Log.e("MediaDRMBridge", "DRM_INFO_PROCESSED event unexpected at this time");
		  goto _L1
		Exception exception;
		exception;
		obj;
		JVM INSTR monitorexit ;
		throw exception;
	}






}
