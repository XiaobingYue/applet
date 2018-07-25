package com.xdja.syncThird.page;

import com.xdja.syncThird.common.Const;
import org.jfaster.mango.binding.BoundSql;

/**
 * SqlServer分页拦截器
 *
 * Created by yxb on  2018/7/7
 */
public class SqlServerPageInterceptor extends AbstractPageInterceptor{

    @Override
    void handleTotal(BoundSql boundSql) {
        String sql = boundSql.getSql();
        if (sql.contains("order by") || sql.contains("ORDER BY")) {
            sql = sql.substring(0,sql.indexOf("order by"));
        }
        sql = "SELECT COUNT(1) FROM (" + sql + ") aliasForPage";
        boundSql.setSql(sql);
    }

    @Override
    void handlePage(int pageNum, int pageSize, BoundSql boundSql) {
        String sql = boundSql.getSql();
        String orderBy = "";
        if (sql.contains("order by") || sql.contains("ORDER BY")) {
            orderBy = sql.substring(sql.indexOf("order by"));
            sql = sql.substring(0,sql.indexOf("order by"));
        }
        int startNum = (pageNum-1)*pageSize;
        int endNum = pageNum*pageSize;
        sql = "select * from (select ROW_NUMBER() OVER( "+ orderBy +" ) AS rowid, a.* from ( "+ sql +" ) a) b where rowid > " + startNum + " and  rowid <="+ endNum;
        boundSql.setSql(sql);
    }

    @Override
    boolean isInterceptor(String dataSourceType) {
        return Const.DATA_SOURCE_TYPE_MSSQL.equals(dataSourceType);
    }
}
