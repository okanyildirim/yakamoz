package com.hof.yakamozauth.help.service.model;


import com.hof.yakamozauth.help.service.model.User;
import com.hof.yakamozauth.help.service.CmsUserDocument;

import java.util.Set;
import java.util.stream.Collectors;

public class UserAssembler {

    public User writeEntity(UserRegistrationRequest userRegistrationRequest) {
        Utility.notNullAndLessThan("Firstname", userRegistrationRequest.getFirstName(), 3, 50);
        Utility.notNullAndLessThan("Lastname", userRegistrationRequest.getLastName(), 3, 50);
        String password = userRegistrationRequest.getPassword();
        Utility.notNullAndLessThan("Password", password, 6, 100);
        // userRegistrationRequest.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        Utility.validateMail(userRegistrationRequest.getEmail());
        // TODO: check phone number too

        return assembleUser(userRegistrationRequest);
    }

    public void patchEntity(User user, UserUpdateRequest userRegistrationRequest) {
        String firstName = userRegistrationRequest.getFirstName();
        if (firstName != null) {
            Utility.lessThan("Firstname", firstName.length(), 3, 50);
            // user.setFirstName(firstName);
        }
        String lastName = userRegistrationRequest.getLastName();
        if (lastName != null) {
            Utility.lessThan("Lastname", lastName.length(), 3, 50);
            // user.setLastName(lastName);
        }
        String password = userRegistrationRequest.getPassword();
        if (password != null) {
            Utility.lessThan("Password", password.length(), 6, 100);
            //  user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        }
        String email = userRegistrationRequest.getEmail();
        if (email != null) {
            Utility.validateMail(email);
            user.setEmail(email);
        }

        String addressLine = userRegistrationRequest.getAddressLine();
        if (addressLine != null) {
            Utility.lessThan("Address", addressLine.length(), 10, 100);
            //user.setAddressLine(addressLine);
        }

        String phoneNumber = userRegistrationRequest.getPhoneNumber();
        if (phoneNumber != null) {
            //  user.setPhoneNumber(phoneNumber);
        }

        Boolean isValid = userRegistrationRequest.getIsValid();
        if (isValid != null) {
            // user.setIsValid(isValid);
        }

        Set<com.hof.yakamozauth.help.service.model.UserRole> userRoles = userRegistrationRequest.getRoles();
        if (userRoles != null && !userRoles.isEmpty()) {
            user.getRoles().clear();
            // ConcurrentModificationException exc
            userRegistrationRequest.getRoles().forEach(user::addRole);
        }
    }

    private com.hof.yakamozauth.help.service.model.User assembleUser (UserRegistrationRequest userRegistrationRequest) {

        com.hof.yakamozauth.help.service.model.User user = new com.hof.yakamozauth.help.service.model.User();

        user.setFirstName(userRegistrationRequest.getFirstName());
        user.setLastName(userRegistrationRequest.getLastName());
        user.setEmail(userRegistrationRequest.getEmail());
        user.setAddressLine(userRegistrationRequest.getAddressLine());
        user.setPhoneNumber(userRegistrationRequest.getPhoneNumber());
        user.setPassword(userRegistrationRequest.getPassword());
        userRegistrationRequest.getRoles().forEach(user::addRole);

        return user;
    }

    public UserRegistrationResponse writeRegistrationResponse(User user, String tenantCountryCode) {
        UserRegistrationResponse response =  new UserRegistrationResponse();
        CmsUserDocument cmsUser = new CmsUserDocument();
        cmsUser.setIsActive(user.getActive());
        cmsUser.setAddressLine(user.getAddressLine());
        cmsUser.setEmail(user.getEmail());
        cmsUser.setFirstName(user.getFirstName());
        cmsUser.setLastName(user.getLastName());
        cmsUser.setPhoneNumber(user.getPhoneNumber());
        cmsUser.setId(user.getId());
        cmsUser.setIsValid(user.getValid());
        cmsUser.setRoles(user.getRoles().stream().collect(Collectors.toSet()));
        cmsUser.setTenantCountryCode(tenantCountryCode);
        response.setCmsUser(cmsUser);
        return response;
    }
}
