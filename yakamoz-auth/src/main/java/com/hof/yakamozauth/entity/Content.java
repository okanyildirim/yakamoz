package com.hof.yakamozauth.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Content {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ContentState state;

    public Content() {
        this.state = ContentState.IN_PROGRESS;
    }

    public ContentState getState() {
        return state;
    }

    public void setState(ContentState state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
