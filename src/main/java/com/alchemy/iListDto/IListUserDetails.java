package com.alchemy.iListDto;

import java.util.List;

public class IListUserDetails {

	private Long userId;
	private String userName;

	private List<TrackList> TrackList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<TrackList> getTrackList() {
		return TrackList;
	}

	public void setTrackList(List<TrackList> trackList) {
		TrackList = trackList;
	}

}
