package com.xdja.syncThird.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

/**
 * Created by yxb on  2018/7/4
 */
public class Department {

    private String id;
    /**
     * 单位编码
     */
    private String code;
    /**
     * 单位名称
     */
    private String name;
    /**
     * 单位名称简写
     */
    private String nameAbbr;
    /**
     * 父级单位id（行政）
     */
    private String parentID;
    /**
     * 父级单位
     */
    private Department parentDep;
    /**
     * 父级单位id2（业务，暂时不用）
     */
    private String parentID2;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 办公电话
     */
    private String phone;
    /**
     * 是否直属单位
     */
    private String isDirectDep;
    /**
     * 单位级别
     */
    private String level;
    /**
     * 单位人数
     */
    private int count = 0;
    /**
     * 更新时间戳
     */
    private long timestamp=System.currentTimeMillis();
    /**
     * 备注说明
     */
    private String note;
    /**
     * 排序号
     */
    private long orderField=999;
    /**
     * 是否删除标志字段，0未删除；1删除。默认为0
     */
    private String flag = "0";
    /**
     * 显示状态 ： 1显示 2不显示
     */
    private String displayState = "1";
    /**
     * 子单位
     */
    private List<Department> childDeps;

    /**
     * depType : 单位类型，单位：org， 部门：dept
     */
    private String depType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAbbr() {
        return nameAbbr;
    }

    public void setNameAbbr(String nameAbbr) {
        this.nameAbbr = nameAbbr;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public Department getParentDep() {
        return parentDep;
    }

    public void setParentDep(Department parentDep) {
        this.parentDep = parentDep;
    }

    public String getParentID2() {
        return parentID2;
    }

    public void setParentID2(String parentID2) {
        this.parentID2 = parentID2;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsDirectDep() {
        return isDirectDep;
    }

    public void setIsDirectDep(String isDirectDep) {
        this.isDirectDep = isDirectDep;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getDisplayState() {
        return displayState;
    }

    public void setDisplayState(String displayState) {
        this.displayState = displayState;
    }

    public List<Department> getChildDeps() {
        return childDeps;
    }

    public void setChildDeps(List<Department> childDeps) {
        this.childDeps = childDeps;
    }

    public String getDepType() {
        return depType;
    }

    public void setDepType(String depType) {
        this.depType = depType;
    }
}
