// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.chromium.base.PathUtils;
import org.chromium.ui.LocalizationUtils;

public class ResourceExtractor
{
	private class ExtractTask extends AsyncTask
	{

		private static final int BUFFER_SIZE = 16384;

		private String checkPakTimestamp()
		{
			PackageManager packagemanager = mContext.getPackageManager();
			PackageInfo packageinfo;
			String s;
			try
			{
				packageinfo = packagemanager.getPackageInfo(mContext.getPackageName(), 0);
			}
			catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
			{
				return "pak_timestamp-";
			}
			if (packageinfo == null)
			{
				s = "pak_timestamp-";
			} else
			{
				s = (new StringBuilder()).append("pak_timestamp-").append(packageinfo.versionCode).append("-").append(packageinfo.lastUpdateTime).toString();
				String as[] = mOutputDir.list(new FilenameFilter() {
					public boolean accept(File file, String s)
					{
						return s.startsWith("pak_timestamp-");
					}
				});
				if (as.length == 1 && s.equals(as[0]))
					return null;
			}
			return s;
		}


//		protected  Void doInBackground(Void avoid[])
//		{
//			String s;
//			SharedPreferences sharedpreferences;
//			HashSet hashset;
//			int l;
//			String s2;
//			File file;
//			InputStream inputstream;
//			Exception exception;
//			FileOutputStream fileoutputstream;
//			FileOutputStream fileoutputstream1;
//			if (!mOutputDir.exists() && !mOutputDir.mkdirs())
//			{
//				Log.e("ResourceExtractor", "Unable to create pak resources directory!");
//				return null;
//			}
//			s = checkPakTimestamp();
//			if (s != null)
//				ResourceExtractor.deleteFiles(mContext);
//			sharedpreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//			hashset = (HashSet)sharedpreferences.getStringSet("Pak filenames", new HashSet());
//			String s1 = LocalizationUtils.getDefaultLocale().split("-", 2)[0];
//			if (sharedpreferences.getString("Last language", "").equals(s1) && hashset.size() >= ResourceExtractor.sMandatoryPaks.length)
//			{
//				boolean flag1 = true;
//				Iterator iterator = hashset.iterator();
//				do
//				{
//					if (!iterator.hasNext())
//						break;
//					String s4 = (String)iterator.next();
//					File file2 = new File(mOutputDir, s4);
//					if (file2.exists())
//						continue;
//					flag1 = false;
//					break;
//				} while (true);
//				if (flag1)
//					return null;
//			} else
//			{
//				sharedpreferences.edit().putString("Last language", s1).apply();
//			}
//			StringBuilder stringbuilder = new StringBuilder();
//			String as[] = ResourceExtractor.sMandatoryPaks;
//			int i = as.length;
//			for (int j = 0; j < i; j++)
//			{
//				String s3 = as[j];
//				if (stringbuilder.length() > 0)
//					stringbuilder.append('|');
//				stringbuilder.append((new StringBuilder()).append("\\Q").append(s3).append("\\E").toString());
//			}
//
//			if (ResourceExtractor.sExtractImplicitLocalePak)
//			{
//				if (stringbuilder.length() > 0)
//					stringbuilder.append('|');
//				stringbuilder.append(s1);
//				stringbuilder.append("(-\\w+)?\\.pak");
//			}
//			Pattern pattern = Pattern.compile(stringbuilder.toString());
//			AssetManager assetmanager = mContext.getResources().getAssets();
//			byte abyte0[] = null;
//			String as1[];
//			int k;
//			boolean flag;
//			int i1;
//			try
//			{
//				as1 = assetmanager.list("");
//				k = as1.length;
//			}
//			catch (IOException ioexception)
//			{
//				Log.w("ResourceExtractor", (new StringBuilder()).append("Exception unpacking required pak resources: ").append(ioexception.getMessage()).toString());
//				ResourceExtractor.deleteFiles(mContext);
//				return null;
//			}
//			l = 0;
//_L11:
//			if (l >= k) goto _L2; else goto _L1
//_L1:
//			s2 = as1[l];
//			if (pattern.matcher(s2).matches()) goto _L4; else goto _L3
//_L4:
//			file = new File(mOutputDir, s2);
//			flag = file.exists();
//			if (flag) goto _L3; else goto _L5
//_L5:
//			inputstream = null;
//			inputstream = assetmanager.open(s2);
//			fileoutputstream1 = new FileOutputStream(file);
//			Log.i("ResourceExtractor", (new StringBuilder()).append("Extracting resource ").append(s2).toString());
//			if (abyte0 != null)
//				break MISSING_BLOCK_LABEL_513;
//			abyte0 = new byte[16384];
//_L8:
//			i1 = inputstream.read(abyte0, 0, 16384);
//			if (i1 == -1) goto _L7; else goto _L6
//_L6:
//			fileoutputstream1.write(abyte0, 0, i1);
//			  goto _L8
//			exception;
//			fileoutputstream = fileoutputstream1;
//_L9:
//			if (inputstream == null)
//				break MISSING_BLOCK_LABEL_561;
//			inputstream.close();
//			if (fileoutputstream == null)
//				break MISSING_BLOCK_LABEL_571;
//			fileoutputstream.close();
//			throw exception;
//_L7:
//			fileoutputstream1.flush();
//			if (file.length() == 0L)
//				throw new IOException((new StringBuilder()).append(s2).append(" extracted with 0 length!").toString());
//			hashset.add(s2);
//			if (inputstream == null)
//				break MISSING_BLOCK_LABEL_680;
//			inputstream.close();
//			if (fileoutputstream1 == null)
//				break; /* Loop/switch isn't completed */
//			fileoutputstream1.close();
//			break; /* Loop/switch isn't completed */
//			Exception exception2;
//			exception2;
//			if (fileoutputstream1 == null)
//				break MISSING_BLOCK_LABEL_705;
//			fileoutputstream1.close();
//			throw exception2;
//			Exception exception1;
//			exception1;
//			if (fileoutputstream == null)
//				break MISSING_BLOCK_LABEL_720;
//			fileoutputstream.close();
//			throw exception1;
//_L2:
//			if (s != null)
//				try
//				{
//					File file1 = new File(mOutputDir, s);
//					file1.createNewFile();
//				}
//				catch (IOException ioexception1)
//				{
//					Log.w("ResourceExtractor", "Failed to write resource pak timestamp!");
//				}
//			sharedpreferences.edit().remove("Pak filenames").apply();
//			sharedpreferences.edit().putStringSet("Pak filenames", hashset).apply();
//			return null;
//			exception;
//			fileoutputstream = null;
//			if (true) goto _L9; else goto _L3
//_L3:
//			l++;
//			if (true) goto _L11; else goto _L10
//_L10:
//		}


        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            return null;
        }
	}


	static final boolean $assertionsDisabled = false;
	private static final String LAST_LANGUAGE = "Last language";
	private static final String LOGTAG = "ResourceExtractor";
	private static final String PAK_FILENAMES = "Pak filenames";
	private static boolean sExtractImplicitLocalePak = true;
	private static ResourceExtractor sInstance;
	private static String sMandatoryPaks[] = null;
	private Context mContext;
	private ExtractTask mExtractTask;
	private File mOutputDir;

	private ResourceExtractor(Context context)
	{
		mContext = context;
		mOutputDir = getOutputDirFromContext(mContext);
	}

	public static void deleteFiles(Context context)
	{
		File file = getOutputDirFromContext(context);
		if (file.exists())
		{
			File afile[] = file.listFiles();
			int i = afile.length;
			for (int j = 0; j < i; j++)
			{
				File file1 = afile[j];
				if (!file1.delete())
					Log.w("ResourceExtractor", (new StringBuilder()).append("Unable to remove existing resource ").append(file1.getName()).toString());
			}

		}
	}

	public static ResourceExtractor get(Context context)
	{
		if (sInstance == null)
			sInstance = new ResourceExtractor(context);
		return sInstance;
	}

	public static File getOutputDirFromContext(Context context)
	{
		return new File(PathUtils.getDataDirectory(context.getApplicationContext()), "paks");
	}

	public static void setExtractImplicitLocaleForTesting(boolean flag)
	{
		if (!$assertionsDisabled && sInstance != null && sInstance.mExtractTask != null)
		{
			throw new AssertionError("Must be called before startExtractingResources is called");
		} else
		{
			sExtractImplicitLocalePak = flag;
			return;
		}
	}

	public static void setMandatoryPaksToExtract(String as[])
	{
		if (!$assertionsDisabled && sInstance != null && sInstance.mExtractTask != null)
		{
			throw new AssertionError("Must be called before startExtractingResources is called");
		} else
		{
			sMandatoryPaks = as;
			return;
		}
	}

	private static boolean shouldSkipPakExtraction()
	{
		if (!$assertionsDisabled && sMandatoryPaks == null)
			throw new AssertionError();
		return sMandatoryPaks.length == 1 && "".equals(sMandatoryPaks[0]);
	}

	public void startExtractingResources()
	{
		while (mExtractTask != null || shouldSkipPakExtraction()) 
			return;
		mExtractTask = new ExtractTask();
		mExtractTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
	}

	public void waitForCompletion()
	{
		if (shouldSkipPakExtraction())
			return;
		if (!$assertionsDisabled && mExtractTask == null)
			throw new AssertionError();
		try
		{
			mExtractTask.get();
			return;
		}
		catch (CancellationException cancellationexception)
		{
			deleteFiles(mContext);
			return;
		}
		catch (ExecutionException executionexception)
		{
			deleteFiles(mContext);
			return;
		}
		catch (InterruptedException interruptedexception)
		{
			deleteFiles(mContext);
		}
	}

	static 
	{
//		boolean flag;
//		if (!org/chromium/content/browser/ResourceExtractor.desiredAssertionStatus())
//			flag = true;
//		else
//			flag = false;
		//$assertionsDisabled = false;
	}




}
