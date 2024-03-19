package com.alchemy.iListDto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface IListTrackTrainer {

	public Long getId();

	public String getTrainerName();

	public Long getTrainerId();

	public String getDescription();

	public Long getTrackId();

	public String getTrackName();

	public String getObjective();

	public String getCurriculum();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getStartDate();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getEndDate();

}
