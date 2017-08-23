/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ScheduledService.java
 * @package org.platform.core.schedule.service
 * @author leise
 * @date 2016年6月30日 下午6:47:32
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.schedule.quartz;

import java.io.Serializable;
import java.util.List;

import org.platform.core.base.service.ScheduledService;
import org.platform.core.schedule.enums.JobStatus;
import org.platform.core.schedule.job.ScheduledJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @classname: ScheduledService
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class QuartzScheduledJob implements Job, Serializable {

    /**
     * @fields serialVersionUID : 串行号
     */

    private static final long serialVersionUID = -6848947468418192437L;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        ScheduledJob job = (ScheduledJob) context.getMergedJobDataMap().get("job_obj");
        job.setJobStatus(JobStatus.RUNNING);

        try {
            List<ScheduledService> services = job.getServices();
            for (ScheduledService service : services) {
                service.doService();
            }
        }
        catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
