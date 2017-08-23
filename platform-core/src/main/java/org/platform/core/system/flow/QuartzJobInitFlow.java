/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: QuartzJobInitFlow.java
 * @package org.platform.core.system.flow
 * @author leise
 * @date 2016年3月18日 下午6:11:18
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.flow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.platform.core.action.ActionConfigurable;
import org.platform.core.action.enums.JdbcOpType;
import org.platform.core.action.jdbc.JdbcEntityQueryAction;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.annotation.service.PFService;
import org.platform.core.base.service.SystemService;
import org.platform.core.constant.Constant;
import org.platform.core.schedule.CronSchedule;
import org.platform.core.schedule.SimpleSchedule;
import org.platform.core.schedule.enums.JobMode;
import org.platform.core.schedule.enums.ScheduleType;
import org.platform.core.schedule.job.CronJob;
import org.platform.core.schedule.job.SimpleJob;
import org.platform.core.system.entity.TQuartzJob;
import org.platform.core.system.entity.TSysHolidays;
import org.platform.core.system.entity.TSysService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: QuartzJobInitFlow
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@PFService(serviceId = "QRTZ0001", serviceVersion = Constant.SERVICE_VERSION_DEFAULT, serviceDesc = "系统定时任务初始化", serviceEnable = false)
public class QuartzJobInitFlow extends SystemService {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = -892588236204145588L;

    /** 服务调用方类名 */
    private String declareClassName = ActionConfig.class.getName();

    @Autowired
    private JdbcEntityQueryAction jdbcEntityQueryAction;

    /* 
     * title: doExecute
     * description: 
     * @see org.platform.core.base.service.SystemService#doExecute(java.lang.Object)
     */

    @SuppressWarnings({ "unchecked" })
    @Override
    protected void doExecute() {

        TQuartzJob condition = new TQuartzJob();
        condition.setJobMode(JobMode.AUTO.name());
        List<TQuartzJob> jobList = (List<TQuartzJob>) jdbcEntityQueryAction.execute(
                ActionConfig.QUERY_QUARTZ_JOB, declareClassName, condition);

        if (CollectionUtils.isEmpty(jobList)) {
            return;
        }

        List<TSysHolidays> holidays = (List<TSysHolidays>) jdbcEntityQueryAction.execute(
                ActionConfig.QUERY_SYS_HOLIDAYS, declareClassName, null);

        Map<String, List<Date>> holidaysMap = new HashMap<String, List<Date>>();
        if (CollectionUtils.isEmpty(holidays)) {
            for (TSysHolidays holiday : holidays) {
                String holidaysId = holiday.getHolidaysId();
                List<Date> holidayDates = holidaysMap.get(holidaysId);
                if (CollectionUtils.isEmpty(holidayDates)) {
                    holidayDates = new ArrayList<Date>();
                }
                holidayDates.add(holiday.getHolidayDate());
                holidaysMap.put(holidaysId, holidayDates);
            }
        }

        for (TQuartzJob job : jobList) {

            String holidaysId = job.getHolidaysId();
            List<Date> holidayDates = new ArrayList<Date>();
            if (StringUtils.isNotBlank(holidaysId)) {
                holidayDates = holidaysMap.get(holidaysId);
            }

            ScheduleType scheduleType = ScheduleType.valueOf(job.getScheduleType());
            if (scheduleType == ScheduleType.SIMPLE) {
                SimpleJob simpleJob = new SimpleJob();
                BeanUtils.copyProperties(job, simpleJob);
                SimpleSchedule simpleSchedule = new SimpleSchedule();
                BeanUtils.copyProperties(job, simpleSchedule);
                simpleJob.setSimpleSchedule(simpleSchedule);

                Constant.SYS_SCHEDULED_JOB_LIST.add(simpleJob);

            } else {
                CronJob cronJob = new CronJob();
                BeanUtils.copyProperties(job, cronJob);
                CronSchedule cronSchedule = new CronSchedule();
                cronSchedule.setCronExpression(job.getConExpression());
                cronSchedule.setHolidays(holidayDates);
                cronJob.setCronSchedule(cronSchedule);

                Constant.SYS_SCHEDULED_JOB_LIST.add(cronJob);
            }

        }

    }

    @Component
    private static class ActionConfig implements ActionConfigurable {

        @JdbcEntityAction(name = "查询定时任务定义列表", entityClass = TQuartzJob.class, opType = JdbcOpType.QUERY)
        public static final String QUERY_QUARTZ_JOB = "QUERY_QUARTZ_JOB";

        @JdbcEntityAction(name = "查询系统节假日历表", entityClass = TSysService.class, opType = JdbcOpType.QUERY)
        public static final String QUERY_SYS_HOLIDAYS = "QUERY_SYS_HOLIDAYS";

    }

}
