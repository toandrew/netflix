// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content.browser;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.amazon.webcrypto.WebCrypto;

// Referenced classes of package org.chromium.content.browser:
//			WebContentsObserverAndroid, ContentViewCore, ContentSettings

public class WebCryptoJavascriptInterface extends WebContentsObserverAndroid
{
	private static class WCJSInterface
	{

		private boolean mEnabled;
		private WebCrypto mWC;

		public void log(String s)
		{
		}

		public void measure(long l)
		{
		}

		public void postMessage(String s)
		{
			if (mEnabled)
				mWC.processReq(s);
		}

		public void setEnabled(boolean flag)
		{
			mEnabled = flag;
		}

		public WCJSInterface(WebCrypto webcrypto)
		{
			mEnabled = false;
			mWC = webcrypto;
		}
	}


	private static final boolean DEBUG = true;
	private static final String NAMESPACE = "NfWebCrypto";
	private static final String TAG = "WebCryptoJavascriptInterface";
	private static WebCryptoJavascriptInterface mInstance = null;
	protected ContentViewCore mContentViewCore;
	private boolean mEnabled;
	private WCJSInterface mWCJSInterface;
	private WebCrypto mWebCrypto;

	public WebCryptoJavascriptInterface(ContentViewCore contentviewcore)
	{
		super(contentviewcore);
		mEnabled = false;
		mContentViewCore = null;
		mWebCrypto = null;
		mWCJSInterface = null;
		mContentViewCore = contentviewcore;
		Log.w("WebCryptoJavascriptInterface", "WebCryptoJavascriptInterface created");
	}

	public static WebCryptoJavascriptInterface newInstance(ContentViewCore contentviewcore)
	{
		return new WebCryptoJavascriptInterface(contentviewcore);
	}

	public void addOrRemoveWebCryptoJavascriptInterface()
	{
		if (webCryptoJavascriptInterfaceIsAvailable())
		{
			addWebCryptoJavascriptInterface();
			return;
		} else
		{
			removeWebCryptoJavascriptInterface();
			return;
		}
	}

	protected void addWebCryptoJavascriptInterface()
	{
		Log.w("WebCryptoJavascriptInterface", "addWebCryptoJavascriptInterface called");
		if (mWebCrypto != null)
		{
			Log.w("WebCryptoJavascriptInterface", "Try to create mWebCrypto again!!!");
			if (mWCJSInterface != null)
			{
				Log.w("WebCryptoJavascriptInterface", "Try to create mWebCrypto again, attach interface!!!");
				mContentViewCore.removeJavascriptInterface("NfWebCrypto");
				mContentViewCore.addJavascriptInterface(mWCJSInterface, "NfWebCrypto");
				return;
			} else
			{
				Log.w("WebCryptoJavascriptInterface", "mWCJSInterface is null!!!");
				return;
			}
		} else
		{
			final Context context = mContentViewCore.getContext();
			mWebCrypto = new WebCrypto(context, new com.amazon.webcrypto.WebCrypto.HandleWebcryptoResponseInterface() {

				final WebCryptoJavascriptInterface this$0;
				final Context val$context;

				public void onHandleResponse(String s)
				{
					((Activity)context).runOnUiThread(s. new Runnable() {

						final 1 this$1;
						final String val$jsonResponse;

						public void run()
						{
							try
							{
								if (mEnabled)
									mContentViewCore.evaluateJavaScript((new StringBuilder()).append("if(window.NfWebCrypto_onmessage) { window.NfWebCrypto_onmessage('").append(jsonResponse).append("');} else { window.postMessage('").append(jsonResponse).append("', '*');}").toString(), null);
								return;
							}
							catch (Exception exception)
							{
								Log.w("WebCryptoJavascriptInterface", "app is exiting, evaluateJavaScript is done");
							}
						}

			
			{
				this$1 = final_1_1;
				jsonResponse = String.this;
				super();
			}
					});
				}

			
			{
				this$0 = WebCryptoJavascriptInterface.this;
				context = context1;
				super();
			}
			});
			Log.w("WebCryptoJavascriptInterface", "new WebCrypto done");
			mWCJSInterface = new WCJSInterface(mWebCrypto);
			mWCJSInterface.setEnabled(true);
			Log.w("WebCryptoJavascriptInterface", "new WCJSInterface done");
			mContentViewCore.addJavascriptInterface(mWCJSInterface, "NfWebCrypto");
			Log.w("WebCryptoJavascriptInterface", "addJavascriptInterface done");
			return;
		}
	}

	public void close()
	{
		if (mWebCrypto != null)
		{
			Log.w("WebCryptoJavascriptInterface", "WebCryptoJavascriptInterface mWebcypto stop called");
			mWebCrypto.stop();
		}
	}

	public void didStartLoading(String s)
	{
		Log.e("WebCryptoJavascriptInterface", (new StringBuilder()).append("didStartLoading ").append(s).append(" ").append(mContentViewCore).toString());
		mEnabled = false;
		if (mWCJSInterface != null)
			mWCJSInterface.setEnabled(false);
		if (s.matches("^https:\\/\\/([a-z0-9\\-]+\\.)*netflix\\.com(:\\d+){0,1}((\\/.*)|)$"))
		{
			mEnabled = true;
			if (mWCJSInterface != null)
				mWCJSInterface.setEnabled(true);
			injectWebCryptoJavascriptInterfaceIntoPage();
		}
	}

	public void didStopLoading(String s)
	{
		Log.e("WebCryptoJavascriptInterface", (new StringBuilder()).append("didStopLoading ").append(s).append(" ").append(mContentViewCore).toString());
	}

	public void injectWebCryptoJavascriptInterfaceIntoPage()
	{
		Log.w("WebCryptoJavascriptInterface", "injectWebCryptoJavascriptInterfaceIntoPage called");
		while (!webCryptoJavascriptInterfaceIsAvailable() || !mContentViewCore.isAlive()) 
			return;
		addOrRemoveWebCryptoJavascriptInterface();
	}

	protected void removeWebCryptoJavascriptInterface()
	{
		if (mContentViewCore != null)
		{
			Log.w("WebCryptoJavascriptInterface", "removeWebCryptoJavascriptInterface done1");
			mContentViewCore.removeJavascriptInterface("NfWebCrypto");
			Log.w("WebCryptoJavascriptInterface", "removeWebCryptoJavascriptInterface done2");
		}
	}

	public boolean webCryptoJavascriptInterfaceIsAvailable()
	{
		return mContentViewCore.getContentSettings() != null && mContentViewCore.getContentSettings().getJavaScriptEnabled();
	}


}
