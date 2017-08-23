/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: DesCryptoAction.java
 * @package org.platform.core.action.crypto
 * @author leise
 * @date 2016年5月30日 上午10:07:00
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.action.crypto;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.platform.core.action.Action;
import org.platform.core.action.enums.CryptoType;
import org.platform.core.annotation.action.CryptoAction;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;
import org.platform.core.utils.ActionAnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @classname: DesCryptoAction
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class DesCryptoAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(DesCryptoAction.class);

    @Value(value = "${platform.crypto.des.encropt-mode}")
    String encroptMode;

    @Value(value = "${platform.crypto.des.padding-mode}")
    String paddingMode;

    @Value(value = "${platform.crypto.des.algorithm}")
    String algorithm;

    @Value(value = "${platform.crypto.des.key}")
    String key;

    public String execute(String actionId, String declareClassName, String cryptoData) throws PFException {

        logger.info("[执行action开始]--[actionId:{}--[调用方:{}]------", actionId, declareClassName);

        Assert.notNull(cryptoData, "the cryptoData must be not null");
        logger.info("[actionId:{}]--[调用方:{}]--[加密或解密数据:{}]", actionId, declareClassName, cryptoData);

        CryptoAction cryptoAction = ActionAnnotationUtils.getAnnotation(declareClassName, actionId, CryptoAction.class);
        Assert.notNull(cryptoAction, "the CryptoAction config must be not null");

        String actionName = cryptoAction.name();
        logger.info("[actionId:{}]--[调用方:{}]--[actionName:{}]", actionId, declareClassName, actionName);

        CryptoType cryptoType = cryptoAction.type();

        String resultData = null;
        try {

            switch (cryptoType) {
                case ENCRYPT_MODE:
                    resultData = encrypt(cryptoData);
                    break;

                case DECRYPT_MODE:
                    resultData = decrypt(cryptoData);
                    break;

                default:
                    return null;
            }
            logger.info("[actionId:{}]--[调用方:{}]--[加密或解密数据:{}]", actionId, declareClassName, resultData);
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.ACTION_EXECUTE_FAILD)
                    .withMessageArgs(new Object[] { "加密解密" }).withCause(e).build();
        }
        logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]------", actionId, declareClassName);

        return resultData;

    }

    private String encrypt(String cryptoData) throws Exception {

        String resultData = null;

        BASE64Decoder base64decoder = new BASE64Decoder();
        byte[] keyBytes = base64decoder.decodeBuffer(this.key);
        KeyGenerator generator = KeyGenerator.getInstance(this.algorithm);
        generator.init(new SecureRandom(keyBytes));
        Key key = generator.generateKey();
        Cipher cipher = Cipher.getInstance(this.algorithm + "/" + this.encroptMode + "/" + this.paddingMode);

        cipher.init(2, key);
        byte[] oriBytes = cipher.doFinal(base64decoder.decodeBuffer(cryptoData));

        resultData = new String(oriBytes, "UTF-8");

        return resultData;

    }

    private String decrypt(String cryptoData) throws Exception {

        String resultData = null;

        BASE64Decoder base64decoder = new BASE64Decoder();
        BASE64Encoder base64encoder = new BASE64Encoder();
        byte[] keyBytes = base64decoder.decodeBuffer(this.key);
        KeyGenerator generator = KeyGenerator.getInstance(this.algorithm);
        generator.init(new SecureRandom(keyBytes));
        Key key = generator.generateKey();
        Cipher cipher = Cipher.getInstance(this.algorithm + "/" + this.encroptMode + "/" + this.paddingMode);

        cipher.init(1, key);
        byte[] encryptBytes = cipher.doFinal(cryptoData.getBytes("UTF-8"));

        resultData = base64encoder.encode(encryptBytes);
        return resultData;
    }

}
