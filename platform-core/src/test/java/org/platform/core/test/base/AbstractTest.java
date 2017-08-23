/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ATest.java
 * @package org.platform.core.test
 * @author leise
 * @date 2016年7月7日 下午9:15:40
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.test.base;

import org.junit.runner.RunWith;
import org.platform.core.PlatformApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @classname: ATest
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PlatformApplication.class)
@Rollback
// @WebAppConfiguration
@ActiveProfiles("junit")
@WebIntegrationTest("server.port:6666")
public abstract class AbstractTest{

}
