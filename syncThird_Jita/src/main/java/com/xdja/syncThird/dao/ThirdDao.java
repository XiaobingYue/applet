package com.xdja.syncThird.dao;

import com.xdja.syncThird.entity.ThirdDepartmentEntity;
import com.xdja.syncThird.entity.ThirdDeviceEntity;
import com.xdja.syncThird.entity.ThirdPersonEntity;
import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.Result;
import org.jfaster.mango.annotation.Results;
import org.jfaster.mango.annotation.SQL;
import org.jfaster.mango.plugin.page.Page;

import java.util.List;

/**
 * Created by yxb on  2018/7/3
 */
@DB(name = "oracle")
public interface ThirdDao {

    @Results({
            @Result(column = "LAST_UPDATE_TIME",property = "timestamp")
    })
    @SQL("select * from T_THIRD_DEPARTMENT t where t.THIRD_ID = :1")
    ThirdDepartmentEntity queryDepByThirdId(String thirdId);

    @SQL("insert into T_THIRD_DEPARTMENT t " +
            "(ID,DEP_ID,THIRD_ID,THIRD_PARENT_ID,NAME,SHORT_NAME,CODE,PARENT_CODE,LAST_UPDATE_TIME,ERR_INFO,flag) " +
            "values" +
            " (:1.id,:1.depId,:1.thirdId,:1.thirdParentId,:1.name,:1.shortName,:1.code,:1.parentCode,:1.timestamp,:1.errInfo,:1.flag)")
    void saveThirdDepartment(ThirdDepartmentEntity thirdDepartmentEntity);

    @SQL(" update T_THIRD_DEPARTMENT t " +
            " set " +
            " t.CODE = :1.code," +
            " t.THIRD_ID = :1.thirdId, " +
            " t.NAME = :1.name," +
            " t.SHORT_NAME = :1.shortName," +
            " t.PARENT_CODE = :1.parentCode," +
            " t.THIRD_PARENT_ID = :1.thirdParentId," +
            " t.DEP_ID = :1.depId," +
            " t.ERR_INFO = :1.errInfo," +
            " t.FLAG = :1.flag," +
            " t.LAST_UPDATE_TIME = :1.timestamp " +
            " where t.ID = :1.id")
    void updateThirdDepartment(ThirdDepartmentEntity thirdDepartmentEntity);

    @Results({
            @Result(column = "LAST_UPDATE_TIME",property = "timestamp")
    })
    @SQL("select * from T_THIRD_PERSON t where t.THIRD_ID = :1")
    ThirdPersonEntity queryPersonByThirdId(String thirdId);

    @SQL("insert into " +
            "T_THIRD_PERSON " +
            " (ID," +
            "PERSON_ID," +
            "THIRD_ID," +
            "IDENTIFIER," +
            "NAME," +
            "CODE," +
            "MOBILE," +
            "POLICE," +
            "POSITION," +
            "OFFICE_PHONE," +
            "SEX," +
            "SORT," +
            "DEP_ID," +
            "ERR_INFO," +
            "LAST_UPDATE_TIME," +
            "FLAG," +
            "COMM_TYPE," +
            "PERSON_TYPE, " +
            "TF_MOBILE, " +
            "TF_COMM_TYPE, " +
            "POS_TF_MOBILE, " +
            "POS_TF_COMM_TYPE, " +
            "USB_KEY_MOBILE, " +
            "USB_KEY_COMM_TYPE  ) " +
            "values (:1.id," +
            ":1.personId," +
            ":1.thirdId," +
            ":1.identifier," +
            ":1.name," +
            ":1.code," +
            ":1.mobile," +
            ":1.police," +
            ":1.position," +
            ":1.officePhone," +
            ":1.sex," +
            ":1.sort," +
            ":1.depId," +
            ":1.errInfo," +
            ":1.timestamp," +
            ":1.flag," +
            ":1.commType," +
            ":1.personType, " +
            ":1.tfMobile, " +
            ":1.tfCommType, " +
            ":1.posTfMobile, " +
            ":1.posTfCommType, " +
            ":1.usbKeyMobile, " +
            ":1.usbKeyCommType  )")
    void saveThirdPerson(ThirdPersonEntity personEntity);

    @SQL("update T_THIRD_PERSON t set " +
            " t.PERSON_ID = :1.personId," +
            " t.IDENTIFIER = :1.identifier," +
            " t.NAME = :1.name," +
            " t.CODE = :1.code," +
            " t.MOBILE = :1.mobile," +
            " t.POLICE = :1.police," +
            " t.POSITION = :1.position," +
            " t.OFFICE_PHONE = :1.officePhone," +
            " t.SEX = :1.sex," +
            " t.SORT = :1.sort," +
            " t.DEP_ID = :1.depId," +
            " t.ERR_INFO = :1.errInfo," +
            " t.LAST_UPDATE_TIME = :1.timestamp," +
            " t.FLAG = :1.flag," +
            " t.COMM_TYPE = :1.commType," +
            " t.PERSON_TYPE = :1.personType, " +
            " t.TF_MOBILE = :1.tfMobile, " +
            " t.TF_COMM_TYPE = :1.tfCommType, " +
            " t.POS_TF_MOBILE = :1.posTfMobile, " +
            " t.POS_TF_COMM_TYPE = :1.posTfCommType, " +
            " t.USB_KEY_MOBILE = :1.usbKeyMobile,  " +
            " t.USB_KEY_COMM_TYPE = :1.usbKeyCommType" +
            " where t.ID = :1.id")
    void updateThirdPerson(ThirdPersonEntity personEntity);

    @Results({
            @Result(column = "LAST_UPDATE_TIME",property = "timestamp"),
            @Result(column = "SN_SIGN_CERT_RSA",property = "snSignCertRSA"),
            @Result(column = "SN_ENC_CERT_RSA",property = "snEncCertRSA"),
            @Result(column = "CERTENTITY_SIGN_RSA",property = "certentitySignRSA"),
            @Result(column = "CERTENTITY_ENC_RSA",property = "certentityEncRSA"),
            @Result(column = "SN_SIGN_CERT_SM2",property = "snSignCertSM2"),
            @Result(column = "SN_ENC_CERT_SM2",property = "snEncCertSM2"),
            @Result(column = "CERTENTITY_SIGN_SM2",property = "certentitySignSM2"),
            @Result(column = "CERTENTITY_ENC_SM2",property = "certentityEncSM2"),
            @Result(column = "DEVICE_ORG",property = "deviceIdOrg"),
    })
    @SQL("select * from T_THIRD_DEVICE t where t.THIRD_ID = :1")
    ThirdDeviceEntity queryDeviceByThirdId(String thirdId);

    @SQL("insert into T_THIRD_DEVICE t " +
            "(ID," +
            "DEVICE_ID," +
            "THIRD_ID," +
            "PERSON_ID," +
            "LAST_UPDATE_TIME," +
            "CARD_NO," +
            "SN_SIGN_CERT_RSA," +
            "SN_ENC_CERT_RSA," +
            "CERTENTITY_SIGN_RSA," +
            "CERTENTITY_ENC_RSA," +
            "SN_SIGN_CERT_SM2," +
            "SN_ENC_CERT_SM2," +
            "CERTENTITY_SIGN_SM2," +
            "CERTENTITY_ENC_SM2," +
            "FLAG," +
            "CERT_TYPE," +
            "ERR_INFO," +
            "CARD_TYPE," +
            "DEVICE_ORG) values (" +
            " :1.id," +
            " :1.deviceId," +
            " :1.thirdId," +
            " :1.personId," +
            " :1.timestamp," +
            " :1.cardNo," +
            " :1.snSignCertRSA," +
            " :1.snEncCertRSA," +
            " :1.certentitySignRSA," +
            " :1.certentityEncRSA," +
            " :1.snSignCertSM2," +
            " :1.snEncCertSM2," +
            " :1.certentitySignSM2," +
            " :1.certentityEncSM2," +
            " :1.flag," +
            " :1.certType," +
            " :1.errInfo," +
            " :1.cardType," +
            " :1.deviceIdOrg)")
    void saveThirdDevice(ThirdDeviceEntity deviceEntity);

    @SQL("update T_THIRD_DEVICE t set " +
            " t.DEVICE_ID = :1.deviceId, " +
            " t.DEVICE_ORG = :1.deviceIdOrg, " +
            " t.PERSON_ID = :1.personId, " +
            " t.LAST_UPDATE_TIME = :1.timestamp, " +
            " t.CARD_NO = :1.cardNo, " +
            " t.SN_SIGN_CERT_RSA = :1.snSignCertRSA, " +
            " t.SN_ENC_CERT_RSA = :1.snEncCertRSA, " +
            " t.CERTENTITY_SIGN_RSA = :1.certentitySignRSA, " +
            " t.CERTENTITY_ENC_RSA = :1.certentityEncRSA, " +
            " t.SN_SIGN_CERT_SM2 = :1.snSignCertSM2, " +
            " t.SN_ENC_CERT_SM2 = :1.snEncCertSM2, " +
            " t.CERTENTITY_SIGN_SM2 = :1.certentitySignSM2, " +
            " t.CERTENTITY_ENC_SM2 = :1.certentityEncSM2, " +
            " t.FLAG = :1.flag, " +
            " t.CERT_TYPE = :1.certType, " +
            " t.ERR_INFO = :1.errInfo, " +
            " t.CARD_TYPE = :1.cardType " +
            " where t.ID = :1.id ")
    void updateThirdDevice(ThirdDeviceEntity deviceEntity);

    @Results({
            @Result(column = "LAST_UPDATE_TIME",property = "timestamp")
    })
    @SQL("select * from T_THIRD_DEPARTMENT t where t.DEP_ID is null order by t.LAST_UPDATE_TIME asc")
    List<ThirdDepartmentEntity> queryUnRelatedDep(Page page);

    @Results({
            @Result(column = "LAST_UPDATE_TIME",property = "timestamp")
    })
    @SQL("select * from T_THIRD_DEPARTMENT t where t.THIRD_ID = :1 and t.FLAG = '0'")
    ThirdDepartmentEntity getThirdDepByThirdId(String thirdParentId);

    @Results({
            @Result(column = "LAST_UPDATE_TIME",property = "timestamp")
    })
    @SQL("select * from T_THIRD_DEPARTMENT t where t.CODE = :1 and t.FLAG = '0'")
    ThirdDepartmentEntity getThirdDepByThirdCode(String parentCode);

    @Results({
            @Result(column = "LAST_UPDATE_TIME",property = "timestamp")
    })
    @SQL("select * from T_THIRD_PERSON t where t.PERSON_ID is null order by t.LAST_UPDATE_TIME asc")
    List<ThirdPersonEntity> queryUnRelatedPerson(Page page);

    @Results({
            @Result(column = "LAST_UPDATE_TIME",property = "timestamp"),
            @Result(column = "SN_SIGN_CERT_RSA",property = "snSignCertRSA"),
            @Result(column = "SN_ENC_CERT_RSA",property = "snEncCertRSA"),
            @Result(column = "CERTENTITY_SIGN_RSA",property = "certentitySignRSA"),
            @Result(column = "CERTENTITY_ENC_RSA",property = "certentityEncRSA"),
            @Result(column = "SN_SIGN_CERT_SM2",property = "snSignCertSM2"),
            @Result(column = "SN_ENC_CERT_SM2",property = "snEncCertSM2"),
            @Result(column = "CERTENTITY_SIGN_SM2",property = "certentitySignSM2"),
            @Result(column = "CERTENTITY_ENC_SM2",property = "certentityEncSM2"),
            @Result(column = "DEVICE_ORG",property = "deviceIdOrg"),
    })
    @SQL("select * from T_THIRD_DEVICE t where t.DEVICE_ID is null order by t.LAST_UPDATE_TIME asc")
    List<ThirdDeviceEntity> queryUnRelatedDevice(Page page);
}
