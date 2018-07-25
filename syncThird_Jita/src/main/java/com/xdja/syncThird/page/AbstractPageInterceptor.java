package com.xdja.syncThird.page;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.xdja.syncThird.common.Const;
import org.jfaster.mango.binding.BoundSql;
import org.jfaster.mango.interceptor.Parameter;
import org.jfaster.mango.interceptor.QueryInterceptor;
import org.jfaster.mango.mapper.SingleColumnRowMapper;
import org.jfaster.mango.plugin.page.Page;
import org.jfaster.mango.plugin.page.PageException;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yxb on  2018/7/7
 */
public abstract class AbstractPageInterceptor extends QueryInterceptor {

    private String dataSourceType = "";

    public void interceptQuery(BoundSql boundSql, List<Parameter> parameters, DataSource dataSource) {
        Iterator it = parameters.iterator();
        while(it.hasNext()) {
            Parameter parameter = (Parameter)it.next();
            Object object = parameter.getValue();
            if (object instanceof Page) {
                Page page = (Page)object;
                int pageNum = page.getPageNum();
                int pageSize = page.getPageSize();
                if (pageNum <= 0) {
                    throw new PageException("pageNum need > 0, but pageNum is " + pageNum);
                }
                if (pageSize <= 0) {
                    throw new PageException("pageSize need > 0, but pageSize is " + pageSize);
                }
                if (dataSource instanceof DruidAbstractDataSource) {
                    DruidAbstractDataSource druidAbstractDataSource = (DruidAbstractDataSource) dataSource;
                    if (druidAbstractDataSource.getDriverClassName().contains("mysql")) {
                        dataSourceType = Const.DATA_SOURCE_TYPE_MYSQL;
                    } else if (druidAbstractDataSource.getDriverClassName().contains("oracle")) {
                        dataSourceType = Const.DATA_SOURCE_TYPE_ORACLE;
                    } else if (druidAbstractDataSource.getDriverClassName().contains("sqlserver")) {
                        dataSourceType = Const.DATA_SOURCE_TYPE_MSSQL;
                    }
                }

                if (page.isFetchTotal()) {
                    BoundSql totalBoundSql = boundSql.copy();
                    if(isInterceptor(dataSourceType)){
                        this.handleTotal(totalBoundSql);
                        SingleColumnRowMapper<Integer> mapper = new SingleColumnRowMapper(Integer.TYPE);
                        int total = ((Integer)this.getJdbcOperations().queryForObject(dataSource, totalBoundSql, mapper)).intValue();
                        page.setTotal(total);
                    }
                }
                if(isInterceptor(dataSourceType)){
                    this.handlePage(pageNum, pageSize, boundSql);
                }
            }
        }

    }

    abstract void handleTotal(BoundSql boundSql);

    abstract void handlePage(int pageNum, int pageSize, BoundSql boundSql);

    abstract boolean isInterceptor(String dataSourceType);

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
    public String getDataSourceType() {
        return dataSourceType;
    }
}
