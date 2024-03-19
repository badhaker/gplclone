package com.alchemy.dto;

public class ErrorResponseDto {

	public ErrorResponseDto() {

		// TODO Auto-generated constructor stub
	}

	public ErrorResponseDto(String message, String msgKey) {

		super();
		this.message = message;
		this.msgKey = msgKey;

	}

	private Object data;

	public ErrorResponseDto(String message, String msgKey, Object data) {
		super();

		this.message = message;
		this.msgKey = msgKey;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	private String message;

	private String msgKey;

	public String getMessage() {

		return message;

	}

	public void setMessage(String message) {

		this.message = message;

	}

	public String getMsgKey() {

		return msgKey;

	}

	public void setMsgKey(String msgKey) {

		this.msgKey = msgKey;

	}

}
