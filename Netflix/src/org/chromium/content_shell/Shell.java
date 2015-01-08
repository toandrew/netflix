// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.content_shell;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import org.chromium.content.browser.*;
import org.chromium.ui.WindowAndroid;

public class Shell extends LinearLayout
{

	private static final long COMPLETED_PROGRESS_TIMEOUT_MS = 200L;
	private Runnable mClearProgressRunnable;
	private ContentView mContentView;
	private ContentViewRenderView mContentViewRenderView;
	private boolean mLoading;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private ClipDrawable mProgressDrawable;
	private EditText mUrlTextView;
	private WindowAndroid mWindow;

	public Shell(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		mClearProgressRunnable = new Runnable() {

			final Shell this$0;

			public void run()
			{
				mProgressDrawable.setLevel(0);
			}

			
			{
				this$0 = Shell.this;
				super();
			}
		};
		mLoading = false;
	}

	private void initFromNativeTabContents(int i)
	{
		mContentView = ContentView.newInstance(getContext(), i, mWindow);
		if (mContentView.getUrl() != null)
			mUrlTextView.setText(mContentView.getUrl());
		((FrameLayout)findViewById(R.id.contentview_holder)).addView(mContentView, new android.widget.FrameLayout.LayoutParams(-1, -1));
		mContentView.requestFocus();
		mContentViewRenderView.setCurrentContentView(mContentView);
	}

	private void initializeNavigationButtons()
	{
		mPrevButton = (ImageButton)findViewById(R.id.prev);
		mPrevButton.setOnClickListener(new android.view.View.OnClickListener() {

			final Shell this$0;

			public void onClick(View view)
			{
				if (mContentView.canGoBack())
					mContentView.goBack();
			}

			
			{
				this$0 = Shell.this;
				super();
			}
		});
		mNextButton = (ImageButton)findViewById(R.id.next);
		mNextButton.setOnClickListener(new android.view.View.OnClickListener() {

			final Shell this$0;

			public void onClick(View view)
			{
				if (mContentView.canGoForward())
					mContentView.goForward();
			}

			
			{
				this$0 = Shell.this;
				super();
			}
		});
	}

	private void initializeUrlField()
	{
		mUrlTextView = (EditText)findViewById(R.id.url);
		mUrlTextView.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {

			final Shell this$0;

			public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent)
			{
				if (i != 2 && (keyevent == null || keyevent.getKeyCode() != 66 || keyevent.getAction() != 0))
				{
					return false;
				} else
				{
					loadUrl(mUrlTextView.getText().toString());
					setKeyboardVisibilityForUrl(false);
					mContentView.requestFocus();
					return true;
				}
			}

			
			{
				this$0 = Shell.this;
				super();
			}
		});
		mUrlTextView.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

			final Shell this$0;

			public void onFocusChange(View view, boolean flag)
			{
				byte byte0 = 8;
				setKeyboardVisibilityForUrl(flag);
				ImageButton imagebutton = mNextButton;
				byte byte1;
				ImageButton imagebutton1;
				if (flag)
					byte1 = byte0;
				else
					byte1 = 0;
				imagebutton.setVisibility(byte1);
				imagebutton1 = mPrevButton;
				if (!flag)
					byte0 = 0;
				imagebutton1.setVisibility(byte0);
				if (!flag)
					mUrlTextView.setText(mContentView.getUrl());
			}

			
			{
				this$0 = Shell.this;
				super();
			}
		});
	}

	private boolean isFullscreenForTabOrPending()
	{
		return false;
	}

	private void onLoadProgressChanged(double d)
	{
		removeCallbacks(mClearProgressRunnable);
		mProgressDrawable.setLevel((int)(10000D * d));
		if (d == 1.0D)
			postDelayed(mClearProgressRunnable, 200L);
	}

	private void onUpdateUrl(String s)
	{
		mUrlTextView.setText(s);
	}

	public static String sanitizeUrl(String s)
	{
		while (s == null || !s.startsWith("www.") && s.indexOf(":") != -1) 
			return s;
		return (new StringBuilder()).append("http://").append(s).toString();
	}

	private void setIsLoading(boolean flag)
	{
		mLoading = flag;
	}

	private void setKeyboardVisibilityForUrl(boolean flag)
	{
		InputMethodManager inputmethodmanager = (InputMethodManager)getContext().getSystemService("input_method");
		if (flag)
		{
			inputmethodmanager.showSoftInput(mUrlTextView, 1);
			return;
		} else
		{
			inputmethodmanager.hideSoftInputFromWindow(mUrlTextView.getWindowToken(), 0);
			return;
		}
	}

	private void toggleFullscreenModeForTab(boolean flag)
	{
	}

	public ContentView getContentView()
	{
		return mContentView;
	}

	public boolean isLoading()
	{
		return mLoading;
	}

	public void loadUrl(String s)
	{
		if (s == null)
			return;
		if (TextUtils.equals(s, mContentView.getUrl()))
			mContentView.reload();
		else
			mContentView.loadUrl(new LoadUrlParams(sanitizeUrl(s)));
		mUrlTextView.clearFocus();
		mContentView.clearFocus();
		mContentView.requestFocus();
	}

	protected void onFinishInflate()
	{
		super.onFinishInflate();
		mProgressDrawable = (ClipDrawable)findViewById(R.id.toolbar).getBackground();
		initializeUrlField();
		initializeNavigationButtons();
	}

	public void setContentViewRenderView(ContentViewRenderView contentviewrenderview)
	{
		FrameLayout framelayout = (FrameLayout)findViewById(R.id.contentview_holder);
		if (contentviewrenderview == null)
		{
			if (mContentViewRenderView != null)
				framelayout.removeView(mContentViewRenderView);
		} else
		{
			framelayout.addView(contentviewrenderview, new android.widget.FrameLayout.LayoutParams(-1, -1));
		}
		mContentViewRenderView = contentviewrenderview;
	}

	public void setWindow(WindowAndroid windowandroid)
	{
		mWindow = windowandroid;
	}






}
