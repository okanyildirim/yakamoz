package com.hof.cms.content;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.AbstractDocument;

public interface ContentRepository extends JpaRepository<Content,Long> {
}
