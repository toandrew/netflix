package com.amazon.mas.kiwi.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.CodeSigner;
import java.security.cert.*;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

// Referenced classes of package com.amazon.mas.kiwi.util:
//			ApkInvalidException

public class ApkHelpers {

    private static final String CONTENT_PREFIX = "com.amazon.content.id.";

    public ApkHelpers() {
    }

    public static byte[] getApkSignature(String s) throws IOException,
            ApkInvalidException {
        if (s == null || s.length() == 0)
            throw new IllegalArgumentException(
                    "apkFileName cannot be null or empty!");
        JarFile jarfile = new JarFile(s);
        Certificate certificate;
        try {
            scanJar(jarfile);
        } catch (SecurityException securityexception) {
            return null;
        }
        certificate = getFirstSigningCert(jarfile);
        if (certificate == null)
            return null;
        boolean flag = certificate instanceof X509Certificate;
        byte abyte0[] = null;
        if (flag)
            abyte0 = ((X509Certificate) certificate).getSignature();
        return abyte0;
    }

    private static CodeSigner[] getCodeSigners(JarFile jarfile) {
        CodeSigner acodesigner[] = null;
        Enumeration enumeration = jarfile.entries();
        do {
            if (!enumeration.hasMoreElements())
                break;
            acodesigner = ((JarEntry) enumeration.nextElement())
                    .getCodeSigners();
        } while (acodesigner == null);
        return acodesigner;
    }

    public static String getContentID(JarFile jarfile) {
        if (jarfile == null)
            throw new IllegalArgumentException("apkSrc must not be null!");
        for (Enumeration enumeration = jarfile.entries(); enumeration
                .hasMoreElements();) {
            JarEntry jarentry = (JarEntry) enumeration.nextElement();
            if (!jarentry.isDirectory()) {
                String s = getContentIDFromName(jarentry.getName());
                if (s != null && s.length() != 0)
                    return s;
            }
        }

        return null;
    }

    public static String getContentIDFromName(String s) {
        if (s == null || s.length() == 0)
            throw new IllegalArgumentException("name cannot be null or empty!");
        int i = s.indexOf("com.amazon.content.id.");
        if (i < 0)
            return null;
        if (s.length() <= "com.amazon.content.id.".length())
            return null;
        else
            return s.substring(i + "com.amazon.content.id.".length());
    }

    private static Certificate getFirstSigningCert(JarFile jarfile) {
        CodeSigner acodesigner[] = getCodeSigners(jarfile);
        Certificate certificate = null;
        if (acodesigner != null) {
            int i = acodesigner.length;
            certificate = null;
            if (i > 0) {
                List list = acodesigner[0].getSignerCertPath()
                        .getCertificates();
                boolean flag = list.isEmpty();
                certificate = null;
                if (!flag)
                    certificate = (Certificate) list.get(0);
            }
        }
        return certificate;
    }

    public static boolean isSigned(JarFile jarfile) throws IOException {
        if (jarfile == null)
            throw new IllegalArgumentException("apkSrc must not be null!");
        try {
            scanJar(jarfile);
        } catch (SecurityException securityexception) {
            return true;
        }
        return getCodeSigners(jarfile) != null;
    }

    private static void scanJar(JarFile jarfile) throws IOException {
        Enumeration enumeration;
        byte abyte0[];
        enumeration = jarfile.entries();
        abyte0 = new byte[8192];

        JarEntry jarentry;
        InputStream inputstream = null;
        try {
            while (enumeration.hasMoreElements()) {
                jarentry = (JarEntry) enumeration.nextElement();
                inputstream = null;
                inputstream = jarfile.getInputStream(jarentry);
                int i;
                do {
                    i = inputstream.read(abyte0, 0, abyte0.length);
                } while (i != -1);
                if (inputstream != null)
                    inputstream.close();
            }
        } catch (Exception e) {
            throw new ApkInvalidException(e);
        } finally {
            if (inputstream != null)
                inputstream.close();
        }

    }
}
