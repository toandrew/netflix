package com.amazon.android.f;

import android.app.Activity;
import android.content.Intent;

public final class MyActivtyResult_f {

    final int a;
    public final int b;
    private final Activity c;
    private final Intent d;

    public MyActivtyResult_f(Activity activity, int i, int j, Intent intent) {
        c = activity;
        a = i;
        b = j;
        d = intent;
    }

    public final String toString() {
        return (new StringBuilder()).append("ActivtyResult: [ requestCode: ")
                .append(a).append(", resultCode: ").append(b)
                .append(", activity: ").append(c).append(", intent: ")
                .append(d).append("]").toString();
    }
}
