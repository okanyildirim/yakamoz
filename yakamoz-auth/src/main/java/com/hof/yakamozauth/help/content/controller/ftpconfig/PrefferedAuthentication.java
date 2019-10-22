package com.hof.yakamozauth.help.content.controller.ftpconfig;

public enum PrefferedAuthentication {
	PASSWORD("PASSWORD"), PRIVATE_KEY("PUBLIC KEY");

	private String value;

	PrefferedAuthentication(String value) {
		this.value = value;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}

}
