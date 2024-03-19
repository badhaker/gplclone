package com.alchemy.iListDto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SubTrackList {
	private Long subTrackId;
	private String subTrackName;
	public String imageUrl;
	public String fileUrl;
    private Float preAssesment;
	private Float postAssesment;
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	private boolean isStartPemformer;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date completeDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date subStartDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date subEndDate;

	public Long getSubTrackId() {
		return subTrackId;
	}

	public void setSubTrackId(Long subTrackId) {
		this.subTrackId = subTrackId;
	}

	public String getSubTrackName() {
		return subTrackName;
	}

	public void setSubTrackName(String subTrackName) {
		this.subTrackName = subTrackName;
	}

	public boolean isStartPemformer() {
		return isStartPemformer;
	}

	public void setStartPemformer(boolean isStartPemformer) {
		this.isStartPemformer = isStartPemformer;
	}

	public Date getSubStartDate() {
		return subStartDate;
	}

	public void setSubStartDate(Date subStartDate) {
		this.subStartDate = subStartDate;
	}

	public Date getSubEndDate() {
		return subEndDate;
	}

	public void setSubEndDate(Date subEndDate) {
		this.subEndDate = subEndDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Float getPreAssesment() {
		return preAssesment;
	}

	public void setPreAssesment(Float preAssesment) {
		this.preAssesment = preAssesment;
	}

	public Float getPostAssesment() {
		return postAssesment;
	}

	public void setPostAssesment(Float postAssesment) {
		this.postAssesment = postAssesment;
	}

}
