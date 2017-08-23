/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: CronTimerSchedule.java
 * @package org.platform.core.schedule
 * @author leise
 * @date 2016年7月4日 下午2:07:43
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.schedule;

import java.util.Date;
import java.util.List;

/**
 * @classname: CronTimerSchedule
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class CronSchedule {

    private String cronExpression;
    
    private List<Date> holidays;

    /**
     * @return the cronExpression
     */

    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * @param cronExpression the cronExpression to set
     */

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    
    	/**
     	 * @return the holidays
    	 */
    
    public List<Date> getHolidays() {
        return holidays;
    }

    
    	/**
     	 * @param holidays the holidays to set
    	 */
    
    public void setHolidays(List<Date> holidays) {
        this.holidays = holidays;
    }

}
