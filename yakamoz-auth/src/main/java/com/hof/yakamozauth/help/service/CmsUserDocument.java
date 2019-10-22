package com.hof.yakamozauth.help.service;

import com.hof.yakamozauth.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserDocument implements Serializable {
	
	private static final long serialVersionUID = 1076445923127551961L;
	
	private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String addressLine;
    private String phoneNumber;
    private Boolean isValid;
    private Boolean isActive;
    private String tenantCountryCode;
    private Set<UserRole> roles;

}
