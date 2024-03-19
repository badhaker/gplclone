package com.alchemy.dto;

import java.util.ArrayList;

public class TrackTrainerDto {

	private ArrayList<Long> trainerId;

	private Long learningId;

	public ArrayList<Long> getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(ArrayList<Long> trainerId) {
		this.trainerId = trainerId;
	}

	public Long getLearningId() {
		return learningId;
	}

	public void setLearningId(Long learningId) {
		this.learningId = learningId;
	}

}
