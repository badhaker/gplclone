package com.alchemy.entities;

import java.util.AbstractMap;
import java.util.Map.Entry;

public enum AttendanceStatus {
	PRESENT("PRESENT", 0), ABSENT("ABSENT", 1), NOATTENDANCE("NOATTENDANCE", 2);

	public final String key;
	public final int value;

	private AttendanceStatus(String key, int value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public int getValue() {
		return value;
	}

	public Entry<String, Integer> getBoth() {
		return new AbstractMap.SimpleEntry<>(key, value);
	}
	
	 public static AttendanceStatus valueOf(int value) {
	        for (AttendanceStatus status : values()) {
	            if (status.getValue() == value) {
	                return status;
	            }
	        }
	        throw new IllegalArgumentException("Invalid AttendanceStatus value: " + value);
	    }

}
