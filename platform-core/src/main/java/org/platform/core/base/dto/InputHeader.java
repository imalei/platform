/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: RequestHeader.java
 * @package org.platform.core.dto.header
 * @author leise
 * @date 2016年5月9日 下午6:13:58
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.base.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @classname: RequestHeader
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
public class InputHeader implements Input, RequestObject {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 8542522852998622805L;

    @NotBlank(message = "the field [requestId] is required")
    @XmlElement
    private String requestId;

    @NotBlank(message = "the field [requestTime] is required")
    @XmlElement
    private String requestTime;

    @NotBlank(message = "the field [serviceId] is required")
    @XmlElement
    private String serviceId;

    @NotBlank(message = "the field [version] is required")
    @XmlElement
    private String version;

    /**
     * @return the requestId
     */

    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
     * @return the version
     */

    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the requestTime
     */

    public String getRequestTime() {
        return requestTime;
    }

    /**
     * @param requestTime the requestTime to set
     */

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

}
