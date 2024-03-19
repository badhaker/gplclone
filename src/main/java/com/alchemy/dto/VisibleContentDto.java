package com.alchemy.dto;

import java.util.List;

public class VisibleContentDto {

	public List<Long> ids;

	public Boolean isVisible;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

}
