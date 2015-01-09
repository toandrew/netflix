package com.amazon.mas.kiwi.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

// Referenced classes of package com.amazon.mas.kiwi.util:
//			Base64

public class BC1 {

    private static String CHECKSUM_ALGORITHM = "MD5";
    private static final int DIGEST_UPDATE_BUFFER_SIZE = 10240;
    private static final Set DIRECTORIES_TO_IGNORE_FOR_PARTIAL_CHECKSUM = new HashSet() {

        {
            add("assets/");
            add("res/");
        }
    };
    private static final int MAX_SIZE_FOR_FULL_CHECKSUM = 0x1400000;

    public BC1() {
    }

    private static void calculateFullChecksum(File file,
            MessageDigest messagedigest) throws IOException {
        updateMessageDigestWithInputStream(new FileInputStream(file),
                messagedigest);
    }

    private static void calculatePartialChecksum(File file,
            MessageDigest messagedigest, byte abyte0[]) throws IOException {
        JarFile jarfile;
        Enumeration enumeration;
        jarfile = new JarFile(file);
        enumeration = jarfile.entries();
        JarEntry jarentry = null;
        InputStream inputstream = null;
        try {
            do {
                if (!enumeration.hasMoreElements())
                    break;
                jarentry = (JarEntry) enumeration.nextElement();
            } while (isInDirectoryToIgnore(jarentry));

            inputstream = null;
            inputstream = jarfile.getInputStream(jarentry);
            updateMessageDigestWithInputStream(inputstream, messagedigest,
                    abyte0);
            closeIgnoreException(inputstream);
        } finally {
            closeIgnoreException(inputstream);
        }
    }

    private static void closeIgnoreException(InputStream inputstream) {
        if (inputstream == null)
            return;
        try {
            inputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeIgnoreException(ZipFile zipfile) {
        if (zipfile == null)
            return;

        try {
            zipfile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getBC1Checksum(File file) throws IOException {
        MessageDigest messagedigest = getMessageDigest();
        if (isTooLargeForFullChecksum(file))
            calculatePartialChecksum(file, messagedigest, new byte[10240]);
        else
            calculateFullChecksum(file, messagedigest);
        return messagedigest.digest();
    }

    public static byte[] getBC1Checksum(String s) throws IOException {
        if (s == null)
            throw new IllegalArgumentException("Given path is null");
        File file = new File(s);
        if (!file.exists())
            throw new IOException((new StringBuilder())
                    .append("Cannot calculate checksum, file does not exist: ")
                    .append(s).toString());
        else
            return getBC1Checksum(file);
    }

    public static String getBC1ChecksumBase64(String s) throws IOException {
        return toBase64(getBC1Checksum(s));
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest messagedigest;
        try {
            messagedigest = MessageDigest.getInstance(CHECKSUM_ALGORITHM);
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            throw new IllegalStateException(nosuchalgorithmexception);
        }
        return messagedigest;
    }

    private static boolean isInDirectoryToIgnore(JarEntry jarentry) {
        for (Iterator iterator = DIRECTORIES_TO_IGNORE_FOR_PARTIAL_CHECKSUM
                .iterator(); iterator.hasNext();) {
            String s = (String) iterator.next();
            if (jarentry.getName().startsWith(s))
                return true;
        }

        return false;
    }

    private static boolean isTooLargeForFullChecksum(File file) {
        return file.length() >= 0x1400000L;
    }

    private static String toBase64(byte abyte0[]) {
        return new String(Base64.encodeBytes(abyte0));
    }

    private static void updateMessageDigestWithInputStream(
            InputStream inputstream, MessageDigest messagedigest)
            throws FileNotFoundException, IOException {
        updateMessageDigestWithInputStream(inputstream, messagedigest,
                new byte[10240]);
    }

    private static void updateMessageDigestWithInputStream(
            InputStream inputstream, MessageDigest messagedigest, byte abyte0[])
            throws FileNotFoundException, IOException {
        do {
            int i = inputstream.read(abyte0);
            if (i != -1)
                messagedigest.update(abyte0, 0, i);
            else
                return;
        } while (true);
    }

}
