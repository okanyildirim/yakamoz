package com.hof.cms.cmsuser.service;

import com.hof.cms.cmsuser.entity.CmsUser;
import com.hof.cms.cmsuser.entity.CmsUserRole;
import com.hof.cms.cmsuser.repository.CmsUserRepository;
import com.hof.cms.writer.entity.PersonalDetails;
import lombok.RequiredArgsConstructor;
import org.omg.CORBA.PERSIST_STORE;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CmsUserInitializer implements InitializingBean {

    private final CmsUserRepository userRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (userRepository.count() == 0){

            CmsUser cmsUser = new CmsUser();
            cmsUser.setEmail("hof@yakamoz.com");
            cmsUser.setPassword("hof.2019");
            cmsUser.setUsername("superadmin");
            cmsUser.addRole(CmsUserRole.SUPER_ADMIN);

            PersonalDetails personalDetails = new PersonalDetails();
            personalDetails.setFirstName("HOF");
            personalDetails.setLastName("SUPER ADMIN");
            cmsUser.setPersonalDetails(personalDetails);
            userRepository.save(cmsUser);
        }
    }
}
