/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ScheduleSimpleJob.java
 * @package org.platform.core.schedule
 * @author leise
 * @date 2016年7月4日 下午2:00:26
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.schedule.job;

import java.util.List;

import org.platform.core.base.service.ScheduledService;
import org.platform.core.schedule.CronSchedule;
import org.platform.core.schedule.enums.ExecuteMode;
import org.platform.core.schedule.enums.JobMode;
import org.platform.core.schedule.enums.JobStatus;
import org.platform.core.schedule.enums.ScheduleType;

/**
 * @classname: ScheduleSimpleJob
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class CronJob implements ScheduledJob {

    private String groupId;

    private String groupName;

    private String jobId;

    private String jobName;

    private JobMode jobMode;

    private ExecuteMode executeMode;

    private JobStatus jobStatus;

    private CronSchedule cronSchedule;

    private String holidaysId;

    private List<ScheduledService> services;

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
     * @return the jobStatus
     */

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    /**
     * @param jobStatus the jobStatus to set
     */

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * @return the cronSchedule
     */

    public CronSchedule getCronSchedule() {
        return cronSchedule;
    }

    /**
     * @param cronSchedule the cronSchedule to set
     */

    public void setCronSchedule(CronSchedule cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    /**
     * @return the services
     */

    public List<ScheduledService> getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */

    public void setServices(List<ScheduledService> services) {
        this.services = services;
    }

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

    public ScheduleType getScheduleType() {
        // TODO Auto-generated method stub
        return ScheduleType.CRON;
    }

    /**
     * @return the jobMode
     */

    public JobMode getJobMode() {
        return jobMode;
    }

    /**
     * @param jobMode the jobMode to set
     */

    public void setJobMode(JobMode jobMode) {
        this.jobMode = jobMode;
    }

    /**
     * @return the executeMode
     */

    public ExecuteMode getExecuteMode() {
        return executeMode;
    }

    /**
     * @param executeMode the executeMode to set
     */

    public void setExecuteMode(ExecuteMode executeMode) {
        this.executeMode = executeMode;
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
