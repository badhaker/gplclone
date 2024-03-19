package com.alchemy.iListDto;

public class PreferenceList {

	private String role;

	private Long roleId;

	private String function;

	private Long funcitonId;

	private String experience;

	private String deprtment;

	private Long departmentId;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getDeprtment() {
		return deprtment;
	}

	public void setDeprtment(String deprtment) {
		this.deprtment = deprtment;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getFuncitonId() {
		return funcitonId;
	}

	public void setFuncitonId(Long funcitonId) {
		this.funcitonId = funcitonId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public PreferenceList(String role, String function, String experience, String deprtment) {
		super();
		this.role = role;
		this.function = function;
		this.experience = experience;
		this.deprtment = deprtment;
	}

	public PreferenceList(String role, Long roleId, String function, Long funcitonId, String experience,
			String deprtment, Long departmentId) {
		super();
		this.role = role;
		this.roleId = roleId;
		this.function = function;
		this.funcitonId = funcitonId;
		this.experience = experience;
		this.deprtment = deprtment;
		this.departmentId = departmentId;
	}

	public PreferenceList() {
		super();
		// TODO Auto-generated constructor stub
	}

}
