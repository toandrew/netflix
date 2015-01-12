// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.webcrypto;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

public class WebCryptoKey {
    static enum KeyType {

        secret, PUBLIC, PRIVATE;
    }

    static enum KeyUsage {
        ENCRYPT, DECRYPT, SIGN, VERIFY, DERIVE, WRAP, UNWRAP;
    }

    private boolean mExtractable;
    private String mKeyData64;
    private RSAPrivateKey mPrivateKey;
    private RSAPublicKey mPublicKey;
    private KeyType mType;
    private List mUsages;

    public WebCryptoKey(String s, KeyType keytype, boolean flag) {
        mType = KeyType.PRIVATE;
        mExtractable = false;
        mType = keytype;
        mExtractable = flag;
        mKeyData64 = s;
    }

    public WebCryptoKey(RSAPrivateKey rsaprivatekey, KeyType keytype,
            boolean flag) {
        mType = KeyType.PRIVATE;
        mExtractable = false;
        mType = keytype;
        mExtractable = flag;
        mPrivateKey = rsaprivatekey;
    }

    public WebCryptoKey(RSAPrivateKey rsaprivatekey, RSAPublicKey rsapublickey,
            KeyType keytype, boolean flag) {
        mType = KeyType.PRIVATE;
        mExtractable = false;
        mType = keytype;
        mExtractable = flag;
        mPrivateKey = rsaprivatekey;
        mPublicKey = rsapublickey;
    }

    public WebCryptoKey(RSAPublicKey rsapublickey, KeyType keytype, boolean flag) {
        mType = KeyType.PRIVATE;
        mExtractable = false;
        mType = keytype;
        mExtractable = flag;
        mPublicKey = rsapublickey;
    }

    public String getKeyData64() {
        if (mExtractable)
            return mKeyData64;
        else
            return null;
    }

    public RSAPrivateKey getPrivateKey() {
        return mPrivateKey;
    }

    public RSAPublicKey getPublicKey() {
        return mPublicKey;
    }

    public boolean isExtractable() {
        return mExtractable;
    }

    public void setPrivateKey(RSAPrivateKey rsaprivatekey) {
        mPrivateKey = rsaprivatekey;
    }

    public void setPublicKey(RSAPublicKey rsapublickey) {
        mPublicKey = rsapublickey;
    }
}
