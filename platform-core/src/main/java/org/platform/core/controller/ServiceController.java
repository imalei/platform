/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ServiceController.java
 * @package org.platform.core.controller
 * @author leise
 * @date 2016年5月9日 下午2:27:21
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.platform.core.base.dto.Input;
import org.platform.core.base.dto.InputHeader;
import org.platform.core.base.dto.Output;
import org.platform.core.base.dto.OutputHeader;
import org.platform.core.base.service.ApiService;
import org.platform.core.base.service.ServiceInfo;
import org.platform.core.constant.Constant;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;
import org.platform.core.formatter.Formatter;
import org.platform.core.formatter.json.JSONFormatter;
import org.platform.core.formatter.xml.XMLFormatter;
import org.platform.core.parser.ServiceInputParser;
import org.platform.core.validator.BeanValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @classname: ServiceController
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Controller
@RequestMapping(value = Constant.REQUEST_URI_PREFIX)
public class ServiceController {

    private static Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private Validator validator;

    /** 服务输入参数类对象解析器 通过反射找到输入参数类对象 */
    @Autowired
    private ServiceInputParser serviceInputParser;

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    @ResponseBody
    public Output doExecute(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestMsg) {

        logger.info("[服务控制器]--[接收到请求数据]:{}", requestMsg);

        OutputHeader outputHeader = new OutputHeader();
        Formatter formatter = null;
        String format = this.getRequestFormat(request);
        if (format.equals("unDefined")) {
            String errorCode = Constant.SYS_ERROR_MAP.get(ErrorCode.DEFAULT_FAILED.getErrorNo()).getErrorCode();
            outputHeader.setErrorCode(errorCode);
            outputHeader.setErrorMsg("Unsupport the request format, can't parse the request message");
            outputHeader.setResponseTime(DateTime.now().toString(Constant.DATE_TIMEFORMAT));
            return outputHeader;
        } else if (Constant.FORMAT_XML.equals(format)) {
            formatter = new XMLFormatter();
        } else if(Constant.FORMAT_JSON.equals(format)){
            formatter = new JSONFormatter();
        }else{
            String errorCode = Constant.SYS_ERROR_MAP.get(ErrorCode.DEFAULT_FAILED.getErrorNo()).getErrorCode();
            outputHeader.setErrorCode(errorCode);
            outputHeader.setErrorMsg("Unsupport the request format, can't parse the request message");
            outputHeader.setResponseTime(DateTime.now().toString(Constant.DATE_TIMEFORMAT));
            return outputHeader;
        }

        ApiService<?, ?> service = null;
        Input input = null;
        try {
            InputHeader inputHeader = this.parseInputHeader(requestMsg, formatter);
            BeanValidator.validateWithException(validator, inputHeader);
            BeanUtils.copyProperties(inputHeader, outputHeader);
            String serviceId = inputHeader.getServiceId();
            String version = inputHeader.getVersion();
            String serviceKey = StringUtils.join(new Object[] { serviceId, version }, "_");
            input = this.parseInputBody(serviceKey, requestMsg, formatter);
            BeanValidator.validateWithException(validator, input);
            service = getService(serviceKey);
        }
        catch (Exception e) {
            e.printStackTrace();
            return exceptionHandler(e, outputHeader);
        }

        Output output = null;

        try {
            output = service.doService(input);
        }
        catch (Exception e) {
            e.printStackTrace();
            return exceptionHandler(e, outputHeader);
        }

        try {
            outputHeader.setResponseTime(DateTime.now().toString(Constant.DATE_TIMEFORMAT));
            String errorCode = Constant.SYS_ERROR_MAP.get(ErrorCode.DEFAULT_SUCCESS.getErrorNo()).getErrorCode();
            String errorMsg = Constant.SYS_ERROR_MAP.get(ErrorCode.DEFAULT_SUCCESS.getErrorNo()).getErrorMessage();
            outputHeader.setErrorCode(errorCode);
            outputHeader.setErrorMsg(errorMsg);

            if (output != null) {
                BeanUtils.copyProperties(outputHeader, output);
                return output;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (output != null) {
                return output;
            }
        }

        return outputHeader;
    }

    private Output exceptionHandler(Exception e, OutputHeader outputHeader) {
        outputHeader.setResponseTime(DateTime.now().toString(Constant.DATE_TIMEFORMAT));
        if (e instanceof PFException) {
            PFException pfe = (PFException) e;
            String errorCode = pfe.getErrorCode();
            String errorMsg = pfe.getErrorMessage();
            outputHeader.setErrorCode(errorCode);
            outputHeader.setErrorMsg(errorMsg);
        } else {
            String errorCode = Constant.SYS_ERROR_MAP.get(ErrorCode.DEFAULT_FAILED.getErrorNo()).getErrorCode();
            String errorMsg = Constant.SYS_ERROR_MAP.get(ErrorCode.DEFAULT_FAILED.getErrorNo()).getErrorMessage();
            outputHeader.setErrorCode(errorCode);
            outputHeader.setErrorMsg(errorMsg);
        }

        return outputHeader;
    }

    private String getRequestFormat(HttpServletRequest request) {
        String contentType = request.getHeader("Content-type");
        String format = null;
        if (contentType.indexOf(Constant.FORMAT_JSON) != -1) {
            format = Constant.FORMAT_JSON;
        } else if (contentType.indexOf(Constant.FORMAT_XML) != -1) {
            format = Constant.FORMAT_XML;
        } else {
            format = "unDefined";
        }
        return format;
    }

    private InputHeader parseInputHeader(final String requestMsg, final Formatter formatter) throws PFException {
        InputHeader inputHeader = null;
        try {
            inputHeader = formatter.unformat(requestMsg, InputHeader.class);
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.PARSE_INPUT_HEADER_ERROR).build();
        }
        return inputHeader;
    }

    private Input parseInputBody(final String serviceKey, final String requestMsg, final Formatter formatter)
            throws PFException {
        Input input = null;

        try {
            input = serviceInputParser.parse(serviceKey);
            input = formatter.unformat(requestMsg, input.getClass());
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.PARSE_INPUT_BODY_ERROR).build();
        }
        return input;
    }

    private ApiService<?, ?> getService(String serviceId) throws PFException {
        ApiService<?, ?> service = null;
        try {
            ServiceInfo serviceInfo = Constant.SERVICE_INFO_MAP.get(serviceId);
            Assert.notNull(serviceInfo, "can't find the service [" + serviceId + "] information");
            String serviceEnable = serviceInfo.getServiceEnable();
            Assert.isTrue(Constant.SERVICE_ENABLE_ON.equals(serviceEnable), "the service [" + serviceId
                    + "] stop using");
            service = (ApiService<?, ?>) serviceInfo.getService();
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.SERVICE_USEABLE_ERROR).build();
        }

        return service;

    }

}
