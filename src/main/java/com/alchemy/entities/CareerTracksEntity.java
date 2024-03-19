package com.alchemy.entities;

import java.sql.Date;

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
@Table(name = "career_tracks")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE career_tracks SET is_active=false WHERE id=?")
public class CareerTracksEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 100)
	private String name;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "thumbnail_id")
	private FileUploadEntity thumbnailFileId;

	@Column(name = "file_id")
	private Long fileId;

	@Column(name = "is_active")
	private boolean isActive = true;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

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

	public Long getFileId() {
		return fileId;
	}

	public FileUploadEntity getThumbnailFileId() {
		return thumbnailFileId;
	}

	public void setThumbnailFileId(FileUploadEntity thumbnailFileId) {
		this.thumbnailFileId = thumbnailFileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public CareerTracksEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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

	public CareerTracksEntity(Long id, String name, FileUploadEntity thumbnailFileId, Long fileId, boolean isActive,
			Date updatedAt, Date createdAt, Long createdBy, Long updatedBy) {
		super();
		this.id = id;
		this.name = name;
		this.thumbnailFileId = thumbnailFileId;
		this.fileId = fileId;
		this.isActive = isActive;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

}
