package com.example.demo;

import com.example.demo.model.BaseUser;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void savedUserSuccess() {
        User user = new User();
        user.setId(1L);
        user.setUsername("p");
        user.setPassword("123456");

        when(userRepository.create(any(User.class))).thenReturn(user);
        BaseUser savedUser = userRepository.create(user);
        assertThat(savedUser.getUsername()).isNotNull();
    }


    @Test
    public void userExistsInDbSuccess() {
        User user = new User();
        user.setId(1L);
        user.setUsername("p");
        user.setPassword("123");
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userRepository.getUsers()).thenReturn(userList);

        List<User> allUsers = userService.getUsers();
        assertThat(allUsers.size()).isGreaterThan(0);
    }

}
