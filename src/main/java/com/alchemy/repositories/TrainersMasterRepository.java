package com.alchemy.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.TrainersMasterEntity;
import com.alchemy.iListDto.IListTrainer;

@Repository
public interface TrainersMasterRepository extends JpaRepository<TrainersMasterEntity, Long> {

	@Query(value = "select tm.id as TrainerId,tm.name as TrainerName,tm.description as Description,d.name as Designation,d.id as DesignationId,"
			+ "tm.file_id as FileId , fu.original_name as FileUrl,\r\n"
			+ "			u.id as userId, u.user_name as userName \r\n"
			+ "			from trainer_master tm \r\n"
			+ "			left join users u on tm.user_id=u.id and u.is_active=true "
			+ " left join file_upload fu on fu.id=tm.file_id  \r\n"
			+ "			left join designation d on d.id=tm.designation_id and d.is_active=true where tm.is_active=true order by tm.id desc\r\n", nativeQuery = true)
	Page<IListTrainer> findByOrderByIdDesc(Pageable pageable, Class<IListTrainer> class1);

	@Query(value = "select tm.id as TrainerId,tm.name as TrainerName,tm.description as Description,d.name as Designation,d.id as DesignationId,\r\n"
			+ "	u.id as userId, u.user_name as userName,tm.file_id as FileId, fu.original_name as FileUrl\r\n"
			+ "	from trainer_master tm\r\n"
			+ "	left join users u on tm.user_id=u.id and u.is_active=true "
			+ " left join file_upload fu on fu.id = tm.file_id \r\n"
			+ "	left join designation d on d.id=tm.designation_id and d.is_active=true where tm.is_active=true \r\n"
			+ "AND (:search = ' ' OR tm.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%' \r\n"
			+ "	OR d.name ILIKE '%' || REPLACE(REPLACE(REPLACE(REPLACE(:search, '%', '\\%'), '_', '\\_'), '[', '\\['), ']', '\\]') || '%')\r\n"
			+ "order by tm.id desc", nativeQuery = true)
	Page<IListTrainer> findAllTrainers(@Param("search") String search, Pageable pageable, Class<IListTrainer> class1);

	@Query(value = "select tm.id as TrainerId,tm.name as TrainerName,tm.description as Description,d.name as Designation,d.id as DesignationId,\r\n"
			+ "			u.id as userId, u.user_name as userName,"
			+ "tm.file_id as FileId , fu.original_name as FileUrl\r\n"
			+ "			from trainer_master tm\r\n"
			+ "			left join users u on tm.user_id=u.id and u.is_active=true "
			+ " left join file_upload fu on fu.id= tm.file_id  \r\n"
			+ "			left join designation d on d.id=tm.designation_id and d.is_active=true where tm.is_active=true order by tm.id desc\r\n"
			+ "	", nativeQuery = true)
	List<IListTrainer> findAllTrainers(Class<IListTrainer> class1);

	@Query(value = "select tm.id as TrainerId,tm.name as TrainerName,tm.description as Description,d.name as Designation,d.id as DesignationId,\r\n"
			+ "			u.id as userId, u.user_name as userName,tm.file_id as FileId , fu.original_name as FileUrl \r\n"
			+ "			from trainer_master tm \r\n"
			+ "			left join users u on tm.user_id=u.id and u.is_active=true "
			+ " left join file_upload fu on fu.id=tm.file_id \r\n"
			+ "			left join designation d on d.id=tm.designation_id and d.is_active=true"
			+ "         where tm.is_active=true and  tm.id=:id	", nativeQuery = true)
	IListTrainer findByTrainerId(@Param("id") Long id, Class<IListTrainer> class1);

	TrainersMasterEntity findByNameIgnoreCase(String name);

	List<TrainersMasterEntity> findByIdIn(ArrayList<Long> trainerId);

	List<TrainersMasterEntity> findByDesignationId(Long designationId);

	TrainersMasterEntity findByDesignationIdAndName(Long id, String trainerName1);

	@Transactional
	@Modifying
	@Query("update TrainersMasterEntity set isActive = false where id in :ids")
	void deleteAllByIdIn(List<Long> ids);


}
