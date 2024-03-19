package com.alchemy.entities;

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

import org.hibernate.annotations.Where;

import com.alchemy.utils.Constant;

@Entity
@Table(name = "sponsors")
@Where(clause = "is_active=true")
public class SponsorMaster {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sponsor_name", length = Constant.NAME_LENGTH)
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id")
	private DesignationEntity designationId;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "profile", length = Constant.PROFILE_LENGTH)
	private String profile;

	@JoinColumn(name = "user_id")
	@OneToOne(fetch = FetchType.LAZY)
	private UserEntity user;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")
	private FileUploadEntity fileId;

	@OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TrackSponsor> trackSponsors;

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public List<TrackSponsor> getTrackSponsors() {
		return trackSponsors;
	}

	public void setTrackSponsors(List<TrackSponsor> trackSponsors) {
		this.trackSponsors = trackSponsors;
	}

	public DesignationEntity getDesignationId() {
		return designationId;
	}

	public String getDesignationName() {
		return designationId.getName();
	}

	public void setDesignationId(DesignationEntity designationId) {
		this.designationId = designationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public SponsorMaster(Long id, String name, DesignationEntity designationId, String description, Long createdBy,
			Long updatedBy, Boolean isActive, String profile, UserEntity user, FileUploadEntity fileId,
			List<TrackSponsor> trackSponsors) {
		super();
		this.id = id;
		this.name = name;
		this.designationId = designationId;
		this.description = description;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isActive = isActive;
		this.profile = profile;
		this.user = user;
		this.fileId = fileId;
		this.trackSponsors = trackSponsors;
	}

	public SponsorMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

}