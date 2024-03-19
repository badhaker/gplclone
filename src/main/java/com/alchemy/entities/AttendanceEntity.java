package com.alchemy.entities;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "attendance_temp")
public class AttendanceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "date")
	private Date date;

	@Column(name = "employee_name")
	private String name;

	@Column(name = "employee_edp")
	private String employeeEdp;

	@Column(name = "email")
	private String email;

	@Column(name = "zone")
	private String zone;

	@Column(name = "function_name")
	private String functionName;

	@Column(name = "star_performer")
	private Boolean starPerformer;
	
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

	public Integer getAttendance() {
		return attendance;
	}

	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}

	@Column(name = "lock_attendance")
	private Boolean lockAttendance= false;
	
	
	
	public Boolean getLockAttendance() {
		return lockAttendance;
	}

	public void setLockAttendance(Boolean lockAttendance) {
		this.lockAttendance = lockAttendance;
	}

	@Column(name = "attendance_status")
	private Integer attendance;

	@Column(name = "track_name")
	private String trackName;

	@Column(name = "subtrack_name")
	private String subTrackName;

//	@Column(name = "from_date_of_attendance")
//	private Date fromDateOfAttendance;

	@Column(name = "complete_date_of_attendance")
	private Date completeDateOfAttendance;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "bulk_upload_id")
	private Long bulkId;

	@Column(name = "flag")
	private Boolean flag = false;
	
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmployeeEdp() {
		return employeeEdp;
	}

	public void setEmployeeEdp(String employeeEdp) {
		this.employeeEdp = employeeEdp;
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

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Boolean getStarPerformer() {
		return starPerformer;
	}

	public void setStarPerformer(Boolean starPerformer) {
		this.starPerformer = starPerformer;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getSubTrackName() {
		return subTrackName;
	}

	public void setSubTrackName(String subTrackName) {
		this.subTrackName = subTrackName;
	}

//	public Date getFromDateOfAttendance() {
//		return fromDateOfAttendance;
//	}
//
//	public void setFromDateOfAttendance(Date fromDateOfAttendance) {
//		this.fromDateOfAttendance = fromDateOfAttendance;
//	}

	public Date getCompleteDateOfAttendance() {
		return completeDateOfAttendance;
	}

	public void setCompleteDateOfAttendance(Date completeDateOfAttendance) {
		this.completeDateOfAttendance = completeDateOfAttendance;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public AttendanceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttendanceEntity(Long id, Date date, String name, String employeeEdp, String email, String zone,
			String functionName, Boolean starPerformer, String trackName, String subTrackName,
			Date completeDateOfAttendance, Date createdAt, Long bulkId, Boolean flag) {
		super();
		this.id = id;
		this.date = date;
		this.name = name;
		this.employeeEdp = employeeEdp;
		this.email = email;
		this.zone = zone;
		this.functionName = functionName;
		this.starPerformer = starPerformer;
		this.trackName = trackName;
		this.subTrackName = subTrackName;
	//	this.fromDateOfAttendance = fromDateOfAttendance;
		this.completeDateOfAttendance = completeDateOfAttendance;
		this.createdAt = createdAt;
		this.bulkId = bulkId;
		this.flag = flag;
	}

}
