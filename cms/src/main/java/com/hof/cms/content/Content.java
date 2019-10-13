package com.hof.cms.content;

import com.hof.cms.writer.entity.Writer;

import javax.persistence.*;

@Entity
@Table(name = "CMS_CONTENT")
public class Content {

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String body;
	@Enumerated(EnumType.STRING)
	private ContentType contentType;

	@OneToOne
	private Writer writer;

	public Content() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}
}
