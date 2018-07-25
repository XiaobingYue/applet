package com.xdja.syncThird.dao;


import com.xdja.syncThird.bean.ThirdDepartmentBean;
import com.xdja.syncThird.bean.ThirdDeviceBean;
import com.xdja.syncThird.bean.ThirdPersonBean;
import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;
import org.jfaster.mango.plugin.page.Page;

import java.util.List;

/**
 * Created by yxb on  2018/7/2
 */
@DB(name = "jita")
public interface JitaDao {


    @SQL("select * " +
            "from (" +
            "select  " +
            "id as thirdId, " +
            "name, " +
            "code," +
            "pcode as parentCode, " +
            "pid as thirdParentId, " +
            "'0' as flag, " +
            "createtime as lastUpdateTimeDate " +
            "from jbase_department  " +
            "union " +
            "select " +
            "id as thirdId, " +
            "name,  " +
            "code," +
            "'' as parentCode, " +
            "'' as thirdParentId," +
            "'1' as flag, " +
            "deletetime as lastUpdateTimeDate " +
            "from jbase_department_delete ) t_dep  " +
            "where " +
            "t_dep.lastUpdateTimeDate > :1 order by lastUpdateTimeDate,thirdId asc ")
    List<ThirdDepartmentBean> queryDepartmentByPage(String lastUpdateTime , Page page);


    @SQL(" SELECT * FROM (SELECT a.id AS thirdId, a.name," +
            " b.col1 AS CODE,  a.IDCARD AS identifier, a.depid, a.policeclass AS police, " +
            " a.position AS POSITION, a.telephone AS officephone, a.mobile, a.sex, a.sort, " +
            " '0' AS flag,a.createtime AS lastUpdateTimeDate,a.modelcode AS personType,b.col2 AS commType," +
            "b.col3 AS tfMobile,b.col2 AS tfCommType,b.col4 AS posTfCommType,b.col5 AS posTfMobile,b.col21 AS usbKeyMobile,b.col22 AS usbKeyCommType" +
            " FROM jbase_user a,jbase_userextattr b" +
            " WHERE a.id=b.userid " +
            " UNION " +
            " SELECT  id AS thirdId, NAME, loginid AS CODE,  IDCARD AS identifier,  depid, policeclass AS police, POSITION AS POSITION," +
            " telephone AS officephone, mobile, sex, sort, '1' AS flag, deletetime AS lastUpdateTimeDate, modelcode AS personType, ''AS commType, " +
            " '' AS tfMobile,'' AS tfCommType,'' AS posTfMobile,'' AS posTfCommType,'' AS usbKeyMobile,'' AS usbKeyCommType " +
            " FROM jbase_user_delete) t_person " +
            " WHERE lastUpdateTimeDate > :1 order by lastUpdateTimeDate,thirdId asc")
    List<ThirdPersonBean> queryPersonByPage(String lastUpdateTime, Page page);

    @SQL("SELECT * FROM (SELECT a.id AS thirdId, a.userid AS personId, a.cardid AS cardNo, " +
            "a.RSASIGNCERTSN AS snSignCertRSA,a.RSAENCCERTSN AS snEncCertRSA, a.RSASIGNCERTENTITY AS certentitySignRSA," +
            "a.RSAENCCERTENTITY AS certentityEncRSA, a.SM2SIGNCERTSN AS snSignCertSM2,  " +
            "a.SM2ENCCERTSN AS snEncCertSM2,a.SM2SIGNCERTENTITY AS certentitySignSM2," +
            "a.SM2ENCCERTENTITY AS certentityEncSM2, a.STATUS AS flag, a.CERTTYPE AS certType,a.updateTime AS lastUpdateTimeDate," +
            "b.devicedrivename AS cardType " +
            "FROM jbase_wireless_certinfo a LEFT JOIN jbase_wireless_deviceinfo b ON a.cardid=b.cardid " +
            "UNION  SELECT id AS thirdId,  userid AS personId, cardid cardNo,  RSASIGNCERTSN AS snSignCertRSA," +
            "RSAENCCERTSN AS snEncCertRSA, RSASIGNCERTENTITY AS certentitySignRSA," +
            "RSAENCCERTENTITY AS certentityEncRSA, SM2SIGNCERTSN AS snSignCertSM2, " +
            "SM2ENCCERTSN AS snEncCertSM2, SM2SIGNCERTENTITY AS certentitySignSM2," +
            "SM2ENCCERTENTITY AS certentityEncSM2, '2' AS flag, CERTTYPE AS certType,deleteTime AS lastUpdateTimeDate, '' AS cardType " +
            "FROM jbase_yd_certinfo_delete) t_device   WHERE lastUpdateTimeDate>:1 order by lastUpdateTimeDate,thirdId asc")
    List<ThirdDeviceBean> queryDeviceByPage(String lastUpdateTime, Page page);
}
