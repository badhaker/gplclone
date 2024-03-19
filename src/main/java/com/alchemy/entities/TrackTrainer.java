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

import com.alchemy.utils.GlobalFunctions;

@Entity
@Table(name = "track_trainer")
public class TrackTrainer {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_id")
	private TrainersMasterEntity trainersMaster;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "track_id")
	private LearningTrackEntity learningTrack;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TrainersMasterEntity getTrainersMaster() {
		return trainersMaster;
	}

	public String getTrainersMasterName() {
		return trainersMaster.getName();
	}

	public Long getTrainerId() {
		return trainersMaster.getId();
	}

	public String getImageUrl() {

		String imageUrl = this.trainersMaster.getFileId() != null
				? GlobalFunctions.getFileUrl(trainersMaster.getFileId().getOriginalName())
				: null;

		return imageUrl;
	}

	public String getDescription() {

		String description = trainersMaster.getDescription();

		return description;
	}

	public Long getDesignationId() {
		Long id = trainersMaster.getDesignation() != null ? trainersMaster.getDesignation().getId() : null;
		return id;
	}

	public String getDesignationName() {
		String name = trainersMaster.getDesignation() != null ? trainersMaster.getDesignation().getName() : null;
		return name;
	}

	public String getDesignationDescription() {
		String description = trainersMaster.getDesignation() != null ? trainersMaster.getDesignation().getDescription()
				: null;
		return description;
	}

	public void setTrainersMaster(TrainersMasterEntity trainersMaster) {
		this.trainersMaster = trainersMaster;
	}

	public LearningTrackEntity getLearningTrack() {
		return learningTrack;
	}

	public void setLearningTrack(LearningTrackEntity learningTrack) {
		this.learningTrack = learningTrack;
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

	public TrackTrainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrackTrainer(Long id, TrainersMasterEntity trainersMaster, LearningTrackEntity learningTrack,
			Boolean isActive, Date createdAt, Date updatedAt, Long createdBy, Long updatedBy) {
		super();
		this.id = id;
		this.trainersMaster = trainersMaster;
		this.learningTrack = learningTrack;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

}
