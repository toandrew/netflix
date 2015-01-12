package com.amazon.android.g;

import android.app.Application;
import android.os.Build;
import com.amazon.android.framework.resource.b;
import com.amazon.android.framework.util.KiwiLogger;
import java.io.UnsupportedEncodingException;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

// Referenced classes of package com.amazon.android.g:
//			b

public final class ObfuscationManagerImpl_a implements b,
        com.amazon.android.g.b {

    private static final KiwiLogger a = new KiwiLogger("ObfuscationManagerImpl");
    private Application b;
    private SecretKeySpec c;

    public ObfuscationManagerImpl_a() {
    }

    private SecretKeySpec a() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(b.getPackageName());
        stringbuilder.append(android.provider.Settings.Secure.getString(
                b.getContentResolver(), "android_id"));
        stringbuilder.append(Build.FINGERPRINT);
        stringbuilder.append(Build.BRAND);
        stringbuilder.append(Build.BOARD);
        stringbuilder.append(Build.MODEL);
        String s = stringbuilder.toString();
        SecretKeySpec secretkeyspec;
        try {
            SecureRandom securerandom = SecureRandom.getInstance("SHA1PRNG");
            KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
            securerandom.setSeed(a("SHA-256", a("MD5", s.getBytes())));
            keygenerator.init(128, securerandom);
            secretkeyspec = new SecretKeySpec(keygenerator.generateKey()
                    .getEncoded(), "AES");
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Unable to create KeySpec: ")
                        .append(nosuchalgorithmexception).toString(),
                        nosuchalgorithmexception);
            return null;
        }
        return secretkeyspec;
    }

    private static byte[] a(String s, byte abyte0[]) {
        MessageDigest messagedigest;
        try {
            messagedigest = MessageDigest.getInstance(s);
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Failed to create MessageDigest: ")
                        .append(nosuchalgorithmexception).toString(),
                        nosuchalgorithmexception);
            return null;
        }
        messagedigest.update(abyte0);
        return messagedigest.digest();
    }

    private byte[] a(byte abyte0[]) {
        byte abyte1[];
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, c);
            abyte1 = cipher.doFinal(abyte0);
        } catch (Exception exception) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Error obfuscating data: ").append(exception)
                        .toString(), exception);
            return null;
        }
        return abyte1;
    }

    private byte[] b(byte abyte0[]) {
        byte abyte1[];
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, c);
            abyte1 = cipher.doFinal(abyte0);
        } catch (Exception exception) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Error unobfuscating data: ").append(exception)
                        .toString(), exception);
            return null;
        }
        return abyte1;
    }

    private static byte[] c(String s) {
        int i = s.length();
        byte abyte0[] = new byte[i / 2];
        for (int j = 0; j < i; j += 2)
            abyte0[j / 2] = (byte) (Character.digit(s.charAt(j), 16) << 4 | Character
                    .digit(s.charAt(j + 1), 16));

        return abyte0;
    }

    public final String a(String s) {
        if (s == null)
            return null;
        if (c == null)
            return null;
        byte abyte0[];
        StringBuffer stringbuffer;
        int i;
        String s1;
        byte byte0;
        try {
            abyte0 = a(s.getBytes("UTF-8"));
            stringbuffer = new StringBuffer(2 * abyte0.length);
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
            return null;
        }
        i = 0;
        while (i < abyte0.length) {

            byte0 = abyte0[i];
            stringbuffer.append("0123456789ABCDEF".charAt(0xf & byte0 >> 4))
                    .append("0123456789ABCDEF".charAt(byte0 & 0xf));
            i++;
        }
        s1 = stringbuffer.toString();
        return s1;
    }

    public final String b(String s) {
        if (s == null)
            return null;
        if (c == null)
            return null;
        String s1;
        try {
            s1 = new String(b(c(s)), "UTF-8");
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
            return null;
        }
        return s1;
    }

    public final void onResourcesPopulated() {
        c = a();
    }

}
