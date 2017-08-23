/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: PageParams.java
 * @package org.platform.core.jdbc.page
 * @author leise
 * @date 2016年5月26日 下午6:59:52
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.jdbc.page;

import java.io.Serializable;
import java.util.Map;

/**
 * @classname: PageParams
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class PageParams implements Serializable {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = 7364641562771551847L;

    public static final int DEFAULT_PAGE_SIZE = 50;

    private int pageSize;

    private int currentPage;

    private int totalPage;

    private int totalCount;

    private Map<String, String> sort;

    public PageParams() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public PageParams(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {

        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {

        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the sort
     */

    public Map<String, String> getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */

    public void setSort(Map<String, String> sort) {
        this.sort = sort;
    }

    public String toString() {
        StringBuilder page = new StringBuilder();
        page.append("PageParams [pageSize=");
        page.append(this.pageSize);
        page.append(", currentPage=");
        page.append(this.currentPage);
        page.append(", totalPage=");
        page.append(this.totalPage);
        page.append(", totalCount=");
        page.append(this.totalCount);
        page.append("]");
        return page.toString();
    }

}
