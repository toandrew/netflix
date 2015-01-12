// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.webcrypto;

import android.content.*;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import com.amazon.tz.webcrypto.IWebCryptoTZService;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

// Referenced classes of package com.amazon.webcrypto:
//			TZInterfaceException

public class TZInterface {
    static enum webcrypto_cmd {
        ZERO, WEBCRYPTO_CMD_DIGEST_SHA256, WEBCRYPTO_CMD_IMPORT_RAW_KEY, WEBCRYPTO_CMD_EXPORT_RAW_KEY, WEBCRYPTO_CMD_AES128_CBC_PKCS7_ENCRYPT, WEBCRYPTO_CMD_AES128_CBC_PKCS7_DECRYPT, WEBCRYPTO_CMD_HMAC_SHA256, WEBCRPYTO_CMD_AES_KEYUNWRAP, WEBCRPYTO_CMD_AES_KEYWRAP, WEBCRPYTO_CMD_GET_KEY_BY_NAME, WEBCRPYTO_CMD_GET_RANDOM_NUMBER;
    }

    static enum webcrypto_error {

        WC_OK, WC_ENOMEM, WC_EBUFOF, WC_EKEYIDNOTFOUND, WC_EKEYLIMIT, WC_EFOPEN, WC_EFRW, WC_EOPFAILED;
    }

    private static final boolean DEBUG = false;
    private static final String TAG = "TZInterface";
    protected boolean mBounded;
    private final int mBufferSize = 0x63fe0;
    ServiceConnection mConnection;
    private Context mContext;
    private final int mHeaderSize = 32;
    IWebCryptoTZService mIWebCryptoTZService;
    private final int mSha256Len = 32;

    public TZInterface(Context context) {
        mConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName componentname,
                    IBinder ibinder) {
                mBounded = true;
                mIWebCryptoTZService = com.amazon.tz.webcrypto.IWebCryptoTZService.Stub
                        .asInterface(ibinder);
            }

            public void onServiceDisconnected(ComponentName componentname) {
                mBounded = false;
                mIWebCryptoTZService = null;
            }
        };
        mContext = context;
        context.bindService(new Intent("com.amazon.tz.webcrypto"), mConnection,
                1);
    }

    private byte[] int2ByteArray(int i) {
        ByteBuffer bytebuffer = ByteBuffer.allocate(4);
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        bytebuffer.putInt(i);
        return bytebuffer.array();
    }

    private byte[] sendReqNative(int i, int j, int k, int l, int i1, int j1,
            int k1, int l1, byte abyte0[]) throws RemoteException {
        return mIWebCryptoTZService.sendReq2TZ(i, j, k, l, i1, j1, k1, l1,
                abyte0);
    }

    String bytArrayToHex(byte abyte0[]) {
        StringBuilder stringbuilder = new StringBuilder();
        int i = abyte0.length;
        for (int j = 0; j < i; j++) {
            byte byte0 = abyte0[j];
            Object aobj[] = new Object[1];
            aobj[0] = Byte.valueOf(byte0);
            stringbuilder.append(String.format("%02x", aobj));
        }

        return stringbuilder.toString();
    }

    public void close() {
        Log.e("TZInterface", "Service unbind called");
        mContext.unbindService(mConnection);
    }

    public byte[] digestHmacSHA256(int i, byte abyte0[]) throws Exception {
        byte abyte1[] = sendReqNative(
                webcrypto_cmd.WEBCRYPTO_CMD_HMAC_SHA256.ordinal(),
                abyte0.length, 0, i, 0, 1, 0, 0, abyte0);
        ByteBuffer bytebuffer = ByteBuffer.wrap(abyte1);
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        int j = bytebuffer.getInt();
        int k = bytebuffer.getInt();
        int l = bytebuffer.getInt();
        int i1 = bytebuffer.getInt();
        int j1 = bytebuffer.getInt();
        int k1 = bytebuffer.getInt();
        if (j != webcrypto_cmd.WEBCRYPTO_CMD_HMAC_SHA256.ordinal())
            throw new TZInterfaceException();
        if (i1 != 0 || j1 != 0)
            throw new TZInterfaceException();
        if (l != webcrypto_error.WC_OK.ordinal())
            throw new TZInterfaceException();
        if (k != 32)
            throw new TZInterfaceException();
        if (k1 != 1) {
            throw new TZInterfaceException();
        } else {
            byte abyte2[] = new byte[k];
            System.arraycopy(abyte1, 32, abyte2, 0, abyte2.length);
            return abyte2;
        }
    }

    public byte[] digestSHA256(byte abyte0[]) throws Exception {
        byte abyte1[] = sendReqNative(
                webcrypto_cmd.WEBCRYPTO_CMD_DIGEST_SHA256.ordinal(),
                abyte0.length, 0, 0, 0, 1, 0, 0, abyte0);
        ByteBuffer bytebuffer = ByteBuffer.wrap(abyte1);
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        int i = bytebuffer.getInt();
        int j = bytebuffer.getInt();
        int k = bytebuffer.getInt();
        int l = bytebuffer.getInt();
        int i1 = bytebuffer.getInt();
        int j1 = bytebuffer.getInt();
        if (i != webcrypto_cmd.WEBCRYPTO_CMD_DIGEST_SHA256.ordinal())
            throw new TZInterfaceException();
        if (l != 0 || i1 != 0)
            throw new TZInterfaceException();
        if (k != webcrypto_error.WC_OK.ordinal())
            throw new TZInterfaceException();
        if (j != 32)
            throw new TZInterfaceException();
        if (j1 != 1) {
            throw new TZInterfaceException();
        } else {
            byte abyte2[] = new byte[32];
            System.arraycopy(abyte1, 32, abyte2, 0, abyte2.length);
            return abyte2;
        }
    }

    public byte[] encryptDecryptAESCBC(boolean flag, int i, byte abyte0[],
            byte abyte1[]) throws Exception {
        byte abyte2[] = new byte[4 + (4 + (abyte0.length + abyte1.length))];
        ByteBuffer bytebuffer = ByteBuffer.wrap(abyte2);
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        bytebuffer.putInt(abyte0.length);
        bytebuffer.put(abyte0);
        bytebuffer.putInt(abyte1.length);
        bytebuffer.put(abyte1);
        int j;
        byte abyte3[];
        ByteBuffer bytebuffer1;
        int k;
        int l;
        int i1;
        int j1;
        int k1;
        int l1;
        if (flag)
            j = webcrypto_cmd.WEBCRYPTO_CMD_AES128_CBC_PKCS7_ENCRYPT.ordinal();
        else
            j = webcrypto_cmd.WEBCRYPTO_CMD_AES128_CBC_PKCS7_DECRYPT.ordinal();
        abyte3 = sendReqNative(j, abyte2.length, 0, i, 0, 2, 0, 0, abyte2);
        bytebuffer1 = ByteBuffer.wrap(abyte3);
        bytebuffer1.order(ByteOrder.LITTLE_ENDIAN);
        k = bytebuffer1.getInt();
        l = bytebuffer1.getInt();
        i1 = bytebuffer1.getInt();
        j1 = bytebuffer1.getInt();
        k1 = bytebuffer1.getInt();
        l1 = bytebuffer1.getInt();
        if (k != webcrypto_cmd.WEBCRYPTO_CMD_AES128_CBC_PKCS7_ENCRYPT.ordinal()
                && k != webcrypto_cmd.WEBCRYPTO_CMD_AES128_CBC_PKCS7_DECRYPT
                        .ordinal())
            throw new TZInterfaceException();
        if (j1 != 0 || k1 != 0)
            throw new TZInterfaceException();
        if (i1 != webcrypto_error.WC_OK.ordinal())
            throw new TZInterfaceException();
        if (l >= 0x63fe0)
            throw new TZInterfaceException();
        if (l1 != 1) {
            throw new TZInterfaceException();
        } else {
            byte abyte4[] = new byte[l];
            System.arraycopy(abyte3, 32, abyte4, 0, abyte4.length);
            return abyte4;
        }
    }

    public byte[] exportAESKey(int i) throws Exception {
        byte abyte0[] = sendReqNative(
                webcrypto_cmd.WEBCRYPTO_CMD_EXPORT_RAW_KEY.ordinal(), 0, 0, i,
                0, 0, 0, 0, new byte[] { -91, 90 });
        ByteBuffer bytebuffer = ByteBuffer.wrap(abyte0);
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        int j = bytebuffer.getInt();
        int k = bytebuffer.getInt();
        int l = bytebuffer.getInt();
        if (j != webcrypto_cmd.WEBCRYPTO_CMD_EXPORT_RAW_KEY.ordinal())
            throw new TZInterfaceException();
        if (l != webcrypto_error.WC_OK.ordinal())
            throw new TZInterfaceException();
        if (k >= 0x63fe0) {
            throw new TZInterfaceException();
        } else {
            byte abyte1[] = new byte[k];
            System.arraycopy(abyte0, 32, abyte1, 0, abyte1.length);
            return abyte1;
        }
    }

    public byte[] generateRandom(int i) throws Exception {
        byte abyte0[] = new byte[1];
        byte abyte1[] = sendReqNative(
                webcrypto_cmd.WEBCRPYTO_CMD_GET_RANDOM_NUMBER.ordinal(), i, 0,
                0, 0, 0, 0, 0, abyte0);
        ByteBuffer bytebuffer = ByteBuffer.wrap(abyte1);
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        int j = bytebuffer.getInt();
        int k = bytebuffer.getInt();
        int l = bytebuffer.getInt();
        int i1 = bytebuffer.getInt();
        int j1 = bytebuffer.getInt();
        int k1 = bytebuffer.getInt();
        if (j != webcrypto_cmd.WEBCRPYTO_CMD_GET_RANDOM_NUMBER.ordinal())
            throw new TZInterfaceException();
        if (i1 != 0 || j1 != 0)
            throw new TZInterfaceException();
        if (l != webcrypto_error.WC_OK.ordinal())
            throw new TZInterfaceException();
        if (k != i)
            throw new TZInterfaceException();
        if (k1 != 1) {
            throw new TZInterfaceException();
        } else {
            byte abyte2[] = new byte[k];
            System.arraycopy(abyte1, 32, abyte2, 0, abyte2.length);
            return abyte2;
        }
    }

    public String[] getKeyByName(String s) throws Exception {
        byte abyte0[] = s.getBytes();
        byte abyte1[] = sendReqNative(
                webcrypto_cmd.WEBCRPYTO_CMD_GET_KEY_BY_NAME.ordinal(),
                abyte0.length, 0, 0, 0, 1, 0, 0, abyte0);
        ByteBuffer bytebuffer = ByteBuffer.wrap(abyte1);
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        int i = bytebuffer.getInt();
        int j = bytebuffer.getInt();
        int k = bytebuffer.getInt();
        int l = bytebuffer.getInt();
        bytebuffer.getInt();
        bytebuffer.getInt();
        if (i != webcrypto_cmd.WEBCRPYTO_CMD_GET_KEY_BY_NAME.ordinal())
            throw new TZInterfaceException();
        if (k != webcrypto_error.WC_OK.ordinal()) {
            throw new TZInterfaceException();
        } else {
            byte abyte2[] = new byte[j];
            System.arraycopy(abyte1, 32, abyte2, 0, j);
            String as[] = new String[2];
            as[0] = new String(String.valueOf(l));
            as[1] = new String(abyte2);
            return as;
        }
    }

    public int importAESKey(int i, byte abyte0[]) throws Exception {
        ByteBuffer bytebuffer = ByteBuffer.wrap(sendReqNative(
                webcrypto_cmd.WEBCRYPTO_CMD_IMPORT_RAW_KEY.ordinal(),
                abyte0.length, 0, i, 0, 1, 0, 0, abyte0));
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        int j = bytebuffer.getInt();
        int k = bytebuffer.getInt();
        int l = bytebuffer.getInt();
        if (j != webcrypto_cmd.WEBCRYPTO_CMD_IMPORT_RAW_KEY.ordinal())
            throw new TZInterfaceException();
        if (l != webcrypto_error.WC_OK.ordinal())
            throw new TZInterfaceException();
        if (k != abyte0.length)
            throw new TZInterfaceException();
        if (!Arrays.equals(exportAESKey(i), abyte0))
            Log.e("TZInterface", "import/export Key failed");
        else
            Log.e("TZInterface", "test passed");
        return -1;
    }

    public byte[] unwrapKey(int i, int j, byte abyte0[], byte abyte1[],
            byte abyte2[], byte abyte3[], byte abyte4[]) throws Exception {
        byte abyte5[] = new byte[20 + (abyte0.length + abyte1.length
                + abyte2.length + abyte3.length + abyte4.length)];
        ByteBuffer bytebuffer = ByteBuffer.wrap(abyte5);
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        bytebuffer.putInt(abyte0.length);
        bytebuffer.put(abyte0);
        bytebuffer.putInt(abyte1.length);
        bytebuffer.put(abyte1);
        bytebuffer.putInt(abyte2.length);
        bytebuffer.put(abyte2);
        bytebuffer.putInt(abyte3.length);
        bytebuffer.put(abyte3);
        bytebuffer.putInt(abyte4.length);
        bytebuffer.put(abyte4);
        ByteBuffer bytebuffer1 = ByteBuffer.wrap(sendReqNative(
                webcrypto_cmd.WEBCRPYTO_CMD_AES_KEYUNWRAP.ordinal(),
                abyte5.length, 0, i, 0, 5, 0, j, abyte5));
        bytebuffer1.order(ByteOrder.LITTLE_ENDIAN);
        int k = bytebuffer1.getInt();
        bytebuffer1.getInt();
        int l = bytebuffer1.getInt();
        int i1 = bytebuffer1.getInt();
        int j1 = bytebuffer1.getInt();
        bytebuffer1.getInt();
        int k1 = bytebuffer1.getInt();
        if (k != webcrypto_cmd.WEBCRPYTO_CMD_AES_KEYUNWRAP.ordinal())
            throw new TZInterfaceException();
        if (l != webcrypto_error.WC_OK.ordinal()) {
            throw new TZInterfaceException();
        } else {
            byte abyte6[] = new byte[3];
            abyte6[0] = (byte) i1;
            abyte6[1] = (byte) k1;
            abyte6[2] = (byte) j1;
            return abyte6;
        }
    }

    public String wrapAESKey(int i, int j) throws Exception {
        byte abyte0[] = new byte[1];
        ByteBuffer bytebuffer = ByteBuffer.wrap(sendReqNative(
                webcrypto_cmd.WEBCRPYTO_CMD_AES_KEYWRAP.ordinal(),
                abyte0.length, 0, i, 0, 0, 0, j, abyte0));
        bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
        int k = bytebuffer.getInt();
        bytebuffer.getInt();
        int l = bytebuffer.getInt();
        bytebuffer.getInt();
        bytebuffer.getInt();
        bytebuffer.getInt();
        bytebuffer.position(8 + bytebuffer.position());
        if (k != webcrypto_cmd.WEBCRPYTO_CMD_AES_KEYWRAP.ordinal())
            throw new TZInterfaceException();
        if (l != webcrypto_error.WC_OK.ordinal()) {
            throw new TZInterfaceException();
        } else {
            byte abyte1[] = new byte[bytebuffer.getInt()];
            bytebuffer.get(abyte1);
            byte abyte2[] = new byte[bytebuffer.getInt()];
            bytebuffer.get(abyte2);
            byte abyte3[] = new byte[bytebuffer.getInt()];
            bytebuffer.get(abyte3);
            byte abyte4[] = new byte[bytebuffer.getInt()];
            bytebuffer.get(abyte4);
            String s = Base64.encodeToString(
                    "{\"alg\":\"A128KW\",\"enc\":\"A128GCM\"}".getBytes(), 11);
            String s1 = Base64.encodeToString(abyte1, 10);
            String s2 = Base64.encodeToString(abyte2, 10);
            String s3 = Base64.encodeToString(abyte3, 10);
            String s4 = Base64.encodeToString(abyte4, 10);
            StringBuffer stringbuffer = new StringBuffer();
            stringbuffer.append("{\"ciphertext\":\"");
            stringbuffer.append(s4);
            stringbuffer.append("\",\"initialization_vector\":\"");
            stringbuffer.append(s2);
            stringbuffer.append("\",\"recipients\":[{\"encrypted_key\":\"");
            stringbuffer.append(s1);
            stringbuffer.append("\",\"header\":\"");
            stringbuffer.append(s);
            stringbuffer.append("\",\"integrity_value\":\"");
            stringbuffer.append(s3);
            stringbuffer.append("\"}]}");
            return Base64.encodeToString(stringbuffer.toString().getBytes(), 2);
        }
    }
}
