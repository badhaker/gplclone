package com.alchemy.entities;

import java.util.AbstractMap;
import java.util.Map.Entry;

public enum CareerAspirationEnum {
	NOT_AT_ALL("NOT_AT_ALL", 0), NOT_REALLY("NOT_REALLY", 1), UNDECIDED("UNDECIDED", 2), SOMEWHAT("SOMEWHAT", 3),
	VERY_MUCH("VERY_MUCH", 4);

	public final String key;
	public final int value;

	private CareerAspirationEnum(String key, int value) {
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
