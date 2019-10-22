package com.hof.yakamozauth.help.content;

import com.hof.yakamozauth.help.AuditableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CMS_SUPPLIER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Supplier extends AuditableEntity {

	@Id
	@SequenceGenerator(name = "seq_supplier_generator", sequenceName = "seq_supplier")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_supplier_generator")
	private Long id;

	@NotNull
	private String name;
	@NotNull
	private String shortName;
	private String externalId;
	private String addressLine;
	private String city;
	private String contactFullName;
	private String contactPhoneNum;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Deal> deals = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Country.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "FK_SUPPLIER_COUNTRY"))
	@NotNull
	private Country county;

	public Supplier() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactFullName() {
		return contactFullName;
	}

	public void setContactFullName(String contactFullName) {
		this.contactFullName = contactFullName;
	}

	public String getContactPhoneNum() {
		return contactPhoneNum;
	}

	public void setContactPhoneNum(String contactPhoneNum) {
		this.contactPhoneNum = contactPhoneNum;
	}

	public Country getCounty() {
		return county;
	}

	public void setCounty(Country county) {
		this.county = county;
	}

	public Long getSupplierID() {
		return id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Deal> getDeals() {
		return deals;
	}

	public void setDeals(Set<Deal> deals) {
		this.deals = deals;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Supplier supplier = (Supplier) o;
		return Objects.equals(id, supplier.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}
