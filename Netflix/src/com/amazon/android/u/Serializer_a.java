package com.amazon.android.u;

import com.amazon.android.framework.util.KiwiLogger;
import com.amazon.mas.kiwi.util.Base64;
import java.io.*;

public final class Serializer_a {

    private static final KiwiLogger a = new KiwiLogger("Serializer");

    public Serializer_a() {
    }

    public static Object a(String s) {
        ObjectInputStream objectinputstream = null;
        if (s == null || s.length() == 0)
            return null;
        byte abyte0[];
        Object obj = null;
        IOException ioexception3;
        try {
            abyte0 = Base64.decode(s.getBytes());
        } catch (IOException ioexception) {
            if (KiwiLogger.ERROR_ON)
                a.error("Could not decode string", ioexception);
            return null;
        }

        try {
            objectinputstream = new ObjectInputStream(new ByteArrayInputStream(
                    abyte0));
            obj = objectinputstream.readObject();
            objectinputstream.close();
        } catch (Exception e) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Could not read object from string: ")
                        .append(s).toString(), e);
        } finally {
            if (objectinputstream != null) {
                try {
                    objectinputstream.close();
                } catch (IOException ioexception2) {
                    return null;
                }
            }
        }

        return obj;
    }

    public static String a(Serializable serializable) {
        ByteArrayOutputStream bytearrayoutputstream = null;
        ObjectOutputStream objectoutputstream = null;
        String s = null;
        if (serializable == null)
            return null;
        try {
            bytearrayoutputstream = new ByteArrayOutputStream();
            objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);

            objectoutputstream.writeObject(serializable);
            s = new String(Base64.encodeBytes(bytearrayoutputstream
                    .toByteArray()));
        } catch (IOException e) {
            if (KiwiLogger.ERROR_ON)
                a.error((new StringBuilder())
                        .append("Could not serialize object: ")
                        .append(serializable).toString(), e);
        } finally {
            if (objectoutputstream != null)
                try {
                    objectoutputstream.close();
                } catch (IOException ioexception2) {
                }
        }

        return s;
    }

}
