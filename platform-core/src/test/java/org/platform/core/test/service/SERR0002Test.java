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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.platform.core.action.ActionConfigurable;
import org.platform.core.action.http.HttpCommAction;
import org.platform.core.annotation.action.HttpAction;
import org.platform.core.comm.http.resource.PayHttpResource;
import org.platform.core.constant.Constant;
import org.platform.core.formatter.Formatter;
import org.platform.core.formatter.json.JSONFormatter;
import org.platform.core.system.dto.SysErrorAddInput;
import org.platform.core.system.dto.SysErrorAddOutput;
import org.platform.core.system.entity.TSysError;
import org.platform.core.system.flow.SysErrorAddFlow;
import org.platform.core.test.base.AbstractTest;
import org.platform.core.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: A
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class SERR0002Test extends AbstractTest {

    private String declareClassName = ActionConfig.class.getName();

    @Autowired
    private SysErrorAddFlow sysErrorAddFlow;

    @Autowired
    private HttpCommAction httpCommAction;

    @Autowired
    private BeanUtils beanUtils;

    @Test
    public void beanTest() throws Exception {
        JSONFormatter formatter = (JSONFormatter) beanUtils.getBean("jsonFormatter", Formatter.class);
        System.out.println(formatter);

    }

    @Test
    public void serviceTest() throws Exception {

        SysErrorAddInput input = new SysErrorAddInput();

        List<TSysError> errors = new ArrayList<TSysError>();
        TSysError error = new TSysError();
        error.setErrorNo("AA0000");
        error.setErrorCode("AA0000");
        error.setErrorMessage("交易成功");

        errors.add(error);

        for (int i = 1; i < 100; i++) {
            TSysError err = new TSysError();
            err.setErrorNo("AA000" + i);
            err.setErrorCode("AA000" + i);
            err.setErrorMessage("{0}成功 " + i);
            err.setCreateTime(new Timestamp(DateTime.now().getMillis()));
            err.setModifyTime(new Timestamp(DateTime.now().getMillis()));

            errors.add(err);
        }
        input.setFlag("2");
        input.setErrorList(errors);
        input.setServiceId("SERR0002");
        input.setVersion(Constant.SERVICE_VERSION_DEFAULT);
        input.setRequestId("000001");
        input.setRequestTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

        SysErrorAddOutput output = sysErrorAddFlow.doService(input);
        System.out.println(output);

    }

    @Test
    public void httpTest() throws Exception {

        SysErrorAddInput input = new SysErrorAddInput();

        List<TSysError> errors = new ArrayList<TSysError>();
        TSysError error = new TSysError();
        error.setErrorNo("AA0000");
        error.setErrorCode("AA0000");
        error.setErrorMessage("交易成功");

        errors.add(error);

        for (int i = 0; i < 100; i++) {
            TSysError err = new TSysError();
            err.setErrorNo("AA000" + i);
            err.setErrorCode("AA000" + i);
            err.setErrorMessage("AA{}成功 " + i);

            errors.add(err);
        }

        input.setErrorList(errors);
        input.setFlag("2");
        input.setServiceId("SERR0002");
        input.setVersion(Constant.SERVICE_VERSION_DEFAULT);
        input.setRequestId("000001");
        input.setRequestTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        
        try {
            httpCommAction.execute(ActionConfig.HTTP_TEST, declareClassName, input);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Component
    private static class ActionConfig implements ActionConfigurable {

        @HttpAction(name = "添加系统错误信息", serviceId = "SERR0002", httpResource = PayHttpResource.class, format = "json", formatter = JSONFormatter.class, reqClass = SysErrorAddInput.class, resClass = SysErrorAddOutput.class)
        public static final String HTTP_TEST = "HTTP_TEST";
    }

}
