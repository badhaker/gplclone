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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "user_track")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE banner SET is_active=false WHERE id=?")
public class UserTrackEntity {

	public UserTrackEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "track_id")
	private LearningTrackEntity trackId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")
	private FileUploadEntity file_id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_id")
	private FileUploadEntity imageId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subtrack_id")
	private SubTrackEntity subtrackId;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "department_id")
//	private DepartmentEntity departmentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private GplFunctionEntity departmentId;

	@Column(name = "is_star_performer")
	private Boolean isStarPerformer = false;

	@Column(name = "enroll_status")
	private Integer enrollStatus;

	@Column(name = "status")
	private Integer status;

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

	@Column(name = "function_name")
	private String functionName;

	@Column(name = "employee_level")
	private String employeeLevel;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "complete_date")
	private Date completeDate;
	
	@Column(name="pre_assesment")
	private Float preAssesment;
	
	@Column(name="post_assesment")
	private Float postAssesment;
	
	public Float getPreAssesment() {
		return preAssesment;
	}

	public void setPreAssesment(Float preAssesment) {
		this.preAssesment = preAssesment;
	}

	public Float getPostAssesment() {
		return postAssesment;
	}

	public void setPostAssesment(Float postAssesment) {
		this.postAssesment = postAssesment;
	}

	public FileUploadEntity getImageId() {
		return imageId;
	}

	public void setImageId(FileUploadEntity imageId) {
		this.imageId = imageId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LearningTrackEntity getTrackId() {
		return trackId;
	}

	public void setTrackId(LearningTrackEntity trackId) {
		this.trackId = trackId;
	}

	public SubTrackEntity getSubtrackId() {
		return subtrackId;
	}

	public void setSubtrackId(SubTrackEntity subtrackId) {
		this.subtrackId = subtrackId;
	}

	public FileUploadEntity getFile_id() {
		return file_id;
	}

	public void setFile_id(FileUploadEntity file_id) {
		this.file_id = file_id;
	}

	public Boolean getIsStarPerformer() {
		return isStarPerformer;
	}

	public void setIsStarPerformer(Boolean isStarPerformer) {
		this.isStarPerformer = isStarPerformer;
	}

	public Integer getEnrollStatus() {
		return enrollStatus;
	}

	public void setEnrollStatus(Integer enrollStatus) {
		this.enrollStatus = enrollStatus;
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

//	public DepartmentEntity getDepartmentId() {
//		return departmentId;
//	}
//
//	public void setDepartmentId(DepartmentEntity departmentId) {
//		this.departmentId = departmentId;
//	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getEmployeeLevel() {
		return employeeLevel;
	}

	public void setEmployeeLevel(String employeeLevel) {
		this.employeeLevel = employeeLevel;
	}

	public UserTrackEntity(Long id, UserEntity userEntity, LearningTrackEntity trackId, FileUploadEntity file_id,
			FileUploadEntity imageId, SubTrackEntity subtrackId, GplFunctionEntity departmentId,
			Boolean isStarPerformer, Integer enrollStatus, Integer status, Boolean isActive, Long createdBy,
			Long updatedBy, Date createdAt, Date updatedAt, String functionName, String employeeLevel,
			String employeeId, Date completeDate) {
		super();
		this.id = id;
		this.userEntity = userEntity;
		this.trackId = trackId;
		this.file_id = file_id;
		this.subtrackId = subtrackId;
		this.imageId = imageId;
		this.departmentId = departmentId;
		this.isStarPerformer = isStarPerformer;
		this.enrollStatus = enrollStatus;
		this.status = status;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.functionName = functionName;
		this.employeeLevel = employeeLevel;
		this.employeeId = employeeId;
		this.completeDate = completeDate;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public GplFunctionEntity getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(GplFunctionEntity departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

}
