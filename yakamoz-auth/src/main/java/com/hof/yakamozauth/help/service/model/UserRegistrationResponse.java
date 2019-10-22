package com.hof.yakamozauth.help.service.model;

import com.hof.yakamozauth.help.service.CmsUserDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponse implements Serializable {

	private static final long serialVersionUID = 6645488861845455088L;
	
	private CmsUserDocument cmsUser;
    
}
