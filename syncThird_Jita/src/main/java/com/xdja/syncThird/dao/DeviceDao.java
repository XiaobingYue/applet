package com.xdja.syncThird.dao;

import com.xdja.syncThird.entity.Device;
import com.xdja.syncThird.entity.Terminal;
import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.Result;
import org.jfaster.mango.annotation.Results;
import org.jfaster.mango.annotation.SQL;

import java.util.List;

/**
 * Created by yxb on  2018/7/2
 */
@DB(name = "oracle")
public interface DeviceDao {

    @Results({
            @Result(column = "CARD_ID",property = "hardNo"),
            @Result(column = "N_LAST_UPDATE_TIME",property = "timestamp"),
            @Result(column = "VIDEO_OPEN_STATE",property = "videoDeviceOpenState"),
            @Result(column = "REVOKEFLAG",property = "revokeFlag")
    })
    @SQL("select * from T_DEVICE t where t.ID = :1")
    Device queryById(String deviceId);

    @SQL("update T_DEVICE set CARD_ID =  :1.hardNo,ICCID= :1.iccid,STATE= :1.state,PERSON_ID =  :1.personId," +
            "WRITE_CARD_DATE =  :1.writeCardDate,TYPE= :1.type,USE_TYPE= :1.useType,MOBILE_ID= :1.mobileId,SN= :1.sn," +
            "CERTIFICATE= :1.certificate,SN2= :1.sn2,CERTIFICATE2= :1.certificate2,TERMINAL_ID= :1.terminalId," +
            "LOCK_STATE= :1.lockState,N_LAST_UPDATE_TIME= :1.timestamp,VIDEO_OPEN_STATE= :1.videoDeviceOpenState," +
            "COMM_TYPE= :1.commType,FLAG= :1.flag,REVOKEFLAG = :1.revokeFlag,REVOCATION_DATE = :1.revocationDate where ID = :1.id")
    void updateDevice(Device device);

    @SQL("insert into T_DEVICE (ID,CARD_ID,ICCID,STATE,PERSON_ID,WRITE_CARD_DATE,TYPE,USE_TYPE,MOBILE_ID,SN," +
            "CERTIFICATE,TERMINAL_ID,LOCK_STATE,N_LAST_UPDATE_TIME,VIDEO_OPEN_STATE,COMM_TYPE,FLAG)  " +
            "values (:1.id,:1.hardNo,:1.iccid,:1.state,:1.personId,:1.writeCardDate,:1.type,:1.useType,:1.mobileId,:1.sn,:1.certificate," +
            ":1.terminalId,:1.lockState,:1.timestamp,:1.videoDeviceOpenState,:1.commType,:1.flag) ")
    void saveDevice(Device device);

    @Results({
            @Result(column = "CARD_ID",property = "hardNo"),
            @Result(column = "N_LAST_UPDATE_TIME",property = "timestamp"),
            @Result(column = "VIDEO_OPEN_STATE",property = "videoDeviceOpenState"),
            @Result(column = "REVOKEFLAG",property = "revokeFlag")
    })
    @SQL("select * from T_DEVICE t where t.CARD_ID = :1")
    Device getByCardNO(String cardNo);

    @SQL("insert into T_TERMINAL (ID,TERMINALBAND,TERMINALNAME,TERMINALOS,TERMINALTYPE) " +
            "values (:1.id,:1.terminalband,:1.terminalname,:1.terminalos,:1.terminaltype)")
    void saveTerminal(Terminal terminal);

    @SQL("update T_DEVICE set mobile_id = null where mobile_id in(select id from T_BIMS_MOBILE where person_id is null)")
    void updateDeviceWhereMobileUnExist();

    @Results({
            @Result(column = "CARD_ID",property = "hardNo"),
            @Result(column = "N_LAST_UPDATE_TIME",property = "timestamp"),
            @Result(column = "VIDEO_OPEN_STATE",property = "videoDeviceOpenState"),
            @Result(column = "REVOKEFLAG",property = "revokeFlag")
    })
    @SQL("select * from T_DEVICE t where t.person_id = :1")
    List<Device> queryByPersonId(String id);

    @Results({
            @Result(column = "CARD_ID",property = "hardNo"),
            @Result(column = "N_LAST_UPDATE_TIME",property = "timestamp"),
            @Result(column = "VIDEO_OPEN_STATE",property = "videoDeviceOpenState"),
            @Result(column = "REVOKEFLAG",property = "revokeFlag")
    })
    @SQL("select * from T_DEVICE t where t.mobile_id is null and t.PERSON_ID = :1 and t.REVOKEFLAG is null")
    List<Device> queryNoMobileDeviceByPersonId(String id);
}
