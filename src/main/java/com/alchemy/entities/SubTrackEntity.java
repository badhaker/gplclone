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
@Table(name = "sub_track_entity")
@Where(clause = "is_active=true")
public class SubTrackEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

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

//	public SubTrackEntity(FileUploadEntity uploadBrochure) {
//		super();
//		this.uploadBrochure = uploadBrochure;
//	}
//
//	public FileUploadEntity getUploadBrochure() {
//		return uploadBrochure;
//	}
//
//	public void setUploadBrochure(FileUploadEntity uploadBrochure) {
//		this.uploadBrochure = uploadBrochure;
//	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "learning_track_entity")
	private LearningTrackEntity learningTrackEntity;

	@OneToMany(mappedBy = "subTrackId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Attendance> attendances;

	@OneToMany(mappedBy = "subtrackId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserTrackEntity> userTrackEntity;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "fileUpload_id")
//	private FileUploadEntity uploadBrochure;

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public SubTrackEntity(LearningTrackEntity learningTrackEntity) {
		super();
		this.learningTrackEntity = learningTrackEntity;
	}

	public LearningTrackEntity getLearningTrackEntity() {
		return learningTrackEntity;
	}

	public void setLearningTrackEntity(LearningTrackEntity learningTrackEntity) {
		this.learningTrackEntity = learningTrackEntity;
	}

	public SubTrackEntity(Long id, String name, Date startDate, Date endDate, Boolean isActive, Long createdBy,
			Long updatedBy, Date createdAt, Date updatedAt, LearningTrackEntity learningTrackEntity,
			List<Attendance> attendances) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;

		this.learningTrackEntity = learningTrackEntity;
		this.attendances = attendances;
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

	public SubTrackEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubTrackEntity(Long id, String name, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
