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
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

@Entity
@Table(name = "level")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE level SET is_active=false WHERE id=?")
public class LevelEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "level_name")
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.LEVEL_E031404)
	private String levelName;

	@Column(name = "description")
	@Size(max = Constant.DESCRIPTION_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION + "*"
			+ ErrorMessageKey.LEVEL_E031404)
	private String description;

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

	@OneToMany(mappedBy = "levelId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserEntity> userEntity;

	@OneToMany(mappedBy = "levelId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TrackLevelEntity> learningTrackEntities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LevelEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LevelEntity(Long id,
			@Size(min = 1, max = 255, message = "Level name required**GPL-E031404") String levelName,
			String description, Boolean isActive, Long createdBy, Long updatedBy, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.levelName = levelName;
		this.description = description;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
