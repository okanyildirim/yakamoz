package com.hof.yakamozauth.help.content.controller.ftpconfig;

import lombok.Data;

@Data
public class FtpConfigRequest {

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

	public void validate() {

		Utility.notNullAndLessThan("Host",this.getHost(),1,50);
		Utility.notNullAndLessThan("Port",this.getPort(),1,5);
		Utility.notNullAndLessThan("Directory",this.getFolderDirectory(),1,100);
		Utility.notNullAndLessThan("User",this.getUsername(),1,100);
		Utility.notNullAndLessThan("Preferred Authentication",this.getPrefferedAuthentication().name(),1,100);
		Utility.notNullAndLessThan("Ftp Type",this.getFtpType().toString(),1,50);
		Utility.notNullAndLessThan("Operation Type", this.getOperationType().toString(),1,50);

		if(this.getPrefferedAuthentication().equals(PrefferedAuthentication.PASSWORD)){
			Utility.notNullAndLessThan("Password",this.getPassword(),1,100);
			this.setIdentityKey(null);
			this.setKnownHost(null);
		}

		if (this.getPrefferedAuthentication().equals(PrefferedAuthentication.PRIVATE_KEY)){
			Utility.notNullAndLessThan("Identity Key",this.getIdentityKey(),1,100);
			Utility.notNullAndLessThan("Known Host",this.getKnownHost(),1,100);
			this.setPassword(null);
		}
	}
}
