package com.xdja.syncThird.entity;

/**
 * Created by yxb on  2018/7/2
 */
public class ThirdDepartmentEntity {

    private String id;
    /**
     * pams单位id
     */
    private String depId;

    private String thirdId;

    private  String thirdParentId;

    private String name;

    private String shortName;

    private  String code;

    private String parentCode;

    /**
     * 更新时间戳
     */
    private String timestamp;

    private String errInfo;
    /**
     * 表示 。1 删除 0 未删除
     */
    private  String flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdParentId() {
        return thirdParentId;
    }

    public void setThirdParentId(String thirdParentId) {
        this.thirdParentId = thirdParentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
