package com.alchemy.entities;

import java.io.Serializable;
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

import com.alchemy.utils.GlobalFunctions;

@Entity
@Table(name = "learning_track")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE learning_track SET is_active=false WHERE id=?")
public class LearningTrackEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "learning_track_name", length = 100)
	private String name;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "objective", columnDefinition = "TEXT")
	private String objective;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "file_id")
	private FileUploadEntity fileId;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "banner_file_id")
	private FileUploadEntity bannerFileId;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "file_card_id")
	private FileUploadEntity bannerCard;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "nudged_file_id")
	private FileUploadEntity nudgedFileId;

	@Column(name = "summary", length = 500)
	private String summary;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "is_visible")
	private Boolean isVisible = false;

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

	@Column(name = "enroll_close_date")
	private Date enrollCloseDate;

	@Column(name = "enroll_start_date")
	private Date enrollStartDate;

	public Date getEnrollCloseDate() {
		return enrollCloseDate;
	}

	public void setEnrollCloseDate(Date enrollCloseDate) {
		this.enrollCloseDate = enrollCloseDate;
	}

	@OneToMany(mappedBy = "learningTrack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TrackSponsor> trackSponsors;

	@OneToMany(mappedBy = "learningTrackEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TrackGplFunctionEntity> gplFunctionEntities;

	@OneToMany(mappedBy = "learningTrack", fetch = FetchType.LAZY)
	private List<TrackTrainer> trackTrainer;

	@OneToMany(mappedBy = "trackId", fetch = FetchType.LAZY)
	private List<UserTrackEntity> userTrack;

	@OneToMany(mappedBy = "trackId", fetch = FetchType.LAZY)
	private List<NudgedTracks> nudgedTracks;

	@OneToMany(mappedBy = "learningTrackEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SubTrackEntity> subTrackEntity;

	@OneToMany(mappedBy = "learningTrackId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EnrollNowEntity> enrollNowEntitiy;

	@OneToMany(mappedBy = "learningTrackId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TrackLevelEntity> levelEntities;

	public List<EnrollNowEntity> getEnrollNowEntitiy() {
		return enrollNowEntitiy;
	}

	public void setEnrollNowEntitiy(List<EnrollNowEntity> enrollNowEntitiy) {
		this.enrollNowEntitiy = enrollNowEntitiy;
	}

	@OneToMany(mappedBy = "learningTrackId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Attendance> attendances;

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public LearningTrackEntity(List<SubTrackEntity> subTrackEntity) {
		super();
		this.subTrackEntity = subTrackEntity;
	}

	public List<SubTrackEntity> getSubTrackEntity() {
		return subTrackEntity;
	}

	public void setSubTrackEntity(List<SubTrackEntity> subTrackEntity) {
		this.subTrackEntity = subTrackEntity;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
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

	public List<TrackSponsor> getTrackSponsors() {
		return trackSponsors;
	}

	public void setTrackSponsors(List<TrackSponsor> trackSponsors) {
		this.trackSponsors = trackSponsors;
	}

	public List<TrackTrainer> getTrackTrainer() {
		return trackTrainer;
	}

	public void setTrackTrainer(List<TrackTrainer> trackTrainer) {
		this.trackTrainer = trackTrainer;
	}

	public List<UserTrackEntity> getUserTrack() {
		return userTrack;
	}

	public void setUserTrack(List<UserTrackEntity> userTrack) {
		this.userTrack = userTrack;
	}

	public FileUploadEntity getFileId() {
		return fileId;
	}

	public void setFileId(FileUploadEntity fileId) {
		this.fileId = fileId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public FileUploadEntity getBannerFileId() {
		return bannerFileId;
	}

	public void setBannerFileId(FileUploadEntity bannerFileId) {
		this.bannerFileId = bannerFileId;
	}

	public FileUploadEntity getBannerCard() {
		return bannerCard;
	}

	public void setBannerCard(FileUploadEntity bannerCard) {
		this.bannerCard = bannerCard;
	}

	public List<TrackLevelEntity> getLevelEntities() {
		return levelEntities;
	}

	public void setLevelEntities(List<TrackLevelEntity> levelEntities) {
		this.levelEntities = levelEntities;
	}

	public Date getEnrollStartDate() {
		return enrollStartDate;
	}

	public void setEnrollStartDate(Date enrollStartDate) {
		this.enrollStartDate = enrollStartDate;
	}

	public LearningTrackEntity() {
		super();
	}

	public List<TrackGplFunctionEntity> getGplFunctionEntities() {
		return gplFunctionEntities;
	}

	public void setGplFunctionEntities(List<TrackGplFunctionEntity> gplFunctionEntities) {
		this.gplFunctionEntities = gplFunctionEntities;
	}

	public String getFileUrl() {

		String url = fileId != null ? GlobalFunctions.getFileUrl(fileId.getOriginalName()) : null;
		return url;
	}

	public Long getEnrollCount() {
		Long count = (long) 0;
		for (int i = 0; i < this.userTrack.size(); i++) {

			EnrollStatus abc = EnrollStatus.valueOf("ACCEPT");
			if (userTrack.get(i).getEnrollStatus() == null) {
				continue;
			} else if (userTrack.get(i).getSubtrackId() == null && userTrack.get(i).getEnrollStatus() == abc.value) {
				count++;
			}
		}
		return count;
	}

	public String getFileName() {
		String fileurl = fileId != null ? fileId.getFilename() : null;
		return fileurl;

	}

	public String getBannerUrl() {

		String url = bannerFileId != null ? GlobalFunctions.getFileUrl(bannerFileId.getOriginalName()) : null;
		return url;
	}

	public String getBannerCardUrl() {

		String url = bannerCard != null ? GlobalFunctions.getFileUrl(bannerCard.getOriginalName()) : null;
		return url;
	}

	public LearningTrackEntity(Long id, String name, Date startDate, Date endDate, String objective,
			FileUploadEntity fileId, FileUploadEntity bannerFileId, FileUploadEntity bannerCard, String summary,
			Boolean isActive, Boolean isVisible, Long createdBy, Long updatedBy, Date createdAt, Date updatedAt,
			Date enrollCloseDate, Date enrollStartDate, List<TrackSponsor> trackSponsors,
			List<TrackGplFunctionEntity> gplFunctionEntities, List<TrackTrainer> trackTrainer,
			List<UserTrackEntity> userTrack, List<SubTrackEntity> subTrackEntity,
			List<EnrollNowEntity> enrollNowEntitiy, List<TrackLevelEntity> levelEntities,
			List<Attendance> attendances) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.objective = objective;
		this.fileId = fileId;
		this.bannerFileId = bannerFileId;
		this.bannerCard = bannerCard;
		this.summary = summary;
		this.isActive = isActive;
		this.isVisible = isVisible;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enrollCloseDate = enrollCloseDate;
		this.enrollStartDate = enrollStartDate;
		this.trackSponsors = trackSponsors;
		this.gplFunctionEntities = gplFunctionEntities;
		this.trackTrainer = trackTrainer;
		this.userTrack = userTrack;
		this.subTrackEntity = subTrackEntity;
		this.enrollNowEntitiy = enrollNowEntitiy;
		this.levelEntities = levelEntities;
		this.attendances = attendances;
	}

	public List<NudgedTracks> getNudgedTracks() {
		return nudgedTracks;
	}

	public void setNudgedTracks(List<NudgedTracks> nudgedTracks) {
		this.nudgedTracks = nudgedTracks;
	}

	public FileUploadEntity getNudgedFileId() {
		return nudgedFileId;
	}

	public void setNudgedFileId(FileUploadEntity nudgedFileId) {
		this.nudgedFileId = nudgedFileId;
	}

	public String getNudgedFileUrl() {
		String url = nudgedFileId != null ? GlobalFunctions.getFileUrl(nudgedFileId.getOriginalName()) : null;
		return url;
	}

}
