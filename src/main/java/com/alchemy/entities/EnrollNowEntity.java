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
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "enroll_now_entity")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE enroll_now_entity SET is_active=false WHERE id=?")
public class EnrollNowEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private UserEntity userId;

	@Column(name = "zone")
	private String zone;

	@Column(name = "region")
	private String region;

	@Column(name = "project")
	private String project;

	@Column(name = "employee_level")
	private String employeeLevel;

	@Column(name = "employee_grade")
	private String employeeGrade;

	@Column(name = "postion_title")
	private String positionTitle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private DepartmentEntity departmentId;

	@Column(name = "function_name")
	private String functionName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "track_id")
	private LearningTrackEntity learningTrackId;

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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getEmployeeLevel() {
		return employeeLevel;
	}

	public void setEmployeeLevel(String employeeLevel) {
		this.employeeLevel = employeeLevel;
	}

	public String getEmployeeGrade() {
		return employeeGrade;
	}

	public void setEmployeeGrade(String employeeGrade) {
		this.employeeGrade = employeeGrade;
	}

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public DepartmentEntity getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(DepartmentEntity departmentId) {
		this.departmentId = departmentId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public LearningTrackEntity getLearningTrackId() {
		return learningTrackId;
	}

	public void setLearningTrackId(LearningTrackEntity learningTrackId) {
		this.learningTrackId = learningTrackId;
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

	public EnrollNowEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnrollNowEntity(Long id, UserEntity userId, String zone, String region, String project, String employeeLevel,
			String employeeGrade, String positionTitle, DepartmentEntity departmentId, String functionName,
			LearningTrackEntity learningTrackId, Boolean isActive, Long createdBy, Long updatedBy, Date createdAt,
			Date updatedAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.zone = zone;
		this.region = region;
		this.project = project;
		this.employeeLevel = employeeLevel;
		this.employeeGrade = employeeGrade;
		this.positionTitle = positionTitle;
		this.departmentId = departmentId;
		this.functionName = functionName;
		this.learningTrackId = learningTrackId;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
