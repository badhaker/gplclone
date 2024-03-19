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
@Table(name = "attendance")
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE attendance SET is_active=false WHERE id=?")
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "date")
	private Date date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private UserEntity userId;

	@Column(name = "zone")
	private String zone;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "function_Id")
	private GplFunctionEntity functionId;
	
	@Column(name = "star_performer")
	private Boolean starPerformer= false;

	@Column(name = "lock_attendance")
	private Boolean lockAttendance= false;
	
	@Column(name = "attendance_status")
	private Integer attendance;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "track_id")
	private LearningTrackEntity learningTrackId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subtrack_id")
	private SubTrackEntity subTrackId;

//	@Column(name = "from_date_of_attendance")
//	private Date fromDateOfAttendance;

	@Column(name = "complete_date_of_attendance")
	private Date completeDateOfAttendance;

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
	private Date updatedAt;
	
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

	public GplFunctionEntity getFunctionId() {
		return functionId;
	}

	public void setFunctionId(GplFunctionEntity functionId) {
		this.functionId = functionId;
	}

	public Boolean getStarPerformer() {
		return starPerformer;
	}

	public void setStarPerformer(Boolean starPerformer) {
		this.starPerformer = starPerformer;
	}

	public LearningTrackEntity getLearningTrackId() {
		return learningTrackId;
	}

	public void setLearningTrackId(LearningTrackEntity learningTrackId) {
		this.learningTrackId = learningTrackId;
	}

	public SubTrackEntity getSubTrackId() {
		return subTrackId;
	}

	public void setSubTrackId(SubTrackEntity subTrackId) {
		this.subTrackId = subTrackId;
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

	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Boolean getLockAttendance() {
		return lockAttendance;
	}

	public void setLockAttendance(Boolean lockAttendance) {
		this.lockAttendance = lockAttendance;
	}

	public Integer getAttendance() {
		return attendance;
	}

	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}
	public Attendance(Long id, Date date, UserEntity userId, String zone, GplFunctionEntity functionId, Boolean starPerformer,
			LearningTrackEntity learningTrackId, SubTrackEntity subTrackId, 
			Date completeDateOfAttendance, Boolean isActive, Long createdBy, Long updatedBy, Date createdAt,
			Date updatedAt) {
		super();
		this.id = id;
		this.date = date;
		this.userId = userId;
		this.zone = zone;
		this.functionId = functionId;
		this.starPerformer = starPerformer;
		this.learningTrackId = learningTrackId;
		this.subTrackId = subTrackId;
	//	this.fromDateOfAttendance = fromDateOfAttendance;
		this.completeDateOfAttendance = completeDateOfAttendance;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
