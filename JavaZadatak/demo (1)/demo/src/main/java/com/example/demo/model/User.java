package com.example.demo.model;

public class User extends BaseUser {

    private Long id;

    public User() {
    }

    public User(long id, String username, String password) {
        super(username, password);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("id=%s, username=%s, password=%s ", id, getUsername(), getPassword());
    }
}
