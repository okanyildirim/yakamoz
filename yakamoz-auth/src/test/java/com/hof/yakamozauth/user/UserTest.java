package com.hof.yakamozauth.user;

import com.hof.yakamozauth.controller.UserController;
import com.hof.yakamozauth.data.UserMapper;
import com.hof.yakamozauth.data.UserResponse;
import com.hof.yakamozauth.entity.User;
import com.hof.yakamozauth.entity.UserRole;
import com.hof.yakamozauth.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
    User user = new User();

    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;

    @Before
    public void setUp(){
        user.setEmail("okan@email.com");
        user.setUsername("okidoki");
        user.setPassword("1234567");
        user.getRoles().add(UserRole.CLIENT);
    }

    @Test
    public void testUserName(){
        assertEquals("okidoki",user.getUsername());

    }

    @Test
    public void testGetAllUser(){

        List<UserResponse> users = new ArrayList<>();
        users.add(UserMapper.userToUserResponse(user));
        when(userService.getAllUsers()).thenReturn(users);
        assertEquals(1,users.size());
    }
}
