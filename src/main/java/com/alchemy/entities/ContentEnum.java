package com.alchemy.entities;

import java.util.AbstractMap;
import java.util.Map.Entry;

public enum ContentEnum {

	IMAGE("IMAGE",0),VIDEO("VIDEO",1);
	
	public final String key;
	public final int value;

	ContentEnum(String key, Integer value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Integer getValue() {
		return value;
	}

	public Entry<String, Integer> getBoth() {
		return new AbstractMap.SimpleEntry<>(key, value);
	}
}
