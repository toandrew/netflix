// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.util.*;

// Referenced classes of package org.chromium.ui:
//			WindowAndroid

class SelectFileDialog
	implements WindowAndroid.IntentCallback
{

	private static final String ALL_AUDIO_TYPES = "audio/*";
	private static final String ALL_IMAGE_TYPES = "image/*";
	private static final String ALL_VIDEO_TYPES = "video/*";
	private static final String ANY_TYPES = "*/*";
	private static final String AUDIO_TYPE = "audio/";
	private static final String CAPTURE_IMAGE_DIRECTORY = "browser-photos";
	private static final String IMAGE_TYPE = "image/";
	private static final String VIDEO_TYPE = "video/";
	private Uri mCameraOutputUri;
	private boolean mCapture;
	private List mFileTypes;
	private final int mNativeSelectFileDialog;

	private SelectFileDialog(int i)
	{
		mNativeSelectFileDialog = i;
	}

	private boolean acceptSpecificType(String s)
	{
		for (Iterator iterator = mFileTypes.iterator(); iterator.hasNext();)
			if (((String)iterator.next()).startsWith(s))
				return true;

		return false;
	}

	private boolean acceptsSpecificType(String s)
	{
		return mFileTypes.size() == 1 && TextUtils.equals((CharSequence)mFileTypes.get(0), s);
	}

	private boolean captureCamcorder()
	{
		return mCapture && acceptsSpecificType("video/*");
	}

	private boolean captureCamera()
	{
		return mCapture && acceptsSpecificType("image/*");
	}

	private boolean captureMicrophone()
	{
		return mCapture && acceptsSpecificType("audio/*");
	}

	private static SelectFileDialog create(int i)
	{
		return new SelectFileDialog(i);
	}

	private File getFileForImageCapture()
	{
		File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		File file1 = new File((new StringBuilder()).append(file.getAbsolutePath()).append(File.separator).append("browser-photos").toString());
		if (!file1.exists() && !file1.mkdirs())
			file1 = file;
		return new File((new StringBuilder()).append(file1.getAbsolutePath()).append(File.separator).append(System.currentTimeMillis()).append(".jpg").toString());
	}

	private native void nativeOnFileNotSelected(int i);

	private native void nativeOnFileSelected(int i, String s);

	private boolean noSpecificType()
	{
		return mFileTypes.size() != 1 || mFileTypes.contains("*/*");
	}

	private void onFileNotSelected()
	{
		nativeOnFileNotSelected(mNativeSelectFileDialog);
	}

	private void selectFile(String as[], boolean flag, WindowAndroid windowandroid)
	{
//		Intent intent;
//		Intent intent1;
//		Intent intent2;
//		Intent intent3;
//		mFileTypes = new ArrayList(Arrays.asList(as));
//		mCapture = flag;
//		intent = new Intent("android.intent.action.CHOOSER");
//		intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
//		mCameraOutputUri = Uri.fromFile(getFileForImageCapture());
//		intent1.putExtra("output", mCameraOutputUri);
//		intent2 = new Intent("android.media.action.VIDEO_CAPTURE");
//		intent3 = new Intent("android.provider.MediaStore.RECORD_SOUND");
//		if (!captureCamera()) goto _L2; else goto _L1
//_L1:
//		if (!windowandroid.showIntent(intent1, this, R.string.low_memory_error)) goto _L4; else goto _L3
//_L3:
//		return;
//_L2:
//		if (!captureCamcorder()) goto _L6; else goto _L5
//_L5:
//		if (windowandroid.showIntent(intent2, this, R.string.low_memory_error)) goto _L7; else goto _L4
//_L4:
//		Intent intent4 = new Intent("android.intent.action.GET_CONTENT");
//		intent4.addCategory("android.intent.category.OPENABLE");
//		ArrayList arraylist = new ArrayList();
//		if (!noSpecificType())
//			if (shouldShowImageTypes())
//			{
//				arraylist.add(intent1);
//				intent4.setType("image/*");
//			} else
//			if (shouldShowVideoTypes())
//			{
//				arraylist.add(intent2);
//				intent4.setType("video/*");
//			} else
//			if (shouldShowAudioTypes())
//			{
//				arraylist.add(intent3);
//				intent4.setType("audio/*");
//			}
//		if (arraylist.isEmpty())
//		{
//			intent4.setType("*/*");
//			arraylist.add(intent1);
//			arraylist.add(intent2);
//			arraylist.add(intent3);
//		}
//		intent.putExtra("android.intent.extra.INITIAL_INTENTS", (android.os.Parcelable[])arraylist.toArray(new Intent[0]));
//		intent.putExtra("android.intent.extra.INTENT", intent4);
//		if (!windowandroid.showIntent(intent, this, R.string.low_memory_error))
//		{
//			onFileNotSelected();
//			return;
//		}
//_L7:
//		if (true) goto _L3; else goto _L6
//_L6:
//		if (captureMicrophone() && windowandroid.showIntent(intent3, this, R.string.low_memory_error))
//			return;
//		  goto _L4
	}

	private boolean shouldShowAudioTypes()
	{
		return shouldShowTypes("audio/*", "audio/");
	}

	private boolean shouldShowImageTypes()
	{
		return shouldShowTypes("image/*", "image/");
	}

	private boolean shouldShowTypes(String s, String s1)
	{
		if (noSpecificType() || mFileTypes.contains(s))
			return true;
		else
			return acceptSpecificType(s1);
	}

	private boolean shouldShowVideoTypes()
	{
		return shouldShowTypes("video/*", "video/");
	}

	public void onIntentCompleted(WindowAndroid windowandroid, int i, ContentResolver contentresolver, Intent intent)
	{
//		if (i == -1) goto _L2; else goto _L1
//_L1:
//		onFileNotSelected();
//_L4:
//		return;
//_L2:
//		boolean flag;
//		if (intent != null)
//			break; /* Loop/switch isn't completed */
//		nativeOnFileSelected(mNativeSelectFileDialog, mCameraOutputUri.getPath());
//		flag = true;
//		windowandroid.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", mCameraOutputUri));
//_L5:
//		if (!flag)
//		{
//			onFileNotSelected();
//			windowandroid.showError(R.string.opening_file_error);
//			return;
//		}
//		if (true) goto _L4; else goto _L3
//_L3:
//		Cursor cursor = contentresolver.query(intent.getData(), new String[] {
//			"_data"
//		}, null, null, null);
//		flag = false;
//		if (cursor != null)
//		{
//			int j = cursor.getCount();
//			flag = false;
//			if (j == 1)
//			{
//				cursor.moveToFirst();
//				String s = cursor.getString(0);
//				flag = false;
//				if (s != null)
//				{
//					nativeOnFileSelected(mNativeSelectFileDialog, s);
//					flag = true;
//				}
//			}
//			cursor.close();
//		}
//		  goto _L5
//		if (true) goto _L4; else goto _L6
//_L6:
	}
}
