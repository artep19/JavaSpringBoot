package com.example.demo.model;

public class Role {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format(" roles=%s ", getRole());
    }


}
