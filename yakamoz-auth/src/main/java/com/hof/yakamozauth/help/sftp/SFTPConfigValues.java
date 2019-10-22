package com.hof.yakamozauth.help.sftp;

public interface SFTPConfigValues {

	String getSftpUsername();

	String getSftpHost();

	int getSftpPort();

	String getSftpPassword();

	String getSftpPreferredAuthentications();

	Integer getSftpConnectionTimeout();

	String getSftpKnownHost();

	String getSftpIdentityKey();
}
