/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: TQuartzRecords.java
 * @package org.platform.core.system.entity
 * @author leise
 * @date 2016年7月6日 下午7:44:44
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.platform.core.annotation.entity.PK;
import org.platform.core.annotation.entity.Table;
import org.platform.core.base.entity.BaseEntity;

/**
 * @classname: TQuartzRecords
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Table("T_QUARTZ_RECORDS")
public class TQuartzRecords extends BaseEntity {

    /**
     * @fields serialVersionUID : 串行号
     */

    private static final long serialVersionUID = 7857301644388127309L;

    /** 记录编号 */
    @PK
    private String recordId;

    /** 任务编号 */
    private String jobId;

    /** 任务名称 */
    private String jobName;

    /** 任务模式 */
    private String jobMode;

    /** 任务计划编号 */
    private String scheduleId;

    /** 服务编号 */
    private String serviceId;

    /** 服务版本号 */
    private String serviceVersion;

    /** 服务类 */
    private String serviceClass;

    /** 执行模式 */
    private String executeMode;

    /** 执行顺序 */
    private String executeOrder;

    /** 执行状态 */
    private String executeStatus;

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

    /** 复杂计划执行表达式 */
    private String conExpression;

    /** 计划开始时间 */
    private Date schedBeginTime;

    /** 计划结束时间 */
    private Date schedEndTime;

    /** 节假日 */
    private String holidays;

    /** 执行开始时间 */
    private Timestamp exeBeginTime;

    /** 执行结束时间 */
    private Timestamp exeEndTime;

    /**
     * @return the recordId
     */

    public String getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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
     * @return the scheduleId
     */

    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * @param scheduleId the scheduleId to set
     */

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * @return the serviceId
     */

    public String getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the serviceVersion
     */

    public String getServiceVersion() {
        return serviceVersion;
    }

    /**
     * @param serviceVersion the serviceVersion to set
     */

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    /**
     * @return the serviceClass
     */

    public String getServiceClass() {
        return serviceClass;
    }

    /**
     * @param serviceClass the serviceClass to set
     */

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
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
     * @return the executeOrder
     */

    public String getExecuteOrder() {
        return executeOrder;
    }

    /**
     * @param executeOrder the executeOrder to set
     */

    public void setExecuteOrder(String executeOrder) {
        this.executeOrder = executeOrder;
    }

    /**
     * @return the executeStatus
     */

    public String getExecuteStatus() {
        return executeStatus;
    }

    /**
     * @param executeStatus the executeStatus to set
     */

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
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
     * @return the holidays
     */

    public String getHolidays() {
        return holidays;
    }

    /**
     * @param holidays the holidays to set
     */

    public void setHolidays(String holidays) {
        this.holidays = holidays;
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
     	 * @return the exeBeginTime
    	 */
    
    public Timestamp getExeBeginTime() {
        return exeBeginTime;
    }

    
    	/**
     	 * @param exeBeginTime the exeBeginTime to set
    	 */
    
    public void setExeBeginTime(Timestamp exeBeginTime) {
        this.exeBeginTime = exeBeginTime;
    }

        
        	/**
         	 * @return the exeEndTime
        	 */
        
        public Timestamp getExeEndTime() {
            return exeEndTime;
        }

        
        	/**
         	 * @param exeEndTime the exeEndTime to set
        	 */
        
        public void setExeEndTime(Timestamp exeEndTime) {
            this.exeEndTime = exeEndTime;
        }

}
