package com.alchemy.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "enroll_now_temp")
public class EnrollNowTemp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "employee_edp")
	private String employeeEdp;
	
	@Column(name = "employee_name")
	private String name;
	
	@Column(name = "employee_email")
	private String email;
	
	@Column(name = "zone")
	private String zone;
	
	@Column(name = "region")
	private String region;
	
	@Column(name = "project")
	private String project;
	
	@Column(name ="employee_level")
	private String employeeLevel;
	
	@Column(name = "employee_grade")
	private String employeeGrade;
	
	@Column(name = "position_title")
	private String positionTitle;
	
	@Column(name = "department_name")
	private String departmentName;
	
	@Column(name = "function_name")
	private String functionName;
	
	@Column(name = "track_name")
	private String trackName;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "enroll_status")
	private Integer enrollStatus;
	
	@Column(name = "bulk_upload_id")
	private Long bulkId;
	
	public Integer getEnrollStatus() {
		return enrollStatus;
	}

	public void setEnrollStatus(Integer enrollStatus) {
		this.enrollStatus = enrollStatus;
	}

	public Long getBulkId() {
		return bulkId;
	}

	public void setBulkId(Long bulkId) {
		this.bulkId = bulkId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeEdp() {
		return employeeEdp;
	}

	public void setEmployeeEdp(String employeeEdp) {
		this.employeeEdp = employeeEdp;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public EnrollNowTemp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnrollNowTemp(Long id, String employeeEdp, String name, String email, String zone, String region,
			String project, String employeeLevel, String employeeGrade, String positionTitle, String departmentName,
			String functionName, String trackName, Date createdAt,Long bulkId) {
		super();
		this.id = id;
		this.employeeEdp = employeeEdp;
		this.name = name;
		this.email = email;
		this.zone = zone;
		this.region = region;
		this.project = project;
		this.employeeLevel = employeeLevel;
		this.employeeGrade = employeeGrade;
		this.positionTitle = positionTitle;
		this.departmentName = departmentName;
		this.functionName = functionName;
		this.trackName = trackName;
		this.createdAt = createdAt;
		this.bulkId = bulkId;
	}
	
	
}
