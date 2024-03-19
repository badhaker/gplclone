package com.alchemy.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.alchemy.utils.Constant;

@Entity
@Table(name = "testimonial")
@Where(clause = "is_active=true")
public class TestimonialEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "testimonial", length = 500)
	private String testimonial;

	@Column(name = "user_name", length = Constant.NAME_LENGTH)
	private String userName;

// instead of this url now file is used	
//	@Column(name = "image_url", length = Constant.IMAGE_URL_LENGTH)
//	private String imageUrl;

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "is_visible")
	private Boolean isVisible = true;

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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id")
	private DesignationEntity designation;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "department_id")
//	private DepartmentEntity department;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "function_id")
	private GplFunctionEntity function;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")
	private FileUploadEntity fileId;

	public TestimonialEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestimonial() {
		return testimonial;
	}

	public void setTestimonial(String testimonial) {
		this.testimonial = testimonial;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DesignationEntity getDesignation() {
		return designation;
	}

	public void setDesignation(DesignationEntity designation) {
		this.designation = designation;
	}

	public GplFunctionEntity getFunction() {
		return function;
	}

	public void setFunction(GplFunctionEntity function) {
		this.function = function;
	}

	public FileUploadEntity getFileId() {
		return fileId;
	}

	public void setFileId(FileUploadEntity fileId) {
		this.fileId = fileId;
	}

}
