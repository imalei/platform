/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: CronTimerQuartz.java
 * @package org.platform.core.schedule.quartz
 * @author leise
 * @date 2016年7月4日 下午6:14:00
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.schedule.quartz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.platform.core.schedule.enums.ScheduleType;
import org.platform.core.schedule.job.CronJob;
import org.platform.core.schedule.job.ScheduledJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * @classname: CronTimerQuartz
 * @description: TODO(这里用一句话描述这个类的作用)
 */
public class CronQuartzJob {

    private Scheduler scheduler;

    private ScheduledJob job;

    public CronQuartzJob(ScheduledJob job) {
        this.job = job;
    }

    public void start() {

        String groupId = this.job.getGroupId();

        if (StringUtils.isBlank(groupId)) {
            groupId = "DEFAULT";
        }

        String jobId = this.job.getJobId();
        JobDetail jobDetail = JobBuilder.newJob(QuartzScheduledJob.class).withIdentity(jobId, groupId).build();
        jobDetail.getJobDataMap().put("job_obj", this.job);

        CronJob cronJob = null;
        if (ScheduleType.CRON == job.getScheduleType()) {
            cronJob = (CronJob) this.job;
        } else {
            return;
        }

        String cronExpression = cronJob.getCronSchedule().getCronExpression();
        try {

            CronTriggerImpl trigger = new CronTriggerImpl();
            trigger.setCronExpression(cronExpression);

            TriggerKey triggerKey = new TriggerKey(jobId + "_trigger", "DEFAULT");
            trigger.setJobName(jobDetail.getKey().getName());
            trigger.setKey(triggerKey);
            scheduler.addJob(jobDetail, true, true);

            String holidaysId = cronJob.getHolidaysId();
            if (StringUtils.isNotBlank(holidaysId)) {
                List<Date> holidayDates = cronJob.getCronSchedule().getHolidays();
                if (CollectionUtils.isNotEmpty(holidayDates)) {
                    AnnualCalendar excludedCalendar = new AnnualCalendar();
                    ArrayList<Calendar> calendars = new ArrayList<Calendar>();
                    for (Date date : holidayDates) {
                        Calendar calendar = new DateTime(date).toCalendar(Locale.CHINA);
                        calendars.add(calendar);
                    }
                    excludedCalendar.setDaysExcluded(calendars);
                    scheduler.addCalendar(holidaysId, excludedCalendar, true, true);
                    trigger.setCalendarName(holidaysId);
                }
            }
            if (scheduler.checkExists(triggerKey)) {
                scheduler.rescheduleJob(triggerKey, trigger);
            } else {
                scheduler.scheduleJob(trigger);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param scheduler the scheduler to set
     */
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
