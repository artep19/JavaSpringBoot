package com.example.demo.service;

import com.example.demo.model.BaseUser;
import com.example.demo.model.PossibleRoles;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username)
            throws EmptyResultDataAccessException {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<String> findRoles(Long id) throws Exception {
        return userRepository.findRoles(id);
    }

    @Override
    public List<String> findRoleFromUsername(String username)
            throws EmptyResultDataAccessException {
        return userRepository.findRolesFromUsername(
                userRepository.findByUsername(username).getUsername());
    }

    @Override
    public List<String> findAllInfo(String username) {
        return userRepository.findAllUserInfo(
                userRepository.findByUsername(username).getUsername());
    }

    @Override
    public String updatePassword(String username, String newPassword) {
        if (newPassword.length() > 5) {
            userRepository.updatePassword(
                    userRepository.findByUsername(
                            username).getUsername(), newPassword);
            return "Password updated successfully";
        } else {

            return "Password must be min 5 characters long";
        }
    }

    @Override
    public String create(BaseUser user) throws IllegalArgumentException {
        List<String> listUsernames = userRepository.getAllUsernames();
        if (!listUsernames.contains(user.getUsername())) {
            userRepository.create(user);
            return "Created";
        } else {
            return "Not created";
        }
    }

    @Override
    public String addRole(String username, String newRole)
            throws IllegalArgumentException {
        List<PossibleRoles> possibleRoles = Arrays.asList(PossibleRoles.values());
        if (possibleRoles.contains(
                PossibleRoles.valueOf(newRole.trim().toUpperCase(Locale.ROOT)))
        ) {
            List<String> existingRolesList = userRepository.findRolesFromUsername(username);
            if (existingRolesList.contains(newRole)) {
                return "Already exists";
            }
            userRepository.addRole(
                    userRepository.findByUsername(username).getId(), newRole.trim()
            );
            return "Role created";
        } else {

            return "Can't add that role";
        }
    }
}
