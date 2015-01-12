package com.amazon.android.framework.util;

import android.util.Log;

public class KiwiLogger {

    public static boolean ERROR_ON = false;
    private static final String TAG = "Kiwi";
    private static boolean TEST_ON = false;
    public static boolean TRACE_ON = true;
    private String componentName;

    public KiwiLogger(String s) {
        componentName = s;
    }

    public static void enableTest() {
        TEST_ON = true;
    }

    private String getComponentMessage(String s) {
        return (new StringBuilder()).append(componentName).append(": ")
                .append(s).toString();
    }

    public static boolean isTestEnabled() {
        return TEST_ON;
    }

    public void error(String s) {
        if (ERROR_ON)
            Log.e("Kiwi", getComponentMessage(s));
    }

    public void error(String s, Throwable throwable) {
        if (ERROR_ON)
            Log.e("Kiwi", getComponentMessage(s), throwable);
    }

    public void test(String s) {
        if (TEST_ON)
            Log.e("Kiwi",
                    (new StringBuilder()).append("TEST-")
                            .append(getComponentMessage(s)).toString());
    }

    public void trace(String s) {
        if (TRACE_ON)
            Log.d("Kiwi", getComponentMessage(s));
    }

    public void trace(String s, Throwable throwable) {
        if (TRACE_ON)
            Log.d("Kiwi", getComponentMessage(s), throwable);
    }

    static {
        ERROR_ON = true;
    }
}
