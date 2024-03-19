package com.alchemy.dto;

import java.util.List;

import com.alchemy.entities.LockAttendance;

public class MultiLockRequestDto {

	private List<Long> ids;

	private Boolean lock;
	
    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

	public Boolean getLock() {
		return lock;
	}

	public void setLock(Boolean lock) {
		this.lock = lock;
	}

	
}
