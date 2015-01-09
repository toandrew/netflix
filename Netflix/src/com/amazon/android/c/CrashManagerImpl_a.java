package com.amazon.android.c;

import android.app.Application;
import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.o.EventManager_g;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

// Referenced classes of package com.amazon.android.c:
//			d, b, c

public final class CrashManagerImpl_a implements d, b,
        Thread.UncaughtExceptionHandler {

    private static final KiwiLogger a = new KiwiLogger("CrashManagerImpl");
    private EventManager_g b;
    private com.amazon.android.g.b c;
    private Application d;
    private Thread.UncaughtExceptionHandler e;
    private Map f;

    public CrashManagerImpl_a() {
        f = new HashMap();
    }

    static Map a(CrashManagerImpl_a a1) {
        return a1.f;
    }

    private void a(String s) {
        synchronized (this) {
            String s1;
            try {
                int i = (new Random()).nextInt(0x1869f);
                s1 = (new StringBuilder()).append("s-").append(i)
                        .append(".amzst").toString();
                FileOutputStream fileoutputstream = d.openFileOutput(s1, 0);
                fileoutputstream.write(s.getBytes());
                fileoutputstream.close();
            } catch (Exception e) {
                e.printStackTrace();
                if (KiwiLogger.ERROR_ON)
                    a.error("Coud not save crash report to file", e);
            }
        }
    }

    private com.amazon.android.c.CrashReport_b b(String s) {
        com.amazon.android.c.CrashReport_b b1;
        try {
            String s1 = c(s);
            b1 = (com.amazon.android.c.CrashReport_b) com.amazon.android.u.Serializer_a
                    .a(c.b(s1));
        } catch (Exception exception) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Failed to load crash report: ").append(s)
                        .toString());
            return null;
        }
        return b1;
    }

    private boolean b() {
        return f.size() >= 5;
    }

    private static String c(String s) throws IOException {
        StringBuilder stringbuilder = new StringBuilder();
        BufferedReader bufferedreader = new BufferedReader(new FileReader(s));
        try {
            while (bufferedreader.ready())
                stringbuilder.append(bufferedreader.readLine());
            if (bufferedreader != null)
                bufferedreader.close();
        } catch (IOException e) {
            throw e;
        }
        bufferedreader.close();
        bufferedreader = null;
        return stringbuilder.toString();
    }

    private static void d(String s) {
        try {
            (new File(s)).delete();
        } catch (Exception e) {
            if (KiwiLogger.ERROR_ON) {
                a.error((new StringBuilder()).append("Cannot delete file: ")
                        .append(s).toString(), e);
                return;
            }
        }
    }

    public final List a() {
        Object obj;
        if (b()) {
            obj = Collections.emptyList();
        } else {
            obj = new ArrayList();
            String as[] = (new File((new StringBuilder())
                    .append(d.getFilesDir().getAbsolutePath()).append("/")
                    .toString())).list(new CrashFilenameFilter_c(this));
            int i = 0;
            while (i < as.length && !b()) {
                String s = as[i];
                String s1 = (new StringBuilder())
                        .append(d.getFilesDir().getAbsolutePath()).append("/")
                        .append(s).toString();
                com.amazon.android.c.CrashReport_b b1 = b(s1);
                if (b1 != null) {
                    f.put(b1, s1);
                    ((List) (obj)).add(b1);
                } else {
                    d(s1);
                }
                i++;
            }
        }
        return ((List) (obj));
    }

    public final void a(List list) {
        com.amazon.android.c.CrashReport_b b1;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); f
                .remove(b1)) {
            b1 = (com.amazon.android.c.CrashReport_b) iterator.next();
            d((String) f.get(b1));
        }

    }

    public final void onResourcesPopulated() {
        com.amazon.android.d.a.a();
        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof d)) {
            if (KiwiLogger.TRACE_ON)
                a.trace("Registering Crash Handler!!!!");
            e = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    public final void uncaughtException(Thread thread, Throwable throwable) {
        if (KiwiLogger.TRACE_ON) {
            a.trace("---------------------------------------------------");
            a.trace("Crash detected", throwable);
            a.trace("---------------------------------------------------");
        }
        try {
            String s = com.amazon.android.u.Serializer_a
                    .a(new com.amazon.android.c.CrashReport_b(d, throwable));
            a(c.a(s));
        } catch (Throwable e) {
            if (KiwiLogger.ERROR_ON)
                a.error("Could not handle uncaught exception", e);
        }

        try {
            b.a(new com.amazon.android.a.b());
        } catch (Throwable e) {
            if (KiwiLogger.TRACE_ON)
                a.trace("Error occured while handling exception", e);
        }
        if (KiwiLogger.TRACE_ON)
            a.trace("Calling previous handler");
        if (e != null)
            e.uncaughtException(thread, throwable);
        return;
    }

}
