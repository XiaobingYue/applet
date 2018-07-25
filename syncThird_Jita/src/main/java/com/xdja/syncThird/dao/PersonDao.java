package com.xdja.syncThird.dao;

import com.xdja.syncThird.entity.Mobile;
import com.xdja.syncThird.entity.Person;
import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.Result;
import org.jfaster.mango.annotation.Results;
import org.jfaster.mango.annotation.SQL;

import java.util.List;

/**
 * Created by yxb on  2018/7/2
 */
@DB(name = "oracle")
public interface PersonDao {

    @Results({
            @Result(column = "ERROR_TIMES",property = "loginErrorTimes"),
            @Result(column = "CLIENT_LOCK_STATE",property = "lockState"),
            @Result(column = "STATE",property = "personState"),
            @Result(column = "N_LAST_UPDATE_TIME",property = "timestamp")
    })
    @SQL("select * from T_PERSON t where t.IDENTIFIER = :1 and t.FLAG = '0'")
    Person queryPersonByIdentifier(String identifier);

    @SQL("update T_PERSON t set t.FLAG = :1.flag, t.N_LAST_UPDATE_TIME = :1.timestamp,t.MOBILE = :1.mobile where t.ID = :1.id")
    void deletePersonById(Person person);

    @SQL("insert into T_PERSON (ID,CODE,CREATE_DATE,CREATOR_ID,DEP_ID,FLAG,GRADE,IDENTIFIER,LEADER_LEVEL," +
            "ERROR_TIMES,MOBILE,NAME,OFFICE_PHONE,ORDER_FIELD,PASSWORD,CLIENT_LOCK_STATE,STATE,POLICE,POSITION,SEX," +
            "N_LAST_UPDATE_TIME,DEP_CODE,NAME_BRIEF_SPELL,PERSON_TYPE,COMM_TYPE,DISPLAY_STATE) " +
            "values (" +
            ":1.id,:1.code,:1.createDate,:1.creatorId,:1.depId,:1.flag,:1.grade,:1.identifier,:1.leaderLevel,:1.loginErrorTimes,:1.mobile,:1.name," +
            ":1.officePhone,:1.orderField,:1.password,:1.lockState,:1.personState,:1.police,:1.position,:1.sex,:1.timestamp,:1.depCode,:1.nameBriefSpell," +
            ":1.personType,:1.commType,:1.displayState)")
    void savePerson(Person person);

    @SQL("insert into T_BIMS_MOBILE t (t.ID,t.MOBILE,t.PERSON_ID)values(:1.id,:1.mobile,:1.personId)")
    void saveMobileBatch(List<Mobile> mobileList);

    @SQL("update T_PERSON set" +
            " IDENTIFIER = :1.identifier, " +
            " CODE = :1.code, " +
            " OFFICE_PHONE = :1.officePhone, " +
            " NAME = :1.name, " +
            " DEP_ID = :1.depId, " +
            " DEP_CODE = :1.depCode, " +
            " PERSON_TYPE = :1.personType, " +
            " COMM_TYPE = :1.commType, " +
            " POSITION = :1.position, " +
            " POLICE = :1.police, " +
            " SEX = :1.sex, " +
            " MOBILE = :1.mobile, " +
            " NAME_BRIEF_SPELL = :1.nameBriefSpell, " +
            " N_LAST_UPDATE_TIME = :1.timestamp," +
            " FLAG = :1.flag  " +
            " where ID = :1.id ")
    void updatePerson(Person person);

    @SQL("select * from T_BIMS_MOBILE t where t.PERSON_ID = :1")
    List<Mobile> queryPersonMobile(String id);

    @SQL("select t.VALUE from T_SYSTEM_CONFIG t where t.CODE = :1")
    String getValueByCode(String defaultPass);

    @Results({
            @Result(column = "ERROR_TIMES",property = "loginErrorTimes"),
            @Result(column = "CLIENT_LOCK_STATE",property = "lockState"),
            @Result(column = "STATE",property = "personState"),
            @Result(column = "N_LAST_UPDATE_TIME",property = "timestamp")
    })
    @SQL("select * from T_PERSON t where t.ID = :1")
    Person queryPersonById(String personId);

    @SQL("select * from T_BIMS_MOBILE t where t.MOBILE = :1")
    Mobile queryByMobile(String mobile);

    @SQL("delete from T_BIMS_MOBILE t where t.person_id is null")
    void deleteNoPersonMobile();

    /**
     *更新需要更新的手机号的person_id为null
     *
     * @param needUpdateMobileIdList 需要更新的手机号id
     */
    @SQL("update T_BIMS_MOBILE t set t.person_id = null where t.person_id = :2 and t.mobile in( :1) ")
    void updateNeedUpdateMobile(List<String> needUpdateMobileIdList,String personId);

    @SQL("update T_BIMS_MOBILE t set t.person_id = null where t.person_id = :1")
    void updateMobilePersonIdNull(String id);

    @SQL("delete from T_BIMS_GROUP_MEMBER t where t.person_id = :1")
    void deletePersonGroup(String id);

    @SQL("delete from T_UPMS_PERSON_ROLE t where t.person_id = :1")
    void deletePersonRole(String id);

    @SQL("delete from T_BIMS_PERSON_CONTROLDEP t where t.person_id = :1")
    void deletePersonControlDep(String id);
    @SQL("delete from T_BIMS_PERSON_CONTROLPOLICE t where t.person_id = :1")
    void deletePersonControlPolice(String id);

    @SQL("update T_BIMS_MOBILE t set t.mobile = :1.mobile where t.id = :1.id")
    void updateMobile(Mobile mobile);

    @SQL("insert into T_BIMS_MOBILE t (t.ID,t.MOBILE,t.PERSON_ID) values(:1.id,:1.mobile,:1.personId)")
    void saveMobile(Mobile mobile);
}
