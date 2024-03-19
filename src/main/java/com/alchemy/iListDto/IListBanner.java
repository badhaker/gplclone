package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

import com.alchemy.entities.ContentEnum;

public interface IListBanner {

	public Long getId();

	public Long getFileId();

	public ContentEnum getShowContent();

	public String getDescription();

	@Value("#{@globalFunctions.getFileUrl(target.FileUrl)}")
	public String getFileUrl();

	public String getName();

	public Integer getDisplayOrder();

	public Boolean getIsVisible();

	public String getVideoUrl();
}
