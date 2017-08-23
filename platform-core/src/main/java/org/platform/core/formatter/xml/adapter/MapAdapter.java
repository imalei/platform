/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JaxbMapXmlAdapter.java
 * @package org.platform.core.format.xml
 * @author leise
 * @date 2016年6月27日 下午6:47:24
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.formatter.xml.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @classname: JaxbMapXmlAdapter
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class MapAdapter extends XmlAdapter<MapType, Map<String, Object>> {

    @Override
    public MapType marshal(Map<String, Object> map) throws Exception {

        MapType mapType = new MapType();
        for (Entry<String, Object> entry : map.entrySet()) {
            MapElement mapElement = new MapElement();
            mapElement.key = entry.getKey();
            mapElement.value = entry.getValue();
            mapType.elementList.add(mapElement);
        }
        return mapType;

    }

    @Override
    public Map<String, Object> unmarshal(MapType mapType) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        for (MapElement element : mapType.elementList) {
            map.put(element.key, element.value);
        }
        return map;
    }

}
