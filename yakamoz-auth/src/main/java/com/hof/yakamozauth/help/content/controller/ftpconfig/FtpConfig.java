package com.hof.yakamozauth.help.content.controller.ftpconfig;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CMS_FTP_CONFIG")
public class FtpConfig extends AbstractEntity {

	@Id
	@SequenceGenerator(name = "seq_ftp_config_generator", sequenceName = "seq_ftp_config")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_content_poster_generator")
	private Long id;
	
	@NotNull
	private String host;
	
	@NotNull
	private String port;
	
	@NotNull
	private String username;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@NotNull
	private PrefferedAuthentication prefferedAuthentication;
	
	@NotNull
	private String folderDirectory;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@NotNull
	private FtpType ftpType;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(unique = true)
	private FtpOperationType operationType;

	private String password;
	private String identityKey;
	private String knownHost;
	
	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public PrefferedAuthentication getPrefferedAuthentication() {
		return prefferedAuthentication;
	}

	public void setPrefferedAuthentication(PrefferedAuthentication prefferedAuthentication) {
		this.prefferedAuthentication = prefferedAuthentication;
	}

	public String getFolderDirectory() {
		return folderDirectory;
	}

	public void setFolderDirectory(String folderDirectory) {
		this.folderDirectory = folderDirectory;
	}

	public FtpType getFtpType() {
		return ftpType;
	}

	public void setFtpType(FtpType ftpType) {
		this.ftpType = ftpType;
	}

	public FtpOperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(FtpOperationType operationType) {
		this.operationType = operationType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String user) {
		this.username = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdentityKey() {
		return identityKey;
	}

	public void setIdentityKey(String identityKey) {
		this.identityKey = identityKey;
	}

	public String getKnownHost() {
		return knownHost;
	}

	public void setKnownHost(String knownHost) {
		this.knownHost = knownHost;
	}
}
