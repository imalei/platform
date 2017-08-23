/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JdbcGenSequenceActionTest.java
 * @package org.platform.core.test.action
 * @author leise
 * @date 2016年7月9日 下午12:47:29
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.test.action;

import org.junit.Test;
import org.platform.core.action.jdbc.JdbcGenSequenceAction;
import org.platform.core.annotation.action.JdbcGenSeqNoAction;
import org.platform.core.test.base.AbstractTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: Snippet
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
public class JdbcGenSequenceActionTest extends AbstractTest {

    private String declareClassName = this.getClass().getName();

    @Autowired
    private JdbcGenSequenceAction jdbcGenSequenceAction;

    @Test
    public void actionTest() {
        String seqNo = jdbcGenSequenceAction.execute(ActionDefined.GEN_SEQNO.name(), declareClassName);
        System.out.println(seqNo);
    }

    private enum ActionDefined {
        @JdbcGenSeqNoAction(name = "生成序列号", seqName = "SEQ_SYS_HOLIDAYS")
        GEN_SEQNO
    }

}
