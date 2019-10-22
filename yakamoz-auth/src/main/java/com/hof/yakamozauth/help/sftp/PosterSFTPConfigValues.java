package com.hof.yakamozauth.help.sftp;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PosterSFTPConfigValues implements SFTPConfigValues {
	@Value("${sftp.poster.host}")
	private String sftpHost;

	@Value("${sftp.poster.port}")
	private int sftpPort;

	@Value("${sftp.poster.username}")
	private String sftpUsername;

	@Value("${sftp.poster.password}")
	private String sftpPassword;

	@Value("${sftp.poster.known.host}")
	private String sftpKnownHost;

	@Value("${sftp.poster.identity.key}")
	private String sftpIdentityKey;

	@Value("${sftp.poster.preferred.authentications}")
	private String sftpPreferredAuthentications;

	@Value("${sftp.connection.timeout}")
	private Integer sftpConnectionTimeout;

	@Value("${sftp.poster.upload.folder}")
	private String sftpPosterUploadFolder;

	@Value("${sftp.poster.view.folder}")
	private String sftpPosterViewFolder;
}
