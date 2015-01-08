// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.android.i;

import com.amazon.android.framework.util.KiwiLogger;
import java.util.Date;

// Referenced classes of package com.amazon.android.i:
//			b

public abstract class RelativeExpirable_c extends Expirable_b
{

	private static final KiwiLogger LOGGER = new KiwiLogger("RelativeExpirable");
	private static final long MILLISECONDS_PER_SECOND = 1000L;
	private final Date instantiation = new Date();

	public RelativeExpirable_c()
	{
	}

	public final Date getExpiration()
	{
		LOGGER.trace((new StringBuilder()).append("RelativeExpiration duration: ").append(getExpirationDurationInSeconds()).append(", expirable: ").append(this).toString());
		Date date = new Date(instantiation.getTime() + 1000L * getExpirationDurationInSeconds());
		LOGGER.trace((new StringBuilder()).append("Expiration should occur at time: ").append(date).toString());
		return date;
	}

	protected abstract long getExpirationDurationInSeconds();

}
