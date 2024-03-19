package com.alchemy.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.CareerAspirationEntity;
import com.alchemy.entities.PreferenceEntity;

@Repository
public interface PreferencesRepository extends JpaRepository<PreferenceEntity, Long> {

	List<PreferenceEntity> findByCareerAspirationIdOrderByIdAsc(CareerAspirationEntity careerAspiration);

	Optional<PreferenceEntity> findById(String experience);

	@Transactional
	@Modifying
	@Query("UPDATE PreferenceEntity SET is_active = false,updated_by=:userId WHERE career_aspiration_id IN :ids")
	void deleteAll(@Param("userId") Long userId, @Param("ids") List<Long> ids);

}
