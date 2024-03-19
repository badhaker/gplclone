package com.alchemy.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name= "invite_entity")
public class InviteEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@GeneratedValue
	@Column(name = "code")
	private UUID code;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getCode() {
		return code;
	}

	public void setCode(UUID code) {
		this.code = code;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public InviteEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InviteEntity(Long id, UUID code, Long userId, Date createdAt) {
		super();
		this.id = id;
		this.code = code;
		this.userId = userId;
		this.createdAt = createdAt;
	}

}
