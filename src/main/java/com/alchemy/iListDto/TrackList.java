package com.alchemy.iListDto;

import java.util.Date;
import java.util.List;

import com.alchemy.entities.EnrollStatus;
import com.alchemy.entities.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TrackList {

	private Long trackId;

	private String trackName;

	public String fileUrl;

	public String imageUrl;

	public String description;

	public EnrollStatus enrollStatus;

	public Status trackStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date endDate;

	private List<SubTrackList> SubTrackList;

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

	public Long getTrackId() {
		return trackId;
	}

	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public List<SubTrackList> getSubTrackList() {
		return SubTrackList;
	}

	public void setSubTrackList(List<SubTrackList> subTrackList) {
		SubTrackList = subTrackList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EnrollStatus getEnrollStatus() {
		return enrollStatus;
	}

	public void setEnrollStatus(EnrollStatus enrollStatus) {
		this.enrollStatus = enrollStatus;
	}

	public Status getTrackStatus() {
		return trackStatus;
	}

	public void setTrackStatus(Status trackStatus) {
		this.trackStatus = trackStatus;
	}

}
