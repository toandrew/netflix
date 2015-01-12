// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package org.chromium.ui.autofill;


public class AutofillSuggestion
{

	final String mLabel;
	final String mSublabel;
	final int mUniqueId;

	public AutofillSuggestion(String s, String s1, int i)
	{
		mLabel = s;
		mSublabel = s1;
		mUniqueId = i;
	}
}
