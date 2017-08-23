/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ScheduleType.java
 * @package org.platform.core.schedule.enums
 * @author leise
 * @date 2016年7月7日 上午9:23:48
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.schedule.enums;

/**
 * @classname: ScheduleType
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public enum ScheduleType {

    SIMPLE("简单任务"), CRON("复杂任务");

    private String value;

    ScheduleType(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */

    public String getValue() {
        return value;
    }

}
