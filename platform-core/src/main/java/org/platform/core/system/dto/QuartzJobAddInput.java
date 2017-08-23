/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: QuartzJobAddInput.java
 * @package org.platform.core.system.dto
 * @author leise
 * @date 2016年7月7日 下午8:40:29
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.dto;

import java.util.Date;

import org.platform.core.base.dto.InputHeader;

/**
 * @classname: QuartzJobAddInput
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class QuartzJobAddInput extends InputHeader {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 5620379041168673767L;

    /** 任务组编号 */
    private String groupId;

    /** 任务组名称 */
    private String groupName;

    /** 任务编号 */
    private String jobId;

    /** 任务名称 */
    private String jobName;

    /**
     * 任务模式 auto:自动模式 man:人工模式
     */
    private String jobMode;

    /**
     * 执行模式 0:串行执行 1:并行执行
     */
    private String executeMode;

    /**
     * 任务计划类型 0：简单任务 1：复杂任务
     */
    private String scheduleType;

    /** 重复执行次数 */
    private String repeatCount;

    /** 延迟执行时间(单位/秒) */
    private Long delay;

    /** 重复执行间隔时间(单位/秒) */
    private Long repeatInterval;

    /** 计划开始时间 */
    private Date schedBeginTime;

    /** 计划结束时间 */
    private Date schedEndTime;

    /** 复杂计划执行表达式 */
    private String conExpression;

    /** 节假日历编号(任务不执行) */
    private String holidaysId;

    /**
     * @return the groupId
     */

    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the groupName
     */

    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the jobId
     */

    public String getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * @return the jobName
     */

    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName the jobName to set
     */

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return the jobMode
     */

    public String getJobMode() {
        return jobMode;
    }

    /**
     * @param jobMode the jobMode to set
     */

    public void setJobMode(String jobMode) {
        this.jobMode = jobMode;
    }

    /**
     * @return the executeMode
     */

    public String getExecuteMode() {
        return executeMode;
    }

    /**
     * @param executeMode the executeMode to set
     */

    public void setExecuteMode(String executeMode) {
        this.executeMode = executeMode;
    }

    /**
     * @return the scheduleType
     */

    public String getScheduleType() {
        return scheduleType;
    }

    /**
     * @param scheduleType the scheduleType to set
     */

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
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

    /**
     * @return the conExpression
     */

    public String getConExpression() {
        return conExpression;
    }

    /**
     * @param conExpression the conExpression to set
     */

    public void setConExpression(String conExpression) {
        this.conExpression = conExpression;
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

}
