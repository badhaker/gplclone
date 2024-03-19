package com.alchemy.entities;

import java.util.AbstractMap;
import java.util.Map.Entry;

public enum Status {

	TO_DO("TO_DO", 0), IN_PROGRESS("IN_PROGRESS", 1), COMPLETE("COMPLETE", 2);

	public final String key;
	public final int value;

	private Status(String key, int value) {
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

}
