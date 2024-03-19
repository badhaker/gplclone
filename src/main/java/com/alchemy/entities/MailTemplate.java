package com.alchemy.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "mail_template")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE mail_template SET is_active=false WHERE id=?")
public class MailTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mailtemp", length = 100000)
	private String mailtemp;

	@Column(name = "templatename")
	private String templatename;

	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Timestamp updatedAt;

	@Column(name = "is_active")
	private Boolean isActive = true;

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

	public MailTemplate(Long id, String mailtemp, String templatename) {
		super();
		this.id = id;
		this.mailtemp = mailtemp;
		this.templatename = templatename;
	}

	public MailTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMailtemp() {
		return mailtemp;
	}

	public void setMailtemp(String mailtemp) {
		this.mailtemp = mailtemp;
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

}