package com.alchemy.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "user_certificate")
@Where(clause = "is_active=true")
public class UserCertificateEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userId;

	@ManyToOne
	@JoinColumn(name = "certificate_id")
	private FileUploadEntity certificateId;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "is_active")
	private boolean isActive = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUserId() {
		return userId;
	}

	public void setUserId(UserEntity userId) {
		this.userId = userId;
	}

	public FileUploadEntity getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(FileUploadEntity certificateId) {
		this.certificateId = certificateId;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UserCertificateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserCertificateEntity(Long id, UserEntity userId, FileUploadEntity certificateId, Date createdAt,
			Date updatedAt, boolean isActive) {
		super();
		this.id = id;
		this.userId = userId;
		this.certificateId = certificateId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
	}
	
	
}
