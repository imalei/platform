/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ScheduleJob.java
 * @package org.platform.core.schedule
 * @author leise
 * @date 2016年7月4日 下午2:32:30
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.schedule.job;

import java.util.List;

import org.platform.core.base.service.ScheduledService;
import org.platform.core.schedule.enums.ExecuteMode;
import org.platform.core.schedule.enums.JobMode;
import org.platform.core.schedule.enums.JobStatus;
import org.platform.core.schedule.enums.ScheduleType;

/**
 * @classname: ScheduleJob
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public interface ScheduledJob {

    public String getGroupId();

    public String getGroupName();

    public String getJobId();

    public String getJobName();

    public List<ScheduledService> getServices();

    public JobMode getJobMode();

    public ExecuteMode getExecuteMode();

    public ScheduleType getScheduleType();
    
    public String getHolidaysId();

    public JobStatus getJobStatus();

    public void setJobStatus(JobStatus jobStatus);

}
