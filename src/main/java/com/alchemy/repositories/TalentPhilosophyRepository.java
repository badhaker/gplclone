package com.alchemy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alchemy.entities.TalentPhilosophyEntity;
import com.alchemy.iListDto.IListTalentPhilosophy;

@Repository
public interface TalentPhilosophyRepository extends JpaRepository<TalentPhilosophyEntity, Long> {

	@Query(value = "select tp.id as Id,tp.name as Name,tp.chro_message as ChroMessage,fu.id as FileId,fu.original_name as FileUrl from talent_philosophy tp \r\n"
			+ "join file_upload fu on fu.id=tp.file_id where tp.is_active=true", nativeQuery = true)
	Page<IListTalentPhilosophy> findByOrderByIdDesc(Pageable pageable, Class<IListTalentPhilosophy> class1);

}
