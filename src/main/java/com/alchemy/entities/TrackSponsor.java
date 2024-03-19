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

import com.alchemy.utils.GlobalFunctions;

@Entity
@Table(name = "track_sponsor")
@Where(clause = "is_active=true")
public class TrackSponsor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "track_id")
	private LearningTrackEntity learningTrack;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sponsor_id")
	private SponsorMaster sponsor;

	@Column(name = "sponsor_message", columnDefinition = "TEXT")
	private String sponsorMessage;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LearningTrackEntity getLearningTrack() {
		return learningTrack;
	}

	public void setLearningTrack(LearningTrackEntity learningTrack) {
		this.learningTrack = learningTrack;
	}

	public SponsorMaster getSponsor() {
		return sponsor;
	}

	public String getSponsorName() {
		return sponsor.getName();
	}

	public String getProfile() {
		return sponsor.getProfile();
	}

	public Long getSponsor_id() {
		return sponsor.getId();
	}

	public String getSponsorMessage() {
		return sponsorMessage;
	}

	public void setSponsorMessage(String sponsorMessage) {
		this.sponsorMessage = sponsorMessage;
	}

	public Long getDesignationId() {
		Long id = sponsor.getDesignationId() != null ? sponsor.getDesignationId().getId() : null;
		return id;
	}

	public String getDesignationName() {
		String name = sponsor.getDesignationId() != null ? sponsor.getDesignationId().getName() : null;
		return name;
	}

	public String getSponsorImage() {
		String imageUrl = sponsor.getFileId() != null
				? GlobalFunctions.getFileUrl(sponsor.getFileId().getOriginalName())
				: null;
		return imageUrl;
	}

	public String getDesignationDescription() {
		String description = sponsor.getDesignationId() != null ? sponsor.getDesignationId().getDescription() : null;
		return description;
	}

	public String getDescription() {
		return sponsor.getDescription();
	}

	public void setSponsor(SponsorMaster sponsor) {
		this.sponsor = sponsor;
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

	public TrackSponsor(Long id, LearningTrackEntity learningTrack, SponsorMaster sponsor, String sponsorMessage,
			Long createdBy, Long updatedBy, Boolean isActive, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.learningTrack = learningTrack;
		this.sponsor = sponsor;
		this.sponsorMessage = sponsorMessage;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public TrackSponsor() {
		super();
		// TODO Auto-generated constructor stub
	}

}
