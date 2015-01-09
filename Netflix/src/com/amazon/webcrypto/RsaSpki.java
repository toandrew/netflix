// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.webcrypto;

import android.util.Base64;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

// Referenced classes of package com.amazon.webcrypto:
//			SpkiParsingException

public class RsaSpki {

    private static final boolean DEBUG = true;
    private byte mBase64DecodedData[];
    private BigInteger mExponent;
    private int mGlobalOffset;
    private BigInteger mModulus;
    private final byte mRsaSpkiObjectIdentifier[] = { 42, -122, 72, -122, -9,
            13, 1, 1, 1 };
    public final String spki64Test = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsjUf4UZyuZ5JKazU0Kq/dbaVY0oQxYZcsCQxRrjKF6yQaHACzeubHHaXRLwXkCVvBf2V0HBdJ/xCiIqwos3XCMgMWu0mzlSxfv0kyAuH46SzTZdAt5hJPMSjt+eTJI+9hYq6DNqN09ujBwlhQM2JhI9V3tZhBD5nQPTNkXYRD3aZp5wWtErIdXDP4ZXFcPdu6sLjH68WZuR9M6Q5XztzO9DA7+m/7CHDvWWhlvuN15t1a4dwBuxlY0eZh1JjM6OPH9zJ2OKJIVOLNIE2WejQE5a7IarLOHM8bYtBZ7/tSyx3MkN40OjPA7ZWiEpyT/wDiZo45aLlN4vsWJpIcdqSRwIDAQAB";
    public final String spki64Test2 = "UIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsjUf4UZyuZ5JKazU0Kq/dbaVY0oQxYZcsCQxRrjKF6yQaHACzeubHHaXRLwXkCVvBf2V0HBdJ/xCiIqwos3XCMgMWu0mzlSxfv0kyAuH46SzTZdAt5hJPMSjt+eTJI+9hYq6DNqN09ujBwlhQM2JhI9V3tZhBD5nQPTNkXYRD3aZp5wWtErIdXDP4ZXFcPdu6sLjH68WZuR9M6Q5XztzO9DA7+m/7CHDvWWhlvuN15t1a4dwBuxlY0eZh1JjM6OPH9zJ2OKJIVOLNIE2WejQE5a7IarLOHM8bYtBZ7/tSyx3MkN40OjPA7ZWiEpyT/wDiZo45aLlN4vsWJpIcdqSRwIDAQAB";

    public RsaSpki() {
        mGlobalOffset = 0;
        setBase64Encoded("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsjUf4UZyuZ5JKazU0Kq/dbaVY0oQxYZcsCQxRrjKF6yQaHACzeubHHaXRLwXkCVvBf2V0HBdJ/xCiIqwos3XCMgMWu0mzlSxfv0kyAuH46SzTZdAt5hJPMSjt+eTJI+9hYq6DNqN09ujBwlhQM2JhI9V3tZhBD5nQPTNkXYRD3aZp5wWtErIdXDP4ZXFcPdu6sLjH68WZuR9M6Q5XztzO9DA7+m/7CHDvWWhlvuN15t1a4dwBuxlY0eZh1JjM6OPH9zJ2OKJIVOLNIE2WejQE5a7IarLOHM8bYtBZ7/tSyx3MkN40OjPA7ZWiEpyT/wDiZo45aLlN4vsWJpIcdqSRwIDAQAB");
        setBase64Encoded("UIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsjUf4UZyuZ5JKazU0Kq/dbaVY0oQxYZcsCQxRrjKF6yQaHACzeubHHaXRLwXkCVvBf2V0HBdJ/xCiIqwos3XCMgMWu0mzlSxfv0kyAuH46SzTZdAt5hJPMSjt+eTJI+9hYq6DNqN09ujBwlhQM2JhI9V3tZhBD5nQPTNkXYRD3aZp5wWtErIdXDP4ZXFcPdu6sLjH68WZuR9M6Q5XztzO9DA7+m/7CHDvWWhlvuN15t1a4dwBuxlY0eZh1JjM6OPH9zJ2OKJIVOLNIE2WejQE5a7IarLOHM8bYtBZ7/tSyx3MkN40OjPA7ZWiEpyT/wDiZo45aLlN4vsWJpIcdqSRwIDAQAB");
        if (!getBase64Encoded()
                .equals("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsjUf4UZyuZ5JKazU0Kq/dbaVY0oQxYZcsCQxRrjKF6yQaHACzeubHHaXRLwXkCVvBf2V0HBdJ/xCiIqwos3XCMgMWu0mzlSxfv0kyAuH46SzTZdAt5hJPMSjt+eTJI+9hYq6DNqN09ujBwlhQM2JhI9V3tZhBD5nQPTNkXYRD3aZp5wWtErIdXDP4ZXFcPdu6sLjH68WZuR9M6Q5XztzO9DA7+m/7CHDvWWhlvuN15t1a4dwBuxlY0eZh1JjM6OPH9zJ2OKJIVOLNIE2WejQE5a7IarLOHM8bYtBZ7/tSyx3MkN40OjPA7ZWiEpyT/wDiZo45aLlN4vsWJpIcdqSRwIDAQAB"))
            throw new NullPointerException();
        else
            return;
    }

    private int byte2uint(byte byte0) {
        return 0 | byte0 & 0xff;
    }

    private void checkLen(int i) throws SpkiParsingException {
        if (mBase64DecodedData.length - i - mGlobalOffset < 0)
            throw new SpkiParsingException();
        else
            return;
    }

    private void checkObjectIdentifier(byte abyte0[])
            throws SpkiParsingException {
        if (!Arrays.equals(mRsaSpkiObjectIdentifier, abyte0))
            throw new SpkiParsingException();
        else
            return;
    }

    private void clearGlobalOffset() {
        mGlobalOffset = 0;
    }

    private void findNextTag(byte byte0) throws SpkiParsingException {
        while (mBase64DecodedData[mGlobalOffset] != byte0) {
            mGlobalOffset = 1 + mGlobalOffset;
            if (mGlobalOffset >= mBase64DecodedData.length)
                throw new SpkiParsingException();
        }
        incGlobalOffset(1);
    }

    private byte[] getSubByteArray(byte abyte0[], int i, int j) {
        byte abyte1[] = new byte[j];
        System.arraycopy(abyte0, i, abyte1, 0, j);
        return abyte1;
    }

    private void incGlobalOffset(int i) {
        mGlobalOffset = i + mGlobalOffset;
    }

    private int lenLen(int i) {
        if (i < 127)
            return 1;
        return i > 255 ? 3 : 2;
    }

    private void putLen2ByteBuffer(ByteBuffer bytebuffer, int i) {
        if (i < 127) {
            bytebuffer.put((byte) i);
            return;
        }
        if (i <= 255) {
            bytebuffer.put((byte) -127);
            bytebuffer.put((byte) i);
            return;
        } else {
            bytebuffer.put((byte) -126);
            bytebuffer.put((byte) (i / 256));
            bytebuffer.put((byte) (i % 256));
            return;
        }
    }

    private int readLength() throws SpkiParsingException {
        return readLength(mGlobalOffset);
    }

    private int readLength(int i) throws SpkiParsingException {
        int j = i;
        int k;
        if ((0x80 & mBase64DecodedData[j]) == 128) {
            int l = 0x7f & mBase64DecodedData[j];
            k = 0;
            if (l > 3)
                throw new SpkiParsingException();
            while (--l >= 0) {
                j++;
                incGlobalOffset(1);
                k = (int) ((double) k + Math.pow(2D, l * 8)
                        * (double) byte2uint(mBase64DecodedData[j]));
            }
        } else {
            k = byte2uint(mBase64DecodedData[j]);
        }
        incGlobalOffset(1);
        checkLen(k);
        return k;
    }

    public String getBase64Encoded() {
        byte abyte0[] = getRawModulus().toByteArray();
        byte abyte1[] = getRawExponent().toByteArray();
        int i = abyte0.length;
        int j = abyte1.length;
        int k = i + (1 + lenLen(i));
        int l = 0 + k;
        int i1 = j + (1 + lenLen(j));
        int j1 = 1 + (l + i1 + lenLen(k + i1));
        int k1 = j1 + 1;
        int l1 = j1 + (2 + lenLen(j1));
        int i2 = 2 + (1 + lenLen(mRsaSpkiObjectIdentifier.length) + mRsaSpkiObjectIdentifier.length);
        int j2 = l1 + (1 + (i2 + lenLen(i2)));
        ByteBuffer bytebuffer = ByteBuffer.allocate(1 + (j2 + lenLen(j2)));
        bytebuffer.put((byte) 48);
        putLen2ByteBuffer(bytebuffer, j2);
        bytebuffer.put((byte) 48);
        putLen2ByteBuffer(bytebuffer, i2);
        bytebuffer.put((byte) 6);
        bytebuffer.put((byte) mRsaSpkiObjectIdentifier.length);
        bytebuffer.put(mRsaSpkiObjectIdentifier);
        bytebuffer.put((byte) 5);
        bytebuffer.put((byte) 0);
        bytebuffer.put((byte) 3);
        putLen2ByteBuffer(bytebuffer, k1);
        bytebuffer.put((byte) 0);
        bytebuffer.put((byte) 48);
        putLen2ByteBuffer(bytebuffer, k + i1);
        bytebuffer.put((byte) 2);
        putLen2ByteBuffer(bytebuffer, i);
        bytebuffer.put(abyte0);
        bytebuffer.put((byte) 2);
        putLen2ByteBuffer(bytebuffer, j);
        bytebuffer.put(abyte1);
        return new String(Base64.encode(bytebuffer.array(), 2));
    }

    public BigInteger getRawExponent() {
        return mExponent;
    }

    public BigInteger getRawModulus() {
        return mModulus;
    }

    public byte[] getSubByteArray(int i) {
        return getSubByteArray(mBase64DecodedData, mGlobalOffset, i);
    }

    public boolean setBase64Encoded(String s) {
        mBase64DecodedData = Base64.decode(s, 2);
        clearGlobalOffset();
        try {
            findNextTag((byte) 48);
            checkLen(readLength());
            findNextTag((byte) 48);
            readLength();
            findNextTag((byte) 6);
            int i = readLength();
            checkObjectIdentifier(getSubByteArray(i));
            incGlobalOffset(i);
            findNextTag((byte) 2);
            int j = readLength();
            byte abyte0[] = getSubByteArray(j);
            incGlobalOffset(j);
            mModulus = new BigInteger(abyte0);
            findNextTag((byte) 2);
            int k = readLength();
            byte abyte1[] = getSubByteArray(k);
            incGlobalOffset(k);
            mExponent = new BigInteger(abyte1);
        } catch (SpkiParsingException spkiparsingexception) {
            clearGlobalOffset();
            return false;
        }
        clearGlobalOffset();
        return true;
    }

    public void setRawExponent(BigInteger biginteger) {
        mExponent = biginteger;
    }

    public void setRawModulus(BigInteger biginteger) {
        mModulus = biginteger;
    }
}
