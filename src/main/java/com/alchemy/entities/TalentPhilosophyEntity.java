package com.alchemy.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.alchemy.utils.Constant;

@Entity
@Table(name = "talent_philosophy")
@Where(clause = "is_active=true")
public class TalentPhilosophyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = Constant.NAME_LENGTH)
	private String name;

	@OneToOne
	@JoinColumn(name = "file_id")
	private FileUploadEntity fileId;

	@Column(name = "chro_message", columnDefinition = "TEXT")
	private String chroMessage;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@JoinColumn(name = "created_by")
	private Long createdBy;

	@JoinColumn(name = "updated_by")
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileUploadEntity getFileId() {
		return fileId;
	}

	public void setFileId(FileUploadEntity fileId) {
		this.fileId = fileId;
	}

	public String getChroMessage() {
		return chroMessage;
	}

	public void setChroMessage(String chroMessage) {
		this.chroMessage = chroMessage;
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

	public TalentPhilosophyEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TalentPhilosophyEntity(Long id, String name, FileUploadEntity fileId, String chroMessage, Boolean isActive,
			Long createdBy, Long updatedBy, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.fileId = fileId;
		this.chroMessage = chroMessage;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
