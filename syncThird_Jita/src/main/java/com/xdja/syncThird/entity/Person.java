package com.xdja.syncThird.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by yxb on  2018/7/5
 */
public class Person {

    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 姓名拼音首字母,多音字使用逗号分隔
     */
    private String nameBriefSpell;
    /**
     * 性别
     */
    private String sex;
    /**
     * 警号
     */
    private String code;
    /**
     * 身份证号
     */
    private String identifier;
    /**
     * 所属单位id
     */
    private String depId;
    /**
     * 所属单位名称，人员编辑页面，
     */
    private String depName;
    /**
     * 所属单位编码
     */
    private String depCode;
    /**
     * 职级
     */
    private String position;
    /**
     * 职务（手工录入，暂时不用）
     */
    private String positionInput;
    /**
     * 办公电话
     */
    private String officePhone;
    /**
     * 备注说明
     */
    private String note;
    /**
     * 警种
     */
    private String police;
    /**
     * 手机小号（暂时不用）
     */
    private String sMobile;
    /**
     * 领导级别
     */
    private String leaderLevel = "3";
    /**
     * 更新时间戳
     */
    private long timestamp=System.currentTimeMillis();
    /**
     * 终端锁定状态
     */
    private String lockState = "0";
    /**
     * 人员状态 1 正常，2死亡，3退休
     */
    private String personState = "1";
    /**
     * 最后一次失败登录时间（暂时不用）
     */
    private Date lastErrorLoginTime;
    /**
     * 后台登录失败次数
     */
    private int loginErrorTimes=0;
    /**
     * 密码
     */
    private String password;
    /**
     * 最近密码更新时间（暂时不用）
     */
    private Date lastUpdatePassTime;
    /**
     * 终端登录上线时间
     */
    private Date clientLoginTime;

    /**
     * 最后一次修改密码时间
     */
    private Date lastChangePwDate;

    /**
     * 录入人员id
     */
    private String creatorId;
    /**
     * 录入时间
     */
    private Date createDate=new Date();
    /**
     * 后台主题
     */
    private String theme;
    /**
     * 排序号
     */
    private long orderField=999;
    /**
     * 是否删除标志字段，0未删除；1删除。默认为0
     */
    private String flag;
    /**
     * 级别
     */
    private String grade;
    /**
     * 运营商编号，1移动，2联通 3电信(加固使用，暂时保留，一人一卡时录入页面提供)
     */
    private String commType;
    /**
     * 手机号(加固使用，暂时保留，一人一卡时存入)
     */
    private String mobile;
    /**
     * 人员所属单位对象
     */
    private Department department;
    /**
     * 人员所在分组的id，用逗号分隔（用于人员编辑页面）
     */

    private String groupIds;
    /**
     * 人员对应的手机号集合
     */
    private List<Mobile> mobileList;
    /**
     * 显示状态 ：  1显示 2不显示
     */
    private String displayState = "1";
    /**
     * 人员类型
     */
    private String personType;
    /**
     * @Fields pcId : PC端登录标识
     */
    private String pcId;

    /**
     * @Fields mobilePersona : 私人手机号
     */
    private String mobilePersonal;

    /**
     * @Fields mobileMultimediaMessage : 彩信手机号
     */
    private String mobileMultimediaMessage;
    /**
     * 警信用户 0不是，1是
     */
    private String jxFlag;

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

    public String getNameBriefSpell() {
        return nameBriefSpell;
    }

    public void setNameBriefSpell(String nameBriefSpell) {
        this.nameBriefSpell = nameBriefSpell;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionInput() {
        return positionInput;
    }

    public void setPositionInput(String positionInput) {
        this.positionInput = positionInput;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getsMobile() {
        return sMobile;
    }

    public void setsMobile(String sMobile) {
        this.sMobile = sMobile;
    }

    public String getLeaderLevel() {
        return leaderLevel;
    }

    public void setLeaderLevel(String leaderLevel) {
        this.leaderLevel = leaderLevel;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getPersonState() {
        return personState;
    }

    public void setPersonState(String personState) {
        this.personState = personState;
    }

    public Date getLastErrorLoginTime() {
        return lastErrorLoginTime;
    }

    public void setLastErrorLoginTime(Date lastErrorLoginTime) {
        this.lastErrorLoginTime = lastErrorLoginTime;
    }

    public int getLoginErrorTimes() {
        return loginErrorTimes;
    }

    public void setLoginErrorTimes(int loginErrorTimes) {
        this.loginErrorTimes = loginErrorTimes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastUpdatePassTime() {
        return lastUpdatePassTime;
    }

    public void setLastUpdatePassTime(Date lastUpdatePassTime) {
        this.lastUpdatePassTime = lastUpdatePassTime;
    }

    public Date getClientLoginTime() {
        return clientLoginTime;
    }

    public void setClientLoginTime(Date clientLoginTime) {
        this.clientLoginTime = clientLoginTime;
    }

    public Date getLastChangePwDate() {
        return lastChangePwDate;
    }

    public void setLastChangePwDate(Date lastChangePwDate) {
        this.lastChangePwDate = lastChangePwDate;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public long getOrderField() {
        return orderField;
    }

    public void setOrderField(long orderField) {
        this.orderField = orderField;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public List<Mobile> getMobileList() {
        return mobileList;
    }

    public void setMobileList(List<Mobile> mobileList) {
        this.mobileList = mobileList;
    }

    public String getDisplayState() {
        return displayState;
    }

    public void setDisplayState(String displayState) {
        this.displayState = displayState;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public String getMobilePersonal() {
        return mobilePersonal;
    }

    public void setMobilePersonal(String mobilePersonal) {
        this.mobilePersonal = mobilePersonal;
    }

    public String getMobileMultimediaMessage() {
        return mobileMultimediaMessage;
    }

    public void setMobileMultimediaMessage(String mobileMultimediaMessage) {
        this.mobileMultimediaMessage = mobileMultimediaMessage;
    }

    public String getJxFlag() {
        return jxFlag;
    }

    public void setJxFlag(String jxFlag) {
        this.jxFlag = jxFlag;
    }
}
