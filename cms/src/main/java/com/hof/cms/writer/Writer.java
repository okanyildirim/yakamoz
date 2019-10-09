package com.hof.cms.writer;

import com.hof.cms.content.Content;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Writer {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSONAL_DETAILS_ID", foreignKey = @ForeignKey(name = "FK_USER_PERSONAL_DETAILS"))
    private PersonalDetails personalDetails;
    private HashSet<Content> contents = new HashSet<>();

    public Writer() {
    }

    public Writer(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public HashSet<Content> getContents() {
        return contents;
    }

    public void setContents(HashSet<Content> contents) {
        this.contents = contents;
    }

    public void  addContent(Content content){
        this.contents.add(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return Objects.equals(id, writer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
