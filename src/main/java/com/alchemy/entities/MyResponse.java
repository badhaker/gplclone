package com.alchemy.entities;

import java.util.List;

public class MyResponse {

	private String msg;

	private String resp_status;

	private List<GplApiResponceEntity> resp_data;

	public MyResponse(String msg, String resp_status, List<GplApiResponceEntity> resp_data) {
		super();
		this.msg = msg;
		this.resp_status = resp_status;
		this.resp_data = resp_data;
	}

	public MyResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResp_status() {
		return resp_status;
	}

	public void setResp_status(String resp_status) {
		this.resp_status = resp_status;
	}

	public List<GplApiResponceEntity> getResp_data() {
		return resp_data;
	}

	public void setResp_data(List<GplApiResponceEntity> resp_data) {
		this.resp_data = resp_data;
	}
	
	
	@Override
    public String toString() {
        return "{\"msg\":\"" + msg + "\",\"resp_status\":\"" + resp_status + "\",\"resp_data\":" + resp_data + "}";
    }
	
}
