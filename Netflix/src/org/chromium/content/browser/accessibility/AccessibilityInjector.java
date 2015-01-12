// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser.accessibility;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.googlecode.eyesfree.braille.selfbraille.SelfBrailleClient;
import com.googlecode.eyesfree.braille.selfbraille.WriteData;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.chromium.content.browser.*;
import org.chromium.content.common.CommandLine;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package org.chromium.content.browser.accessibility:
//			JellyBeanAccessibilityInjector

public class AccessibilityInjector extends WebContentsObserverAndroid
{
	private static class TextToSpeechWrapper
	{

		private SelfBrailleClient mSelfBrailleClient;
		private TextToSpeech mTextToSpeech;
		private View mView;

		public void braille(String s)
		{
			try
			{
				JSONObject jsonobject = new JSONObject(s);
				WriteData writedata = WriteData.forView(mView);
				writedata.setText(jsonobject.getString("text"));
				writedata.setSelectionStart(jsonobject.getInt("startIndex"));
				writedata.setSelectionEnd(jsonobject.getInt("endIndex"));
				mSelfBrailleClient.write(writedata);
				return;
			}
			catch (JSONException jsonexception)
			{
				Log.w("AccessibilityInjector", "Error parsing JS JSON object", jsonexception);
			}
		}

		public boolean isSpeaking()
		{
			return mTextToSpeech.isSpeaking();
		}

		protected void shutdownInternal()
		{
			mTextToSpeech.shutdown();
			mSelfBrailleClient.shutdown();
		}

		public int speak(String s, int i, String s1)
		{
		    return 0;
//			HashMap hashmap = null;
//			if (s1 == null) goto _L2; else goto _L1
//_L1:
//			HashMap hashmap1 = new HashMap();
//			try
//			{
//				JSONObject jsonobject = new JSONObject(s1);
//				Iterator iterator = jsonobject.keys();
//				do
//				{
//					if (!iterator.hasNext())
//						break;
//					String s2 = (String)iterator.next();
//					if (jsonobject.optJSONObject(s2) == null && jsonobject.optJSONArray(s2) == null)
//						hashmap1.put(s2, jsonobject.getString(s2));
//				} while (true);
//				break MISSING_BLOCK_LABEL_115;
//			}
//			catch (JSONException jsonexception) { }
//_L3:
//			hashmap = null;
//_L2:
//			return mTextToSpeech.speak(s, i, hashmap);
//			JSONException jsonexception1;
//			jsonexception1;
//			  goto _L3
//			hashmap = hashmap1;
//			  goto _L2
		}

		public int stop()
		{
			return mTextToSpeech.stop();
		}

		public TextToSpeechWrapper(View view, Context context)
		{
			mView = view;
			mTextToSpeech = new TextToSpeech(context, null, null);
			mSelfBrailleClient = new SelfBrailleClient(context, CommandLine.getInstance().hasSwitch("debug-braille-service"));
		}
	}

	private static class VibratorWrapper
	{

		private static final long MAX_VIBRATE_DURATION_MS = 5000L;
		private Vibrator mVibrator;

		public void cancel()
		{
			mVibrator.cancel();
		}

		public boolean hasVibrator()
		{
			return mVibrator.hasVibrator();
		}

		public void vibrate(long l)
		{
			long l1 = Math.min(l, 5000L);
			mVibrator.vibrate(l1);
		}

		public void vibrate(long al[], int i)
		{
			for (int j = 0; j < al.length; j++)
				al[j] = Math.min(al[j], 5000L);

			mVibrator.vibrate(al, -1);
		}

		public VibratorWrapper(Context context)
		{
			mVibrator = (Vibrator)context.getSystemService("vibrator");
		}
	}


	private static final String ACCESSIBILITY_SCREEN_READER_JAVASCRIPT_TEMPLATE = "(function() {    var chooser = document.createElement('script');    chooser.type = 'text/javascript';    chooser.src = '%1s';    document.getElementsByTagName('head')[0].appendChild(chooser);  })();";
	private static final int ACCESSIBILITY_SCRIPT_INJECTION_OPTED_OUT = 0;
	private static final int ACCESSIBILITY_SCRIPT_INJECTION_PROVIDED = 1;
	private static final int ACCESSIBILITY_SCRIPT_INJECTION_UNDEFINED = -1;
	private static final String ALIAS_ACCESSIBILITY_JS_INTERFACE = "accessibility";
	private static final String ALIAS_ACCESSIBILITY_JS_INTERFACE_2 = "accessibility2";
	private static final String DEFAULT_ACCESSIBILITY_SCREEN_READER_URL = "https://ssl.gstatic.com/accessibility/javascript/android/chromeandroidvox.js";
	private static final int FEEDBACK_BRAILLE = 32;
	private static final String TAG = "AccessibilityInjector";
	private static final String TOGGLE_CHROME_VOX_JAVASCRIPT = "(function() {    if (typeof cvox !== 'undefined') {        cvox.ChromeVox.host.activateOrDeactivateChromeVox(%1s);    }  })();";
	private AccessibilityManager mAccessibilityManager;
	private final String mAccessibilityScreenReaderUrl = CommandLine.getInstance().getSwitchValue("accessibility-js-url", "https://ssl.gstatic.com/accessibility/javascript/android/chromeandroidvox.js");
	protected ContentViewCore mContentViewCore;
	private final boolean mHasVibratePermission;
	protected boolean mInjectedScriptEnabled;
	protected boolean mScriptInjected;
	private TextToSpeechWrapper mTextToSpeech;
	private VibratorWrapper mVibrator;

	protected AccessibilityInjector(ContentViewCore contentviewcore)
	{
		super(contentviewcore);
		mContentViewCore = contentviewcore;
		boolean flag;
		if (mContentViewCore.getContext().checkCallingOrSelfPermission("android.permission.VIBRATE") == 0)
			flag = true;
		else
			flag = false;
		mHasVibratePermission = flag;
	}

	private AccessibilityManager getAccessibilityManager()
	{
		if (mAccessibilityManager == null)
			mAccessibilityManager = (AccessibilityManager)mContentViewCore.getContext().getSystemService("accessibility");
		return mAccessibilityManager;
	}

	private int getAxsUrlParameterValue()
	{
	    return -1;
//		if (mContentViewCore.getUrl() != null) goto _L2; else goto _L1
//_L1:
//		return -1;
//_L2:
//		Iterator iterator = URLEncodedUtils.parse(new URI(mContentViewCore.getUrl()), null).iterator();
//_L5:
//		if (!iterator.hasNext()) goto _L1; else goto _L3
//_L3:
//		NameValuePair namevaluepair = (NameValuePair)iterator.next();
//		if (!"axs".equals(namevaluepair.getName())) goto _L5; else goto _L4
//_L4:
//		int i = Integer.parseInt(namevaluepair.getValue());
//		return i;
//		IllegalArgumentException illegalargumentexception;
//		illegalargumentexception;
//		return -1;
//		NumberFormatException numberformatexception;
//		numberformatexception;
//		return -1;
//		URISyntaxException urisyntaxexception;
//		urisyntaxexception;
//		return -1;
	}

	private String getScreenReaderInjectingJs()
	{
		Object aobj[] = new Object[1];
		aobj[0] = mAccessibilityScreenReaderUrl;
		return String.format("(function() {    var chooser = document.createElement('script');    chooser.type = 'text/javascript';    chooser.src = '%1s';    document.getElementsByTagName('head')[0].appendChild(chooser);  })();", aobj);
	}

	public static AccessibilityInjector newInstance(ContentViewCore contentviewcore)
	{
		if (android.os.Build.VERSION.SDK_INT < 16)
			return new AccessibilityInjector(contentviewcore);
		else
			return new JellyBeanAccessibilityInjector(contentviewcore);
	}

	public boolean accessibilityIsAvailable()
	{
		if (getAccessibilityManager().isEnabled() && mContentViewCore.getContentSettings() != null && mContentViewCore.getContentSettings().getJavaScriptEnabled())
		{
			int i;
			try
			{
				i = getAccessibilityManager().getEnabledAccessibilityServiceList(33).size();
			}
			catch (NullPointerException nullpointerexception)
			{
				return false;
			}
			if (i > 0)
				return true;
		}
		return false;
	}

	protected void addAccessibilityApis()
	{
		Context context = mContentViewCore.getContext();
		if (context != null)
		{
			if (mTextToSpeech == null)
			{
				mTextToSpeech = new TextToSpeechWrapper(mContentViewCore.getContainerView(), context);
				mContentViewCore.addJavascriptInterface(mTextToSpeech, "accessibility");
			}
			if (mVibrator == null && mHasVibratePermission)
			{
				mVibrator = new VibratorWrapper(context);
				mContentViewCore.addJavascriptInterface(mVibrator, "accessibility2");
			}
		}
	}

	public void addOrRemoveAccessibilityApisIfNecessary()
	{
		if (accessibilityIsAvailable())
		{
			addAccessibilityApis();
			return;
		} else
		{
			removeAccessibilityApis();
			return;
		}
	}

	public void didStartLoading(String s)
	{
		mScriptInjected = false;
	}

	public void didStopLoading(String s)
	{
		injectAccessibilityScriptIntoPage();
	}

	public void injectAccessibilityScriptIntoPage()
	{
		if (accessibilityIsAvailable() && getAxsUrlParameterValue() == -1)
		{
			String s = getScreenReaderInjectingJs();
			if (mContentViewCore.isDeviceAccessibilityScriptInjectionEnabled() && s != null && mContentViewCore.isAlive())
			{
				addOrRemoveAccessibilityApisIfNecessary();
				mContentViewCore.evaluateJavaScript(s, null);
				mInjectedScriptEnabled = true;
				mScriptInjected = true;
				return;
			}
		}
	}

	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilitynodeinfo)
	{
	}

	public void onPageLostFocus()
	{
		if (mContentViewCore.isAlive())
		{
			if (mTextToSpeech != null)
				mTextToSpeech.stop();
			if (mVibrator != null)
				mVibrator.cancel();
		}
	}

	public boolean performAccessibilityAction(int i, Bundle bundle)
	{
		return false;
	}

	protected void removeAccessibilityApis()
	{
		if (mTextToSpeech != null)
		{
			mContentViewCore.removeJavascriptInterface("accessibility");
			mTextToSpeech.stop();
			mTextToSpeech.shutdownInternal();
			mTextToSpeech = null;
		}
		if (mVibrator != null)
		{
			mContentViewCore.removeJavascriptInterface("accessibility2");
			mVibrator.cancel();
			mVibrator = null;
		}
	}

	public void setScriptEnabled(boolean flag)
	{
		if (flag && !mScriptInjected)
			injectAccessibilityScriptIntoPage();
		if (accessibilityIsAvailable() && mInjectedScriptEnabled != flag)
		{
			mInjectedScriptEnabled = flag;
			if (mContentViewCore.isAlive())
			{
				Object aobj[] = new Object[1];
				aobj[0] = Boolean.toString(mInjectedScriptEnabled);
				String s = String.format("(function() {    if (typeof cvox !== 'undefined') {        cvox.ChromeVox.host.activateOrDeactivateChromeVox(%1s);    }  })();", aobj);
				mContentViewCore.evaluateJavaScript(s, null);
				if (!mInjectedScriptEnabled)
				{
					onPageLostFocus();
					return;
				}
			}
		}
	}

	public boolean supportsAccessibilityAction(int i)
	{
		return false;
	}
}
