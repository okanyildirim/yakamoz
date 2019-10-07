package com.hof.cms.writer;

import com.hof.cms.content.Content;
import com.hof.yakamozauth.entity.UserDetails;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;

@Entity
public class Writer {

    @Id
    @GeneratedValue
    private Long id;
    private UserDetails userDetails;
    private HashSet<Content> contents = new HashSet<>();
}
