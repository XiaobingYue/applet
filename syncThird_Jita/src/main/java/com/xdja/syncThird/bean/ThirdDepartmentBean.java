package com.xdja.syncThird.bean;

import com.xdja.syncThird.entity.ThirdDepartmentEntity;

import java.util.Date;

/**
 * Created by yxb on  2018/7/2
 */
public class ThirdDepartmentBean extends ThirdDepartmentEntity {
    /**
     * 最后更新时间 YYYY-MM-DD HH:mi:ss
     */
    private Date lastUpdateTimeDate;
    /**
     * 最后更新时间 时间戳
     */
    private String lastUpdateTimeStamp;

    public Date getLastUpdateTimeDate() {
        return lastUpdateTimeDate;
    }

    public void setLastUpdateTimeDate(Date lastUpdateTimeDate) {
        this.lastUpdateTimeDate = lastUpdateTimeDate;
    }

    public String getLastUpdateTimeStamp() {
        return lastUpdateTimeStamp;
    }

    public void setLastUpdateTimeStamp(String lastUpdateTimeStamp) {
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    }
}
