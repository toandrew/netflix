package com.amazon.android.framework.task.command;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.amazon.android.b.AuthTokenVerFailureKiwiException_d;
import com.amazon.android.b.CommandServiceNotInstalledKiwiException_g;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.android.h.CertFailedToLoadKiwiException_a;
import com.amazon.android.m.DataAuthenticationKeyLoader_c;
import com.amazon.mas.kiwi.util.Base64;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public final class CommandResultVerifier_e {

    private static final KiwiLogger a = new KiwiLogger("CommandResultVerifier");
    private Application b;
    private DataAuthenticationKeyLoader_c c;

    public CommandResultVerifier_e() {
    }

    private PackageInfo a() throws CommandServiceNotInstalledKiwiException_g {
        PackageManager packagemanager = b.getPackageManager();
        PackageInfo packageinfo;
        try {
            packageinfo = packagemanager.getPackageInfo("com.amazon.venezia",
                    64);
        } catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            throw new CommandServiceNotInstalledKiwiException_g();
        }
        return packageinfo;
    }

    private boolean a(String s, Signature signature)
            throws CertFailedToLoadKiwiException_a {
        String s1;
        try {
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(
                    signature.toByteArray());
            s1 = Base64.encodeBytes(((X509Certificate) CertificateFactory
                    .getInstance("X509").generateCertificate(
                            bytearrayinputstream)).getSignature());
        } catch (CertificateException certificateexception) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Failed to extract fingerprint from signature: ")
                        .append(signature).toString());
            return false;
        }
        return com.amazon.android.m.SignatureVerifier_a.a(s1, s, c.a());
    }

    public final void a(String s) throws KiwiException {
        if (KiwiLogger.TRACE_ON)
            a.trace((new StringBuilder()).append("Verifying auth token: ")
                    .append(s).toString());
        Signature asignature[] = a().signatures;
        int i = asignature.length;
        for (int j = 0; j < i; j++)
            if (a(s, asignature[j]))
                return;

        throw new AuthTokenVerFailureKiwiException_d();
    }

}
