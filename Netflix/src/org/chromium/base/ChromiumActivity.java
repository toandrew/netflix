// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.base;

import android.app.Activity;
import android.os.Bundle;

// Referenced classes of package org.chromium.base:
//			ActivityStatus

public class ChromiumActivity extends Activity
{

	public ChromiumActivity()
	{
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		ActivityStatus.onStateChange(this, 1);
	}

	protected void onDestroy()
	{
		ActivityStatus.onStateChange(this, 6);
		super.onDestroy();
	}

	protected void onPause()
	{
		ActivityStatus.onStateChange(this, 4);
		super.onPause();
	}

	protected void onResume()
	{
		super.onResume();
		ActivityStatus.onStateChange(this, 3);
	}

	protected void onStart()
	{
		super.onStart();
		ActivityStatus.onStateChange(this, 2);
	}

	protected void onStop()
	{
		ActivityStatus.onStateChange(this, 5);
		super.onStop();
	}
}
