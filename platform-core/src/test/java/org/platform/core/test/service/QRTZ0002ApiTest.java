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

import org.joda.time.DateTime;
import org.junit.Test;
import org.platform.core.action.ActionConfigurable;
import org.platform.core.action.http.HttpCommAction;
import org.platform.core.annotation.action.HttpAction;
import org.platform.core.comm.http.resource.PayHttpResource;
import org.platform.core.constant.Constant;
import org.platform.core.formatter.json.JSONFormatter;
import org.platform.core.system.dto.QuartzJobAddInput;
import org.platform.core.system.dto.QuartzJobAddOutput;
import org.platform.core.test.base.AbstractTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: A
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class QRTZ0002ApiTest extends AbstractTest {

    private String declareClassName = ActionConfig.class.getName();

    @Autowired
    private HttpCommAction httpCommAction;

    @Test
    public void httpTest() throws Exception {
        QuartzJobAddInput input = new QuartzJobAddInput();
        input.setServiceId("QRTZ0002");
        input.setVersion(Constant.SERVICE_VERSION_DEFAULT);
        input.setRequestId("000001");
        input.setRequestTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

        QuartzJobAddOutput output = (QuartzJobAddOutput) httpCommAction.execute(ActionConfig.HTTP_TEST,
                declareClassName, input);
        System.out.println(output);
    }

    @Component
    private static class ActionConfig implements ActionConfigurable {

        @HttpAction(name = "添加定时任务", serviceId = "QRTZ0002", httpResource = PayHttpResource.class, format = "json", formatter = JSONFormatter.class, reqClass = QuartzJobAddInput.class, resClass = QuartzJobAddOutput.class)
        public static final String HTTP_TEST = "HTTP_TEST";
    }

}
