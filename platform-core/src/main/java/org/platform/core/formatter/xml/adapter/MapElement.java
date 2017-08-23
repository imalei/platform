/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JaxbMapAdater.java
 * @package org.platform.core.format.xml
 * @author leise
 * @date 2016年6月27日 下午6:47:24
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.formatter.xml.adapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @classname: JaxbMapAdater
 * @description: TODO(这里用一句话描述这个类的作用)
 */
public class MapElement {

    @XmlAttribute
    public String key;

    @XmlElement
    public Object value;

    public MapElement() {

    }

    public MapElement(String key, Object value) {
        this.key = key;
        this.value = value;
    }

}
