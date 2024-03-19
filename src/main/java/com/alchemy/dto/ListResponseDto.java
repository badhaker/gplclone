package com.alchemy.dto;

public class ListResponseDto {

	public ListResponseDto() {

		// TODO Auto-generated constructor stub
	}

	public ListResponseDto(Object data, Object count) {

		super();
		this.data = data;
		this.count = count;

	}

	public ListResponseDto(Long count) {

		super();
		this.count = count;

	}

	private Object data;

	private Object count;

	public Object getData() {

		return data;

	}

	public void setData(Object data) {

		this.data = data;

	}

	public Object getCount() {

		return count;

	}

	public void setCount(Long count) {

		this.count = count;

	}

}
