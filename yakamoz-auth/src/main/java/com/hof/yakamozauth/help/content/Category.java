package com.hof.yakamozauth.help.content;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cms_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category extends AuditableEntity implements PublishablePointEntity, RegionableEntity {

	@Id
	@SequenceGenerator(name = "seq_category_generator", sequenceName = "seq_category")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_category_generator")
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PublishPoint publishPoint = PublishPoint.PROD;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoryType type;

	@Column(nullable = false)
	private String guid;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<CategoryTitleDescriptionLanguage> categoryTitleLanguages = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<CategoryPoster> categoryPosters = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_PARENT_CATEGORY"))
	private Category parent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Category> subCategories = new HashSet<>();

	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
			CascadeType.MERGE}, mappedBy = "categories")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Content> contents = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CMS_CATEGORY_REGION",
			joinColumns = {@JoinColumn(name = "CATEGORY_ID", foreignKey = @ForeignKey(name = "FK_CATEGORY_REGION_CATEGORY"), nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "REGION_ID", foreignKey = @ForeignKey(name = "FK_CATEGORY_REGION_REGION"), nullable = false, updatable = false)},
			uniqueConstraints = {@UniqueConstraint(columnNames = { "CATEGORY_ID", "REGION_ID" }) },
			indexes = {
					@Index(name = "I_FK_CATEGORY_REGION_CATEGORY", columnList = "CATEGORY_ID"),
					@Index(name = "I_FK_CATEGORY_REGION_REGION", columnList = "REGION_ID")
			})
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Country> regions = new HashSet<>();
	
	public Category() {
		this.guid = UUID.randomUUID().toString();
	}

	Category(String name) {
		this.guid = UUID.randomUUID().toString();
		setName(name);
		setPublishPoint(PublishPoint.ALL);
		setType(CategoryType.REQUIRED_NODE);
	}

	Category(String name, Category root) {
		this.guid = UUID.randomUUID().toString();
		updateName(name);
		setPublishPoint(PublishPoint.ALL);
		setType(CategoryType.REQUIRED_NODE);
		setParent(root);
	}

	public Category(String name, CategoryType type, Category parent, PublishPoint publishPoint) {
		this.guid = UUID.randomUUID().toString();
		updateName(name);
		setType(type);
		setParent(parent);
		setPublishPoint(publishPoint);
		assignParent(parent);
		updatePublishPoint(publishPoint);
	}

	public void addTitleAndDescription(CategoryTitleDescriptionLanguage descriptionLanguage) {
		getCategoryTitleLanguages().add(descriptionLanguage);
	}

	void assignParent(Category parent) {
		if (parent == null) {
			throw new CategoryMoveException("Parent cannot be empty!");
		}

		if (parent.getType().equals(CategoryType.LEAF_NODE)) {
			throw new CategoryMoveException("You cannot add category to leaf node category!");
		}

		if (parent.getPublishPoint().getValue() < getPublishPoint().getValue()) {
			throw new CategoryMoveException("Publishing point conflict!");
		}

		if (parent.isSiblingNameExists(getName())) {
			throw new CategoryMoveException("There is an existing category name in the parent category!");
		}

		setParent(parent);
	}

	public Boolean isSiblingNameExists(String name) {
		return getSubCategories().stream().anyMatch(c -> c.getName().equalsIgnoreCase(name));
	}

	public void update(String name, CategoryType type, PublishPoint publishPoint, String description) {
		if (this.type == CategoryType.REQUIRED_NODE) {
			throw new CategoryUpdateException("You cannot update required category!");
		}

		if (name != null) {
			updateName(name);
		}

		if (description != null) {
			setDescription(description);
		}

		if (type != null) {
			updateCategoryType(type);
		}

		if (publishPoint != null) {
			updatePublishPoint(publishPoint);
		}

	}

	public void updateName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Category type cannot be empty!");
		}
		setName(name);
	}

	public void updateCategoryType(CategoryType categoryType) {
		if (this.type == categoryType) {
			return;
		}

		if (categoryType == CategoryType.REQUIRED_NODE) {
			throw new CategoryUpdateException("You cannot update category type to required node!");
		}

		if (this.type == CategoryType.NODE && !this.subCategories.isEmpty()) {
			throw new CategoryUpdateException("You cannot update category type when there is at least one child!");
		}

		if (this.type == CategoryType.LEAF_NODE && !this.contents.isEmpty()) {
			throw new CategoryUpdateException("You cannot leaf category type when there is at least connected content!");
		}

		setType(categoryType);
	}

	public void updatePublishPoint(PublishPoint publishPoint) {
		if (getPublishPoint() == publishPoint) {
			return;
		}

		if (this.parent == null) {
			throw new CategoryUpdateException("You cannot change root category publishing point!");
		}

		if (this.parent.getPublishPoint().getValue() < publishPoint.getValue()) {
			throw new CategoryUpdateException("Category publishing point conflict!");
		}

		boolean siblingsConflict = getSubCategories()
				.stream()
				.anyMatch(c -> publishPoint.getValue() < c.getPublishPoint().getValue());

		if (siblingsConflict) {
			throw new CategoryUpdateException("Category publish point conflict!");
		}

		setPublishPoint(publishPoint);
	}

	public void move(Category parent) {
		if (this.parent == null) {
			throw new CategoryMoveException("Root category cannot be moved!");
		}

		if (this.type == CategoryType.REQUIRED_NODE) {
			throw new CategoryMoveException("You cannot required node!");
		}

		assignParent(parent);
	}

	public boolean isLeafNode() {
		return this.type == CategoryType.LEAF_NODE;
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

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Set<CategoryTitleDescriptionLanguage> getCategoryTitleLanguages() {
		return categoryTitleLanguages;
	}

	public void setCategoryTitleLanguages(Set<CategoryTitleDescriptionLanguage> categoryTitleLanguages) {
		this.categoryTitleLanguages = categoryTitleLanguages;
	}

	public Set<CategoryPoster> getCategoryPosters() {
		return categoryPosters;
	}

	public void setCategoryPosters(Set<CategoryPoster> categoryPosters) {
		this.categoryPosters = categoryPosters;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Content> getContents() {
		return contents;
	}

	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}

	@Override
	public Set<Country> getRegions() {
		return regions;
	}

	@Override
	public PublishPoint getPublishPoint() {
		return publishPoint;
	}
	
	public void setPublishPoint(PublishPoint publishPoint) {
		if (publishPoint == null) {
			throw new IllegalArgumentException("Publish point cannot be empty!");
		}
		
		this.publishPoint = publishPoint;
	}
	
}

