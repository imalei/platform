/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: BaseRespHeader.java
 * @package org.platform.core.dto.header
 * @author leise
 * @date 2016年5月9日 下午6:15:11
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.base.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @classname: BaseRespHeader
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
public class OutputHeader implements Output, ResponseObject {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -5535463733906992287L;

    @XmlElement
    private String errorCode;

    @XmlElement
    private String errorMsg;

    @XmlElement
    private String serviceId;

    @XmlElement
    private String version;

    @XmlElement
    private String requestId;

    @XmlElement
    private String responseTime;

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
     * @return the errorCode
     */

    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMsg
     */

    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the responseTime
     */

    public String getResponseTime() {
        return responseTime;
    }

    /**
     * @param responseTime the responseTime to set
     */

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

}
