
/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。 
 * @title: MapType.java
 * @package org.platform.core.format.xml.adapter
 * @author leise
 * @date 2016年6月28日 下午3:17:48
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */ 

package org.platform.core.formatter.xml.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


	
/**
 * @classname: MapType
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class MapType {
    
    @XmlElement(name ="element")
    public List<MapElement> elementList = new ArrayList<MapElement>();

}
