package com.alchemy.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "role_permission")
@Where(clause = "is_active=true")
public class RolePermissionEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private RoleEntity roleId;

	@ManyToOne
	@JoinColumn(name = "permission_id")
	private PermissionEntity permissionId;

	@Column(name = "is_active")
	private boolean isActive = true;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleEntity getRoleId() {
		return roleId;
	}

	public void setRoleId(RoleEntity roleId) {
		this.roleId = roleId;
	}

	public PermissionEntity getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(PermissionEntity permissionId) {
		this.permissionId = permissionId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
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

	public RolePermissionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RolePermissionEntity(RoleEntity roleId, PermissionEntity permissionId) {
		super();
		this.roleId = roleId;
		this.permissionId = permissionId;
	}
}
