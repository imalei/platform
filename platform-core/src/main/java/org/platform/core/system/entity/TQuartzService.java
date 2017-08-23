/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: TQuartzService.java
 * @package org.platform.core.system.entity
 * @author leise
 * @date 2016年7月6日 下午7:35:28
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.entity;

import org.platform.core.annotation.entity.PK;
import org.platform.core.annotation.entity.Table;
import org.platform.core.base.entity.BaseEntity;

/**
 * @classname: TQuartzService
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Table("T_QUARTZ_SERVICE")
public class TQuartzService extends BaseEntity {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = -581543589267846011L;

    /** 序号 */
    @PK
    private String id;

    /** 任务编号 */
    private String jobId;

    /** 服务编号 */
    private String serviceId;

    /** 服务版本号 */
    private String serviceVersion;

    /** 服务描述 */
    private String serviceDesc;

    /** 服务类 */
    private String serviceClass;

    /**
     * 执行模式 0：串行执行 1：并行执行
     */
    private String executeMode;

    /** 执行顺序 */
    private String executeOrder;

    /**
     * @return the id
     */

    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */

    public void setId(String id) {
        this.id = id;
    }

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
     * @return the serviceId
     */

    public String getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the serviceVersion
     */

    public String getServiceVersion() {
        return serviceVersion;
    }

    /**
     * @param serviceVersion the serviceVersion to set
     */

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    /**
     * @return the serviceDesc
     */

    public String getServiceDesc() {
        return serviceDesc;
    }

    /**
     * @param serviceDesc the serviceDesc to set
     */

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    /**
     * @return the serviceClass
     */

    public String getServiceClass() {
        return serviceClass;
    }

    /**
     * @param serviceClass the serviceClass to set
     */

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    /**
     * @return the executeMode
     */

    public String getExecuteMode() {
        return executeMode;
    }

    /**
     * @param executeMode the executeMode to set
     */

    public void setExecuteMode(String executeMode) {
        this.executeMode = executeMode;
    }

    /**
     * @return the executeOrder
     */

    public String getExecuteOrder() {
        return executeOrder;
    }

    /**
     * @param executeOrder the executeOrder to set
     */

    public void setExecuteOrder(String executeOrder) {
        this.executeOrder = executeOrder;
    }

}
