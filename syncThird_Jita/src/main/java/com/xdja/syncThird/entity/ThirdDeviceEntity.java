package com.xdja.syncThird.entity;

/**
 * Created by yxb on  2018/7/3
 */
public class ThirdDeviceEntity {

    private String id;
    /**
     * pams设备ID
     */
    private String deviceId;

    private String thirdId;

    private String personId;
    /**
     * 更新时间戳
     */
    private String timestamp;

    private String cardNo;
    /**
     * RSA签名证书sn
     */
    private String snSignCertRSA;
    /**
     *  RSA加密证书sn
     */
    private String snEncCertRSA;
    /**
     * RSA签名证书实体
     */
    private String certentitySignRSA;
    /**
     * RSA加密证书实体
     */
    private String certentityEncRSA;

    /**
     * SM2签名证书sn
     */
    private String snSignCertSM2;
    /**
     *  SM2加密证书sn
     */
    private String snEncCertSM2;
    /**
     * SM2签名证书实体
     */
    private String certentitySignSM2;
    /**
     * SM2加密证书实体
     */
    private String certentityEncSM2;

    /**
     * 表示 。1 撤销，3开通 , 4暂停
     */
    private String flag;

    /**
     * 证书类别[RSA|RSA-SM2|国密]
     */
    private String certType;

    private String errInfo;
    /**
     * 安全卡类型
     */
    private String cardType;

    /**
     * 设备原始id（同步撤卡信息用）
     */
    private String deviceIdOrg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSnSignCertRSA() {
        return snSignCertRSA;
    }

    public void setSnSignCertRSA(String snSignCertRSA) {
        this.snSignCertRSA = snSignCertRSA;
    }

    public String getSnEncCertRSA() {
        return snEncCertRSA;
    }

    public void setSnEncCertRSA(String snEncCertRSA) {
        this.snEncCertRSA = snEncCertRSA;
    }

    public String getCertentitySignRSA() {
        return certentitySignRSA;
    }

    public void setCertentitySignRSA(String certentitySignRSA) {
        this.certentitySignRSA = certentitySignRSA;
    }

    public String getCertentityEncRSA() {
        return certentityEncRSA;
    }

    public void setCertentityEncRSA(String certentityEncRSA) {
        this.certentityEncRSA = certentityEncRSA;
    }

    public String getSnSignCertSM2() {
        return snSignCertSM2;
    }

    public void setSnSignCertSM2(String snSignCertSM2) {
        this.snSignCertSM2 = snSignCertSM2;
    }

    public String getSnEncCertSM2() {
        return snEncCertSM2;
    }

    public void setSnEncCertSM2(String snEncCertSM2) {
        this.snEncCertSM2 = snEncCertSM2;
    }

    public String getCertentitySignSM2() {
        return certentitySignSM2;
    }

    public void setCertentitySignSM2(String certentitySignSM2) {
        this.certentitySignSM2 = certentitySignSM2;
    }

    public String getCertentityEncSM2() {
        return certentityEncSM2;
    }

    public void setCertentityEncSM2(String certentityEncSM2) {
        this.certentityEncSM2 = certentityEncSM2;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getDeviceIdOrg() {
        return deviceIdOrg;
    }

    public void setDeviceIdOrg(String deviceIdOrg) {
        this.deviceIdOrg = deviceIdOrg;
    }
}
