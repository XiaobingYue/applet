package com.xdja.syncThird.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by yxb on  2018/7/5
 */
public class Base64Encrypt {
    public String encodeStr(byte[] bs) {
        Base64 base64 = new Base64();
        return new String(base64.encodeBase64Chunked(bs));
    }
    public String getBASE64(String s) {
        Base64 base64 = new Base64();
        if (s == null){
            return null;
        }
        return new String(base64.encodeBase64Chunked(s.getBytes()));
    }

    public String getBase64FromBytes(byte[] buf) {
        Base64 base64 = new Base64();
        byte[] enbytes =  base64.encodeBase64Chunked(buf);
        return new String(enbytes);
    }

    public String getFromBASE64(String s) {
        if (s == null){
            return null;
        }
        Base64 base64 = new Base64();
        try {
            byte[] b =  base64.decodeBase64(s.getBytes());
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
    public byte[] encodeByteArr(String str) {
        Base64 base64 = new Base64();
        return base64.encodeBase64Chunked(str.getBytes());
    }
    public byte[] decode(String str) {
        Base64 base64 = new Base64();
        return base64.decode(str.getBytes());

    }
}
