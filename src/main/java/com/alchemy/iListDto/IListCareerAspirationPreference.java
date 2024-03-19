package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

public interface IListCareerAspirationPreference {

	public String getUser();

	public String getEmail();

	public String getExtraDetails();

	public Boolean getCareerMove();

	@Value("#{@globalFunctions.getFileUrl(target.FileUrl)}")
	public String getFileUrl();

	public String getExperience();

	public String getDepartment();

	public String getFunction();

	public String getRole();

	public Long getRoleId();

	public Long getFuncitonId();

	public Long getDepartmentId();

	public String getCity1();

	public String getCity2();

	public String getCity3();

	public Long getCityId1();

	public Long getCityId2();

	public Long getCityId3();

}
