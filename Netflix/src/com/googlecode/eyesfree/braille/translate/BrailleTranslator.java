// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.googlecode.eyesfree.braille.translate;


public interface BrailleTranslator
{

	public abstract String backTranslate(byte abyte0[]);

	public abstract byte[] translate(String s);
}
