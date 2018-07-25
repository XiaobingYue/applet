package com.xdja.syncThird.entity;

/**
 * Created by yxb on  2018/7/3
 */
public class ThirdPersonEntity {

    private String id;

    private String personId;

    private String thirdId;

    private String identifier;

    private String code;

    private String mobile;

    private  String police;

    private   String position;

    private   String officePhone;

    private    String sex;

    private    String sort;


    /**
     *  部门UUID
     */
    private   String depId;

    private    String name;

    private   String errInfo;
    /**
     * 更新时间戳
     */
    private String timestamp;

    /**
     * personType : 人员类型
     */
    private String personType;

    /**
     *  运营商编号，1移动，2联通 3电信
     */
    private String commType;
    /**
     * 表示 。1 删除 0 未删除
     */
    private  String flag;
    /**
     * TF卡手机号
     */
    private String tfMobile;
    /**
     * TF卡运营商
     */
    private String tfCommType;
    /**
     * pos机TF卡手机号
     */
    private String posTfMobile;
    /**
     * pos机TF卡运营商
     */
    private String posTfCommType;
    /**
     * usbKey手机号
     */
    private String usbKeyMobile;
    /**
     * usbKey运营商
     */
    private String usbKeyCommType;

    public String getTfMobile() {
        return tfMobile;
    }

    public void setTfMobile(String tfMobile) {
        this.tfMobile = tfMobile;
    }

    public String getTfCommType() {
        return tfCommType;
    }

    public void setTfCommType(String tfCommType) {
        this.tfCommType = tfCommType;
    }

    public String getPosTfMobile() {
        return posTfMobile;
    }

    public void setPosTfMobile(String posTfMobile) {
        this.posTfMobile = posTfMobile;
    }

    public String getPosTfCommType() {
        return posTfCommType;
    }

    public void setPosTfCommType(String posTfCommType) {
        this.posTfCommType = posTfCommType;
    }

    public String getUsbKeyMobile() {
        return usbKeyMobile;
    }

    public void setUsbKeyMobile(String usbKeyMobile) {
        this.usbKeyMobile = usbKeyMobile;
    }

    public String getUsbKeyCommType() {
        return usbKeyCommType;
    }

    public void setUsbKeyCommType(String usbKeyCommType) {
        this.usbKeyCommType = usbKeyCommType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
