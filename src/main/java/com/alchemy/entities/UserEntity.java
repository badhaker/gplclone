package com.alchemy.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@Where(clause = "is_active = true")
public class UserEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "is_admin")
	private Boolean isAdmin = false;

	@OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
	private List<UserTrackEntity> userTrackEntity;

	@OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
	private List<NudgedTracks> nudgedTracks;

	@Column(name = "career_aspiration", columnDefinition = "TEXT")
	private String careerAspiration;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id")
	private LevelEntity levelId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "function_id")
	private GplFunctionEntity functionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private GplDepartmentEntity departmentId;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "zone")
	private String zone;

	@Column(name = "region")
	private String region;

	@Column(name = "project")
	private String project;

	@Column(name = "position_title")
	private String positionTitle;

	@Column(name = "employee_grade")
	private String employeeGrade;

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

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getEmployeeGrade() {
		return employeeGrade;
	}

	public void setEmployeeGrade(String employeeGrade) {
		this.employeeGrade = employeeGrade;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public GplFunctionEntity getFunctionId() {
		return functionId;
	}

	public void setFunctionId(GplFunctionEntity functionId) {
		this.functionId = functionId;
	}

	public LevelEntity getLevelId() {
		return levelId;
	}

	public void setLevelId(LevelEntity levelId) {
		this.levelId = levelId;
	}

	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EnrollNowEntity> enrollNowEntity;

	public List<EnrollNowEntity> getEnrollNowEntity() {
		return enrollNowEntity;
	}

	public void setEnrollNowEntity(List<EnrollNowEntity> enrollNowEntity) {
		this.enrollNowEntity = enrollNowEntity;
	}

	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Attendance> attendances;

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<UserTrackEntity> getUserTrackEntity() {
		return userTrackEntity;
	}

	public void setUserTrackEntity(List<UserTrackEntity> userTrackEntity) {
		this.userTrackEntity = userTrackEntity;
	}

	public GplDepartmentEntity getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(GplDepartmentEntity departmentId) {
		this.departmentId = departmentId;
	}

	public String getCareerAspiration() {
		return careerAspiration;
	}

	public void setCareerAspiration(String careerAspiration) {
		this.careerAspiration = careerAspiration;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public UserEntity(Long id, String name, String email, GenderEnum gender, String password, String phoneNumber,
			Boolean isActive, Date createdAt, Date updatedAt, Long createdBy, Long updatedBy, Boolean isAdmin,
			List<UserTrackEntity> userTrackEntity, String careerAspiration, LevelEntity levelId,
			GplFunctionEntity functionId, GplDepartmentEntity departmentId, String employeeId, String zone,
			String region, String project, String positionTitle, String employeeGrade,
			List<EnrollNowEntity> enrollNowEntity, List<Attendance> attendances) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isAdmin = isAdmin;
		this.userTrackEntity = userTrackEntity;
		this.careerAspiration = careerAspiration;
		this.levelId = levelId;
		this.functionId = functionId;
		this.departmentId = departmentId;
		this.employeeId = employeeId;
		this.zone = zone;
		this.region = region;
		this.project = project;
		this.positionTitle = positionTitle;
		this.employeeGrade = employeeGrade;
		this.enrollNowEntity = enrollNowEntity;
		this.attendances = attendances;
	}

	@Override
	public String toString() {
		return "{\"id\":" + id + ",\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"password\":\"" + password
				+ "\"}";
	}
}
