package com.alchemy.entities;

import java.util.AbstractMap;
import java.util.Map.Entry;

public enum EnrollStatus {

	ACCEPT("ACCEPT", 0), REJECT("REJECT", 1), HOLD("HOLD", 2), SUBMITTED("SUBMITTED", 3);

	public final String key;
	public final int value;

	private EnrollStatus(String key, int value) {
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

	 public static EnrollStatus valueOf(int value) {
	        for (EnrollStatus status : values()) {
	            if (status.getValue() == value) {
	                return status;
	            }
	        }
	        throw new IllegalArgumentException("Invalid EnrollStatus value: " + value);
	    }

}
