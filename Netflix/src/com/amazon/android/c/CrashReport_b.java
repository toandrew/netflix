package com.amazon.android.c;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.*;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.framework.util.a;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CrashReport_b implements Serializable {

    private static final KiwiLogger a = new KiwiLogger("CrashReport");
    private static final long serialVersionUID = 1L;
    private final HashMap b;

    public CrashReport_b(Application application, Throwable throwable) {

        b = new LinkedHashMap();
        try {
            PackageInfo packageinfo;
            b.put("crashTime", (new Date()).toString());
            packageinfo = a(application);
            if (packageinfo == null) {
                return;
            }
            b.put("packageVersionName", packageinfo.versionName);
            b.put("packageName", packageinfo.packageName);
            b.put("packageFilePath", application.getFilesDir()
                    .getAbsolutePath());
            ActivityManager activitymanager;
            b.put("deviceModel", Build.MODEL);
            b.put("androidVersion", android.os.Build.VERSION.RELEASE);
            b.put("deviceBoard", Build.BOARD);
            b.put("deviceBrand", Build.BRAND);
            b.put("deviceDisplay", Build.DISPLAY);
            b.put("deviceFingerPrint", Build.FINGERPRINT);
            b.put("deviceHost", Build.HOST);
            b.put("deviceId", Build.ID);
            b.put("deviceManufacturer", Build.MANUFACTURER);
            b.put("deviceProduct", Build.PRODUCT);
            b.put("deviceTags", Build.TAGS);
            b.put("deviceTime", Long.toString(Build.TIME));
            b.put("deviceType", Build.TYPE);
            b.put("deviceUser", Build.USER);
            HashMap hashmap = b;
            StatFs statfs = new StatFs(Environment.getDataDirectory().getPath());
            hashmap.put(
                    "totalInternalMemorySize",
                    Long.toString((long) statfs.getBlockSize()
                            * (long) statfs.getBlockCount()));
            HashMap hashmap1 = b;
            StatFs statfs1 = new StatFs(Environment.getDataDirectory()
                    .getPath());
            hashmap1.put(
                    "availableInternalMemorySize",
                    Long.toString((long) statfs1.getBlockSize()
                            * (long) statfs1.getAvailableBlocks()));
            activitymanager = (ActivityManager) application
                    .getSystemService("activity");
            if (activitymanager == null) {
                return;
            }
            android.app.ActivityManager.MemoryInfo memoryinfo = new android.app.ActivityManager.MemoryInfo();
            activitymanager.getMemoryInfo(memoryinfo);
            b.put("memLowFlag", Boolean.toString(memoryinfo.lowMemory));
            b.put("memLowThreshold", Long.toString(memoryinfo.threshold));
            b.put("nativeHeapSize", Long.toString(Debug.getNativeHeapSize()));
            b.put("nativeHeapFreeSize",
                    Long.toString(Debug.getNativeHeapAllocatedSize()));
            b.put("threadAllocCount",
                    Long.toString(Debug.getThreadAllocCount()));
            b.put("threadAllocSize", Long.toString(Debug.getThreadAllocSize()));
            a(throwable);
            b();
            c();
        } catch (Throwable e) {
            if (KiwiLogger.ERROR_ON) {
                a.error("Error collection crash report details", e);
                return;
            }
        }
    }

    private static PackageInfo a(Application application) {
        PackageManager packagemanager = application.getPackageManager();
        PackageInfo packageinfo;
        try {
            packageinfo = packagemanager.getPackageInfo(
                    application.getPackageName(), 0);
        } catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            if (KiwiLogger.ERROR_ON)
                a.error("Unable to fetch package info", namenotfoundexception);
            return null;
        }
        return packageinfo;
    }

    private void a(Throwable throwable) {
        StringBuilder stringbuilder = new StringBuilder();
        StringWriter stringwriter = new StringWriter();
        PrintWriter printwriter = new PrintWriter(stringwriter);
        throwable.printStackTrace(printwriter);
        stringbuilder.append(stringwriter.toString());
        stringbuilder.append("\n");
        for (Throwable throwable1 = throwable.getCause(); throwable1 != null;) {
            throwable1.printStackTrace(printwriter);
            stringbuilder.append(stringwriter.toString());
            throwable1 = throwable1.getCause();
            stringbuilder.append("\n\n");
        }

        printwriter.close();
        b.put("stackTrace", stringbuilder.toString());
    }

    private void b() {
        StringBuilder stringbuilder = new StringBuilder();
        for (Iterator iterator = Thread.getAllStackTraces().entrySet()
                .iterator(); iterator.hasNext(); stringbuilder.append("\n\n")) {
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            Thread thread = (Thread) entry.getKey();
            StackTraceElement astacktraceelement[] = (StackTraceElement[]) entry
                    .getValue();
            stringbuilder.append((new StringBuilder()).append("Thread : ")
                    .append(thread.getId()).toString());
            if (!com.amazon.android.framework.util.a.a(thread.getName()))
                stringbuilder.append((new StringBuilder()).append("/")
                        .append(thread.getName()).toString());
            stringbuilder.append("\n");
            stringbuilder.append((new StringBuilder()).append("isAlive : ")
                    .append(thread.isAlive()).append("\n").toString());
            stringbuilder.append((new StringBuilder())
                    .append("isInterrupted : ").append(thread.isInterrupted())
                    .append("\n").toString());
            stringbuilder.append((new StringBuilder()).append("isDaemon : ")
                    .append(thread.isDaemon()).append("\n").toString());
            for (int i = 0; i < astacktraceelement.length; i++)
                stringbuilder.append((new StringBuilder()).append("\tat ")
                        .append(astacktraceelement[i]).append("\n").toString());

        }

        b.put("threadDump", stringbuilder.toString());
    }

    private void c() {
        StringBuilder stringbuilder;
        String s;
        Matcher matcher;
        try {
            stringbuilder = new StringBuilder();
            stringbuilder.append((String) b.get("packageName"))
                    .append((String) b.get("packageVersionName"))
                    .append((String) b.get("androidVersion"));
            s = (String) b.get("stackTrace");
        } catch (Exception exception) {
            if (KiwiLogger.ERROR_ON)
                a.error("Error capturing crash id", exception);
            return;
        }
        if (s == null) {
            return;
        }
        try {
            for (matcher = Pattern.compile(
                    "([a-zA-Z0-9_.]+(Exception|Error))|(at\\s.*\\(.*\\))")
                    .matcher(s); matcher.find(); stringbuilder.append(matcher
                    .group()))
                ;
            String s1 = (new BigInteger(MessageDigest.getInstance("SHA1")
                    .digest(stringbuilder.toString().getBytes("UTF-8")))).abs()
                    .toString(16);
            b.put("crashId", s1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public final Map a() {
        return b;
    }

}
