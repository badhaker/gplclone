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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

@Entity
@Table(name = "department")
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE banner SET is_active=false WHERE id=?")
public class DepartmentEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "department_name", length = 100)
	private String name;

	@Column(name = "description")
	@Size(max = Constant.DESCRIPTION_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION + "*"
			+ ErrorMessageKey.DEPARTMENT_E031604)
	private String description;

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

	@OneToMany(mappedBy = "departmentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EnrollNowEntity> enrollNowEntity;

	@OneToMany(mappedBy = "departmentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserTrackEntity> userTrackEntity;

//	@OneToMany(mappedBy = "departmentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private List<UserEntity> userEntity;

	public List<UserTrackEntity> getUserTrackEntity() {
		return userTrackEntity;
	}

	public void setUserTrackEntity(List<UserTrackEntity> userTrackEntity) {
		this.userTrackEntity = userTrackEntity;
	}

//	public List<UserEntity> getUserEntity() {
//		return userEntity;
//	}
//
//	public void setUserEntity(List<UserEntity> userEntity) {
//		this.userEntity = userEntity;
//	}

	public List<EnrollNowEntity> getEnrollNowEntity() {
		return enrollNowEntity;
	}

	public void setEnrollNowEntity(List<EnrollNowEntity> enrollNowEntity) {
		this.enrollNowEntity = enrollNowEntity;
	}

	@OneToMany(mappedBy = "departmentEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TrackDepartmentEntity> trackDepartmentEntity;

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

	public DepartmentEntity(Long id,
			@Size(max = 100, message = "Name should not be greater than 100 characters*GPL-E031604") String name,
			@Size(max = 250, message = "Description should not be greater than 250 characters*GPL-E031604") String description,
			Boolean isActive, Long createdBy, Long updatedBy, Date createdAt, Date updatedAt,
			List<EnrollNowEntity> enrollNowEntitiy) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enrollNowEntity = enrollNowEntitiy;
	}

	public DepartmentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<TrackDepartmentEntity> getTrackDepartmentEntity() {
		return trackDepartmentEntity;
	}

	public void setTrackDepartmentEntity(List<TrackDepartmentEntity> trackDepartmentEntity) {
		this.trackDepartmentEntity = trackDepartmentEntity;
	}

}
