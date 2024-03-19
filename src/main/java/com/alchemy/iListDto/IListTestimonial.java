package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

public interface IListTestimonial {

	public Long getId();

	public String getTestimonial();

	public String getUserName();

	@Value("#{@globalFunctions.getFileUrl(target.FileUrl)}")
	public String getFileUrl();

	public String getDescription();

	public Long getDesignationId();

	public String getDesignationName();

	public Long getFunctionId();

	public String getFunctionName();

	public Boolean getIsVisible();
}
