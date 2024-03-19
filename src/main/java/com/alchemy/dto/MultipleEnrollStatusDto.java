package com.alchemy.dto;

import java.util.List;

import com.alchemy.entities.EnrollStatus;
import com.alchemy.utils.ValidEnum;

public class MultipleEnrollStatusDto {

	private List<Long> ids;

	@ValidEnum(enumClass = EnrollStatus.class)
	private EnrollStatus status;
	
    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

	public EnrollStatus getStatus() {
		return status;
	}

	public void setStatus(EnrollStatus status) {
		this.status = status;
	}

	
}
