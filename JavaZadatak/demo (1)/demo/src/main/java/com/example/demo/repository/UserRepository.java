package com.example.demo.repository;

import com.example.demo.model.BaseUser;
import com.example.demo.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();

    List<String> getAllUsernames();

    User findById(long id);

    User findByUsername(String username);

    BaseUser create(BaseUser user);

    void updatePassword(String username, String password);

    List<String> findAllUserInfo(String username);

    void addRole(Long id, String role);

    List<String> findRoles(Long id);

    List<String> findRolesFromUsername(String username);

}
