package com.hof.yakamozauth.help.content;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CMS_DEAL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deal extends AuditableEntity {

	@Id
	@SequenceGenerator(name = "seq_deal_generator", sequenceName = "seq_deal")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_deal_generator")
	private Long id;

	@NotNull
	private String name;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotNull
	private LocalDateTime signedDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDateTime cancellationTill;
	@Enumerated(EnumType.STRING)
	private CancellationOption cancellationOptionId;
	private Double cancellationFine;
	private Boolean advertorialRigths;
	private Boolean promotionalRights;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotNull
	private LocalDateTime startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotNull
	private LocalDateTime endDate;
	private Double price;
	@Enumerated(EnumType.STRING)
	private PaymentType paymentTypeId;
	@Enumerated(EnumType.STRING)
	private CurrencyType currencyTypeId;
	private String notes;
	private String externalId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "FK_DEAL_SUPPLIER"))
	@NotNull
	private Supplier supplier;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "cms_deal_definition")
	@Enumerated(EnumType.STRING)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Definition> definitions = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deal", cascade = CascadeType.PERSIST)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<DealSubDubLanguagePermission> subDubLanguagePermissions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deal", cascade = CascadeType.REMOVE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<DealFile> dealFiles;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deal")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Licence> licences;

	public Long getId() {
		return id;
	}

	public void setId(Long dealID) {
		this.id = dealID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(LocalDateTime signedDate) {
		this.signedDate = signedDate;
	}

	public LocalDateTime getCancellationTill() {
		return cancellationTill;
	}

	public void setCancellationTill(LocalDateTime cancellationTill) {
		this.cancellationTill = cancellationTill;
	}

	public CancellationOption getCancellationOptionId() {
		return cancellationOptionId;
	}

	public void setCancellationOptionId(CancellationOption cancellationOptionID) {
		this.cancellationOptionId = cancellationOptionID;
	}

	public Double getCancellationFine() {
		return cancellationFine;
	}

	public void setCancellationFine(Double cancellationFine) {
		this.cancellationFine = cancellationFine;
	}

	public Boolean getAdvertorialRigths() {
		return advertorialRigths;
	}

	public void setAdvertorialRigths(Boolean advertorialRigths) {
		this.advertorialRigths = advertorialRigths;
	}

	public Boolean getPromotionalRights() {
		return promotionalRights;
	}

	public void setPromotionalRights(Boolean promotionalRights) {
		this.promotionalRights = promotionalRights;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public PaymentType getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(PaymentType paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public CurrencyType getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(CurrencyType currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Set<Definition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(Set<Definition> definitions) {
		this.definitions = definitions;
	}

	public Set<DealSubDubLanguagePermission> getSubDubLanguagePermissions() {
		return subDubLanguagePermissions;
	}

	public void setSubDubLanguagePermissions(Set<DealSubDubLanguagePermission> subDubLanguagePermissions) {
		this.subDubLanguagePermissions = subDubLanguagePermissions;
	}

	public Set<DealFile> getDealFiles() {
		return dealFiles;
	}

	public void setDealFiles(Set<DealFile> dealFiles) {
		this.dealFiles = dealFiles;
	}

	public Set<Licence> getLicences() {
		return licences;
	}

	public void setLicences(Set<Licence> licences) {
		this.licences = licences;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Deal deal = (Deal) o;
		return Objects.equals(id, deal.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}
