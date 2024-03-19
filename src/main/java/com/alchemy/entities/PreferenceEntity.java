package com.alchemy.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Preferences")
@Where(clause = "is_active = true")
public class PreferenceEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "experience",length = 2500)
	private String experience;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "career_aspiration_id")
	private CareerAspirationEntity careerAspirationId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "function_id")
	private GplFunctionEntity functionId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private GplDepartmentEntity departmentId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private GplRoleEntity roleId;

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

	public PreferenceEntity(Long id, String experience, CareerAspirationEntity careerAspirationId,
			GplFunctionEntity functionId, GplDepartmentEntity departmentId, GplRoleEntity roleId, Boolean isActive,
			Long createdBy, Long updatedBy, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.experience = experience;
		this.careerAspirationId = careerAspirationId;
		this.functionId = functionId;
		this.departmentId = departmentId;
		this.roleId = roleId;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public PreferenceEntity() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CareerAspirationEntity getCareerAspirationId() {
		return careerAspirationId;
	}

	public void setCareerAspirationId(CareerAspirationEntity careerAspirationId) {
		this.careerAspirationId = careerAspirationId;
	}

	public GplFunctionEntity getFunctionId() {
		return functionId;
	}

	public void setFunctionId(GplFunctionEntity functionId) {
		this.functionId = functionId;
	}

	public GplDepartmentEntity getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(GplDepartmentEntity departmentId) {
		this.departmentId = departmentId;
	}

	public GplRoleEntity getRoleId() {
		return roleId;
	}

	public void setRoleId(GplRoleEntity roleId) {
		this.roleId = roleId;
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

	public PreferenceEntity(Long id, CareerAspirationEntity careerAspirationId, GplFunctionEntity functionId,
			GplDepartmentEntity departmentId, GplRoleEntity roleId, Boolean isActive, Long createdBy, Long updatedBy,
			Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.careerAspirationId = careerAspirationId;
		this.functionId = functionId;
		this.departmentId = departmentId;
		this.roleId = roleId;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
