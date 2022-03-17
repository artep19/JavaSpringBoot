package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void getAllUsers() {
        List<User> allUser = userRepository.getUsers();
        assertThat(allUser).hasSize(4);
    }

    @Test
    void findById() {
        User user = userRepository.findById(1);
        Assertions.assertEquals(user.getUsername(), "pcrmaric");
    }

    @Test
    void findByUsername() {
        User user = userRepository.findByUsername("pcrmaric");
        assertSame(user.getId(), 1L);
    }

    @Test
    void createNewUser() {
        List<User> allUsers = userRepository.getUsers();
        AtomicInteger validIdFound = new AtomicInteger();
        allUsers.forEach(user -> {
            if (user.getId() > 0) {
                validIdFound.getAndIncrement();
            }
        });
        assertThat(validIdFound.intValue()).isEqualTo(4);
    }

    @Test
    void findUserRoles() {
        userRepository.addRole(1L, "admin");
        userRepository.addRole(1L, "user");
        List<String> listRoles = userRepository.findRoles(1L);
        assertThat(listRoles).hasSize(2);
    }


    @Test
    void findUserRolesFromUsername() {
        userRepository.addRole(1L, "admin");
        userRepository.addRole(1L, "user");
        userRepository.addRole(1L, "guest");
        User user = userRepository.findById(1L);
        List<String> listRoles = userRepository.findRolesFromUsername(user.getUsername());
        assertThat(listRoles).hasSize(3);
    }


    @Test
    void addNewRole() {
        userRepository.addRole(2L, "admin");
        List<String> listOfRoles = userRepository.findRoles(2L);
        Assertions.assertEquals(listOfRoles.toString().replace("[", "").replace("]", ""), "admin");
    }


}
