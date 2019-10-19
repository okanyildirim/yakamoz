package com.hof.yakamozauth.help.content.controller.ftpconfig;

import com.hof.yakamozauth.help.sftp.SFTPConfigValues;
import lombok.Data;

@Data
public class FtpTestConfigValues implements SFTPConfigValues {
	private String sftpHost;
	private int sftpPort;
	private String sftpUsername;
	private String sftpHLSIngestURL;
	private String sftpPassword;
	private String sftpKnownHost;
	private String sftpIdentityKey;
	private String sftpPreferredAuthentications;
	private Integer sftpConnectionTimeout;
	private String sftpFileUploadFolder;

}
