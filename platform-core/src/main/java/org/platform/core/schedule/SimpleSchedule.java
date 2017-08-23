/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: SimpleTimerSchedule.java
 * @package org.platform.core.schedule
 * @author leise
 * @date 2016年7月4日 下午2:05:06
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.schedule;

import java.util.Date;
import java.util.List;

/**
 * @classname: SimpleTimerSchedule
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class SimpleSchedule {

    private Date schedBeginTime;

    private Date schedEndTime;

    private Long delay = 0L;

    private Long repeatInterval = 0L;

    private String repeatCount;

    private List<Date> holidays;

    /**
     * @return the delay
     */

    public Long getDelay() {
        return delay;
    }

    /**
     * @param delay the delay to set
     */

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    /**
     * @return the repeatInterval
     */

    public Long getRepeatInterval() {
        return repeatInterval;
    }

    /**
     * @param repeatInterval the repeatInterval to set
     */

    public void setRepeatInterval(Long repeatInterval) {
        this.repeatInterval = repeatInterval;
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

    /**
     * @return the repeatCount
     */

    public String getRepeatCount() {
        return repeatCount;
    }

    /**
     * @param repeatCount the repeatCount to set
     */

    public void setRepeatCount(String repeatCount) {
        this.repeatCount = repeatCount;
    }

    /**
     * @return the schedBeginTime
     */

    public Date getSchedBeginTime() {
        return schedBeginTime;
    }

    /**
     * @param schedBeginTime the schedBeginTime to set
     */

    public void setSchedBeginTime(Date schedBeginTime) {
        this.schedBeginTime = schedBeginTime;
    }

    /**
     * @return the schedEndTime
     */

    public Date getSchedEndTime() {
        return schedEndTime;
    }

    /**
     * @param schedEndTime the schedEndTime to set
     */

    public void setSchedEndTime(Date schedEndTime) {
        this.schedEndTime = schedEndTime;
    }

}
