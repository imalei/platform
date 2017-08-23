/**
 * Copyright (C) 2015-2016 版权所有者为leise。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: MessageSourceConfig.java
 * @package org.p2p.core.messagesource
 * @author leise
 * @date 2015-1-22 下午7:03:08
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.messagesource;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @classname: MessageSourceConfig
 * @description: 注册信息类文件配置
 */
@Configuration
public class MessageSourceConfig {

    @Autowired(required = false)
    private List<MessageSourceRegistration> messageSourceRegistrations;

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        List<String> basenames = new ArrayList<String>();
        basenames.add("common-errors");
        if (CollectionUtils.isNotEmpty(messageSourceRegistrations)) {
            for (MessageSourceRegistration registration : messageSourceRegistrations) {
                basenames.add(registration.getBasename());
            }
        }
        messageSource.setBasenames(basenames.toArray(new String[basenames.size()]));
        return messageSource;
    }

}
