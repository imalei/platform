/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: P2PWebSiteApplication.java
 * @package org.p2p.website
 * @author leise
 * @date 2016年3月8日 上午10:57:24
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @classname: P2PWebSiteApplication
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@ComponentScan({ "org.platform.core"})
@EnableAutoConfiguration
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}
