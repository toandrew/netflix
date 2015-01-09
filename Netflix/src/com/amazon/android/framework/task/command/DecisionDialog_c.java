package com.amazon.android.framework.task.command;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import com.amazon.android.framework.prompt.Prompt;
import com.amazon.android.framework.prompt.PromptFailedReason_d;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.venezia.command.n;
import com.amazon.venezia.command.r;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Referenced classes of package com.amazon.android.framework.task.command:
//			f, l, d, a

public final class DecisionDialog_c extends Prompt {

    private static final KiwiLogger a = new KiwiLogger("DecisionDialog");
    private final Thread b = Thread.currentThread();
    private final f c;
    private final BlockingQueue d = new LinkedBlockingQueue();

    public DecisionDialog_c(r r) throws RemoteException {
        c = new f(r);
    }

    private static ActivityInfo a(Activity activity) {
        ActivityInfo activityinfo;
        try {
            activityinfo = activity.getPackageManager().getActivityInfo(
                    activity.getComponentName(), 128);
        } catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Unable to get info for activity: ")
                        .append(activity).toString());
            return null;
        }
        return activityinfo;
    }

    private void a(AlertDialog alertdialog, l l1, int i) {
        if (l1 == null) {
            return;
        } else {
            alertdialog
                    .setButton(
                            i,
                            l1.b,
                            new com.amazon.android.framework.task.command.OnChoiceClickListener_d(
                                    this, l1));
            return;
        }
    }

    static boolean a(DecisionDialog_c c1) {
        return c1.dismiss();
    }

    private static boolean a(l l1) {
        return l1 != null && l1.c != null;
    }

    static KiwiLogger b() {
        return a;
    }

    static BlockingQueue b(DecisionDialog_c c1) {
        return c1.d;
    }

    private n c() throws com.amazon.android.b.MiscKiwiException_c {
        n n;
        try {
            if (KiwiLogger.TRACE_ON)
                a.trace((new StringBuilder())
                        .append("Blocking while awaiting customer decision: ")
                        .append(Thread.currentThread()).toString());
            n = ((l) d.take()).a;
        } catch (InterruptedException interruptedexception) {
            if (KiwiLogger.TRACE_ON)
                a.trace("Interrupted while awaiting decision, throwing decision expired!");
            FailedReason_a a1;
            if (getExpirationReason() == PromptFailedReason_d.NOT_COMPATIBLE_a)
                a1 = FailedReason_a.APP_NOT_COMPATIBLE_b;
            else
                a1 = FailedReason_a.EXPIRATION_DURATION_ELAPSED_a;
            throw new com.amazon.android.b.MiscKiwiException_c(a1);
        }
        return n;
    }

    public final n a() throws com.amazon.android.b.MiscKiwiException_c {
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder()).append("GetCustomerDecision: ")
                    .append(b).toString());
        return c();
    }

    public final boolean doCompatibilityCheck(Activity activity) {
        boolean flag;
        if (a(c.d) || a(c.e) || a(c.f))
            flag = true;
        else
            flag = false;
        if (!flag)
            return true;
        ActivityInfo activityinfo = a(activity);
        if (activityinfo == null)
            return false;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        if (activityinfo.launchMode == 3)
            flag1 = true;
        else
            flag1 = false;
        a.trace((new StringBuilder()).append("Single instance: ").append(flag1)
                .toString());
        if ((2 & activityinfo.flags) != 0)
            flag2 = true;
        else
            flag2 = false;
        a.trace((new StringBuilder()).append("Finish on task launch:")
                .append(flag2).toString());
        if ((0x80 & activityinfo.flags) != 0)
            flag3 = true;
        else
            flag3 = false;
        a.trace((new StringBuilder()).append("No History: ").append(flag3)
                .toString());
        return !flag1 && !flag2 && !flag3;
    }

    public final Dialog doCreate(Activity activity) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
                activity);
        builder.setTitle(c.a).setMessage(c.b).setCancelable(false);
        AlertDialog alertdialog = builder.create();
        a(alertdialog, c.d, -1);
        a(alertdialog, c.e, -3);
        a(alertdialog, c.f, -2);
        return alertdialog;
    }

    protected final void doExpiration(PromptFailedReason_d d1) {
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder())
                    .append("Expiring Decision Dialog: Thread: ")
                    .append(Thread.currentThread()).toString());
        b.interrupt();
    }

    protected final long getExpirationDurationInSeconds() {
        return c.c;
    }

    public final String toString() {
        return (new StringBuilder()).append("DecisionDialog: ").append(c.a)
                .toString();
    }
}
