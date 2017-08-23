/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JdbcSqlContext.java
 * @package org.platform.core.context
 * @author leise
 * @date 2016年4月13日 下午2:39:49
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.jdbc.context;

import java.util.Map;

import org.platform.core.jdbc.page.PageParams;

/**
 * @classname: JdbcSqlContext
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class JdbcSqlContext {

    private String sql;

    private Map<String, ?>[] valueMaps;

    private PageParams pageParams;

    public JdbcSqlContext(String sql) {
        this(sql, null);
    }

    public JdbcSqlContext(String sql, Map<String, ?>[] valueMaps) {
        this(sql, valueMaps, null);
    }

    public JdbcSqlContext(String sql, Map<String, ?>[] valueMaps, PageParams pageParams) {
        this.sql = sql;
        this.valueMaps = valueMaps;
        this.pageParams = pageParams;
    }

    /**
     * @return the sql
     */

    public String getSql() {
        return sql;
    }

    /**
     * @param sql the sql to set
     */

    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @return the valueMaps
     */

    public Map<String, ?>[] getValueMaps() {
        return valueMaps;
    }

    /**
     * @param valueMaps the valueMaps to set
     */

    public void setValueMaps(Map<String, ?>[] valueMaps) {
        this.valueMaps = valueMaps;
    }

    /**
     * @return the pageParams
     */

    public PageParams getPageParams() {
        return pageParams;
    }

    /**
     * @param pageParams the pageParams to set
     */

    public void setPageParams(PageParams pageParams) {
        this.pageParams = pageParams;
    }

}
