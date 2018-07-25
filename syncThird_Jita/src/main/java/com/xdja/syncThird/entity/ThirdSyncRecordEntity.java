package com.xdja.syncThird.entity;

import java.util.Date;

/**
 * Created by yxb on  2018/7/3
 */
public class ThirdSyncRecordEntity {

    private String id;

    private String lastUpdateTime;

    private Date createDate;
    /**
     * 同步类型 dep,person,device
     */
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
