package com.hof.yakamozauth.help.content.controller.ftpconfig;

import lombok.Data;

@Data
public class FtpConfigResponse {
	private Long id;
	private String host;
	private String port;
	private String username;
	private PrefferedAuthentication prefferedAuthentication;
	private String password;
	private String identityKey;
	private String knownHost;
	private String folderDirectory;
	private FtpType ftpType;
	private FtpOperationType operationType;


	public FtpConfigResponse entityParseToFtpResponse(FtpConfig entity){

		this.setId(entity.getId());
		this.setHost(entity.getHost());
		this.setPort(entity.getPort());
		this.setUsername(entity.getUsername());
		this.setPrefferedAuthentication(entity.getPrefferedAuthentication());
		this.setFtpType(entity.getFtpType());
		this.setPassword(entity.getPassword());
		this.setIdentityKey(entity.getIdentityKey());
		this.setKnownHost(entity.getKnownHost());
		this.setFolderDirectory(entity.getFolderDirectory());
		this.setOperationType(entity.getOperationType());

		return this;
	}
}
