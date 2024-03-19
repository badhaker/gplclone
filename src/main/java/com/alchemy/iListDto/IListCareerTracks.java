package com.alchemy.iListDto;

import org.springframework.beans.factory.annotation.Value;

public interface IListCareerTracks {
	public Long getId();

	public String getName();

	@Value("#{@globalFunctions.getFileUrl(target.Original)}")
	public String getFile();

	@Value("#{@globalFunctions.getFileUrl(target.ThumbnailFileName)}")
	public String getThumbnailFileName();

	public Long getFileId();

}
