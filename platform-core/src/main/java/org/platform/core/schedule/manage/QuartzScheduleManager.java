/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: QuartzScheduleManage.java
 * @package org.platform.core.schedule.manage
 * @author leise
 * @date 2016年7月4日 下午6:45:54
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.schedule.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.platform.core.constant.Constant;
import org.platform.core.schedule.enums.JobStatus;
import org.platform.core.schedule.enums.ScheduleType;
import org.platform.core.schedule.initializer.QuartzJobInitializer;
import org.platform.core.schedule.job.ScheduledJob;
import org.platform.core.schedule.quartz.CronQuartzJob;
import org.platform.core.schedule.quartz.SimpleQuartzJob;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @classname: QuartzScheduleManage
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Configuration
@Lazy(false)
public class QuartzScheduleManager implements InitializingBean {

    private Map<String, Object> scheduledJobs = new HashMap<String, Object>();

    private Scheduler scheduler;

    @Autowired
    private QuartzJobInitializer quartzJobInitializer;

    @Value("${platform.quartz.enable:false}")
    private boolean quartzEnable;

    @Override
    public void afterPropertiesSet() throws Exception {

        if (!quartzEnable) {
            return;
        }

        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        quartzJobInitializer.init();

        List<ScheduledJob> jobs = Constant.SYS_SCHEDULED_JOB_LIST;

        for (int i = 0; i < jobs.size(); i++) {
            ScheduledJob job = (ScheduledJob) jobs.get(i);

            if (ScheduleType.CRON == job.getScheduleType()) {
                CronQuartzJob cronQuartzJob = new CronQuartzJob(job);
                cronQuartzJob.setScheduler(this.scheduler);
                cronQuartzJob.start();
                this.scheduledJobs.put(job.getJobId(), cronQuartzJob);
            } else {
                SimpleQuartzJob simpleQuartzJob = new SimpleQuartzJob(job);
                simpleQuartzJob.setScheduler(this.scheduler);
                simpleQuartzJob.start();
                this.scheduledJobs.put(job.getJobId(), simpleQuartzJob);
            }

            job.setJobStatus(JobStatus.STARTED);
        }
    }

    /**
     * @return the scheduler
     */

    public Scheduler getScheduler() {
        return scheduler;
    }

}
