/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: HttpReqTest.java
 * @package org.platform.core.a.test
 * @author leise
 * @date 2016年6月20日 下午12:23:14
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.a.test;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.platform.core.base.dto.RequestObject;
import org.platform.core.formatter.xml.adapter.MapAdapter;

/**
 * @classname: HttpReqTest
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class HttpReqTest implements RequestObject {

    @XmlElement
    private String serviceId;

    @XmlElement
    private String aaa;

    @XmlElementWrapper(name = "testList")
    @XmlElement(name = "custRegInfo")
    private List<TCustRegInfo> testList;

    @XmlJavaTypeAdapter(value = MapAdapter.class)
    @XmlElement(name = "testMap")
    private Map<String, Object> testMap;
    
    /**
     * @return the aaa
     */

    public String getAaa() {
        return aaa;
    }

    /**
     * @param aaa the aaa to set
     */

    public void setAaa(String aaa) {
        this.aaa = aaa;
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
     * @return the testList
     */

    public List<TCustRegInfo> getTestList() {
        return testList;
    }

    /**
     * @param testList the testList to set
     */

    public void setTestList(List<TCustRegInfo> testList) {
        this.testList = testList;
    }

    /**
     * @return the testMap
     */

    public Map<String, Object> getTestMap() {
        return testMap;
    }

    /**
     * @param testMap the testMap to set
     */

    public void setTestMap(Map<String, Object> testMap) {
        this.testMap = testMap;
    }

}
