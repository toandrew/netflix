package com.amazon.android.d;

import android.os.Looper;

// Referenced classes of package com.amazon.android.d:
//			b

public final class a {

    public a() {
    }

    public static void a() {
        long l = Looper.getMainLooper().getThread().getId();
        if (Thread.currentThread().getId() != l)
            a((new StringBuilder()).append("Executing thread must be thread: ")
                    .append(l).append(", was: ")
                    .append(Thread.currentThread().getId()).toString());
    }

    public static void a(Object obj, String s) {
        if (obj == null)
            a((new StringBuilder()).append("Argument: ").append(s)
                    .append(" cannot be null").toString());
    }

    private static void a(String s) {
        throw new MyRuntimeException_b(s);
    }

    public static void a(boolean flag, String s) {
        if (!flag)
            a(s);
    }

    public static void b(boolean flag, String s) {
        if (flag)
            a(s);
    }
}
