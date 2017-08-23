/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ActionProcessRegister.java
 * @package org.platform.core.initialize.bean
 * @author leise
 * @date 2016年6月1日 下午4:13:09
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.initializer.register;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.platform.core.annotation.action.CryptoAction;
import org.platform.core.annotation.action.FileDataOperAction;
import org.platform.core.annotation.action.FtpAction;
import org.platform.core.annotation.action.HttpAction;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.annotation.action.JdbcGenSeqNoAction;
import org.platform.core.annotation.action.JdbcSqlAction;

/**
 * @classname: ActionProcessRegister
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class ActionProcessRegister {

    public static final Set<Class<? extends Annotation>> PROCESS_ACTION_ANNOTATIONS = new HashSet<Class<? extends Annotation>>();

    static {
        PROCESS_ACTION_ANNOTATIONS.add(JdbcSqlAction.class);
        PROCESS_ACTION_ANNOTATIONS.add(JdbcEntityAction.class);
        PROCESS_ACTION_ANNOTATIONS.add(JdbcGenSeqNoAction.class);
        PROCESS_ACTION_ANNOTATIONS.add(CryptoAction.class);
        PROCESS_ACTION_ANNOTATIONS.add(FtpAction.class);
        PROCESS_ACTION_ANNOTATIONS.add(FileDataOperAction.class);
        PROCESS_ACTION_ANNOTATIONS.add(HttpAction.class);
    }

}
