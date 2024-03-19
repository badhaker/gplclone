package com.alchemy.dto;

public class PaginationResponse {

	private int pageNo;
	private int pageSize;
	private Long total;

	public int getPageNumber() {
		return pageNo;
	}

	public void setPageNumber(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PaginationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public PaginationResponse(int pageNo, int pageSize, Long total) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
	}

}
