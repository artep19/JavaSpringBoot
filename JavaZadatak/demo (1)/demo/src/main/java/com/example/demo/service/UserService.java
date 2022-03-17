package com.example.demo.service;

import com.example.demo.model.BaseUser;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User findById(long id);

    User findByUsername(String username) throws Exception;

    List<String> findRoles(Long id) throws Exception;

    List<String> findRoleFromUsername(String username) throws Exception;

    List<String> findAllInfo(String username) throws Exception;

    String updatePassword(String username, String newPassword) throws Exception;

    String create(BaseUser user) throws Exception;

    String addRole(String username, String newRole) throws Exception;

}
