/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: FtpAction.java
 * @package org.platform.core.annotation.action
 * @author leise
 * @date 2016年4月26日 下午12:26:51
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.annotation.action;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.platform.core.action.enums.FtpType;
import org.platform.core.comm.ftp.resource.FtpResource;

/**
 * @classname: FtpAction
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FtpAction {

    String name();

    String remotePath() default "";

    String localPath() default "";

    FtpType ftpType();

    Class<? extends FtpResource> ftpResource();

}
