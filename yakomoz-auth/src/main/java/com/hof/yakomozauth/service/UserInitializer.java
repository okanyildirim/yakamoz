package com.hof.yakomozauth.service;

import com.hof.yakomozauth.entity.User;
import com.hof.yakomozauth.entity.UserRole;
import com.hof.yakomozauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer implements InitializingBean {

    private final UserRepository userRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (userRepository.count() == 0){

            User user = new User();
            user.setEmail("hof@yakamoz.com");
            user.setPassword("hof.2019");
            user.setUsername("superadmin");
            user.addRole(UserRole.SUPER_ADMIN);
            userRepository.save(user);
        }
    }
}
