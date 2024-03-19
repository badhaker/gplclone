package com.alchemy.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "gpl_token")
public class GplToken {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "gpl_token", length = 500)
	private String gplToken;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "expired_at")
	private Date expiredAt;

	@Column(name = "is_active")
	private Boolean is_active = true;

	@Column(name = "email", unique = true)
	private String email;

	public GplToken(Long id, String gplToken, Date createdAt, Date expiredAt, Boolean is_active, String email) {
		super();
		this.id = id;
		this.gplToken = gplToken;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
		this.is_active = is_active;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public GplToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGplToken() {
		return gplToken;
	}

	public void setGplToken(String gplToken) {
		this.gplToken = gplToken;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

}
