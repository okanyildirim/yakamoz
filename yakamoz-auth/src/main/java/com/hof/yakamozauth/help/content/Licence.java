package com.hof.yakamozauth.help.content;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CMS_LICENCE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Licence extends AuditableEntity implements RegionableEntity {
	
	@Id
	@SequenceGenerator(name = "seq_licence_generator", sequenceName = "seq_licence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_licence_generator")
	private Long id;

	@NotNull
	private String name;
	
	@Column(columnDefinition = "TIMESTAMP")
	@NotNull
	private LocalDateTime startDate;

	@NotNull
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime endDate;

	private Boolean exclusivity;
	private String runTypeId;
	private String notes;
	private String externalId;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Deal.class)
	@JoinColumn(name = "deal_id", foreignKey = @ForeignKey(name = "FK_LICENCE_DEAL"))
	@NotNull
	private Deal deal;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CMS_LICENCE_REGION",
			joinColumns = {@JoinColumn(name = "LICENCE_ID", foreignKey = @ForeignKey(name = "FK_LICENCE_REGION_LICENCE"), nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "REGION_ID", foreignKey = @ForeignKey(name = "FK_LICENCE_REGION_REGION"), nullable = false, updatable = false)},
			uniqueConstraints = {@UniqueConstraint(columnNames = { "LICENCE_ID", "REGION_ID" }) },
			indexes = {
					@Index(name = "I_FK_LICENCE_REGION_LICENCE", columnList = "LICENCE_ID"),
					@Index(name = "I_FK_LICENCE_REGION_REGION", columnList = "REGION_ID")
			})
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Country> regions = new HashSet<>();

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "cms_licence_contenttype")
	@Enumerated(EnumType.STRING)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<ContentType> contentTypes;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "licences")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Content> contents = new HashSet<>();

	@Enumerated(EnumType.STRING)
	@NotNull
	private LicenceType licenceType;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CMS_LICENCE_DEVICE",
			joinColumns = @JoinColumn(name = "LICENCE_ID", foreignKey = @ForeignKey(name = "FK_LICENCE_DEVICE_LICENCE"), nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "DEVICE_ID", foreignKey = @ForeignKey(name = "FK_LICENCE_DEVICE_DEVICE"), nullable = false, updatable = false),
			uniqueConstraints = @UniqueConstraint(columnNames = {"LICENCE_ID", "DEVICE_ID"}))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@NotNull
	private Set<Device> devices = new HashSet<>();

	public void deleteAllDevices() {
		devices.forEach(d -> d.getLicences().remove(this));
		devices.clear();
	}

	public void addDevices(Set<Device> devices) {
		this.devices.forEach(d -> d.getLicences().add(this));
		this.devices.addAll(devices);
	}

	public Long getLicenceID() {
		return id;
	}

	public void setLicenceID(Long licenceID) {
		this.id = licenceID;
	}

	@Override
	public String toString() {
		return "Licence{" +
				"id=" + id +
				", name='" + name + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Licence licence = (Licence) o;
		return Objects.equals(id, licence.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	
	public Boolean getExclusivity() {
		return exclusivity;
	}

	public void setExclusivity(Boolean exclusivity) {
		this.exclusivity = exclusivity;
	}

	public String getRunTypeId() {
		return runTypeId;
	}

	public void setRunTypeId(String runTypeId) {
		this.runTypeId = runTypeId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	@Override
	public Set<Country> getRegions() {
		return regions;
	}

	public void setRegions(Set<Country> regions) {
		this.regions = regions;
	}

	public Set<ContentType> getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(Set<ContentType> contentTypes) {
		this.contentTypes = contentTypes;
	}

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public Set<Content> getContents() {
		return contents;
	}

	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}

	public LicenceType getLicenceType() {
		return licenceType;
	}

	public void setLicenceType(LicenceType licenceType) {
		this.licenceType = licenceType;
	}
	
}
