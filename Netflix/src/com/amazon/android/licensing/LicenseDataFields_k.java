// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.licensing;

import com.amazon.android.k.c;

public enum LicenseDataFields_k
	implements c
{

    EXPIRATION_a,
    CUSTOMER_ID_b,
    DEVICE_ID_c,
    PACKAGE_NAME_d,
    CHECKSUM_e;
    
    public final String a()
    {
        return name();
    }
    
    /*
	public static final k a;
	public static final k b;
	public static final k c;
	public static final k d;
	public static final k e;
	private static final k f[];

	private LicenseDataFields(String s, int i)
	{
		super(s, i);
	}

	public static k valueOf(String s)
	{
		return (k)Enum.valueOf(com/amazon/android/licensing/k, s);
	}

	public static k[] values()
	{
		return (k[])f.clone();
	}

	public final String a()
	{
		return name();
	}

	static 
	{
		a = new k("EXPIRATION", 0);
		b = new k("CUSTOMER_ID", 1);
		c = new k("DEVICE_ID", 2);
		d = new k("PACKAGE_NAME", 3);
		e = new k("CHECKSUM", 4);
		k ak[] = new k[5];
		ak[0] = a;
		ak[1] = b;
		ak[2] = c;
		ak[3] = d;
		ak[4] = e;
		f = ak;
	}
	*/
}
