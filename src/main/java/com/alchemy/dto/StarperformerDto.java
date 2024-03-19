package com.alchemy.dto;

import java.util.List;

public class StarperformerDto {

	private Boolean starperformer;
	
	private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

	public Boolean getStarperformer() {
		return starperformer;
	}

	public void setStarperformer(Boolean starperformer) {
		this.starperformer = starperformer;
	}
	
}
