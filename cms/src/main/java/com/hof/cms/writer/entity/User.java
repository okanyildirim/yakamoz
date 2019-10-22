package com.hof.cms.writer.entity;

import com.hof.cms.content.Content;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends Writer {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    @Email
    private String email;
    private String password;

    private HashSet<Content> contents = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();

    public void addRole(UserRole role){
        this.roles.add(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashSet<Content> getContents() {
        return contents;
    }

    public void setContents(HashSet<Content> contents) {
        this.contents = contents;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}

