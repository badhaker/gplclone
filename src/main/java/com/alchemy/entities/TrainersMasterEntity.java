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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.alchemy.utils.Constant;

@Entity
@Table(name = "trainer_master")
@Where(clause = "is_active=true")
public class TrainersMasterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = Constant.NAME_LENGTH)
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

//	@Column(name = "image_url", length = Constant.IMAGE_URL_LENGTH)
//	private String imageUrl;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id")
	private DesignationEntity designation;

	@JoinColumn(name = "user_id")
	@OneToOne(fetch = FetchType.LAZY)
	private UserEntity user;

	@OneToMany(mappedBy = "trainersMaster", fetch = FetchType.LAZY)
	private List<TrackTrainer> trackTrainer;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")
	private FileUploadEntity fileId;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

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

	public TrainersMasterEntity() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TrackTrainer> getTrackTrainer() {
		return trackTrainer;
	}

	public void setTrackTrainer(List<TrackTrainer> trackTrainer) {
		this.trackTrainer = trackTrainer;
	}

	public DesignationEntity getDesignation() {
		return designation;
	}

	public void setDesignation(DesignationEntity designation) {
		this.designation = designation;
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

	public FileUploadEntity getFileId() {
		return fileId;
	}

	public void setFileId(FileUploadEntity fileId) {
		this.fileId = fileId;
	}

	public TrainersMasterEntity(Long id, String name, String description, DesignationEntity designation,
			UserEntity user, List<TrackTrainer> trackTrainer, FileUploadEntity fileId, Boolean isActive, Date createdAt,
			Date updatedAt, Long createdBy, Long updatedBy) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.designation = designation;
		this.user = user;
		this.trackTrainer = trackTrainer;
		this.fileId = fileId;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

}
