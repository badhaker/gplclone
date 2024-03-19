package com.alchemy.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "certificate_master")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE certificate_master SET is_active=false WHERE id=?")
public class CertificateMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "template_body", length = 2500)
	private String templateBody;

	
	@Column(name = "template_name",unique=true, length= Constant.NAME_LENGTH)
	private String templateName;

	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Timestamp updatedAt;

	@Column(name = "is_active")
	private Boolean isActive = true;
	
	@JoinColumn(name = "created_by")
	private Long createdBy;

	@JoinColumn(name = "updated_by")
	private Long updatedBy;
	
	public CertificateMaster(Long id, String templateBody, String templateName, Timestamp createdAt,
			Timestamp updatedAt, Boolean isActive, Long createdBy, Long updatedBy) {
		super();
		this.id = id;
		this.templateBody = templateBody;
		this.templateName = templateName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	public String getTemplateBody() {
		return templateBody;
	}

	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public CertificateMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
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
	
	

}

