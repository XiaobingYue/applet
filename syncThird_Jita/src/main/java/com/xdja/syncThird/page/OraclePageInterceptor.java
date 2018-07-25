package com.xdja.syncThird.page;

import com.xdja.syncThird.common.Const;
import org.jfaster.mango.binding.BoundSql;

/**
 * Created by yxb on  2018/7/7
 */
public class OraclePageInterceptor extends AbstractPageInterceptor {
    @Override
    void handleTotal(BoundSql boundSql) {
        String sql = boundSql.getSql();
        sql = "SELECT COUNT(1) FROM (" + sql + ") aliasForPage";
        boundSql.setSql(sql);
    }

    @Override
    void handlePage(int pageNum, int pageSize, BoundSql boundSql) {
        int startRow = (pageNum - 1) * pageSize;
        int endRow = pageNum * pageSize;
        String sql = boundSql.getSql();
        sql = "SELECT * FROM ( SELECT B.* , ROWNUM RN FROM (" + sql + ") B WHERE ROWNUM <= " + endRow + " ) WHERE RN > " + startRow;
        boundSql.setSql(sql);
    }

    @Override
    boolean isInterceptor(String dataSourceType) {
        return Const.DATA_SOURCE_TYPE_ORACLE.equals(dataSourceType);
    }
}
