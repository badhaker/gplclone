package com.alchemy.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "banner")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE banner SET is_active=false WHERE id=?")
public class BannerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "banner_name")
	private String name;

	@Column(name = "display_order")
	private Integer displayOrder;

	@Column(name = "url", length = 500)
	private String url;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "file_id")
	private FileUploadEntity fileId;

	@Column(name = "show_content")
	private Integer showContent;

	@Column(name = "description")
	private String description;

	@Column(name = "is_visible")
	private Boolean isVisible = true;

	public FileUploadEntity getFileId() {
		return fileId;
	}

	public void setFileId(FileUploadEntity fileId) {
		this.fileId = fileId;
	}

	@Column(name = "is_active")
	private Boolean isActive = true;

	@JoinColumn(name = "created_by")
	@Column(name = "created_by")
	private Long createdBy;

	@JoinColumn(name = "updated_by")
	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getShowContent() {
		return showContent;
	}

	public void setShowContent(Integer showContent) {
		this.showContent = showContent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public BannerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BannerEntity(Long id, String name, Integer displayOrder, String url, FileUploadEntity fileId,
			Integer showContent, String description, Boolean isVisible, Boolean isActive, Long createdBy,
			Long updatedBy, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.displayOrder = displayOrder;
		this.url = url;
		this.fileId = fileId;
		this.showContent = showContent;
		this.description = description;
		this.isVisible = isVisible;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
