package com.hof.cms.cmsuser.entity;

import com.hof.cms.writer.entity.PersonalDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CmsUser {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_DETAILS_ID", foreignKey = @ForeignKey(name = "FK_USER_USER_DETAILS"))
    private PersonalDetails personalDetails;

    @Email
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<CmsUserRole> roles = new HashSet<>();

    public void addRole(CmsUserRole role){
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

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
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

    public Set<CmsUserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<CmsUserRole> roles) {
        this.roles = roles;
    }
}

