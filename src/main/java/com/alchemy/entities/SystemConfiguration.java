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

@Entity
@Table(name = "system_configuration")
public class SystemConfiguration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "key", unique = true)
	private String key;

	@Column(name = "value", columnDefinition = "TEXT")
	private String value;

	@Column(name = "flag")
	private Boolean flag = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@OneToOne
	@JoinColumn(name = "file_id")
	private FileUploadEntity fileId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public SystemConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SystemConfiguration(Long id, String key, String value, Boolean flag) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
		this.flag = flag;
	}

	public FileUploadEntity getFileId() {
		return fileId;
	}

	public void setFileId(FileUploadEntity fileId) {
		this.fileId = fileId;
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

}
