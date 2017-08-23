/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: FtpFileActon.java
 * @package org.platform.core.action.ftp
 * @author leise
 * @date 2016年5月30日 下午12:21:38
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.action.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.platform.core.action.Action;
import org.platform.core.action.enums.FtpType;
import org.platform.core.annotation.action.FtpAction;
import org.platform.core.comm.ftp.resource.FtpResource;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;
import org.platform.core.utils.ActionAnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @classname: FtpFileActon
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class FtpFileAction implements Action {

	private static final Logger logger = LoggerFactory
			.getLogger(FtpFileAction.class);

	@Autowired
	private List<? extends FtpResource> ftpResources;

	private static String OS = System.getProperty("os.name");

	private static String ENCODING = System.getProperty("file.encoding");

	public void execute(String actionId, String declareClassName,
			String localFileName, String remoteFileName) throws PFException {

		logger.info("[执行action开始]--[actionId:{}]--[调用方:{}]------", actionId,
				declareClassName);

		Assert.isTrue(
				StringUtils.isNotBlank(localFileName)
						&& StringUtils.isNotBlank(remoteFileName),
				"the localFileName or remoteFileName must be not blank");
		logger.info("[actionId:{}]--[调用方:{}]--[本地文件名称:{}, 远程文件名称:{}]]",
				actionId, declareClassName, localFileName, remoteFileName);

		FtpAction ftpAction = ActionAnnotationUtils.getAnnotation(
				declareClassName, actionId, FtpAction.class);
		Assert.notNull(ftpAction, "the FtpAction config must be not null");

		String actionName = ftpAction.name();
		logger.info("[actionId:{}]--[调用方:{}]--[actionName:{}]", actionId,
				declareClassName, actionName);

		FtpType ftpType = ftpAction.ftpType();
		logger.info("[actionId:{}]--[调用方:{}]--[FTP操作类型:{}]", actionId,
				declareClassName, ftpType);

		Class<? extends FtpResource> ftpResourceClass = ftpAction.ftpResource();
		FtpResource ftpResource = null;
		for (FtpResource res : ftpResources) {
			if (ftpResourceClass.getName().equals(res.getClass().getName())) {
				ftpResource = res;
				break;
			}
		}
		Assert.notNull(ftpResource, "the ftp resource must be not null");

		FTPClient ftpClient = getFtpClient();

		boolean isConnected = doFtpConnect(ftpClient, ftpResource, ftpType);

		Assert.isTrue(isConnected, "the ftpClient connect ftp server failed!");

		String remotePath = StringUtils.isNotBlank(ftpAction.remotePath()) ? ftpAction
				.remotePath() : ftpResource.getRemotePath();
		String localPath = StringUtils.isNotBlank(ftpAction.localPath()) ? ftpAction
				.localPath() : ftpResource.getLocalPath();
		Assert.isTrue(StringUtils.isNotBlank(remotePath)
				&& StringUtils.isNotBlank(localPath));

		String localFileUri = new StringBuffer(localPath).append("/")
				.append(localFileName).toString();

		try {

			boolean result = false;

			result = ftpClient.changeWorkingDirectory(remotePath);

			switch (ftpType) {
			case UPLOAD:
				if (!result) {
					buildDirs(ftpClient, remotePath);
				}
				result = doFileUpload(ftpClient, localFileUri, remoteFileName);
				break;

			case DOWNLOAD:
				result = doFileDownload(ftpClient, localFileUri, remoteFileName);
				break;

			default:
				return;

			}

			logger.info("[actionId:{}]--[调用方:{}]--[文件操作结果:{}]", actionId,
					declareClassName, result ? "success" : "failed");
			Assert.isTrue(result, "ftp file failed!");

		} catch (Exception e) {
			throw ExceptionBuilder.newException()
					.withError(ErrorCode.ACTION_EXECUTE_FAILD)
					.withMessageArgs(new Object[] { "FTP文件数据" }).withCause(e)
					.build();
		}

		logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]------", actionId,
				declareClassName);

	}

	/**
	 * 
	 * @title: doFtpConnect
	 * @author leise
	 * @description:
	 * @date 2016年5月31日 上午9:52:37
	 * @throws
	 */
	private boolean doFtpConnect(FTPClient ftpClient, FtpResource ftpResource,
			FtpType ftpType) {

		boolean isConnected = false;

		String url = ftpResource.getUrl();
		Integer port = ftpResource.getPort();
		String user = ftpResource.getUser();
		String password = ftpResource.getPassword();

		Assert.notNull(url);
		Assert.notNull(port);
		Assert.notNull(user);
		Assert.notNull(password);

		try {

			if (port < 0) {
				ftpClient.connect(url);
			} else {
				ftpClient.connect(url, port);
			}

			ftpClient.login(user, password);
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				ftpClient.disconnect();
				return isConnected;
			}

			isConnected = true;
		} catch (Exception e) {
			e.printStackTrace();
			isConnected = false;
		}

		return isConnected;

	}

	/**
	 * 
	 * @title: doFileUpload
	 * @author leise
	 * @description:
	 * @date 2016年5月31日 上午10:50:38
	 * @throws
	 */
	private boolean doFileUpload(FTPClient ftpClient, String localFileUri,
			String remoteFileName) throws Exception {

		boolean result = false;

		File localFile = new File(localFileUri);
		if (!localFile.exists()) {
			return result;
		}
		InputStream in = null;
		try {
			in = new FileInputStream(localFile);
			ftpClient.storeFile(remoteFileName, in);
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		} finally {

			this.clear(in, ftpClient);
		}

		return result;
	}

	private boolean doFileDownload(FTPClient ftpClient, String localFileUri,
			String remoteFileName) throws Exception {

		boolean result = false;
		OutputStream outputStream = null;
		try {
			FTPFile[] ftpFiles = ftpClient.listFiles();

			for (FTPFile ftpFile : ftpFiles) {
				if (ftpFile.getName().equals(remoteFileName)) {
					File localFile = new File(localFileUri);
					outputStream = new FileOutputStream(localFile);
					ftpClient.retrieveFile(remoteFileName, outputStream);
					outputStream.close();
				}
			}

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {

			if (outputStream != null) {
				outputStream.close();
			}

			this.clear(null, ftpClient);

		}

		return result;

	}

	private void clear(InputStream in, FTPClient ftpClient) {
		if (in != null) {
			// 关闭输入流
			try {
				in.close();
			} catch (IOException e) {
			}
		}

		if (ftpClient != null) {

			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {

				}
			}

			try {
				ftpClient.logout();
			} catch (IOException e) {
			}
		}
	}

	public void buildDirs(FTPClient ftpClient, String remoteFilePath)
			throws Exception {
		String[] dirs = remoteFilePath.split("/");
		for (String dir : dirs) {
			ftpClient.mkd(dir);
		}
		ftpClient.changeWorkingDirectory(remoteFilePath);
	}

	private FTPClient getFtpClient() {

		FTPClient ftpClient = new FTPClient();
		ftpClient.configure(getClientConfig());
		ftpClient.setControlEncoding(ENCODING);
		try {
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ftpClient.setBufferSize(3072);
		return ftpClient;
	}

	private FTPClientConfig getClientConfig() {
		String systemType = null;
		if (isLinux()) {
			systemType = FTPClientConfig.SYST_UNIX;
		} else if (isWindows()) {
			systemType = FTPClientConfig.SYST_NT;
		}
		FTPClientConfig config = new FTPClientConfig(systemType);
		config.setRecentDateFormatStr("yyyy-MM-dd HH:mm");
		return config;
	}

	private boolean isLinux() {
		return OS.indexOf("linux") >= 0;
	}

	private boolean isWindows() {
		return OS.indexOf("windows") >= 0;
	}

}
