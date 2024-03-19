package com.alchemy.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "gpl_department")
@Where(clause = "is_active=true")
public class GplDepartmentEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@JoinColumn(name = "created_by")
	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	@JoinColumn(name = "updated_by")
	private Long updatedBy;

	@Column(name = "name", length = 100)
	private String name;

	@ManyToOne
	@JoinColumn(name = "gpl_function_id")
	private GplFunctionEntity gplFunctionId;
	
	@OneToMany(mappedBy = "departmentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserEntity> userEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GplFunctionEntity getGplFunctionId() {
		return gplFunctionId;
	}

	public GplFunctionEntity getGplFunctionEntity() {
		return gplFunctionId;
	}

	public void setGplFunctionId(GplFunctionEntity gplFunctionId) {
		this.gplFunctionId = gplFunctionId;
	}

}
