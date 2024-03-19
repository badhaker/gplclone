package com.alchemy.entities;

import java.util.AbstractMap;
import java.util.Map.Entry;

public enum LockAttendance {

	LOCKED("LOCKED", 0), UNLOCKED("UNLOCKED", 1);

	public final String key;
	public final int value;

	private LockAttendance(String key, int value) {
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

	 public static LockAttendance valueOf(int value) {
	        for (LockAttendance status : values()) {
	            if (status.getValue() == value) {
	                return status;
	            }
	        }
	        throw new IllegalArgumentException("Invalid LockAttendance value: " + value);
	    }
	
}
