package com.example.demo.repository;

import com.example.demo.model.BaseUser;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * FROM USER", getUsersRowMapper());
    }

    @Override
    public List<String> getAllUsernames() {
        return jdbcTemplate.query(
                "SELECT username FROM user", getUsernamesRowMapper());
    }

    @Override
    public User findById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM user WHERE id=?",
                getUsersRowMapper(), id)).orElseThrow(() ->
                new NoSuchElementException("User not found - "));
    }

    @Override
    public User findByUsername(String username) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM USER WHERE username=?",
                getUsersRowMapper(), username)).orElseThrow(() ->
                new NoSuchElementException("User not found - "));
    }

    @Override
    public List<String> findRoles(Long id) {
        User user = findById(id);
        return jdbcTemplate.query(
                "SELECT rolename FROM role WHERE user_id=?",
                getRoleRowMapper(), user.getId());
    }

    @Override
    public List<String> findRolesFromUsername(String username) {
        return jdbcTemplate.query(
                "select rolename from role left join user on user.id=role.user_id "
                        + "WHERE user.username=?", getRoleRowMapper(), username);
    }

    @Override
    public void addRole(Long id, String role) {
        User user = findById(id);
        jdbcTemplate.update(
                "INSERT INTO role(rolename,user_id) VALUES (?,?)",
                role, user.getId());
    }

    @Override
    public BaseUser create(BaseUser user) {
        jdbcTemplate.update("INSERT INTO user (username, password) VALUES(?,?)",
                user.getUsername(), user.getPassword());
        return user;
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        jdbcTemplate.update("UPDATE user SET password=? WHERE username=?",
                newPassword, username);
    }

    @Override
    public List<String> findAllUserInfo(String username) {
        return jdbcTemplate.query(
                "SELECT user.id, user.username, user.password,"
                        + " userinformation.first_name,"
                        + " userinformation.last_name, userinformation.date_of_birth,"
                        + " STRING_AGG(rolename, ', ') "
                        + " from role left join user"
                        + " on user.id=role.user_id"
                        + " inner join userinformation"
                        + " on role.user_id=userinformation.id"
                        + " WHERE user.username=?", getAllUserInfoRowMapper(), username);
    }

    private RowMapper<String> getRoleRowMapper() {
        return (rs, rowNum) -> {
            Role role = new Role();
            role.setRole(rs.getString(1).trim());
            return role.getRole();
        };
    }

    private RowMapper<User> getUsersRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong(1));
            user.setUsername(rs.getString(2));
            user.setPassword(rs.getString(3));
            return user;
        };
    }

    private RowMapper<String> getUsernamesRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUsername(rs.getString(1));
            return user.getUsername();
        };
    }

    private RowMapper<String> getAllUserInfoRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            UserInformation userInfo = new UserInformation();
            Role role = new Role();
            user.setId(rs.getLong(1));
            user.setUsername(rs.getString(2));
            user.setPassword(rs.getString(3));
            userInfo.setFirstName(rs.getString(4));
            userInfo.setLastName(rs.getString(5));
            userInfo.setDateOfBirth(rs.getDate(6));
            role.setRole(rs.getString(7));
            return user.toString() + userInfo + role;
        };
    }
}
