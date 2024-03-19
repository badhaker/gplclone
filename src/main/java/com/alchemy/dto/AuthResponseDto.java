package com.alchemy.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class AuthResponseDto {

	private String jwtToken;

	private String refreshToken;

	private ArrayList<String> permission;

	private String email;

	private String name;

	private Long id;

	private List<String> roles;

	private boolean updateCareerAspiration;

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public ArrayList<String> getPermission() {
		return permission;
	}

	public void setPermission(ArrayList<String> permission) {
		this.permission = permission;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public AuthResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isUpdateCareerAspiration() {
		return updateCareerAspiration;
	}

	public void setUpdateCareerAspiration(boolean updateCareerAspiration) {
		this.updateCareerAspiration = updateCareerAspiration;
	}

	@Override
	public String toString() {

// Create a new JSON object
		JSONObject jsonObject = new JSONObject();

// Add the key-value pairs to the JSON object
		jsonObject.put("jwtToken", jwtToken);
		jsonObject.put("refreshToken", refreshToken);
		jsonObject.put("permission", permission);
		jsonObject.put("email", email);
		jsonObject.put("name", name);
		jsonObject.put("id", id);
//		jsonObject.put("careerAspiration", careerAspiration);
		jsonObject.put("updateCareerAspiration", updateCareerAspiration);
		jsonObject.put("roles", roles);

// Convert the JSON object to a string
		return jsonObject.toString();
	}

	public AuthResponseDto(String jwtToken, String refreshToken, ArrayList<String> permission, String email,
			String name, Long id, List<String> roles, boolean updateCareerAspiration) {
		super();
		this.jwtToken = jwtToken;
		this.refreshToken = refreshToken;
		this.permission = permission;
		this.email = email;
		this.name = name;
		this.id = id;
		this.roles = roles;
		this.updateCareerAspiration = updateCareerAspiration;
	}

}
