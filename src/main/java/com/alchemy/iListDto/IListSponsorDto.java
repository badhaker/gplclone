package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

import com.alchemy.entities.ContentEnum;

public interface IListSponsorDto {

	public Long getId();

	public String getDescription();

	public String getSponsorName();

	
	
	public String getDesignationId();

	@Value("#{@globalFunctions.getFileUrl(target.FileUrl)}")
	public String getFileUrl();

	
	
	public String getDesignationName();

	public String getProfile();

	public String getUserName();

	public Long getUserId();
}
