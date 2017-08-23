/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: TSysHolidays.java
 * @package org.platform.core.system.entity
 * @author leise
 * @date 2016年7月6日 下午8:08:05
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.entity;

import java.util.Date;

import org.platform.core.annotation.entity.PK;
import org.platform.core.annotation.entity.Table;
import org.platform.core.base.entity.BaseEntity;

/**
 * @classname: TSysHolidays
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Table("T_SYS_HOLIDAYS")
public class TSysHolidays extends BaseEntity {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = 5949323873103121378L;

    /** 序号 */
    @PK
    private String id;

    /** 节假日历编号(任务不执行) */
    private String holidaysId;

    /** 节假日历名称 */
    private String holidaysName;

    /** 节假日日期 */
    private Date holidayDate;

    /**
     * @return the id
     */

    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the holidaysId
     */

    public String getHolidaysId() {
        return holidaysId;
    }

    /**
     * @param holidaysId the holidaysId to set
     */

    public void setHolidaysId(String holidaysId) {
        this.holidaysId = holidaysId;
    }

    /**
     * @return the holidaysName
     */

    public String getHolidaysName() {
        return holidaysName;
    }

    /**
     * @param holidaysName the holidaysName to set
     */

    public void setHolidaysName(String holidaysName) {
        this.holidaysName = holidaysName;
    }

    /**
     * @return the holidayDate
     */

    public Date getHolidayDate() {
        return holidayDate;
    }

    /**
     * @param holidayDate the holidayDate to set
     */

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

}
