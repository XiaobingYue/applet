package com.xdja.syncThird.entity;

import java.util.Date;

/**
 * Created by yxb on  2018/7/5
 */
public class Device {
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 卡的状态 0-未审批 1-已审批 2-已写卡 3 -已开通 4-暂停 11-撤销
     */
    private String state;
    /**
     * 卡的类型：1-SIM卡；3-TF卡；2-USBKey； 6-贴膜卡
     */
    private String type;
    /**
     * 是在用标志 0：不在用 1：在用
     */
    private String flag="1";
    /**
     * 申请时间（添加时间）
     */
    private Date applyDate;
    /**
     * 开通时间
     */
    private Date openDate;
    /**
     * 审批时间
     */
    private Date examineDate;
    /**
     * 撤销时间
     */
    private Date revocationDate;
    /**
     * 暂停时间
     */
    private Date pauseDate;
    /**
     * 写卡时间
     */
    private Date writeCardDate;
    /**
     * 证书sn 第一个卡证书
     */
    private String sn;
    /**
     * 卡编号（可以是 sim卡的imsi；tf卡的硬件号，iccid；ukey卡号）HARD_NO
     */
    private String hardNo;
    /**
     * ICCID号
     */
    private String iccid;
    /**
     * 加密算法类型:1-RSA老算法，2-SM2算法
     */
    private String algType;
    /**
     * 卡证书 存储-D返回的第一个证书，默认是签名证书
     */
    private String certificate;
    /**
     *SIM卡的IMSI信息
     */
    private String imsi;
    /**
     * 手机IMEI信息--终端绑定校验用
     */
    private String imei;
    /**
     * 锁定状态，0-正常，1-锁定
     */
    private String lockState="0";
    /**
     * TF卡绑定状态：0不绑定，1绑定TF卡，2绑定SIM、TF卡
     */
    private String bindingState;
    /**
     * TF卡绑定时间
     */
    private Date bindingDate;
    /**
     * 运营商编号，1移动，2联通 3电信
     */
    private String commType;

    /**
     * 所属人
     */
    private String personId;

    /**
     * 设备信息
     */
    private Terminal terminal;

    /**
     * 手机号
     */
    private Mobile mobile;

    private String mobileId;
    /**
     * 注销标记，1已注销，0未注销
     */
    private String revokeFlag;


    /**
     * 更新时间戳
     */
    private Long timestamp=System.currentTimeMillis();
    /**
     * 使用类型，0手机，1加固
     */
    private String useType;
    /**
     * 身份认证系统人员ID ，必须为数字 20位
     */
    private String enaasUserId;
    /**
     * 身份认证状态
     */
    private String enaasState;
    /**
     * 身份认证结果描述
     */
    private String enaasDesc;
    /**
     * 视频设备编号
     */
    private String videoDeviceNO;
    /**
     * 视频设备开通状态 1开通，0不开通
     */
    private String videoDeviceOpenState = "1";
    /**
     * 第二个证书的sn
     */
    private String sn2;

    /**
     * 卡证书 双证书使用， 存储-D返回的第二个证书，默认是交换证书
     */
    private String certificate2;

    private String terminalId;

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(Date examineDate) {
        this.examineDate = examineDate;
    }

    public Date getRevocationDate() {
        return revocationDate;
    }

    public void setRevocationDate(Date revocationDate) {
        this.revocationDate = revocationDate;
    }

    public Date getPauseDate() {
        return pauseDate;
    }

    public void setPauseDate(Date pauseDate) {
        this.pauseDate = pauseDate;
    }

    public Date getWriteCardDate() {
        return writeCardDate;
    }

    public void setWriteCardDate(Date writeCardDate) {
        this.writeCardDate = writeCardDate;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getHardNo() {
        return hardNo;
    }

    public void setHardNo(String hardNo) {
        this.hardNo = hardNo;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getAlgType() {
        return algType;
    }

    public void setAlgType(String algType) {
        this.algType = algType;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getBindingState() {
        return bindingState;
    }

    public void setBindingState(String bindingState) {
        this.bindingState = bindingState;
    }

    public Date getBindingDate() {
        return bindingDate;
    }

    public void setBindingDate(Date bindingDate) {
        this.bindingDate = bindingDate;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public String getRevokeFlag() {
        return revokeFlag;
    }

    public void setRevokeFlag(String revokeFlag) {
        this.revokeFlag = revokeFlag;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getEnaasUserId() {
        return enaasUserId;
    }

    public void setEnaasUserId(String enaasUserId) {
        this.enaasUserId = enaasUserId;
    }

    public String getEnaasState() {
        return enaasState;
    }

    public void setEnaasState(String enaasState) {
        this.enaasState = enaasState;
    }

    public String getEnaasDesc() {
        return enaasDesc;
    }

    public void setEnaasDesc(String enaasDesc) {
        this.enaasDesc = enaasDesc;
    }

    public String getVideoDeviceNO() {
        return videoDeviceNO;
    }

    public void setVideoDeviceNO(String videoDeviceNO) {
        this.videoDeviceNO = videoDeviceNO;
    }

    public String getVideoDeviceOpenState() {
        return videoDeviceOpenState;
    }

    public void setVideoDeviceOpenState(String videoDeviceOpenState) {
        this.videoDeviceOpenState = videoDeviceOpenState;
    }

    public String getSn2() {
        return sn2;
    }

    public void setSn2(String sn2) {
        this.sn2 = sn2;
    }

    public String getCertificate2() {
        return certificate2;
    }

    public void setCertificate2(String certificate2) {
        this.certificate2 = certificate2;
    }
}
