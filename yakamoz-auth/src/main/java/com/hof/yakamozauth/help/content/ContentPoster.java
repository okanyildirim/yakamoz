package com.hof.yakamozauth.help.content;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "CMS_CONTENT_POSTER", uniqueConstraints = @UniqueConstraint(columnNames = {"CONTENT_ID", "POSTER_DEFINITION_ID", "LANGUAGE_ID"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContentPoster extends LingualContentEntity {
	
	@Id
	@SequenceGenerator(name = "seq_content_poster_generator", sequenceName = "seq_content_poster")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_content_poster_generator")
	private Long id;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_id", foreignKey = @ForeignKey(name = "FK_CONTENT_POSTER_LANGUAGE"), nullable = false)
	private Language language;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "poster_definition_id", foreignKey = @ForeignKey(name = "FK_CONTENT_POSTER_DEF"), nullable = false)
	private PosterDefinition posterDefinition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENT_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_POSTER_CONTENT"), nullable = false)
	private Content content;
	
	private String guid;
	
	private String url;
	
	private String externalUrl;
	
	private String fileName;
	
	private String fileSize;
	
	public ContentPoster() {
		this.guid = UUID.randomUUID().toString();
	}

	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public PosterDefinition getPosterDefinition() {
		return posterDefinition;
	}

	public void setPosterDefinition(PosterDefinition posterDefinition) {
		this.posterDefinition = posterDefinition;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
