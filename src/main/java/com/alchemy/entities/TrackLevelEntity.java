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

@Entity
@Table(name = "track_level")
public class TrackLevelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "track_id")
	private LearningTrackEntity learningTrackId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id")
	private LevelEntity levelId;

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

	public TrackLevelEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrackLevelEntity(Long id, LearningTrackEntity learningTrackId, LevelEntity levelId, Boolean isActive,
			Long createdBy, Long updatedBy, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.learningTrackId = learningTrackId;
		this.levelId = levelId;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LearningTrackEntity getLearningTrackId() {
		return learningTrackId;
	}

	public void setLearningTrackId(LearningTrackEntity learningTrackId) {
		this.learningTrackId = learningTrackId;
	}

	public LevelEntity getLevelId() {
		return levelId;
	}

	public void setLevelId(LevelEntity levelId) {
		this.levelId = levelId;
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

	public Long getLevel() {
		return this.levelId.getId();
	}

	public String getLevelName() {
		return this.levelId.getLevelName();
	}

	public String getLevelDescription() {
		return this.levelId.getDescription();
	}
}
