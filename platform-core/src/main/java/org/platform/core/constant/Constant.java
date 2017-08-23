/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: Constant.java
 * @package org.platform.core.constant
 * @author leise
 * @date 2016年4月7日 下午11:03:42
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.platform.core.base.service.Service;
import org.platform.core.base.service.ServiceInfo;
import org.platform.core.initializer.register.EntityRegister;
import org.platform.core.schedule.job.ScheduledJob;
import org.platform.core.system.entity.TSysError;

/**
 * @classname: Constant
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class Constant {

    public static final String SERVICE_VERSION_DEFAULT = "1.0.0";

    public static final String FORMAT_JSON = "json";

    public static final String FORMAT_XML = "xml";

    public static final String DEFAULT_ENCODING = "utf-8";

    public static List<Service> SYS_SERVICE_LIST = new ArrayList<Service>();

    public static List<ScheduledJob> SYS_SCHEDULED_JOB_LIST = new ArrayList<ScheduledJob>();

    public static final Map<String, TSysError> SYS_ERROR_MAP = new HashMap<String, TSysError>();

    public static final Map<String, ServiceInfo> SERVICE_INFO_MAP = new HashMap<String, ServiceInfo>();

    public static final Map<String, EntityRegister> ENTITY_INFO_MAP = new HashMap<String, EntityRegister>();

    public static final HashMap<String, String> ENTITY_SQL_MAP = new HashMap<String, String>();

    public static final String REQUEST_URI_PREFIX = "/service";

    public static final String SERVICE_ENABLE_ON = "1";

    public static final String SERVICE_ENABLE_OFF = "0";

    public static final String DATE_TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String ERROR_CODE_SUCCESS = "000000";

    public static final String ERROR_CODE_FAILED = "999999";

}
