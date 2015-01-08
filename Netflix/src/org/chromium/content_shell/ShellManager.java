// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content_shell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.FrameLayout;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.chromium.content.browser.ContentView;
import org.chromium.content.browser.ContentViewRenderView;
import org.chromium.ui.WindowAndroid;

// Referenced classes of package org.chromium.content_shell:
//			Shell, SplashScreen

public class ShellManager extends FrameLayout
{
	private class CheckNetflixConnectivityTask extends AsyncTask
	{

		final ShellManager this$0;

		protected transient Boolean doInBackground(URL aurl[])
		{
			boolean flag = true;
			if ("file:///sdcard/NeroStartUrl.html".equals(aurl[0].toString()))
				return Boolean.valueOf(flag);
			int i;
			Boolean boolean1;
			try
			{
				HttpURLConnection httpurlconnection = (HttpURLConnection)aurl[0].openConnection();
				HttpURLConnection.setFollowRedirects(true);
				httpurlconnection.setRequestProperty("Accept-Encoding", "");
				httpurlconnection.setRequestMethod("HEAD");
				i = httpurlconnection.getResponseCode();
			}
			catch (Exception exception)
			{
				Log.e("ShellManager", exception.getMessage());
				exception.printStackTrace();
				return Boolean.valueOf(false);
			}
			if (200 > i || i > 399)
				flag = false;
			boolean1 = Boolean.valueOf(flag);
			return boolean1;
		}

		protected volatile Object doInBackground(Object aobj[])
		{
			return doInBackground((URL[])aobj);
		}

		private CheckNetflixConnectivityTask()
		{
			this$0 = ShellManager.this;
			super();
		}

	}


	public static final String ACTION_CONNECTIVITY_LOST = "com.amazon.tv.networkmonitor.CONNECTIVITY_LOST";
	public static final String ACTION_NETFLIX_CONNECTIVITY_LOST = "org.chromium.content_shell_apk.action.ACTION_NETFLIX_CONNECTIVITY_LOST";
	public static final String ARGS_FILE = "/sdcard/NeroArgsUrl.txt";
	public static final String DEFAULT_FILE = "/sdcard/NeroStartUrl.html";
	public static final String DEFAULT_FILE_URL = "file:///sdcard/NeroStartUrl.html";
	public static final String DEFAULT_SHELL_URL = "https://uiboot.netflix.com/apps/cadmiumtvui/upgrade_policy?v=2.0&first_boot=true&platform_id=nero";
	private static final String DEFAULT_SHELL_URL_FORMAT = "https://uiboot.netflix.com/apps/cadmiumtvui/upgrade_policy?v=2.0&first_boot=true&platform_id=nero&version_release=%s&version_incremental=%s&build_id=%s&build_time=%s&nettype=%s&video_height=%s&video_width=%s";
	private static final String EXTRA_FORCE_UI = "com.amazon.tv.networkmonitor.FORCE_UI";
	public static final String EXTRA_ON_TRY_AGAIN = "org.chromium.content_shell_apk.extra.EXTRA_ON_TRY_AGAIN";
	private static final String EXTRA_SENDER_PKG_NAME = "com.amazon.tv.networkmonitor.SENDER_PKG_NAME";
	public static final String ON_TRY_AGAIN_RELOAD_STARTUP_URL = "on_try_again_reload_startup_url";
	private static final String TAG = "ShellManager";
	private static boolean sStartup = true;
	private Shell mActiveShell;
	private Activity mActivity;
	private ContentViewRenderView mContentViewRenderView;
	private SplashScreen mSplash;
	private String mStartupUrl;
	private WindowAndroid mWindow;

	public ShellManager(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		mSplash = null;
		mStartupUrl = "https://uiboot.netflix.com/apps/cadmiumtvui/upgrade_policy?v=2.0&first_boot=true&platform_id=nero";
		mActivity = (Activity)context;
		nativeInit(this);
		mContentViewRenderView = new ContentViewRenderView(context) {

			final ShellManager this$0;

			protected void onReadyToRender()
			{
				if (ShellManager.sStartup)
				{
					mStartupUrl = getDefaultStartupUrl();
					loadStartupUrl();
				}
			}

			
			{
				this$0 = ShellManager.this;
				super(context);
			}
		};
	}

	private void closeShell(Shell shell)
	{
		if (shell == mActiveShell)
			mActiveShell = null;
		ContentView contentview = shell.getContentView();
		FrameLayout framelayout = (FrameLayout)findViewById(R.id.contentview_holder);
		if (contentview != null)
		{
			contentview.destroy();
			framelayout.removeView(contentview);
		}
		if (shell != null)
		{
			shell.setContentViewRenderView(null);
			shell.setWindow(null);
			removeView(shell);
		}
		mActivity.finish();
	}

	private Object createShell()
	{
		Shell shell = (Shell)((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(R.layout.shell_view, null);
		shell.setWindow(mWindow);
		if (mActiveShell != null)
			closeShell(mActiveShell);
		shell.setContentViewRenderView(mContentViewRenderView);
		addView(shell, new android.widget.FrameLayout.LayoutParams(-1, -1));
		mActiveShell = shell;
		ContentView contentview = mActiveShell.getContentView();
		if (contentview != null)
		{
			mContentViewRenderView.setCurrentContentView(contentview);
			contentview.onShow();
		}
		return shell;
	}

	private static native void nativeCloseAllWindows();

	private static native void nativeInit(Object obj);

	private static native void nativeLaunchShell(String s);

	public void broadcastNetflixConnectivityLost(String s)
	{
		Intent intent = new Intent("org.chromium.content_shell_apk.action.ACTION_NETFLIX_CONNECTIVITY_LOST");
		intent.putExtra("org.chromium.content_shell_apk.extra.EXTRA_ON_TRY_AGAIN", s);
		getContext().sendBroadcast(intent);
	}

	public boolean canConnectToNetflix()
	{
		NetworkInfo networkinfo = getNetworkInfo();
		if (networkinfo == null || !networkinfo.isConnected())
			return false;
		boolean flag;
		try
		{
			CheckNetflixConnectivityTask checknetflixconnectivitytask = new CheckNetflixConnectivityTask();
			URL aurl[] = new URL[1];
			aurl[0] = new URL(mStartupUrl);
			flag = ((Boolean)checknetflixconnectivitytask.execute(aurl).get()).booleanValue();
		}
		catch (Exception exception)
		{
			Log.e("ShellManager", exception.getMessage());
			exception.printStackTrace();
			return false;
		}
		return flag;
	}

	public void closeAllWindows()
	{
		nativeCloseAllWindows();
	}

	public void createSplashScreen()
	{
		if (mSplash == null)
		{
			mSplash = new SplashScreen(mActivity);
			mActivity.getWindow().addContentView(mSplash, new android.widget.FrameLayout.LayoutParams(-1, -1, 17));
		}
	}

	public void destroySplashScreen()
	{
		if (mSplash != null)
		{
			mSplash.destroy();
			((FrameLayout)mActivity.getWindow().getDecorView()).removeView(mSplash);
			mSplash = null;
		}
	}

	public Shell getActiveShell()
	{
		return mActiveShell;
	}

	public String getDefaultStartupUrl()
	{
		if ((new File("/sdcard/NeroStartUrl.html")).length() <= 0L) goto _L2; else goto _L1
_L1:
		String s2 = "file:///sdcard/NeroStartUrl.html";
_L4:
		return s2;
_L2:
		NetworkInfo networkinfo = getNetworkInfo();
		if (networkinfo == null || !networkinfo.isConnected())
		{
			Log.e("ShellManager", "Connectivity lost, cannot load page.");
			Intent intent = new Intent("com.amazon.tv.networkmonitor.CONNECTIVITY_LOST");
			intent.putExtra("com.amazon.tv.networkmonitor.FORCE_UI", true);
			intent.putExtra("com.amazon.tv.networkmonitor.SENDER_PKG_NAME", getContext().getPackageName());
			getContext().sendBroadcast(intent);
			return null;
		}
		String s = "ETHERNET";
		if (networkinfo.getType() == 1)
			s = "WIFI";
		String s1 = Build.DISPLAY;
		String as[] = s1.split(" ");
		boolean flag = false;
		int i = as.length;
		int j = 0;
		do
		{
			String s4;
label0:
			{
				if (j < i)
				{
					s4 = as[j];
					if (!flag)
						break label0;
					s1 = s4;
				}
				Log.w("DEFAULT_FILE_URL empty or not found: ", "file:///sdcard/NeroStartUrl.html");
				Object aobj[] = new Object[7];
				aobj[0] = android.os.Build.VERSION.RELEASE;
				aobj[1] = "1.0";
				aobj[2] = s1;
				aobj[3] = String.valueOf(Build.TIME);
				aobj[4] = s;
				aobj[5] = "1080";
				aobj[6] = "1920";
				s2 = String.format("https://uiboot.netflix.com/apps/cadmiumtvui/upgrade_policy?v=2.0&first_boot=true&platform_id=nero&version_release=%s&version_incremental=%s&build_id=%s&build_time=%s&nettype=%s&video_height=%s&video_width=%s", aobj);
				String s3 = getStartupArgsUrl();
				if (!s3.isEmpty())
				{
					StringBuilder stringbuilder = new StringBuilder();
					stringbuilder.append(s2);
					stringbuilder.append(s3);
					return stringbuilder.toString();
				}
			}
			if (true)
				continue;
			if (s4.equals(android.os.Build.VERSION.RELEASE))
				flag = true;
			j++;
		} while (true);
		if (true) goto _L4; else goto _L3
_L3:
	}

	public NetworkInfo getNetworkInfo()
	{
		return ((ConnectivityManager)mActivity.getSystemService("connectivity")).getActiveNetworkInfo();
	}

	public String getStartupArgsUrl()
	{
		File file;
		StringBuilder stringbuilder;
		file = new File("/sdcard/NeroArgsUrl.txt");
		stringbuilder = new StringBuilder();
		if (file.length() == 0L)
			break MISSING_BLOCK_LABEL_104;
		BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
_L1:
		String s = bufferedreader.readLine();
		if (s == null)
			break MISSING_BLOCK_LABEL_104;
		stringbuilder.append('&');
		stringbuilder.append(s);
		  goto _L1
		Exception exception;
		exception;
		Log.e("Error while reading the args file: /sdcard/NeroArgsUrl.txt", (new StringBuilder()).append("Exception :").append(exception.toString()).toString());
		return stringbuilder.toString();
	}

	public WindowAndroid getWindow()
	{
		return mWindow;
	}

	public boolean isStartingUp()
	{
		return sStartup;
	}

	public void launchShell(String s)
	{
		nativeLaunchShell(s);
	}

	public void loadStartupUrl()
	{
		FrameLayout framelayout = (FrameLayout)findViewById(R.id.contentview_holder);
		if (framelayout != null)
			framelayout.setY(getHeight());
		createSplashScreen();
		if (mStartupUrl == null)
		{
			return;
		} else
		{
			(new Handler()).postDelayed(new Runnable() {

				final ShellManager this$0;

				public void run()
				{
					if (canConnectToNetflix())
					{
						Log.i("Loading Startup Url: ", mStartupUrl);
						mActiveShell.loadUrl(mStartupUrl);
						ShellManager.sStartup = false;
						return;
					} else
					{
						broadcastNetflixConnectivityLost("on_try_again_reload_startup_url");
						return;
					}
				}

			
			{
				this$0 = ShellManager.this;
				super();
			}
			}, 1L);
			return;
		}
	}

	public void setStartupUrl(String s)
	{
		mStartupUrl = s;
	}

	public void setWindow(WindowAndroid windowandroid)
	{
		mWindow = windowandroid;
	}




/*
	static boolean access$002(boolean flag)
	{
		sStartup = flag;
		return flag;
	}

*/



/*
	static String access$102(ShellManager shellmanager, String s)
	{
		shellmanager.mStartupUrl = s;
		return s;
	}

*/

}
