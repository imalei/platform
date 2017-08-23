/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: CustmerInfoRegistService.java
 * @package org.p2p.service.bizflow.regist
 * @author leise
 * @date 2016年3月18日 下午6:11:18
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.a.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;
import org.platform.core.action.ActionConfigurable;
import org.platform.core.action.crypto.DesCryptoAction;
import org.platform.core.action.enums.CryptoType;
import org.platform.core.action.enums.FileOpType;
import org.platform.core.action.enums.FileType;
import org.platform.core.action.enums.JdbcOpType;
import org.platform.core.action.file.ReadFileDataAction;
import org.platform.core.action.http.HttpCommAction;
import org.platform.core.action.jdbc.JdbcEntityBatchOperAction;
import org.platform.core.action.jdbc.JdbcEntityPageQueryAction;
import org.platform.core.action.jdbc.JdbcEntityQueryAction;
import org.platform.core.action.jdbc.JdbcEntitySingleOperAction;
import org.platform.core.action.jdbc.JdbcSqlOperAction;
import org.platform.core.action.jdbc.JdbcSqlPageQueryAction;
import org.platform.core.action.jdbc.JdbcSqlQueryAction;
import org.platform.core.annotation.action.CryptoAction;
import org.platform.core.annotation.action.FileDataOperAction;
import org.platform.core.annotation.action.HttpAction;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.annotation.action.JdbcSqlAction;
import org.platform.core.annotation.service.PFService;
import org.platform.core.base.service.ApiService;
import org.platform.core.comm.http.resource.PayHttpResource;
import org.platform.core.constant.Constant;
import org.platform.core.error.exception.PFException;
import org.platform.core.formatter.xml.XMLFormatter;
import org.platform.core.jdbc.page.PageParams;
import org.platform.core.mock.server.MockHttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @classname: I00001Service
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@PFService(serviceId = "0002", serviceVersion = Constant.SERVICE_VERSION_DEFAULT, serviceDesc = "测试服务")
public class TestService extends ApiService<TestInput, TestOutput> {

    /**
     * @fields serialVersionUID : 串行号
     */

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */

    private static final long serialVersionUID = -6717198324996916843L;

    /** 服务调用方类名 */
    private String declareClassName = ActionConfig.class.getName();

    @Autowired
    private JdbcSqlQueryAction jdbcSqlQueryAction;

    @Autowired
    private JdbcSqlOperAction jdbcSqlOperAction;

    @Autowired
    private JdbcEntityQueryAction jdbcEntityQueryAction;

    @Autowired
    private JdbcEntitySingleOperAction jdbcEntitySingleOperAction;

    @Autowired
    private JdbcEntityBatchOperAction jdbcEntityBatchOperAction;

    @Autowired
    private JdbcEntityPageQueryAction jdbcEntityPageQueryAction;

    @Autowired
    private JdbcSqlPageQueryAction jdbcSqlPageQueryAction;

    @Autowired
    private DesCryptoAction desCryptoAction;

    @Autowired
    private ReadFileDataAction readFileDataAction;

    @Value("${spring.datasource.url}")
    private String url;

    @Autowired
    private MockHttpServer mockHttpServer;

    @Autowired
    private HttpCommAction httpCommAction;

    @SuppressWarnings("unchecked")
    @Override
    protected TestOutput doExecute(TestInput input) throws PFException {

        HttpReqTest test = new HttpReqTest();
        test.setServiceId("test001");
        test.setAaa("aaa");
        List<TCustRegInfo> testList = new ArrayList<TCustRegInfo>();
        TCustRegInfo r1 = new TCustRegInfo();
        r1.setCustNo("001");
        r1.setCustType("A");
        testList.add(r1);
        TCustRegInfo r2 = new TCustRegInfo();
        r2.setCustNo("002");
        r2.setCustType("B");
        testList.add(r2);
        TCustRegInfo r3 = new TCustRegInfo();
        r3.setCustNo("003");
        r3.setCustType("C");
        testList.add(r3);
        test.setTestList(testList);
        Map<String, Object> testMap = new HashMap<String, Object>();
        testMap.put("maptest", r1);
        test.setTestMap(testMap);
        httpCommAction.execute(ActionConfig.HTTP_TEST, declareClassName, test);

        readFileDataAction.execute(ActionConfig.READ_FILE_DATA_CSV, declareClassName, "F:\\temp\\test\\222.csv");

        readFileDataAction.execute(ActionConfig.READ_FILE_DATA_TXT, declareClassName, "F:\\temp\\test\\111.txt");

        PageParams pageParams = this.initPageParams();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("custNo", "1511160");
        jdbcSqlPageQueryAction.execute(ActionConfig.QUERY_CUSTINFO3, declareClassName, params, pageParams);

        pageParams = this.initPageParams();
        params = new HashMap<String, Object>();
        params.put("custNo", "1511160");
        jdbcSqlPageQueryAction.execute(ActionConfig.QUERY_CUSTINFO4, declareClassName, params, pageParams);

        pageParams = this.initPageParams();
        TCustRegInfo quertRegInfo = new TCustRegInfo();
        quertRegInfo.setLoginPassword("*****");

        Map<String, String> sort = new HashMap<String, String>();
        sort.put("cust_no", "asc");
        pageParams.setSort(sort);
        jdbcEntityPageQueryAction.execute(ActionConfig.QUERY_ENTITY1, declareClassName, quertRegInfo, pageParams);

        params = new HashMap<String, Object>();
        params.put("custNo", "1511160");
        List<?> result = jdbcSqlQueryAction.execute(ActionConfig.QUERY_CUSTINFO1, declareClassName, params);

        params.put("custNo", "1511160");
        List<?> regInfoList = jdbcSqlQueryAction.execute(ActionConfig.QUERY_CUSTINFO2.toString(), declareClassName,
                params);

        params.put("custMobile", "1861111");
        jdbcSqlOperAction.execute(ActionConfig.OPER_CUSTINFO1, declareClassName, params);

        TCustRegInfo regInfo = new TCustRegInfo();
        regInfo.setCustNo("0014");
        jdbcEntityQueryAction.execute(ActionConfig.QUERY_ENTITY1, declareClassName, regInfo);

        TCustRegInfo regInfo1 = new TCustRegInfo();
        regInfo1.setCustNo("a" + RandomUtils.nextInt() + "0016");
        regInfo1.setCustType("03");
        regInfo1.setCustAlias("03");
        regInfo1.setCustMobile("03");
        regInfo1.setCustEmail("03");
        regInfo1.setCustName("03");
        regInfo1.setCustCardtype("03");
        regInfo1.setCustCardno("03");
        regInfo1.setAuthState("0");
        regInfo1.setLoginPassword("123456");

        String desCryptData = desCryptoAction.execute(ActionConfig.PWD_DECRYPT, declareClassName,
                regInfo1.getLoginPassword());

        regInfo1.setLoginPassword(desCryptData);

        String orginData = desCryptoAction.execute(ActionConfig.PWD_ENCRYPT, declareClassName, desCryptData);
        regInfo1.setLoginPassword(orginData);
        jdbcEntitySingleOperAction.execute(ActionConfig.OPERATION_INSERT_ENTITY1, declareClassName, regInfo1);
        //
        // if (true) {
        // throw new RuntimeException("111");
        // }

        TCustRegInfo regInfo2 = new TCustRegInfo();
        regInfo2.setCustNo("1511160");
        regInfo2.setLoginPassword("第13次修改");
        jdbcEntitySingleOperAction.execute(ActionConfig.OPERATION_UPDATE_ENTITY2, declareClassName, regInfo2);

        regInfo2.setCustNo("1511160");
        regInfo2.setLoginPassword("第14次修改");
        jdbcEntitySingleOperAction.execute(ActionConfig.OPERATION_UPDATE_ENTITY3, declareClassName, regInfo2);

        // ----------------
        List<TCustRegInfo> tCustRegInfoList = new ArrayList<TCustRegInfo>();

        for (int i = 0; i < 50000; i++) {
            TCustRegInfo regInfo3 = new TCustRegInfo();
            StringBuffer custNoSBuffer = new StringBuffer().append(DateTime.now().toString("HHmmss")).append(i);
            regInfo3.setCustNo(custNoSBuffer.toString());
            regInfo3.setCustType("03");
            regInfo3.setCustAlias("03");
            regInfo3.setCustMobile("03");
            regInfo3.setCustEmail("03");
            regInfo3.setCustName("03");
            regInfo3.setCustCardtype("03");
            regInfo3.setCustCardno("03");
            regInfo3.setAuthState("0");
            regInfo3.setLoginPassword("*****");

            tCustRegInfoList.add(regInfo3);
        }

        jdbcEntityBatchOperAction.execute(ActionConfig.OPERATION_BATCH_INSERT_ENTITY1, declareClassName,
                tCustRegInfoList);

        List<?> regInfoList1 = jdbcEntityQueryAction.execute(ActionConfig.QUERY_ENTITY1, declareClassName, null);
        List<TCustRegInfo> executeList = new ArrayList<TCustRegInfo>();
        for (TCustRegInfo tCustRegInfo : (List<TCustRegInfo>) regInfoList1) {

            TCustRegInfo temp = new TCustRegInfo();
            temp.setLoginPassword("修改密码");
            temp.setCustNo(tCustRegInfo.getCustNo());
            executeList.add(temp);
        }

        jdbcEntityBatchOperAction.execute(ActionConfig.OPERATION_BATCH_UPDATE_ENTITY2, declareClassName, executeList);

        jdbcEntityBatchOperAction.execute(ActionConfig.OPERATION_BATCH_DELETE_ENTITY4, declareClassName, executeList);

        TestOutput output = new TestOutput();

        // if (true) {
        // throw new RuntimeException("111");
        // }

        return output;
    }

    public PageParams initPageParams() {

        PageParams pageParams = new PageParams();
        pageParams.setCurrentPage(10);
        pageParams.setPageSize(2000);

        return pageParams;
    }

    @Component
    private static class ActionConfig implements ActionConfigurable {

        @HttpAction(name = "模拟发送Http请求", serviceId = "test001", httpResource = PayHttpResource.class, format = "xml", formatter = XMLFormatter.class, reqClass = HttpReqTest.class, resClass = HttpRespTest.class)
        public static final String HTTP_TEST = "HTTP_TEST";

        @CryptoAction(name = "多思密码加密", type = CryptoType.DECRYPT_MODE)
        public static final String PWD_DECRYPT = "PWD_DECRYPT";

        @CryptoAction(name = "多思密码解密", type = CryptoType.ENCRYPT_MODE)
        public static final String PWD_ENCRYPT = "PWD_ENCRYPT";

        @JdbcSqlAction(name = "客户信息查询1", sql = SqlDefined.SQL_QUERY_CUSTINFO, dtoClass = TCustRegInfo.class)
        public static final String QUERY_CUSTINFO1 = "QUERY_CUSTINFO1";

        @JdbcSqlAction(name = "客户信息查询2", sql = SqlDefined.SQL_QUERY_CUSTINFO)
        public static final String QUERY_CUSTINFO2 = "QUERY_CUSTINFO2";

        @JdbcSqlAction(name = "客户信息查询2", sql = SqlDefined.SQL_PAGE_QUERY_CUSTINFO, dtoClass = TCustRegInfo.class)
        public static final String QUERY_CUSTINFO3 = "QUERY_CUSTINFO3";

        @JdbcSqlAction(name = "客户信息查询2", sql = SqlDefined.SQL_PAGE_QUERY_CUSTINFO)
        public static final String QUERY_CUSTINFO4 = "QUERY_CUSTINFO4";

        @JdbcSqlAction(name = "客户信息查询2", sql = SqlDefined.SQL_OPER_CUSTINFO, newTransaction = true)
        public static final String OPER_CUSTINFO1 = "OPER_CUSTINFO1";

        @JdbcEntityAction(name = "实体类查询1", entityClass = TCustRegInfo.class, opType = JdbcOpType.QUERY)
        public static final String QUERY_ENTITY1 = "QUERY_ENTITY1";

        @JdbcEntityAction(name = "实体类操作1", entityClass = TCustRegInfo.class, opType = JdbcOpType.INSERT, newTransaction = true)
        public static final String OPERATION_INSERT_ENTITY1 = "OPERATION_INSERT_ENTITY1";

        @JdbcEntityAction(name = "实体类操作2", entityClass = TCustRegInfo.class, opType = JdbcOpType.UPDATE, newTransaction = true)
        public static final String OPERATION_UPDATE_ENTITY2 = "OPERATION_UPDATE_ENTITY2";

        @JdbcEntityAction(name = "实体类操作3", entityClass = TCustRegInfo.class, opType = JdbcOpType.UPDATE)
        public static final String OPERATION_UPDATE_ENTITY3 = "OPERATION_UPDATE_ENTITY3";

        @JdbcEntityAction(name = "批量实体类操作1", entityClass = TCustRegInfo.class, opType = JdbcOpType.INSERT, newTransaction = true)
        public static final String OPERATION_BATCH_INSERT_ENTITY1 = "OPERATION_BATCH_INSERT_ENTITY1";

        @JdbcEntityAction(name = "批量实体类操作2", entityClass = TCustRegInfo.class, opType = JdbcOpType.UPDATE)
        public static final String OPERATION_BATCH_UPDATE_ENTITY2 = "OPERATION_BATCH_UPDATE_ENTITY2";

        @JdbcEntityAction(name = "批量实体类操作3", entityClass = TCustRegInfo.class, opType = JdbcOpType.UPDATE)
        public static final String OPERATION_BATCH_UPDATE_ENTITY3 = "OPERATION_BATCH_UPDATE_ENTITY3";

        @JdbcEntityAction(name = "批量实体类操作4", entityClass = TCustRegInfo.class, opType = JdbcOpType.DELETE)
        public static final String OPERATION_BATCH_DELETE_ENTITY4 = "OPERATION_BATCH_DELETE_ENTITY4";

        @FileDataOperAction(name = "读取txt文件数据", dtoClass = FileDataDto.class, fileType = FileType.TXT, opType = FileOpType.READ, fields = {
                "s1", "s2", "s3", "s4", "s5" })
        public static final String READ_FILE_DATA_TXT = "READ_FILE_DATA_TXT";

        @FileDataOperAction(name = "读取csv文件数据", dtoClass = FileDataDto.class, fileType = FileType.CSV, opType = FileOpType.READ, fields = {
                "s1", "s2", "s3", "s4", "s5" })
        public static final String READ_FILE_DATA_CSV = "READ_FILE_DATA_CSV";

    }

    private class SqlDefined {

        public static final String SQL_PAGE_QUERY_CUSTINFO = "select c.cust_no from t_cust_reginfo c where 1=1 and c.cust_no>:custNo";

        public static final String SQL_QUERY_CUSTINFO = "select c.cust_no from t_cust_reginfo c where c.cust_no=:custNo";

        public static final String SQL_OPER_CUSTINFO = "update t_cust_reginfo c set c.cust_mobile = :custMobile where c.cust_no=:custNo";

    }
}
