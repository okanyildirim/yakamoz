package com.hof.yakamozauth.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Long gsmNo;
    @OneToOne
    private User user;
}
