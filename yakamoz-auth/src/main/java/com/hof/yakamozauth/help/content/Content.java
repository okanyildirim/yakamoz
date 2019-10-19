package com.hof.yakamozauth.help.content;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "CMS_CONTENT")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Content extends AuditableEntity implements PublishablePointEntity {

	private static final long serialVersionUID = -9053288151164929900L;

	@Id
	@SequenceGenerator(name = "seq_content_generator", sequenceName = "seq_content")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_content_generator")
	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ContentType contentType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PublishPoint publishPoint = PublishPoint.PROD;
	
	private String guid;

	@Column(nullable = false)
	private String name;

	private Boolean isLocal;

	private String externalId;

	@Enumerated(EnumType.STRING)
	private VodType vodType;

	private Integer releaseYear;

	private String imdbID;

	private String studio;

	@Enumerated(EnumType.STRING)
	private ContentBOCategory contentBOCategory;

	private Long duration;

	@Enumerated(EnumType.STRING)
	private ContentState state;

	@Enumerated(EnumType.STRING)
	private ScreenFormat screenFormat;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "CMS_CONTENT_DEFINITION")
	@Enumerated(EnumType.STRING)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Definition> definitions = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "encoding_process_id", foreignKey = @ForeignKey(name = "FK_CONTENT_ENCODING_PROCESS"))
	private EncodingParameters encodingParameters;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "raw_media_data_id", foreignKey = @ForeignKey(name = "FK_CONTENT_RAW_MEDIA_DATA"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private RawMediaData rawMediaData;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "content", cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<VODOperation> vodOperations = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MATURITY_LEVEL_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_MATURITY_LEVEL"))
	private MaturityLevel maturityLevel;

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = Country.class, cascade = CascadeType.DETACH)
	@JoinTable(name = "CMS_CONTENT_PRODUCTION_COUNTRY",
			joinColumns = {@JoinColumn(name = "CNT_ID", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "CN_ID", nullable = false, updatable = false)})
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Country> countries = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "content", cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<ContentPoster> posters = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<ContentTitleDescriptionLanguage> contentTitleDescriptionLanguages = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CMS_CONTENT_LICENCE",
	joinColumns = @JoinColumn(name = "CONTENT_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_LICENCE_CONTENT")),
	inverseJoinColumns = @JoinColumn(name = "LICENCE_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_LICENCE_LICENCE")),
	uniqueConstraints = @UniqueConstraint(columnNames = {"CONTENT_ID", "LICENCE_ID"}))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Licence> licences = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE,
			CascadeType.REMOVE
	})
	@JoinTable(name = "CMS_CONTENT_CATEGORY",
	joinColumns = @JoinColumn(name = "CONTENT_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_CATEGORY_CONTENT")),
	inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_CATEGORY_CATEGORY")),
	uniqueConstraints = @UniqueConstraint(columnNames = {"CONTENT_ID", "CATEGORY_ID"}))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Category> categories = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<ContentAudioSubtitleLanguage> contentAudioSubtitles = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<ContentCast> contentCasts= new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<ContentPlayUrl> contentPlayUrls = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CMS_CONTENT_GENRE",
			joinColumns = @JoinColumn(name = "CONTENT_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_GENRE_CONTENT")),
			inverseJoinColumns = @JoinColumn(name = "GENRE_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_GENRE_GENRE")),
			uniqueConstraints = @UniqueConstraint(columnNames = {"GENRE_ID", "CONTENT_ID"}))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Genre> genres = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CMS_CONTENT_PRNTL_ADV",
			joinColumns = @JoinColumn(name = "CONTENT_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_PRNTL_ADV_CONTENT")),
			inverseJoinColumns = @JoinColumn(name = "PARENTAL_ADVISORY_ID", foreignKey = @ForeignKey(name = "FK_CONTENT_PRNTL_ADV_PRNTL_ADV")),
			uniqueConstraints = @UniqueConstraint(columnNames = {"PARENTAL_ADVISORY_ID", "CONTENT_ID"}))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<ParentalAdvisory> parentalAdvisories = new HashSet<>();

	public Content() {
		this.state = ContentState.IN_PROGRESS;
		this.guid = UUID.randomUUID().toString();
	}

	public Content(ContentType contentType) {
		this.contentType = contentType;
		this.state = ContentState.IN_PROGRESS;
		this.guid = UUID.randomUUID().toString();
	}

	public Notification nextState() {
		return this.state.getState().next(this);
	}

	public Notification previousState() {
		return this.state.getState().previous(this);
	}

	public void update(ContentType contentType,
			String title, Integer releaseYear,
			String imdb, String studio,
			ContentBOCategory boCategory, Long duration,
			ScreenFormat screenFormat, PublishPoint publishPoint, Boolean isLocal, MaturityLevel maturityLevel, Set<Definition> definitions, VodType vodType, String externalId) {
		this.state.getState().update(this, contentType, title, releaseYear,
				imdb, studio, boCategory, duration, screenFormat, publishPoint, isLocal, maturityLevel, definitions, vodType, externalId);
	}

	public void update(ContentType contentType, String title, PublishPoint publishPoint){
		this.contentType = contentType;
		this.name = title;
		this.setPublishPoint(publishPoint);
	}

	public void addLicence(Licence licence) {
		this.state.getState().addLicence(this, licence);
	}

	public void removeLicence(Licence licence) {
		this.state.getState().removeLicence(this, licence);
	}

	public void fillEncodingParameters(EncodingParameters encodingParameters) {
		this.state.getState().fillEncodingParameters(this, encodingParameters);
	}

	public void addCategory(Category category) {
		this.state.getState().addCategory(this, category);
	}

	public void addCountry(Country country) {
		this.state.getState().addCountry(this, country);
	}

	public void removeCountry(Country country) {
		this.state.getState().removeCountry(this, country);
	}

	public void addContentTitleDescriptionLanguage(ContentTitleDescriptionLanguage contentTitleDescriptionLanguage) {
		this.state.getState().addTitleDescriptionLanguage(this, contentTitleDescriptionLanguage);
	}

	public void addPoster(ContentPoster contentPoster) {
		this.state.getState().addPoster(this, contentPoster);
	}

	public void removePoster(ContentPoster contentPoster) {
		this.state.getState().removePoster(this, contentPoster);
	}

	public void fillDefinitions(Set<Definition> definitions) {
		this.state.getState().fillDefinitions(this, definitions);
	}

	public void fillMaturityLevel(MaturityLevel maturityLevel) {
		this.state.getState().fillMaturityLevel(this, maturityLevel);
	}

	public Notification addCategories(Set<Category> categories) {
		return this.state.getState().addCategories(this, categories);
	}

	public void addGenre(Set<Genre> genres) {
		this.state.getState().addGenre(this, genres);
	}

	public void addParentalAdvisory(Set<ParentalAdvisory> parentalAdvisories) {
		this.state.getState().addParentalAdvisory(this, parentalAdvisories);
	}

	public void setMaterialOperation(MaterialOperation materialOperation) {
		this.state.getState().setMaterialOperation(this, materialOperation);
	}

	public void removeTitleDescriptionLanguage(ContentTitleDescriptionLanguage titleNDescription) {
		this.state.getState().removeTitleDescriptionLanguage(this, titleNDescription);
	}

	public Licence getActiveLicence() {
		List<Licence> licences = getLicences().stream().filter(l -> {
			LocalDateTime now = LocalDateTime.now();
			return l.getStartDate().isBefore(now) && l.getEndDate().isAfter(now);
		}).collect(Collectors.toList());
		if (licences.isEmpty()) {
			return null;
		}

		if (licences.size() > 1) {
			throw new LicenceConflictException("There are conflicting licences! ContentId: " + this.getId());
		}

		return licences.get(0);
	}

	public Boolean hasValidLicence() {
		List<Licence> licences = getLicences().stream().filter(l -> {
			LocalDateTime now = LocalDateTime.now();
			return l.getEndDate().isAfter(now);
		}).collect(Collectors.toList());
		return !licences.isEmpty();
	}

	public Boolean isIngested() {
		DrmType[] types = DrmType.values();
		Set<ContentPlayUrl> playUrls = getContentPlayUrls();
		for (DrmType drmType : types) {
			if (playUrls.stream().noneMatch(p -> p.getDrmType().equals(drmType))) {
				return false;
			}
		}
		return true;
	}

	public void addVodOperation(VODOperation vodOperation) {
		this.vodOperations.add(vodOperation);
	}

	public void addPlayUrl(ContentPlayUrl contentPlayUrl) {
		this.contentPlayUrls.add(contentPlayUrl);
	}

	@Override
	public String toString() {
		return "Content{" +
				"id=" + id +
				", contentType=" + contentType +
				", name='" + name + '\'' +
				'}';
	}

	public Boolean hasLicenceConflict(Licence licence) {
		return this.getLicences()
				.stream()
				.anyMatch(l -> l.getStartDate().equals(licence.getStartDate()) ||
						l.getEndDate().equals(licence.getEndDate()) ||
						l.getStartDate().isBefore(licence.getStartDate()) && l.getEndDate().isAfter(licence.getStartDate()) ||
						l.getEndDate().isAfter(licence.getEndDate()) && l.getStartDate().isBefore(licence.getEndDate()) ||
						l.getStartDate().isAfter(licence.getStartDate()) && l.getEndDate().isBefore(licence.getEndDate()) ||
						l.equals(licence));
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public VodType getVodType() {
		return vodType;
	}

	public void setVodType(VodType vodType) {
		this.vodType = vodType;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public ContentBOCategory getContentBOCategory() {
		return contentBOCategory;
	}

	public void setContentBOCategory(ContentBOCategory contentBOCategory) {
		this.contentBOCategory = contentBOCategory;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public ContentState getState() {
		return state;
	}

	public void setState(ContentState state) {
		this.state = state;
	}

	public ScreenFormat getScreenFormat() {
		return screenFormat;
	}

	public void setScreenFormat(ScreenFormat screenFormat) {
		this.screenFormat = screenFormat;
	}

	public Set<Definition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(Set<Definition> definitions) {
		this.definitions = definitions;
	}

	public EncodingParameters getEncodingParameters() {
		return encodingParameters;
	}

	public void setEncodingParameters(EncodingParameters encodingParameters) {
		this.encodingParameters = encodingParameters;
	}

	public Set<VODOperation> getVodOperations() {
		return vodOperations;
	}

	public void setVodOperations(Set<VODOperation> vodOperations) {
		this.vodOperations = vodOperations;
	}

	public MaturityLevel getMaturityLevel() {
		return maturityLevel;
	}

	public void setMaturityLevel(MaturityLevel maturityLevel) {
		this.maturityLevel = maturityLevel;
	}

	public Set<Country> getCountries() {
		return countries;
	}

	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}

	public Set<ContentPoster> getPosters() {
		return posters;
	}

	public void setPosters(Set<ContentPoster> posters) {
		this.posters = posters;
	}

	public Set<ContentTitleDescriptionLanguage> getContentTitleDescriptionLanguages() {
		return contentTitleDescriptionLanguages;
	}

	public void setContentTitleDescriptionLanguages(Set<ContentTitleDescriptionLanguage> contentTitleDescriptionLanguages) {
		this.contentTitleDescriptionLanguages = contentTitleDescriptionLanguages;
	}

	public Set<Licence> getLicences() {
		return licences;
	}

	public void setLicences(Set<Licence> licences) {
		this.licences = licences;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<ContentAudioSubtitleLanguage> getContentAudioSubtitles() {
		return contentAudioSubtitles;
	}

	public void setContentAudioSubtitles(Set<ContentAudioSubtitleLanguage> contentAudioSubtitles) {
		this.contentAudioSubtitles = contentAudioSubtitles;
	}

	public Set<ContentCast> getContentCasts() {
		return contentCasts;
	}

	public void setContentCasts(Set<ContentCast> contentCasts) {
		this.contentCasts = contentCasts;
	}

	public Set<ContentPlayUrl> getContentPlayUrls() {
		return contentPlayUrls;
	}

	public void setContentPlayUrls(Set<ContentPlayUrl> contentPlayUrls) {
		this.contentPlayUrls = contentPlayUrls;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public Set<ParentalAdvisory> getParentalAdvisories() {
		return parentalAdvisories;
	}

	public void setParentalAdvisories(Set<ParentalAdvisory> parentalAdvisories) {
		this.parentalAdvisories = parentalAdvisories;
	}

	public Boolean getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(Boolean isLocal) {
		this.isLocal = isLocal;
	}

	public RawMediaData getRawMediaData() {
		return rawMediaData;
	}

	public void setRawMediaData(RawMediaData rawMediaData) {
		this.rawMediaData = rawMediaData;
	}

	@Override
	public PublishPoint getPublishPoint() {
		return publishPoint;
	}

	public void setPublishPoint(PublishPoint publishPoint) {
		this.publishPoint = publishPoint;
	}

}
