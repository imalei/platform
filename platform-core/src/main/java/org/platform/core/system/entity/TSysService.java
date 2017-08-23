/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: TServiceInfo.java
 * @package org.platform.core.entity
 * @author leise
 * @date 2016年5月9日 下午3:16:10
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.entity;

import org.platform.core.annotation.entity.PK;
import org.platform.core.annotation.entity.Table;
import org.platform.core.base.entity.BaseEntity;

/**
 * @classname: TServiceInfo
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Table("T_SYS_SERVICE")
public class TSysService extends BaseEntity {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -6148146771036131046L;

    /** 服务ID */
    @PK
    private String serviceId;

    /** 版本号 **/
    @PK
    private String serviceVersion;

    /** 服务类型 */
    private String serviceType;

    /** 服务描述 */
    private String serviceDesc;

    /** 服务类名 **/
    private String serviceClass;

    /** 输入参数类名 */
    private String inputClass;

    /** 输出参数类名 */
    private String outputClass;

    /** 是否启用标识 */
    private String serviceEnable;

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
     * @return the serviceEnable
     */

    public String getServiceEnable() {
        return serviceEnable;
    }

    /**
     * @param serviceEnable the serviceEnable to set
     */

    public void setServiceEnable(String serviceEnable) {
        this.serviceEnable = serviceEnable;
    }

    /**
     * @return the inputClass
     */

    public String getInputClass() {
        return inputClass;
    }

    /**
     * @param inputClass the inputClass to set
     */

    public void setInputClass(String inputClass) {
        this.inputClass = inputClass;
    }

    /**
     * @return the outputClass
     */

    public String getOutputClass() {
        return outputClass;
    }

    /**
     * @param outputClass the outputClass to set
     */

    public void setOutputClass(String outputClass) {
        this.outputClass = outputClass;
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
     * @return the serviceType
     */

    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

}
