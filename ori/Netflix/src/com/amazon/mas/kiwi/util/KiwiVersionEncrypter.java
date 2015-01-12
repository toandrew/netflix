package com.amazon.mas.kiwi.util;

import java.io.*;

// Referenced classes of package com.amazon.mas.kiwi.util:
//			Base64

public class KiwiVersionEncrypter {

    private static final String SECRET_KEY = "Kiwi__Version__Obfuscator";

    private KiwiVersionEncrypter() {
    }

    private static void checkInput(String s, String s1) {
        if (s == null || s.isEmpty())
            throw new IllegalArgumentException((new StringBuilder())
                    .append("input '").append(s1)
                    .append("' cannot be null or empty").toString());
        else
            return;
    }

    public static String decrypt(String s) throws IOException {
        checkInput(s, "text");
        return new String(performXOR(Base64.decode(s.getBytes())), "UTF-8");
    }

    public static String decryptFromFile(String s) throws IOException {
        BufferedReader bufferedreader;
        checkInput(s, "file");
        bufferedreader = null;
        String s1 = null;
        try {
            bufferedreader = new BufferedReader(new FileReader(s));
            s1 = bufferedreader.readLine();

        } catch (Exception e) {
            if (bufferedreader != null)
                try {
                    bufferedreader.close();
                } catch (IOException ioexception) {
                }
            bufferedreader = null;
            return null;
        } finally {
            if (bufferedreader != null)
                try {
                    bufferedreader.close();
                } catch (IOException ioexception) {
                }
        }
        return new String(performXOR(Base64.decode(s1.getBytes())), "UTF-8");
    }

    public static String encrypt(String s) {
        checkInput(s, "text");
        return Base64.encodeBytes(performXOR(s.getBytes()));
    }

    public static void encryptToFile(String s, String s1) throws IOException {
        String s2;
        BufferedWriter bufferedwriter;
        checkInput(s, "text");
        checkInput(s1, "file");
        s2 = Base64.encodeBytes(performXOR(s.getBytes()));
        bufferedwriter = null;
        try {
            BufferedWriter bufferedwriter1 = new BufferedWriter(new FileWriter(
                    s1));
            bufferedwriter1.write(s2);
            bufferedwriter1.flush();
            if (bufferedwriter1 == null)
                return;
            bufferedwriter1.close();
        } catch (Exception e) {
            if (bufferedwriter != null)
                try {
                    bufferedwriter.close();
                } catch (IOException ioexception) {
                }
        }
        return;

    }

    public static void main(String args[]) throws Exception {
        if (args == null || args.length == 0) {
            System.out
                    .println("Usage: com.amazon.mas.kiwi.util.KiwiVersionEncrypter <textToBeEncrypted> [<encryptToFileName>]");
            return;
        }
        if (args.length > 1) {
            encryptToFile(args[0], args[1]);
            return;
        } else {
            System.out.println(encrypt(args[0]));
            return;
        }
    }

    private static byte[] performXOR(byte abyte0[]) {
        byte abyte1[] = new byte[abyte0.length];
        byte abyte2[] = "Kiwi__Version__Obfuscator".getBytes();
        int i = 0;
        for (int j = 0; j < abyte0.length; j++) {
            abyte1[j] = (byte) (abyte0[j] ^ abyte2[i]);
            if (++i >= abyte2.length)
                i = 0;
        }

        return abyte1;
    }
}
