/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ErrorNo.java
 * @package org.platform.core.exception.enums
 * @author leise
 * @date 2016年7月11日 上午10:07:01
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.error.enums;

/**
 * @classname: ErrorNo
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public enum ErrorCode {

    /** errorode:000000 errorMessage: 处理成功 */
    DEFAULT_SUCCESS("000000"),

    /** errorode:999999 errorMessage: 处理失败，系统错误，请联系管理员 */
    DEFAULT_FAILED("999999"),

    /** errorode:SE9999 errorMessage: 服务流程执行失败 */
    SERVICE_EXECUTE_FAILED("SE9999"),

    /** errorode:SU9999 errorMessage: 未找到相应的服务或服务已关闭 */
    SERVICE_USEABLE_ERROR("SU9999"),

    /** errorode:PI9999 errorMessage: 解析请求报文头失败 */
    PARSE_INPUT_HEADER_ERROR("PI9999"),

    /** errorode:PI9998 errorMessage: 解析请求报文体失败 */
    PARSE_INPUT_BODY_ERROR("PI9998"),

    TEST_ERROR("test"),

    /** errorode:AE9999 errorMessage: {0}失败 */
    ACTION_EXECUTE_FAILD("AE9999"),

    /** errorode:HC9000 errorMessage: 通讯超时 */
    HTTP_COMM_UNKNOW("HC9000"),

    /** errorode:HC9999 errorMessage: 通讯失败 */
    HTTP_COMM_FAILED("HC9999"),

    /** errorode:DB9000 errorMessage: 数据单笔操作失败 */
    JDBC_SINGLE_OPERATION_FAILED("DB9000"),

    /** errorode:DB9001 errorMessage: 数据批量操作失败 */
    JDBC_BATCH_OPERATION_FAILED("DB9001"),

    /** errorode:DB9002 errorMessage: 数据分页查询失败 */
    JDBC_PAGE_QUERY_FAILED("DB9002"),

    /** errorode:DB9003 errorMessage: 数据查询失败 */
    JDBC_QUERY_FAILED("DB9003"),

    /** DB9004 errorMessage: 序列号生成失败 */
    JDBC_GENSEQ_FAILED("DB9004"),

    /** errorode:DB9005 errorMessage: 数据操作失败 */
    JDBC_SQL_OPERATION_FAILED("DB9005"),

    /** errorode:DB9006 errorMessage: 数据查询失败 */
    JDBC_SQL_QUERY_FAILED("DB9006"),

    /** errorode:DB9006 errorMessage: 数据分页查询失败 */
    JDBC_SQL_PAGEQUERY_FAILED("DB9007"),

    /** errorode:VD9999 errorMessage: 数据验证失败:{} */
    VALIDATE_DATA_FAILED("VD9999");

    private String errorNo;

    ErrorCode(String errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorNo() {
        return errorNo;
    }

}
