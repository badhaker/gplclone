package com.alchemy.entities;

import java.util.Date;

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

@Entity
@Table(name = "auth_logger")
public class LoggerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserEntity userId;

	@CreationTimestamp
	@Column(name = "login_at")
	private Date loginAt;

	@Column(name = "expired_at")
	private Date expiredAt;

	@Column(name = "gpl_token")
	private String gplToken;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserEntity getUserId() {
		return userId;
	}

	public void setUserId(UserEntity userId) {
		this.userId = userId;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

	public String getGplToken() {
		return gplToken;
	}

	public void setGplToken(String gplToken) {
		this.gplToken = gplToken;
	}

	public LoggerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoggerEntity(long id, UserEntity userId, Date loginAt, Date expiredAt, String gplToken) {
		super();
		this.id = id;
		this.userId = userId;
		this.loginAt = loginAt;
		this.expiredAt = expiredAt;
		this.gplToken = gplToken;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", userId:" + userId + "}";
	}

}
