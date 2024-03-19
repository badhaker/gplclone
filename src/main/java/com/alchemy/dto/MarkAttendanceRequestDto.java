package com.alchemy.dto;

import java.util.List;

import com.alchemy.entities.AttendanceStatus;
import com.alchemy.utils.ValidEnum;


public class MarkAttendanceRequestDto {

	private List<Long> ids;

	@ValidEnum(enumClass = AttendanceStatus.class)
	private AttendanceStatus attendance;
	
    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

	public AttendanceStatus getAttendance() {
		return attendance;
	}

	public void setAttendance(AttendanceStatus attendance) {
		this.attendance = attendance;
	}

	
}
