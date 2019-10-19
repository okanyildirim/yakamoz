package com.hof.yakamozauth.help.content;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "CMS_POSTER_DEFINITION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PosterDefinition extends AbstractEntity {

	@Id
	@SequenceGenerator(name = "seq_poster_definition_generator", sequenceName = "seq_poster_definition")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_poster_definition_generator")
	private Long id;

	private String name;

	private Integer width;

	private Integer height;

	@Column(name = "MAX_SIZE_IN_KB", nullable = false)
	private Integer maxSize;

	private Boolean defaultPoster;

	private Integer posterType;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "cms_poster_def_asset_type")
	@Enumerated(EnumType.STRING)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<AssetType> assetTypes = new HashSet<AssetType>();

	@Override
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

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Boolean getDefaultPoster() {
		return defaultPoster;
	}

	public void setDefaultPoster(Boolean defaultPoster) {
		this.defaultPoster = defaultPoster;
	}

	public Integer getPosterType() {
		return posterType;
	}

	public void setPosterType(Integer posterType) {
		this.posterType = posterType;
	}

	public Set<AssetType> getAssetTypes() {
		return assetTypes;
	}

	public void setAssetTypes(Set<AssetType> assetTypes) {
		this.assetTypes = assetTypes;
	}


}
