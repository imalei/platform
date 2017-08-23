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

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.platform.core.schedule.SimpleSchedule;
import org.platform.core.schedule.enums.ScheduleType;
import org.platform.core.schedule.job.ScheduledJob;
import org.platform.core.schedule.job.SimpleJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.util.Assert;

/**
 * @classname: CronTimerQuartz
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class SimpleQuartzJob {

    private Scheduler scheduler;

    private ScheduledJob job;

    public SimpleQuartzJob(ScheduledJob job) {

        this.job = job;
    }

    public void start() {

        String groupId = this.job.getGroupId();

        if (StringUtils.isBlank(groupId)) {
            groupId = "DEFAULT";
        }

        String jobId = this.job.getJobId();

        JobDetail jobDetail = newJob(QuartzScheduledJob.class).withIdentity(jobId, groupId).build();
        jobDetail.getJobDataMap().put("job_obj", this.job);

        SimpleJob simpleJob = null;
        if (ScheduleType.SIMPLE == job.getScheduleType()) {
            simpleJob = (SimpleJob) this.job;
        } else {
            return;
        }

        SimpleSchedule simpleSchedule = simpleJob.getSimpleSchedule();
        try {

            TriggerBuilder<Trigger> triggerBuilder = newTrigger();
            TriggerKey triggerKey = new TriggerKey(jobId + "_trigger", groupId);
            triggerBuilder.withIdentity(triggerKey);

            Date beginTime = simpleSchedule.getSchedBeginTime() == null ? new Date() : simpleSchedule
                    .getSchedBeginTime();
            Long delay = simpleSchedule.getDelay();
            triggerBuilder.startAt(new Date(beginTime.getTime() + delay * 1000));
            Date endTime = simpleSchedule.getSchedEndTime();
            if (endTime != null) {
                triggerBuilder.endAt(endTime);
            }
            String repeatCount = simpleSchedule.getRepeatCount();
            if ("FOREVER".equalsIgnoreCase(repeatCount)) {
                triggerBuilder.withSchedule(simpleSchedule().repeatForever());
            } else {
                Assert.isTrue(Integer.valueOf(repeatCount) > 0);
                triggerBuilder.withSchedule(simpleSchedule().withRepeatCount(Integer.valueOf(repeatCount)));
            }

            Long repeatInterval = simpleSchedule.getRepeatInterval();
            Assert.isTrue(repeatInterval > 0);
            triggerBuilder.withSchedule(simpleSchedule().withIntervalInSeconds(repeatInterval.intValue()));

            SimpleTriggerImpl trigger = (SimpleTriggerImpl) triggerBuilder.build();
            trigger.setJobName(jobDetail.getKey().getName());

            scheduler.addJob(jobDetail, true, true);

            String holidaysId = simpleJob.getHolidaysId();
            if (StringUtils.isNotBlank(holidaysId)) {
                List<Date> holidayDates = simpleJob.getSimpleSchedule().getHolidays();
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
     * @title: setScheduler
     * @author leise
     * @description:
     * @date 2016年7月4日 下午7:07:03
     * @throws
     */
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

}
