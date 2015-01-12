package com.amazon.android.licensing;

import com.amazon.android.d.a;
import com.amazon.android.framework.task.Task;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.framework.util.Reflection_c;
import java.lang.reflect.Method;

public final class DRMSuccessTask_n implements Task {

    private static final KiwiLogger a = new KiwiLogger("DRMSuccessTask");

    public DRMSuccessTask_n() {
    }

    private static Method a(Class class1, String s) {
        com.amazon.android.d.a.a(class1, "Class<?> target");
        com.amazon.android.d.a.a(s, "String methodName");
        Method method;
        Method method1;
        try {
            method = class1.getDeclaredMethod(s, new Class[0]);
        } catch (NoSuchMethodException nosuchmethodexception) {
            if (KiwiLogger.TRACE_ON)
                a.trace((new StringBuilder()).append("Did not find method ")
                        .append(s).toString());
            return null;
        }
        method1 = method;
        if (method1 == null) {
            if (KiwiLogger.TRACE_ON)
                a.trace((new StringBuilder())
                        .append("No exception thrown, but method '").append(s)
                        .append("' was not found, this should not happen. ")
                        .toString());
            method1 = null;
        } else {
            method1.setAccessible(true);
            if (!Reflection_c.c(method1)) {
                if (KiwiLogger.TRACE_ON)
                    a.trace((new StringBuilder()).append("Callback ").append(s)
                            .append(" isn't static, ignoring...").toString());
                return null;
            }
            if (!Reflection_c.b(method1)) {
                if (KiwiLogger.TRACE_ON)
                    a.trace((new StringBuilder()).append("Callback ").append(s)
                            .append(" returns a value, ignoring...").toString());
                return null;
            }
            if (Reflection_c.a(method1)) {
                if (KiwiLogger.TRACE_ON)
                    a.trace((new StringBuilder()).append("Callback ").append(s)
                            .append(" takes parameters, ignoring...")
                            .toString());
                return null;
            }
        }
        return method1;
    }

    public final void execute() {
        Class class1 = Reflection_c
                .a("com.amazon.drm.AmazonLicenseVerificationCallback");
        Method method;
        if (class1 != null)
            if ((method = a(class1, "onDRMSuccess")) != null) {
                if (KiwiLogger.TRACE_ON)
                    a.trace((new StringBuilder()).append("Invoking callback: ")
                            .append(method.getName()).toString());
                try {
                    method.invoke(null, new Object[0]);
                } catch (Exception exception) {
                    return;
                }
                if (KiwiLogger.TRACE_ON) {
                    a.trace("Callback invoked.");
                    return;
                }
            }
    }

}
