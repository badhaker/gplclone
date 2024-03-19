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
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "track_function")
@Where(clause = "is_active=true")
public class TrackGplFunctionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Track_id")
	private LearningTrackEntity learningTrackEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "function_id")
	private GplFunctionEntity gplFunctionEntity;

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

	public LearningTrackEntity getLearningTrackEntity() {
		return learningTrackEntity;
	}

	public void setLearningTrackEntity(LearningTrackEntity learningTrackEntity) {
		this.learningTrackEntity = learningTrackEntity;
	}

	public GplFunctionEntity getGplFunctionEntity() {
		return gplFunctionEntity;
	}

	public void setGplFunctionEntity(GplFunctionEntity gplFunctionEntity) {
		this.gplFunctionEntity = gplFunctionEntity;
	}

	public TrackGplFunctionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrackGplFunctionEntity(Long id, Boolean isActive, Date createdAt, Date updatedAt, Long createdBy,
			Long updatedBy, LearningTrackEntity learningTrackEntity, GplFunctionEntity gplFunctionEntity) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.learningTrackEntity = learningTrackEntity;
		this.gplFunctionEntity = gplFunctionEntity;
	}

	public Long getFunctionId() {
		Long functionId = gplFunctionEntity != null ? gplFunctionEntity.getId() : null;
		return functionId;
	}

	public String getFunctionName() {
		String functionName = gplFunctionEntity != null ? gplFunctionEntity.getName() : null;
		return functionName;
	}

}
