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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "permissions")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE permissions SET is_active=false WHERE id=?")
public class PermissionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "action_name")
	private String actionName;

	@Column(name = "description")
	private String description;

	@Column(name = "is_active")
	private boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@JoinColumn(name = "created_by")
	@Column(name = "created_by")
	private Long createdBy;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	private ModuleMasterEntity moduleMasterEntity;

	@Column(name = "method")
	private String method;

	@Column(name = "url")
	private String url;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public ModuleMasterEntity getModuleMasterEntity() {
		return moduleMasterEntity;
	}

	public void setModuleMasterEntity(ModuleMasterEntity moduleMasterEntity) {
		this.moduleMasterEntity = moduleMasterEntity;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public PermissionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PermissionEntity(Long id, String actionName, String description, boolean isActive, Date createdAt,
			Date updatedAt, Long updatedBy, Long createdBy, ModuleMasterEntity moduleMasterEntity) {
		super();
		this.id = id;
		this.actionName = actionName;
		this.description = description;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.createdBy = createdBy;
		this.moduleMasterEntity = moduleMasterEntity;
	}

}
