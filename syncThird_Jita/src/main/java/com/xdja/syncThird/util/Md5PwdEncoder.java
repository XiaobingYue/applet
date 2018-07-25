package com.xdja.syncThird.util;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yxb on  2018/7/5
 */
public final class Md5PwdEncoder {
    /**
     * Md5PwdEncoder类的单例对象
     */
    private static Md5PwdEncoder instance = new Md5PwdEncoder();
    private static MD5 md5=new MD5();

    /**
     * 获取Md5PwdEncoder类单例对象
     *
     * @return Md5PwdEncoder类的单例对象
     */
    public static Md5PwdEncoder getInstance() {
        return instance;
    }

    /**
     * Md5PwdEncoder类私有构造方法
     */
    private Md5PwdEncoder() {
    }

    /**
     *
     * 方法描述：加密密码
     *
     * @author: 任瑞修
     * @date: 2012-6-29 下午03:16:09
     * @param rawPass
     *            待加密密码
     * @return MD5加密后的密码信息
     */
    public String encodePassword(String rawPass) {
        return md5.getEncodePass(rawPass);
//		return encodePassword(rawPass, defaultSalt).toUpperCase();
    }

    /**
     *
     * 方法描述：按照混淆码加密密码
     *
     * @author: 任瑞修
     * @date: 2012-6-29 下午03:15:51
     * @param rawPass
     *            加密密码
     * @param salt
     *            混淆因子
     * @return 按照混淆码加密后的密码
     */
    public String encodePassword(String rawPass, String salt) {
        String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
        MessageDigest messageDigest = getMessageDigest();
        byte[] digest;
        try {
            digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported!");
        }
        return new String(Hex.encodeHex(digest));
    }

    /**
     *
     * 方法描述：使用默认的混淆因子校验密码
     *
     * @author: 任瑞修
     * @date: 2012-6-29 下午03:14:43
     * @param encPass
     *            加密后的密码
     * @param rawPass
     *            原始密码
     * @return 校验结果：true-成功，false-失败
     */
    public boolean isPasswordValid(String encPass, String rawPass) {
        return isPasswordValid(encPass, rawPass, defaultSalt);
    }

    /**
     *
     * 方法描述：按照混淆码校验密码
     *
     * @author: 任瑞修
     * @date: 2012-6-29 下午03:14:56
     * @param encPass
     *            加密后的密码
     * @param rawPass
     *            原始密码
     * @param salt
     *            混淆因子
     * @return 校验结果：true-成功，false-失败
     */
    public boolean isPasswordValid(String encPass, String rawPass, String salt) {
        if (encPass == null) {
            return false;
        }
        return encPass.equals(encodePassword(rawPass, salt));
    }

    /**
     * 获取MessageDigest（信息摘要）类的MD5算法实例
     *
     * @return MessageDigest类的MD5算法实例
     */
    protected MessageDigest getMessageDigest() {
        String algorithm = "MD5";
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm ["
                    + algorithm + "]");
        }
    }

    /**
     * 连接密码和混淆因子
     *
     * @param password
     *            密码字符串
     * @param salt
     *            混淆因子
     * @param strict
     *            是否验证混淆因子格式，当为true时，需要验证salt.toString()的返回值是否包含'{'或'}'符号，当包含这些符号时
     *            ，方法抛出IllegalArgumentException
     * @return 返回连接后的密码和混淆因子字符串，结果形如：password{salt.toString()}
     */
    protected String mergePasswordAndSalt(String password, Object salt,
                                          boolean strict) {
        if (password == null) {
            password = "";
        }
        if (strict && (salt != null)) {
            if ((salt.toString().lastIndexOf("{") != -1)
                    || (salt.toString().lastIndexOf("}") != -1)) {
                throw new IllegalArgumentException(
                        "Cannot use { or } in salt.toString()");
            }
        }
        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

    /**
     * 混淆码。防止破解。
     */
    private String defaultSalt;

    /**
     *
     * 方法描述：获得对象默认的混淆码
     *
     * @author: 任瑞修
     * @date: 2012-6-29 下午03:14:21
     * @return 默认的混淆码
     */
    public String getDefaultSalt() {
        return defaultSalt;
    }

    /**
     *
     * 方法描述：设置对象默认混淆码
     *
     * @author: 任瑞修
     * @date: 2012-6-29 下午03:14:29
     * @param defaultSalt
     *            默认混淆码
     */
    public void setSefaultSalt(String defaultSalt) {
        this.defaultSalt = defaultSalt;
    }
}
