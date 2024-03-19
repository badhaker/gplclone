package com.alchemy.dto;

import java.util.List;

import com.alchemy.iListDto.PreferenceList;

public class CareerAspirationPrefereceDto {

	public String username;

	public String email;

	public String extraDetails;

	public Boolean nextCareerMove;

	public String fileUrl;

	public List<PreferenceList> preference;

	public List<CityList> cities;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getExtraDetails() {
		return extraDetails;
	}

	public void setExtraDetails(String extraDetails) {
		this.extraDetails = extraDetails;
	}

	public Boolean getNextCareerMove() {
		return nextCareerMove;
	}

	public void setNextCareerMove(Boolean nextCareerMove) {
		this.nextCareerMove = nextCareerMove;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public List<PreferenceList> getPreference() {
		return preference;
	}

	public void setPreference(List<PreferenceList> preference) {
		this.preference = preference;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CareerAspirationPrefereceDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<CityList> getCities() {
		return cities;
	}

	public void setCities(List<CityList> cities) {
		this.cities = cities;
	}

}
