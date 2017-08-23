/**
 * Copyright (C) 2015-2016 版权所有者为leise。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: MessageUtils.java
 * @package org.p2p.core.utils
 * @author leise
 * @date 2015-1-23 下午12:26:07
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */
package org.platform.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * @classname: MessageUtils
 * @description: message工具类
 */

@Component
public class MessageUtils {

    @Autowired
    private MessageSource messageSource;

    /**
     * @title: getMessage
     * @author leise
     * @description:
     * @date 2016年3月7日 下午6:33:34
     * @throws
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * @title: getMessage
     * @author leise
     * @description:
     * @date 2016年3月7日 下午6:33:40
     * @throws
     */
    public String getMessage(String code, Object[] params) {
        return messageSource.getMessage(code, params, null);
    }

}
