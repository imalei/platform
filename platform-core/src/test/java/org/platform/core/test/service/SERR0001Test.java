/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: A.java
 * @package org.platform.core.test.base
 * @author leise
 * @date 2016年7月8日 下午2:18:06
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.test.service;

import org.junit.Test;
import org.platform.core.system.flow.SysErrorInitFlow;
import org.platform.core.test.base.AbstractTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: A
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class SERR0001Test extends AbstractTest {

    @Autowired
    private SysErrorInitFlow sysErrorInitFlow;

    @Test
    public void serviceTest() throws Exception {
        sysErrorInitFlow.doService();
    }

}
