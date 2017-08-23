/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: SysServiceInitFlow.java
 * @package org.platform.core.system.flow
 * @author leise
 * @date 2016年3月18日 下午6:11:18
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.platform.core.action.ActionConfigurable;
import org.platform.core.action.enums.JdbcOpType;
import org.platform.core.action.jdbc.JdbcEntityBatchOperAction;
import org.platform.core.action.jdbc.JdbcEntityQueryAction;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.annotation.service.PFService;
import org.platform.core.base.service.ApiService;
import org.platform.core.base.service.Service;
import org.platform.core.base.service.ServiceInfo;
import org.platform.core.base.service.SystemService;
import org.platform.core.constant.Constant;
import org.platform.core.system.entity.TSysService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @classname: SysServiceInitFlow
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@PFService(serviceId = "SYS0001", serviceVersion = Constant.SERVICE_VERSION_DEFAULT, serviceDesc = "系统服务初始化", serviceEnable = false)
public class SysServiceInitFlow extends SystemService {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = -892588236204145588L;

    /** 服务调用方类名 */
    private String declareClassName = ActionConfig.class.getName();

    @Autowired
    private JdbcEntityQueryAction jdbcEntityQueryAction;

    @Autowired
    private JdbcEntityBatchOperAction jdbcEntityBatchOperAction;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected void doExecute() {

        List<TSysService> dbSysServiceList = (List<TSysService>) jdbcEntityQueryAction.execute(
                ActionConfig.QUERY_SYS_SERVICE, declareClassName, null);

        Map<String, TSysService> dbSysServiceMap = new HashMap<String, TSysService>();

        for (TSysService sysService : dbSysServiceList) {
            String serviceId = sysService.getServiceId();
            String serviceVersion = sysService.getServiceVersion();
            String serviceEntityKey = StringUtils.join(new Object[] { serviceId, serviceVersion }, "_");
            dbSysServiceMap.put(serviceEntityKey, sysService);
        }

        List<TSysService> insertSysServiceList = new ArrayList<TSysService>();

        List<TSysService> updateSysServiceList = new ArrayList<TSysService>();

        List<Service> services = Constant.SYS_SERVICE_LIST;

        for (Service service : services) {

            Class<?> serviceClass = service.getClass();
            String serviceClassName = service.getClass().getName();

            Assert.isTrue(serviceClass.isAnnotationPresent(PFService.class), "the service [" + serviceClassName
                    + "] must be set @PFService Annotation!");

            PFService pfService = serviceClass.getAnnotation(PFService.class);
            String serviceId = pfService.serviceId();
            String serviceVersion = pfService.serviceVersion();
            String serviceDesc = pfService.serviceDesc();

            Assert.isTrue(StringUtils.isNotBlank(serviceId), "the service [" + serviceClassName
                    + "]  @PFService Annotation's field [serviceId] must be not blank");
            Assert.isTrue(StringUtils.isNotBlank(serviceVersion), "the service [" + serviceClassName
                    + "]  @PFService Annotation's field [serviceVersion] must be not blank");
            Assert.isTrue(StringUtils.isNotBlank(serviceDesc), "the service [" + serviceClassName
                    + "]  @PFService Annotation's field [serviceDesc] must be not blank");

            String serviceType = serviceClass.getSuperclass().getSimpleName();

            String inputClassName = "";
            String outputClassName = "";
            if (service instanceof ApiService) {
                inputClassName = ((ApiService) service).getInputClassName();
                outputClassName = ((ApiService) service).getOutputClassName();
            }

            TSysService serviceEntity = new TSysService();
            serviceEntity.setServiceId(serviceId);
            serviceEntity.setServiceVersion(serviceVersion);
            serviceEntity.setServiceType(serviceType);
            serviceEntity.setServiceDesc(serviceDesc);
            serviceEntity.setServiceClass(serviceClassName);
            serviceEntity.setInputClass(inputClassName);
            serviceEntity.setOutputClass(outputClassName);

            String serviceInfoKey = StringUtils.join(new Object[] { serviceId, serviceVersion }, "_");

            if (dbSysServiceMap.containsKey(serviceInfoKey)) {
                serviceEntity.setServiceEnable(dbSysServiceMap.get(serviceInfoKey).getServiceEnable());
                updateSysServiceList.add(serviceEntity);
            } else {
                boolean serviceEnable = pfService.serviceEnable();
                serviceEntity
                        .setServiceEnable(serviceEnable ? Constant.SERVICE_ENABLE_ON : Constant.SERVICE_ENABLE_OFF);
                insertSysServiceList.add(serviceEntity);
            }

            ServiceInfo serviceInfo = new ServiceInfo();
            BeanUtils.copyProperties(serviceEntity, serviceInfo);
            serviceInfo.setService(service);

            Constant.SERVICE_INFO_MAP.put(serviceInfoKey, serviceInfo);

        }

        List<TSysService> deleteSysServiceList = new ArrayList<TSysService>();

        Set<String> dbKeySet = dbSysServiceMap.keySet();

        for (String dbKey : dbKeySet) {
            if (!Constant.SERVICE_INFO_MAP.containsKey(dbKey)) {
                TSysService sysService = dbSysServiceMap.get(dbKey);
                deleteSysServiceList.add(sysService);
            }
        }

        if (CollectionUtils.isNotEmpty(insertSysServiceList)) {
            jdbcEntityBatchOperAction.execute(ActionConfig.INSERT_SYS_SERVICE, declareClassName, insertSysServiceList);
            insertSysServiceList.clear();
        }

        if (CollectionUtils.isNotEmpty(updateSysServiceList)) {
            jdbcEntityBatchOperAction.execute(ActionConfig.UPDATE_SYS_SERVICE, declareClassName, updateSysServiceList);
            updateSysServiceList.clear();
        }

        if (CollectionUtils.isNotEmpty(deleteSysServiceList)) {
            jdbcEntityBatchOperAction.execute(ActionConfig.DELETE_SYS_SERVICE, declareClassName, deleteSysServiceList);
            deleteSysServiceList.clear();
        }

    }

    @Component
    private static class ActionConfig implements ActionConfigurable {

        @JdbcEntityAction(name = "查询系统服务列表", entityClass = TSysService.class, opType = JdbcOpType.QUERY)
        public static final String QUERY_SYS_SERVICE = "QUERY_SYS_SERVICE";

        @JdbcEntityAction(name = "新增系统服务信息", entityClass = TSysService.class, opType = JdbcOpType.INSERT)
        public static final String INSERT_SYS_SERVICE = "INSERT_SYS_SERVICE";

        @JdbcEntityAction(name = "更新系统服务信息", entityClass = TSysService.class, opType = JdbcOpType.UPDATE)
        public static final String UPDATE_SYS_SERVICE = "UPDATE_SYS_SERVICE";

        @JdbcEntityAction(name = "删除系统服务信息", entityClass = TSysService.class, opType = JdbcOpType.DELETE)
        public static final String DELETE_SYS_SERVICE = "DELETE_SYS_SERVICE";

    }

}
