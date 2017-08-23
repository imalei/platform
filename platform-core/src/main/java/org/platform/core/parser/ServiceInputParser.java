/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ServiceInputParser.java
 * @package org.platform.core.parser
 * @author leise
 * @date 2016年5月9日 下午2:49:25
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.parser;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.platform.core.base.dto.Input;
import org.platform.core.base.service.ServiceInfo;
import org.platform.core.constant.Constant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @classname: ServiceInputParser
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
public class ServiceInputParser {

    public Input parse(String serviceId) throws Exception {

        Input input = getInput(serviceId);

        return input;
    }

    private Input getInput(String serviceId) throws Exception {
        ServiceInfo serviceInfo = Constant.SERVICE_INFO_MAP.get(serviceId);
        Assert.notNull(serviceInfo, "the service info can't be found, please check in the service initializer");
        Class<? extends Input> inputDtoClass = getInputClass(serviceId, serviceInfo);
        return BeanUtils.instantiate(inputDtoClass);
    }

    @SuppressWarnings("unchecked")
    protected Class<? extends Input> getInputClass(String serviceId, ServiceInfo serviceInfo) throws Exception {
        String inputClassName = serviceInfo.getInputClass();
        Assert.isTrue(StringUtils.isNotBlank(inputClassName), "the inupt class is required");
        Class<? extends Input> inputDtoClass = ClassUtils.getClass(inputClassName);
        return inputDtoClass;
    }

}
