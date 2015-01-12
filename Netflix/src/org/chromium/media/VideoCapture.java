// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.media;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class VideoCapture
	implements android.hardware.Camera.PreviewCallback, android.graphics.SurfaceTexture.OnFrameAvailableListener
{
	static class CaptureCapability
	{

		public int mDesiredFps;
		public int mHeight;
		public int mWidth;

		CaptureCapability()
		{
			mWidth = 0;
			mHeight = 0;
			mDesiredFps = 0;
		}
	}

	private static class ChromiumCameraInfo
	{

		private final android.hardware.Camera.CameraInfo mCameraInfo = new android.hardware.Camera.CameraInfo();
		private final int mId;

		private static ChromiumCameraInfo getAt(int i)
		{
			return new ChromiumCameraInfo(i);
		}

		private String getDeviceName()
		{
			StringBuilder stringbuilder = (new StringBuilder()).append("camera ").append(mId).append(", facing ");
			String s;
			if (mCameraInfo.facing == 1)
				s = "front";
			else
				s = "back";
			return stringbuilder.append(s).toString();
		}

		private int getId()
		{
			return mId;
		}

		private static int getNumberOfCameras()
		{
			return Camera.getNumberOfCameras();
		}

		private int getOrientation()
		{
			return mCameraInfo.orientation;
		}

		private ChromiumCameraInfo(int i)
		{
			mId = i;
			Camera.getCameraInfo(i, mCameraInfo);
		}
	}

	private static class DeviceImageFormatHack
	{

		private static final String sBUGGY_DEVICE_LIST[] = {
			"SAMSUNG-SGH-I747"
		};

		static int getImageFormat()
		{
		    return 17;//TODO
//			if (android.os.Build.VERSION.SDK_INT >= 16) goto _L2; else goto _L1
//_L1:
//			return 17;
//_L2:
//			String as[] = sBUGGY_DEVICE_LIST;
//			int i = as.length;
//			int j = 0;
//label0:
//			do
//			{
//label1:
//				{
//					if (j >= i)
//						break label1;
//					if (as[j].contentEquals(Build.MODEL))
//						break label0;
//					j++;
//				}
//			} while (true);
//			if (true) goto _L1; else goto _L3
//_L3:
//			return 0x32315659;
		}


		private DeviceImageFormatHack()
		{
		}
	}


	private static final int GL_TEXTURE_EXTERNAL_OES = 36197;
	private static final int NUM_CAPTURE_BUFFERS = 3;
	private static final String TAG = "VideoCapture";
	private Camera mCamera;
	private int mCameraFacing;
	private int mCameraOrientation;
	private byte mColorPlane[];
	private Context mContext;
	CaptureCapability mCurrentCapability;
	private int mDeviceOrientation;
	private int mExpectedFrameSize;
	private int mGlTextures[];
	private int mId;
	private int mImageFormat;
	private boolean mIsRunning;
	private int mNativeVideoCaptureDeviceAndroid;
	public ReentrantLock mPreviewBufferLock;
	private SurfaceTexture mSurfaceTexture;

	public VideoCapture(Context context, int i, int j)
	{
		mPreviewBufferLock = new ReentrantLock();
		mImageFormat = 0x32315659;
		mColorPlane = null;
		mContext = null;
		mIsRunning = false;
		mExpectedFrameSize = 0;
		mId = 0;
		mNativeVideoCaptureDeviceAndroid = 0;
		mGlTextures = null;
		mSurfaceTexture = null;
		mCameraOrientation = 0;
		mCameraFacing = 0;
		mDeviceOrientation = 0;
		mCurrentCapability = null;
		mContext = context;
		mId = i;
		mNativeVideoCaptureDeviceAndroid = j;
	}

	private void calculateImageFormat(int i, int j)
	{
		mImageFormat = DeviceImageFormatHack.getImageFormat();
		if (mImageFormat == 17)
			mColorPlane = new byte[(i * j) / 4];
	}

	private void convertNV21ToYV12(byte abyte0[])
	{
		int i = mCurrentCapability.mWidth * mCurrentCapability.mHeight;
		int j = i / 4;
		for (int k = 0; k < j; k++)
		{
			int l = i + k * 2;
			abyte0[i + k] = abyte0[l];
			mColorPlane[k] = abyte0[l + 1];
		}

		System.arraycopy(mColorPlane, 0, abyte0, i + j, j);
	}

	public static VideoCapture createVideoCapture(Context context, int i, int j)
	{
		return new VideoCapture(context, i, j);
	}

	private int getDeviceOrientation()
	{
	    return 0;
	            
//		if (mContext == null) goto _L2; else goto _L1
//_L1:
//		((WindowManager)mContext.getSystemService("window")).getDefaultDisplay().getRotation();
//		JVM INSTR tableswitch 1 3: default 52
//	//	               1 54
//	//	               2 57
//	//	               3 61;
//		   goto _L2 _L3 _L4 _L5
//_L2:
//		return 0;
//_L3:
//		return 90;
//_L4:
//		return 180;
//_L5:
//		return 270;
	}

	private native void nativeOnFrameAvailable(int i, byte abyte0[], int j, int k, boolean flag, boolean flag1);

	public boolean allocate(int i, int j, int k)
	{
	    return true;
//		Log.d("VideoCapture", (new StringBuilder()).append("allocate: requested width=").append(i).append(", height=").append(j).append(", frameRate=").append(k).toString());
//		android.hardware.Camera.Parameters parameters;
//		List list;
//		mCamera = Camera.open(mId);
//		android.hardware.Camera.CameraInfo camerainfo = new android.hardware.Camera.CameraInfo();
//		Camera.getCameraInfo(mId, camerainfo);
//		mCameraOrientation = camerainfo.orientation;
//		mCameraFacing = camerainfo.facing;
//		mDeviceOrientation = getDeviceOrientation();
//		Log.d("VideoCapture", (new StringBuilder()).append("allocate: device orientation=").append(mDeviceOrientation).append(", camera orientation=").append(mCameraOrientation).append(", facing=").append(mCameraFacing).toString());
//		parameters = mCamera.getParameters();
//		list = parameters.getSupportedPreviewFpsRange();
//		if (list.size() != 0)
//			break MISSING_BLOCK_LABEL_186;
//		Log.e("VideoCapture", "allocate: no fps range found");
//		return false;
//		int l = k * 1000;
//		int i1;
//		int j1;
//		int k1;
//		Iterator iterator = list.iterator();
//		int ai[] = (int[])(int[])iterator.next();
//		i1 = ai[0];
//		j1 = ai[1];
//		k1 = (i1 + 999) / 1000;
//		int ai1[];
//		do
//		{
//			if (!iterator.hasNext())
//				break MISSING_BLOCK_LABEL_299;
//			ai1 = (int[])(int[])iterator.next();
//		} while (ai1[0] > l || l > ai1[1]);
//		i1 = ai1[0];
//		j1 = ai1[1];
//		k1 = k;
//		int l1 = k1;
//		List list1;
//		int i2;
//		int j2;
//		int k2;
//		Iterator iterator1;
//		int l2;
//		int i3;
//		byte abyte0[];
//		android.hardware.Camera.Size size;
//		int j3;
//		try
//		{
//			Log.d("VideoCapture", (new StringBuilder()).append("allocate: fps set to ").append(l1).toString());
//			mCurrentCapability = new CaptureCapability();
//			mCurrentCapability.mDesiredFps = l1;
//			list1 = parameters.getSupportedPreviewSizes();
//		}
//		catch (IOException ioexception)
//		{
//			Log.e("VideoCapture", (new StringBuilder()).append("allocate: ").append(ioexception).toString());
//			return false;
//		}
//		i2 = 0x7fffffff;
//		j2 = i;
//		k2 = j;
//		iterator1 = list1.iterator();
//_L2:
//		do
//		{
//			if (!iterator1.hasNext())
//				break MISSING_BLOCK_LABEL_511;
//			size = (android.hardware.Camera.Size)iterator1.next();
//			j3 = Math.abs(size.width - i) + Math.abs(size.height - j);
//			Log.d("VideoCapture", (new StringBuilder()).append("allocate: support resolution (").append(size.width).append(", ").append(size.height).append("), diff=").append(j3).toString());
//		} while (j3 >= i2);
//		if (size.width % 32 != 0)
//			continue; /* Loop/switch isn't completed */
//		i2 = j3;
//		j2 = size.width;
//		k2 = size.height;
//		if (true) goto _L2; else goto _L1
//_L1:
//		if (i2 != 0x7fffffff)
//			break MISSING_BLOCK_LABEL_529;
//		Log.e("VideoCapture", "allocate: can not find a resolution whose width is multiple of 32");
//		return false;
//		mCurrentCapability.mWidth = j2;
//		mCurrentCapability.mHeight = k2;
//		Log.d("VideoCapture", (new StringBuilder()).append("allocate: matched width=").append(j2).append(", height=").append(k2).toString());
//		calculateImageFormat(j2, k2);
//		parameters.setPreviewSize(j2, k2);
//		parameters.setPreviewFormat(mImageFormat);
//		parameters.setPreviewFpsRange(i1, j1);
//		mCamera.setParameters(parameters);
//		mGlTextures = new int[1];
//		GLES20.glGenTextures(1, mGlTextures, 0);
//		GLES20.glBindTexture(36197, mGlTextures[0]);
//		GLES20.glTexParameterf(36197, 10241, 9729F);
//		GLES20.glTexParameterf(36197, 10240, 9729F);
//		GLES20.glTexParameteri(36197, 10242, 33071);
//		GLES20.glTexParameteri(36197, 10243, 33071);
//		mSurfaceTexture = new SurfaceTexture(mGlTextures[0]);
//		mSurfaceTexture.setOnFrameAvailableListener(null);
//		mCamera.setPreviewTexture(mSurfaceTexture);
//		l2 = (j2 * k2 * ImageFormat.getBitsPerPixel(mImageFormat)) / 8;
//		i3 = 0;
//_L4:
//		if (i3 >= 3)
//			break; /* Loop/switch isn't completed */
//		abyte0 = new byte[l2];
//		mCamera.addCallbackBuffer(abyte0);
//		i3++;
//		if (true) goto _L4; else goto _L3
//_L3:
//		mExpectedFrameSize = l2;
//		return true;
	}

	public void deallocate()
	{
		if (mCamera == null)
			return;
		stopCapture();
		try
		{
			mCamera.setPreviewTexture(null);
			if (mGlTextures != null)
				GLES20.glDeleteTextures(1, mGlTextures, 0);
			mCurrentCapability = null;
			mCamera.release();
			mCamera = null;
			return;
		}
		catch (IOException ioexception)
		{
			Log.e("VideoCapture", (new StringBuilder()).append("deallocate: failed to deallocate camera, ").append(ioexception).toString());
		}
	}

	public void onFrameAvailable(SurfaceTexture surfacetexture)
	{
	}

	public void onPreviewFrame(byte abyte0[], Camera camera)
	{
//		mPreviewBufferLock.lock();
//		boolean flag = mIsRunning;
//		if (flag) goto _L2; else goto _L1
//_L1:
//		mPreviewBufferLock.unlock();
//		if (camera != null)
//			camera.addCallbackBuffer(abyte0);
//_L8:
//		return;
//_L2:
//		if (abyte0.length != mExpectedFrameSize) goto _L4; else goto _L3
//_L3:
//		int i;
//		i = getDeviceOrientation();
//		if (i != mDeviceOrientation)
//		{
//			mDeviceOrientation = i;
//			Log.d("VideoCapture", (new StringBuilder()).append("onPreviewFrame: device orientation=").append(mDeviceOrientation).append(", camera orientation=").append(mCameraOrientation).toString());
//		}
//		if (mCameraFacing != 1) goto _L6; else goto _L5
//_L5:
//		int j = (360 - (i + mCameraOrientation) % 360) % 360;
//		boolean flag1;
//		boolean flag2;
//		Exception exception;
//		if (j != 270 && j != 90)
//			flag2 = false;
//		else
//			flag2 = true;
//		break MISSING_BLOCK_LABEL_257;
//_L9:
//		if (mImageFormat == 17)
//			convertNV21ToYV12(abyte0);
//		nativeOnFrameAvailable(mNativeVideoCaptureDeviceAndroid, abyte0, mExpectedFrameSize, j, flag1, flag2);
//_L4:
//		mPreviewBufferLock.unlock();
//		if (camera == null) goto _L8; else goto _L7
//_L7:
//		camera.addCallbackBuffer(abyte0);
//		return;
//_L6:
//		j = (360 + (mCameraOrientation - i)) % 360;
//		flag1 = false;
//		flag2 = false;
//		  goto _L9
//		exception;
//		mPreviewBufferLock.unlock();
//		if (camera != null)
//			camera.addCallbackBuffer(abyte0);
//		throw exception;
//		flag1 = flag2;
//		  goto _L9
	}

	public int queryFrameRate()
	{
		return mCurrentCapability.mDesiredFps;
	}

	public int queryHeight()
	{
		return mCurrentCapability.mHeight;
	}

	public int queryWidth()
	{
		return mCurrentCapability.mWidth;
	}

	public int startCapture()
	{
	    return 0;
//		if (mCamera == null)
//		{
//			Log.e("VideoCapture", "startCapture: camera is null");
//			return -1;
//		}
//		mPreviewBufferLock.lock();
//		boolean flag = mIsRunning;
//		if (flag)
//		{
//			mPreviewBufferLock.unlock();
//			return 0;
//		}
//		mIsRunning = true;
//		mPreviewBufferLock.unlock();
//		mCamera.setPreviewCallbackWithBuffer(this);
//		mCamera.startPreview();
//		return 0;
//		Exception exception;
//		exception;
//		mPreviewBufferLock.unlock();
//		throw exception;
	}

	public int stopCapture()
	{
	    return 0;
//		if (mCamera == null)
//		{
//			Log.e("VideoCapture", "stopCapture: camera is null");
//			return 0;
//		}
//		mPreviewBufferLock.lock();
//		boolean flag = mIsRunning;
//		if (!flag)
//		{
//			mPreviewBufferLock.unlock();
//			return 0;
//		}
//		mIsRunning = false;
//		mPreviewBufferLock.unlock();
//		mCamera.stopPreview();
//		mCamera.setPreviewCallbackWithBuffer(null);
//		return 0;
//		Exception exception;
//		exception;
//		mPreviewBufferLock.unlock();
//		throw exception;
	}
}
