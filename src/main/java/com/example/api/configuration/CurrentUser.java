package com.example.api.configuration;

import java.util.Set;

import org.springframework.security.core.authority.AuthorityUtils;

import com.example.api.model.Role;
import com.example.api.model.User;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return user.getId();
    }

    public Set<Role> getRole() {
        return user.getRoles();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
