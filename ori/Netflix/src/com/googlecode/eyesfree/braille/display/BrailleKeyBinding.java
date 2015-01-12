// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.googlecode.eyesfree.braille.display;

import android.os.Parcel;
import android.os.Parcelable;

public class BrailleKeyBinding
	implements Parcelable
{

	public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

		public BrailleKeyBinding createFromParcel(Parcel parcel)
		{
			return new BrailleKeyBinding(parcel);
		}

//		public volatile Object createFromParcel(Parcel parcel)
//		{
//			return createFromParcel(parcel);
//		}

		public BrailleKeyBinding[] newArray(int i)
		{
			return new BrailleKeyBinding[i];
		}

//		public volatile Object[] newArray(int i)
//		{
//			return newArray(i);
//		}

	};
	private int mCommand;
	private String mKeyNames[];

	public BrailleKeyBinding()
	{
	}

	public BrailleKeyBinding(int i, String as[])
	{
		mCommand = i;
		mKeyNames = as;
	}

	private BrailleKeyBinding(Parcel parcel)
	{
		mCommand = parcel.readInt();
		mKeyNames = parcel.createStringArray();
	}


	public int describeContents()
	{
		return 0;
	}

	public int getCommand()
	{
		return mCommand;
	}

	public String[] getKeyNames()
	{
		return mKeyNames;
	}

	public BrailleKeyBinding setCommand(int i)
	{
		mCommand = i;
		return this;
	}

	public BrailleKeyBinding setKeyNames(String as[])
	{
		mKeyNames = as;
		return this;
	}

	public void writeToParcel(Parcel parcel, int i)
	{
		parcel.writeInt(mCommand);
		parcel.writeStringArray(mKeyNames);
	}

}
