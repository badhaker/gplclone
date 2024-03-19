package com.alchemy.serviceInterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.TrainersMasterDto;
import com.alchemy.iListDto.IListTrainer;

public interface TrainersMasterInterface {

	public TrainersMasterDto addTrainer(TrainersMasterDto trainersMasterDtocc, Long userId, MultipartFile file , HttpServletRequest request) throws Exception;

	public TrainersMasterDto editTrainer(Long trainerId, TrainersMasterDto trainersMasterDto, Long userId, HttpServletRequest request, MultipartFile file)
			throws Exception;

	public void deleteTrainer(Long trainerId);

	Page<IListTrainer> getAllTrainers(String search, String pageNo, String pageSize) throws Exception;

	List<IListTrainer> findAllTrainers();
	
	IListTrainer findById(Long trainerId);

	public void deleteMultipleTrainersById(DeleteId id, Long userId);

}
