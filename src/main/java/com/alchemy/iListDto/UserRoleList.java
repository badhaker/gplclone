package com.alchemy.iListDto;

import java.util.List;

public class UserRoleList {

	public Long userId;

	public String userName;

	List<RoleList> roleList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<RoleList> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleList> roleList) {
		this.roleList = roleList;
	}

}
