/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: TestOutput.java
 * @package org.platform.core.a.test
 * @author leise
 * @date 2016年4月27日 下午7:26:23
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.a.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.platform.core.base.dto.OutputHeader;

/**
 * @classname: TestOutput
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
public class TestOutput extends OutputHeader {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */

    private static final long serialVersionUID = 1269511303304736918L;

    @XmlElement
    private String test;

    /**
     * @return the test
     */

    public String getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */

    public void setTest(String test) {
        this.test = test;
    }

}
