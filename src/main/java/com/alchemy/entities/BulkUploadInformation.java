package com.alchemy.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "bulk_upload_info")
public class BulkUploadInformation implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "uploaded_by")
	private Long userId;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "module_id")
	private Long moduleId;

	@Column(name = "uploaded_at")
	@CreationTimestamp
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public BulkUploadInformation(Long id, Long userId, String fileName, Long moduleId, Date createdAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.fileName = fileName;
		this.moduleId = moduleId;
		this.createdAt = createdAt;
	}

	public BulkUploadInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
