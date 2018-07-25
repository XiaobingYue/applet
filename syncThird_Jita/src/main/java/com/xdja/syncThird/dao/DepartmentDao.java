package com.xdja.syncThird.dao;

import com.xdja.syncThird.entity.Department;
import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.Result;
import org.jfaster.mango.annotation.Results;
import org.jfaster.mango.annotation.SQL;

import java.util.List;

/**
 * Created by yxb on  2018/7/2
 */
@DB(name = "oracle")
public interface DepartmentDao {

    @Results({
            @Result(column = "GRADE",property = "level"),
            @Result(column = "N_LAST_UPDATE_TIME",property = "timestamp"),
            @Result(column = "DEP_ABB",property = "nameAbbr"),
            @Result(column = "PARENT_ID",property = "parentID"),
            @Result(column = "pCode",property = "parentDep.code"),
            @Result(column = "pId",property = "parentDep.id"),
            @Result(column = "pName",property = "parentDep.name"),
            @Result(column = "pDepAbb",property = "parentDep.nameAbbr"),
            @Result(column = "pGrade",property = "parentDep.level"),
            @Result(column = "pParentId",property = "parentDep.parentID"),
            @Result(column = "pOrderField",property = "parentDep.orderField"),
            @Result(column = "pFlag",property = "parentDep.flag"),
            @Result(column = "pTimestamp",property = "parentDep.timestamp"),
            @Result(column = "pDisplayState",property = "parentDep.displayState")
    })
    @SQL("select t.*,d.ID AS pId,d.CODE AS pCode,d.NAME AS pName,d.DEP_ABB AS pDepAbb,d.GRADE AS pGrade," +
            "d.PARENT_ID AS pParentId,d.ORDER_FIELD AS pOrderField,d.FLAG AS pFlag,d.N_LAST_UPDATE_TIME AS pTimestamp," +
            "d.DISPLAY_STATE AS pDisplayState from T_DEPARTMENT t left join T_DEPARTMENT d on t.PARENT_ID = d.ID where t.code = :1 and t.flag = '0'")
    Department queryByCode(String code);

    @SQL("update T_DEPARTMENT t set t.FLAG = '1', t.N_LAST_UPDATE_TIME = :1.timestamp where t.id in( select id from T_DEPARTMENT start with id = :1.id connect by prior id = parent_id)")
    void deleteDepById(Department department);

    @SQL("insert into T_DEPARTMENT t (ID,CODE,NAME,DEP_ABB,GRADE,PARENT_ID,ORDER_FIELD,FLAG,N_LAST_UPDATE_TIME,DISPLAY_STATE,AMOUNT,DEPTYPE) " +
            "values (:1.id,:1.code,:1.name,:1.nameAbbr,:1.level,:1.parentID,:1.orderField,:1.flag,:1.timestamp,:1.displayState,:1.count,:1.depType)")
    void saveDep(Department department);

    @Results({
            @Result(column = "GRADE",property = "level"),
            @Result(column = "N_LAST_UPDATE_TIME",property = "timestamp"),
            @Result(column = "DEP_ABB",property = "nameAbbr"),
            @Result(column = "PARENT_ID",property = "parentID"),
            @Result(column = "pCode",property = "parentDep.code"),
            @Result(column = "pId",property = "parentDep.id"),
            @Result(column = "pName",property = "parentDep.name"),
            @Result(column = "pDepAbb",property = "parentDep.nameAbbr"),
            @Result(column = "pGrade",property = "parentDep.level"),
            @Result(column = "pParentId",property = "parentDep.parentID"),
            @Result(column = "pOrderField",property = "parentDep.orderField"),
            @Result(column = "pFlag",property = "parentDep.flag"),
            @Result(column = "pTimestamp",property = "parentDep.timestamp"),
            @Result(column = "pDisplayState",property = "parentDep.displayState")
    })
    @SQL("select t.*,d.ID AS pId,d.CODE AS pCode,d.NAME AS pName,d.DEP_ABB AS pDepAbb,d.GRADE AS pGrade," +
            " d.PARENT_ID AS pParentId,d.ORDER_FIELD AS pOrderField,d.FLAG AS pFlag,d.N_LAST_UPDATE_TIME AS pTimestamp," +
            " d.DISPLAY_STATE AS pDisplayState from T_DEPARTMENT t left join T_DEPARTMENT d on t.PARENT_ID = d.ID where t.ID = :1 and t.FLAG = '0'")
    Department queryDepById(String depId);

    @SQL("update T_DEPARTMENT t set t.NAME = :1.name,t.DEP_ABB = :1.nameAbbr,t.PARENT_ID = :1.parentID, " +
            "t.GRADE = :1.level,t.N_LAST_UPDATE_TIME = :1.timestamp,t.FLAG = :1.flag,t.DEPTYPE = :1.depType where t.ID = :1.id")
    void updateDep(Department department);

    @SQL("update T_DEPARTMENT t set t.GRADE = :1.level,t.N_LAST_UPDATE_TIME = :1.timestamp where t.ID = :1.id")
    void updateDepLevel(Department department);

    @Results({
            @Result(column = "GRADE",property = "level"),
            @Result(column = "N_LAST_UPDATE_TIME",property = "timestamp"),
            @Result(column = "DEP_ABB",property = "nameAbbr"),
            @Result(column = "PARENT_ID",property = "parentID")
    })
    @SQL("select * from T_DEPARTMENT t where t.PARENT_ID = :1 and t.FLAG = '0'")
    List<Department> queryChildDepListByParentId(String id);
}
