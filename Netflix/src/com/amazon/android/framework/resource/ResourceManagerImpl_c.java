// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.framework.resource;

import com.amazon.android.d.a;
import com.amazon.android.d.MyRuntimeException_b;
import com.amazon.android.framework.util.KiwiLogger;
import java.lang.reflect.Field;
import java.util.*;

// Referenced classes of package com.amazon.android.framework.resource:
//			a, Resource, b

public final class ResourceManagerImpl_c
	implements com.amazon.android.framework.resource.a
{

	private static KiwiLogger a = new KiwiLogger("ResourceManagerImpl");
	private List b;
	private boolean c;

	public ResourceManagerImpl_c()
	{
		b = new ArrayList();
		c = false;
		a(this);
	}

	private Object a(Class class1)
	{
		for (Iterator iterator = b.iterator(); iterator.hasNext();)
		{
			Object obj = iterator.next();
			if (class1.isAssignableFrom(obj.getClass()))
				return obj;
		}

		return null;
	}

	public final void a()
	{
		for (Iterator iterator = b.iterator(); iterator.hasNext(); b(iterator.next()));
	}

	public final void a(Object obj)
	{
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Registering resource: ").append(obj).toString());
		com.amazon.android.d.a.a();
		if (c)
			throw new IllegalStateException("Attempt made to register resource after population has begun!");
		com.amazon.android.d.a.a(obj, "resource");
		boolean flag;
		if (a(obj.getClass()) != null)
			flag = true;
		else
			flag = false;
		com.amazon.android.d.a.b(flag, (new StringBuilder()).append("Resource already registered: ").append(obj).toString());
		b.add(obj);
	}

	public final void b(Object obj)
	{
		com.amazon.android.d.a.a(obj, "target");
		if (KiwiLogger.TRACE_ON)
			a.trace((new StringBuilder()).append("Populating: ").append(obj).toString());
		if (!obj.getClass().getName().startsWith("com.amazon.android"))
			a.trace((new StringBuilder()).append("Ignoring: ").append(obj).append(", not a kiwi class").toString());
		for (Class class1 = obj.getClass(); class1 != java/lang/Object; class1 = class1.getSuperclass())
		{
			Field afield[] = class1.getDeclaredFields();
			int i = afield.length;
			int j = 0;
			while (j < i) 
			{
				Field field = afield[j];
				boolean flag;
				if (field.getAnnotation(com/amazon/android/framework/resource/Resource) != null)
					flag = true;
				else
					flag = false;
				if (flag)
				{
					Class class2 = field.getType();
					Object obj1 = a(class2);
					String s = (new StringBuilder()).append("no resource found for type: ").append(class2).toString();
					boolean flag1;
					if (obj1 != null)
						flag1 = true;
					else
						flag1 = false;
					com.amazon.android.d.a.a(flag1, s);
					field.setAccessible(true);
					try
					{
						field.set(obj, obj1);
					}
					catch (Exception exception)
					{
						throw new MyRuntimeException_b((new StringBuilder()).append("Failed to populate field: ").append(field).toString(), exception);
					}
				}
				j++;
			}
		}

		if (obj instanceof com.amazon.android.framework.resource.b)
			((com.amazon.android.framework.resource.b)obj).onResourcesPopulated();
	}

}
