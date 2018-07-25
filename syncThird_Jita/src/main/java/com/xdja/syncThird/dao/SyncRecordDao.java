package com.xdja.syncThird.dao;

import com.xdja.syncThird.entity.ThirdSyncRecordEntity;
import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;

/**
 * Created by yxb on  2018/7/3
 */
@DB(name = "oracle")
public interface SyncRecordDao {

    @SQL("select * from (select  *  from T_Third_SYN_RECORD where type = :1 order by create_date desc)where rownum=1  ")
    ThirdSyncRecordEntity getLatastRecord(String type);


    @SQL("insert into T_Third_SYN_RECORD t (t.id,t.LAST_UPDATE_TIME,t.CREATE_DATE,t.TYPE) values (:1.id,:1.lastUpdateTime,:1.createDate,:1.type)")
    void addRecord(ThirdSyncRecordEntity newRecord);
}
