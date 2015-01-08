// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.mas.kiwi.util;


public class DeveloperInfo
{

	private String ID;
	private String name;

	public DeveloperInfo()
	{
		ID = "";
		name = "";
	}

	public String getID()
	{
		return ID;
	}

	public String getName()
	{
		return name;
	}

	public boolean isValid()
	{
		return ID.length() != 0 && name.length() != 0;
	}

	public void setID(String s)
	{
		ID = s;
	}

	public void setName(String s)
	{
		name = s;
	}

	public String toString()
	{
		String s = (new StringBuilder()).append("ID: ").append(ID).append(", ").toString();
		return (new StringBuilder()).append(s).append("name: ").append(name).toString();
	}
}
