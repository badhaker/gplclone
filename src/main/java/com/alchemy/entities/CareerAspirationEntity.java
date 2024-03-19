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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "career_aspiration")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE career_aspiration SET is_active=false WHERE id=?")
public class CareerAspirationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToMany(mappedBy = "careerAspirationId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// @JoinColumn(name = "preference_id")
	private List<PreferenceEntity> preferenceEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")
	private FileUploadEntity file;

	@Column(name = "additional_Details", length = 1000)
	private String additionalDetails;

	@Column(name = "next_career_Move")
	private Boolean nextcareerMove;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity userId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "city1")
	private CityMasterEntity city1;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "city2")
	private CityMasterEntity city2;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "city3")
	private CityMasterEntity city3;

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

	public CareerAspirationEntity(Long id, FileUploadEntity file, String additionalDetails, Boolean nextcareerMove,
			Boolean isActive, Long createdBy, Long updatedBy, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.file = file;
		this.additionalDetails = additionalDetails;
		this.nextcareerMove = nextcareerMove;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public List<PreferenceEntity> getPreferenceEntity() {
		return preferenceEntity;
	}

	public void setPreferenceEntity(List<PreferenceEntity> preferenceEntity) {
		this.preferenceEntity = preferenceEntity;
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

	public CareerAspirationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FileUploadEntity getFile() {
		return file;
	}

	public void setFile(FileUploadEntity file) {
		this.file = file;
	}

	public String getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	public Boolean getNextcareerMove() {
		return nextcareerMove;
	}

	public void setNextcareerMove(Boolean nextcareerMove) {
		this.nextcareerMove = nextcareerMove;
	}

	public UserEntity getUserId() {
		return userId;
	}

	public void setUserId(UserEntity userId) {
		this.userId = userId;
	}

	public CareerAspirationEntity(Long id, List<PreferenceEntity> preferenceEntity, FileUploadEntity file,
			String additionalDetails, Boolean nextcareerMove, Boolean isActive, UserEntity userId, Long createdBy,
			Long updatedBy, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.preferenceEntity = preferenceEntity;
		this.file = file;
		this.additionalDetails = additionalDetails;
		this.nextcareerMove = nextcareerMove;
		this.isActive = isActive;
		this.userId = userId;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public CityMasterEntity getCity1() {
		return city1;
	}

	public void setCity1(CityMasterEntity city1) {
		this.city1 = city1;
	}

	public CityMasterEntity getCity2() {
		return city2;
	}

	public void setCity2(CityMasterEntity city2) {
		this.city2 = city2;
	}

	public CityMasterEntity getCity3() {
		return city3;
	}

	public void setCity3(CityMasterEntity city3) {
		this.city3 = city3;
	}

}
