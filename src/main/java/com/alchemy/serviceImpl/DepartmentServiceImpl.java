package com.alchemy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alchemy.dto.DeleteId;
import com.alchemy.dto.DepartmentDto;
import com.alchemy.entities.DepartmentEntity;
import com.alchemy.entities.TrackDepartmentEntity;
import com.alchemy.exceptionHandling.ResourceNotFoundException;
import com.alchemy.iListDto.IListDepartment;
import com.alchemy.repositories.DepartmentRepository;
import com.alchemy.repositories.TrackDepartmentRepository;
import com.alchemy.repositories.UserTrackRepository;
import com.alchemy.serviceInterface.DepartmentInterface;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.Pagination;

@Service
public class DepartmentServiceImpl implements DepartmentInterface {

	@Autowired
	private DepartmentRepository deptRepo;

	@Autowired
	UserTrackRepository userTrackRepository;

	@Autowired
	TrackDepartmentRepository trackDepartmentRepository;

	@Override
	public Page<IListDepartment> getAllDepartment(String search, String pageNumber, String pageSize) throws Exception {
		Page<IListDepartment> iListDepartment;

		Pageable pageable = new Pagination().getPagination(pageNumber, pageSize);

		if (search == "" || search == null || search.length() == 0) {

			iListDepartment = this.deptRepo.findByOrderByIdDesc(pageable, IListDepartment.class);
		} else {

			iListDepartment = this.deptRepo.findByNameContainingIgnoreCase(search, pageable, IListDepartment.class);
		}
		return iListDepartment;
	}

	@Override
	public List<IListDepartment> findAllDepartmentList(Class<IListDepartment> class1) {
		List<IListDepartment> departmentDto = this.deptRepo.findByOrderByIdDesc(IListDepartment.class);
		return departmentDto;
	}

	@Override
	public DepartmentDto updateDepartment(DepartmentDto deptDto, Long id, Long userId) {
		DepartmentEntity deptEntity = this.deptRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DEPARTMENT_NOT_FOUND));

		DepartmentEntity deptName = this.deptRepo.findByNameIgnoreCaseAndIsActiveTrue(deptDto.getName());

		if (deptName != null && !deptEntity.getName().equalsIgnoreCase(deptDto.getName())) {
			throw new ResourceNotFoundException(ErrorMessageCode.DEPARTMENT_ALREADY_EXISTS);
		}

		deptEntity.setName(deptDto.getName());
		deptEntity.setDescription(deptDto.getDescription());
		deptEntity.setUpdatedBy(userId);

		this.deptRepo.save(deptEntity);
		return deptDto;

	}

	@Override
	public DepartmentDto addDepartment(DepartmentDto deptDto, Long userId) {

		DepartmentEntity deptEntity = new DepartmentEntity();

		DepartmentEntity deptName = this.deptRepo.findByNameIgnoreCaseAndIsActiveTrue(deptDto.getName());
		if (deptName == null) {
			deptEntity.setName(deptDto.getName());
			deptEntity.setDescription(deptDto.getDescription());
			deptEntity.setCreatedBy(userId);
			this.deptRepo.save(deptEntity);
			return deptDto;
		} else {
			throw new ResourceNotFoundException(ErrorMessageCode.DEPARTMENT_ALREADY_EXISTS);
		}
	}

	@Override
	public void deleteDepartmentById(Long deptId, Long userId) {
		DepartmentEntity entity = deptRepo.findById(deptId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageCode.DEPARTMENT_NOT_FOUND));
		entity.setIsActive(false);
		entity.setUpdatedBy(userId);

		List<TrackDepartmentEntity> trackDeptEntity = trackDepartmentRepository.findByDepartmentEntityId(deptId);

		for (int i = 0; i < trackDeptEntity.size(); i++) {
			trackDeptEntity.get(i).setDepartmentEntity(null);
		}
//		List<UserTrackEntity> userTrackEntity = userTrackRepository.findByDepartmentIdId(deptId);
//
//		for (int i = 0; i < userTrackEntity.size(); i++) {
//
//			userTrackEntity.get(i).setDepartmentId(null);
//		}

//		List<TestimonialEntity> testimonialList = this.testimonialRepository.findByDepartmentId(deptId);
//		for (int i = 0; i < testimonialList.size(); i++) {
//
//			testimonialList.get(i).setDepartment(null);
//		}

		this.deptRepo.save(entity);
//		userTrackRepository.saveAll(userTrackEntity);
		// testimonialRepository.saveAll(testimonialList);
		trackDepartmentRepository.saveAll(trackDeptEntity);

	}

	@Override
	public void deleteMultipleDepartmentById(DeleteId ids, Long userId) {

		List<DepartmentEntity> entity = deptRepo.findAllById(ids.getIds());
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).setUpdatedBy(userId);
		}

		deptRepo.deleteAllByIdIn(ids.getIds());

	}
}
