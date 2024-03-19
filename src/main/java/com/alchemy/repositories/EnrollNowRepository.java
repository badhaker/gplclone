package com.alchemy.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.alchemy.entities.EnrollNowEntity;

@Repository
public interface EnrollNowRepository extends JpaRepository<EnrollNowEntity, Long>{

//	@Query(value = "select id as Id, employee_id as UserId,zone as Zone,function_name as FunctionName,track_id as LearningTrackId,department_id as DepartmentId,\r\n"
//			+ "region as Region,project as Project,postion_title as PositionTitle,employee_grade as EmployeeGrade,employee_level as EmployeeLevel\r\n"
//			+ "from enroll_now_entity where is_Active = true\r\n"
//			+ "order by id DESC",nativeQuery =  true)
//	Page<IListEnroll> findByOrderByIdDesc(org.springframework.data.domain.Pageable pageable, Class<IListEnroll> class1);

}
