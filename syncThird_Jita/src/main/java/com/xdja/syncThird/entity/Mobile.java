package com.xdja.syncThird.entity;

/**
 * Created by yxb on  2018/7/5
 */
public class Mobile {

    public Mobile() {
    }

    public Mobile(String id, String mobile, String personId) {
        this.id = id;
        this.mobile = mobile;
        this.personId = personId;
    }

    private String id;
    /**
     * 手机号
     */
    private String mobile;

    private String personId;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
